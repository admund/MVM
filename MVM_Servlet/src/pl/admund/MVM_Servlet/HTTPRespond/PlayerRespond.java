package pl.admund.MVM_Servlet.HTTPRespond;

import java.util.ArrayList;
import java.util.HashMap;

import javax.mail.MessagingException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import pl.admund.MVM_Servlet.League.LeagueDBUtils;
import pl.admund.MVM_Servlet.Player.Player;
import pl.admund.MVM_Servlet.Player.PlayerDBUtils;
import pl.admund.MVM_Servlet.Tactic.Tactic;
import pl.admund.MVM_Servlet.Tactic.TacticDBUtils;
import pl.admund.MVM_Servlet.Tactic.TeamMatchSquad;
import pl.admund.MVM_Servlet.Team.Stadium;
import pl.admund.MVM_Servlet.Team.Team;
import pl.admund.MVM_Servlet.Team.TeamDBUtils;
import pl.admund.MVM_Servlet.Volleyballer.Attributes;
import pl.admund.MVM_Servlet.Volleyballer.Volleyballer;
import pl.admund.MVM_Servlet.Volleyballer.VolleyballerDBUtils;
import pl.admund.MVM_Servlet.XML.XMLUtils;
import pl.admund.Constans.ErrorCode;

public class PlayerRespond 
{
	public static String createNewPlayer(HashMap<String, String> _hashMap)
	{
		System.out.println("createNewPlayer()");
		System.out.println(_hashMap.get("login") +" "+ _hashMap.get("pass") + " " + _hashMap.get("mail"));
		
		Team tmpTeam = Team.createTeamAndVolleyballers(_hashMap.get("login") + " team", false, 0);
		Player tmpPlayer = Player.createPlayer(tmpTeam.getId(), _hashMap.get("login"), _hashMap.get("pass"), tmpTeam.getId(), _hashMap.get("mail"));
		Stadium tmpStadium = Stadium.createStadium(_hashMap.get("login") + " stadium", tmpTeam.getId());
		
		if(!PlayerDBUtils.checkIsMailFree(tmpPlayer.getMail()))
			return XMLUtils.createErrorXML("Mail is occupied.", ErrorCode.ERR_LOGIN_OCCUPIED);
		
		if( PlayerDBUtils.createNewPlayer(tmpPlayer, tmpTeam, tmpStadium) )
		{
	        Document tmpDoc = XMLUtils.startXMLDocument("new_player");
	        Element rootElement = (Element)tmpDoc.getFirstChild();
			tmpPlayer.addToXmlDoc(tmpDoc, rootElement);
			
			try
			{
				tmpPlayer.sendActivitationMail();
			} 
			catch (MessagingException e) 
			{
				return XMLUtils.createErrorXML("Can't send mail.", ErrorCode.ERR_DB_ERROR);
			}
			
			System.out.println(XMLUtils.XMLDocumentToString(tmpDoc));
			return XMLUtils.XMLDocumentToString(tmpDoc);
		}
		else
			return XMLUtils.createErrorXML("DB error.", ErrorCode.ERR_DB_ERROR);
	}
	
	public static String deletePlayer(HashMap<String, String> _hashMap)
	{
		System.out.println("deletePlayer()");
		//TODO
		int playerId = Integer.parseInt(_hashMap.get("player_id"));
		//System.out.println( DBUtils.deleteTeam(_teamId) );
		System.out.println( PlayerDBUtils.deletePlayer(playerId) );
		
		//TODO return respond xml
		return "XML";
	}
	
	public static String login(HashMap<String, String> _hashMap)
	{
		System.out.println("login()");
		try
		{
			System.out.println( "Mail " + _hashMap.get("mail") + " Pass: " + _hashMap.get("pass"));
			
			Player tmpPlayer = PlayerDBUtils.getPlayerFromMailAndPass(_hashMap.get("mail"), _hashMap.get("pass"));
			if(tmpPlayer == null)
				return XMLUtils.createErrorXML("Player not found.", ErrorCode.ERR_PLAYER_NOT_FOUND);
			
			if(tmpPlayer.getStatus() == 0)
				return XMLUtils.createErrorXML("Player hasn't click on activate link.", ErrorCode.ERR_PLAYER_NOT_ACTIV);
			
			Team tmpTeam = TeamDBUtils.getTeamFromId(tmpPlayer.getTeamId());
			if(tmpTeam == null)
				return XMLUtils.createErrorXML("Team not found.", ErrorCode.ERR_TEAM_NOT_FOUND);
			
			ArrayList<Volleyballer> tmpVolleyballerList = VolleyballerDBUtils.getVolleyballerListFromTeamId(tmpTeam.getId());
			System.out.println( "tmpVolleyballerList " + tmpVolleyballerList.size());
			if(tmpVolleyballerList == null || tmpVolleyballerList.isEmpty())
				return XMLUtils.createErrorXML("Volleyballers list is empty.", ErrorCode.ERR_TEAM_NOT_FOUND);//TODO
			
			tmpTeam.setVolleyballerList(tmpVolleyballerList);
			
			for(int i=0; i<tmpVolleyballerList.size(); i++)
			{
				Attributes tmpAttr = VolleyballerDBUtils.getAttributesFromVolleyballerId(tmpVolleyballerList.get(i).getId());
				if(tmpAttr == null)
					return XMLUtils.createErrorXML("Attribute not found.", ErrorCode.ERR_STADIUM_NOT_FOUND);
				
				tmpVolleyballerList.get(i).setAttributes(tmpAttr);
			}
			
			Stadium tmpStadium = TeamDBUtils.getStadiumFromPlayerId(tmpTeam.getId());
			if(tmpStadium == null)
				return XMLUtils.createErrorXML("Stadium not found.", ErrorCode.ERR_STADIUM_NOT_FOUND);
			
			Document tmpDoc = XMLUtils.startXMLDocument("login");
	        Element rootElement = (Element)tmpDoc.getFirstChild();
	        
	        tmpPlayer.addToXmlDoc(tmpDoc, rootElement);
	        tmpTeam.addToXmlDoc(tmpDoc, rootElement);
	        tmpStadium.addToXmlDoc(tmpDoc, rootElement);
	        
	        Tactic tmpTactic = TacticDBUtils.getTactic(tmpTeam.getId());
	        if(tmpTactic != null)
	        	tmpTactic.addToXmlDoc(tmpDoc, rootElement);
	        
	        String squadTab = TeamDBUtils.getSquad(tmpTeam.getId());
			if(squadTab != null)
			{
				TeamMatchSquad tmTeamMatchSquad = TeamMatchSquad.parseTeamMatchSquad(squadTab);
				tmTeamMatchSquad.addToXmlDoc(tmpDoc, rootElement);
			}
	
			return XMLUtils.XMLDocumentToString(tmpDoc);
		}
		catch(Exception e)
		{
			System.out.println( "C:"  + e.getClass() + "\nM:" + e.getMessage()  );
			return XMLUtils.createErrorXML("Player not found.", ErrorCode.ERR_PLAYER_NOT_FOUND);
		}
	}
	
	public static String getPlayerProfile(HashMap<String, String> _hashMap)
	{
		int playerId = Integer.parseInt(_hashMap.get("pid"));
		String pass = _hashMap.get("pass");
		
		if( !PlayerDBUtils.checkPass(playerId, pass) )
			return XMLUtils.createErrorXML("Wrong pass.", ErrorCode.ERR_WRONG_PASS);
		
		Document tmpDoc = XMLUtils.startXMLDocument("profile");
        Element rootElement = (Element)tmpDoc.getFirstChild();
		
		int userId = Integer.parseInt(_hashMap.get("uid"));
		XMLUtils.addToXMLDoc(tmpDoc, rootElement, "uid", ""+userId);
		
		String playerName = PlayerDBUtils.getNameFromUid(userId);
		if(playerName == null)
			return XMLUtils.createErrorXML("DB ERROR.", ErrorCode.ERR_DB_ERROR);
		XMLUtils.addToXMLDoc(tmpDoc, rootElement, "user_name", playerName);
		
		int teamId = TeamDBUtils.getTeamIdFromPlayerId(userId);
		if(teamId == -1)
			return XMLUtils.createErrorXML("DB ERROR.", ErrorCode.ERR_DB_ERROR);
		XMLUtils.addToXMLDoc(tmpDoc, rootElement, "team_id", ""+teamId);
		
		String teamName = TeamDBUtils.getTeamNameFromId(teamId);
		if(teamName == null)
			return XMLUtils.createErrorXML("DB ERROR.", ErrorCode.ERR_DB_ERROR);
		XMLUtils.addToXMLDoc(tmpDoc, rootElement, "team_name", teamName);
		
		int leagueId = LeagueDBUtils.getLeagueIdFromId(teamId);
		if(leagueId == -1)
			return XMLUtils.createErrorXML("DB ERROR.", ErrorCode.ERR_DB_ERROR);
		XMLUtils.addToXMLDoc(tmpDoc, rootElement, "league_id", ""+leagueId);
		
		String leagueName = LeagueDBUtils.getLeagueNameFromId(leagueId);
		if(leagueName == null)
			return XMLUtils.createErrorXML("DB ERROR.", ErrorCode.ERR_DB_ERROR);
		XMLUtils.addToXMLDoc(tmpDoc, rootElement, "league_name", leagueName);
		
		return XMLUtils.XMLDocumentToString(tmpDoc);
	}
	
	public static String checkActivitationLink(HashMap<String, String> _hashMap)
	{
		int playerId = Integer.parseInt(_hashMap.get("pid"));
		String pass = _hashMap.get("pass");
		
		if( !PlayerDBUtils.checkPass(playerId, pass) )
			return XMLUtils.createErrorXML("Wrong pass.", ErrorCode.ERR_WRONG_PASS);
		
		String hash = _hashMap.get("hash");
		if(hash.equals("terefere"))
		{
			if(PlayerDBUtils.updateStatus(playerId))
				return "Twoje konto zostalo aktywowane";
			else
				return "Twoje konto NIE zostalo aktywowane";
		}
		
		return "Ni hu hu hakierze jebany!!!";
	}
}

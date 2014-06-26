package pl.admund.MVM_Servlet.HTTPRespond;

import java.util.HashMap;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import pl.admund.MVM_Servlet.Player.PlayerDBUtils;
import pl.admund.MVM_Servlet.Tactic.TeamMatchSquad;
import pl.admund.MVM_Servlet.Team.Team;
import pl.admund.MVM_Servlet.Team.TeamDBUtils;
import pl.admund.MVM_Servlet.XML.XMLUtils;
import pl.admund.Constans.ErrorCode;

public class SquadRespond 
{
	public static String setSquad(HashMap<String, String> _hashMap)
	{
		System.out.println("setSquad()");
		
		int playerId = Integer.parseInt(_hashMap.get("pid"));
		String pass = _hashMap.get("pass");
		
		if( !PlayerDBUtils.checkPass(playerId, pass) )
			return XMLUtils.createErrorXML("Wrong pass.", ErrorCode.ERR_WRONG_PASS);
			
		int teamId = TeamDBUtils.getTeamIdFromPlayerId(playerId);
		Team team = TeamDBUtils.getTeamFromId(teamId);
		if(team == null)
			return XMLUtils.createErrorXML("DataBase error. Player don't have team.", ErrorCode.ERR_DB_ERROR);

		String squad = _hashMap.get("squad");
		String[] idList = squad.split(",");
		
		TeamMatchSquad tmTeamMatchSquad = TeamMatchSquad.parseTeamMatchSquad(squad);
		
        Document tmpDoc = XMLUtils.startXMLDocument("squad");
        Element rootElement = (Element)tmpDoc.getFirstChild();
        
        tmTeamMatchSquad.addToXmlDoc(tmpDoc, rootElement);
		
		if(TeamDBUtils.checkSquadExist(team.getId()))
		{
			if( TeamDBUtils.updateSquad(team.getId(), idList))
				return XMLUtils.XMLDocumentToString(tmpDoc);
			else
				return XMLUtils.createErrorXML("DataBase error.", ErrorCode.ERR_DB_ERROR);
		}
		else
		{
			if( TeamDBUtils.addSquad(team.getId(), idList))
				return XMLUtils.XMLDocumentToString(tmpDoc);
			else
				return XMLUtils.createErrorXML("DataBase error.", ErrorCode.ERR_DB_ERROR);
		}
	}
	
	public static String getSquad(HashMap<String, String> _hashMap)
	{
		System.out.println("getSquad()");
		
		int playerId = Integer.parseInt(_hashMap.get("pid"));
		String pass = _hashMap.get("pass");
		
		if( !PlayerDBUtils.checkPass(playerId, pass) )
			return XMLUtils.createErrorXML("Wrong pass.", ErrorCode.ERR_WRONG_PASS);
		
		int teamId = Integer.parseInt(_hashMap.get("team_id"));
		
		String squadTab = TeamDBUtils.getSquad(teamId);
		if(squadTab == null)
			return XMLUtils.createErrorXML("Squad not set", ErrorCode.ERR_SQUAD_NOT_SET);
		
		TeamMatchSquad tmTeamMatchSquad = TeamMatchSquad.parseTeamMatchSquad(squadTab);
		
        Document tmpDoc = XMLUtils.startXMLDocument("squad");
        Element rootElement = (Element)tmpDoc.getFirstChild();
        
        tmTeamMatchSquad.addToXmlDoc(tmpDoc, rootElement);

		return XMLUtils.XMLDocumentToString(tmpDoc);
	}
}

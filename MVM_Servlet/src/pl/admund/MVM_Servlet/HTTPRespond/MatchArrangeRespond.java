package pl.admund.MVM_Servlet.HTTPRespond;

import java.util.ArrayList;
import java.util.HashMap;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import pl.admund.MVM_Servlet.TCPServerInfo;
import pl.admund.MVM_Servlet.Match.Match;
import pl.admund.MVM_Servlet.Match.MatchDBUtils;
import pl.admund.MVM_Servlet.Messages.Message;
import pl.admund.MVM_Servlet.Messages.MessagesDBUtils;
import pl.admund.MVM_Servlet.Player.PlayerDBUtils;
import pl.admund.MVM_Servlet.XML.XMLUtils;
import pl.admund.Constans.ErrorCode;

public class MatchArrangeRespond
{
	public static String matchProp(HashMap<String, String> _hashMap)
	{
		System.out.println("matchProp()");
		
		int playerId = Integer.parseInt(_hashMap.get("pid"));
		String pass = _hashMap.get("pass");
		
		if( !PlayerDBUtils.checkPass(playerId, pass) )
			return XMLUtils.createErrorXML("Wrong pass.", ErrorCode.ERR_WRONG_PASS);
			
		int matchId = Integer.parseInt(_hashMap.get("match_id"));
		int propCnt = Integer.parseInt(_hashMap.get("prop_cnt"));
		String terms = _hashMap.get("terms");
		
		Match tmpMatch = MatchDBUtils.getMatchInfoFromId(matchId);
		Message tmpMessage;
		if(tmpMatch.getHomeTeamId() == playerId)
			tmpMessage = Message.createMatchPropMessage(tmpMatch.getAwayTeamId(), tmpMatch, propCnt, terms);
		else
			tmpMessage = Message.createMatchPropMessage(tmpMatch.getHomeTeamId(), tmpMatch, propCnt, terms);
		
		if(MessagesDBUtils.addMessage(tmpMessage))
			return XMLUtils.createOkResultXML();
		else
			return XMLUtils.createErrorXML("DataBase error. Can't send message.", ErrorCode.ERR_DB_ERROR);
	}
	
	public static String matchAccept(HashMap<String, String> _hashMap)
	{
		System.out.println("matchAccept()");
		int playerId = Integer.parseInt(_hashMap.get("pid"));
		String pass = _hashMap.get("pass");
		
		if( !PlayerDBUtils.checkPass(playerId, pass) )
			return XMLUtils.createErrorXML("Wrong pass.", ErrorCode.ERR_WRONG_PASS);
			
		int matchId = Integer.parseInt(_hashMap.get("match_id"));
		String term = _hashMap.get("term");
		
		Match tmpMatch = MatchDBUtils.getMatchInfoFromId(matchId);
		Message tmpMessage;
		if(tmpMatch.getHomeTeamId() == playerId)
			tmpMessage = Message.createMatchAcceptMessage(tmpMatch.getAwayTeamId(), tmpMatch, term);
		else
			tmpMessage = Message.createMatchAcceptMessage(tmpMatch.getHomeTeamId(), tmpMatch, term);
		
		if(!MatchDBUtils.updateMatchTerm(matchId, term))
			return XMLUtils.createErrorXML("DataBase error. Can't accept term.", ErrorCode.ERR_DB_ERROR);
		
		if(MessagesDBUtils.addMessage(tmpMessage))
			return XMLUtils.createOkResultXML();
		else
			return XMLUtils.createErrorXML("DataBase error. Can't send message.", ErrorCode.ERR_DB_ERROR);
	}
	
	public static String matchNext(HashMap<String, String> _hashMap)
	{
		System.out.println("matchNext()");
		int playerId = Integer.parseInt(_hashMap.get("pid"));
		String pass = _hashMap.get("pass");
		
		if( !PlayerDBUtils.checkPass(playerId, pass) )
			return XMLUtils.createErrorXML("Wrong pass.", ErrorCode.ERR_WRONG_PASS);
		
		ArrayList<Match> matchList = MatchDBUtils.getPlayingMatch(playerId);
		if(matchList.isEmpty())
		{
			Match nextMatch = MatchDBUtils.getNextMatch(playerId);
			
	        Document tmpDoc = XMLUtils.startXMLDocument("next_match");
	        Element rootElement = (Element)tmpDoc.getFirstChild();
	        nextMatch.addToXmlDoc(tmpDoc, rootElement);
			
			System.out.println(XMLUtils.XMLDocumentToString(tmpDoc));
			return XMLUtils.XMLDocumentToString(tmpDoc);
		}
		else
		{
	        Document tmpDoc = XMLUtils.startXMLDocument("next_match");
	        Element rootElement = (Element)tmpDoc.getFirstChild();
	        
	        TCPServerInfo.addToXmlDoc(tmpDoc, rootElement);//TODO FAKE
			
			System.out.println(XMLUtils.XMLDocumentToString(tmpDoc));
			return XMLUtils.XMLDocumentToString(tmpDoc);
		}
	}
}

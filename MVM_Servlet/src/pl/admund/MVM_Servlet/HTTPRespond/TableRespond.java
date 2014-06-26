package pl.admund.MVM_Servlet.HTTPRespond;

import java.util.ArrayList;
import java.util.HashMap;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import pl.admund.MVM_Servlet.League.LeagueDBUtils;
import pl.admund.MVM_Servlet.League.TeamTableInfo;
import pl.admund.MVM_Servlet.Player.PlayerDBUtils;
import pl.admund.MVM_Servlet.Team.TeamDBUtils;
import pl.admund.MVM_Servlet.XML.XMLUtils;
import pl.admund.Constans.ErrorCode;

public class TableRespond 
{
	public static String getTable(HashMap<String, String> _hashMap)
	{
		System.out.println("getTable()");
		
		int playerId = Integer.parseInt(_hashMap.get("pid"));
		String pass = _hashMap.get("pass");
		
		if( !PlayerDBUtils.checkPass(playerId, pass) )
			return XMLUtils.createErrorXML("Wrong pass.", ErrorCode.ERR_WRONG_PASS);
		
		int leagueId = Integer.parseInt(_hashMap.get("league_id"));
		
        Document tmpDoc = XMLUtils.startXMLDocument("table");
        Element rootElement = (Element)tmpDoc.getFirstChild();
		
		ArrayList<Integer> teamList = LeagueDBUtils.getTeamListFromLeagueId(leagueId);
		for(int i=0; i<teamList.size(); i++)
		{
			int tmpTeamId = teamList.get(i);
			TeamTableInfo tmpTeamTableInfo = new TeamTableInfo();
			tmpTeamTableInfo.setTeamId(tmpTeamId);
			
			String teamName = TeamDBUtils.getTeamNameFromId(tmpTeamId);
			if(teamName == null)
				return XMLUtils.createErrorXML("DB ERROR.", ErrorCode.ERR_DB_ERROR);
			tmpTeamTableInfo.setTeamName(teamName);
			
			int points = LeagueDBUtils.getPointsCnt(tmpTeamId, leagueId);
			if( points == -1)
				return XMLUtils.createErrorXML("DB ERROR.", ErrorCode.ERR_DB_ERROR);
			tmpTeamTableInfo.setPoints(points);
			
			int matachCnt = LeagueDBUtils.getMatchCnt(tmpTeamId, leagueId);
			if( matachCnt == -1)
				return XMLUtils.createErrorXML("DB ERROR.", ErrorCode.ERR_DB_ERROR);
			tmpTeamTableInfo.setMatchCnt(matachCnt);
			
			int matchWinCnt = LeagueDBUtils.getMatchWinCnt(tmpTeamId, leagueId);
			if( matchWinCnt == -1)
				return XMLUtils.createErrorXML("DB ERROR.", ErrorCode.ERR_DB_ERROR);
			tmpTeamTableInfo.setMatchWin(matchWinCnt);
			
			int matchLooseCnt = LeagueDBUtils.getMatchLooseCnt(tmpTeamId, leagueId);
			if( matchLooseCnt == -1)
				return XMLUtils.createErrorXML("DB ERROR.", ErrorCode.ERR_DB_ERROR);
			tmpTeamTableInfo.setMatchLoose(matchLooseCnt);
			
			int setWin = LeagueDBUtils.getSetWinCnt(tmpTeamId, leagueId);
			if( setWin == -1)
				return XMLUtils.createErrorXML("DB ERROR.", ErrorCode.ERR_DB_ERROR);
			tmpTeamTableInfo.setSetWin(setWin);
			
			int setLoose = LeagueDBUtils.getSetLooseCnt(tmpTeamId, leagueId);
			if( setLoose == -1)
				return XMLUtils.createErrorXML("DB ERROR.", ErrorCode.ERR_DB_ERROR);
			tmpTeamTableInfo.setSetLoose(setLoose);
			
			int littlePointsWin = LeagueDBUtils.getWinLittlePoints(tmpTeamId, leagueId);
			if( littlePointsWin == -1)
				return XMLUtils.createErrorXML("DB ERROR.", ErrorCode.ERR_DB_ERROR);
			tmpTeamTableInfo.setLittleWin(littlePointsWin);
			
			int littlePointsLoose = LeagueDBUtils.getLooseLittlePoints(tmpTeamId, leagueId);
			if( littlePointsLoose == -1)
				return XMLUtils.createErrorXML("DB ERROR.", ErrorCode.ERR_DB_ERROR);
			tmpTeamTableInfo.setLittleLoose(littlePointsLoose);
			
			//tmpTeamTableInfo.computeRatio();
			
			tmpTeamTableInfo.addToXmlDoc(tmpDoc, rootElement);
		}

		return XMLUtils.XMLDocumentToString(tmpDoc);
	}
}

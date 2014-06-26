package pl.admund.MVM_Server.Season;

import java.util.ArrayList;
import pl.admund.MVM_Server.DBConnect.DBUtils;
import pl.admund.MVM_Server.MatchEngine.MatchInfo;
import pl.admund.MVM_Server.Messages.Message;

public class SeasonTask 
{
	public static boolean addLeagueSeasonSchedule(int leagueId, int season)
	{
		ArrayList<Integer> teamIdList = DBUtils.getTeamIdFromLeagueId(leagueId);
		
		if(teamIdList.size() != 10)
			return false;
		
		// 1 round
		if( !DBUtils.addMatch( MatchInfo.createMatch(leagueId, season, teamIdList.get(9), teamIdList.get(0), 1)) )
			return false;
		if( !DBUtils.addMatch( MatchInfo.createMatch(leagueId, season, teamIdList.get(1), teamIdList.get(7), 1)) )
			return false;
		if( !DBUtils.addMatch( MatchInfo.createMatch(leagueId, season, teamIdList.get(2), teamIdList.get(6), 1)) )
			return false;
		if( !DBUtils.addMatch( MatchInfo.createMatch(leagueId, season, teamIdList.get(4), teamIdList.get(3), 1)) )
			return false;
		if( !DBUtils.addMatch( MatchInfo.createMatch(leagueId, season, teamIdList.get(5), teamIdList.get(8), 1)) )
			return false;
		// 2 round
		if( !DBUtils.addMatch( MatchInfo.createMatch(leagueId, season, teamIdList.get(8), teamIdList.get(9), 2)) )
			return false;
		if( !DBUtils.addMatch( MatchInfo.createMatch(leagueId, season, teamIdList.get(3), teamIdList.get(5), 2)) )
			return false;
		if( !DBUtils.addMatch( MatchInfo.createMatch(leagueId, season, teamIdList.get(6), teamIdList.get(4), 2)) )
			return false;
		if( !DBUtils.addMatch( MatchInfo.createMatch(leagueId, season, teamIdList.get(7), teamIdList.get(2), 2)) )
			return false;
		if( !DBUtils.addMatch( MatchInfo.createMatch(leagueId, season, teamIdList.get(0), teamIdList.get(1), 2)) )
			return false;
		// 3 round
		if( !DBUtils.addMatch( MatchInfo.createMatch(leagueId, season, teamIdList.get(1), teamIdList.get(9), 3)) )
			return false;
		if( !DBUtils.addMatch( MatchInfo.createMatch(leagueId, season, teamIdList.get(2), teamIdList.get(0), 3)) )
			return false;
		if( !DBUtils.addMatch( MatchInfo.createMatch(leagueId, season, teamIdList.get(4), teamIdList.get(7), 3)) )
			return false;
		if( !DBUtils.addMatch( MatchInfo.createMatch(leagueId, season, teamIdList.get(5), teamIdList.get(6), 3)) )
			return false;
		if( !DBUtils.addMatch( MatchInfo.createMatch(leagueId, season, teamIdList.get(8), teamIdList.get(3), 3)) )
			return false;
		// 4 round
		if( !DBUtils.addMatch( MatchInfo.createMatch(leagueId, season, teamIdList.get(9), teamIdList.get(3), 4)) )
			return false;
		if( !DBUtils.addMatch( MatchInfo.createMatch(leagueId, season, teamIdList.get(6), teamIdList.get(8), 4)) )
			return false;
		if( !DBUtils.addMatch( MatchInfo.createMatch(leagueId, season, teamIdList.get(7), teamIdList.get(5), 4)) )
			return false;
		if( !DBUtils.addMatch( MatchInfo.createMatch(leagueId, season, teamIdList.get(0), teamIdList.get(4), 4)) )
			return false;
		if( !DBUtils.addMatch( MatchInfo.createMatch(leagueId, season, teamIdList.get(1), teamIdList.get(2), 4)) )
			return false;
		// 5 round
		if( !DBUtils.addMatch( MatchInfo.createMatch(leagueId, season, teamIdList.get(2), teamIdList.get(9), 5)) )
			return false;
		if( !DBUtils.addMatch( MatchInfo.createMatch(leagueId, season, teamIdList.get(4), teamIdList.get(1), 5)) )
			return false;
		if( !DBUtils.addMatch( MatchInfo.createMatch(leagueId, season, teamIdList.get(5), teamIdList.get(0), 5)) )
			return false;
		if( !DBUtils.addMatch( MatchInfo.createMatch(leagueId, season, teamIdList.get(7), teamIdList.get(8), 5)) )
			return false;
		if( !DBUtils.addMatch( MatchInfo.createMatch(leagueId, season, teamIdList.get(3), teamIdList.get(6), 5)) )
			return false;
		// 6 round
		if( !DBUtils.addMatch( MatchInfo.createMatch(leagueId, season, teamIdList.get(9), teamIdList.get(6), 6)) )
			return false;
		if( !DBUtils.addMatch( MatchInfo.createMatch(leagueId, season, teamIdList.get(7), teamIdList.get(3), 6)) )
			return false;
		if( !DBUtils.addMatch( MatchInfo.createMatch(leagueId, season, teamIdList.get(0), teamIdList.get(8), 6)) )
			return false;
		if( !DBUtils.addMatch( MatchInfo.createMatch(leagueId, season, teamIdList.get(1), teamIdList.get(5), 6)) )
			return false;
		if( !DBUtils.addMatch( MatchInfo.createMatch(leagueId, season, teamIdList.get(2), teamIdList.get(4), 6)) )
			return false;
		// 7 round
		if( !DBUtils.addMatch( MatchInfo.createMatch(leagueId, season, teamIdList.get(4), teamIdList.get(9), 7)) )
			return false;
		if( !DBUtils.addMatch( MatchInfo.createMatch(leagueId, season, teamIdList.get(5), teamIdList.get(2), 7)) )
			return false;
		if( !DBUtils.addMatch( MatchInfo.createMatch(leagueId, season, teamIdList.get(8), teamIdList.get(1), 7)) )
			return false;
		if( !DBUtils.addMatch( MatchInfo.createMatch(leagueId, season, teamIdList.get(3), teamIdList.get(0), 7)) )
			return false;
		if( !DBUtils.addMatch( MatchInfo.createMatch(leagueId, season, teamIdList.get(6), teamIdList.get(7), 7)) )
			return false;
		// 8 round
		if( !DBUtils.addMatch( MatchInfo.createMatch(leagueId, season, teamIdList.get(9), teamIdList.get(7), 8)) )
			return false;
		if( !DBUtils.addMatch( MatchInfo.createMatch(leagueId, season, teamIdList.get(0), teamIdList.get(6), 8)) )
			return false;
		if( !DBUtils.addMatch( MatchInfo.createMatch(leagueId, season, teamIdList.get(1), teamIdList.get(3), 8)) )
			return false;
		if( !DBUtils.addMatch( MatchInfo.createMatch(leagueId, season, teamIdList.get(2), teamIdList.get(8), 8)) )
			return false;
		if( !DBUtils.addMatch( MatchInfo.createMatch(leagueId, season, teamIdList.get(4), teamIdList.get(5), 8)) )
			return false;
		// 9 round
		if( !DBUtils.addMatch( MatchInfo.createMatch(leagueId, season, teamIdList.get(5), teamIdList.get(9), 9)) )
			return false;
		if( !DBUtils.addMatch( MatchInfo.createMatch(leagueId, season, teamIdList.get(8), teamIdList.get(4), 9)) )
			return false;
		if( !DBUtils.addMatch( MatchInfo.createMatch(leagueId, season, teamIdList.get(3), teamIdList.get(2), 9)) )
			return false;
		if( !DBUtils.addMatch( MatchInfo.createMatch(leagueId, season, teamIdList.get(6), teamIdList.get(1), 9)) )
			return false;
		if( !DBUtils.addMatch( MatchInfo.createMatch(leagueId, season, teamIdList.get(7), teamIdList.get(0), 9)) )
			return false;
		// 10 round
		if( !DBUtils.addMatch( MatchInfo.createMatch(leagueId, season, teamIdList.get(0), teamIdList.get(9), 10)) )
			return false;
		if( !DBUtils.addMatch( MatchInfo.createMatch(leagueId, season, teamIdList.get(7), teamIdList.get(1), 10)) )
			return false;
		if( !DBUtils.addMatch( MatchInfo.createMatch(leagueId, season, teamIdList.get(6), teamIdList.get(2), 10)) )
			return false;
		if( !DBUtils.addMatch( MatchInfo.createMatch(leagueId, season, teamIdList.get(3), teamIdList.get(4), 10)) )
			return false;
		if( !DBUtils.addMatch( MatchInfo.createMatch(leagueId, season, teamIdList.get(8), teamIdList.get(5), 10)) )
			return false;
		// 11 round
		if( !DBUtils.addMatch( MatchInfo.createMatch(leagueId, season, teamIdList.get(9), teamIdList.get(8), 11)) )
			return false;
		if( !DBUtils.addMatch( MatchInfo.createMatch(leagueId, season, teamIdList.get(5), teamIdList.get(3), 11)) )
			return false;
		if( !DBUtils.addMatch( MatchInfo.createMatch(leagueId, season, teamIdList.get(4), teamIdList.get(6), 11)) )
			return false;
		if( !DBUtils.addMatch( MatchInfo.createMatch(leagueId, season, teamIdList.get(2), teamIdList.get(7), 11)) )
			return false;
		if( !DBUtils.addMatch( MatchInfo.createMatch(leagueId, season, teamIdList.get(1), teamIdList.get(0), 11)) )
			return false;
		// 12 round
		if( !DBUtils.addMatch( MatchInfo.createMatch(leagueId, season, teamIdList.get(9), teamIdList.get(1), 12)) )
			return false;
		if( !DBUtils.addMatch( MatchInfo.createMatch(leagueId, season, teamIdList.get(0), teamIdList.get(2), 12)) )
			return false;
		if( !DBUtils.addMatch( MatchInfo.createMatch(leagueId, season, teamIdList.get(7), teamIdList.get(4), 12)) )
			return false;
		if( !DBUtils.addMatch( MatchInfo.createMatch(leagueId, season, teamIdList.get(6), teamIdList.get(5), 12)) )
			return false;
		if( !DBUtils.addMatch( MatchInfo.createMatch(leagueId, season, teamIdList.get(3), teamIdList.get(8), 12)) )
			return false;
		// 13 round
		if( !DBUtils.addMatch( MatchInfo.createMatch(leagueId, season, teamIdList.get(3), teamIdList.get(9), 13)) )
			return false;
		if( !DBUtils.addMatch( MatchInfo.createMatch(leagueId, season, teamIdList.get(8), teamIdList.get(6), 13)) )
			return false;
		if( !DBUtils.addMatch( MatchInfo.createMatch(leagueId, season, teamIdList.get(5), teamIdList.get(7), 13)) )
			return false;
		if( !DBUtils.addMatch( MatchInfo.createMatch(leagueId, season, teamIdList.get(4), teamIdList.get(0), 13)) )
			return false;
		if( !DBUtils.addMatch( MatchInfo.createMatch(leagueId, season, teamIdList.get(2), teamIdList.get(1), 13)) )
			return false;
		// 14 round
		if( !DBUtils.addMatch( MatchInfo.createMatch(leagueId, season, teamIdList.get(9), teamIdList.get(2), 14)) )
			return false;
		if( !DBUtils.addMatch( MatchInfo.createMatch(leagueId, season, teamIdList.get(1), teamIdList.get(4), 14)) )
			return false;
		if( !DBUtils.addMatch( MatchInfo.createMatch(leagueId, season, teamIdList.get(0), teamIdList.get(5), 14)) )
			return false;
		if( !DBUtils.addMatch( MatchInfo.createMatch(leagueId, season, teamIdList.get(8), teamIdList.get(7), 14)) )
			return false;
		if( !DBUtils.addMatch( MatchInfo.createMatch(leagueId, season, teamIdList.get(6), teamIdList.get(3), 14)) )
			return false;
		// 15 round
		if( !DBUtils.addMatch( MatchInfo.createMatch(leagueId, season, teamIdList.get(6), teamIdList.get(9), 15)) )
			return false;
		if( !DBUtils.addMatch( MatchInfo.createMatch(leagueId, season, teamIdList.get(3), teamIdList.get(7), 15)) )
			return false;
		if( !DBUtils.addMatch( MatchInfo.createMatch(leagueId, season, teamIdList.get(8), teamIdList.get(0), 15)) )
			return false;
		if( !DBUtils.addMatch( MatchInfo.createMatch(leagueId, season, teamIdList.get(5), teamIdList.get(1), 15)) )
			return false;
		if( !DBUtils.addMatch( MatchInfo.createMatch(leagueId, season, teamIdList.get(2), teamIdList.get(4), 15)) )
			return false;
		// 16 round
		if( !DBUtils.addMatch( MatchInfo.createMatch(leagueId, season, teamIdList.get(9), teamIdList.get(4), 16)) )
			return false;
		if( !DBUtils.addMatch( MatchInfo.createMatch(leagueId, season, teamIdList.get(2), teamIdList.get(5), 16)) )
			return false;
		if( !DBUtils.addMatch( MatchInfo.createMatch(leagueId, season, teamIdList.get(1), teamIdList.get(8), 16)) )
			return false;
		if( !DBUtils.addMatch( MatchInfo.createMatch(leagueId, season, teamIdList.get(0), teamIdList.get(3), 16)) )
			return false;
		if( !DBUtils.addMatch( MatchInfo.createMatch(leagueId, season, teamIdList.get(7), teamIdList.get(6), 16)) )
			return false;
		// 17 round
		if( !DBUtils.addMatch( MatchInfo.createMatch(leagueId, season, teamIdList.get(7), teamIdList.get(9), 17)) )
			return false;
		if( !DBUtils.addMatch( MatchInfo.createMatch(leagueId, season, teamIdList.get(6), teamIdList.get(0), 17)) )
			return false;
		if( !DBUtils.addMatch( MatchInfo.createMatch(leagueId, season, teamIdList.get(3), teamIdList.get(1), 17)) )
			return false;
		if( !DBUtils.addMatch( MatchInfo.createMatch(leagueId, season, teamIdList.get(8), teamIdList.get(2), 17)) )
			return false;
		if( !DBUtils.addMatch( MatchInfo.createMatch(leagueId, season, teamIdList.get(5), teamIdList.get(4), 17)) )
			return false;
		// 18 round
		if( !DBUtils.addMatch( MatchInfo.createMatch(leagueId, season, teamIdList.get(9), teamIdList.get(5), 18)) )
			return false;
		if( !DBUtils.addMatch( MatchInfo.createMatch(leagueId, season, teamIdList.get(4), teamIdList.get(8), 18)) )
			return false;
		if( !DBUtils.addMatch( MatchInfo.createMatch(leagueId, season, teamIdList.get(2), teamIdList.get(3), 18)) )
			return false;
		if( !DBUtils.addMatch( MatchInfo.createMatch(leagueId, season, teamIdList.get(1), teamIdList.get(6), 18)) )
			return false;
		if( !DBUtils.addMatch( MatchInfo.createMatch(leagueId, season, teamIdList.get(0), teamIdList.get(7), 18)) )
			return false;
		
		return true;
	}
	
	public static boolean startNewRound(int leagueId, int round)
	{
		// SEND MSG TO PALYERS
		ArrayList<MatchInfo> matchList = DBUtils.getMatchInRound(leagueId, round);
		
		for(int i=0; i<matchList.size(); i++)
		{
			Message tmpMessage1 = Message.createMatchReqMessage(matchList.get(i).getHomeTeamId(), matchList.get(i));
			if( !DBUtils.addMessage(tmpMessage1) )
				return false;
			
			Message tmpMessage2 = Message.createMatchReqMessage(matchList.get(i).getAwayTeamId(), matchList.get(i));
			if( !DBUtils.addMessage(tmpMessage2) )
				return false;
			
			DBUtils.updateMatchStatus(MatchInfo.STATUS_REQ_SENDED, matchList.get(i).getId());
		}
		
		return true;
	}
}

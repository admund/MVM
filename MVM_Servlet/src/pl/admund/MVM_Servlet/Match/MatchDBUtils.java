package pl.admund.MVM_Servlet.Match;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import pl.admund.MVM_Servlet.DBConnect.DBUtils;
import pl.admund.MVM_Servlet.DBConnect.SQLQueryCreator;
import pl.admund.MVM_Servlet.Utils.GlobalDate;

public class MatchDBUtils 
{
	public static ArrayList<Match> getMatchInRound(int leagueId, int round)
	{
		ArrayList<Match> matchList = new ArrayList<Match>();
		String query = "SELECT ID, GOSPODARZ_ID, GOSC_ID FROM WM_MECZ WHERE LIGA_ID=" + leagueId + " AND KOLEJKA=" + round;
		ResultSet result = DBUtils.executeQuery(query);
		try
		{
			while(result.next())
			{
				Match tmpMatch = new Match();
				int matchId = result.getInt(1);
				tmpMatch.setId(matchId);
				
				int homeTeamId = result.getInt(2);
				tmpMatch.setHomeTeamId(homeTeamId);
				
				int awayTeamId = result.getInt(3);
				tmpMatch.setAwayTeamId(awayTeamId);
				
				matchList.add(tmpMatch);
			}
			result.close();
			return matchList;
		}
		catch (SQLException e)
		{
			System.out.println( "C:"  + e.getClass() + "\nM:" + e.getMessage()  );
			return null;
		}
	}
	
	public static Match getMatchInfoFromId(int matchId)
	{
		String query = "SELECT LIGA_ID, SEZON, KOLEJKA, GOSPODARZ_ID, GOSC_ID FROM WM_MECZ WHERE ID=" + matchId;
		ResultSet result = DBUtils.executeQuery(query);
		try
		{
			while(result.next())
			{
				Match tmpMatch = new Match();
				tmpMatch.setId(matchId);
				
				int leagueId = result.getInt(1);
				tmpMatch.setLeagueId(leagueId);
				
				int season = result.getInt(2);
				tmpMatch.setSeason(season);
				
				int round = result.getInt(3);
				tmpMatch.setRound(round);
				
				int homeTeamId = result.getInt(4);
				tmpMatch.setHomeTeamId(homeTeamId);
				
				int awayTeamId = result.getInt(5);
				tmpMatch.setAwayTeamId(awayTeamId);
				
				result.close();
				return tmpMatch;
			}
			result.close();
			return null;
		}
		catch (SQLException e)
		{
			System.out.println( "C:"  + e.getClass() + "\nM:" + e.getMessage()  );
			return null;
		}
	}
	
	public static ArrayList<Match> getMatchThatStartInMin(int minToStart)
	{
		ArrayList<Match> matchList = new ArrayList<Match>();
		String date = GlobalDate.addToDateMin(minToStart);
		System.out.println("+min: " + date);
		String query = "SELECT ID, LIGA_ID, SEZON, GOSPODARZ_ID, GOSC_ID, TERMIN, KOLEJKA FROM WM_MECZ ";
		query += "WHERE TERMIN > CAST('" + GlobalDate.getCurrentDateAndTime() + "' AS TIMESTAMP) AND TERMIN < CAST('" + date + "' AS TIMESTAMP)";
		ResultSet result = DBUtils.executeQuery(query);
		try
		{
			while(result.next())
			{
				Match tmpMatch = new Match();
				
				int matchId = result.getInt(1);
				tmpMatch.setId(matchId);
				
				int leagueId = result.getInt(2);
				tmpMatch.setLeagueId(leagueId);
				
				int season = result.getInt(3);
				tmpMatch.setSeason(season);
				
				int homeTeamId = result.getInt(4);
				tmpMatch.setHomeTeamId(homeTeamId);
				
				int awayTeamId = result.getInt(5);
				tmpMatch.setAwayTeamId(awayTeamId);
				
				Timestamp term = result.getTimestamp(6);
				tmpMatch.setDate(term);
				
				int round = result.getInt(7);
				tmpMatch.setRound(round);
				
				matchList.add(tmpMatch);
			}
			result.close();
			return matchList;
		}
		catch (SQLException e)
		{
			System.out.println( "C:"  + e.getClass() + "\nM:" + e.getMessage()  );
			return null;
		}
	}
	
	public static ArrayList<Match> getMatchWhereSatus(int status)
	{
		ArrayList<Match> matchList = new ArrayList<Match>();
		String query = "SELECT ID, LIGA_ID, SEZON, GOSPODARZ_ID, GOSC_ID, TERMIN, KOLEJKA FROM WM_MECZ ";
		query += "WHERE STATUS=" + status;
		ResultSet result = DBUtils.executeQuery(query);
		try
		{
			while(result.next())
			{
				Match tmpMatch = new Match();
				
				int matchId = result.getInt(1);
				tmpMatch.setId(matchId);
				
				int leagueId = result.getInt(2);
				tmpMatch.setLeagueId(leagueId);
				
				int season = result.getInt(3);
				tmpMatch.setSeason(season);
				
				int homeTeamId = result.getInt(4);
				tmpMatch.setHomeTeamId(homeTeamId);
				
				int awayTeamId = result.getInt(5);
				tmpMatch.setAwayTeamId(awayTeamId);
				
				Timestamp term = result.getTimestamp(6);
				tmpMatch.setDate(term);
				
				int round = result.getInt(7);
				tmpMatch.setRound(round);
				
				matchList.add(tmpMatch);
			}
			result.close();
			return matchList;
		}
		catch (SQLException e)
		{
			System.out.println( "C:"  + e.getClass() + "\nM:" + e.getMessage()  );
			return null;
		}
	}
	
	public static boolean updateMatchStatus(int status, int id)
	{
		String updateMatchStatus = "UPDATE WM_MECZ SET STATUS=" + status + " WHERE ID=" + id;
		if(DBUtils.executeUpdate(updateMatchStatus) == -1)
			return false;
		
		return true;
	}
	
	public static boolean updateMatchTerm(int matchId, String term)
	{
		String updateMatchStatus = "UPDATE WM_MECZ SET TERMIN='" + term + "' WHERE ID=" + matchId;
		if(DBUtils.executeUpdate(updateMatchStatus) == -1)
			return false;
		
		return true;
	}
	
	public static void getTerm(int id)
	{
		String query = "SELECT GOSPODARZ_ID, GOSC_ID, TERMIN FROM WM_MECZ WHERE ID=" + id;
		ResultSet result = DBUtils.executeQuery(query);
		try
		{
			while(result.next())
			{
				int id1 = result.getInt(1);
				System.out.println("id1: " + id1);
				
				int id2 = result.getInt(2);
				System.out.println("id2: " + id2);
				
				//String term = result.getString(3);
				Timestamp term = result.getTimestamp(3);
				System.out.println("string: " + term);
				//System.out.println("TimeStamp:" + Timestamp.valueOf(term));
			}
			result.close();
		}
		catch (SQLException e)
		{
			System.out.println( "C:"  + e.getClass() + "\nM:" + e.getMessage()  );
			return;
		}
	}
	
	public static ArrayList<Match> getPlayingMatch(int playerId)
	{
		ArrayList<Match> matchList = new ArrayList<Match>();
		String query = "SELECT ID, LIGA_ID, SEZON, GOSPODARZ_ID, GOSC_ID, TERMIN, KOLEJKA FROM WM_MECZ ";
		query += "WHERE STATUS=" + Match.STATUS_MATCH_START + " AND (GOSPODARZ_ID=" + playerId + " OR GOSC_ID=" + playerId +")";
		ResultSet result = DBUtils.executeQuery(query);
		try
		{
			while(result.next())
			{
				Match tmpMatch = new Match();
				
				int matchId = result.getInt(1);
				tmpMatch.setId(matchId);
				
				int leagueId = result.getInt(2);
				tmpMatch.setLeagueId(leagueId);
				
				int season = result.getInt(3);
				tmpMatch.setSeason(season);
				
				int homeTeamId = result.getInt(4);
				tmpMatch.setHomeTeamId(homeTeamId);
				
				int awayTeamId = result.getInt(5);
				tmpMatch.setAwayTeamId(awayTeamId);
				
				Timestamp term = result.getTimestamp(6);
				tmpMatch.setDate(term);
				
				int round = result.getInt(7);
				tmpMatch.setRound(round);
				
				matchList.add(tmpMatch);
			}
			result.close();
			return matchList;
		}
		catch (SQLException e)
		{
			System.out.println( "C:"  + e.getClass() + "\nM:" + e.getMessage()  );
			return null;
		}
	}
	
	public static Match getNextMatch(int playerId)
	{
		String query = "Select ID, LIGA_ID, SEZON, GOSPODARZ_ID, GOSC_ID, TERMIN, KOLEJKA, STATUS FROM WM_MECZ WHERE TERMIN = (SELECT MIN(TERMIN) FROM WM_MECZ " +
				"WHERE GOSPODARZ_ID="+playerId+" OR GOSC_ID="+playerId+") AND STATUS !="+ Match.STATUS_MATCH_END;
		ResultSet result = DBUtils.executeQuery(query);
		try
		{
			while(result.next())
			{
				Match tmpMatch = new Match();
				
				int matchId = result.getInt(1);
				tmpMatch.setId(matchId);
				
				int leagueId = result.getInt(2);
				tmpMatch.setLeagueId(leagueId);
				
				int season = result.getInt(3);
				tmpMatch.setSeason(season);
				
				int homeTeamId = result.getInt(4);
				tmpMatch.setHomeTeamId(homeTeamId);
				
				int awayTeamId = result.getInt(5);
				tmpMatch.setAwayTeamId(awayTeamId);
				
				Timestamp term = result.getTimestamp(6);
				tmpMatch.setDate(term);
				
				int round = result.getInt(7);
				tmpMatch.setRound(round);
				
				int status = result.getInt(7);
				tmpMatch.setStatus(status);
				
				result.close();
				return tmpMatch;
			}
			result.close();
			return null;
		}
		catch (SQLException e)
		{
			System.out.println( "C:"  + e.getClass() + "\nM:" + e.getMessage()  );
			return null;
		}
	}
	
	public static boolean addMatch(Match _match)
	{
		String addMatch= SQLQueryCreator.addMatch(_match);
		
		if(DBUtils.executeUpdate(addMatch) == -1)
			return false;
		
		return true;
	}
	
	public static void changeMatch(int botTeamId, int newTeamId)
	{
		ArrayList<Integer> matchList = new ArrayList<Integer>();
		String query1 = "SELECT ID FROM WM_MECZ WHERE GOSPODARZ_ID=" + botTeamId;
		ResultSet result1 = DBUtils.executeQuery(query1);
		try
		{
			while(result1.next())
			{
				int matchId = result1.getInt(1);
				matchList.add(matchId);
				/*String query12 = "UPDATE WM_MECZ SET GOSPODARZ_ID="+ newTeamId +" WHERE ID=" + matchId;
				DBUtils.executeUpdate(query12);*/
			}
			result1.close();
		}
		catch (SQLException e)
		{
			System.out.println( "C:"  + e.getClass() + "\nM:" + e.getMessage()  );
			return;
		}
		
		for(int i=0; i<matchList.size(); i++)
		{
			int matchId = matchList.get(i);
			String query12 = "UPDATE WM_MECZ SET GOSPODARZ_ID="+ newTeamId +" WHERE ID=" + matchId;
			DBUtils.executeUpdate(query12);
		}
		matchList.clear();
		
		String query2 = "SELECT ID FROM WM_MECZ WHERE GOSC_ID=" + botTeamId;
		ResultSet result2 = DBUtils.executeQuery(query2);
		try
		{
			while(result2.next())
			{
				int matchId = result2.getInt(1);
				matchList.add(matchId);
				/*String query22 = "UPDATE WM_MECZ SET GOSC_ID="+ newTeamId +" WHERE ID=" + matchId;
				DBUtils.executeUpdate(query22);*/
			}
			result2.close();
		}
		catch (SQLException e)
		{
			System.out.println( "C:"  + e.getClass() + "\nM:" + e.getMessage()  );
			return;
		}
		
		for(int i=0; i<matchList.size(); i++)
		{
			int matchId = matchList.get(i);
			String query22 = "UPDATE WM_MECZ SET GOSC_ID="+ newTeamId +" WHERE ID=" + matchId;
			DBUtils.executeUpdate(query22);
		}
		matchList.clear();
	}
}

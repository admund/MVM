package pl.admund.MVM_Servlet.League;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import pl.admund.MVM_Servlet.DBConnect.DBUtils;
import pl.admund.MVM_Servlet.DBConnect.SQLQueryCreator;
import pl.admund.MVM_Servlet.Match.Match;
import pl.admund.MVM_Servlet.Match.MatchDBUtils;
import pl.admund.MVM_Servlet.Season.SeasonTask;
import pl.admund.MVM_Servlet.Team.Team;
import pl.admund.MVM_Servlet.Team.TeamDBUtils;
import pl.admund.MVM_Servlet.Volleyballer.VolleyballerDBUtils;

public class LeagueDBUtils 
{
	public static int getActualSezon(int leagueId)
	{
		String query = "SELECT SEZON FROM WM_LIGA WHERE ID=" + leagueId;
		ResultSet result = DBUtils.executeQuery(query);
		try
		{
			while(result.next())
			{
				int actualSezon = result.getInt(1);
				result.close();
				return actualSezon;
			}
			result.close();
			return 1;
		}
		catch (SQLException e)
		{
			System.out.println( "C:"  + e.getClass() + "\nM:" + e.getMessage()  );
			return -1;
		}
	}
	
	public static int getActualSezon(String leagueName)
	{
		String query = "SELECT MAX(SEZON) FROM WM_LIGA WHERE NAZWA='" + leagueName + "'";
		ResultSet result = DBUtils.executeQuery(query);
		try
		{
			while(result.next())
			{
				int actualSezon = result.getInt(1);
				result.close();
				return actualSezon;
			}
			result.close();
			return 1;
		}
		catch (SQLException e)
		{
			System.out.println( "C:"  + e.getClass() + "\nM:" + e.getMessage()  );
			return -1;
		}
	}
	
	public static int getNextSezon(String leagueName)
	{
		String query = "SELECT MAX(SEZON) FROM WM_LIGA WHERE NAZWA='" + leagueName + "'";
		ResultSet result = DBUtils.executeQuery(query);
		try
		{
			while(result.next())
			{
				int actualSezon = result.getInt(1);
				result.close();
				return actualSezon + 1;
			}
			result.close();
			return 1;
		}
		catch (SQLException e)
		{
			System.out.println( "C:"  + e.getClass() + "\nM:" + e.getMessage()  );
			return -1;
		}
	}
	
	public static int getNrOfPlayedRound(String leagueName)
	{
		int actualSezonHighestLeagueId = 0;
		String query = "SELECT ID FROM WM_LIGA WHERE NAZWA='" + leagueName + "' AND SEZON=(SELECT MAX(SEZON) FROM WM_LIGA WHERE NAZWA='" + leagueName + "')";
		ResultSet result = DBUtils.executeQuery(query);
		try
		{
			while(result.next())
			{
				actualSezonHighestLeagueId = result.getInt(1);
			}
			result.close();
		}
		catch (SQLException e)
		{
			System.out.println( "C:"  + e.getClass() + "\nM:" + e.getMessage()  );
			return -1;
		}
		
		for(int i=1; i<19; i++)
		{
			String query1 = "SELECT MALE_PKT FROM WM_MECZ WHERE LIGA_ID=" + actualSezonHighestLeagueId + " AND KOLEJKA=" + i;
			ResultSet result1 = DBUtils.executeQuery(query1);
			try
			{
				while(result1.next())
				{
					System.out.println("k:_" + result1.getString(1) +"_");
					if( result1.getString(1) == null || result1.getString(1).equals(""))
					{
						result1.close();
						return i-1;
					}
				}
				result1.close();
			}
			catch (SQLException e)
			{
				System.out.println( "C:"  + e.getClass() + "\nM:" + e.getMessage()  );
				return -1;
			}
		}
		return -1;
	}
	
	public static void deleteBotFromLeague(int _leagueId, int newTeamId)
	{
		String query = "SELECT ID FROM WM_DRUZYNA WHERE LIGA_ID=" + _leagueId + " AND BOT=1";
		ResultSet result = DBUtils.executeQuery(query);
		try
		{
			while(result.next())
			{
				int botTeamId = result.getInt(1);
				
				VolleyballerDBUtils.freeVolleyballers(botTeamId);
				
				MatchDBUtils.changeMatch(botTeamId, newTeamId);
				
				TeamDBUtils.deleteTeam(botTeamId);
			}
			result.close();
		}
		catch (SQLException e)
		{
			System.out.println( "C:"  + e.getClass() + "\nM:" + e.getMessage()  );
			return;
		}
	}
	
	public static int getFreeLeagueId()
	{
		int maxLeagueLevel = DBUtils.getParamMaxFromTable("POZIOM", "WM_LIGA");
		System.out.println("maxLeagueLevel " + maxLeagueLevel);
		for(int i=1; i<maxLeagueLevel+1; i++)
		{
			ArrayList<Integer> listOfLeague = getListOfLeaguOnLevel(i);
			System.out.println("listOfLeague.size() " + listOfLeague.size());
			for(int j=0; j<listOfLeague.size(); j++)
			{
				if( checkIfHaveFreeSlot(listOfLeague.get(j)) )
				{
					return listOfLeague.get(j);
				}
			}
		}
		
		return addNextLeagueLevel(maxLeagueLevel);
	}
	
	public /*private*/ static int addNextLeagueLevel(int _level)
	{
		int leagueCnt = (int)Math.pow(2, _level);
		
		int newLeagueId = DBUtils.getMaxTableId("WM_LIGA") + 1;
		
		for(int i=0; i<leagueCnt; i++)
		{
			League tmpLeague = new League();
			int leagueId = DBUtils.getMaxTableId("WM_LIGA");
			tmpLeague.setId(leagueId + 1);
			tmpLeague.setName("LIGA " + (_level + 1) + "." + (i + 1));
			tmpLeague.setLevel(_level + 1);
			int sezon = getNextSezon(tmpLeague.getName());
			tmpLeague.setSezon(sezon);
			
			String query = SQLQueryCreator.addLeague(tmpLeague);
			if(DBUtils.executeUpdate(query) == -1)
				return -1;
			
			for(int j=0; j<10; j++)
				TeamDBUtils.createNewTeam(Team.createTeamAndVolleyballers("BOT", true, leagueId), null);
			
			SeasonTask.addLeagueSeasonSchedule(leagueId, sezon);
			int playedRoundNr = getNrOfPlayedRound("SuperLiga");
			if(playedRoundNr > 0)
				fillMatchRandomResult(leagueId, playedRoundNr);
		}
		
		return newLeagueId;
	}
	
	public static void fillMatchRandomResult(int leagueId, int playedRoundNr)
	{
		for(int i=0; i<playedRoundNr; i++)
		{
			ArrayList<Integer> listOfMatchInRound = new ArrayList<Integer>();
			String query = "SELECT ID FROM WM_MECZ WHERE LIGA_ID=" + leagueId + " AND KOLEJKA=" + (i+1);
			ResultSet result1 = DBUtils.executeQuery(query);
			try
			{
				while(result1.next())
				{
					listOfMatchInRound.add( result1.getInt(1));
				}
				result1.close();
			}
			catch (SQLException e)
			{
				System.out.println( "C:"  + e.getClass() + "\nM:" + e.getMessage()  );
				return;
			}
			
			for(int j=0; j<listOfMatchInRound.size(); j++)
			{
				Match tmpMatch = new Match();
				tmpMatch.setId(listOfMatchInRound.get(j));
				Match.addFakeResult(tmpMatch);
				String update = SQLQueryCreator.updateMatch(tmpMatch);
				DBUtils.executeUpdate(update);
			}
		}
	}
	
	public static ArrayList<Integer> getListOfLeaguOnLevel(int _level)
	{
		String query = "SELECT ID FROM WM_LIGA WHERE POZIOM=" + _level;
		ResultSet result = DBUtils.executeQuery(query);
		try
		{
			ArrayList<Integer> listOfLeague = new ArrayList<Integer>();
			while(result.next())
			{
				listOfLeague.add(result.getInt(1));
			}
			result.close();
			return listOfLeague;
		}
		catch (SQLException e)
		{
			System.out.println( "C:"  + e.getClass() + "\nM:" + e.getMessage()  );
			return null;
		}
	}
	
	public static boolean checkIfHaveFreeSlot(int _leagueId)
	{
		String query = "SELECT COUNT(ID) FROM WM_DRUZYNA WHERE LIGA_ID=" + _leagueId + " AND BOT=0";
		ResultSet result = DBUtils.executeQuery(query);
		try
		{
			while(result.next())
			{
				int count = result.getInt(1);
				result.close();
				if(count < 10)
					return true;
				else
					return false;
			}
			result.close();
			return false;
		}
		catch (SQLException e)
		{
			System.out.println( "C:"  + e.getClass() + "\nM:" + e.getMessage()  );
			return false;
		}
	}
	
	public static int getLeagueIdFromId(int teamId)
	{
		String query = "SELECT LIGA_ID FROM WM_DRUZYNA WHERE ID=" + teamId;
		ResultSet result = DBUtils.executeQuery(query);
		try
		{
			while(result.next())
			{
				int leagueId = result.getInt(1);
				result.close();
				return leagueId;
			}
			result.close();
			return -1;
		}
		catch (SQLException e)
		{
			System.out.println( "C:"  + e.getClass() + "\nM:" + e.getMessage()  );
			return -1;
		}
	}
	
	public static String getLeagueNameFromId(int leagueId)
	{
		String query = "SELECT NAZWA FROM WM_LIGA WHERE ID=" + leagueId;
		ResultSet result = DBUtils.executeQuery(query);
		try
		{
			while(result.next())
			{
				String leagueName = result.getString(1);
				result.close();
				return leagueName;
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
	
	public static ArrayList<Integer> getTeamListFromLeagueId(int leagueId)
	{
		String query = "SELECT ID FROM WM_DRUZYNA WHERE LIGA_ID=" + leagueId;
		ResultSet result = DBUtils.executeQuery(query);
		try
		{
			ArrayList<Integer> teamList = new ArrayList<Integer>();
			while(result.next())
			{
				int teamId = result.getInt(1);
				
				teamList.add(teamId);
			}
			result.close();
			return teamList;
		}
		catch (SQLException e)
		{
			System.out.println( "C:"  + e.getClass() + "\nM:" + e.getMessage()  );
			return null;
		}
	}
	
	public static int getPointsCnt(int teamId, int ligaId)
	{
		int points = 0;
		String query1 = "SELECT COUNT(ID) FROM WM_MECZ WHERE GOSPODARZ_ID=" + teamId +
						" AND LIGA_ID=" + ligaId + " AND GOSPODARZ_SET=3 " +
						" AND (GOSC_SET=1 OR GOSC_SET=0)";
		ResultSet result1 = DBUtils.executeQuery(query1);
		try
		{
			while(result1.next())
			{
				int matchWinCnt = result1.getInt(1);
				
				points += 3*matchWinCnt;
			}
			result1.close();
		}
		catch (SQLException e)
		{
			System.out.println( "C:"  + e.getClass() + "\nM:" + e.getMessage()  );
			return -1;
		}
		
		String query2 = "SELECT COUNT(ID) FROM WM_MECZ WHERE GOSPODARZ_ID=" + teamId +
				" AND LIGA_ID=" + ligaId + " AND GOSPODARZ_SET=3 " +
				" AND GOSC_SET=2";
		ResultSet result2 = DBUtils.executeQuery(query2);
		try
		{
			while(result2.next())
			{
				int matchWinCnt = result2.getInt(1);
				
				points += 2*matchWinCnt;
			}
			result2.close();
		}
		catch (SQLException e)
		{
			System.out.println( "C:"  + e.getClass() + "\nM:" + e.getMessage()  );
			return -1;
		}
		
		String query3 = "SELECT COUNT(ID) FROM WM_MECZ WHERE GOSPODARZ_ID=" + teamId +
				" AND LIGA_ID=" + ligaId + " AND GOSPODARZ_SET=2 " +
				" AND GOSC_SET=3";
		ResultSet result3 = DBUtils.executeQuery(query3);
		try
		{
			while(result3.next())
			{
				int matchWinCnt = result3.getInt(1);
				
				points += matchWinCnt;
			}
			result3.close();
		}
		catch (SQLException e)
		{
			System.out.println( "C:"  + e.getClass() + "\nM:" + e.getMessage()  );
			return -1;
		}
		
		String query4 = "SELECT COUNT(ID) FROM WM_MECZ WHERE GOSC_ID=" + teamId +
						" AND LIGA_ID=" + ligaId + " AND GOSC_SET=3 " +
						" AND (GOSPODARZ_SET=1 OR GOSPODARZ_SET=0)";
		ResultSet result4 = DBUtils.executeQuery(query4);
		try
		{
			while(result4.next())
			{
				int matchWinCnt = result4.getInt(1);
				
				points += 3*matchWinCnt;
			}
			result4.close();
		}
		catch (SQLException e)
		{
			System.out.println( "C:"  + e.getClass() + "\nM:" + e.getMessage()  );
			return -1;
		}
		
		String query5 = "SELECT COUNT(ID) FROM WM_MECZ WHERE GOSC_ID=" + teamId +
				" AND LIGA_ID=" + ligaId + " AND GOSC_SET=3 " +
				" AND GOSPODARZ_SET=2";
		ResultSet result5 = DBUtils.executeQuery(query5);
		try
		{
			while(result5.next())
			{
				int matchWinCnt = result5.getInt(1);
				
				points += 2*matchWinCnt;
			}
			result5.close();
		}
		catch (SQLException e)
		{
			System.out.println( "C:"  + e.getClass() + "\nM:" + e.getMessage()  );
			return -1;
		}
		
		String query6 = "SELECT COUNT(ID) FROM WM_MECZ WHERE GOSC_ID=" + teamId +
				" AND LIGA_ID=" + ligaId + " AND GOSC_SET=2 " +
				" AND GOSPODARZ_SET=3";
		ResultSet result6 = DBUtils.executeQuery(query6);
		try
		{
			while(result6.next())
			{
				int matchWinCnt = result6.getInt(1);
				
				points += matchWinCnt;
			}
			result6.close();
		}
		catch (SQLException e)
		{
			System.out.println( "C:"  + e.getClass() + "\nM:" + e.getMessage()  );
			return -1;
		}
		
		return points;
	}
	
	public static int getMatchCnt(int teamId, int ligaId)
	{
		String query = "SELECT COUNT(ID) FROM WM_MECZ WHERE (GOSPODARZ_ID=" + teamId + " OR GOSC_ID=" + teamId +
						") AND LIGA_ID=" + ligaId;
		ResultSet result = DBUtils.executeQuery(query);
		try
		{
			while(result.next())
			{
				int matchCnt = result.getInt(1);
				result.close();
				return matchCnt;
			}
			result.close();
			return -1;
		}
		catch (SQLException e)
		{
			System.out.println( "C:"  + e.getClass() + "\nM:" + e.getMessage()  );
			return -1;
		}
	}
	
	public static ArrayList<Integer> getMatchsId(int teamId, int leagueId)
	{
		String query = "SELECT ID FROM WM_MECZ WHERE (GOSPODARZ_ID=" + teamId + " OR GOSC_ID=" + teamId +
						") AND LIGA_ID=" + leagueId;
		ResultSet result = DBUtils.executeQuery(query);
		try
		{
			ArrayList<Integer> matchList = new ArrayList<Integer>();
			while(result.next())
			{
				int matchId = result.getInt(1);
				
				matchList.add( matchId );
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
	
	public static int getMatchWinCnt(int teamId, int ligaId)
	{
		int matchWinCnt = 0;
		String query1 = "SELECT COUNT(ID) FROM WM_MECZ WHERE GOSPODARZ_ID=" + teamId +
						" AND LIGA_ID=" + ligaId + " AND GOSPODARZ_SET=3";
		ResultSet result1 = DBUtils.executeQuery(query1);
		try
		{
			while(result1.next())
			{
				int matchCnt = result1.getInt(1);
				
				matchWinCnt += matchCnt;
			}
			result1.close();
		}
		catch (SQLException e)
		{
			System.out.println( "C:"  + e.getClass() + "\nM:" + e.getMessage()  );
			return -1;
		}
		
		String query2 = "SELECT COUNT(ID) FROM WM_MECZ WHERE GOSC_ID=" + teamId +
				" AND LIGA_ID=" + ligaId + " AND GOSC_SET=3";
		ResultSet result2 = DBUtils.executeQuery(query2);
		try
		{
			while(result2.next())
			{
				int matchCnt = result2.getInt(1);
				
				matchWinCnt += matchCnt;
			}
			result2.close();
		}
		catch (SQLException e)
		{
			System.out.println( "C:"  + e.getClass() + "\nM:" + e.getMessage()  );
			return -1;
		}
		
		return matchWinCnt;
	}
	
	public static int getMatchLooseCnt(int teamId, int ligaId)
	{
		int matchLooseCnt = 0;
		String query1 = "SELECT COUNT(ID) FROM WM_MECZ WHERE GOSPODARZ_ID=" + teamId +
						" AND LIGA_ID=" + ligaId +
						" AND (GOSPODARZ_SET=2 OR GOSPODARZ_SET=1 OR GOSPODARZ_SET=0)";
		ResultSet result1 = DBUtils.executeQuery(query1);
		try
		{
			while(result1.next())
			{
				int matchCnt = result1.getInt(1);
				
				matchLooseCnt += matchCnt;
			}
			result1.close();
		}
		catch (SQLException e)
		{
			System.out.println( "C:"  + e.getClass() + "\nM:" + e.getMessage()  );
			return -1;
		}
		
		String query2 = "SELECT COUNT(ID) FROM WM_MECZ WHERE GOSC_ID=" + teamId +
				" AND LIGA_ID=" + ligaId +
				" AND (GOSC_ID=2 OR GOSC_ID=1 OR GOSC_ID=0)";
		ResultSet result2 = DBUtils.executeQuery(query2);
		try
		{
			while(result2.next())
			{
				int matchCnt = result2.getInt(1);
				
				matchLooseCnt += matchCnt;
			}
			result2.close();
		}
		catch (SQLException e)
		{
			System.out.println( "C:"  + e.getClass() + "\nM:" + e.getMessage()  );
			return -1;
		}
		
		return matchLooseCnt;
	}
	
	public static int getSetWinCnt(int teamId, int ligaId)
	{
		int setWinCnt = 0;
		String query1 = "SELECT GOSPODARZ_SET FROM WM_MECZ WHERE GOSPODARZ_ID=" + teamId +
						" AND LIGA_ID=" + ligaId;
		ResultSet result1 = DBUtils.executeQuery(query1);
		try
		{
			while(result1.next())
			{
				int setCnt = result1.getInt(1);
				
				setWinCnt += setCnt;
			}
			result1.close();
		}
		catch (SQLException e)
		{
			System.out.println( "C:"  + e.getClass() + "\nM:" + e.getMessage()  );
			return -1;
		}
		
		String query2 = "SELECT GOSC_SET FROM WM_MECZ WHERE GOSC_ID=" + teamId +
						" AND LIGA_ID=" + ligaId;
		ResultSet result2 = DBUtils.executeQuery(query2);
		try
		{
			while(result2.next())
			{
				int matchCnt = result2.getInt(1);
				
				setWinCnt += matchCnt;
			}
			result2.close();
		}
		catch (SQLException e)
		{
			System.out.println( "C:"  + e.getClass() + "\nM:" + e.getMessage()  );
			return -1;
		}
		
		return setWinCnt;
	}
	
	public static int getSetLooseCnt(int teamId, int ligaId)
	{
		int setLooseCnt = 0;
		String query1 = "SELECT GOSC_SET FROM WM_MECZ WHERE GOSPODARZ_ID=" + teamId +
						" AND LIGA_ID=" + ligaId;
		ResultSet result1 = DBUtils.executeQuery(query1);
		try
		{
			while(result1.next())
			{
				int setCnt = result1.getInt(1);
				
				setLooseCnt += setCnt;
			}
			result1.close();
		}
		catch (SQLException e)
		{
			System.out.println( "C:"  + e.getClass() + "\nM:" + e.getMessage()  );
			return -1;
		}
		
		String query2 = "SELECT GOSPODARZ_SET FROM WM_MECZ WHERE GOSC_ID=" + teamId +
				" AND LIGA_ID=" + ligaId;
		ResultSet result2 = DBUtils.executeQuery(query2);
		try
		{
			while(result2.next())
			{
				int setCnt = result2.getInt(1);
				
				setLooseCnt += setCnt;
			}
			result2.close();
		}
		catch (SQLException e)
		{
			System.out.println( "C:"  + e.getClass() + "\nM:" + e.getMessage()  );
			return -1;
		}
		
		return setLooseCnt;
	}
	
	public static int getWinLittlePoints(int teamId, int leagueId)
	{
		ArrayList<Integer> matchList = getMatchsId(teamId, leagueId);
		if(matchList == null)
			return -1;
		
		int winLittlePoints = 0;
		for(int i=0; i<matchList.size(); i++)
		{
			String query = "SELECT COUNT(ID) FROM WM_PUNKT WHERE MECZ_ID=" + matchList.get(i) + " AND PKT_W_DRUZYNA_ID=" + teamId;
			ResultSet result = DBUtils.executeQuery(query);
			try
			{
				while(result.next())
				{
					int pointsCnt = result.getInt(1);
					
					winLittlePoints += pointsCnt;
					result.close();
					continue;
				}
				result.close();
			}
			catch (SQLException e)
			{
				System.out.println( "C:"  + e.getClass() + "\nM:" + e.getMessage()  );
				return -1;
			}
		}
		
		return winLittlePoints;
	}
	
	public static int getLooseLittlePoints(int teamId, int leagueId)
	{
		ArrayList<Integer> matchList = getMatchsId(teamId, leagueId);
		if(matchList == null)
			return -1;
		
		int winLittlePoints = 0;
		for(int i=0; i<matchList.size(); i++)
		{
			String query = "SELECT COUNT(ID) FROM WM_PUNKT WHERE MECZ_ID=" + matchList.get(i) + " AND PKT_P_DRUZYNA_ID=" + teamId;
			ResultSet result = DBUtils.executeQuery(query);
			try
			{
				while(result.next())
				{
					int pointsCnt = result.getInt(1);
					
					winLittlePoints += pointsCnt;
					result.close();
					continue;
				}
				result.close();
			}
			catch (SQLException e)
			{
				System.out.println( "C:"  + e.getClass() + "\nM:" + e.getMessage()  );
				return -1;
			}
		}
		
		return winLittlePoints;
	}
}

package pl.admund.MVM_Server.DBConnect;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import pl.admund.MVM_Server.Main.Player;
import pl.admund.MVM_Server.MatchEngine.MatchInfo;
import pl.admund.MVM_Server.Messages.Message;
import pl.admund.MVM_Server.Season.SeasonTask;
import pl.admund.MVM_Server.SportStuff.League;
import pl.admund.MVM_Server.SportStuff.Stadium;
import pl.admund.MVM_Server.Team.Team;
import pl.admund.MVM_Server.Utils.GlobalDate;

public class DBUtils
{
	private static Statement statement = OracleDBConnector.getInstance().getStatement();
	/**
	 * Metoda wykonuje zapytania typu INSERT, UPDATE i DELETE
	 * 
	 * @param _query
	 * @return -1 = error
	 */
	public static int executeUpdate(String _query)
	{
		System.out.println( "EXECUTE_U: "  + _query );
		//Statement statement = OracleDBConnector.getInstance().getStatement();
		try
		{	int result = statement.executeUpdate(_query);
			//statement.close();
			return result;
		}
		catch (SQLException e)
		{
			System.out.println( "C:"  + e.getClass() + "\nM:" + e.getMessage()  );
			return -1;
		}
	}
	
	/**
	 * Metoda wykonujacy zapytanie w bazie, zwraca ResultSet
	 * 
	 * @param _query
	 * @return null = error
	 */
	public static ResultSet executeQuery(String _query)
	{
		System.out.println( "EXECUTE_Q: "  + _query );
		//Statement statement = OracleDBConnector.getInstance().getStatement();
		try
		{
			ResultSet result = statement.executeQuery(_query);
			return result;
		}
		catch (SQLException e)
		{
			System.out.println( "C:"  + e.getClass() + "\nM:" + e.getMessage()  );
			return null;
		}
	}
		
	/**
	 * Metoda ktora zwraca ilosc rekordow w danej tabeli, rekordy liczone po ID
	 * czyli moze byc uzywana tylko do tabeli gdzie jest kolumna ID
	 * 
	 * @param _tableName
	 * @return -1 = error
	 */
	public static int getTableCount(String _tableName)
	{
		String query = "SELECT COUNT(ID) FROM " + _tableName;
		ResultSet result = executeQuery(query);
		try 
		{
			while(result.next())
			{
				int tableCnt = result.getInt(1);
				
				result.close();
				return tableCnt;
			}
			result.close();
		} 
		catch (SQLException e)
		{
			System.out.println( "C:"  + e.getClass() + "\nM:" + e.getMessage()  );
			return -1;
		}
		return -1;
	}
	
	/**
	 * Metoda zwracajaca najwiekszy ID w danej tablicy
	 * 
	 * @param _tableName
	 * @return
	 */
	public static int getMaxTableId(String _tableName)
	{
		String query = "SELECT MAX(ID) FROM " + _tableName;
		ResultSet result = executeQuery(query);
		try 
		{
			while(result.next())
			{
				int tableCnt = result.getInt(1);
				result.close();
				return tableCnt;
			}
			result.close();
		}
		catch (SQLException e)
		{
			System.out.println( "C:"  + e.getClass() + "\nM:" + e.getMessage()  );
			return -1;
		}
		return -1;
	}
	
	public static int getParamMaxFromTable(String _paramName, String _tableName)
	{
		String query = "SELECT MAX(" + _paramName + ") FROM " + _tableName;
		ResultSet result = executeQuery(query);
		try 
		{
			while(result.next())
			{
				int tableCnt = result.getInt(1);
				result.close();
				return tableCnt;
			}
			result.close();
		}
		catch (SQLException e)
		{
			System.out.println( "C:"  + e.getClass() + "\nM:" + e.getMessage()  );
			return -1;
		}
		return -1;
	}
	
	/**
	 * Metoda usuwa z danej tabeli rekord o podanym ID
	 * 
	 * @param _tableName
	 * @param id
	 * @return
	 */
	private static int deleteFromTableById(String _tableName, int id)
	{
		String query = "DELETE FROM " + _tableName + " WHERE ID=" + id;
		//System.out.println(query);
		int result = executeUpdate(query);
		return result;
	}
	
	public static boolean createNewRandomPlayer()
	{
		Team tmpTeam = Team.createRandomTeamDB();
		//Team tmpTeam = Team.createTeamAndVolleyballers(teamName, tmpPlayer.getId());
		String addTeamQuery = SQLQueryCreator.addTeam(tmpTeam);
		if(executeUpdate(addTeamQuery) == -1)
			return false;
		
		Player tmpPlayer = Player.createRandomPlayer(tmpTeam.getId());
		String addPlayerQuery = SQLQueryCreator.addPlayer(tmpPlayer);
		if(executeUpdate(addPlayerQuery) == -1)
			return false;
		
		Stadium tmpStadium = Stadium.createRandomStadium(tmpTeam.getId());
		String addStadiumQuery = SQLQueryCreator.addStadium(tmpStadium);
		if(executeUpdate(addStadiumQuery) == -1)
			return false;
		
		return true;
	}
	
	public static boolean createNewPlayer(Player _player, Team _team, Stadium _stadium)
	{
		if(!createNewTeam(_team, _stadium))
			return false;
		
		String addPlayerQuery = SQLQueryCreator.addPlayer(_player);
		if(executeUpdate(addPlayerQuery) == -1)
			return false;

		return true;
	}
	
	public static boolean createNewTeam(Team _team, Stadium _stadium)
	{
		String addTeamQuery = SQLQueryCreator.addTeam(_team);
		//System.out.println( "TQ: " + addTeamQuery );
		if(executeUpdate(addTeamQuery) == -1)
			return false;

		for(int i=0; i<_team.getVolleyballerList().size(); i++)
		{
			String addVolleyballerQuery = SQLQueryCreator.addVolleyballer(_team.getVolleyballerList().get(i));
			//System.out.println( "SQ: " + addVolleyballer );
			if(executeUpdate(addVolleyballerQuery) == -1)
				return false;
			
			String addAttributesQuery = SQLQueryCreator.addAttributes(_team.getVolleyballerList().get(i).getAttributes(), _team.getVolleyballerList().get(i).getId());
			//System.out.println( "SQ: " + addAttributesQuery );
			if(executeUpdate(addAttributesQuery) == -1)
				return false;
		}
		
		if(_stadium != null)
		{
			String addStadiumQuery = SQLQueryCreator.addStadium(_stadium);
			//System.out.println( "SQ: " + addStadiumQuery );
			if(executeUpdate(addStadiumQuery) == -1)
				return false;
		}
		
		return true;
	}
	
	public static boolean deletePlayer(int _playerId)
	{
		int result = deleteFromTableById("WM_GRACZ", _playerId);
		if(result == 0)
			return true;
		return false;
	}
	
	public static boolean deleteTeam(int _teamId)
	{
		int result = deleteFromTableById("WM_DRUZYNA", _teamId);
		if(result == 0)
			return true;
		return false;
	}
	
	public static Player getPlayerFromLoginAndPass(String _login, String _pass)
	{
		String query = "SELECT * FROM WM_GRACZ WHERE NICK='" + _login + "' AND TOKEN='" + _pass + "'";
		ResultSet result = executeQuery(query);
		try
		{
			while(result.next())
			{
				Player tmpPlayer = new Player();
				int playerId = result.getInt(1);
				tmpPlayer.setId(playerId);
				
				tmpPlayer.setNick(_login);
				tmpPlayer.setToken(_pass);
				
				int teamId = result.getInt(4);
				tmpPlayer.setmTeamId(teamId);
				result.close();
				return tmpPlayer;
			}
			result.close();
		}
		catch (SQLException e)
		{
			System.out.println( "C:"  + e.getClass() + "\nM:" + e.getMessage()  );
			return null;
		}
		return null;
	}
	
	public static Team getTeamFromId(int _teamId)
	{
		String query = "SELECT * FROM WM_DRUZYNA WHERE ID=" + _teamId;
		ResultSet result = executeQuery(query);
		try
		{
			while(result.next())
			{
				Team tmpTeam = new Team();
				int teamId = result.getInt(1);
				tmpTeam.setId(teamId);
				
				String teamName = result.getString(2);
				tmpTeam.setTeamName(teamName);
				
				int leagueId = result.getInt(4);
				tmpTeam.setLeagueId(leagueId);
				
				result.close();
				return tmpTeam;
			}
			result.close();
		}
		catch (SQLException e)
		{
			System.out.println( "C:"  + e.getClass() + "\nM:" + e.getMessage()  );
			return null;
		}
		return null;
	}
	
	public static int getTeamIdFromPlayerId(int _playerId)
	{
		String query = "SELECT DRUZYNA_ID FROM WM_GRACZ WHERE ID=" + _playerId;
		ResultSet result = executeQuery(query);
		try
		{
			while(result.next())
			{
				int tmpId = result.getInt(1);
				result.close();
				return tmpId;
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
	
	public static String getNameFromUid(int _playerId)
	{
		String query = "SELECT NICK FROM WM_GRACZ WHERE ID=" + _playerId;
		ResultSet result = executeQuery(query);
		try
		{
			while(result.next())
			{
				String tmpNick = result.getString(1);
				System.out.println( "tmpNick:" + tmpNick);
				result.close();
				System.out.println( "tmpNick:" + tmpNick);
				return tmpNick;
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
	
	public static ArrayList<Integer> getTeamIdFromLeagueId(int leagueId)
	{
		String query = "SELECT ID FROM WM_DRUZYNA WHERE LIGA_ID=" + leagueId;
		ResultSet result = executeQuery(query);
		try
		{
			ArrayList<Integer> teamIdList = new ArrayList<Integer>();
			System.out.println( "result: "  + result);
			while(result.next())
			{
				int id = result.getInt(1);
				teamIdList.add(id);
			}
			result.close();
			return teamIdList;
		}
		catch (SQLException e)
		{
			System.out.println( "C:"  + e.getClass() + "\nM:" + e.getMessage()  );
			return null;
		}
	}

	public static Stadium getStadiumFromPlayerId(int _teamId)
	{
		String query = "SELECT NAZWA, KANAPKI_UPG, PAMIATKI_UPG, STADION_UPG FROM WM_STADION WHERE DRUZYNA_ID=" + _teamId;
		ResultSet result = executeQuery(query);
		try
		{
			while(result.next())
			{
				Stadium tmpStadium = new Stadium();
				String stadiumName = result.getString(1);
				tmpStadium.setmName(stadiumName);
				byte upg_lv = result.getByte(2);
				tmpStadium.setmFoodUpgLevel(upg_lv);
				upg_lv = result.getByte(3);
				tmpStadium.setmShopUpgLevel(upg_lv);
				upg_lv = result.getByte(4);
				tmpStadium.setmStadiumUpgLevel(upg_lv);
				
				result.close();
				return tmpStadium;
			}
			result.close();
		}
		catch (SQLException e)
		{
			System.out.println( "C:"  + e.getClass() + "\nM:" + e.getMessage()  );
			return null;
		}
		return null;
	}
	
	public static boolean checkIsLoginFree(String login)
	{
		String query = "SELECT ID FROM WM_GRACZ WHERE NICK='" + login + "'";
		ResultSet result = executeQuery(query);
		try
		{
			while(result.next())
			{
				result.close();
				return false;
			}
			result.close();
			return true;
		}
		catch (SQLException e)
		{
			System.out.println( "C:"  + e.getClass() + "\nM:" + e.getMessage()  );
			return false;
		}
	}
	
	public static boolean checkPass(int _playerId, String _pass)
	{
		String query = "SELECT * FROM WM_GRACZ WHERE ID=" + _playerId + " AND TOKEN='" + _pass + "'";
		ResultSet result = executeQuery(query);
		try
		{
			while(result.next())
			{
				result.close();
				return true;
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
	
	public static boolean checkSquadExist(int _teamId)
	{
		String query = "SELECT * FROM WM_SKLAD WHERE DRUZYNA_ID=" + _teamId;
		ResultSet result = executeQuery(query);
		try
		{
			while(result.next())
			{
				result.close();
				return true;
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
	
	public static boolean addSquad(int _teamId, String[] _squadList )
	{
		String addSquad = SQLQueryCreator.addSquad(_teamId, _squadList);
		
		if(executeUpdate(addSquad) == -1)
			return false;
		
		return true;
	}
	
	public static boolean addMatch(MatchInfo _match)
	{
		String addMatch= SQLQueryCreator.addMatch(_match);
		
		if(executeUpdate(addMatch) == -1)
			return false;
		
		return true;
	}
	
	public static boolean updateSquad(int _teamId, String[] _squadList )
	{
		String updateSquad = SQLQueryCreator.updateSquad(_teamId, _squadList);
		
		if(executeUpdate(updateSquad) == -1)
			return false;
		
		return true;
	}
	
	public static int[] getSquad(int _teamId)
	{
		String query = "SELECT * FROM WM_SKLAD WHERE DRUZYNA_ID=" + _teamId;
		ResultSet result = executeQuery(query);
		try
		{
			int[] squadTab = new int[12];
			while(result.next())
			{
				squadTab[0] = result.getInt(1);
				squadTab[1] = result.getInt(2);
				squadTab[2] = result.getInt(3);
				squadTab[3] = result.getInt(4);
				squadTab[4] = result.getInt(5);
				squadTab[5] = result.getInt(6);
				squadTab[6] = result.getInt(7);
				squadTab[7] = result.getInt(8);
				squadTab[8] = result.getInt(9);
				squadTab[9] = result.getInt(10);
				squadTab[10] = result.getInt(11);
				squadTab[11] = result.getInt(12);
				
				result.close();
				return squadTab;
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
	
	public static int getActualSezon(int leagueId)
	{
		String query = "SELECT SEZON FROM WM_LIGA WHERE ID=" + leagueId;
		ResultSet result = executeQuery(query);
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
		ResultSet result = executeQuery(query);
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
		ResultSet result = executeQuery(query);
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
		ResultSet result = executeQuery(query);
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
			ResultSet result1 = executeQuery(query1);
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
	
	public static int getFreeLeagueId()
	{
		int maxLeagueLevel = getParamMaxFromTable("POZIOM", "WM_LIGA");
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
		
		int newLeagueId = getMaxTableId("WM_LIGA") + 1;
		
		for(int i=0; i<leagueCnt; i++)
		{
			League tmpLeague = new League();
			int leagueId = getMaxTableId("WM_LIGA");
			tmpLeague.setId(leagueId + 1);
			tmpLeague.setName("LIGA " + (_level + 1) + "." + (i + 1));
			tmpLeague.setLevel(_level + 1);
			int sezon = DBUtils.getNextSezon(tmpLeague.getName());
			tmpLeague.setSezon(sezon);
			
			String query = SQLQueryCreator.addLeague(tmpLeague);
			if(executeUpdate(query) == -1)
				return -1;
			
			for(int j=0; j<10; j++)
				createNewTeam(Team.createTeamAndVolleyballers("BOT", true, leagueId), null);
			
			SeasonTask.addLeagueSeasonSchedule(leagueId, sezon);
			int playedRoundNr = DBUtils.getNrOfPlayedRound("SuperLiga");
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
			ResultSet result1 = executeQuery(query);
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
				MatchInfo tmpMatch = new MatchInfo();
				tmpMatch.setId(listOfMatchInRound.get(j));
				MatchInfo.addFakeResult(tmpMatch);
				String update = SQLQueryCreator.updateMatch(tmpMatch);
				DBUtils.executeUpdate(update);
			}
		}
	}
	
	public static ArrayList<Integer> getListOfLeaguOnLevel(int _level)
	{
		String query = "SELECT ID FROM WM_LIGA WHERE POZIOM=" + _level;
		ResultSet result = executeQuery(query);
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
		ResultSet result = executeQuery(query);
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
	
	public static void deleteBotFromLeague(int _leagueId, int newTeamId)
	{
		String query = "SELECT ID FROM WM_DRUZYNA WHERE LIGA_ID=" + _leagueId + " AND BOT=1";
		ResultSet result = executeQuery(query);
		try
		{
			while(result.next())
			{
				int botTeamId = result.getInt(1);
				
				freeVolleyballers(botTeamId);
				
				changeMatch(botTeamId, newTeamId);
				
				deleteTeam(botTeamId);
			}
			result.close();
		}
		catch (SQLException e)
		{
			System.out.println( "C:"  + e.getClass() + "\nM:" + e.getMessage()  );
			return;
		}
	}
	
	public static void freeVolleyballers(int botTeamId)
	{
		String query = "SELECT ID FROM WM_SIATKARZ WHERE DRUZYNA_ID=" + botTeamId;
		ResultSet result = executeQuery(query);
		try
		{
			while(result.next())
			{
				int volleyballerId = result.getInt(1);
				String query2 = "UPDATE WM_SIATKARZ SET KONTRAKT=0, DRUZYNA_ID=0 WHERE ID=" + volleyballerId;
				executeUpdate(query2);
			}
			result.close();
		}
		catch (SQLException e)
		{
			System.out.println( "C:"  + e.getClass() + "\nM:" + e.getMessage()  );
			return;
		}
	}
	
	public static void changeMatch(int botTeamId, int newTeamId)
	{
		String query1 = "SELECT ID FROM WM_MECZ WHERE GOSPODARZ_ID=" + botTeamId;
		ResultSet result1 = executeQuery(query1);
		try
		{
			while(result1.next())
			{
				int matchId = result1.getInt(1);
				String query12 = "UPDATE WM_MECZ SET GOSPODARZ_ID="+ newTeamId +" WHERE ID=" + matchId;
				executeUpdate(query12);
			}
			result1.close();
		}
		catch (SQLException e)
		{
			System.out.println( "C:"  + e.getClass() + "\nM:" + e.getMessage()  );
			return;
		}
		
		String query2 = "SELECT ID FROM WM_MECZ WHERE GOSC_ID=" + botTeamId;
		ResultSet result2 = executeQuery(query2);
		try
		{
			while(result2.next())
			{
				int matchId = result2.getInt(1);
				String query22 = "UPDATE WM_MECZ SET GOSC_ID="+ newTeamId +" WHERE ID=" + matchId;
				executeUpdate(query22);
			}
			result2.close();
		}
		catch (SQLException e)
		{
			System.out.println( "C:"  + e.getClass() + "\nM:" + e.getMessage()  );
			return;
		}
	}
	
	public static String getTeamNameFromId(int teamId)
	{
		String query = "SELECT NAZWA FROM WM_DRUZYNA WHERE ID=" + teamId;
		ResultSet result = executeQuery(query);
		try
		{
			while(result.next())
			{
				String teamName = result.getString(1);
				result.close();
				return teamName;
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
	
	public static int getLeagueIdFromId(int teamId)
	{
		String query = "SELECT LIGA_ID FROM WM_DRUZYNA WHERE ID=" + teamId;
		ResultSet result = executeQuery(query);
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
		ResultSet result = executeQuery(query);
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
		ResultSet result = executeQuery(query);
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
		ResultSet result1 = executeQuery(query1);
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
		ResultSet result2 = executeQuery(query2);
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
		ResultSet result3 = executeQuery(query3);
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
		ResultSet result4 = executeQuery(query4);
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
		ResultSet result5 = executeQuery(query5);
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
		ResultSet result6 = executeQuery(query6);
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
		ResultSet result = executeQuery(query);
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
		ResultSet result = executeQuery(query);
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
		ResultSet result1 = executeQuery(query1);
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
		ResultSet result2 = executeQuery(query2);
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
		ResultSet result1 = executeQuery(query1);
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
		ResultSet result2 = executeQuery(query2);
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
		ResultSet result1 = executeQuery(query1);
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
		ResultSet result2 = executeQuery(query2);
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
		ResultSet result1 = executeQuery(query1);
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
		ResultSet result2 = executeQuery(query2);
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
			ResultSet result = executeQuery(query);
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
			ResultSet result = executeQuery(query);
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
	
	public static boolean addMessage(Message _msg)
	{
		String addMessage = SQLQueryCreator.addMsg(_msg);
		
		if(executeUpdate(addMessage) == -1)
			return false;
		
		return true;
	}
	
	public static boolean delMessage(int _msgId)
	{
		String delMessage = "UPDATE WM_WIADOMOSCI SET STATUS=0 WHERE ID=" + _msgId;
		
		if(executeUpdate(delMessage) == -1)
			return false;
		
		return true;
	}
	
	public static ArrayList<Message> getMsgList(int playerId)
	{
		ArrayList<Message> msgList = new ArrayList<Message>();
		String query = "SELECT * FROM WM_WIADOMOSCI WHERE ODBIORCA_ID=" + playerId + " AND STATUS=1";
		ResultSet result = executeQuery(query);
		try
		{
			while(result.next())
			{
				System.out.println("MSG PYK");
				Message tmpMessage = new Message();
				int msgId = result.getInt(1);
				tmpMessage.setId(msgId);
				
				int receiverId = result.getInt(2);
				tmpMessage.setReceiverId(receiverId);
				
				int senderId = result.getInt(3);
				tmpMessage.setSenderId(senderId);
				
				int msgType = result.getInt(4);
				tmpMessage.setMsgType(msgType);
				
				String msgText = result.getString(5);
				tmpMessage.setMsgText(msgText);
				
				String date = result.getString(6);
				tmpMessage.setDate(date);
				
				int status = result.getInt(7);
				tmpMessage.setStatus(status);
				
				msgList.add(tmpMessage);
			}
			result.close();
			
			for(int i=0; i<msgList.size(); i++)
			{
				if(msgList.get(i).getSenderId() == 0)
					msgList.get(i).setSenderName("AVM_SYSTEM");
				else
				{
					String senderName = DBUtils.getNameFromUid(msgList.get(i).getSenderId());
					if(senderName == null)
						senderName = DBUtils.getTeamNameFromId(msgList.get(i).getSenderId());
					msgList.get(i).setSenderName(senderName);
				}
			}
			return msgList;
		}
		catch (SQLException e)
		{
			System.out.println( "C:"  + e.getClass() + "\nM:" + e.getMessage()  );
			return null;
		}
	}
	
	public static ArrayList<MatchInfo> getMatchInRound(int leagueId, int round)
	{
		ArrayList<MatchInfo> matchList = new ArrayList<MatchInfo>();
		String query = "SELECT ID, GOSPODARZ_ID, GOSC_ID FROM WM_MECZ WHERE LIGA_ID=" + leagueId + " AND KOLEJKA=" + round;
		ResultSet result = executeQuery(query);
		try
		{
			while(result.next())
			{
				MatchInfo tmpMatch = new MatchInfo();
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
	
	public static MatchInfo getMatchInfoFromId(int matchId)
	{
		String query = "SELECT LIGA_ID, SEZON, KOLEJKA, GOSPODARZ_ID, GOSC_ID FROM WM_MECZ WHERE ID=" + matchId;
		ResultSet result = executeQuery(query);
		try
		{
			while(result.next())
			{
				MatchInfo tmpMatch = new MatchInfo();
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
	
	public static ArrayList<MatchInfo> getMatchThatStartInMin(int minToStart)
	{
		ArrayList<MatchInfo> matchList = new ArrayList<MatchInfo>();
		String date = GlobalDate.addToDateMin(minToStart);
		System.out.println("+min: " + date);
		String query = "SELECT ID, LIGA_ID, SEZON, GOSPODARZ_ID, GOSC_ID, TERMIN, KOLEJKA FROM WM_MECZ ";
		query += "WHERE TERMIN > CAST('" + GlobalDate.getCurrentDateAndTime() + "' AS TIMESTAMP) AND TERMIN < CAST('" + date + "' AS TIMESTAMP)"
				+ " AND STATUS < " + MatchInfo.STATUS_MATCH_START;
		ResultSet result = executeQuery(query);
		try
		{
			while(result.next())
			{
				MatchInfo tmpMatch = new MatchInfo();
				
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
	
	public static ArrayList<MatchInfo> getMatchWhereSatus(int status)
	{
		ArrayList<MatchInfo> matchList = new ArrayList<MatchInfo>();
		String query = "SELECT ID, LIGA_ID, SEZON, GOSPODARZ_ID, GOSC_ID, TERMIN, KOLEJKA FROM WM_MECZ ";
		query += "WHERE STATUS=" + status;
		ResultSet result = executeQuery(query);
		try
		{
			while(result.next())
			{
				MatchInfo tmpMatch = new MatchInfo();
				
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
		if(executeUpdate(updateMatchStatus) == -1)
			return false;
		
		return true;
	}
	
	public static boolean updateMatchTerm(int matchId, String term)
	{
		String updateMatchStatus = "UPDATE WM_MECZ SET TERMIN='" + term + "' WHERE ID=" + matchId;
		if(executeUpdate(updateMatchStatus) == -1)
			return false;
		
		return true;
	}
	
	public static void getTerm(int id)
	{
		String query = "SELECT GOSPODARZ_ID, GOSC_ID, TERMIN FROM WM_MECZ WHERE ID=" + id;
		ResultSet result = executeQuery(query);
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
}

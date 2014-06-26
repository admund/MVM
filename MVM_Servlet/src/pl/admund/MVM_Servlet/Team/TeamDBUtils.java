package pl.admund.MVM_Servlet.Team;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import pl.admund.MVM_Servlet.DBConnect.DBUtils;
import pl.admund.MVM_Servlet.DBConnect.SQLQueryCreator;

public class TeamDBUtils
{
	public static boolean createNewTeam(Team _team, Stadium _stadium)
	{
		String addTeamQuery = SQLQueryCreator.addTeam(_team);
		//System.out.println( "TQ: " + addTeamQuery );
		if(DBUtils.executeUpdate(addTeamQuery) == -1)
			return false;

		for(int i=0; i<_team.getVolleyballerList().size(); i++)
		{
			String addVolleyballerQuery = SQLQueryCreator.addVolleyballer(_team.getVolleyballerList().get(i));
			//System.out.println( "SQ: " + addVolleyballer );
			if(DBUtils.executeUpdate(addVolleyballerQuery) == -1)
				return false;
			
			String addAttributesQuery = SQLQueryCreator.addAttributes(_team.getVolleyballerList().get(i).getAttributes(), _team.getVolleyballerList().get(i).getId());
			//System.out.println( "SQ: " + addAttributesQuery );
			if(DBUtils.executeUpdate(addAttributesQuery) == -1)
				return false;
		}
		
		if(_stadium != null)
		{
			String addStadiumQuery = SQLQueryCreator.addStadium(_stadium);
			//System.out.println( "SQ: " + addStadiumQuery );
			if(DBUtils.executeUpdate(addStadiumQuery) == -1)
				return false;
		}
		
		return true;
	}
	
	public static boolean deleteTeam(int _teamId)
	{
		int result = DBUtils.deleteFromTableById("WM_DRUZYNA", _teamId);
		if(result == 0)
			return true;
		return false;
	}
	
	public static Team getTeamFromId(int _teamId)
	{
		String query = "SELECT * FROM WM_DRUZYNA WHERE ID=" + _teamId;
		ResultSet result = DBUtils.executeQuery(query);
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
		ResultSet result = DBUtils.executeQuery(query);
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
	
	public static Stadium getStadiumFromPlayerId(int _teamId)
	{
		String query = "SELECT NAZWA, KANAPKI_UPG, PAMIATKI_UPG, STADION_UPG FROM WM_STADION WHERE DRUZYNA_ID=" + _teamId;
		ResultSet result = DBUtils.executeQuery(query);
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
	
	public static ArrayList<Integer> getTeamIdFromLeagueId(int leagueId)
	{
		String query = "SELECT ID FROM WM_DRUZYNA WHERE LIGA_ID=" + leagueId;
		ResultSet result = DBUtils.executeQuery(query);
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
	
	public static String getTeamNameFromId(int teamId)
	{
		String query = "SELECT NAZWA FROM WM_DRUZYNA WHERE ID=" + teamId;
		ResultSet result = DBUtils.executeQuery(query);
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
	
//-----------------------------------SQUAD---------------------------------------
	public static boolean checkSquadExist(int _teamId)
	{
		String query = "SELECT * FROM WM_SKLAD WHERE DRUZYNA_ID=" + _teamId;
		ResultSet result = DBUtils.executeQuery(query);
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
		
		if(DBUtils.executeUpdate(addSquad) == -1)
			return false;
		
		return true;
	}
	
	public static boolean updateSquad(int _teamId, String[] _squadList )
	{
		String updateSquad = SQLQueryCreator.updateSquad(_teamId, _squadList);
		
		if(DBUtils.executeUpdate(updateSquad) == -1)
			return false;
		
		return true;
	}
	
	public static String getSquad(int _teamId)
	{
		String query = "SELECT * FROM WM_SKLAD WHERE DRUZYNA_ID=" + _teamId;
		ResultSet result = DBUtils.executeQuery(query);
		try
		{
			//int[] squadTab = new int[12];
			while(result.next())
			{
				String tmpStr = "";
				/*squadTab[0] = result.getInt(1);
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
				squadTab[11] = result.getInt(12);*/
				
				tmpStr += result.getInt(1) + ",";
				tmpStr += result.getInt(2) + ",";
				tmpStr += result.getInt(3) + ",";
				tmpStr += result.getInt(4) + ",";
				tmpStr += result.getInt(5) + ",";
				tmpStr += result.getInt(6) + ",";
				tmpStr += result.getInt(7) + ",";
				tmpStr += result.getInt(8) + ",";
				tmpStr += result.getInt(9) + ",";
				tmpStr += result.getInt(10) + ",";
				tmpStr += result.getInt(11) + ",";
				tmpStr += result.getInt(12) + ",";
				
				result.close();
				return tmpStr;
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
}

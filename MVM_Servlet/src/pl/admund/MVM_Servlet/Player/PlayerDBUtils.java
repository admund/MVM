package pl.admund.MVM_Servlet.Player;

import java.sql.ResultSet;
import java.sql.SQLException;
import pl.admund.MVM_Servlet.DBConnect.DBUtils;
import pl.admund.MVM_Servlet.DBConnect.SQLQueryCreator;
import pl.admund.MVM_Servlet.Team.Stadium;
import pl.admund.MVM_Servlet.Team.Team;
import pl.admund.MVM_Servlet.Team.TeamDBUtils;

public class PlayerDBUtils
{
	public static boolean createNewRandomPlayer()
	{
		Team tmpTeam = Team.createRandomTeamDB();
		//Team tmpTeam = Team.createTeamAndVolleyballers(teamName, tmpPlayer.getId());
		String addTeamQuery = SQLQueryCreator.addTeam(tmpTeam);
		if(DBUtils.executeUpdate(addTeamQuery) == -1)
			return false;
		
		Player tmpPlayer = Player.createRandomPlayer(tmpTeam.getId());
		String addPlayerQuery = SQLQueryCreator.addPlayer(tmpPlayer);
		if(DBUtils.executeUpdate(addPlayerQuery) == -1)
			return false;
		
		Stadium tmpStadium = Stadium.createRandomStadium(tmpTeam.getId());
		String addStadiumQuery = SQLQueryCreator.addStadium(tmpStadium);
		if(DBUtils.executeUpdate(addStadiumQuery) == -1)
			return false;
		
		return true;
	}
	
	public static boolean createNewPlayer(Player _player, Team _team, Stadium _stadium)
	{
		if(!TeamDBUtils.createNewTeam(_team, _stadium))
			return false;
		
		String addPlayerQuery = SQLQueryCreator.addPlayer(_player);
		if(DBUtils.executeUpdate(addPlayerQuery) == -1)
			return false;

		return true;
	}
	
	public static boolean deletePlayer(int _playerId)
	{
		int result = DBUtils.deleteFromTableById("WM_GRACZ", _playerId);
		if(result == 0)
			return true;
		return false;
	}
	
	public static Player getPlayerFromMailAndPass(String _mail, String _pass)
	{
		String query = "SELECT * FROM WM_GRACZ WHERE MAIL='" + _mail + "' AND TOKEN='" + _pass + "'";
		ResultSet result = DBUtils.executeQuery(query);
		try
		{
			while(result.next())
			{
				Player tmpPlayer = new Player();
				int playerId = result.getInt(1);
				tmpPlayer.setId(playerId);
				
				String nick = result.getString(2);
				tmpPlayer.setNick(nick);
				
				String pass = result.getString(3);
				tmpPlayer.setToken(pass);
				
				int teamId = result.getInt(4);
				tmpPlayer.setmTeamId(teamId);
				
				int status = result.getInt(5);
				tmpPlayer.setStatus(status);
				
				String mail = result.getString(6);
				tmpPlayer.setMail(mail);
				
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
	
	public static String getNameFromUid(int _playerId)
	{
		String query = "SELECT NICK FROM WM_GRACZ WHERE ID=" + _playerId;
		ResultSet result = DBUtils.executeQuery(query);
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
	
	public static boolean checkIsMailFree(String mail)
	{
		String query = "SELECT ID FROM WM_GRACZ WHERE MAIL='" + mail + "'";
		ResultSet result = DBUtils.executeQuery(query);
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
	
	public static boolean updateStatus(int _playerId)
	{
		String query = "UPDATE WM_GRACZ set STATUS=1 WHERE ID=" + _playerId;
		int result = DBUtils.executeUpdate(query);
		if(result != -1)
			return true;
		
		return false;
	}
}

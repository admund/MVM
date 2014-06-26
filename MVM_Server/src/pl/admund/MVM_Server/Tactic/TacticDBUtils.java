package pl.admund.MVM_Server.Tactic;

import java.sql.ResultSet;
import java.sql.SQLException;

import pl.admund.MVM_Server.DBConnect.DBUtils;
import pl.admund.MVM_Server.DBConnect.SQLQueryCreator;

public class TacticDBUtils 
{
	public static Tactic getTactic(int teamId)
	{
		String query = "SELECT * FROM WM_TAKTYKA WHERE ID=" + teamId;
		ResultSet result = DBUtils.executeQuery(query);
		try
		{
			while(result.next())
			{
			// TEAM
				int teamAttackStr = result.getInt(2);
				int teamAttackDir = result.getInt(3);
				
				int teamSerwisStr = result.getInt(4);
				int teamSerwisDir = result.getInt(5);
				
				int teamReceptionQuality = result.getInt(6);
				int teamReceptionArea = result.getInt(7);
				
				int teamBlock3 = result.getInt(8);
				int teamBlockOption = result.getInt(9);
				
				int teamSetStyle = result.getInt(10);
				int teamSetPrefer = result.getInt(11);

			// ATTACKER
				int attAttackStr = result.getInt(12);
				int attAttackDir = result.getInt(13);
				
				int attSerwisStr = result.getInt(14);
				int attSerwisDir = result.getInt(15);
				
				int attBlock3 = result.getInt(16);
				int attBlockOption = result.getInt(17);
				
			// RECIVER 1
				int rec1AttackStr = result.getInt(18);
				int rec1AttackDir = result.getInt(19);
				
				int rec1SerwisStr = result.getInt(20);
				int rec1SerwisDir = result.getInt(21);
				
				int rec1ReceptionQuality = result.getInt(22);
				int rec1ReceptionArea = result.getInt(23);
				
				int rec1Block3 = result.getInt(24);
				int rec1BlockOption = result.getInt(25);
				
			// BLOCKER 1
				int blo1AttackStr = result.getInt(26);
				int blo1AttackDir = result.getInt(27);
				
				int blo1SerwisStr = result.getInt(28);
				int blo1SerwisDir = result.getInt(29);
				
				int blo1Block3 = result.getInt(30);
				int blo1BlockOption = result.getInt(31);
				
			// SETTER 
				int setSerwisStr = result.getInt(32);
				int setSerwisDir = result.getInt(33);
				
				int setBlock3 = result.getInt(34);
				int setBlockOption = result.getInt(35);
				
				int setSetStyle = result.getInt(36);
				int setSetPrefer = result.getInt(37);
				
			// RECIVER 2
				int rec2AttackStr = result.getInt(38);
				int rec2AttackDir = result.getInt(39);
				
				int rec2SerwisStr = result.getInt(40);
				int rec2SerwisDir = result.getInt(41);
				
				int rec2ReceptionQuality = result.getInt(42);
				int rec2ReceptionArea = result.getInt(43);
				
				int rec2Block3 = result.getInt(44);
				int rec2BlockOption = result.getInt(45);
				
			// BLOCKER 2
				int blo2AttackStr = result.getInt(46);
				int blo2AttackDir = result.getInt(47);
				
				int blo2SerwisStr = result.getInt(48);
				int blo2SerwisDir = result.getInt(49);
				
				int blo2Block3 = result.getInt(50);
				int blo2BlockOption = result.getInt(51);
				
			// LIBERO
				int libReceptionQuality = result.getInt(52);
				int libReceptionArea = result.getInt(53);
				
				boolean teamTacticImportent;
				int importent = result.getInt(54);
				if(importent == 1)
					teamTacticImportent = true;
				else
					teamTacticImportent = false;
				
				result.close();
				return new Tactic(teamTacticImportent, teamAttackStr, teamAttackDir, teamSerwisStr, teamSerwisDir, 
						teamReceptionQuality, teamReceptionArea, teamBlock3, teamBlockOption, 
						teamSetStyle, teamSetPrefer, 
						attAttackStr, attAttackDir, attSerwisStr, attSerwisDir, 
						attBlock3, attBlockOption, 
						rec1AttackStr, rec1AttackDir, rec1SerwisStr, rec1SerwisDir, 
						rec1ReceptionQuality, rec1ReceptionArea, rec1Block3, rec1BlockOption, 
						blo1AttackStr,  blo1AttackDir, blo1SerwisStr, blo1SerwisDir, 
						blo1Block3, blo1BlockOption, 
						setSerwisStr, setSerwisDir, setBlock3, setBlockOption, 
						setSetStyle, setSetPrefer, 
						rec2AttackStr, rec2AttackDir, rec2SerwisStr, rec2SerwisDir, 
						rec2ReceptionQuality, rec2ReceptionArea, rec2Block3, rec2BlockOption, 
						blo2AttackStr, blo2AttackDir, blo2SerwisStr, blo2SerwisDir, 
						blo2Block3, blo2BlockOption,
						libReceptionQuality, libReceptionArea);
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
	
	public static boolean setTactic(int teamId, Tactic _tactic)
	{
		String query = "SELECT ID FROM WM_TAKTYKA WHERE ID=" + teamId;
		ResultSet result = DBUtils.executeQuery(query);
		try
		{
			while(result.next())
			{
				result.close();
				return updateTactic(teamId, _tactic);
			}
			result.close();
			return addTactic(teamId, _tactic);
		}
		catch (SQLException e)
		{
			System.out.println( "C:"  + e.getClass() + "\nM:" + e.getMessage()  );
			return false;
		}
	}
	
	public static boolean updateTactic(int teamId, Tactic _tactic)
	{
		String updateTactic = SQLQueryCreator.updateTactic(teamId, _tactic);
		
		if(DBUtils.executeUpdate(updateTactic) == -1)
			return false;
		
		return true;
	}
	
	public static boolean addTactic(int teamId, Tactic _tactic)
	{
		String addTactic = SQLQueryCreator.addTactic(teamId, _tactic);
		
		if(DBUtils.executeUpdate(addTactic) == -1)
			return false;
		
		return true;
	}
}

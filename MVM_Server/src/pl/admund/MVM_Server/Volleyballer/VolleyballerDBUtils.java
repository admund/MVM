package pl.admund.MVM_Server.Volleyballer;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import pl.admund.MVM_Server.DBConnect.DBUtils;

public class VolleyballerDBUtils 
{
	public static ArrayList<Volleyballer> getVolleyballerListFromTeamId(int _teamId)
	{
		String query = "SELECT * FROM WM_SIATKARZ WHERE DRUZYNA_ID=" + _teamId;
		ResultSet result = DBUtils.executeQuery(query);
		try
		{
			ArrayList<Volleyballer> volleyballersList = new ArrayList<Volleyballer>();
			while(result.next())
			{
				Volleyballer tmpVolleyballer = new Volleyballer();

				int id = result.getInt(1);
				tmpVolleyballer.setId(id);

				String longName = result.getString(2);
				tmpVolleyballer.setLongName(longName);

				String shortName = result.getString(3);
				tmpVolleyballer.setShortName(shortName);
					
				//TODO FOTO
				
				int heigh = result.getInt(5);
				tmpVolleyballer.setHeigh(heigh);
				
				int weight = result.getInt(6);
				tmpVolleyballer.setWeight(weight);
				
				int age = result.getInt(7);
				tmpVolleyballer.setAge(age);
				
				String nationality = result.getString(8);
				tmpVolleyballer.setNationality(nationality);
				
				String possition = result.getString(9);
				tmpVolleyballer.setPossition(Volleyballer.stringToEnumPos(possition));
				
				int morale = result.getInt(10);
				tmpVolleyballer.setMorale(morale);
				
				int salary = result.getInt(11);
				tmpVolleyballer.setSalary(salary);
				
				int contractLength = result.getInt(12);
				tmpVolleyballer.setContractLength(contractLength);
				
				int loyalty = result.getInt(13);
				tmpVolleyballer.setLoyalty(loyalty);
				
				int suspToInjury = result.getInt(14);
				tmpVolleyballer.setSuspToInjury(suspToInjury);
				
				int teamId = result.getInt(15);
				tmpVolleyballer.setTeamId(teamId);
				
				int trim = result.getInt(16);
				tmpVolleyballer.setTrim(trim);
				
				System.out.println( "1: "  + teamId + " 2:" + longName + " 3:" + shortName);
				volleyballersList.add(tmpVolleyballer);
			}
			result.close();
			return volleyballersList;
		}
		catch (SQLException e)
		{
			System.out.println( "C:"  + e.getClass() + "\nM:" + e.getMessage()  );
			return null;
		}
	}
	
	public static Volleyballer getVolleyballerFromId(int _volleyballerId)
	{
		String query = "SELECT * FROM WM_SIATKARZ WHERE ID=" + _volleyballerId;
		ResultSet result = DBUtils.executeQuery(query);
		try
		{
			while(result.next())
			{
				Volleyballer tmpVolleyballer = new Volleyballer();

				int id = result.getInt(1);
				tmpVolleyballer.setId(id);

				String longName = result.getString(2);
				tmpVolleyballer.setLongName(longName);

				String shortName = result.getString(3);
				tmpVolleyballer.setShortName(shortName);
					
				//TODO FOTO
				
				int heigh = result.getInt(5);
				tmpVolleyballer.setHeigh(heigh);
				
				int weight = result.getInt(6);
				tmpVolleyballer.setWeight(weight);
				
				int age = result.getInt(7);
				tmpVolleyballer.setAge(age);
				
				String nationality = result.getString(8);
				tmpVolleyballer.setNationality(nationality);
				
				String possition = result.getString(9);
				tmpVolleyballer.setPossition(Volleyballer.stringToEnumPos(possition));
				
				int morale = result.getInt(10);
				tmpVolleyballer.setMorale(morale);
				
				int salary = result.getInt(11);
				tmpVolleyballer.setSalary(salary);
				
				int contractLength = result.getInt(12);
				tmpVolleyballer.setContractLength(contractLength);
				
				int loyalty = result.getInt(13);
				tmpVolleyballer.setLoyalty(loyalty);
				
				int suspToInjury = result.getInt(14);
				tmpVolleyballer.setSuspToInjury(suspToInjury);
				
				int teamId = result.getInt(15);
				tmpVolleyballer.setTeamId(teamId);
				
				int trim = result.getInt(16);
				tmpVolleyballer.setTrim(trim);

				return tmpVolleyballer;
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
	
	public static Attributes getAttributesFromVolleyballerId(int _volleyballerId)
	{
		String query = "SELECT * FROM WM_ATRYBUTY WHERE SIATKARZ_ID=" + _volleyballerId;
		ResultSet result = DBUtils.executeQuery(query);
		try
		{
			Attributes tmpAttributes = new Attributes();
			
			while(result.next())
			{
				int atack = result.getInt(1);
				tmpAttributes.setAtack(atack);

				int block = result.getInt(2);
				tmpAttributes.setBlock(block);
				
				int reception = result.getInt(3);
				tmpAttributes.setReception(reception);

				int rozegranie = result.getInt(4);
				tmpAttributes.setRozegranie(rozegranie);
				
				int serwis = result.getInt(5);
				tmpAttributes.setSerwis(serwis);
				
				int technique = result.getInt(6);
				tmpAttributes.setTechnique(technique);
				
				int talent = result.getInt(7);
				tmpAttributes.setTalent(talent);
				
				int temper = result.getInt(8);
				tmpAttributes.setTemper(temper);

				int ustawianie = result.getInt(9);
				tmpAttributes.setUstawianie(ustawianie);
				
				int intuition = result.getInt(10);
				tmpAttributes.setIntuition(intuition);

				int creativity = result.getInt(11);
				tmpAttributes.setCreativity(creativity);
				
				int walecznosc = result.getInt(12);
				tmpAttributes.setWalecznosc(walecznosc);
				
				int charisma = result.getInt(13);
				tmpAttributes.setCharisma(charisma);
				
				int psychoforce = result.getInt(14);
				tmpAttributes.setPsychoforce(psychoforce);
				
				int strenght = result.getInt(15);
				tmpAttributes.setStrenght(strenght);

				int jumping = result.getInt(16);
				tmpAttributes.setJumping(jumping);
				
				int reflex = result.getInt(17);
				tmpAttributes.setReflex(reflex);

				int quickness = result.getInt(18);
				tmpAttributes.setQuickness(quickness);
				
				int agility = result.getInt(19);
				tmpAttributes.setAgility(agility);
				
				int stamina = result.getInt(20);
				tmpAttributes.setStamina(stamina);
				
				int pracowitosc = result.getInt(21);
				tmpAttributes.setPracowitosc(pracowitosc);
			}
			result.close();
			return tmpAttributes;
		}
		catch (SQLException e)
		{
			System.out.println( "C:"  + e.getClass() + "\nM:" + e.getMessage()  );
			return null;
		}
	}
}

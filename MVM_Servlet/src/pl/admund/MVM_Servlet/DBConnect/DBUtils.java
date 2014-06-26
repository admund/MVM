package pl.admund.MVM_Servlet.DBConnect;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

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
	public static int deleteFromTableById(String _tableName, int id)
	{
		String query = "DELETE FROM " + _tableName + " WHERE ID=" + id;
		//System.out.println(query);
		int result = executeUpdate(query);
		return result;
	}
}

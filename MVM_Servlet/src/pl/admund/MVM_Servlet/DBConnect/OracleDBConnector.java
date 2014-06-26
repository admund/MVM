package pl.admund.MVM_Servlet.DBConnect;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import pl.admund.Private.PrivateConst;

public class OracleDBConnector 
{
//------------------SINGLETON----------------------
	private OracleDBConnector(){}
	private static OracleDBConnector mInstance;
	public static OracleDBConnector getInstance()
	{
		if(mInstance == null)
			mInstance = new OracleDBConnector();
		return mInstance;
	}
//--------------------------------------------------

	private Connection mConnection = null;

	public boolean connectToDB()
	{
		try 
		{
		    // Load the JDBC driver
		    String driverName = "oracle.jdbc.driver.OracleDriver";
		    Class.forName(driverName);
		    
		    // Create a connection to the database
		    String serverName = "localhost";
		    String portNumber = "1521";
		    String sid = "XE";
		    String url = "jdbc:oracle:thin:@" + serverName + ":" + portNumber + "/" + sid;
		    mConnection = DriverManager.getConnection(url, PrivateConst.getUsernameDB(), PrivateConst.getPasswordDB());
		} 
		catch (ClassNotFoundException e) 
		{
			System.out.println( "C:" + e.getClass() + "\nM:" + e.getMessage()  );
			return false;
		} 
		catch (SQLException e) 
		{
			System.out.println( "C:"  + e.getClass() + "\nM:" + e.getMessage()  );
			return false;
		}
		
		return true;
	}
	
	public Statement getStatement()
	{
		try 
		{
			if(mConnection != null)
				return mConnection.createStatement();
			else
			{
				System.out.println( "ERROR: mConnection is NULL" );
				return null;
			}
			
		} 
		catch (SQLException e) 
		{
			System.out.println( "C:"  + e.getClass() + "\nM:" + e.getMessage()  );
			return null;
		}
	}
}

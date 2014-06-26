package pl.admund.MVM_Server.Main;

import pl.admund.MVM_Server.DBConnect.OracleDBConnector;
import pl.admund.MVM_Server.Season.WatchDog;
import pl.admund.MVM_Server.TCPServer.TCPServer;

public class Simulation
{
	public static void main(String [] args)
	{
		System.out.println( " " + OracleDBConnector.getInstance().connectToDB() );
		if(TCPServer.getInstance().init())
		{
			new Thread()
			{
				public void run()
				{	
					TCPServer.getInstance().runServer();
				}
			}.start();
		}
		WatchDog.getInstance().init();
	}
}

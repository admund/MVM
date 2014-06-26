package pl.admund.MVM_Server.TCPServer;

import java.net.Socket;
import pl.admund.MVM_Server.Season.WatchDog;

public class TCPMessageParser implements Runnable
{
	private String msg;
	private Socket socket;
	
	public TCPMessageParser(Socket socket, String msg)
	{
		this.msg = msg;
		this.socket = socket;
	}
	
	@Override
	public void run()
	{
		System.out.println("run() " + msg);
		String splitedMsg[] = msg.split("_");
		int cmd = Integer.parseInt(splitedMsg[0]);
		switch(cmd)
		{
			case TCPMessageCmd.TCP_MSG_JOIN:
			{
				if(splitedMsg.length >= 2)
				{
					int teamId = Integer.parseInt(splitedMsg[1]);
					WatchDog.getInstance().connectSocket(socket, teamId);
				}
				else
					System.out.println("Not enough arguments in msg.");
			}
			break;
			case TCPMessageCmd.TCP_MSG_TIME:
			{
				if(splitedMsg.length >= 2)
				{
					int teamId = Integer.parseInt(splitedMsg[1]);
					WatchDog.getInstance().takeTime(socket, teamId);
				}
				else
					System.out.println("Not enough arguments in msg.");
			}
			break;
			case TCPMessageCmd.TCP_MSG_TACTIC:
			{
				if(splitedMsg.length >= 2)
				{
					int teamId = Integer.parseInt(splitedMsg[1]);
					//WatchDog.getInstance().getNewTactic(socket, teamId);
					WatchDog.getInstance().needTacticUpdate(socket, teamId);
				}
				else
					System.out.println("Not enough arguments in msg.");
			}
			break;
			case TCPMessageCmd.TCP_MSG_SQD_CHANGE:
			{
				if(splitedMsg.length >= 4)
				{
					int teamId = Integer.parseInt(splitedMsg[1]);
					int volleyballerId1 = Integer.parseInt(splitedMsg[2]);
					int volleyballerId2 = Integer.parseInt(splitedMsg[3]);
					WatchDog.getInstance().addChange(socket, teamId, volleyballerId1, volleyballerId2);
				}
				else
					System.out.println("Not enough arguments in msg.");
			}
			break;
		}
	}
	
}

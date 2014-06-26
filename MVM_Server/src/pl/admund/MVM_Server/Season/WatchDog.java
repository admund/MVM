package pl.admund.MVM_Server.Season;

import java.net.Socket;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;
import pl.admund.MVM_Server.DBConnect.DBUtils;
import pl.admund.MVM_Server.MatchEngine.MatchInfo;
import pl.admund.MVM_Server.Messages.Message;
import pl.admund.MVM_Server.TCPServer.MatchServer;
import pl.admund.MVM_Server.TCPServer.TCPMessageCmd;
import pl.admund.MVM_Server.TCPServer.TCPServer;
import pl.admund.MVM_Server.Utils.GlobalDate;

public class WatchDog
{
	private Timer mainTimer = new Timer();
	private ArrayList<MatchServer> matchServerList = new ArrayList<MatchServer>();
	
	private static WatchDog myInstance;
	public static WatchDog getInstance()
	{
		if(myInstance == null)
			myInstance = new WatchDog();
		return myInstance;
	};
	private WatchDog(){};
	
	public void init()
	{
		mainTimer.schedule(new TimerTask() 
		{
			@Override
			public void run() 
			{
				System.out.println("\nWatchDog run() time:" + GlobalDate.getCurrentDateAndTime()+ "\n");
				
				sendTermRequest();
				
				startMatch();
			}
		}, 0, 600000);// co 10min
	}
	
	private void startMatch()
	{
		ArrayList<MatchInfo> matchList = DBUtils.getMatchThatStartInMin(35);
		for(int i=0; i<matchList.size(); i++)
		{
			final MatchInfo tmpMatch = matchList.get(i);
			System.out.println("\ni: " + i);
			System.out.println("id: " + tmpMatch.getId() + "  " + tmpMatch.getDate());
			System.out.println("gos: " + tmpMatch.getHomeTeamId() + " gosc: " + tmpMatch.getAwayTeamId());
			
			//Message msgHome = Message.createMatchMessage(matchList.get(i).getHomeTeamId());
			//DBUtils.addMessage(msgHome); // TODO
			
			//Message msgAway = Message.createMatchMessage(matchList.get(i).getAwayTeamId());
			//DBUtils.addMessage(msgAway); // TODO
			
			new Thread()
			{
				public void run()
				{	
					MatchServer matchServer = new MatchServer();
					matchServerList.add(matchServer);
					matchServer.startMatchServer(tmpMatch, GlobalDate.timeToDateInSec(tmpMatch.getDate().getTime()));
				}
			}.start();
		}
	}
	
	private void sendTermRequest()
	{
		ArrayList<MatchInfo> matchList = DBUtils.getMatchWhereSatus(MatchInfo.STATUS_NEED_SEND_REQ);
		for(int i=0; i<matchList.size(); i++)
		{
			//System.out.println("\nid: " + matchList.get(i).getId() + "  " + matchList.get(i).getDate());
			//System.out.println("gos: " + matchList.get(i).getHomeTeamId() + " gosc: " + matchList.get(i).getAwayTeamId());
			
			Message msgHome = Message.createMatchReqMessage(matchList.get(i).getHomeTeamId(), matchList.get(i));
			DBUtils.addMessage(msgHome);
			
			Message msgAway = Message.createMatchReqMessage(matchList.get(i).getAwayTeamId(), matchList.get(i));
			DBUtils.addMessage(msgAway);
			
			DBUtils.updateMatchStatus(MatchInfo.STATUS_REQ_SENDED, matchList.get(i).getId());
		}
	}
	
	public void connectSocket(Socket socket, int teamId)
	{
		System.out.println("connectSocket: " + socket + " " + teamId );
		for(int i=0; i<matchServerList.size(); i++)
		{
			if(matchServerList.get(i).getHomeTeamId() == teamId)
			{
				matchServerList.get(i).setHomeTeamSocket(socket);
				sendJoin(socket, teamId, matchServerList.get(i).getAwayTeamId());
				return;
			}
			
			if(matchServerList.get(i).getAwayTeamId() == teamId)
			{
				matchServerList.get(i).setAwayTeamSocket(socket);
				sendJoin(socket, matchServerList.get(i).getHomeTeamId(), teamId);
				return;
			}
		}
	}
	
	public void takeTime(Socket socket, int teamId)
	{
		System.out.println("connectSocket: " + socket + " " + teamId );
		for(int i=0; i<matchServerList.size(); i++)
		{
			if(matchServerList.get(i).getHomeTeamId() == teamId)
			{
				matchServerList.get(i).takeTime(teamId);
				return;
			}
			
			if(matchServerList.get(i).getAwayTeamId() == teamId)
			{
				matchServerList.get(i).takeTime(teamId);
				return;
			}
		}
	}
	
	public void needTacticUpdate(Socket socket, int teamId)
	{
		System.out.println("connectSocket: " + socket + " " + teamId );
		for(int i=0; i<matchServerList.size(); i++)
		{
			if(matchServerList.get(i).getHomeTeamId() == teamId)
			{
				matchServerList.get(i).needTacticUpdate(teamId);
				return;
			}
			
			if(matchServerList.get(i).getAwayTeamId() == teamId)
			{
				matchServerList.get(i).needTacticUpdate(teamId);
				return;
			}
		}
	}
	
	public void addChange(Socket socket, int teamId, int volleyballerId1, int volleyballerId2)
	{
		System.out.println("connectSocket: " + socket + " " + teamId );
		for(int i=0; i<matchServerList.size(); i++)
		{
			if(matchServerList.get(i).getHomeTeamId() == teamId)
			{
				matchServerList.get(i).addChange(teamId, volleyballerId1, volleyballerId2);
				return;
			}
			
			if(matchServerList.get(i).getAwayTeamId() == teamId)
			{
				matchServerList.get(i).addChange(teamId, volleyballerId1, volleyballerId2);
				return;
			}
		}
	}
	
	public void sendJoin(Socket socket, int homeTeamId, int awayteamId)
	{
		String msg = TCPMessageCmd.TCP_MSG_JOIN + "_" + homeTeamId + ":" + awayteamId;
		TCPServer.getInstance().send(socket, msg);
	}
}

package pl.admund.MVM_Server.TCPServer;

import java.io.IOException;
import java.io.InputStream;
import java.io.InterruptedIOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;
import pl.admund.MVM_Server.DBConnect.DBUtils;
import pl.admund.MVM_Server.MatchEngine.MatchInfo;
import pl.admund.MVM_Server.MatchEngine.MatchEngine;
import pl.admund.MVM_Server.Tactic.Tactic;
import pl.admund.MVM_Server.Tactic.TacticDBUtils;
import pl.admund.MVM_Server.Team.TeamMatchSquad;
import pl.admund.MVM_Server.Team.Team;
import pl.admund.MVM_Server.Volleyballer.Attributes;
import pl.admund.MVM_Server.Volleyballer.Volleyballer;
import pl.admund.MVM_Server.Volleyballer.VolleyballerDBUtils;

public class MatchServer
{
	private boolean DEBUG = true;
	
	private MatchEngine matchEngine;
	private Socket homeTeamSocket;
	private Socket awayTeamSocket;
	
	public void startMatchServer(final MatchInfo match, final int couldown)
	{
		Team homeTeam = DBUtils.getTeamFromId(match.getHomeTeamId());
		getPlayingSquadAndTactic(homeTeam);
		
		Team awayTeam = DBUtils.getTeamFromId(match.getAwayTeamId());
		getPlayingSquadAndTactic(awayTeam);

		matchEngine = new MatchEngine(homeTeam, awayTeam, match, this);
		
		Timer waitForPlayerTimer = new Timer();
		waitForPlayerTimer.schedule(new TimerTask()
		{
			private int waitSec = 30;//couldown;//TODO SET COULDOWN
			@Override
			public void run()
			{
				if(DEBUG) System.out.println("Count down: " + waitSec + " " + couldown);
				waitSec -= 1;
				if(waitSec == 0)
				{
					DBUtils.updateMatchStatus(MatchInfo.STATUS_MATCH_START, match.getId());
					matchEngine.playTheGame();
					this.cancel();
				}
			}
		}, 0, 1000);
	}
	
	public void sendAction(String stringAction)
	{
		if(DEBUG) System.out.println("homeTeamSocket: " + homeTeamSocket);
		if(DEBUG) System.out.println("awayTeamSocket: " + awayTeamSocket);
		
		if(DEBUG) System.out.println("stringAction: " + stringAction);
		
		if(homeTeamSocket != null)
		{
			if(!TCPServer.getInstance().send(homeTeamSocket, stringAction))
				homeTeamSocket = null;
		}
		if(awayTeamSocket != null)
		{
			if(!TCPServer.getInstance().send(awayTeamSocket, stringAction))
				awayTeamSocket = null;
		}
	}
	
	public void sendResult(String stringResult)
	{
		stringResult = TCPMessageCmd.TCP_MSG_RESULT + "_" + stringResult;
		if(DEBUG) System.out.println("stringResult: " + stringResult);
		
		if(homeTeamSocket != null)
		{
			if(!TCPServer.getInstance().send(homeTeamSocket, stringResult))
				homeTeamSocket = null;
		}
		
		if(awayTeamSocket != null)
		{
			if(!TCPServer.getInstance().send(awayTeamSocket, stringResult))
				awayTeamSocket = null;
		}
	}
	
	public void sendSetEnd(String stringSetEnd)
	{
		stringSetEnd = TCPMessageCmd.TCP_MSG_SET_END + "_" + stringSetEnd;
		if(DEBUG) System.out.println("stringSetEnd: " + stringSetEnd);
		
		if(homeTeamSocket != null)
		{
			if(!TCPServer.getInstance().send(homeTeamSocket, stringSetEnd))
				homeTeamSocket = null;
		}
		
		if(awayTeamSocket != null)
		{
			if(!TCPServer.getInstance().send(awayTeamSocket, stringSetEnd))
				awayTeamSocket = null;
		}
	}
	
	public void sendTime(String timeStr)
	{
		timeStr = TCPMessageCmd.TCP_MSG_TIME + "_" + timeStr;
		if(DEBUG) System.out.println("timeStr: " + timeStr);
		
		if(homeTeamSocket != null)
		{
			if(!TCPServer.getInstance().send(homeTeamSocket, timeStr))
				homeTeamSocket = null;
		}
		
		if(awayTeamSocket != null)
		{
			if(!TCPServer.getInstance().send(awayTeamSocket, timeStr))
				awayTeamSocket = null;
		}
	}
	
	public void sendTactic(boolean succes, int teamId)
	{
		String tmpStr = "" + TCPMessageCmd.TCP_MSG_TACTIC;
		if(succes)
			tmpStr += "_1";
		else
			tmpStr += "_0";
		
		tmpStr += "_" + teamId;
		
		if(DEBUG) System.out.println("tmpStr: " + tmpStr);
		if(homeTeamSocket != null)
		{
			if(!TCPServer.getInstance().send(homeTeamSocket, tmpStr))
				homeTeamSocket = null;
		}
		
		if(awayTeamSocket != null)
		{
			if(!TCPServer.getInstance().send(awayTeamSocket, tmpStr))
				awayTeamSocket = null;
		}
	}
	
	public void sendChange(String str)
	{
		String tmpStr = TCPMessageCmd.TCP_MSG_SQD_CHANGE + "_" + str;
		if(DEBUG) System.out.println("tmpStr: " + tmpStr);
		if(homeTeamSocket != null)
		{
			if(!TCPServer.getInstance().send(homeTeamSocket, tmpStr))
				homeTeamSocket = null;
		}
		
		if(awayTeamSocket != null)
		{
			if(!TCPServer.getInstance().send(awayTeamSocket, tmpStr))
				awayTeamSocket = null;
		}
	}
	
	public void sendMatchEnd(int winnerId)
	{
		String tmpStr = TCPMessageCmd.TCP_MSG_MATCH_END + "_" + winnerId;
		if(DEBUG) System.out.println("tmpStr: " + tmpStr);
		if(homeTeamSocket != null)
		{
			if(!TCPServer.getInstance().send(homeTeamSocket, tmpStr))
				homeTeamSocket = null;
		}
		
		if(awayTeamSocket != null)
		{
			if(!TCPServer.getInstance().send(awayTeamSocket, tmpStr))
				awayTeamSocket = null;
		}
	}
	
	public void getPlayingSquadAndTactic(Team team)
	{
		int teamId = team.getId();
		if(DBUtils.checkSquadExist(teamId))
		{
			int squad[] = DBUtils.getSquad(teamId);
			ArrayList<Volleyballer> squadVolleyArray = new ArrayList<Volleyballer>();
			for(int i=0; i<squad.length; i++)
			{
				Volleyballer tmpVolleyballer = VolleyballerDBUtils.getVolleyballerFromId(squad[i]);
				Attributes attributes = VolleyballerDBUtils.getAttributesFromVolleyballerId(tmpVolleyballer.getId());
				tmpVolleyballer.setAttributes(attributes);
				
				squadVolleyArray.add(tmpVolleyballer);
			}
			
			team.setMatchSquad( new TeamMatchSquad(squadVolleyArray) );
		}
		else
		{
			ArrayList<Volleyballer> squadVolleyArray = new ArrayList<Volleyballer>();
			squadVolleyArray = VolleyballerDBUtils.getVolleyballerListFromTeamId(teamId);
			for(int i=0; i<12; i++)
			{	
				Volleyballer tmpVolleyballer = squadVolleyArray.get(i);
				Attributes attributes = VolleyballerDBUtils.getAttributesFromVolleyballerId(tmpVolleyballer.getId());
				tmpVolleyballer.setAttributes(attributes);
			}
			
			team.setMatchSquad( new TeamMatchSquad(squadVolleyArray) );
		}
		
		Tactic tmpTactic = TacticDBUtils.getTactic(teamId);
		if(tmpTactic != null)
			team.setTactic(tmpTactic);
		else
			team.setTactic(new Tactic());
	}
	
	public void setHomeTeamSocket(Socket socket)
	{
		this.homeTeamSocket = socket;
		new Thread()
		{
			public void run()
			{
				while(homeTeamSocket != null)
				{		
					ReceiveData(homeTeamSocket);
				}
			}
		}.start();
	}
	
	public void setAwayTeamSocket(Socket socket)
	{
		this.awayTeamSocket = socket;
		new Thread()
		{
			public void run()
			{
				while(awayTeamSocket != null)
				{		
					ReceiveData(awayTeamSocket);
				}
			}
		}.start();
	}
	
	public void takeTime(int teamId)
	{
		if(!matchEngine.takeTime(teamId))
			sendTime("-1");
	}
	
	public void needTacticUpdate(int teamId)
	{
		matchEngine.needTacticUpdate(teamId);
	}
	
	public void addChange(int teamId, int volleyballerId1, int volleyballerId2)
	{
		matchEngine.addChange(teamId, volleyballerId1, volleyballerId2);
	}
	
	public void ReceiveData(Socket socket)
	{
		try 
		{
			if(socket == null)
				return;
			else
			{
				InputStream in = socket.getInputStream();
				int result = in.read();
				byte[] buff = new byte[result];
				in.read(buff);
				new TCPMessageParser(socket, new String(buff)).run();
			}
		} 
		catch (InterruptedIOException  e) 
		{
			e.getStackTrace();
			CloseSocket(socket);
		}
		catch(IOException e) 
		{
			e.getStackTrace();
			CloseSocket(socket);
		}
	}
	
	public void CloseSocket(Socket socket)
	{
		System.out.println("TCP_Server close()");
		try 
		{
			socket.close();
		} 
		catch (IOException e) 
		{
			System.out.println( "C:" + e.getClass() + "\nM:" + e.getMessage());
			e.printStackTrace();
		}
	}
	
	public int getHomeTeamId()
	{
		return matchEngine.getHomeTeam().getId();
	}
	public int getAwayTeamId()
	{
		return matchEngine.getAwayTeam().getId();
	}
}

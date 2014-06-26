package pl.admund.MVM_Servlet.Season;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;
import pl.admund.MVM_Servlet.DBConnect.DBUtils;
import pl.admund.MVM_Servlet.Match.Match;
import pl.admund.MVM_Servlet.Match.MatchDBUtils;
import pl.admund.MVM_Servlet.Messages.Message;
import pl.admund.MVM_Servlet.Messages.MessagesDBUtils;
import pl.admund.MVM_Servlet.Utils.GlobalDate;

public class WatchDog
{
	private Timer globalTimer = new Timer();
	
	private static WatchDog myInstance;
	public static WatchDog getInstance()
	{
		if(myInstance == null)
			myInstance = new WatchDog();
		return myInstance;
	};
	private WatchDog()
	{
		init();
	};
	
	private void init()
	{
		globalTimer.schedule(new TimerTask() 
		{
			@Override
			public void run() 
			{
				System.out.println("\nWatchDog run() time:" + GlobalDate.getCurrentDateAndTime()+ "\n");
				
				sendTermRequest();
				
				startMatch();
			}
		}, 0, 600000);
	}
	
	private void startMatch()
	{
		ArrayList<Match> matchList = MatchDBUtils.getMatchThatStartInMin(35);
		for(int i=0; i<matchList.size(); i++)
		{
			//System.out.println("id: " + matchList.get(i).getId() + "  " + matchList.get(i).getDate());
			//System.out.println("gos: " + matchList.get(i).getHomeTeamId() + " gosc: " + matchList.get(i).getAwayTeamId());
			
			Message msgHome = Message.createMatchMessage(matchList.get(i).getHomeTeamId());
			MessagesDBUtils.addMessage(msgHome);
			
			Message msgAway = Message.createMatchMessage(matchList.get(i).getAwayTeamId());
			MessagesDBUtils.addMessage(msgAway);
			
			// TCP SERRVER START MATCH TODO!!!
		}
	}
	
	private void sendTermRequest()
	{
		ArrayList<Match> matchList = MatchDBUtils.getMatchWhereSatus(Match.STATUS_NEED_SEND_REQ);
		for(int i=0; i<matchList.size(); i++)
		{
			//System.out.println("\nid: " + matchList.get(i).getId() + "  " + matchList.get(i).getDate());
			//System.out.println("gos: " + matchList.get(i).getHomeTeamId() + " gosc: " + matchList.get(i).getAwayTeamId());
			
			Message msgHome = Message.createMatchReqMessage(matchList.get(i).getHomeTeamId(), matchList.get(i));
			MessagesDBUtils.addMessage(msgHome);
			
			Message msgAway = Message.createMatchReqMessage(matchList.get(i).getAwayTeamId(), matchList.get(i));
			MessagesDBUtils.addMessage(msgAway);
			
			MatchDBUtils.updateMatchStatus(Match.STATUS_REQ_SENDED, matchList.get(i).getId());
		}
	}
}

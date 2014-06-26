package pl.admund.MVM_Server.MatchEngine;

import java.util.ArrayList;
import pl.admund.MVM_Server.TCPServer.MatchServer;
import pl.admund.MVM_Server.Tactic.Change;
import pl.admund.MVM_Server.Tactic.Tactic;
import pl.admund.MVM_Server.Tactic.TacticDBUtils;
import pl.admund.MVM_Server.Team.Team;
import pl.admund.MVM_Server.Utils.GlobalRandom;
import pl.admund.MVM_Server.Volleyballer.Volleyballer;

public class MatchEngine
{
	private final int BREAK_TIME = 10;
	
	private MatchInfo mMatchInfo;
	private Team homeTeam;
	private Team awayTeam;
	
	private MatchServer matchServer;
	
	private ArrayList<MatchAction> actions = new ArrayList<MatchAction>();
	private ArrayList<Change> changeList = new ArrayList<Change>();
	
	private boolean servHome = true;
	private boolean actionHome = true;
	private boolean isTieBreak = false;
	private boolean isMatchEnd = false;
	
	private boolean is8PtsBreak = false;
	private boolean is16PtsBreak = false;
	
	private boolean isHomeNeedTacticUpdate = false;
	private boolean isAwayNeedTacticUpdate = false;
	
	private int whoTakeTime = 0;
	private int timeToWait = -1;
	private long lastWaitTime = 0;
	private long lastServisTime = 0;
	
	private int timeTakenHome = 0;
	private int timeTakenAway = 0;
	
	private boolean superMegaPowerShot = false;
	
	public MatchEngine(Team _homeTeam, Team _awayTeam, MatchInfo _matchInfo, MatchServer matchServer)
	{
		this.homeTeam = _homeTeam;
		this.awayTeam = _awayTeam;
		this.matchServer = matchServer;
		
		mMatchInfo = _matchInfo;
	}
	
//----------------AKCJE--------------------
	public void playTheGame()
	{
		//Scanner in = new Scanner(System.in);
		while(!isMatchEnd)
		{
			if(getTimeToWait() > -1)
			{
				if(System.currentTimeMillis() - lastWaitTime > 1000 )
				{
					if(isHomeNeedTacticUpdate)
					{
						if(getNewTactic(homeTeam))
						{
							matchServer.sendTactic(true, homeTeam.getId());
							isHomeNeedTacticUpdate = false;
						}
					}
					
					if(isAwayNeedTacticUpdate)
					{
						if(getNewTactic(awayTeam))
						{
							matchServer.sendTactic( true, awayTeam.getId() );
							isAwayNeedTacticUpdate = false;
						}
					} // TODO do klienta dopisac spradanie kto zmienil taktyke
					
					if(whoTakeTime != 0)
					{
						matchServer.sendTime("" + getTimeToWait() + "_" + whoTakeTime);
						whoTakeTime = 0;
					}
					else
						matchServer.sendTime("" + getTimeToWait());
					
					lastWaitTime = System.currentTimeMillis();
					timeToWait--;
				}
				continue;
			}
			
			if(changeList.size() > 0)
				doChanges();
			
			if(System.currentTimeMillis() - lastServisTime > 5000)
			{
				lastServisTime = System.currentTimeMillis();
				serwis();
			}
		}
		
		MatchDBUtils.updateMatchResult(mMatchInfo.getId(), mMatchInfo.getHomeSetWon(), mMatchInfo.getAwaySetWon(), mMatchInfo.getLittlePts());
		if(mMatchInfo.getHomeSetWon() == 3)
			matchServer.sendMatchEnd(homeTeam.getId());
		else
			matchServer.sendMatchEnd(awayTeam.getId());
	}
	
//----------------serwis()--------------------
	private void serwis()
	{
		MatchAction action = new MatchAction();
		Volleyballer serwisVolleyballer;
		if(servHome)
		{
			System.out.println("\n\nSerwis druzyna " + homeTeam.getTeamName());
			serwisVolleyballer = homeTeam.getServVolleyballer();
			System.out.println("\tSerwuje " + serwisVolleyballer.getShortName());
			actionHome = true;
		}
		else
		{
			System.out.println("\n\nSerwis druzyna " + awayTeam.getTeamName());
			serwisVolleyballer = awayTeam.getServVolleyballer();
			System.out.println("\tSerwuje " + serwisVolleyballer.getShortName());
			actionHome = false;
		}
		
		int rand = GlobalRandom.getInt(10);
		if( serwisVolleyballer.getSerwis() > rand )
		{
			System.out.println("\t\tSerwuje " + (int)((serwisVolleyballer.getSerwis() + serwisVolleyballer.getStrenght()) * 2.5));
			actionHome = !actionHome;
			action.addAction(serwisVolleyballer.getId(), ActionConst.ACTION_TYPE_SERVE, ActionConst.ACTION_RESULT_CONTINUE);
			przyjecie(action, (int)((serwisVolleyballer.getSerwis() + serwisVolleyballer.getStrenght()) * 2.5));
		}
		else
		{
			System.out.println("Serwis nieudany "+serwisVolleyballer.getSerwis()+" > "+rand);
			action.addAction(serwisVolleyballer.getId(), ActionConst.ACTION_TYPE_SERVE, ActionConst.ACTION_RESULT_ERROR);
			addPoint(action, true);
		}
	};
		
//----------------przyjecie()--------------------
	private void przyjecie(MatchAction action, int _serwisResult)
	{
		_serwisResult = _serwisResult % 100;//TODO WALEK
			
		Volleyballer reciveVolleybaler;
		if(actionHome)
		{
			System.out.println("Przyjecie druzyna " + homeTeam.getTeamName());
			reciveVolleybaler = homeTeam.getReciveVolleyballer();
			System.out.println("\tPrzyjmuje " + reciveVolleybaler.getShortName());
		}
		else
		{
			System.out.println("Przyjecie druzyna " + awayTeam.getTeamName());
			reciveVolleybaler = awayTeam.getReciveVolleyballer();
			System.out.println("\tPrzyjmuje " + reciveVolleybaler.getShortName());
		}
			
		if(superMegaPowerShot) // PRZERYWAMY ZA DLUGA SERIE WYMIAN :P
		{
			System.out.println("\t\tPrzyjecie nieudane");
			action.addAction(reciveVolleybaler.getId(), ActionConst.ACTION_TYPE_RECEPTION, ActionConst.ACTION_RESULT_ERROR);
			addPoint(action, true);
			superMegaPowerShot = false;
		}
		
		int reciveResult = reciveVolleybaler.getReception()*reciveVolleybaler.getReflex()*3 - _serwisResult;
		if(reciveResult < 0)
		{
			System.out.println("\t\tPrzyjecie nieudane");
			action.addAction(reciveVolleybaler.getId(), ActionConst.ACTION_TYPE_RECEPTION, ActionConst.ACTION_RESULT_ERROR);
			addPoint(action, true);
		}
		else if(reciveResult < 10)
		{
			System.out.println("\t\tPrzyjecie -> blok " + reciveResult);
			action.addAction(reciveVolleybaler.getId(), ActionConst.ACTION_TYPE_RECEPTION, ActionConst.ACTION_RESULT_CONTINUE);
			actionHome = !actionHome;
			blok(action, reciveResult);
		}
		else
		{
			System.out.println("\t\tPrzyjecie -> rozegranie " + reciveResult);
			action.addAction(reciveVolleybaler.getId(), ActionConst.ACTION_TYPE_RECEPTION, ActionConst.ACTION_RESULT_CONTINUE);
			rozegranie(action, reciveResult);
		}
	};
		
//----------------rozegranie()--------------------	
	private void rozegranie(MatchAction action, int reciveResult)
	{
		reciveResult = reciveResult % 100;//TODO WALEK
		
		Volleyballer setVolleybaler;
		if(actionHome)
		{
			System.out.println("Rozegranie druzyna " + homeTeam.getTeamName());
			setVolleybaler = homeTeam.getSetVolleyballer();
			System.out.println("\tRozgrywa " + setVolleybaler.getShortName());
		}
		else
		{
			System.out.println("Rozegranie druzyna " + awayTeam.getTeamName());
			setVolleybaler = awayTeam.getSetVolleyballer();
			System.out.println("\tRozgrywa " + setVolleybaler.getShortName());
		}
		
		int setResult = (setVolleybaler.getRozegranie() + reciveResult) * 5 - GlobalRandom.getInt(100);
		if(setResult < 0)
		{
			System.out.println("\t\tRozegranie nieudane");
			action.addAction(setVolleybaler.getId(), ActionConst.ACTION_TYPE_SET, ActionConst.ACTION_RESULT_ERROR);
			addPoint(action, true);
		}
		else
		{
			System.out.println("\t\trozegranie -> atak " + setResult);
			action.addAction(setVolleybaler.getId(), ActionConst.ACTION_TYPE_SET, ActionConst.ACTION_RESULT_CONTINUE);
			atak(action, setResult);
		}

	};
		
//----------------atak()--------------------		
	private void atak(MatchAction action, int setResult)
	{
		setResult = setResult % 100;//TODO WALEK
		
		Volleyballer attackVolleybaler;
		if(actionHome)
		{
			System.out.println("Atak druzyna " + homeTeam.getTeamName());
			attackVolleybaler = homeTeam.getAttackVolleyballer();
			System.out.println("\tAtakuje " + attackVolleybaler.getShortName());
		}
		else
		{
			System.out.println("Atak druzyna " + awayTeam.getTeamName());
			attackVolleybaler = awayTeam.getAttackVolleyballer();
			System.out.println("\tAtakuje " + attackVolleybaler.getShortName());
		}
		
		int atakResult = (attackVolleybaler.getAtack() + setResult) * 5 - GlobalRandom.getInt(100);
		if(setResult < 0)
		{
			System.out.println("\t\tAtak nieudany");
			action.addAction(attackVolleybaler.getId(), ActionConst.ACTION_TYPE_ATACK, ActionConst.ACTION_RESULT_ERROR);
			addPoint(action, true);
		}
		else if(atakResult < 10)
		{
			System.out.println("\t\tatak -> blok " + atakResult);
			action.addAction(attackVolleybaler.getId(), ActionConst.ACTION_TYPE_ATACK, ActionConst.ACTION_RESULT_CONTINUE);
			actionHome = !actionHome;
			blok(action, atakResult);
		}
		else if(atakResult < 20)
		{
			System.out.println("\t\tatak -> przyjecie " + atakResult);
			action.addAction(attackVolleybaler.getId(), ActionConst.ACTION_TYPE_ATACK, ActionConst.ACTION_RESULT_CONTINUE);
			actionHome = !actionHome;
			przyjecie(action, setResult);
		}
		else			
		{
			System.out.println("\t\tAtak punktowy " + atakResult);
			action.addAction(attackVolleybaler.getId(), ActionConst.ACTION_TYPE_ATACK, ActionConst.ACTION_RESULT_POINT);
			addPoint(action, false);
		}
	};
		
//----------------blok()--------------------		
	private void blok(MatchAction action, int attackResult)
	{
		attackResult = attackResult % 100;//TODO WALEK
		
		Volleyballer blockVolleybaler;
		if(actionHome)
		{
			System.out.println("Blok druzyna " + homeTeam.getTeamName());
			blockVolleybaler = homeTeam.getBlockVolleyballer();
			System.out.println("\tBlokuje " + blockVolleybaler.getShortName());
		}
		else
		{
			System.out.println("Blok druzyna " + awayTeam.getTeamName());
			blockVolleybaler = awayTeam.getBlockVolleyballer();
			System.out.println("\tBlokuje " + blockVolleybaler.getShortName());
		}
		
		int blockkResult = (blockVolleybaler.getBlock() + attackResult) * 5 - GlobalRandom.getInt(100);
		if(blockkResult < 0)
		{
			System.out.println("\t\tBlok nieudane");
			action.addAction(blockVolleybaler.getId(), ActionConst.ACTION_TYPE_BLOCK, ActionConst.ACTION_RESULT_ERROR);
			addPoint(action, true);
		}
		else if(blockkResult < 5)
		{
			System.out.println("\t\tBlok -> przyjecie " +  blockkResult);
			action.addAction(blockVolleybaler.getId(), ActionConst.ACTION_TYPE_BLOCK, ActionConst.ACTION_RESULT_CONTINUE);
			actionHome = !actionHome;
			przyjecie(action, blockkResult);
		}
		else if(blockkResult < 15)
		{
			System.out.println("\t\tBlok -> wyblok " +  blockkResult);
			action.addAction(blockVolleybaler.getId(), ActionConst.ACTION_TYPE_BLOCK, ActionConst.ACTION_RESULT_CONTINUE);
			przyjecie(action, blockkResult);
		}
		else
		{
			System.out.println("\t\tBlok punktowy");
			action.addAction(blockVolleybaler.getId(), ActionConst.ACTION_TYPE_BLOCK, ActionConst.ACTION_RESULT_POINT);
			addPoint(action, false);
		}
		
	};
		
//----------------walkaNaSiatce()--------------------		
	void walkaNaSiatce(int reciveResult)
	{
		
	
	};
		
//----------------addPoint()--------------------	
	private void addPoint(MatchAction action, boolean isError)
	{
		if(actionHome && isError) // home popelnia blad
		{
			mMatchInfo.addAwayPoints();//awayTeam.addPoints();
			if(servHome)
				awayTeam.rotation();
			servHome = false;
		}
		else if(actionHome && !isError) // home zdobywa pkt
		{
			mMatchInfo.addHomePoints();//homeTeam.addPoints();
			if(!servHome)
				homeTeam.rotation();
			servHome = true;
		}
		else if(!actionHome && isError) // away popelnia blad
		{
			mMatchInfo.addHomePoints();//homeTeam.addPoints();
			if(!servHome)
				homeTeam.rotation();
			homeTeam.rotation();
			servHome = true;
		}
		else if(!actionHome && !isError) // away zdobywa pkt
		{
			mMatchInfo.addAwayPoints();//awayTeam.addPoints();
			if(servHome)
				awayTeam.rotation();
			servHome = false;
		}
		
		if(!isTieBreak)
		{
			//if(homeTeam.getPoints() > 24 && awayTeam.getPoints() < homeTeam.getPoints() - 1)
			if(mMatchInfo.getHomePoints() > 24 && mMatchInfo.getAwayPoints() < mMatchInfo.getHomePoints() - 1)
			{
				timeTakenHome = 0;
				timeTakenAway = 0;
				mMatchInfo.addHomeSetWon();//homeTeam.addSetPoints();
				//String setEndStr = (homeTeam.getSetPoints() + awayTeam.getSetPoints()) + "_" + homeTeam.getPoints() + ":" + awayTeam.getPoints();
				String setEndStr = (mMatchInfo.getHomeSetWon() + mMatchInfo.getAwaySetWon()) + "_" + mMatchInfo.getHomePoints() + ":" + mMatchInfo.getAwayPoints();
				matchServer.sendSetEnd(setEndStr);
				mMatchInfo.addLittlePoints(mMatchInfo.getHomePoints() + ":" + mMatchInfo.getAwayPoints());
				
				mMatchInfo.setHomePoints(0);//homeTeam.setPoints(0);
				mMatchInfo.setAwayPoints(0);//awayTeam.setPoints(0);
				is8PtsBreak = false;
				is16PtsBreak = false;
				//if(homeTeam.getSetPoints() == 2 && awayTeam.getSetPoints() == 2)
				if(mMatchInfo.getHomeSetWon() == 2 && mMatchInfo.getAwaySetWon() == 2)
					isTieBreak = true;
				//if(homeTeam.getSetPoints() == 3)
				if(mMatchInfo.getHomeSetWon() == 3)
				{
					isMatchEnd = true;
				}
				else
					setTimeToWait(BREAK_TIME, -2);
			}
			//else if(awayTeam.getPoints() > 24 && homeTeam.getPoints() < awayTeam.getPoints() - 1)
			else if(mMatchInfo.getAwayPoints() > 24 && mMatchInfo.getHomePoints() < mMatchInfo.getAwayPoints() - 1)
			{
				timeTakenHome = 0;
				timeTakenAway = 0;
				mMatchInfo.addAwaySetWon();//awayTeam.addSetPoints();
				//String setEndStr = (homeTeam.getSetPoints() + awayTeam.getSetPoints()) + "_" + homeTeam.getPoints() + ":" + awayTeam.getPoints();
				String setEndStr = (mMatchInfo.getHomeSetWon() + mMatchInfo.getAwaySetWon()) + "_" + mMatchInfo.getHomePoints() + ":" + mMatchInfo.getAwayPoints();
				matchServer.sendSetEnd(setEndStr);
				mMatchInfo.addLittlePoints(mMatchInfo.getHomePoints() + ":" + mMatchInfo.getAwayPoints());
				
				mMatchInfo.setHomePoints(0);//homeTeam.setPoints(0);
				mMatchInfo.setAwayPoints(0);//awayTeam.setPoints(0);
				is8PtsBreak = false;
				is16PtsBreak = false;
				//if(homeTeam.getSetPoints() == 2 && awayTeam.getSetPoints() == 2)
				if(mMatchInfo.getHomeSetWon() == 2 && mMatchInfo.getAwaySetWon() == 2)
					isTieBreak = true;
				//if(awayTeam.getSetPoints() == 3)
				if(mMatchInfo.getAwaySetWon() == 3)
				{
					isMatchEnd = true;
				}
				else
					setTimeToWait(BREAK_TIME, -2);
			}
		}
		else
		{
			//if(homeTeam.getPoints() > 14 && awayTeam.getPoints() < homeTeam.getPoints() - 1)
			if(mMatchInfo.getHomePoints() > 14 && mMatchInfo.getAwayPoints() < mMatchInfo.getHomePoints() - 1)
			{
				timeTakenHome = 0;
				timeTakenAway = 0;
				mMatchInfo.addHomeSetWon();//homeTeam.addSetPoints();
				
				//String setEndStr = (homeTeam.getSetPoints() + awayTeam.getSetPoints()) + "_" + homeTeam.getPoints() + ":" + awayTeam.getPoints();
				String setEndStr = (mMatchInfo.getHomeSetWon() + mMatchInfo.getAwaySetWon()) + "_" + mMatchInfo.getHomePoints() + ":" + mMatchInfo.getAwayPoints();
				matchServer.sendSetEnd(setEndStr);
				mMatchInfo.addLittlePoints(mMatchInfo.getHomePoints() + ":" + mMatchInfo.getAwayPoints());
				
				isMatchEnd = true;
				mMatchInfo.setHomePoints(0);//homeTeam.setPoints(0);
				mMatchInfo.setAwayPoints(0);//awayTeam.setPoints(0);
				is8PtsBreak = false;
				is16PtsBreak = false;
			}	
			//else if(awayTeam.getPoints() > 14 && homeTeam.getPoints() < awayTeam.getPoints() - 1)
			else if(mMatchInfo.getAwayPoints() > 14 && mMatchInfo.getHomePoints() < mMatchInfo.getAwayPoints() - 1)
			{
				timeTakenHome = 0;
				timeTakenAway = 0;
				mMatchInfo.addAwaySetWon();//awayTeam.addSetPoints();
				
				//String setEndStr = (homeTeam.getSetPoints() + awayTeam.getSetPoints()) + "_" + homeTeam.getPoints() + ":" + awayTeam.getPoints();
				String setEndStr = (mMatchInfo.getHomeSetWon() + mMatchInfo.getAwaySetWon()) + "_" + mMatchInfo.getHomePoints() + ":" + mMatchInfo.getAwayPoints();
				matchServer.sendSetEnd(setEndStr);
				mMatchInfo.addLittlePoints(mMatchInfo.getHomePoints() + ":" + mMatchInfo.getAwayPoints());
				
				isMatchEnd = true;
				mMatchInfo.setHomePoints(0);//homeTeam.setPoints(0);
				mMatchInfo.setAwayPoints(0);//awayTeam.setPoints(0);
				is8PtsBreak = false;
				is16PtsBreak = false;
			}
		}
		
		if( mMatchInfo.getHomePoints() == 8 || mMatchInfo.getAwayPoints() == 8 )
		{
			if(!is8PtsBreak)
			{
				setTimeToWait(BREAK_TIME, -1);
				is8PtsBreak = true;
			}
		}
		
		if( mMatchInfo.getHomePoints() == 16 || mMatchInfo.getAwayPoints() == 16 )
		{
			if(!is16PtsBreak)
			{
				setTimeToWait(BREAK_TIME, -1);
				is16PtsBreak = true;
			}
		}
		
		if(mMatchInfo.getHomePoints() == 33 || mMatchInfo.getAwayPoints() == 33)
			superMegaPowerShot = true;
		
		/*System.out.println("Wynik: " + homeTeam.getSetPoints() + ":"+ awayTeam.getSetPoints() 
									+ ", " + homeTeam.getPoints() + ":" + awayTeam.getPoints() + "\n");
		String result = homeTeam.getSetPoints() + ":"+ awayTeam.getSetPoints()
				+ "_" + homeTeam.getPoints() + ":" + awayTeam.getPoints();*/
		
		System.out.println("Wynik: " + mMatchInfo.getHomeSetWon() + ":"+ mMatchInfo.getAwaySetWon() 
				+ ", " + mMatchInfo.getHomePoints() + ":" + mMatchInfo.getAwayPoints() + "\n");
		
	String result = mMatchInfo.getHomeSetWon() + ":"+ mMatchInfo.getAwaySetWon()
				+ "_" + mMatchInfo.getHomePoints() + ":" + mMatchInfo.getAwayPoints();
		
		actions.add(action);
		matchServer.sendAction(action.actionsToString());
		matchServer.sendResult(result);
	};
	
	public boolean takeTime(int teamId)
	{
		if(homeTeam.getId() == teamId)
		{
			if(timeTakenHome < 2)
			{
				setTimeToWait(BREAK_TIME, teamId);
				timeTakenHome++;
				return true;
			}
			return false;
		}
		
		if(awayTeam.getId() == teamId)
		{
			if(timeTakenAway < 2)
			{
				setTimeToWait(BREAK_TIME, teamId);
				timeTakenAway++;
				return true;
			}
			return false;
		}
		
		return false;
	}
	
	public boolean getNewTactic(Team team)
	{
		Tactic tmpTactic = TacticDBUtils.getTactic(team.getId());
		if(tmpTactic == null)
			return false;
		
		team.setTactic(tmpTactic);
		return true;
	}
	
	public void needTacticUpdate(int teamId)
	{
		if(teamId == homeTeam.getId())
			isHomeNeedTacticUpdate = true;
		
		if(teamId == awayTeam.getId())
			isAwayNeedTacticUpdate = true;
	}
	
	public void addChange(int teamId, int volleyballerId1, int volleyballerId2)
	{
		System.out.println("add Squad Change()");
		
		changeList.add(new Change(teamId, volleyballerId1, volleyballerId2));
	}
	
	private void doChanges()
	{
		for(int i=0; i<changeList.size(); i++)
		{
			if(changeList.get(i).getTeamId() == homeTeam.getId())
			{
				homeTeam.getMatchSquad().doChange(changeList.get(i).getVolleyballerId1(), changeList.get(i).getVolleyballerId2());
				matchServer.sendChange("" + changeList.get(i).getVolleyballerId1() + "_" + changeList.get(i).getVolleyballerId2());
				continue;
			}
			
			if(changeList.get(i).getTeamId() == awayTeam.getId())
			{
				awayTeam.getMatchSquad().doChange(changeList.get(i).getVolleyballerId1(), changeList.get(i).getVolleyballerId2());
				matchServer.sendChange("" + changeList.get(i).getVolleyballerId1() + "_" + changeList.get(i).getVolleyballerId2());
				continue;
			}
		}
		
		changeList.clear(); // TODO trzeba by gdzies zapamietywac zmiany 
							// i robic tylko dozwolonoe i max 6 na set
	}
//---------------------------------------------------------------------------------------------------
	
	public Team getHomeTeam()
	{
		return homeTeam;
	}
	public Team getAwayTeam()
	{
		return awayTeam;
	}
	public int getTimeToWait() 
	{
		return timeToWait;
	}
	public void setTimeToWait(int timeToWait, int whoTakeTime)
	{
		this.timeToWait = timeToWait;
		this.whoTakeTime = whoTakeTime;
	}
}

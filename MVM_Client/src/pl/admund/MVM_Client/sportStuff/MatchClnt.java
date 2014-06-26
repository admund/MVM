package pl.admund.MVM_Client.sportStuff;

import java.util.Random;

public class MatchClnt
{
	int matchID;
	String date;
	
	static Random rand = new Random(System.currentTimeMillis());
	
	TeamClnt[] teams = new TeamClnt[2];
	byte  whoSerw;
	byte homeSetPoints;
	byte homePoints;
	byte awaySetPoints;
	byte awayPoints;
	
	public static MatchClnt creatRandomMatch()
	{
		MatchClnt tmpMatch = new MatchClnt();
		
		//tmpMatch.teams[0] = TeamClnt.creatRandomTeam();
		//tmpMatch.teams[1] = TeamClnt.creatRandomTeam();
		
		tmpMatch.whoSerw = 0;
		tmpMatch.homeSetPoints = 0;
		tmpMatch.homePoints = 0;
		tmpMatch.awaySetPoints = 0;
		tmpMatch.awayPoints = 0;
		
		return tmpMatch;
	}
	
//----------------AKCJE--------------------
		int serwis()
		{
			
			return 0;//player.serwis*teams[howSerw].teamTac.;
		};
		
		int przyjecie()
		{
			
			return 0;
		};
		
		int rozegranie()
		{
			
			return 0;
		};
		
		int atak()
		{
			
			return 0;
		};
		
		int blok()
		{
			
			return 0;
		};
		
		int walkaNaSiatce()
		{
			
			return 0;
		};
}

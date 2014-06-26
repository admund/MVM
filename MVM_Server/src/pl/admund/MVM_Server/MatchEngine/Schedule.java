package pl.admund.MVM_Server.MatchEngine;

import java.util.ArrayList;
import java.util.List;

public class Schedule 
{
	List<MatchInfo> matachInfoList = new ArrayList<MatchInfo>();
	
//-------------------------------Schedule()----------------------------------------------	
	public Schedule(Division division)
	{
/*		//kolejka 1
		addMatchInfo(division.getTeamList().get(9).getId(), division.getTeamList().get(0).getId(), (byte)1);
		addMatchInfo(division.getTeamList().get(1).getId(), division.getTeamList().get(7).getId(), (byte)1);
		addMatchInfo(division.getTeamList().get(2).getId(), division.getTeamList().get(6).getId(), (byte)1);
		addMatchInfo(division.getTeamList().get(4).getId(), division.getTeamList().get(3).getId(), (byte)1);
		addMatchInfo(division.getTeamList().get(5).getId(), division.getTeamList().get(8).getId(), (byte)1);
		
		//kolejka 2
		addMatchInfo(division.getTeamList().get(8).getId(), division.getTeamList().get(9).getId(), (byte)2);
		addMatchInfo(division.getTeamList().get(3).getId(), division.getTeamList().get(5).getId(), (byte)2);
		addMatchInfo(division.getTeamList().get(6).getId(), division.getTeamList().get(4).getId(), (byte)2);
		addMatchInfo(division.getTeamList().get(7).getId(), division.getTeamList().get(2).getId(), (byte)2);
		addMatchInfo(division.getTeamList().get(0).getId(), division.getTeamList().get(1).getId(), (byte)2);
		
		//kolejka 3
		addMatchInfo(division.getTeamList().get(1).getId(), division.getTeamList().get(9).getId(), (byte)3);
		addMatchInfo(division.getTeamList().get(2).getId(), division.getTeamList().get(0).getId(), (byte)3);
		addMatchInfo(division.getTeamList().get(4).getId(), division.getTeamList().get(7).getId(), (byte)3);
		addMatchInfo(division.getTeamList().get(5).getId(), division.getTeamList().get(6).getId(), (byte)3);
		addMatchInfo(division.getTeamList().get(8).getId(), division.getTeamList().get(3).getId(), (byte)3);
		
		//kolejka 4
		addMatchInfo(division.getTeamList().get(9).getId(), division.getTeamList().get(3).getId(), (byte)4);
		addMatchInfo(division.getTeamList().get(6).getId(), division.getTeamList().get(8).getId(), (byte)4);
		addMatchInfo(division.getTeamList().get(7).getId(), division.getTeamList().get(5).getId(), (byte)4);
		addMatchInfo(division.getTeamList().get(0).getId(), division.getTeamList().get(4).getId(), (byte)4);
		addMatchInfo(division.getTeamList().get(1).getId(), division.getTeamList().get(2).getId(), (byte)4);
		
		//kolejka 5
		addMatchInfo(division.getTeamList().get(2).getId(), division.getTeamList().get(9).getId(), (byte)5);
		addMatchInfo(division.getTeamList().get(4).getId(), division.getTeamList().get(1).getId(), (byte)5);
		addMatchInfo(division.getTeamList().get(5).getId(), division.getTeamList().get(0).getId(), (byte)5);
		addMatchInfo(division.getTeamList().get(7).getId(), division.getTeamList().get(8).getId(), (byte)5);
		addMatchInfo(division.getTeamList().get(3).getId(), division.getTeamList().get(6).getId(), (byte)5);
		
		//kolejka 6
		addMatchInfo(division.getTeamList().get(9).getId(), division.getTeamList().get(6).getId(), (byte)6);
		addMatchInfo(division.getTeamList().get(7).getId(), division.getTeamList().get(3).getId(), (byte)6);
		addMatchInfo(division.getTeamList().get(0).getId(), division.getTeamList().get(8).getId(), (byte)6);
		addMatchInfo(division.getTeamList().get(1).getId(), division.getTeamList().get(5).getId(), (byte)6);
		addMatchInfo(division.getTeamList().get(2).getId(), division.getTeamList().get(4).getId(), (byte)6);
		
		//kolejka 7
		addMatchInfo(division.getTeamList().get(4).getId(), division.getTeamList().get(9).getId(), (byte)7);
		addMatchInfo(division.getTeamList().get(5).getId(), division.getTeamList().get(2).getId(), (byte)7);
		addMatchInfo(division.getTeamList().get(8).getId(), division.getTeamList().get(1).getId(), (byte)7);
		addMatchInfo(division.getTeamList().get(3).getId(), division.getTeamList().get(0).getId(), (byte)7);
		addMatchInfo(division.getTeamList().get(6).getId(), division.getTeamList().get(7).getId(), (byte)7);
		
		//kolejka 8
		addMatchInfo(division.getTeamList().get(9).getId(), division.getTeamList().get(7).getId(), (byte)8);
		addMatchInfo(division.getTeamList().get(0).getId(), division.getTeamList().get(6).getId(), (byte)8);
		addMatchInfo(division.getTeamList().get(1).getId(), division.getTeamList().get(3).getId(), (byte)8);
		addMatchInfo(division.getTeamList().get(2).getId(), division.getTeamList().get(8).getId(), (byte)8);
		addMatchInfo(division.getTeamList().get(4).getId(), division.getTeamList().get(5).getId(), (byte)8);
		
		//kolejka 9
		addMatchInfo(division.getTeamList().get(5).getId(), division.getTeamList().get(9).getId(), (byte)9);
		addMatchInfo(division.getTeamList().get(8).getId(), division.getTeamList().get(4).getId(), (byte)9);
		addMatchInfo(division.getTeamList().get(3).getId(), division.getTeamList().get(2).getId(), (byte)9);
		addMatchInfo(division.getTeamList().get(6).getId(), division.getTeamList().get(1).getId(), (byte)9);
		addMatchInfo(division.getTeamList().get(7).getId(), division.getTeamList().get(0).getId(), (byte)9);
		
		//kolejka 10
		addMatchInfo(division.getTeamList().get(0).getId(), division.getTeamList().get(9).getId(), (byte)10);
		addMatchInfo(division.getTeamList().get(7).getId(), division.getTeamList().get(1).getId(), (byte)10);
		addMatchInfo(division.getTeamList().get(6).getId(), division.getTeamList().get(2).getId(), (byte)10);
		addMatchInfo(division.getTeamList().get(3).getId(), division.getTeamList().get(4).getId(), (byte)10);
		addMatchInfo(division.getTeamList().get(8).getId(), division.getTeamList().get(5).getId(), (byte)10);
		
		//kolejka 11
		addMatchInfo(division.getTeamList().get(9).getId(), division.getTeamList().get(8).getId(), (byte)11);
		addMatchInfo(division.getTeamList().get(5).getId(), division.getTeamList().get(3).getId(), (byte)11);
		addMatchInfo(division.getTeamList().get(4).getId(), division.getTeamList().get(6).getId(), (byte)11);
		addMatchInfo(division.getTeamList().get(2).getId(), division.getTeamList().get(7).getId(), (byte)11);
		addMatchInfo(division.getTeamList().get(1).getId(), division.getTeamList().get(0).getId(), (byte)11);
		
		//kolejka 12
		addMatchInfo(division.getTeamList().get(9).getId(), division.getTeamList().get(1).getId(), (byte)12);
		addMatchInfo(division.getTeamList().get(0).getId(), division.getTeamList().get(2).getId(), (byte)12);
		addMatchInfo(division.getTeamList().get(7).getId(), division.getTeamList().get(4).getId(), (byte)12);
		addMatchInfo(division.getTeamList().get(6).getId(), division.getTeamList().get(5).getId(), (byte)12);
		addMatchInfo(division.getTeamList().get(3).getId(), division.getTeamList().get(8).getId(), (byte)12);
		
		//kolejka 13
		addMatchInfo(division.getTeamList().get(3).getId(), division.getTeamList().get(9).getId(), (byte)13);
		addMatchInfo(division.getTeamList().get(8).getId(), division.getTeamList().get(6).getId(), (byte)13);
		addMatchInfo(division.getTeamList().get(5).getId(), division.getTeamList().get(7).getId(), (byte)13);
		addMatchInfo(division.getTeamList().get(4).getId(), division.getTeamList().get(0).getId(), (byte)13);
		addMatchInfo(division.getTeamList().get(2).getId(), division.getTeamList().get(1).getId(), (byte)13);
		
		//kolejka 14
		addMatchInfo(division.getTeamList().get(9).getId(), division.getTeamList().get(2).getId(), (byte)14);
		addMatchInfo(division.getTeamList().get(1).getId(), division.getTeamList().get(4).getId(), (byte)14);
		addMatchInfo(division.getTeamList().get(0).getId(), division.getTeamList().get(5).getId(), (byte)14);
		addMatchInfo(division.getTeamList().get(8).getId(), division.getTeamList().get(7).getId(), (byte)14);
		addMatchInfo(division.getTeamList().get(6).getId(), division.getTeamList().get(3).getId(), (byte)14);
		
		//kolejka 15
		addMatchInfo(division.getTeamList().get(6).getId(), division.getTeamList().get(9).getId(), (byte)15);
		addMatchInfo(division.getTeamList().get(3).getId(), division.getTeamList().get(7).getId(), (byte)15);
		addMatchInfo(division.getTeamList().get(8).getId(), division.getTeamList().get(0).getId(), (byte)15);
		addMatchInfo(division.getTeamList().get(5).getId(), division.getTeamList().get(1).getId(), (byte)15);
		addMatchInfo(division.getTeamList().get(2).getId(), division.getTeamList().get(4).getId(), (byte)15);
		
		//kolejka 16
		addMatchInfo(division.getTeamList().get(9).getId(), division.getTeamList().get(4).getId(), (byte)16);
		addMatchInfo(division.getTeamList().get(2).getId(), division.getTeamList().get(5).getId(), (byte)16);
		addMatchInfo(division.getTeamList().get(1).getId(), division.getTeamList().get(8).getId(), (byte)16);
		addMatchInfo(division.getTeamList().get(0).getId(), division.getTeamList().get(3).getId(), (byte)16);
		addMatchInfo(division.getTeamList().get(7).getId(), division.getTeamList().get(6).getId(), (byte)16);
		
		//kolejka 17
		addMatchInfo(division.getTeamList().get(7).getId(), division.getTeamList().get(9).getId(), (byte)17);
		addMatchInfo(division.getTeamList().get(6).getId(), division.getTeamList().get(0).getId(), (byte)17);
		addMatchInfo(division.getTeamList().get(3).getId(), division.getTeamList().get(1).getId(), (byte)17);
		addMatchInfo(division.getTeamList().get(8).getId(), division.getTeamList().get(2).getId(), (byte)17);
		addMatchInfo(division.getTeamList().get(5).getId(), division.getTeamList().get(4).getId(), (byte)17);
		
		//kolejka 18
		addMatchInfo(division.getTeamList().get(9).getId(), division.getTeamList().get(5).getId(), (byte)18);
		addMatchInfo(division.getTeamList().get(4).getId(), division.getTeamList().get(8).getId(), (byte)18);
		addMatchInfo(division.getTeamList().get(2).getId(), division.getTeamList().get(3).getId(), (byte)18);
		addMatchInfo(division.getTeamList().get(1).getId(), division.getTeamList().get(6).getId(), (byte)18);
		addMatchInfo(division.getTeamList().get(0).getId(), division.getTeamList().get(7).getId(), (byte)18);
*/
	};
//------------------------------------------------------------------------------
	/*void addMatchInfo(int homeID, int awayID, byte roundNR)
	{
		MatchInfo tmpMatchInfo1 = new MatchInfo();
		tmpMatchInfo1.date = "DATA";
		tmpMatchInfo1.isEnd = false;
		tmpMatchInfo1.roundNr = roundNR;
		tmpMatchInfo1.winTeam = -1;
		tmpMatchInfo1.homeTeamID = homeID;
		tmpMatchInfo1.awayTeamID = awayID;
		matachInfoList.add(tmpMatchInfo1);
	}*/
	
//------------------------------------------------------------------------------	
	/*public void printSchedule()
	{
		for(int i=0; i < matachInfoList.size(); i++)
			System.out.println("Mecz " + matachInfoList.get(i).matchID + " h: " + matachInfoList.get(i).homeTeamID 
					+ " a: " + matachInfoList.get(i).awayTeamID + " kNR: " + matachInfoList.get(i).roundNr);
	}*/
	
//------------------------------------------------------------------------------
}

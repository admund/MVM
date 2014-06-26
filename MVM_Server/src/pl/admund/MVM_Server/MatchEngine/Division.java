package pl.admund.MVM_Server.MatchEngine;

import java.util.ArrayList;
import java.util.List;

import pl.admund.MVM_Server.Team.Team;


public class Division
{
	static int mIdCount = 0;
	private int mDivisionID;
	
	private List<Team> mTeamList = new ArrayList<Team>();
	private DivisionInfo mDivisionInfo;
	private Schedule mSchedule;
	
	public static Division createRandomDivision()
	{
		Division tmpDivision = new Division();
		
		tmpDivision.mDivisionID = mIdCount; mIdCount++;
		
		for(int i=0; i < 10; i++)
			tmpDivision.mTeamList.add(Team.createRandomTeam());
		
		tmpDivision.mDivisionInfo = tmpDivision.new DivisionInfo();
		{
			tmpDivision.mDivisionInfo.divisionName = "RAND_DIVISION_" + tmpDivision.mDivisionID;
		}
		
		tmpDivision.mSchedule = new Schedule(tmpDivision);
		
		return tmpDivision;
	}
	
	public void printSchedule()
	{
		;//TODO mSchedule.printSchedule();
	}
	
	public int getTeamId(int _id)
	{
		return mTeamList.get(_id).getId();
	}

	public List<Team> getTeamList(){
		return mTeamList;
	}
	
//--------------------------------------class DivisionInfo---------------------------------------------------
	class DivisionInfo
	{
		String divisionName;
	}
//---------------------------------------------------------------------------------------------------
}

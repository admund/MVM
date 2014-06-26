package pl.admund.MVM_Client.LeagueTable;

import pl.admund.MVM_Client.Utils.GlobalRandom;

public class TeamTableInfoClnt implements Comparable<TeamTableInfoClnt>
{
	private int teamId;
	private String teamName;
	private int points;
	private int matchCnt;
	private int matchWin;
	private int matchLoose;
	private int setWin;
	private int setLoose;
	private double setRatio;
	private int littleWin;
	private int littleLoose;
	private double littleRatio;
	
	public static TeamTableInfoClnt createRandom()
	{
		TeamTableInfoClnt tmp = new TeamTableInfoClnt();
		tmp.teamId = GlobalRandom.getInt(100);
		tmp.teamName = "Rand_" + GlobalRandom.getInt(100);
		tmp.points = GlobalRandom.getInt(100);
		tmp.matchCnt = GlobalRandom.getInt(100);
		tmp.matchWin = GlobalRandom.getInt(100);
		tmp.matchLoose = GlobalRandom.getInt(100);
		tmp.setWin = GlobalRandom.getInt(100);
		tmp.setLoose = GlobalRandom.getInt(100);
		tmp.littleWin = GlobalRandom.getInt(100);
		tmp.littleLoose = GlobalRandom.getInt(100);
		
		tmp.computeRatio();
	
		return tmp;
	}
	
	public void computeRatio()
	{
		this.setRatio = (double)setWin/(double)setLoose;
		this.littleRatio = (double)littleWin/(double)littleLoose;
	}
	
	@Override
	public int compareTo(TeamTableInfoClnt another) 
	{
		if(this.points < another.points)
			return 1;
		else if(this.points > another.points)
			return -1;
		else
			if(this.littleRatio < another.littleRatio)
				return 1;
			else
				return -1;
	}

	public int getTeamId() {
		return teamId;
	}
	public void setTeamId(int teamId) {
		this.teamId = teamId;
	}
	public String getTeamName() {
		return teamName;
	}
	public void setTeamName(String teamName) {
		this.teamName = teamName;
	}
	public int getPoints() {
		return points;
	}
	public void setPoints(int points) {
		this.points = points;
	}
	public int getMatchCnt() {
		return matchCnt;
	}
	public void setMatchCnt(int matchCnt) {
		this.matchCnt = matchCnt;
	}
	public int getMatchWin() {
		return matchWin;
	}
	public void setMatchWin(int matchWin) {
		this.matchWin = matchWin;
	}
	public int getMatchLoose() {
		return matchLoose;
	}
	public void setMatchLoose(int matchLoose) {
		this.matchLoose = matchLoose;
	}
	public int getSetWin() {
		return setWin;
	}
	public void setSetWin(int setWin) {
		this.setWin = setWin;
	}
	public int getSetLoose() {
		return setLoose;
	}
	public void setSetLoose(int setLoose) {
		this.setLoose = setLoose;
	}
	public double getSetRatio() {
		return setRatio;
	}
	public void setSetRatio(double setRation) {
		this.setRatio = setRation;
	}
	public int getLittleWin() {
		return littleWin;
	}
	public void setLittleWin(int littleWin) {
		this.littleWin = littleWin;
	}
	public int getLittleLoose() {
		return littleLoose;
	}
	public void setLittleLoose(int littleLoose) {
		this.littleLoose = littleLoose;
	}
	public double getLittleRatio() {
		return littleRatio;
	}
	public void setLittleRatio(double littleRation) {
		this.littleRatio = littleRation;
	}
}

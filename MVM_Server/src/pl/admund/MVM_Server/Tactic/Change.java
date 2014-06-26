package pl.admund.MVM_Server.Tactic;

public class Change 
{
	private int teamId;
	private int volleyballerId1;
	private int volleyballerId2;
	
	public Change(int teamId, int volleyballerId1, int volleyballerId2)
	{
		this.teamId = teamId;
		this.volleyballerId1 = volleyballerId1;
		this.volleyballerId2 = volleyballerId2;
	}
	
	public int getTeamId() {
		return teamId;
	}
	public void setTeamId(int teamId) {
		this.teamId = teamId;
	}
	public int getVolleyballerId1() {
		return volleyballerId1;
	}
	public void setVolleyballerId1(int volleyballerId1) {
		this.volleyballerId1 = volleyballerId1;
	}
	public int getVolleyballerId2() {
		return volleyballerId2;
	}
	public void setVolleyballerId2(int volleyballerId2) {
		this.volleyballerId2 = volleyballerId2;
	}
}

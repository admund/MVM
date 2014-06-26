package pl.admund.MVM_Client.Tactics;

public class TeamMatchSquad 
{
	private int attackerId;
	private int reciver1Id;
	private int reciver2Id;
	private int setterId;
	private int blcker1Id;
	private int blcker2Id;
	private int liberoId;
	
	private int attackerResId;
	private int reciver1resId;
	private int reciver2resId;
	private int blckerResId;
	private int setterResId;
	
	public TeamMatchSquad(int attackerId, int reciver1Id, int reciver2Id,
						int setterId, int blcker1Id, int blcker2Id, int liberoId,
						int attackerResId, int reciver1resId, int reciver2resId,
						int blckerResId, int setterResId)
	{
		this.attackerId = attackerId;
		this.reciver1Id = reciver1Id;
		this.reciver2Id = reciver2Id;
		this.setterId = setterId;
		this.blcker1Id = blcker1Id;
		this.blcker2Id = blcker2Id;
		this.liberoId = liberoId;
		
		this.attackerResId = attackerResId;
		this.reciver1resId = reciver1resId;
		this.reciver2resId = reciver2resId;
		this.blckerResId = blckerResId;
		this.setterResId = setterResId;
	}
	
	public static TeamMatchSquad parseTeamMatchSquad(String str)
	{
		String[] strTab = str.split(",");
		if(strTab.length != 12)
			return null;
		
		int attackerId = Integer.parseInt(strTab[0]);
		int reciver1Id = Integer.parseInt(strTab[1]);
		int reciver2Id = Integer.parseInt(strTab[2]);
		int setterId = Integer.parseInt(strTab[3]);
		int blcker1Id = Integer.parseInt(strTab[4]);
		int blcker2Id = Integer.parseInt(strTab[5]);
		int liberoId = Integer.parseInt(strTab[6]);
		
		int attackerResId = Integer.parseInt(strTab[7]);
		int reciver1resId = Integer.parseInt(strTab[8]);
		int reciver2resId = Integer.parseInt(strTab[9]);
		int blckerResId = Integer.parseInt(strTab[10]);
		int setterResId = Integer.parseInt(strTab[11]);
		
		return new TeamMatchSquad(attackerId, reciver1Id, reciver2Id,
				setterId, blcker1Id, blcker2Id, liberoId,
				attackerResId, reciver1resId, reciver2resId,
				blckerResId, setterResId);
	}
	
	public String toString()
	{
		String str = "";
		str += getAttackerId() + ",";
		str += getReciver1Id() + ",";
		str += getReciver2Id() + ",";
		str += getSetterId() + ",";
		str += getBlcker1Id() + ",";
		str += getBlcker2Id() + ",";
		str += getLiberoId() + ",";
		str += getAttackerResId() + ",";
		str += getReciver1resId() + ",";
		str += getReciver2resId() + ",";
		str += getBlckerResId() + ",";
		str += getSetterResId();
		
		return str;
	}
	
	public int getVolleyballerIdFromPos(int pos)
	{
		if(pos == 0)
			return getAttackerId();
		else if(pos == 1)
			return getReciver1Id();
		else if(pos == 2)
			return getReciver2Id();
		else if(pos == 3)
			return getSetterId();
		else if(pos == 4)
			return getBlcker1Id();
		else if(pos == 5)
			return getBlcker2Id();
		else if(pos == 6)
			return getLiberoId();
		else if(pos == 7)
			return getAttackerResId();
		else if(pos == 8)
			return getReciver1resId();
		else if(pos == 9)
			return getReciver2resId();
		else if(pos == 10)
			return getBlckerResId();
		else
			return getSetterResId();
	}
	
	public void doChange(int volleyballerId1, int volleyballerId2)
	{
		if(getAttackerId() == volleyballerId1)
			setAttackerId(volleyballerId2);
		else if(getReciver1Id() == volleyballerId1)
			setReciver1Id(volleyballerId2);
		else if(getReciver2Id() == volleyballerId1)
			setReciver2Id(volleyballerId2);
		else if(getBlcker1Id() == volleyballerId1)
			setBlcker1Id(volleyballerId2);
		else if(getBlcker2Id() == volleyballerId1)
			setBlcker2Id(volleyballerId2);
		else if(getSetterId() == volleyballerId1)
			setSetterId(volleyballerId2);
		else if(getLiberoId() == volleyballerId1)
			setLiberoId(volleyballerId2);
		
		if(getAttackerResId() == volleyballerId2)
			setAttackerResId(volleyballerId1);
		else if(getReciver1resId() == volleyballerId2)
			setReciver1resId(volleyballerId1);
		else if(getReciver2resId() == volleyballerId2)
			setReciver2resId(volleyballerId1);
		else if(getBlckerResId() == volleyballerId2)
			setBlckerResId(volleyballerId1);
		else if(getSetterResId() == volleyballerId2)
			setSetterResId(volleyballerId1);
	}
	
	public int getAttackerId() {
		return attackerId;
	}
	public void setAttackerId(int attackerId) {
		this.attackerId = attackerId;
	}
	public int getReciver1Id() {
		return reciver1Id;
	}
	public void setReciver1Id(int reciver1Id) {
		this.reciver1Id = reciver1Id;
	}
	public int getReciver2Id() {
		return reciver2Id;
	}
	public void setReciver2Id(int reciver2Id) {
		this.reciver2Id = reciver2Id;
	}
	public int getSetterId() {
		return setterId;
	}
	public void setSetterId(int setterId) {
		this.setterId = setterId;
	}
	public int getBlcker1Id() {
		return blcker1Id;
	}
	public void setBlcker1Id(int blcker1Id) {
		this.blcker1Id = blcker1Id;
	}
	public int getBlcker2Id() {
		return blcker2Id;
	}
	public void setBlcker2Id(int blcker2Id) {
		this.blcker2Id = blcker2Id;
	}
	public int getLiberoId() {
		return liberoId;
	}
	public void setLiberoId(int liberoId) {
		this.liberoId = liberoId;
	}
	public int getAttackerResId() {
		return attackerResId;
	}
	public void setAttackerResId(int attackerResId) {
		this.attackerResId = attackerResId;
	}
	public int getReciver1resId() {
		return reciver1resId;
	}
	public void setReciver1resId(int reciver1resId) {
		this.reciver1resId = reciver1resId;
	}
	public int getReciver2resId() {
		return reciver2resId;
	}
	public void setReciver2resId(int reciver2resId) {
		this.reciver2resId = reciver2resId;
	}
	public int getBlckerResId() {
		return blckerResId;
	}
	public void setBlckerResId(int blckerResId) {
		this.blckerResId = blckerResId;
	}
	public int getSetterResId() {
		return setterResId;
	}
	public void setSetterResId(int setterResId) {
		this.setterResId = setterResId;
	}
}

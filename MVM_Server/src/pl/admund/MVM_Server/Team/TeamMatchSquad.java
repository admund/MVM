package pl.admund.MVM_Server.Team;

import java.util.ArrayList;
import pl.admund.MVM_Server.Volleyballer.Volleyballer;

public class TeamMatchSquad
{
	private Volleyballer attacker;
	private Volleyballer receiver1;
	private Volleyballer blocker1;
	private Volleyballer setter;
	private Volleyballer receiver2;
	private Volleyballer blocker2;
	private Volleyballer libero;
	private Volleyballer attackerRes;
	private Volleyballer receiver1Res;
	private Volleyballer blockerRes;
	private Volleyballer receiver2Res;
	private Volleyballer setterRes;
	
	public TeamMatchSquad(){};
	
	public TeamMatchSquad(ArrayList<Volleyballer> volleyballerArray)
	{
		setMatchSquad(volleyballerArray);
	}
	
	public boolean setMatchSquad(ArrayList<Volleyballer> volleyballerArray)
	{
		if(volleyballerArray.size() >= 12)
		{
			attacker = volleyballerArray.get(0);
			receiver1 = volleyballerArray.get(1);
			blocker1 = volleyballerArray.get(2);
			setter = volleyballerArray.get(3);
			receiver2 = volleyballerArray.get(4);
			blocker2 = volleyballerArray.get(5);
			libero = volleyballerArray.get(6);
			attackerRes = volleyballerArray.get(7);
			receiver1Res = volleyballerArray.get(8);
			blockerRes = volleyballerArray.get(9);
			receiver2Res = volleyballerArray.get(10);
			setterRes = volleyballerArray.get(11);
			return true;
		}
		else
			System.out.println("ZA MALO ZIOMKOW DRUZYNIE ZEBY WYSTAWIC SKLAD!!!");
		return false;
	}
	
	public void doChange(int volleyballerId1, int volleyballerId2)
	{
		Volleyballer tmpVolleyballer1 = null;
		Volleyballer tmpVolleyballer2 = null;
		
		if(getAttacker().getId() == volleyballerId1)
			tmpVolleyballer1 = getAttacker();
		else if(getReceiver1().getId() == volleyballerId1)
			tmpVolleyballer1 = getReceiver1();
		else if(getReceiver2().getId() == volleyballerId1)
			tmpVolleyballer1 = getReceiver2();
		else if(getBlocker1().getId() == volleyballerId1)
			tmpVolleyballer1 = getBlocker1();
		else if(getBlocker2().getId() == volleyballerId1)
			tmpVolleyballer1 = getBlocker2();
		else if(getSetter().getId() == volleyballerId1)
			tmpVolleyballer1 = getSetter();
		else if(getLibero().getId() == volleyballerId1)
			tmpVolleyballer1 = getLibero();
		
		if(getAttackerRes().getId() == volleyballerId2)
			tmpVolleyballer2 = getAttackerRes();
		else if(getReceiver1Res().getId() == volleyballerId2)
			tmpVolleyballer2 = getReceiver1Res();
		else if(getReceiver2Res().getId() == volleyballerId2)
			tmpVolleyballer2 = getReceiver2Res();
		else if(getBlockerRes().getId() == volleyballerId2)
			tmpVolleyballer2 = getBlockerRes();
		else if(getSetterRes().getId() == volleyballerId2)
			tmpVolleyballer2 = getSetterRes();
		
		//System.out.println("SWAP: att: " + getAttacker() + " tmp: " + tmpVolleyballer1);
		//System.out.println("SWAP 1: att: " + getAttacker() + " att res: " + getAttackerRes());
		
		if(getAttacker().getId() == volleyballerId1)
			setAttacker(tmpVolleyballer2);
		else if(getReceiver1().getId() == volleyballerId1)
			setReceiver1(tmpVolleyballer2);
		else if(getReceiver2().getId() == volleyballerId1)
			setReceiver2(tmpVolleyballer2);
		else if(getBlocker1().getId() == volleyballerId1)
			setBlocker1(tmpVolleyballer2);
		else if(getBlocker2().getId() == volleyballerId1)
			setBlocker2(tmpVolleyballer2);
		else if(getSetter().getId() == volleyballerId1)
			setSetter(tmpVolleyballer2);
		else if(getLibero().getId() == volleyballerId1)
			setLibero(tmpVolleyballer2);
		
		if(getAttackerRes().getId() == volleyballerId2)
			setAttackerRes(tmpVolleyballer1);
		else if(getReceiver1Res().getId() == volleyballerId2)
			setReceiver1Res(tmpVolleyballer1);
		else if(getReceiver2Res().getId() == volleyballerId2)
			setReceiver2Res(tmpVolleyballer1);
		else if(getBlockerRes().getId() == volleyballerId2)
			setBlockerRes(tmpVolleyballer1);
		else if(getSetterRes().getId() == volleyballerId2)
			setSetterRes(tmpVolleyballer1);
		
		/*if(getAttackerRes().getId() == volleyballerId2)
			swap(tmpVolleyballer, getAttackerRes());
		else if(getReceiver1Res().getId() == volleyballerId2)
			swap(tmpVolleyballer, getReceiver1Res());
		else if(getReceiver2Res().getId() == volleyballerId2)
			swap(tmpVolleyballer, getReceiver2Res());
		else if(getBlockerRes().getId() == volleyballerId2)
			swap(tmpVolleyballer, getBlockerRes());
		else if(getSetterRes().getId() == volleyballerId2)
			swap(tmpVolleyballer, getSetterRes());*/
		//System.out.println("SWAP 2: att: " + getAttacker() + " att res: " + getAttackerRes());
	}
	
	private void swap(Volleyballer volleyballer1, Volleyballer volleyballer2)
	{
		System.out.println("swap 1: " + volleyballer1.getId() + " -> " + volleyballer2.getId());
		Volleyballer tmp = volleyballer2;
		volleyballer2 = volleyballer1;
		volleyballer1 = tmp;
		System.out.println("swap 2: " + volleyballer1.getId() + " -> " + volleyballer2.getId());
	}
	
	public Volleyballer getAttacker() {
		return attacker;
	}
	public void setAttacker(Volleyballer attacker) {
		this.attacker = attacker;
	}
	public Volleyballer getReceiver1() {
		return receiver1;
	}
	public void setReceiver1(Volleyballer receiver1) {
		this.receiver1 = receiver1;
	}
	public Volleyballer getBlocker1() {
		return blocker1;
	}
	public void setBlocker1(Volleyballer blocker1) {
		this.blocker1 = blocker1;
	}
	public Volleyballer getSetter() {
		return setter;
	}
	public void setSetter(Volleyballer setter) {
		this.setter = setter;
	}
	public Volleyballer getReceiver2() {
		return receiver2;
	}
	public void setReceiver2(Volleyballer receiver2) {
		this.receiver2 = receiver2;
	}
	public Volleyballer getBlocker2() {
		return blocker2;
	}
	public void setBlocker2(Volleyballer blocker2) {
		this.blocker2 = blocker2;
	}
	public Volleyballer getLibero() {
		return libero;
	}
	public void setLibero(Volleyballer libero) {
		this.libero = libero;
	}
	public Volleyballer getAttackerRes() {
		return attackerRes;
	}
	public void setAttackerRes(Volleyballer attackerRes) {
		this.attackerRes = attackerRes;
	}
	public Volleyballer getReceiver1Res() {
		return receiver1Res;
	}
	public void setReceiver1Res(Volleyballer receiver1Res) {
		this.receiver1Res = receiver1Res;
	}
	public Volleyballer getBlockerRes() {
		return blockerRes;
	}
	public void setBlockerRes(Volleyballer blockerRes) {
		this.blockerRes = blockerRes;
	}
	public Volleyballer getReceiver2Res() {
		return receiver2Res;
	}
	public void setReceiver2Res(Volleyballer receiver2Res) {
		this.receiver2Res = receiver2Res;
	}
	public Volleyballer getSetterRes() {
		return setterRes;
	}
	public void setSetterRes(Volleyballer setterRes) {
		this.setterRes = setterRes;
	}
}

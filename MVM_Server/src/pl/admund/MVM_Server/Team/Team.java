package pl.admund.MVM_Server.Team;

import java.util.ArrayList;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import pl.admund.MVM_Server.DBConnect.DBUtils;
import pl.admund.MVM_Server.Tactic.Tactic;
import pl.admund.MVM_Server.Utils.GlobalRandom;
import pl.admund.MVM_Server.Volleyballer.Volleyballer;
import pl.admund.MVM_Server.Volleyballer.Volleyballer.eVolleyballerPosition;
import pl.admund.MVM_Server.XML.XMLUtils;

public class Team
{
	private int id;
	private String teamName;
	private int mLeagueId;
	private int actualSezon;
	private boolean isBot;
	private Tactic teamTac;
	private TeamMatchSquad matchSquad;
	private ArrayList<Volleyballer> volleyballerList = new ArrayList<Volleyballer>();
	
	private int servNr;
	/*private int setPoints;		//TODO zjebane do poprawy
	private int points;		//TODO zjebane do poprawy*/

	public static Team createTeam(String _name)
	{
		//System.out.println("createTeam " + _name);
		Team tmpTeam = new Team();
		
		int tmpId = DBUtils.getMaxTableId("WM_DRUZYNA");
		tmpTeam.id = ++tmpId;
		tmpTeam.teamName = _name;
		
		return tmpTeam;
	}
	
	public static Team createRandomTeamDB()
	{
		//System.out.print("creatRandomTeam\n");
		Team tmpTeam = new Team();
		
		int tmpId = DBUtils.getMaxTableId("WM_DRUZYNA");
		tmpTeam.id = ++tmpId;
		tmpTeam.teamName = "RANDO_TEAM_NAME_" + tmpTeam.id;
		
		return tmpTeam;
	}
	
	public static Team createRandomTeam()
	{
		//System.out.print("creatRandomTeam\n");
		Team tmpTeam = new Team();
		
		int tmpId = DBUtils.getMaxTableId("WM_DRUZYNA");
		tmpTeam.id = ++tmpId;
		tmpTeam.teamName = "RANDO_TEAM_NAME_" + tmpTeam.id;
		int tmpOwnerId = DBUtils.getTableCount("WM_GRACZ");
		tmpTeam.id = tmpOwnerId;
		
		tmpTeam.teamTac = new Tactic();
		
		tmpTeam.volleyballerList.add(Volleyballer.creatRandomVolleyballer(eVolleyballerPosition.ATTACKER, tmpTeam.id));
		tmpTeam.volleyballerList.add(Volleyballer.creatRandomVolleyballer(eVolleyballerPosition.RECIVER, tmpTeam.id));
		tmpTeam.volleyballerList.add(Volleyballer.creatRandomVolleyballer(eVolleyballerPosition.RECIVER, tmpTeam.id));
		tmpTeam.volleyballerList.add(Volleyballer.creatRandomVolleyballer(eVolleyballerPosition.LIBERO, tmpTeam.id));
		tmpTeam.volleyballerList.add(Volleyballer.creatRandomVolleyballer(eVolleyballerPosition.SETTER, tmpTeam.id));
		tmpTeam.volleyballerList.add(Volleyballer.creatRandomVolleyballer(eVolleyballerPosition.BLOCKER, tmpTeam.id));
		tmpTeam.volleyballerList.add(Volleyballer.creatRandomVolleyballer(eVolleyballerPosition.BLOCKER, tmpTeam.id));
		tmpTeam.volleyballerList.add(Volleyballer.creatRandomVolleyballer(eVolleyballerPosition.ATTACKER, tmpTeam.id));
		tmpTeam.volleyballerList.add(Volleyballer.creatRandomVolleyballer(eVolleyballerPosition.RECIVER, tmpTeam.id));
		tmpTeam.volleyballerList.add(Volleyballer.creatRandomVolleyballer(eVolleyballerPosition.RECIVER, tmpTeam.id));
		tmpTeam.volleyballerList.add(Volleyballer.creatRandomVolleyballer(eVolleyballerPosition.SETTER, tmpTeam.id));
		tmpTeam.volleyballerList.add(Volleyballer.creatRandomVolleyballer(eVolleyballerPosition.BLOCKER, tmpTeam.id));
		tmpTeam.volleyballerList.add(Volleyballer.creatRandomVolleyballer(eVolleyballerPosition.BLOCKER, tmpTeam.id));
		tmpTeam.volleyballerList.add(Volleyballer.creatRandomVolleyballer(eVolleyballerPosition.LIBERO, tmpTeam.id));
		
		tmpTeam.servNr = 0;
		/*tmpTeam.setPoints = 0;
		tmpTeam.points = 0;*/
		
		return tmpTeam;
	}
	
	public static Team createTeamAndVolleyballers(String teamName, boolean isBot, int leagueId)
	{
		//System.out.print("creatRandomTeam\n");
		Team tmpTeam = new Team();
		
		int tmpId = DBUtils.getMaxTableId("WM_DRUZYNA");
		tmpTeam.id = ++tmpId;
		//tmpTeam.id = 30;
		if(isBot)
		{
			tmpTeam.teamName = "BOT_TEAM_" + tmpTeam.id;
			tmpTeam.mLeagueId = leagueId;
		}
		else
		{
			tmpTeam.teamName = teamName;
			tmpTeam.mLeagueId = DBUtils.getFreeLeagueId();
			
			DBUtils.deleteBotFromLeague(tmpTeam.mLeagueId, tmpTeam.id);
		}
		tmpTeam.isBot = isBot;
		
		tmpTeam.actualSezon = DBUtils.getActualSezon(tmpTeam.mLeagueId);
		tmpTeam.teamTac = new Tactic();
		
		int attCnt = 2;
		int settCnt = 2;
		int recCnt = 4;
		int blcCnt = 4;
		int libCnt = 2;
		int volleyballerCnt = 0;
		
		while(volleyballerCnt < 16)
		{
			int postInt = GlobalRandom.getInt(5);
			int pktToSpend = 120 + GlobalRandom.getInt(60 - volleyballerCnt/3 * 5);
			//System.out.println("postInt " + postInt + " volleyballerCnt " + volleyballerCnt + " pktToSpend " + pktToSpend);
			switch(postInt)
			{
			case 0:
				if(attCnt > 0 || volleyballerCnt > 13)
				{
					tmpTeam.volleyballerList.add( Volleyballer.createVolleyballer(eVolleyballerPosition.ATTACKER, tmpTeam.id, pktToSpend, volleyballerCnt) );
					attCnt--; volleyballerCnt++;
					break;
				}
				break;
			case 1:
				if(settCnt > 0 || volleyballerCnt > 13)
				{
					tmpTeam.volleyballerList.add( Volleyballer.createVolleyballer(eVolleyballerPosition.SETTER, tmpTeam.id, pktToSpend, volleyballerCnt) );
					settCnt--; volleyballerCnt++;
					break;
				}
				break;
			case 2:
				if(recCnt > 0 || volleyballerCnt > 13)
				{
					tmpTeam.volleyballerList.add( Volleyballer.createVolleyballer(eVolleyballerPosition.RECIVER, tmpTeam.id, pktToSpend, volleyballerCnt) );
					recCnt--; volleyballerCnt++;
					break;
				}
				break;
			case 3:
				if(blcCnt > 0 || volleyballerCnt > 13)
				{
					tmpTeam.volleyballerList.add( Volleyballer.createVolleyballer(eVolleyballerPosition.BLOCKER, tmpTeam.id, pktToSpend, volleyballerCnt) );
					blcCnt--; volleyballerCnt++;
					break;
				}
				break;
			case 4:
				if(libCnt > 0 || volleyballerCnt > 13)
				{
					tmpTeam.volleyballerList.add( Volleyballer.createVolleyballer(eVolleyballerPosition.LIBERO, tmpTeam.id, pktToSpend, volleyballerCnt) );
					libCnt--; volleyballerCnt++;
					break;
				}
				break;
			}
		}
		return tmpTeam;
	}
	
//--------------------------PLAY_MATCH-------------------------
	public Volleyballer getServVolleyballer()
	{
		if(servNr == 0)
			return matchSquad.getSetter();
		else if(servNr == 1)
			return matchSquad.getReceiver2();
		else if(servNr == 2)
			return matchSquad.getBlocker2();
		else if(servNr == 3)
			return matchSquad.getAttacker();
		else if(servNr == 4)
			return matchSquad.getReceiver1();
		else
			return matchSquad.getBlocker1();
	}
	
	public Volleyballer getReciveVolleyballer()
	{
		int result = GlobalRandom.getInt(3);
		if(result == 0)
			return matchSquad.getReceiver1();
		else if(result == 1)
			return matchSquad.getReceiver2();
		else
			return matchSquad.getLibero();
	}

	public Volleyballer getAttackVolleyballer()
	{
		int result = GlobalRandom.getInt(5);
		
		if(result == 0)
		{
			if(servNr == 0 || servNr == 4 || servNr == 5)
				return matchSquad.getReceiver1();
			else
				return matchSquad.getReceiver2();
		}
		else if(result == 1)
			// DRUGA LINIA
		{
			if(servNr == 0 || servNr == 4 || servNr == 5)
				return matchSquad.getReceiver2();
			else
				return matchSquad.getReceiver1();
		}
		else if(result == 2)
			return matchSquad.getAttacker();
		else
		{
			if(servNr == 0 || servNr == 1 || servNr == 5)
				return matchSquad.getBlocker2();
			else
				return matchSquad.getBlocker1();
		}
	}
	
	public Volleyballer getSetVolleyballer()
	{
		return matchSquad.getSetter();
	}
	
	public Volleyballer getBlockVolleyballer() // TODO POWINNO BYC W ZALEZNOSCI OD KIERUNKU ATAKU
	{
		int result = GlobalRandom.getInt(3);
		if(servNr == 0)
		{
			if(result == 0)
				return matchSquad.getAttacker();
			else if(result == 1)
				return matchSquad.getBlocker2();
			else
				return matchSquad.getReceiver2();
		}
		else if(servNr == 1)
		{
			if(result == 0)
				return matchSquad.getReceiver1();
			else if(result == 1)
				return matchSquad.getAttacker();
			else
				return matchSquad.getBlocker2();
		}
		else if(servNr == 2)
		{
			if(result == 0)
				return matchSquad.getBlocker1();
			else if(result == 1)
				return matchSquad.getReceiver1();
			else
				return matchSquad.getAttacker();
		}
		else if(servNr == 3)
		{
			if(result == 0)
				return matchSquad.getSetter();
			else if(result == 1)
				return matchSquad.getBlocker1();
			else
				return matchSquad.getReceiver1();
		}
		else if(servNr == 4)
		{
			if(result == 0)
				return matchSquad.getReceiver2();
			else if(result == 1)
				return matchSquad.getSetter();
			else
				return matchSquad.getBlocker1();
		}
		else
		{
			if(result == 0)
				return matchSquad.getBlocker2();
			else if(result == 1)
				return matchSquad.getReceiver2();
			else
				return matchSquad.getSetter();
		}
	}
	
	//
	
	public boolean addToXmlDoc(Document doc, Element rootElement)
	{
		Element rootTeam = doc.createElement("team");
		rootElement.appendChild(rootTeam);
		
		XMLUtils.addToXMLDoc(doc, rootTeam, "id", "" + id);
		XMLUtils.addToXMLDoc(doc, rootTeam, "name", "" + teamName);
		XMLUtils.addToXMLDoc(doc, rootTeam, "league_id", "" + mLeagueId);
		XMLUtils.addToXMLDoc(doc, rootTeam, "act_sezon", "" + actualSezon);
		
		Element rootVolleyballerList = doc.createElement("volleyballers_list");
		rootTeam.appendChild(rootVolleyballerList);
		System.out.println( "volleyballerList " + volleyballerList + " " +volleyballerList.size());
		for(int i=0; i<volleyballerList.size(); i++)
		{
			volleyballerList.get(i).addToXmlDoc(doc, rootVolleyballerList);
		}
		return true;
	}
	
	public void rotation()
	{
		servNr++;
		if(servNr > 5)
			servNr = 0;
	}
	
	public int getId(){
		return id;
	}
	public void setId(int _id){
		this.id = _id;
	}
	public String getTeamName(){
		return teamName;
	}
	public void setTeamName(String _teamName){
		this.teamName = _teamName;
	}
	public void setTactic(Tactic teamTac) {
		this.teamTac = teamTac;
	}
	public Tactic getTactic() {
		return teamTac;
	}
	public int getSerwNr() {
		return servNr;
	}
	public void setSerwNr(int serwNr) {
		this.servNr = serwNr;
	}
	/*public int getSetPoints() {
		return setPoints;
	}
	public void addSetPoints() {
		setPoints++;
	}
	public int getPoints() {
		return points;
	}
	public void addPoints() {
		points++;
	}
	public void setPoints(int points) {
		this.points = points;
	}*/
	public ArrayList<Volleyballer> getVolleyballerList() {
		return volleyballerList;
	}
	public void setVolleyballerList(ArrayList<Volleyballer> list) {
		this.volleyballerList = list;
	}
	public int getmLeagueId() {
		return mLeagueId;
	}
	public void setLeagueId(int mLeagueId) {
		this.mLeagueId = mLeagueId;
	}
	public boolean isBot() {
		return isBot;
	}
	public void setBot(boolean isBot) {
		this.isBot = isBot;
	}
	public int getActualSezon() {
		return actualSezon;
	}
	public void setActualSezon(int actualSezon) {
		this.actualSezon = actualSezon;
	}
	public TeamMatchSquad getMatchSquad() {
		return matchSquad;
	}
	public void setMatchSquad(TeamMatchSquad matchSquad) {
		this.matchSquad = matchSquad;
	}
}

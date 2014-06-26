package pl.admund.MVM_Client.sportStuff;

import java.util.ArrayList;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import pl.admund.MVM_Client.Volleyballer.VolleyballerClnt;

public class TeamClnt 
{
	private int teamId;
	private String teamName;
	private int leagueId;
	private int actualSeason;

	private ArrayList<VolleyballerClnt> volleyballersList = new ArrayList<VolleyballerClnt>();
	
	/* DEBUG public static TeamClnt creatRandomTeam()
	{
		TeamClnt tmpTeam = new TeamClnt();
		
		tmpTeam.setTeamName("RANDO_TEAM_NAME");
		tmpTeam.setTeamTac(TeamTacticClnt.creatRandomTeamTactic());
		
		tmpTeam.volleyballersList.add(VolleyballerClnt.createVolleyballer(eVolleyballerPosition.ATTACKER, 1, 140 + GlobalRandom.getInt(40), 1));
		tmpTeam.volleyballersList.add(VolleyballerClnt.createVolleyballer(eVolleyballerPosition.ATTACKER, 1, 140 + GlobalRandom.getInt(40), 2));
		tmpTeam.volleyballersList.add(VolleyballerClnt.createVolleyballer(eVolleyballerPosition.RECIVER, 1, 140 + GlobalRandom.getInt(40), 3));
		tmpTeam.volleyballersList.add(VolleyballerClnt.createVolleyballer(eVolleyballerPosition.RECIVER, 1, 140 + GlobalRandom.getInt(40), 4));
		tmpTeam.volleyballersList.add(VolleyballerClnt.createVolleyballer(eVolleyballerPosition.RECIVER, 1, 140 + GlobalRandom.getInt(40), 5));
		tmpTeam.volleyballersList.add(VolleyballerClnt.createVolleyballer(eVolleyballerPosition.RECIVER, 1, 140 + GlobalRandom.getInt(40), 6));
		tmpTeam.volleyballersList.add(VolleyballerClnt.createVolleyballer(eVolleyballerPosition.SETTER, 1, 140 + GlobalRandom.getInt(40), 7));
		tmpTeam.volleyballersList.add(VolleyballerClnt.createVolleyballer(eVolleyballerPosition.SETTER, 1, 140 + GlobalRandom.getInt(40), 8));
		tmpTeam.volleyballersList.add(VolleyballerClnt.createVolleyballer(eVolleyballerPosition.BLOCKER, 1, 140 + GlobalRandom.getInt(40), 9));
		tmpTeam.volleyballersList.add(VolleyballerClnt.createVolleyballer(eVolleyballerPosition.BLOCKER, 1, 140 + GlobalRandom.getInt(40), 10));
		tmpTeam.volleyballersList.add(VolleyballerClnt.createVolleyballer(eVolleyballerPosition.BLOCKER, 1, 140 + GlobalRandom.getInt(40), 11));
		tmpTeam.volleyballersList.add(VolleyballerClnt.createVolleyballer(eVolleyballerPosition.BLOCKER, 1, 140 + GlobalRandom.getInt(40), 12));
		tmpTeam.volleyballersList.add(VolleyballerClnt.createVolleyballer(eVolleyballerPosition.LIBERO, 1, 140 + GlobalRandom.getInt(40), 13));
		tmpTeam.volleyballersList.add(VolleyballerClnt.createVolleyballer(eVolleyballerPosition.LIBERO, 1, 140 + GlobalRandom.getInt(40), 14));
		
		tmpTeam.volleyballersList.add(VolleyballerClnt.createVolleyballer(eVolleyballerPosition.BLOCKER, 1, 140 + GlobalRandom.getInt(40), 15));
		tmpTeam.volleyballersList.add(VolleyballerClnt.createVolleyballer(eVolleyballerPosition.LIBERO, 1, 140 + GlobalRandom.getInt(40), 16));
		tmpTeam.volleyballersList.add(VolleyballerClnt.createVolleyballer(eVolleyballerPosition.LIBERO, 1, 140 + GlobalRandom.getInt(40), 17));
		
		return tmpTeam;
	}*/
	
	public VolleyballerClnt getVolleyballerById(int volleyballerId)
	{
		for(int i=0; i<volleyballersList.size(); i++)
		{
			if(volleyballersList.get(i).getId() == volleyballerId)
				return volleyballersList.get(i);
		}
		return null;
	}
	
	public void parseTeamXML(Node _node)
	{
		NodeList teamChildNods = _node.getChildNodes();
	    for(int i=0; i<teamChildNods.getLength(); i++)
	    {
	    	if(teamChildNods.item(i).getNodeName().equals("id"))
	    	{
	    		teamId = Integer.parseInt( teamChildNods.item(i).getTextContent() );
	    	}
	    	else if(teamChildNods.item(i).getNodeName().equals("name"))
	    	{
	    		teamName = teamChildNods.item(i).getTextContent();
	    	}
	    	else if(teamChildNods.item(i).getNodeName().equals("league_id"))
	    	{
	    		leagueId = Integer.parseInt( teamChildNods.item(i).getTextContent() );
	    	}
	    	else if(teamChildNods.item(i).getNodeName().equals("act_sezon"))
	    	{
	    		actualSeason = Integer.parseInt( teamChildNods.item(i).getTextContent() );
	    	}
	    	else if(teamChildNods.item(i).getNodeName().equals("volleyballers_list"))
	    	{
	    		NodeList volleyballersListChildNods = teamChildNods.item(i).getChildNodes();
	    	    for(int j=0; j<volleyballersListChildNods.getLength(); j++)
	    	    {
	    	    	if(volleyballersListChildNods.item(j).getNodeName().equals("volleyballer"))
	    	    	{
	    	    		VolleyballerClnt tmpVolleyballer = new VolleyballerClnt();
	    	    		tmpVolleyballer.parseVolleyballer(volleyballersListChildNods.item(j));
	    	    		volleyballersList.add(tmpVolleyballer);
	    	    	}
	    	    }
	    	}
	    }
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
	public int getLeagueId() {
		return leagueId;
	}
	public void setLeagueId(int leagueId) {
		this.leagueId = leagueId;
	}
	public int getActualSeason() {
		return actualSeason;
	}
	public void setActualSeason(int actualSeason) {
		this.actualSeason = actualSeason;
	}
	public ArrayList<VolleyballerClnt> getVolleyballersList() {
		return volleyballersList;
	}
	public void setVolleyballersList(ArrayList<VolleyballerClnt> volleyballersList) {
		this.volleyballersList = volleyballersList;
	}
}

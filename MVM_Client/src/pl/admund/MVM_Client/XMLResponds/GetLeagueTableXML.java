package pl.admund.MVM_Client.XMLResponds;

import java.util.ArrayList;
import java.util.Collections;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import android.util.Log;
import pl.admund.MVM_Client.LeagueTable.TeamTableInfoClnt;

public class GetLeagueTableXML extends HTTPRespond
{	
	private ArrayList<TeamTableInfoClnt> teamTableList = new ArrayList<TeamTableInfoClnt>();
	
	public HTTPRespond parseGetLeagueTableXML(Document _doc)
	{
	    NodeList errorList = _doc.getElementsByTagName("error");
	    if(errorList.getLength() != 0)
	    {
	    	NodeList errorTextList = _doc.getElementsByTagName("error_msg");
	        if(errorTextList.getLength() != 0)
	        {
	        	setErrorMSG( errorTextList.item(0).getTextContent() );
	        }
	        
	    	NodeList errorCodeList = _doc.getElementsByTagName("error_code");
	        if(errorCodeList.getLength() != 0)
	        {
	        	Log.d("INT", errorCodeList.item(0).getTextContent());
	        	int errorCode = Integer.parseInt( errorCodeList.item(0).getTextContent() );
	        	setErrorCode( errorCode );
	        }
	        return this;
	    }
	    
	    NodeList childeNodesList = _doc.getChildNodes();
	    //Log.d("CRT_PLR", "childeNodesList.getLength() "+childeNodesList.getLength());
	    if(childeNodesList.item(0).getNodeName().equals("table"))
	    {
	    	NodeList leagueChildeNodesList = childeNodesList.item(0).getChildNodes();
	    	//Log.d("CRT_PLR", "newPlayerChildeNodesList.getLength() "+newPlayerChildeNodesList.getLength());
		    for(int i=0; i<leagueChildeNodesList.getLength(); i++)
		    {
		    	//Log.d("CRT_PLR", newPlayerChildeNodesList.item(i).getNodeName());
		    	if(leagueChildeNodesList.item(i).getNodeName().equals("team"))
		    	{
		    		TeamTableInfoClnt tmpTeamTableInfo = new TeamTableInfoClnt();
		    		NodeList teamChildeNodesList = leagueChildeNodesList.item(i).getChildNodes();
		    		for(int j=0; j<teamChildeNodesList.getLength(); j++)
		    		{
		    			if(teamChildeNodesList.item(j).getNodeName().equals("id"))
		    			{
		    				String teamId = teamChildeNodesList.item(j).getTextContent();
		    				tmpTeamTableInfo.setTeamId(Integer.parseInt(teamId));
		    			}
		    			else if(teamChildeNodesList.item(j).getNodeName().equals("name"))
		    			{
		    				String teamName = teamChildeNodesList.item(j).getTextContent();
		    				tmpTeamTableInfo.setTeamName(teamName);
		    			}
		    			else if(teamChildeNodesList.item(j).getNodeName().equals("points"))
		    			{
		    				String points = teamChildeNodesList.item(j).getTextContent();
		    				tmpTeamTableInfo.setPoints(Integer.parseInt(points));
		    			}
		    			else if(teamChildeNodesList.item(j).getNodeName().equals("match_cnt"))
		    			{
		    				String matchCnt = teamChildeNodesList.item(j).getTextContent();
		    				tmpTeamTableInfo.setMatchCnt(Integer.parseInt(matchCnt));
		    			}
		    			else if(teamChildeNodesList.item(j).getNodeName().equals("match_w"))
		    			{
		    				String matchWin = teamChildeNodesList.item(j).getTextContent();
		    				tmpTeamTableInfo.setMatchWin(Integer.parseInt(matchWin));
		    			}
		    			else if(teamChildeNodesList.item(j).getNodeName().equals("match_l"))
		    			{
		    				String matchLoose = teamChildeNodesList.item(j).getTextContent();
		    				tmpTeamTableInfo.setMatchLoose(Integer.parseInt(matchLoose));
		    			}
		    			else if(teamChildeNodesList.item(j).getNodeName().equals("set_w"))
		    			{
		    				String setWin = teamChildeNodesList.item(j).getTextContent();
		    				tmpTeamTableInfo.setSetWin(Integer.parseInt(setWin));
		    			}
		    			else if(teamChildeNodesList.item(j).getNodeName().equals("set_l"))
		    			{
		    				String setLoose = teamChildeNodesList.item(j).getTextContent();
		    				tmpTeamTableInfo.setSetLoose(Integer.parseInt(setLoose));
		    			}
		    			else if(teamChildeNodesList.item(j).getNodeName().equals("little_w"))
		    			{
		    				String littlePointsWin = teamChildeNodesList.item(j).getTextContent();
		    				tmpTeamTableInfo.setLittleWin(Integer.parseInt(littlePointsWin));
		    			}
		    			else if(teamChildeNodesList.item(j).getNodeName().equals("little_l"))
		    			{
		    				String littlePointsLoose = teamChildeNodesList.item(j).getTextContent();
		    				tmpTeamTableInfo.setLittleLoose(Integer.parseInt(littlePointsLoose));
		    			}
		    		}
		    		tmpTeamTableInfo.computeRatio();
		    		teamTableList.add(tmpTeamTableInfo);
		    	}
		    }
		} 
		Collections.sort(teamTableList);
		Log.d("HTTP", "list size " +teamTableList.size() + " 1 " + teamTableList.get(1));
		return this;
	}
	
	public ArrayList<TeamTableInfoClnt> getTable()
	{
		return teamTableList;
	}
	
	public int getSize()
	{
		return teamTableList.size();
	}
	
	public TeamTableInfoClnt getValueOnPos(int i)
	{
		if(teamTableList.size() > i)
			return teamTableList.get(i);
		return null;
	}
}

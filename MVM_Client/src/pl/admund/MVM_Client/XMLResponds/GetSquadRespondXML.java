package pl.admund.MVM_Client.XMLResponds;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

import pl.admund.MVM_Client.Tactics.TeamMatchSquad;
import android.util.Log;

public class GetSquadRespondXML extends HTTPRespond
{
	//private String[] squadList;
	private TeamMatchSquad teamMatchSquad;
	
	public HTTPRespond parseGetSquadXML(Document _doc)
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
	    if(childeNodesList.item(0).getNodeName().equals("squad"))
	    {
	    	NodeList squadChildeNodesList = childeNodesList.item(0).getChildNodes();
	    	//Log.d("CRT_PLR", "newPlayerChildeNodesList.getLength() "+newPlayerChildeNodesList.getLength());
		    for(int i=0; i<squadChildeNodesList.getLength(); i++)
		    {
		    	//Log.d("CRT_PLR", newPlayerChildeNodesList.item(i).getNodeName());
		    	if(squadChildeNodesList.item(i).getNodeName().equals("squad_list"))
		    	{
		    		String squad = squadChildeNodesList.item(i).getTextContent();
		    		teamMatchSquad = TeamMatchSquad.parseTeamMatchSquad(squad);
		    	}
		    }
		}
		return this;
	}

	public TeamMatchSquad getTeamMatchSquad() {
		return teamMatchSquad;
	}

	public void setTeamMatchSquad(TeamMatchSquad teamMatchSquad) {
		this.teamMatchSquad = teamMatchSquad;
	}
}

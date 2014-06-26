package pl.admund.MVM_Client.XMLResponds;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import android.util.Log;

public class GetPlayerProfileRespondXML extends HTTPRespond
{
	private int userId;
	private String userName;
	private int teamId;
	private String teamName;
	private int leagueId;
	private String leagueName;
	
	public HTTPRespond parseGetPlayerProfileXML(Document _doc)
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
	    if(childeNodesList.item(0).getNodeName().equals("profile"))
	    {
	    	NodeList profileChildeNodesList = childeNodesList.item(0).getChildNodes();
	    	//Log.d("CRT_PLR", "newPlayerChildeNodesList.getLength() "+newPlayerChildeNodesList.getLength());
		    for(int i=0; i<profileChildeNodesList.getLength(); i++)
		    {
    			if(profileChildeNodesList.item(i).getNodeName().equals("uid"))
    				this.setUserId( Integer.parseInt(profileChildeNodesList.item(i).getTextContent()) );
    			else if(profileChildeNodesList.item(i).getNodeName().equals("user_name"))
    				this.setUserName( profileChildeNodesList.item(i).getTextContent() );
    			else if(profileChildeNodesList.item(i).getNodeName().equals("team_id"))
    				this.setTeamId( Integer.parseInt(profileChildeNodesList.item(i).getTextContent()) );
    			else if(profileChildeNodesList.item(i).getNodeName().equals("team_name"))
    				this.setTeamName( profileChildeNodesList.item(i).getTextContent() );
    			else if(profileChildeNodesList.item(i).getNodeName().equals("league_id"))
    				this.setLeagueId( Integer.parseInt(profileChildeNodesList.item(i).getTextContent()) );
    			else if(profileChildeNodesList.item(i).getNodeName().equals("league_name"))
    				this.setLeagueName( profileChildeNodesList.item(i).getTextContent() );
		    }
		} 
		Log.d("HTTP", "userId " + userId);
		return this;
	}
	
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
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
	public String getLeagueName() {
		return leagueName;
	}
	public void setLeagueName(String leagueName) {
		this.leagueName = leagueName;
	}
}

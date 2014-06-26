package pl.admund.MVM_Client.XMLResponds;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import pl.admund.MVM_Client.Tactics.Tactic;
import pl.admund.MVM_Client.Tactics.TeamMatchSquad;
import pl.admund.MVM_Client.main.PlayerAllInfo;
import pl.admund.MVM_Client.sportStuff.StadiumClnt;
import pl.admund.MVM_Client.sportStuff.TeamClnt;
import android.util.Log;

public class LoginRespondXML extends HTTPRespond
{
	public HTTPRespond parseLoginXML(Document _doc)
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
        
        PlayerAllInfo.getInstance().reset();
        
	    NodeList childeNodesList = _doc.getChildNodes();
	    if(childeNodesList.item(0).getNodeName().equals("login"))
	    {
	    	NodeList loginChildeNodesList = childeNodesList.item(0).getChildNodes();
		    for(int i=0; i<loginChildeNodesList.getLength(); i++)
		    {
		    	//Log.d("HTTP LOGIN", "login node: " + loginChildeNodesList.item(i).getNodeName());
		    	if(loginChildeNodesList.item(i).getNodeName().equals("player"))
		    	{
		    		NodeList playerChildeNodesList = loginChildeNodesList.item(i).getChildNodes();
		    		for(int j=0; j<playerChildeNodesList.getLength(); j++)
		    		{
		    			//Log.d("HTTP LOGIN", "player node: " + playerChildeNodesList.item(j).getNodeName());
		    			if(playerChildeNodesList.item(j).getNodeName().equals("id"))
		    				PlayerAllInfo.getInstance().setPlayerId( Integer.parseInt( playerChildeNodesList.item(j).getTextContent() ) );
		    			else if(playerChildeNodesList.item(j).getNodeName().equals("nick"))
		    				PlayerAllInfo.getInstance().setLogin( playerChildeNodesList.item(j).getTextContent() );
		    			else if(playerChildeNodesList.item(j).getNodeName().equals("token"))
		    				PlayerAllInfo.getInstance().setToken( playerChildeNodesList.item(j).getTextContent() );
		    			else if(playerChildeNodesList.item(j).getNodeName().equals("mail"))
		    				PlayerAllInfo.getInstance().setMail( playerChildeNodesList.item(j).getTextContent() );
		    		}
		    	}
		    	else if(loginChildeNodesList.item(i).getNodeName().equals("team"))
		    	{
		    		TeamClnt tmpTeam = new TeamClnt();
		    		tmpTeam.parseTeamXML(loginChildeNodesList.item(i));
		    		PlayerAllInfo.getInstance().setTeam(tmpTeam);
		    	}
		    	else if(loginChildeNodesList.item(i).getNodeName().equals("stadium"))
		    	{
		    		StadiumClnt tmpStadium = new StadiumClnt();
		    		tmpStadium.parseStadium(loginChildeNodesList.item(i));
		    		PlayerAllInfo.getInstance().setStadium(tmpStadium);
		    	}
		    	else if(loginChildeNodesList.item(i).getNodeName().equals("tactic"))
		    	{
		    		String tmpStr = loginChildeNodesList.item(i).getTextContent();
		    		Tactic tmpTactic = Tactic.parseTactic(tmpStr);
		    		PlayerAllInfo.getInstance().setTactic(tmpTactic);
		    	}
		    	else if(loginChildeNodesList.item(i).getNodeName().equals("squad"))
		    	{
		    		String tmpStr = loginChildeNodesList.item(i).getTextContent();
		    		TeamMatchSquad tmpTeamMatchSquad = TeamMatchSquad.parseTeamMatchSquad(tmpStr);
		    		PlayerAllInfo.getInstance().setTeamMatchSquad(tmpTeamMatchSquad);
		    	}
		    }
	    }
	    Log.d("HTTP", "player " +PlayerAllInfo.getInstance().getLogin() + 
	    		"\npass " +PlayerAllInfo.getInstance().getToken() +
	    		"\nteam " + PlayerAllInfo.getInstance().getTeam()+ 
	    		"\nstadium " + PlayerAllInfo.getInstance().getStadium());
        return this;
	}

}

package pl.admund.MVM_Client.XMLResponds;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import pl.admund.MVM_Client.sportStuff.TeamClnt;
import android.util.Log;

public class GetTeamInfoXML  extends HTTPRespond
{
	private TeamClnt teamInfo = new TeamClnt();
	
	public HTTPRespond parseGetTeamInfoXML(Document _doc)
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
	    if(childeNodesList.item(0).getNodeName().equals("team_info"))
	    {
	    	NodeList teamInfoChildeNodesList = childeNodesList.item(0).getChildNodes();
		    for(int i=0; i<teamInfoChildeNodesList.getLength(); i++)
		    {
		    	if(teamInfoChildeNodesList.item(i).getNodeName().equals("team"))
		    	{
		    		teamInfo.parseTeamXML(teamInfoChildeNodesList.item(i));
		    	}
		    }
	    }
        return this;
	}

	public TeamClnt getTeamInfo() {
		return teamInfo;
	}
}

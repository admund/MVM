package pl.admund.MVM_Client.XMLResponds;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

import android.util.Log;

public class GetTacticRespondXML extends HTTPRespond
{
	private String tactic;
	
	public HTTPRespond parseGetTacticRespondXML(Document _doc)
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
	    if(childeNodesList.item(0).getNodeName().equals("tactic_info"))
	    {
	    	NodeList tacticInfoChildeNodesList = childeNodesList.item(0).getChildNodes();
	    	//Log.d("CRT_PLR", "newPlayerChildeNodesList.getLength() "+newPlayerChildeNodesList.getLength());
		    for(int i=0; i<tacticInfoChildeNodesList.getLength(); i++)
		    {
		    	//Log.d("CRT_PLR", newPlayerChildeNodesList.item(i).getNodeName());
		    	if(tacticInfoChildeNodesList.item(i).getNodeName().equals("tactic"))
		    	{
		    		tactic = tacticInfoChildeNodesList.item(i).getTextContent();
		    	}
		    }
		} 
		Log.d("HTTP", "tactic size " + tactic.length());
		return this;
	}

	public String getTactic() {
		return tactic;
	}

	public void setTactic(String tactic) {
		this.tactic = tactic;
	}
}

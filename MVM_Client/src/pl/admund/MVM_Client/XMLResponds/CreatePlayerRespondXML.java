package pl.admund.MVM_Client.XMLResponds;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import android.util.Log;

public class CreatePlayerRespondXML extends HTTPRespond
{
	private int mPlayerId = -1;
	private String mNick = null;
	private String mToken = null;
	private String mMail = null;
	
	public HTTPRespond parseCreatePlayerXML(Document _doc)
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
	    if(childeNodesList.item(0).getNodeName().equals("new_player"))
	    {
	    	NodeList newPlayerChildeNodesList = childeNodesList.item(0).getChildNodes();
	    	//Log.d("CRT_PLR", "newPlayerChildeNodesList.getLength() "+newPlayerChildeNodesList.getLength());
		    for(int i=0; i<newPlayerChildeNodesList.getLength(); i++)
		    {
		    	//Log.d("CRT_PLR", newPlayerChildeNodesList.item(i).getNodeName());
		    	if(newPlayerChildeNodesList.item(i).getNodeName().equals("player"))
		    	{
		    		NodeList playerChildeNodesList = newPlayerChildeNodesList.item(i).getChildNodes();
		    		//Log.d("CRT_PLR", "playerChildeNodesList.getLength() "+playerChildeNodesList.getLength());
		    		for(int j=0; j<playerChildeNodesList.getLength(); j++)
		    		{
		    			if(playerChildeNodesList.item(j).getNodeName().equals("id"))
		    				mPlayerId = Integer.parseInt( playerChildeNodesList.item(j).getTextContent() );
		    			else if(playerChildeNodesList.item(j).getNodeName().equals("nick"))
		    				mNick = playerChildeNodesList.item(j).getTextContent();
		    			else if(playerChildeNodesList.item(j).getNodeName().equals("token"))
		    				mToken = playerChildeNodesList.item(j).getTextContent();
		    			else if(playerChildeNodesList.item(j).getNodeName().equals("mail"))
		    				setMail(playerChildeNodesList.item(j).getTextContent());
		    		}
		    	}
		    }
		} 
		Log.d("HTTP", "id " +mPlayerId + " nick " + mNick+ " token " + mToken);
		return this;
	}
	
	public int getPlayerId() {
		return mPlayerId;
	}
	public void setPlayerId(int mPlayerId) {
		this.mPlayerId = mPlayerId;
	}
	public String getNick() {
		return mNick;
	}
	public void setNick(String mNick) {
		this.mNick = mNick;
	}
	public String getToken() {
		return mToken;
	}
	public void setToken(String mToken) {
		this.mToken = mToken;
	}

	public String getMail() {
		return mMail;
	}

	public void setMail(String mMail) {
		this.mMail = mMail;
	}
}
package pl.admund.MVM_Client.XMLResponds;

import java.util.ArrayList;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import android.util.Log;
import pl.admund.MVM_Client.Message.MessageClnt;

public class GetMessagesRespondXML extends HTTPRespond
{
	private ArrayList<MessageClnt> messageList = new ArrayList<MessageClnt>();
	
	public HTTPRespond parseGetMessagesXML(Document _doc)
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
	    if(childeNodesList.item(0).getNodeName().equals("msg_list"))
	    {
	    	NodeList msgListChildeNodesList = childeNodesList.item(0).getChildNodes();
	    	//Log.d("CRT_PLR", "newPlayerChildeNodesList.getLength() "+newPlayerChildeNodesList.getLength());
		    for(int i=0; i<msgListChildeNodesList.getLength(); i++)
		    {
		    	//Log.d("CRT_PLR", newPlayerChildeNodesList.item(i).getNodeName());
		    	if(msgListChildeNodesList.item(i).getNodeName().equals("msg"))
		    	{
		    		MessageClnt tmpMessage = new MessageClnt();
		    		NodeList msgChildeNodesList = msgListChildeNodesList.item(i).getChildNodes();
		    		//Log.d("CRT_PLR", "playerChildeNodesList.getLength() "+playerChildeNodesList.getLength());
		    		for(int j=0; j<msgChildeNodesList.getLength(); j++)
		    		{
		    			if(msgChildeNodesList.item(j).getNodeName().equals("id"))
		    				tmpMessage.setMsgId( Integer.parseInt(msgChildeNodesList.item(j).getTextContent()) );
		    			else if(msgChildeNodesList.item(j).getNodeName().equals("msg_type"))
		    				tmpMessage.setMsgType( Integer.parseInt(msgChildeNodesList.item(j).getTextContent()) );
		    			else if(msgChildeNodesList.item(j).getNodeName().equals("date"))
		    				tmpMessage.setDate( msgChildeNodesList.item(j).getTextContent() );
		    			else if(msgChildeNodesList.item(j).getNodeName().equals("sender_id"))
		    				tmpMessage.setSenderId( Integer.parseInt(msgChildeNodesList.item(j).getTextContent()) );
		    			else if(msgChildeNodesList.item(j).getNodeName().equals("sender_name"))
		    				tmpMessage.setSender( msgChildeNodesList.item(j).getTextContent() );
		    			else if(msgChildeNodesList.item(j).getNodeName().equals("msg_text"))
		    				tmpMessage.setMsg( msgChildeNodesList.item(j).getTextContent() );
		    		}
		    		tmpMessage.setNew(true);
		    		messageList.add(tmpMessage);
		    	}
		    }
		} 
		Log.d("HTTP", "messageList.size " +messageList.size());
		return this;
	}
	
	public boolean isEmpty(){
		return messageList.isEmpty();
	}
	public int getSize(){
		return messageList.size();
	}
	public ArrayList<MessageClnt> getMessageList(){
		return messageList;
	}
	public MessageClnt getFromPos(int i){
		return messageList.get(i);
	}
}

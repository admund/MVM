package pl.admund.MVM_Servlet.HTTPRespond;

import java.util.ArrayList;
import java.util.HashMap;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import pl.admund.MVM_Servlet.Messages.Message;
import pl.admund.MVM_Servlet.Messages.MessagesDBUtils;
import pl.admund.MVM_Servlet.Player.PlayerDBUtils;
import pl.admund.MVM_Servlet.XML.XMLUtils;
import pl.admund.Constans.ErrorCode;

public class MessagesRespond 
{
	public static String getMsg(HashMap<String, String> _hashMap)
	{
		int playerId = Integer.parseInt(_hashMap.get("pid"));
		String pass = _hashMap.get("pass");
		
		if( !PlayerDBUtils.checkPass(playerId, pass) )
			return XMLUtils.createErrorXML("Wrong pass.", ErrorCode.ERR_WRONG_PASS);
		
		Document tmpDoc = XMLUtils.startXMLDocument("msg_list");
        Element rootElement = (Element)tmpDoc.getFirstChild();
        
		ArrayList<Message> msgList = MessagesDBUtils.getMsgList(playerId);
		for(int i=0; i<msgList.size(); i++)
		{
			msgList.get(i).addToXmlDoc(tmpDoc, rootElement);
		}
		
		return XMLUtils.XMLDocumentToString(tmpDoc);
	}
	
	public static String sendMsg(HashMap<String, String> _hashMap)
	{
		int playerId = Integer.parseInt(_hashMap.get("pid"));
		String pass = _hashMap.get("pass");
		
		if( !PlayerDBUtils.checkPass(playerId, pass) )
			return XMLUtils.createErrorXML("Wrong pass.", ErrorCode.ERR_WRONG_PASS);
		
		int receiverId = Integer.parseInt(_hashMap.get("receiver_id"));
		int msgType = Integer.parseInt(_hashMap.get("msg_type"));
		String msgText = _hashMap.get("msg_txt");
		
		//Message tmpMessage = Message.createNormalMessage(receiverId, playerId, msgText);
		Message tmpMessage = Message.createMessage(receiverId, playerId, msgType, msgText);
		
		if(MessagesDBUtils.addMessage(tmpMessage))
			return XMLUtils.createOkResultXML();
		else
			return XMLUtils.createErrorXML("DataBase error. Can't send message.", ErrorCode.ERR_DB_ERROR);
	}
	
	public static String deleteMsg(HashMap<String, String> _hashMap)
	{
		int playerId = Integer.parseInt(_hashMap.get("pid"));
		String pass = _hashMap.get("pass");
		
		if( !PlayerDBUtils.checkPass(playerId, pass) )
			return XMLUtils.createErrorXML("Wrong pass.", ErrorCode.ERR_WRONG_PASS);
		
		int msgId = Integer.parseInt(_hashMap.get("msg_id"));
		
		if(MessagesDBUtils.delMessage(msgId))
			return XMLUtils.createOkResultXML();
		else
			return XMLUtils.createErrorXML("DataBase error. Can't send message.", ErrorCode.ERR_DB_ERROR);
	}
}
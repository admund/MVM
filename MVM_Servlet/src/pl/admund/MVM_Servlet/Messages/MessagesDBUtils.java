package pl.admund.MVM_Servlet.Messages;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import pl.admund.MVM_Servlet.DBConnect.DBUtils;
import pl.admund.MVM_Servlet.DBConnect.SQLQueryCreator;
import pl.admund.MVM_Servlet.Player.PlayerDBUtils;
import pl.admund.MVM_Servlet.Team.TeamDBUtils;

public class MessagesDBUtils 
{
	public static boolean addMessage(Message _msg)
	{
		String addMessage = SQLQueryCreator.addMsg(_msg);
		
		if(DBUtils.executeUpdate(addMessage) == -1)
			return false;
		
		return true;
	}
	
	public static boolean delMessage(int _msgId)
	{
		String delMessage = "UPDATE WM_WIADOMOSCI SET STATUS=0 WHERE ID=" + _msgId;
		
		if(DBUtils.executeUpdate(delMessage) == -1)
			return false;
		
		return true;
	}
	
	public static ArrayList<Message> getMsgList(int playerId)
	{
		ArrayList<Message> msgList = new ArrayList<Message>();
		String query = "SELECT * FROM WM_WIADOMOSCI WHERE ODBIORCA_ID=" + playerId + " AND STATUS=1";
		ResultSet result = DBUtils.executeQuery(query);
		try
		{
			while(result.next())
			{
				System.out.println("MSG PYK");
				Message tmpMessage = new Message();
				int msgId = result.getInt(1);
				tmpMessage.setId(msgId);
				
				int receiverId = result.getInt(2);
				tmpMessage.setReceiverId(receiverId);
				
				int senderId = result.getInt(3);
				tmpMessage.setSenderId(senderId);
				
				int msgType = result.getInt(4);
				tmpMessage.setMsgType(msgType);
				
				String msgText = result.getString(5);
				tmpMessage.setMsgText(msgText);
				
				String date = result.getString(6);
				tmpMessage.setDate(date);
				
				int status = result.getInt(7);
				tmpMessage.setStatus(status);
				
				msgList.add(tmpMessage);
			}
			result.close();
			
			for(int i=0; i<msgList.size(); i++)
			{
				if(msgList.get(i).getSenderId() == 0)
					msgList.get(i).setSenderName("AVM_SYSTEM");
				else
				{
					String senderName = PlayerDBUtils.getNameFromUid(msgList.get(i).getSenderId());
					if(senderName == null)
						senderName = TeamDBUtils.getTeamNameFromId(msgList.get(i).getSenderId());
					msgList.get(i).setSenderName(senderName);
				}
			}
			return msgList;
		}
		catch (SQLException e)
		{
			System.out.println( "C:"  + e.getClass() + "\nM:" + e.getMessage()  );
			return null;
		}
	}
}

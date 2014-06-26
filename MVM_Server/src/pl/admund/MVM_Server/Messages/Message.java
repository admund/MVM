package pl.admund.MVM_Server.Messages;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import pl.admund.MVM_Server.DBConnect.DBUtils;
import pl.admund.MVM_Server.MatchEngine.MatchInfo;
import pl.admund.MVM_Server.Utils.GlobalDate;
import pl.admund.MVM_Server.XML.XMLUtils;

public class Message
{
	static final int MSG_STATUS_NEW = 1;
	static final int MSG_STATUS_READ = 2;
	static final int MSG_STATUS_DEL = 3;
	
	static final int MSG_TYPE_NORMAL = 1;
	static final int MSG_TYPE_MATCH_REQ = 2;
	static final int MSG_TYPE_MATCH_PROP = 3;
	static final int MSG_TYPE_MATCH_ACCEPT = 4;
	static final int MSG_TYPE_MATCH = 5;
	
	private int id;
	private int msgType;
	private int senderId;
	private String senderName;
	private int receiverId;
	private String msgText;
	private String date;
	private int status;
	
	public static Message createMessage(int receiverId, int senderId, int msgType, String msgText)
	{
		Message tmpMsg = new Message();
		int tmpId = DBUtils.getMaxTableId("WM_WIADOMOSCI") + 1;
		tmpMsg.setId(tmpId);
		tmpMsg.setReceiverId(receiverId);
		tmpMsg.setSenderId(senderId);
		String senderName = DBUtils.getNameFromUid(senderId);
		tmpMsg.setSenderName(senderName);
		tmpMsg.setMsgType(msgType);
		tmpMsg.setDate( GlobalDate.getCurrentDateAndTime() );
		tmpMsg.setMsgText(msgText);
		tmpMsg.setStatus(MSG_STATUS_NEW);
		
		return tmpMsg;
	}
	
	public static Message createNormalMessage(int receiverId, int senderId, String msgText)
	{
		Message tmpMsg = new Message();
		int tmpId = DBUtils.getMaxTableId("WM_WIADOMOSCI") + 1;
		tmpMsg.setId(tmpId);
		tmpMsg.setReceiverId(receiverId);
		tmpMsg.setSenderId(senderId);
		String senderName = DBUtils.getNameFromUid(senderId);
		tmpMsg.setSenderName(senderName);
		tmpMsg.setMsgType(MSG_TYPE_NORMAL);
		tmpMsg.setDate( GlobalDate.getCurrentDateAndTime() );
		tmpMsg.setMsgText(msgText);
		tmpMsg.setStatus(MSG_STATUS_NEW);
		
		return tmpMsg;
	}
	
	public static Message createMatchMessage(int receiverId)
	{
		Message tmpMsg = new Message();
		int tmpId = DBUtils.getMaxTableId("WM_WIADOMOSCI") + 1;
		tmpMsg.setId(tmpId);
		tmpMsg.setReceiverId(receiverId);
		tmpMsg.setSenderId(0);
		tmpMsg.setSenderName("AVM SYSTEM");
		tmpMsg.setMsgType(MSG_TYPE_MATCH);
		tmpMsg.setDate( GlobalDate.getCurrentDateAndTime() );
		tmpMsg.setMsgText("Zaczynasz mecz za mniej niz 30min. Przygotuj sie!");
		tmpMsg.setStatus(MSG_STATUS_NEW);
		
		return tmpMsg;
	}
	
	public static Message createMatchReqMessage(int receiverId, MatchInfo match)
	{
		Message tmpMsg = new Message();
		int tmpId = DBUtils.getMaxTableId("WM_WIADOMOSCI") + 1;
		tmpMsg.setId(tmpId);
		tmpMsg.setReceiverId(receiverId);
		tmpMsg.setSenderId(0);
		tmpMsg.setSenderName("AVM SYSTEM");
		tmpMsg.setMsgType(MSG_TYPE_MATCH_REQ);
		tmpMsg.setDate( GlobalDate.getCurrentDateAndTime() );
		//FORMAT:  MATCH_ID # OPPONENT_ID # OPPONENT_NAME # LEAGUE_NAME # SEASON # ROUND
		String nextMatchMsg = "" + match.getId();
		if(match.getHomeTeamId() == receiverId)
		{
			nextMatchMsg += "#" + match.getAwayTeamId();
			String opponentName = DBUtils.getTeamNameFromId(match.getAwayTeamId());
			nextMatchMsg += "#" + opponentName;
		}
		else
		{
			nextMatchMsg += "#" + match.getHomeTeamId();
			String opponentName = DBUtils.getTeamNameFromId(match.getHomeTeamId());
			nextMatchMsg += "#" + opponentName;
		}
		String leagueName = DBUtils.getLeagueNameFromId(match.getLeagueId());
		nextMatchMsg += "#" + leagueName;
		nextMatchMsg += "#" + match.getSeason();
		nextMatchMsg += "#" + match.getRound();
		
		tmpMsg.setMsgText(nextMatchMsg);
		tmpMsg.setStatus(MSG_STATUS_NEW);
		
		return tmpMsg;
	}
	
	public static Message createMatchPropMessage(int receiverId, MatchInfo match, int propCnt, String terms)
	{
		Message tmpMsg = new Message();
		int tmpId = DBUtils.getMaxTableId("WM_WIADOMOSCI") + 1;
		tmpMsg.setId(tmpId);
		tmpMsg.setReceiverId(receiverId);
		tmpMsg.setSenderId(0);
		tmpMsg.setSenderName("AVM SYSTEM");
		tmpMsg.setMsgType(MSG_TYPE_MATCH_PROP);
		tmpMsg.setDate( GlobalDate.getCurrentDateAndTime() );
		//FORMAT:  MATCH_ID # OPPONENT_ID # OPPONENT_NAME # LEAGUE_NAME # SEASON # ROUND # PROP_CNT # TERMS
		String nextMatchMsg = "" + match.getId();
		if(match.getHomeTeamId() == receiverId)
		{
			//tmpMsg.setSenderId(match.getAwayTeamId());
			nextMatchMsg += "#" + match.getAwayTeamId();
			String opponentName = DBUtils.getTeamNameFromId(match.getAwayTeamId());
			nextMatchMsg += "#" + opponentName;
		}
		else
		{
			//tmpMsg.setSenderId(match.getHomeTeamId());
			nextMatchMsg += "#" + match.getHomeTeamId();
			String opponentName = DBUtils.getTeamNameFromId(match.getHomeTeamId());
			nextMatchMsg += "#" + opponentName;
		}
		String leagueName = DBUtils.getLeagueNameFromId(match.getLeagueId());
		nextMatchMsg += "#" + leagueName;
		nextMatchMsg += "#" + match.getSeason();
		nextMatchMsg += "#" + match.getRound();
		nextMatchMsg += "#" + propCnt;
		nextMatchMsg += "#" + terms;
		
		tmpMsg.setMsgText(nextMatchMsg);
		tmpMsg.setStatus(MSG_STATUS_NEW);
		
		return tmpMsg;
	}
	
	public static Message createMatchAcceptMessage(int receiverId, MatchInfo match, String term)
	{
		Message tmpMsg = new Message();
		int tmpId = DBUtils.getMaxTableId("WM_WIADOMOSCI") + 1;
		tmpMsg.setId(tmpId);
		tmpMsg.setReceiverId(receiverId);
		tmpMsg.setSenderId(0);
		tmpMsg.setSenderName("AVM SYSTEM");
		tmpMsg.setMsgType(MSG_TYPE_MATCH_ACCEPT);
		tmpMsg.setDate( GlobalDate.getCurrentDateAndTime() );
		//FORMAT:  MATCH_ID # OPPONENT_ID # OPPONENT_NAME # LEAGUE_NAME # SEASON # ROUND # TERM
		String nextMatchMsg = "" + match.getId();
		if(match.getHomeTeamId() == receiverId)
		{
			//tmpMsg.setSenderId(match.getAwayTeamId());
			nextMatchMsg += "#" + match.getAwayTeamId();
			String opponentName = DBUtils.getTeamNameFromId(match.getAwayTeamId());
			nextMatchMsg += "#" + opponentName;
		}
		else
		{
			//tmpMsg.setSenderId(match.getHomeTeamId());
			nextMatchMsg += "#" + match.getHomeTeamId();
			String opponentName = DBUtils.getTeamNameFromId(match.getHomeTeamId());
			nextMatchMsg += "#" + opponentName;
		}
		String leagueName = DBUtils.getLeagueNameFromId(match.getLeagueId());
		nextMatchMsg += "#" + leagueName;
		nextMatchMsg += "#" + match.getSeason();
		nextMatchMsg += "#" + match.getRound();
		nextMatchMsg += "#" + term;
		
		tmpMsg.setMsgText(nextMatchMsg);
		tmpMsg.setStatus(MSG_STATUS_NEW);
		
		return tmpMsg;
	}
	
	public boolean addToXmlDoc(Document doc, Element rootElement)
	{
		Element rootStadium = doc.createElement("msg");
		rootElement.appendChild(rootStadium);
		
		XMLUtils.addToXMLDoc(doc, rootStadium, "id", "" + id);
		XMLUtils.addToXMLDoc(doc, rootStadium, "msg_type", "" + msgType);
		XMLUtils.addToXMLDoc(doc, rootStadium, "date", date);
		XMLUtils.addToXMLDoc(doc, rootStadium, "sender_id", "" + senderId);
		XMLUtils.addToXMLDoc(doc, rootStadium, "sender_name", "" + senderName);
		XMLUtils.addToXMLDoc(doc, rootStadium, "msg_text", "" + msgText);
		XMLUtils.addToXMLDoc(doc, rootStadium, "status", "" + status);

		return true;
	}

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getMsgType() {
		return msgType;
	}
	public void setMsgType(int msgType) {
		this.msgType = msgType;
	}
	public int getSenderId() {
		return senderId;
	}
	public void setSenderId(int senderId) {
		this.senderId = senderId;
	}
	public String getSenderName() {
		return senderName;
	}
	public void setSenderName(String senderName) {
		this.senderName = senderName;
	}
	public int getReceiverId() {
		return receiverId;
	}
	public void setReceiverId(int receiverId) {
		this.receiverId = receiverId;
	}
	public String getMsgText() {
		return msgText;
	}
	public void setMsgText(String msgText) {
		this.msgText = msgText;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
}

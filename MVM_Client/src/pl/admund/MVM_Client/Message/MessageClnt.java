package pl.admund.MVM_Client.Message;

public class MessageClnt
{
	static final int MSG_TYPE_NORMAL = 1;
	static final int MSG_TYPE_MATCH_REQ = 2;
	static final int MSG_TYPE_MATCH_PROP = 3;
	static final int MSG_TYPE_MATCH_ACCEPT = 4;
	static final int MSG_TYPE_MATCH = 5;
	
	private int msgId;
	private int msgType;
	private String date;
	private int senderId;
	private String sender;
	private String msg;
	private boolean isNew;
	
	public MessageClnt(){}
	
	public MessageClnt(int _msgId, int _msgType, String _date, String _sender, String _msg, boolean _isNew, int _senderId)
	{
		this.msgId = _msgId;
		this.setMsgType(_msgType);
		this.date = _date;
		this.sender = _sender;
		this.msg = _msg;
		this.isNew = _isNew;
		this.setSenderId(_senderId);
	}
	
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getSender() {
		return sender;
	}
	public void setSender(String sender) {
		this.sender = sender;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public boolean isNew() {
		return isNew;
	}
	public void setNew(boolean isNew) {
		this.isNew = isNew;
	}
	public int getSenderId() {
		return senderId;
	}
	public void setSenderId(int senderId) {
		this.senderId = senderId;
	}
	public int getMsgId() {
		return msgId;
	}
	public void setMsgId(int msgId) {
		this.msgId = msgId;
	}
	public int getMsgType() {
		return msgType;
	}
	public void setMsgType(int msgType) {
		this.msgType = msgType;
	}
}

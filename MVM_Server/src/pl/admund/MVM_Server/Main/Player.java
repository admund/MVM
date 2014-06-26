package pl.admund.MVM_Server.Main;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import pl.admund.MVM_Server.DBConnect.DBUtils;
import pl.admund.MVM_Server.XML.XMLUtils;

public class Player
{
	private int mId;
	private String mNick;
	private String mToken;
	private int mTeamId;

	public static Player createPlayer(String _nickname, String _token, int _teamId)
	{
		//System.out.println("createPlayer " + _name);
		Player tmpPlayer = new Player();
		
		int tmpId = DBUtils.getMaxTableId("WM_GRACZ");
		tmpPlayer.mId = ++tmpId;
		tmpPlayer.mNick = _nickname;
		tmpPlayer.mToken = _token;
		tmpPlayer.setmTeamId(_teamId);
		
		return tmpPlayer;
	}
	
	public static Player createRandomPlayer(int _teamId)
	{
		//System.out.print("creatRandomVolleyballer\n");
		Player tmpPlayer = new Player();
		
		int tmpId = DBUtils.getMaxTableId("WM_GRACZ");
		tmpPlayer.mId = ++tmpId;
		tmpPlayer.mNick = "RAND_NICK";
		tmpPlayer.mToken = "token";
		tmpPlayer.setmTeamId(_teamId);
		
		return tmpPlayer;
	}
	
	public boolean addToXmlDoc(Document doc, Element rootElement)
	{
		Element rootPlayer = doc.createElement("player");
		rootElement.appendChild(rootPlayer);
		
		XMLUtils.addToXMLDoc(doc, rootPlayer, "id", "" + mId);
		XMLUtils.addToXMLDoc(doc, rootPlayer, "nick", "" + mNick);
		XMLUtils.addToXMLDoc(doc, rootPlayer, "token", "" + mToken);

		return true;
	}
	
	public String getToken() {
		return mToken;
	}
	public void setToken(String mToken) {
		this.mToken = mToken;
	}
	public String getNick() {
		return mNick;
	}
	public void setNick(String mNick) {
		this.mNick = mNick;
	}
	public int getId() {
		return mId;
	}
	public void setId(int mId) {
		this.mId = mId;
	}
	public int getTeamId() {
		return mTeamId;
	}
	public void setmTeamId(int mTeamId) {
		this.mTeamId = mTeamId;
	}
}

package pl.admund.MVM_Server.SportStuff;

import org.w3c.dom.Element;
import org.w3c.dom.Document;
import pl.admund.MVM_Server.XML.XMLUtils;

public class Stadium 
{
	private int mTeamId;
	private String mName;
	private byte mFoodUpgLevel;
	private byte mShopUpgLevel;
	private byte mStadiumUpgLevel;
	
	public static Stadium createStadium(String _name, int _teamId)
	{
		//System.out.println("createStadium " + _name);
		Stadium tmpStadium = new Stadium();
		
		tmpStadium.mTeamId = _teamId;
		tmpStadium.mName = _name;
		tmpStadium.mFoodUpgLevel = 0;
		tmpStadium.mShopUpgLevel = 0;
		tmpStadium.mStadiumUpgLevel = 0;
		
		return tmpStadium;
	}
	
	public static Stadium createRandomStadium(int _teamId)
	{
		//System.out.print("creatRandomVolleyballer\n");
		Stadium tmpStadium = new Stadium();
		
		tmpStadium.mTeamId = _teamId;
		tmpStadium.mName = "RAND_NAME";
		tmpStadium.mFoodUpgLevel = 0;
		tmpStadium.mShopUpgLevel = 0;
		tmpStadium.mStadiumUpgLevel = 0;
		
		return tmpStadium;
	}
	
	public boolean addToXmlDoc(Document doc, Element rootElement)
	{
		Element rootStadium = doc.createElement("stadium");
		rootElement.appendChild(rootStadium);
		
		XMLUtils.addToXMLDoc(doc, rootStadium, "name", "" + mName);
		XMLUtils.addToXMLDoc(doc, rootStadium, "food_lv", "" + mFoodUpgLevel);
		XMLUtils.addToXMLDoc(doc, rootStadium, "shop_lv", "" + mShopUpgLevel);
		XMLUtils.addToXMLDoc(doc, rootStadium, "stadium_lv", "" + mStadiumUpgLevel);

		return true;
	}

	public int getmTeamId() {
		return mTeamId;
	}
	public void setmTeamId(int mTeamId) {
		this.mTeamId = mTeamId;
	}
	public String getmName() {
		return mName;
	}
	public void setmName(String mName) {
		this.mName = mName;
	}
	public byte getmFoodUpgLevel() {
		return mFoodUpgLevel;
	}
	public void setmFoodUpgLevel(byte mFoodUpgLevel) {
		this.mFoodUpgLevel = mFoodUpgLevel;
	}
	public byte getmShopUpgLevel() {
		return mShopUpgLevel;
	}
	public void setmShopUpgLevel(byte mShopUpgLevel) {
		this.mShopUpgLevel = mShopUpgLevel;
	}
	public byte getmStadiumUpgLevel() {
		return mStadiumUpgLevel;
	}
	public void setmStadiumUpgLevel(byte mStadiumUpgLevel) {
		this.mStadiumUpgLevel = mStadiumUpgLevel;
	}
}

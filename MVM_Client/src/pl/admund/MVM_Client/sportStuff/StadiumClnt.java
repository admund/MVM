package pl.admund.MVM_Client.sportStuff;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class StadiumClnt
{
	private int mTeamId;
	private String mName;
	private int mFoodUpgLevel;
	private int mShopUpgLevel;
	private int mStadiumUpgLevel;

	public void parseStadium(Node _node)
	{
		NodeList stadiumChildNods = _node.getChildNodes();
	    for(int i=0; i<stadiumChildNods.getLength(); i++)
	    {
	    	if(stadiumChildNods.item(i).getNodeName().equals("name"))
	    	{
	    		mName = stadiumChildNods.item(i).getTextContent();
	    	}
	    	else if(stadiumChildNods.item(i).getNodeName().equals("food_lv"))
	    	{
	    		mFoodUpgLevel = Integer.parseInt( stadiumChildNods.item(i).getTextContent() );
	    	}
	    	else if(stadiumChildNods.item(i).getNodeName().equals("shop_lv"))
	    	{
	    		mShopUpgLevel = Integer.parseInt( stadiumChildNods.item(i).getTextContent() );
	    	}
	    	else if(stadiumChildNods.item(i).getNodeName().equals("stadium_lv"))
	    	{
	    		mStadiumUpgLevel = Integer.parseInt( stadiumChildNods.item(i).getTextContent() );
	    	}
	    }
	}

	public int getTeamId() {
		return mTeamId;
	}
	public void setTeamId(int mTeamId) {
		this.mTeamId = mTeamId;
	}
	public String getName() {
		return mName;
	}
	public void setName(String mName) {
		this.mName = mName;
	}
	public int getFoodUpgLevel() {
		return mFoodUpgLevel;
	}
	public void setFoodUpgLevel(int mFoodUpgLevel) {
		this.mFoodUpgLevel = mFoodUpgLevel;
	}
	public int getShopUpgLevel() {
		return mShopUpgLevel;
	}
	public void setShopUpgLevel(int mShopUpgLevel) {
		this.mShopUpgLevel = mShopUpgLevel;
	}
	public int getStadiumUpgLevel() {
		return mStadiumUpgLevel;
	}
	public void setStadiumUpgLevel(int mStadiumUpgLevel) {
		this.mStadiumUpgLevel = mStadiumUpgLevel;
	}
}

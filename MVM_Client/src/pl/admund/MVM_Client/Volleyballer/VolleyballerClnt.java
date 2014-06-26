package pl.admund.MVM_Client.Volleyballer;

import java.io.Serializable;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import pl.admund.MVM_Client.Utils.GlobalRandom;

public class VolleyballerClnt implements Serializable
{
	private static final long serialVersionUID = 1L;
	public enum eVolleyballerPosition
	{
		ATTACKER, SETTER, RECIVER, BLOCKER, LIBERO
	}
	
	//const attributes
	private int id;
	private String longName;
	private String shortName;
	private int heigh;
	private int weight;
	private int mAge;
	private String nationality;
	private int region;
	private eVolleyballerPosition possition;
	//dynamic attributes
	private int morale;
	private int mSalary;
	private int mContractLength;
	private int trim;
	private int mRating;
	private int mLoyalty;
	private int mSatisfaction;
	private int mSuspToInjury;
	private AttributesClnt mAttributes;
	private int mTeamId;
	
	public void parseVolleyballer(Node _node)
	{
		NodeList volleyballerChildNods = _node.getChildNodes();
	    for(int i=0; i<volleyballerChildNods.getLength(); i++)
	    {
	    	if(volleyballerChildNods.item(i).getNodeName().equals("id"))
	    	{
	    		id = Integer.parseInt( volleyballerChildNods.item(i).getTextContent() );
	    	}
	    	else if(volleyballerChildNods.item(i).getNodeName().equals("long_name"))
	    	{
	    		longName = volleyballerChildNods.item(i).getTextContent();
	    	}
	    	else if(volleyballerChildNods.item(i).getNodeName().equals("short_name"))
	    	{
	    		shortName = volleyballerChildNods.item(i).getTextContent();
	    	}
	    	else if(volleyballerChildNods.item(i).getNodeName().equals("heigh"))
	    	{
	    		heigh = Integer.parseInt( volleyballerChildNods.item(i).getTextContent() );
	    	}
	    	else if(volleyballerChildNods.item(i).getNodeName().equals("weight"))
	    	{
	    		weight = Integer.parseInt( volleyballerChildNods.item(i).getTextContent() );
	    	}
	    	else if(volleyballerChildNods.item(i).getNodeName().equals("nationality"))
	    	{
	    		nationality = volleyballerChildNods.item(i).getTextContent();
	    	}
	    	else if(volleyballerChildNods.item(i).getNodeName().equals("possition"))
	    	{
	    		String str = volleyballerChildNods.item(i).getTextContent();
	    		if(str.equals("ATTACKER"))
	    			possition = eVolleyballerPosition.ATTACKER;
	    		else if(str.equals("SETTER"))
	    			possition = eVolleyballerPosition.SETTER;
	    		else if(str.equals("RECIVER"))
	    			possition = eVolleyballerPosition.RECIVER;
	    		else if(str.equals("BLOCKER"))
	    			possition = eVolleyballerPosition.BLOCKER;
	    		else if(str.equals("LIBERO"))
	    			possition = eVolleyballerPosition.LIBERO;
	    	}
	    	else if(volleyballerChildNods.item(i).getNodeName().equals("morale"))
	    	{
	    		morale = Integer.parseInt( volleyballerChildNods.item(i).getTextContent() );
	    	}
	    	else if(volleyballerChildNods.item(i).getNodeName().equals("salary"))
	    	{
	    		mSalary = Integer.parseInt( volleyballerChildNods.item(i).getTextContent() );
	    	}
	    	else if(volleyballerChildNods.item(i).getNodeName().equals("contract_length"))
	    	{
	    		mContractLength = Integer.parseInt( volleyballerChildNods.item(i).getTextContent() );
	    	}
	    	else if(volleyballerChildNods.item(i).getNodeName().equals("rating"))
	    	{
	    		mRating = Integer.parseInt( volleyballerChildNods.item(i).getTextContent() );
	    	}
	    	else if(volleyballerChildNods.item(i).getNodeName().equals("loyalty"))
	    	{
	    		mLoyalty = Integer.parseInt( volleyballerChildNods.item(i).getTextContent() );
	    	}
	    	else if(volleyballerChildNods.item(i).getNodeName().equals("satisfaction"))
	    	{
	    		mSatisfaction = Integer.parseInt( volleyballerChildNods.item(i).getTextContent() );
	    	}
	    	else if(volleyballerChildNods.item(i).getNodeName().equals("susp_to_injury"))
	    	{
	    		mSuspToInjury = Integer.parseInt( volleyballerChildNods.item(i).getTextContent() );
	    	}
	    	else if(volleyballerChildNods.item(i).getNodeName().equals("trim"))
	    	{
	    		trim = Integer.parseInt( volleyballerChildNods.item(i).getTextContent() );
	    	}
	    	else if(volleyballerChildNods.item(i).getNodeName().equals("attributes"))
	    	{
	    		AttributesClnt tmpAttr = new AttributesClnt();
	    		tmpAttr.parseAttributes(volleyballerChildNods.item(i));
	    		setAttributes(tmpAttr);
	    	}
	    }
	}
	
	public static VolleyballerClnt createVolleyballer(eVolleyballerPosition _possition, int _teamId, int _pktToSpend, int _nr)
	{
		//System.out.print("creatRandomVolleyballer\n");
		VolleyballerClnt tmpVoleyballer = new VolleyballerClnt();
		
		//int tmpId = DBUtils.getTableCount("WM_SIATKARZ");
		//tmpVoleyballer.id = ++tmpId; TODO
		tmpVoleyballer.id = _nr;
		tmpVoleyballer.longName = "RAND ZIOMO"+_nr;
		tmpVoleyballer.shortName = "R. ZIOMO"+_nr;
		tmpVoleyballer.heigh = 180 + GlobalRandom.getInt(40);
		tmpVoleyballer.weight = 70 + GlobalRandom.getInt(40);
		tmpVoleyballer.nationality = "Polska";
		//tmpVoleyballer.region = 1;
		tmpVoleyballer.possition = _possition;
		tmpVoleyballer.morale = GlobalRandom.getInt(100);
		tmpVoleyballer.mSalary = GlobalRandom.getInt(50);
		tmpVoleyballer.mContractLength = (GlobalRandom.getInt(3)+1);
		tmpVoleyballer.mRating = GlobalRandom.getInt(100);
		tmpVoleyballer.mLoyalty = GlobalRandom.getInt(100);
		tmpVoleyballer.mSatisfaction = GlobalRandom.getInt(100);
		tmpVoleyballer.mSuspToInjury = GlobalRandom.getInt(100);
		tmpVoleyballer.trim = GlobalRandom.getInt(100);
		tmpVoleyballer.mTeamId = _teamId;
		tmpVoleyballer.mAttributes = AttributesClnt.createAttributes(_possition, _pktToSpend);
		
		return tmpVoleyballer;
	}
	
	public int getId() {
		return id;
	}
	public String getLongName() {
		return longName;
	}
	public String getShortName() {
		return shortName;
	}
	public int getHeigh() {
		return heigh;
	}
	public int getWeight() {
		return weight;
	}
	public String getNationality() {
		return nationality;
	}
	public int getRegion() {
		return region;
	}
	public eVolleyballerPosition getPossition() {
		return possition;
	}
	public int getMorale() {
		return morale;
	}
	public int getTrim() {
		return trim;
	}
	public AttributesClnt getmAttributes() {
		return mAttributes;
	}
	public int getAtack() {
		return mAttributes.getAtack();
	}
	public int getBlock() {
		return mAttributes.getBlock();
	}
	public int getReception() {
		return mAttributes.getReception();
	}
	public int getRozegranie() {
		return mAttributes.getRozegranie();
	}
	public int getSerwis() {
		return mAttributes.getSerwis();
	}
	public int getTechnique() {
		return mAttributes.getTechnique();
	}
	public int getTemper() {
		return mAttributes.getTemper();
	}
	public int getUstawianie() {
		return mAttributes.getUstawianie();
	}
	public int getIntuition() {
		return mAttributes.getIntuition();
	}
	public int getCreativity() {
		return mAttributes.getCreativity();
	}
	public int getWalecznosc() {
		return mAttributes.getWalecznosc();
	}
	public int getCharisma() {
		return mAttributes.getCharisma();
	}
	public int getStrenght() {
		return mAttributes.getStrenght();
	}
	public int getJumping() {
		return mAttributes.getJumping();
	}
	public int getReflex() {
		return mAttributes.getReflex();
	}
	public int getQuickness() {
		return mAttributes.getQuickness();
	}
	public int getAgility() {
		return mAttributes.getAgility();
	}
	public int getStamina() {
		return mAttributes.getStamina();
	}
	public int getTalent() {
		return mAttributes.getTalent();
	}
	public int getKnownTalent() {
		return mAttributes.getKnownTalent();
	}
	public int getPsychoforce() {
		return mAttributes.getPsychoforce();
	}
	public int getKnownPsychoForce() {
		return mAttributes.getKnownPsychoForce();
	}
	public int getPracowitosc() {
		return mAttributes.getPracowitosc();
	}
	public int getKnownPracowitosc() {
		return mAttributes.getKnownPracowitosc();
	}
	public double getOverall(){
		return mAttributes.getOverall();
	}
	public int getRating() {
		return mRating;
	}
	public int getLoyalty() {
		return mLoyalty;
	}
	public int getSuspToInjury() {
		return mSuspToInjury;
	}
	public int getAge() {
		return mAge;
	}
	public int getSalary() {
		return mSalary;
	}
	public int getContractLength() {
		return mContractLength;
	}
	public int getSatisfaction() {
		return mSatisfaction;
	}
	public int getTeamId() {
		return mTeamId;
	}
	public void setAge(int mAge) {
		this.mAge = mAge;
	}
	public void setSalary(int mSalary) {
		this.mSalary = mSalary;
	}
	public void setContractLength(int mContractLength) {
		this.mContractLength = mContractLength;
	}
	public void setRating(int mRating) {
		this.mRating = mRating;
	}
	public void setLoyalty(int mLoyalty) {
		this.mLoyalty = mLoyalty;
	}
	public void setSatisfaction(int mSatisfaction) {
		this.mSatisfaction = mSatisfaction;
	}
	public void setSuspToInjury(int mSuspToInjury) {
		this.mSuspToInjury = mSuspToInjury;
	}
	public void setTeamId(int mTeamId) {
		this.mTeamId = mTeamId;
	}
	public void setId(int id) {
		this.id = id;
	}
	public void setLongName(String longName) {
		this.longName = longName;
	}
	public void setShortName(String shortName) {
		this.shortName = shortName;
	}
	public void setHeigh(int heigh) {
		this.heigh = heigh;
	}
	public void setWeight(int weight) {
		this.weight = weight;
	}
	public void setNationality(String nationality) {
		this.nationality = nationality;
	}
	public void setRegion(int region) {
		this.region = region;
	}
	public void setPossition(eVolleyballerPosition possition) {
		this.possition = possition;
	}
	public void setMorale(int morale) {
		this.morale = morale;
	}
	public void setTrim(int trim) {
		this.trim = trim;
	}
	public void setAttributes(AttributesClnt mAttributes) {
		this.mAttributes = mAttributes;
	}
}
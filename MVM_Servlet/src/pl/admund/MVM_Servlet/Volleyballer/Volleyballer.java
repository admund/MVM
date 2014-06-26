package pl.admund.MVM_Servlet.Volleyballer;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import pl.admund.MVM_Servlet.DBConnect.DBUtils;
import pl.admund.MVM_Servlet.Utils.GlobalRandom;
import pl.admund.MVM_Servlet.XML.XMLUtils;

public class Volleyballer
{
	public enum eVolleyballerPosition
	{
		ATTACKER, SETTER, RECIVER, BLOCKER, LIBERO
	}
	
	public static eVolleyballerPosition stringToEnumPos(String pos)
	{
		if(pos.equals("ATTACKER"))
			return eVolleyballerPosition.ATTACKER;
		else if(pos.equals("SETTER"))
			return eVolleyballerPosition.SETTER;
		else if(pos.equals("RECIVER"))
			return eVolleyballerPosition.RECIVER;
		else if(pos.equals("BLOCKER"))
			return eVolleyballerPosition.BLOCKER;
		else if(pos.equals("LIBERO"))
			return eVolleyballerPosition.LIBERO;
		return null;
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
	private Attributes mAttributes;
	private int mTeamId;
	
	public static Volleyballer creatRandomVolleyballer(eVolleyballerPosition _possition, int _teamId)
	{
		//System.out.print("creatRandomVolleyballer\n");
		Volleyballer tmpVoleyballer = new Volleyballer();
		
		int tmpId = DBUtils.getTableCount("WM_SIATKARZ");
		tmpVoleyballer.id = ++tmpId;
		tmpVoleyballer.longName = "RAND ZIOMO";
		tmpVoleyballer.shortName = "R. ZIOMO_" + tmpVoleyballer.id;
		tmpVoleyballer.heigh = (short)(180 + GlobalRandom.getInt(40));
		tmpVoleyballer.weight = (short) (70 + GlobalRandom.getInt(40));
		tmpVoleyballer.nationality = "Polska";
		//tmpVoleyballer.region = 1;
		tmpVoleyballer.possition = _possition;
		tmpVoleyballer.morale = (byte)GlobalRandom.getInt(100);
		tmpVoleyballer.mSalary = GlobalRandom.getInt(50);
		tmpVoleyballer.mContractLength = (byte) (GlobalRandom.getInt(3)+1);
		tmpVoleyballer.mRating = (byte)GlobalRandom.getInt(100);
		tmpVoleyballer.mLoyalty = (byte)GlobalRandom.getInt(100);
		tmpVoleyballer.mSatisfaction = (byte)GlobalRandom.getInt(100);
		tmpVoleyballer.mSuspToInjury = (byte)GlobalRandom.getInt(100);
		tmpVoleyballer.trim = (byte)GlobalRandom.getInt(100);
		tmpVoleyballer.mTeamId = _teamId;
		tmpVoleyballer.mAttributes = Attributes.creatRandomAttributes(_possition);
		
		return tmpVoleyballer;
	}
	
	public static Volleyballer createVolleyballer(eVolleyballerPosition _possition, int _teamId, int _pktToSpend, int _cnt)
	{
		//System.out.print("creatRandomVolleyballer\n");
		Volleyballer tmpVoleyballer = new Volleyballer();
		
		int tmpId = DBUtils.getTableCount("WM_SIATKARZ");
		System.out.print("tmpId\n");
		tmpVoleyballer.id = ++tmpId + _cnt;
		//tmpVoleyballer.id = 69;
		tmpVoleyballer.longName = "RAND ZIOMO";
		tmpVoleyballer.shortName = "R. ZIOMO_" + tmpVoleyballer.id;
		if(_possition == eVolleyballerPosition.LIBERO)
			tmpVoleyballer.heigh = 180 + GlobalRandom.getInt(15);
		else if(_possition == eVolleyballerPosition.SETTER)
			tmpVoleyballer.heigh = 185 + GlobalRandom.getInt(20);
		else if(_possition == eVolleyballerPosition.RECIVER || _possition == eVolleyballerPosition.ATTACKER)
			tmpVoleyballer.heigh = 190 + GlobalRandom.getInt(20);
		else if(_possition == eVolleyballerPosition.BLOCKER)
			tmpVoleyballer.heigh = 199 + GlobalRandom.getInt(17);
			
		tmpVoleyballer.weight = 70 + GlobalRandom.getInt(40);
		tmpVoleyballer.nationality = "Polska";
		//tmpVoleyballer.region = 1;
		tmpVoleyballer.possition = _possition;
		tmpVoleyballer.morale = (byte)GlobalRandom.getInt(100);
		tmpVoleyballer.mSalary = GlobalRandom.getInt(50);
		tmpVoleyballer.mContractLength = (byte) (GlobalRandom.getInt(3)+1);
		tmpVoleyballer.mRating = (byte)GlobalRandom.getInt(100);
		tmpVoleyballer.mLoyalty = (byte)GlobalRandom.getInt(100);
		tmpVoleyballer.mSatisfaction = (byte)GlobalRandom.getInt(100);
		tmpVoleyballer.mSuspToInjury = (byte)GlobalRandom.getInt(100);
		tmpVoleyballer.trim = (byte)GlobalRandom.getInt(100);
		tmpVoleyballer.mTeamId = _teamId;
		tmpVoleyballer.mAttributes = Attributes.createAttributes(_possition, _pktToSpend);
		
		return tmpVoleyballer;
	}
	
	public boolean addToXmlDoc(Document doc, Element rootElement)
	{
		Element rootPlayer = doc.createElement("volleyballer");
		rootElement.appendChild(rootPlayer);
		
		XMLUtils.addToXMLDoc(doc, rootPlayer, "id", "" + id);
		XMLUtils.addToXMLDoc(doc, rootPlayer, "long_name", "" + longName);
		XMLUtils.addToXMLDoc(doc, rootPlayer, "short_name", "" + shortName);
		XMLUtils.addToXMLDoc(doc, rootPlayer, "heigh", "" + heigh);
		XMLUtils.addToXMLDoc(doc, rootPlayer, "weight", "" + weight);
		XMLUtils.addToXMLDoc(doc, rootPlayer, "nationality", "" + nationality);
		
		XMLUtils.addToXMLDoc(doc, rootPlayer, "possition", "" + possition);
		XMLUtils.addToXMLDoc(doc, rootPlayer, "morale", "" + morale);
		XMLUtils.addToXMLDoc(doc, rootPlayer, "salary", "" + mSalary);
		XMLUtils.addToXMLDoc(doc, rootPlayer, "contract_length", "" + mContractLength);
		XMLUtils.addToXMLDoc(doc, rootPlayer, "rating", "" + mRating);
		XMLUtils.addToXMLDoc(doc, rootPlayer, "loyalty", "" + mLoyalty);
		
		XMLUtils.addToXMLDoc(doc, rootPlayer, "satisfaction", "" + mSatisfaction);
		XMLUtils.addToXMLDoc(doc, rootPlayer, "susp_to_injury", "" + mSuspToInjury);
		XMLUtils.addToXMLDoc(doc, rootPlayer, "trim", "" + trim);
		
		if(mAttributes != null)
			mAttributes.addToXmlDoc(doc, rootPlayer);

		return true;
	}
	
	public boolean addShortInfoToXmlDoc(Document doc, Element rootElement)
	{
		Element rootPlayer = doc.createElement("volleyballer");
		rootElement.appendChild(rootPlayer);
		
		XMLUtils.addToXMLDoc(doc, rootPlayer, "id", "" + id);
		XMLUtils.addToXMLDoc(doc, rootPlayer, "short_name", "" + shortName);
		
		return true;
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
	public Attributes getAttributes() {
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
	public void setAttributes(Attributes mAttributes) {
		this.mAttributes = mAttributes;
	}
}
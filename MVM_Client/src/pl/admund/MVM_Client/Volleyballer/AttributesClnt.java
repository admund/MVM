package pl.admund.MVM_Client.Volleyballer;

import java.io.Serializable;
import java.text.DecimalFormat;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import pl.admund.MVM_Client.Utils.GlobalRandom;
import pl.admund.MVM_Client.Volleyballer.VolleyballerClnt.eVolleyballerPosition;

public class AttributesClnt implements Serializable
{
	private static final long serialVersionUID = 1L;
	
	//tech attributes
	private int atack;
	private int block;
	private int reception;
	private int rozegranie;
	private int serwis;
	private int technique;
	//psycho attributes
	private int temper;
	private int ustawianie;
	private int intuition;
	private int creativity;
	private int walecznosc;
	private int charisma;
	//physical attributes
	private int strenght;
	private int jumping;
	private int reflex;
	private int quickness;
	private int agility;
	private int stamina;
	//hiden attributes
	private int talent;
	private int knownTalent;
	private int psychoforce;
	private int knownPsychoForce;
	private int pracowitosc;
	private int knownPracowitosc;
	
	public double getOverall()
	{
		double sum = 0;
		sum += atack;
		sum += block;
		sum += reception;
		sum += rozegranie;
		sum += serwis;
		sum += technique;
		//psycho attributes
		sum += temper;
		sum += ustawianie;
		sum += intuition;
		sum += creativity;
		sum += walecznosc;
		sum += charisma;
		//physical attributes
		sum += strenght;
		sum += jumping;
		sum += reflex;
		sum += quickness;
		sum += agility;
		sum += stamina;
		
		return sum/3.6; // 3.6 = 18 * 20 / 100 %
	}
	
	public void parseAttributes(Node _node)
	{
		NodeList attributesChildNods = _node.getChildNodes();
	    for(int i=0; i<attributesChildNods.getLength(); i++)
	    {
	    	if(attributesChildNods.item(i).getNodeName().equals("atack"))
	    	{
	    		atack = Integer.parseInt( attributesChildNods.item(i).getTextContent() );
	    	}
	    	else if(attributesChildNods.item(i).getNodeName().equals("block"))
	    	{
	    		block = Integer.parseInt( attributesChildNods.item(i).getTextContent() );
	    	}
	    	else if(attributesChildNods.item(i).getNodeName().equals("reception"))
	    	{
	    		reception = Integer.parseInt( attributesChildNods.item(i).getTextContent() );
	    	}
	    	else if(attributesChildNods.item(i).getNodeName().equals("rozegranie"))
	    	{
	    		rozegranie = Integer.parseInt( attributesChildNods.item(i).getTextContent() );
	    	}
	    	else if(attributesChildNods.item(i).getNodeName().equals("serwis"))
	    	{
	    		serwis = Integer.parseInt( attributesChildNods.item(i).getTextContent() );
	    	}
	    	else if(attributesChildNods.item(i).getNodeName().equals("technique"))
	    	{
	    		technique = Integer.parseInt( attributesChildNods.item(i).getTextContent() );
	    	}
	    	else if(attributesChildNods.item(i).getNodeName().equals("temper"))
	    	{
	    		temper = Integer.parseInt( attributesChildNods.item(i).getTextContent() );
	    	}
	    	else if(attributesChildNods.item(i).getNodeName().equals("ustawianie"))
	    	{
	    		ustawianie = Integer.parseInt( attributesChildNods.item(i).getTextContent() );
	    	}
	    	else if(attributesChildNods.item(i).getNodeName().equals("intuition"))
	    	{
	    		intuition = Integer.parseInt( attributesChildNods.item(i).getTextContent() );
	    	}
	    	else if(attributesChildNods.item(i).getNodeName().equals("creativity"))
	    	{
	    		creativity = Integer.parseInt( attributesChildNods.item(i).getTextContent() );
	    	}
	    	else if(attributesChildNods.item(i).getNodeName().equals("walecznosc"))
	    	{
	    		walecznosc = Integer.parseInt( attributesChildNods.item(i).getTextContent() );
	    	}
	    	else if(attributesChildNods.item(i).getNodeName().equals("charisma"))
	    	{
	    		charisma = Integer.parseInt( attributesChildNods.item(i).getTextContent() );
	    	}
	    	else if(attributesChildNods.item(i).getNodeName().equals("strenght"))
	    	{
	    		strenght = Integer.parseInt( attributesChildNods.item(i).getTextContent() );
	    	}
	    	else if(attributesChildNods.item(i).getNodeName().equals("jumping"))
	    	{
	    		jumping = Integer.parseInt( attributesChildNods.item(i).getTextContent() );
	    	}
	    	else if(attributesChildNods.item(i).getNodeName().equals("reflex"))
	    	{
	    		reflex = Integer.parseInt( attributesChildNods.item(i).getTextContent() );
	    	}
	    	else if(attributesChildNods.item(i).getNodeName().equals("quickness"))
	    	{
	    		quickness = Integer.parseInt( attributesChildNods.item(i).getTextContent() );
	    	}
	    	else if(attributesChildNods.item(i).getNodeName().equals("agility"))
	    	{
	    		agility = Integer.parseInt( attributesChildNods.item(i).getTextContent() );
	    	}
	    	else if(attributesChildNods.item(i).getNodeName().equals("stamina"))
	    	{
	    		stamina = Integer.parseInt( attributesChildNods.item(i).getTextContent() );
	    	}
	    	else if(attributesChildNods.item(i).getNodeName().equals("talent"))
	    	{
	    		talent = Integer.parseInt( attributesChildNods.item(i).getTextContent() );
	    	}
	    	else if(attributesChildNods.item(i).getNodeName().equals("psychoforce"))
	    	{
	    		psychoforce = Integer.parseInt( attributesChildNods.item(i).getTextContent() );
	    	}
	    	else if(attributesChildNods.item(i).getNodeName().equals("pracowitosc"))
	    	{
	    		pracowitosc = Integer.parseInt( attributesChildNods.item(i).getTextContent() );
	    	}
	    }
	}
	
	public static AttributesClnt createAttributes(eVolleyballerPosition _possition, int pktToSpent)
	{
		int _RAND_ = 4;
		
		AttributesClnt tmpAttributes = new AttributesClnt();
		
		int attrTab[] = new int[18];
		int i = 0;
		while(pktToSpent > 0)
		{
			int tmpInt = 2 + GlobalRandom.getInt(_RAND_);
			attrTab[i % 18] += tmpInt;
			//System.out.print("pktToSpent " + pktToSpent + " tmpInt " + tmpInt + " i " + i);
			if( attrTab[i % 18] > 20)
			{
				int diff = attrTab[i % 18] - 20;
				attrTab[i % 18] = 20;
				pktToSpent += diff;
			}
			pktToSpent -= tmpInt;
			i++;
		}
		
		tmpAttributes.strenght = attrTab[0];
		tmpAttributes.jumping = attrTab[1];
		tmpAttributes.reflex = attrTab[2];
		tmpAttributes.quickness = attrTab[3];
		tmpAttributes.agility = attrTab[4];
		tmpAttributes.stamina = attrTab[5];
		
		tmpAttributes.atack = attrTab[6];
		tmpAttributes.block = attrTab[7];
		tmpAttributes.reception = attrTab[8];
		tmpAttributes.rozegranie = attrTab[9];
		tmpAttributes.serwis = attrTab[10];
		tmpAttributes.technique = attrTab[11];
		
		tmpAttributes.temper = attrTab[12];
		tmpAttributes.ustawianie = attrTab[13];
		tmpAttributes.intuition = attrTab[14];
		tmpAttributes.creativity = attrTab[15];
		tmpAttributes.walecznosc = attrTab[16];
		tmpAttributes.charisma = attrTab[17];
				
		tmpAttributes.talent = tmpAttributes.knownTalent = GlobalRandom.getInt(100);
		tmpAttributes.psychoforce = tmpAttributes.knownPsychoForce = GlobalRandom.getInt(100);
		tmpAttributes.pracowitosc = tmpAttributes.knownPracowitosc = GlobalRandom.getInt(100);
	
		return tmpAttributes;
	}
	
	public int getAtack() {
		return atack;
	}
	public int getBlock() {
		return block;
	}
	public int getReception() {
		return reception;
	}
	public int getRozegranie() {
		return rozegranie;
	}
	public int getSerwis() {
		return serwis;
	}
	public int getTechnique() {
		return technique;
	}
	public int getTemper() {
		return temper;
	}
	public int getUstawianie() {
		return ustawianie;
	}
	public int getIntuition() {
		return intuition;
	}
	public int getCreativity() {
		return creativity;
	}
	public int getWalecznosc() {
		return walecznosc;
	}
	public int getCharisma() {
		return charisma;
	}
	public int getStrenght() {
		return strenght;
	}
	public int getJumping() {
		return jumping;
	}
	public int getReflex() {
		return reflex;
	}
	public int getQuickness() {
		return quickness;
	}
	public int getAgility() {
		return agility;
	}
	public int getStamina() {
		return stamina;
	}
	public int getTalent() {
		return talent;
	}
	public int getKnownTalent() {
		return knownTalent;
	}
	public int getPsychoforce() {
		return psychoforce;
	}
	public int getKnownPsychoForce() {
		return knownPsychoForce;
	}
	public int getPracowitosc() {
		return pracowitosc;
	}
	public int getKnownPracowitosc() {
		return knownPracowitosc;
	}
}

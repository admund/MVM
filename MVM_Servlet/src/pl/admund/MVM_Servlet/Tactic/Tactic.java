package pl.admund.MVM_Servlet.Tactic;

import java.io.StringWriter;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import pl.admund.MVM_Servlet.XML.XMLUtils;

public class Tactic
{
	private boolean teamTacticImportent = false;
	
// TEAM
	private int teamAttackStr;
	private int teamAttackDir;
	
	private int teamSerwisStr;
	private int teamSerwisDir;
	
	private int teamReceptionQuality;
	private int teamReceptionArea;
	
	private int teamBlock3;
	private int teamBlockOption;
	
	private int teamSetStyle;
	private int teamSetPrefer;

// ATTACKER
	private int attAttackStr;
	private int attAttackDir;
	
	private int attSerwisStr;
	private int attSerwisDir;
	
	private int attBlock3;
	private int attBlockOption;
	
// RECIVER 1
	private int rec1AttackStr;
	private int rec1AttackDir;
	
	private int rec1SerwisStr;
	private int rec1SerwisDir;
	
	private int rec1ReceptionQuality;
	private int rec1ReceptionArea;
	
	private int rec1Block3;
	private int rec1BlockOption;
	
// BLOCKER 1
	private int blo1AttackStr;
	private int blo1AttackDir;
	
	private int blo1SerwisStr;
	private int blo1SerwisDir;
	
	private int blo1Block3;
	private int blo1BlockOption;
	
// SETTER 
	private int setSerwisStr;
	private int setSerwisDir;
	
	private int setBlock3;
	private int setBlockOption;
	
	private int setSetStyle;
	private int setSetPrefer;
	
// RECIVER 2
	private int rec2AttackStr;
	private int rec2AttackDir;
	
	private int rec2SerwisStr;
	private int rec2SerwisDir;
	
	private int rec2ReceptionQuality;
	private int rec2ReceptionArea;
	
	private int rec2Block3;
	private int rec2BlockOption;
	
// BLOCKER 2
	private int blo2AttackStr;
	private int blo2AttackDir;
	
	private int blo2SerwisStr;
	private int blo2SerwisDir;
	
	private int blo2Block3;
	private int blo2BlockOption;
	
// LIBERO
	private int libReceptionQuality;
	private int libReceptionArea;
	
	public Tactic(){};
	
	public Tactic(boolean teamTacticImportent, int teamAttackStr, int teamAttackDir, int teamSerwisStr, int teamSerwisDir, 
			int teamReceptionQuality, int teamReceptionArea, int teamBlock3, int teamBlockOption, 
			int teamSetStyle, int teamSetPrefer, 
			int attAttackStr, int attAttackDir, int attSerwisStr, int attSerwisDir, 
			int attBlock3, int attBlockOption, 
			int rec1AttackStr, int rec1AttackDir, int rec1SerwisStr, int rec1SerwisDir, 
			int rec1ReceptionQuality, int rec1ReceptionArea, int rec1Block3, int rec1BlockOption, 
			int blo1AttackStr, int blo1AttackDir, int blo1SerwisStr, int blo1SerwisDir, 
			int blo1Block3, int blo1BlockOption, 
			int setSerwisStr, int setSerwisDir, int setBlock3, int setBlockOption, 
			int setSetStyle, int setSetPrefer, 
			int rec2AttackStr, int rec2AttackDir, int rec2SerwisStr, int rec2SerwisDir, 
			int rec2ReceptionQuality, int rec2ReceptionArea, int rec2Block3, int rec2BlockOption, 
			int blo2AttackStr, int blo2AttackDir, int blo2SerwisStr, int blo2SerwisDir, 
			int blo2Block3, int blo2BlockOption, 
			int libReceptionQuality, int libReceptionArea)
	{
		this.teamTacticImportent = teamTacticImportent;
	
		this.teamAttackStr = teamAttackStr;
		this.teamAttackDir = teamAttackDir;
		
		this.teamSerwisStr = teamSerwisStr;
		this.teamSerwisDir = teamSerwisDir;
		
		this.teamReceptionQuality = teamReceptionQuality;
		this.teamReceptionArea = teamReceptionArea;
		
		this.teamBlock3 = teamBlock3;
		this.teamBlockOption = teamBlockOption;
		
		this.teamSetStyle = teamSetStyle;
		this.teamSetPrefer = teamSetPrefer;

	// ATTACKER
		this.attAttackStr = attAttackStr;
		this.attAttackDir = attAttackDir;
		
		this.attSerwisStr = attSerwisStr;
		this.attSerwisDir = attSerwisDir;
		
		this.attBlock3 = attBlock3;
		this.attBlockOption = attBlockOption;
		
	// RECIVER 1
		this.rec1AttackStr = rec1AttackStr;
		this.rec1AttackDir = rec1AttackDir;
		
		this.rec1SerwisStr = rec1SerwisStr;
		this.rec1SerwisDir = rec1SerwisDir;
		
		this.rec1ReceptionQuality = rec1ReceptionQuality;
		this.rec1ReceptionArea = rec1ReceptionArea;
		
		this.rec1Block3 = rec1Block3;
		this.rec1BlockOption = rec1BlockOption;
		
	// BLOCKER 1
		this.blo1AttackStr = blo1AttackStr;
		this.blo1AttackDir = blo1AttackDir;
		
		this.blo1SerwisStr = blo1SerwisStr;
		this.blo1SerwisDir = blo1SerwisDir;
		
		this.blo1Block3 = blo1Block3;
		this.blo1BlockOption = blo1BlockOption;
		
	// SETTER 
		this.setSerwisStr = setSerwisStr;
		this.setSerwisDir = setSerwisDir;
		
		this.setBlock3 = setBlock3;
		this.setBlockOption = setBlockOption;
		
		this.setSetStyle = setSetStyle;
		this.setSetPrefer = setSetPrefer;
		
	// RECIVER 2
		this.rec2AttackStr = rec2AttackStr;
		this.rec2AttackDir = rec2AttackDir;
		
		this.rec2SerwisStr = rec2SerwisStr;
		this.rec2SerwisDir = rec2SerwisDir;
		
		this.rec2ReceptionQuality = rec2ReceptionQuality;
		this.rec2ReceptionArea = rec2ReceptionArea;
		
		this.rec2Block3 = rec2Block3;
		this.rec2BlockOption = rec2BlockOption;
		
	// BLOCKER 2
		this.blo2AttackStr = blo2AttackStr;
		this.blo2AttackDir = blo2AttackDir;
		
		this.blo2SerwisStr = blo2SerwisStr;
		this.blo2SerwisDir = blo2SerwisDir;
		
		this.blo2Block3 = blo2Block3;
		this.blo2BlockOption = blo2BlockOption;
		
	// LIBERO
		this.libReceptionQuality = libReceptionQuality;
		this.libReceptionArea = libReceptionArea;
	}
	
	public String toString()
	{
		StringWriter str = new StringWriter();
		if(teamTacticImportent)
			str.append("1:");
		else
			str.append("0:");
		
		str.append(""+ teamAttackStr +":");
		str.append(""+ teamAttackDir +":");
		str.append(""+ teamSerwisStr +":");
		str.append(""+ teamSerwisDir +":");
		str.append(""+ teamReceptionQuality +":");
		str.append(""+ teamReceptionArea +":");
		str.append(""+ teamBlock3 +":");
		str.append(""+ teamBlockOption +":");
		str.append(""+ teamSetStyle +":");
		str.append(""+ teamSetPrefer +":");
		
		str.append(""+ attAttackStr +":");
		str.append(""+ attAttackDir +":");
		str.append(""+ attSerwisStr +":");
		str.append(""+ attSerwisDir +":");
		str.append(""+ attBlock3 +":");
		str.append(""+ attBlockOption +":");
		
		str.append(""+ rec1AttackStr +":");
		str.append(""+ rec1AttackDir +":");
		str.append(""+ rec1SerwisStr +":");
		str.append(""+ rec1SerwisDir +":");
		str.append(""+ rec1ReceptionQuality +":");
		str.append(""+ rec1ReceptionArea +":");
		str.append(""+ rec1Block3 +":");
		str.append(""+ rec1BlockOption +":");
		
		str.append(""+ blo1AttackStr +":");
		str.append(""+ blo1AttackDir +":");
		str.append(""+ blo1SerwisStr +":");
		str.append(""+ blo1SerwisDir +":");
		str.append(""+ blo1Block3 +":");
		str.append(""+ blo1BlockOption +":");
		
		str.append(""+ setSerwisStr +":");
		str.append(""+ setSerwisDir +":");
		str.append(""+ setBlock3 +":");
		str.append(""+ setBlockOption +":");
		str.append(""+ setSetStyle +":");
		str.append(""+ setSetPrefer +":");
		
		str.append(""+ rec2AttackStr +":");
		str.append(""+ rec2AttackDir +":");
		str.append(""+ rec2SerwisStr +":");
		str.append(""+ rec2SerwisDir +":");
		str.append(""+ rec2ReceptionQuality +":");
		str.append(""+ rec2ReceptionArea +":");
		str.append(""+ rec2Block3 +":");
		str.append(""+ rec2BlockOption +":");
		
		str.append(""+ blo2AttackStr +":");
		str.append(""+ blo2AttackDir +":");
		str.append(""+ blo2SerwisStr +":");
		str.append(""+ blo2SerwisDir +":");
		str.append(""+ blo2Block3 +":");
		str.append(""+ blo2BlockOption +":");
		
		str.append(""+ libReceptionQuality +":");
		str.append(""+ libReceptionArea);
		
		return str.toString();
	}
	
	public boolean addToXmlDoc(Document doc, Element rootElement)
	{
		XMLUtils.addToXMLDoc(doc, rootElement, "tactic", toString());
		
		return true;
	}
	
	public static Tactic parseString(String str)
	{
		String[] starTab = str.split(":");
		if(starTab.length != 53)
			return null;
		
		boolean teamTacticImportent;
		if(Integer.parseInt(starTab[0]) == 1)
			teamTacticImportent = true;
		else
			teamTacticImportent = false;
		
	// TEAM
		int teamAttackStr = Integer.parseInt(starTab[1]);
		int teamAttackDir = Integer.parseInt(starTab[2]);
		
		int teamSerwisStr = Integer.parseInt(starTab[3]);
		int teamSerwisDir = Integer.parseInt(starTab[4]);
		
		int teamReceptionQuality = Integer.parseInt(starTab[5]);
		int teamReceptionArea = Integer.parseInt(starTab[6]);
		
		int teamBlock3 = Integer.parseInt(starTab[7]);
		int teamBlockOption = Integer.parseInt(starTab[8]);
		
		int teamSetStyle = Integer.parseInt(starTab[9]);
		int teamSetPrefer = Integer.parseInt(starTab[10]);

	// ATTACKER
		int attAttackStr = Integer.parseInt(starTab[11]);
		int attAttackDir = Integer.parseInt(starTab[12]);
		
		int attSerwisStr = Integer.parseInt(starTab[13]);
		int attSerwisDir = Integer.parseInt(starTab[14]);
		
		int attBlock3 = Integer.parseInt(starTab[15]);
		int attBlockOption = Integer.parseInt(starTab[16]);
		
	// RECIVER 1
		int rec1AttackStr = Integer.parseInt(starTab[17]);
		int rec1AttackDir = Integer.parseInt(starTab[18]);
		
		int rec1SerwisStr = Integer.parseInt(starTab[19]);
		int rec1SerwisDir = Integer.parseInt(starTab[20]);
		
		int rec1ReceptionQuality = Integer.parseInt(starTab[21]);
		int rec1ReceptionArea = Integer.parseInt(starTab[22]);
		
		int rec1Block3 = Integer.parseInt(starTab[23]);
		int rec1BlockOption = Integer.parseInt(starTab[24]);
		
	// BLOCKER 1
		int blo1AttackStr = Integer.parseInt(starTab[25]);
		int blo1AttackDir = Integer.parseInt(starTab[26]);
		
		int blo1SerwisStr = Integer.parseInt(starTab[27]);
		int blo1SerwisDir = Integer.parseInt(starTab[28]);
		
		int blo1Block3 = Integer.parseInt(starTab[29]);
		int blo1BlockOption = Integer.parseInt(starTab[30]);
		
	// SETTER 
		int setSerwisStr = Integer.parseInt(starTab[31]);
		int setSerwisDir = Integer.parseInt(starTab[32]);
		
		int setBlock3 = Integer.parseInt(starTab[33]);
		int setBlockOption = Integer.parseInt(starTab[34]);
		
		int setSetStyle = Integer.parseInt(starTab[35]);
		int setSetPrefer = Integer.parseInt(starTab[36]);
		
	// RECIVER 2
		int rec2AttackStr = Integer.parseInt(starTab[37]);
		int rec2AttackDir = Integer.parseInt(starTab[38]);
		
		int rec2SerwisStr = Integer.parseInt(starTab[39]);
		int rec2SerwisDir = Integer.parseInt(starTab[40]);
		
		int rec2ReceptionQuality = Integer.parseInt(starTab[41]);
		int rec2ReceptionArea = Integer.parseInt(starTab[42]);
		
		int rec2Block3 = Integer.parseInt(starTab[43]);
		int rec2BlockOption = Integer.parseInt(starTab[44]);
		
	// BLOCKER 2
		int blo2AttackStr = Integer.parseInt(starTab[45]);
		int blo2AttackDir = Integer.parseInt(starTab[46]);
		
		int blo2SerwisStr = Integer.parseInt(starTab[47]);
		int blo2SerwisDir = Integer.parseInt(starTab[48]);
		
		int blo2Block3 = Integer.parseInt(starTab[49]);
		int blo2BlockOption = Integer.parseInt(starTab[50]);
		
	// LIBERO
		int libReceptionQuality = Integer.parseInt(starTab[51]);
		int libReceptionArea = Integer.parseInt(starTab[52]);
		
		return new Tactic(teamTacticImportent, teamAttackStr, teamAttackDir, teamSerwisStr, teamSerwisDir, 
				teamReceptionQuality, teamReceptionArea, teamBlock3, teamBlockOption, 
				teamSetStyle, teamSetPrefer, 
				attAttackStr, attAttackDir, attSerwisStr, attSerwisDir, 
				attBlock3, attBlockOption, 
				rec1AttackStr, rec1AttackDir, rec1SerwisStr, rec1SerwisDir, 
				rec1ReceptionQuality, rec1ReceptionArea, rec1Block3, rec1BlockOption, 
				blo1AttackStr,  blo1AttackDir, blo1SerwisStr, blo1SerwisDir, 
				blo1Block3, blo1BlockOption, 
				setSerwisStr, setSerwisDir, setBlock3, setBlockOption, 
				setSetStyle, setSetPrefer, 
				rec2AttackStr, rec2AttackDir, rec2SerwisStr, rec2SerwisDir, 
				rec2ReceptionQuality, rec2ReceptionArea, rec2Block3, rec2BlockOption, 
				blo2AttackStr, blo2AttackDir, blo2SerwisStr, blo2SerwisDir, 
				blo2Block3, blo2BlockOption, 
				libReceptionQuality, libReceptionArea);
	}
	
	public static int attStrFromStringToInt(String str)
	{
		if(str.equals("Mocny"))
			return TacticConst.ATTACK_STR_HIGH;
		else if(str.equals("Sredni"))
			return TacticConst.ATTACK_STR_MED;
		else if(str.equals("Lekki"))
			return TacticConst.ATTACK_STR_LOW;
		else 
			return TacticConst.DEFAULT;
	}
	
	public static int attDirFromStringToInt(String str)
	{
		if(str.equals("Po prostej"))
			return TacticConst.ATTACK_DIR_PROSTA;
		else if(str.equals("Skos"))
			return TacticConst.ATTACK_DIR_SKOS;
		else if(str.equals("Kiwka"))
			return TacticConst.ATTACK_DIR_KIWKA;
		else 
			return TacticConst.DEFAULT;
	}
	
	public static int serwisStrFromStringToInt(String str)
	{
		if(str.equals("Mocny"))
			return TacticConst.SERWIS_STR_HIGH;
		else if(str.equals("Sredni"))
			return TacticConst.SERWIS_STR_MED;
		else if(str.equals("Lekki"))
			return TacticConst.SERWIS_STR_LOW;
		else 
			return TacticConst.DEFAULT;
	}
	
	public static int serwisDirFromStringToInt(String str)
	{
		if(str.equals("Przyjmujacy1"))
			return TacticConst.SERWIS_DIR_REC1;
		else if(str.equals("Przyjmujacy2"))
			return TacticConst.SERWIS_DIR_REC2;
		else if(str.equals("Libero"))
			return TacticConst.SERWIS_DIR_LIB;
		else if(str.equals("Do lini"))
			return TacticConst.SERWIS_DIR_LINE;
		else if(str.equals("Miedzy dwoch"))
			return TacticConst.SERWIS_DIR_BEETWEN;
		else if(str.equals("Skrot"))
			return TacticConst.SERWIS_DIR_SKROT;
		else if(str.equals("Bomba"))
			return TacticConst.SERWIS_DIR_BOMBA;
		else 
			return TacticConst.DEFAULT;
	}
	
	public static int recQualityFromStringToInt(String str)
	{
		if(str.equals("Dokladnie"))
			return TacticConst.RECEPTION_Q_GOOD;
		else if(str.equals("Do gory"))
			return TacticConst.RECEPTION_Q_BAD;
		else 
			return TacticConst.DEFAULT;
	}
	
	public static int recAreaFromStringToInt(String str)
	{
		if(str.equals("Wiecej"))
			return TacticConst.RECEPTION_AREA_HIGH;
		else if(str.equals("Mniej"))
			return TacticConst.RECEPTION_AREA_LOW;
		else 
			return TacticConst.DEFAULT;
	}
	
	public static int block3FromStringToInt(String str)
	{
		if(str.equals("Tak"))
			return TacticConst.BLOCK_3_YES;
		else if(str.equals("Nie"))
			return TacticConst.BLOCK_3_NO;
		else 
			return TacticConst.DEFAULT;
	}
	
	public static int blockOptionFromStringToInt(String str)
	{
		if(str.equals("Prosta"))
			return TacticConst.BLOCK_DIR_PROSTA;
		else if(str.equals("Skos"))
			return TacticConst.BLOCK_DIR_SKOS;
		else if(str.equals("Srodek"))
			return TacticConst.BLOCK_DIR_SRODEK;
		else if(str.equals("Intuicja"))
			return TacticConst.BLOCK_DIR_INTUICJA;
		else 
			return TacticConst.DEFAULT;
	}
	
	public static int setStyleFromStringToInt(String str)
	{
		if(str.equals("Prosty"))
			return TacticConst.SET_DIR_SIMPLE;
		else if(str.equals("Kombinacyjny"))
			return TacticConst.SET_DIR_COMBINATIVE;
		else 
			return TacticConst.DEFAULT;
	}
	
	public static int setPrefFromStringToInt(String str)
	{
		if(str.equals("Przyjmujacy1"))
			return TacticConst.SET_PREF_REC1;
		else if(str.equals("Przyjmujacy2"))
			return TacticConst.SET_PREF_REC2;
		else if(str.equals("Srodkowy"))
			return TacticConst.SET_PREF_BLO;
		else if(str.equals("Atakujacy"))
			return TacticConst.SET_PREF_ATT;
		else if(str.equals("Druga linia"))
			return TacticConst.SET_PREF_2;
		else 
			return TacticConst.DEFAULT;
	}
	
	public int getTeamAttackStr() {
		return teamAttackStr;
	}
	public int getTeamAttackDir() {
		return teamAttackDir;
	}
	public int getTeamSerwisStr() {
		return teamSerwisStr;
	}
	public int getTeamSerwisDir() {
		return teamSerwisDir;
	}
	public int getTeamReceptionQuality() {
		return teamReceptionQuality;
	}
	public int getTeamReceptionArea() {
		return teamReceptionArea;
	}
	public int getTeamBlock3() {
		return teamBlock3;
	}
	public int getTeamBlockOption() {
		return teamBlockOption;
	}
	public int getTeamSetStyle() {
		return teamSetStyle;
	}
	public int getTeamSetPrefer() {
		return teamSetPrefer;
	}
	public int getAttAttackStr() {
		return attAttackStr;
	}
	public int getAttAttackDir() {
		return attAttackDir;
	}
	public int getAttSerwisStr() {
		return attSerwisStr;
	}
	public int getAttSerwisDir() {
		return attSerwisDir;
	}
	public int getAttBlock3() {
		return attBlock3;
	}
	public int getAttBlockOption() {
		return attBlockOption;
	}
	public int getRec1AttackStr() {
		return rec1AttackStr;
	}
	public int getRec1AttackDir() {
		return rec1AttackDir;
	}
	public int getRec1SerwisStr() {
		return rec1SerwisStr;
	}
	public int getRec1SerwisDir() {
		return rec1SerwisDir;
	}
	public int getRec1ReceptionQuality() {
		return rec1ReceptionQuality;
	}
	public int getRec1ReceptionArea() {
		return rec1ReceptionArea;
	}
	public int getRec1Block3() {
		return rec1Block3;
	}
	public int getRec1BlockOption() {
		return rec1BlockOption;
	}
	public int getBlo1AttackStr() {
		return blo1AttackStr;
	}
	public int getBlo1AttackDir() {
		return blo1AttackDir;
	}
	public int getBlo1SerwisStr() {
		return blo1SerwisStr;
	}
	public int getBlo1SerwisDir() {
		return blo1SerwisDir;
	}
	public int getBlo1Block3() {
		return blo1Block3;
	}
	public int getBlo1BlockOption() {
		return blo1BlockOption;
	}
	public int getSetSerwisStr() {
		return setSerwisStr;
	}
	public int getSetSerwisDir() {
		return setSerwisDir;
	}
	public int getSetBlock3() {
		return setBlock3;
	}
	public int getSetBlockOption() {
		return setBlockOption;
	}
	public int getSetSetStyle() {
		return setSetStyle;
	}
	public int getSetSetPrefer() {
		return setSetPrefer;
	}
	public int getRec2AttackStr() {
		return rec2AttackStr;
	}
	public int getRec2AttackDir() {
		return rec2AttackDir;
	}
	public int getRec2SerwisStr() {
		return rec2SerwisStr;
	}
	public int getRec2SerwisDir() {
		return rec2SerwisDir;
	}
	public int getRec2ReceptionQuality() {
		return rec2ReceptionQuality;
	}
	public int getRec2ReceptionArea() {
		return rec2ReceptionArea;
	}
	public int getRec2Block3() {
		return rec2Block3;
	}
	public int getRec2BlockOption() {
		return rec2BlockOption;
	}
	public int getBlo2AttackStr() {
		return blo2AttackStr;
	}
	public int getBlo2AttackDir() {
		return blo2AttackDir;
	}
	public int getBlo2SerwisStr() {
		return blo2SerwisStr;
	}
	public int getBlo2SerwisDir() {
		return blo2SerwisDir;
	}
	public int getBlo2Block3() {
		return blo2Block3;
	}
	public int getBlo2BlockOption() {
		return blo2BlockOption;
	}
	public int getLibReceptionQuality() {
		return libReceptionQuality;
	}
	public int getLibReceptionArea() {
		return libReceptionArea;
	}
	public boolean isTeamTacticImportent() {
		return teamTacticImportent;
	}
	public void setTeamTacticImportent(boolean teamTacticImportent) {
		this.teamTacticImportent = teamTacticImportent;
	}
}

package pl.admund.MVM_Server.SportStuff;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import pl.admund.MVM_Server.XML.XMLUtils;

public class TeamTableInfo
{
	private int teamId;
	private String teamName;
	private int points;
	private int matchCnt;
	private int matchWin;
	private int matchLoose;
	private int setWin;
	private int setLoose;
	private double setRatio;
	private int littleWin;
	private int littleLoose;
	private double littleRatio;
	
	public boolean addToXmlDoc(Document doc, Element rootElement)
	{
		Element rootTeamInfo = doc.createElement("team");
		rootElement.appendChild(rootTeamInfo);
		
		XMLUtils.addToXMLDoc(doc, rootTeamInfo, "id", "" + teamId);
		XMLUtils.addToXMLDoc(doc, rootTeamInfo, "name", "" + teamName);
		XMLUtils.addToXMLDoc(doc, rootTeamInfo, "points", "" + points);
		XMLUtils.addToXMLDoc(doc, rootTeamInfo, "match_cnt", "" + matchCnt);
		XMLUtils.addToXMLDoc(doc, rootTeamInfo, "match_w", "" + matchWin);
		XMLUtils.addToXMLDoc(doc, rootTeamInfo, "match_l", "" + matchLoose);
		XMLUtils.addToXMLDoc(doc, rootTeamInfo, "set_w", "" + setWin);
		XMLUtils.addToXMLDoc(doc, rootTeamInfo, "set_l", "" + setLoose);
		XMLUtils.addToXMLDoc(doc, rootTeamInfo, "little_w", "" + littleWin);
		XMLUtils.addToXMLDoc(doc, rootTeamInfo, "little_l", "" + littleLoose);

		return true;
	}
	
	public void computeRatio()
	{
		this.setRatio = (double)setWin/(double)setLoose;
		this.littleRatio = (double)littleWin/(double)littleLoose;
	}

	public int getTeamId() {
		return teamId;
	}
	public void setTeamId(int teamId) {
		this.teamId = teamId;
	}
	public String getTeamName() {
		return teamName;
	}
	public void setTeamName(String teamName) {
		this.teamName = teamName;
	}
	public int getPoints() {
		return points;
	}
	public void setPoints(int points) {
		this.points = points;
	}
	public int getMatchCnt() {
		return matchCnt;
	}
	public void setMatchCnt(int matchCnt) {
		this.matchCnt = matchCnt;
	}
	public int getMatchWin() {
		return matchWin;
	}
	public void setMatchWin(int matchWin) {
		this.matchWin = matchWin;
	}
	public int getMatchLoose() {
		return matchLoose;
	}
	public void setMatchLoose(int matchLoose) {
		this.matchLoose = matchLoose;
	}
	public int getSetWin() {
		return setWin;
	}
	public void setSetWin(int setWin) {
		this.setWin = setWin;
	}
	public int getSetLoose() {
		return setLoose;
	}
	public void setSetLoose(int setLoose) {
		this.setLoose = setLoose;
	}
	public double getSetRatio() {
		return setRatio;
	}
	public void setSetRatio(double setRation) {
		this.setRatio = setRation;
	}
	public int getLittleWin() {
		return littleWin;
	}
	public void setLittleWin(int littleWin) {
		this.littleWin = littleWin;
	}
	public int getLittleLoose() {
		return littleLoose;
	}
	public void setLittleLoose(int littleLoose) {
		this.littleLoose = littleLoose;
	}
	public double getLittleRatio() {
		return littleRatio;
	}
	public void setLittleRatio(double littleRation) {
		this.littleRatio = littleRation;
	}
}

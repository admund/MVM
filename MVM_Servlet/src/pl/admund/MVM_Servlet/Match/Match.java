package pl.admund.MVM_Servlet.Match;

import java.sql.Timestamp;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import pl.admund.MVM_Servlet.DBConnect.DBUtils;
import pl.admund.MVM_Servlet.Utils.GlobalRandom;
import pl.admund.MVM_Servlet.XML.XMLUtils;

public class Match 
{
	public static final int STATUS_CREATED = 0;
	public static final int STATUS_NEED_SEND_REQ = 1;
	public static final int STATUS_REQ_SENDED = 2;
	public static final int STATUS_TERMS_ACCEPT = 3;
	public static final int STATUS_MATCH_START = 4;
	public static final int STATUS_MATCH_END = 5;
	
	private int id;
	private int leagueId;
	private int season;
	private int round;
	private int homeTeamId;
	private int awayTeamId;
	private int homeSetWon;
	private int awaySetWon;
	private Timestamp date;
	private String littlePts;
	private int status;
	
	public static Match createMatch(int leagueId, int season, int homeTeamId, int awayTeamId, int round)
	{
		Match tmpMatch = new Match();
		int tmpId = DBUtils.getMaxTableId("WM_MECZ") + 1;
		tmpMatch.setId(tmpId);
		tmpMatch.setLeagueId(leagueId);
		tmpMatch.setSeason(season);
		tmpMatch.setHomeTeamId(homeTeamId);
		tmpMatch.setAwayTeamId(awayTeamId);
		tmpMatch.setRound(round);
		tmpMatch.setStatus(0);
		
		return tmpMatch;
	}
	
	@SuppressWarnings("deprecation")
	public static Match createRandomMatch(int leagueId, int season, int homeTeamId, int awayTeamId, int round)
	{
		Match tmpMatch = new Match();
		int tmpId = DBUtils.getMaxTableId("WM_MECZ") + 1;
		tmpMatch.setId(tmpId);
		tmpMatch.setLeagueId(leagueId);
		tmpMatch.setSeason(season);
		tmpMatch.setHomeTeamId(homeTeamId);
		tmpMatch.setAwayTeamId(awayTeamId);
		tmpMatch.setRound(round);
		
		tmpMatch.setDate(new Timestamp(2012, 03, 20, 15, 46, 34, 00));
		tmpMatch.setStatus(0);
		
		addFakeResult(tmpMatch);
		
		System.out.println("hw: " +tmpMatch.getHomeSetWon() + " aw: " +tmpMatch.getAwaySetWon() + " L: " + tmpMatch.getLittlePts());
		return tmpMatch;
	}
	
	public static void addFakeResult(Match _match)
	//public static void addFakeResult()
	{
		int homeSetWon = 0;
		int awaySetWon = 0;
		String littlePointResult = "";
		while(homeSetWon < 3 && awaySetWon < 3)
		{
			if(homeSetWon + awaySetWon != 0)
				littlePointResult += ", ";
		
			if( GlobalRandom.getBoolean())
			{
				if(homeSetWon + awaySetWon < 4)
					littlePointResult += "25:" + (14 + GlobalRandom.getInt(10));
				else
					littlePointResult += "15:" + (4 + GlobalRandom.getInt(10));
				homeSetWon++;
			}
			else
			{
				if(homeSetWon + awaySetWon < 4)
					littlePointResult += (14 + GlobalRandom.getInt(10)) + ":25";
				else
					littlePointResult += (4 + GlobalRandom.getInt(10) + ":15");
				awaySetWon++;
			}
		}
		_match.setHomeSetWon(homeSetWon);
		_match.setAwaySetWon(awaySetWon);
		_match.setLittlePts(littlePointResult);
		_match.setStatus(5);
		//System.out.println("hw: " +homeSetWon + " aw: " +awaySetWon + " L: " + littlePointResult);
	}
	
	public boolean addToXmlDoc(Document doc, Element rootElement)
	{
		Element rootTeam = doc.createElement("match");
		rootElement.appendChild(rootTeam);
		
		XMLUtils.addToXMLDoc(doc, rootTeam, "id", "" + id);
		XMLUtils.addToXMLDoc(doc, rootTeam, "league_id", "" + leagueId);
		XMLUtils.addToXMLDoc(doc, rootTeam, "season", "" + season);
		XMLUtils.addToXMLDoc(doc, rootTeam, "round", "" + round);
		XMLUtils.addToXMLDoc(doc, rootTeam, "homeTeamId", "" + homeTeamId);
		XMLUtils.addToXMLDoc(doc, rootTeam, "awayTeamId", "" + awayTeamId);
		XMLUtils.addToXMLDoc(doc, rootTeam, "homeSetWon", "" + homeSetWon);
		XMLUtils.addToXMLDoc(doc, rootTeam, "awaySetWon", "" + awaySetWon);
		XMLUtils.addToXMLDoc(doc, rootTeam, "date", "" + date);
		XMLUtils.addToXMLDoc(doc, rootTeam, "littlePts", "" + littlePts);
		XMLUtils.addToXMLDoc(doc, rootTeam, "status", "" + status);
		
		return true;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getLeagueId() {
		return leagueId;
	}
	public void setLeagueId(int leagueId) {
		this.leagueId = leagueId;
	}
	public int getSeason() {
		return season;
	}
	public void setSeason(int season) {
		this.season = season;
	}	
	public int getRound() {
		return round;
	}
	public void setRound(int round) {
		this.round = round;
	}
	public int getHomeTeamId() {
		return homeTeamId;
	}
	public void setHomeTeamId(int homeTeamId) {
		this.homeTeamId = homeTeamId;
	}
	public int getAwayTeamId() {
		return awayTeamId;
	}
	public void setAwayTeamId(int awayTeamId) {
		this.awayTeamId = awayTeamId;
	}
	public int getHomeSetWon() {
		return homeSetWon;
	}
	public void setHomeSetWon(int homeSetWon) {
		this.homeSetWon = homeSetWon;
	}
	public int getAwaySetWon() {
		return awaySetWon;
	}
	public void setAwaySetWon(int awaySetWon) {
		this.awaySetWon = awaySetWon;
	}
	public Timestamp getDate() {
		return date;
	}
	public void setDate(Timestamp date) {
		this.date = date;
	}
	@SuppressWarnings("deprecation")
	public String getExactDate(){
		return ""+date.getYear()+"/"+(date.getMonth()+1)+"/"+date.getDate();
	}
	public String getLittlePts() {
		return littlePts;
	}
	public void setLittlePts(String littlePts) {
		this.littlePts = littlePts;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}

}

package pl.admund.MVM_Server.DBConnect;

import pl.admund.MVM_Server.Main.Player;
import pl.admund.MVM_Server.MatchEngine.MatchInfo;
import pl.admund.MVM_Server.Messages.Message;
import pl.admund.MVM_Server.SportStuff.League;
import pl.admund.MVM_Server.SportStuff.Stadium;
import pl.admund.MVM_Server.Tactic.Tactic;
import pl.admund.MVM_Server.Team.Team;
import pl.admund.MVM_Server.Team.TeamTactic;
import pl.admund.MVM_Server.Volleyballer.Attributes;
import pl.admund.MVM_Server.Volleyballer.Volleyballer;

public class SQLQueryCreator
{
	public static String addPlayer(Player _player)
	{
		String query = "INSERT INTO WM_GRACZ VALUES (";
		query += _player.getId()+", ";
		query += "'"+_player.getNick()+"', ";
		query += "'"+_player.getToken()+"',";
		query += +_player.getTeamId()+")";
		
		return query;
	}
	
	public static String addTeam(Team _team)
	{
		String query = "INSERT INTO WM_DRUZYNA VALUES (";
		query += _team.getId()+", ";
		query += "'"+_team.getTeamName()+"', ";
		if(_team.isBot())
			query += "1, ";
		else
			query += "0, ";
		query += ""+_team.getmLeagueId()+")";

		return query;
	}
	
	public static String addStadium(Stadium _stadium)
	{
		String query = "INSERT INTO WM_STADION VALUES (";
		query += "'"+_stadium.getmName()+"', ";
		query += _stadium.getmTeamId()+", ";
		query += ""+_stadium.getmFoodUpgLevel()+", ";
		query += ""+_stadium.getmShopUpgLevel()+", ";
		query += ""+_stadium.getmStadiumUpgLevel()+")";
		
		return query;
	}
	
	public static String addVolleyballer(Volleyballer _volleyballer)
	{
		String query = "INSERT INTO WM_SIATKARZ VALUES (";
		query += _volleyballer.getId()+", ";
		query += "'"+_volleyballer.getLongName()+"', ";
		query += "'"+_volleyballer.getShortName()+"', ";
		query += "null, "; //foto
		query += _volleyballer.getHeigh()+", ";
		query += _volleyballer.getWeight()+", ";
		query += _volleyballer.getAge()+", ";
		query += "'"+_volleyballer.getNationality()+"', ";
		query += "'"+_volleyballer.getPossition()+"', ";
		query += _volleyballer.getMorale()+", ";
		query += _volleyballer.getSalary()+", ";
		query += _volleyballer.getContractLength()+", ";
		query += _volleyballer.getLoyalty()+", ";
		query += _volleyballer.getSuspToInjury()+", ";
		query += _volleyballer.getTeamId()+", ";
		query += _volleyballer.getTrim()+")";
		
		return query;
	}
	
	public static String addAttributes(Attributes _attributes, int _volleyballerId)
	{
		String query = "INSERT INTO WM_ATRYBUTY VALUES (";

		query += _attributes.getAtack()+", ";
		query += _attributes.getBlock()+", ";
		query += _attributes.getReception()+", ";
		query += _attributes.getRozegranie()+", ";
		query += _attributes.getSerwis()+", ";
		query += _attributes.getTechnique()+", ";
		query += _attributes.getTalent()+", ";
		
		query += _attributes.getTemper()+", ";
		query += _attributes.getUstawianie()+", ";
		query += _attributes.getIntuition()+", ";
		query += _attributes.getCreativity()+", ";
		query += _attributes.getWalecznosc()+", ";
		query += _attributes.getCharisma()+", ";
		query += _attributes.getPsychoforce()+", ";
		
		query += _attributes.getStrenght()+", ";
		query += _attributes.getJumping()+", ";
		query += _attributes.getQuickness()+", ";
		query += _attributes.getReflex()+", ";
		query += _attributes.getAgility()+", ";
		query += _attributes.getStamina()+", ";
		query += _attributes.getPracowitosc()+", ";
		query += _volleyballerId+")";
		
		return query;
	}
	
	public static String addTeamTactic(TeamTactic _teamTactic)
	{
		String query = null;
		
		return query;
	}
	
	public static String addLeague(League _League)
	{
		String query = "INSERT INTO WM_LIGA VALUES (";
		
		query += _League.getId() + ", ";
		query += "'" + _League.getName() + "', ";
		query += _League.getLevel() + ", ";
		query += _League.getSezon() + ")";
		
		return query;
	}
	
	public static String addSquad(int teamId, String[] squadList)
	{
		String query = "INSERT INTO WM_SKLAD VALUES (";
	
		query += squadList[0] + ", ";
		query += squadList[1] + ", ";
		query += squadList[2] + ", ";
		query += squadList[3] + ", ";
		query += squadList[4] + ", ";
		query += squadList[5] + ", ";
		query += squadList[6] + ", ";
		
		query += squadList[7] + ", ";
		query += squadList[8] + ", ";
		query += squadList[9] + ", ";
		query += squadList[10] + ", ";
		query += squadList[11] + ", ";
		
		query += teamId + ")";

		return query;
	}
	
	public static String updateSquad(int teamId, String[] squadList)
	{
		String query = "UPDATE WM_SKLAD SET ";
	
		query += "ATAKUJACY=" + squadList[0] + ", ";
		query += "PRZYJMUJACY1=" + squadList[1] + ", ";
		query += "SRODKOWY1=" + squadList[2] + ", ";
		query += "ROZGRYWAJACY=" + squadList[3] + ", ";
		query += "PRZYJMUJACY2=" + squadList[4] + ", ";
		query += "SRODKOWY2=" + squadList[5] + ", ";
		query += "LIBERO=" + squadList[6] + ", ";
		
		query += "ATAKUJACY_REZ=" + squadList[7] + ", ";
		query += "PRZYJMUJACY1_REZ=" + squadList[8] + ", ";
		query += "SRODKOWY1_REZ=" + squadList[9] + ", ";
		query += "ROZGRYWAJACY_REZ=" + squadList[10] + ", ";
		query += "PRZYJMUJACY2_REZ=" + squadList[11] + " ";
		
		query += "WHERE DRUZYNA_ID=" + teamId;

		return query;
	}
	
	public static String addMatch(MatchInfo _match)
	{
		String query = "INSERT INTO WM_MECZ(ID, LIGA_ID, SEZON, GOSPODARZ_ID, GOSC_ID, KOLEJKA) VALUES ( ";
		
		query += _match.getId() + ", ";
		query += _match.getLeagueId() + ", ";
		query += _match.getSeason() + ", ";
		query += _match.getHomeTeamId() + ", ";
		query += _match.getAwayTeamId() + ", ";
		query += _match.getRound() + ", ";
		query += _match.getStatus() + ")";
		return query;
	}
	
	public static String updateMatch(MatchInfo _match)
	{
		String query = "UPDATE WM_MECZ SET ";
		
		query += "GOSPODARZ_SET=" + _match.getHomeSetWon() + ", ";
		query += "GOSC_SET=" + _match.getAwaySetWon() + ", ";
		query += "MALE_PKT='" + _match.getLittlePts() + "', ";
		query += "STATUS=" + _match.getStatus() + " ";
		
		query += "WHERE ID=" + _match.getId();

		return query;
	}
	
	public static String updateMatch(int matchId, int homeSetWon, int awaySetWon, String littlePts)
	{
		String query = "UPDATE WM_SKLAD SET ";
		
		query += "GOSPODARZ_SET=" + homeSetWon + ", ";
		query += "GOSC_SET=" + awaySetWon + ", ";
		query += "MALE_PKT='" + littlePts + "', ";
		query += "STATUS=" + MatchInfo.STATUS_MATCH_END + " ";
		
		query += "WHERE ID=" + matchId;

		return query;
	}
	
	public static String addMatchRandResult(MatchInfo _match)
	{
		String query = "INSERT INTO WM_MECZ VALUES ( ";
		
		query += _match.getId() + ", ";
		query += _match.getLeagueId() + ", ";
		query += _match.getSeason() + ", ";
		query += _match.getHomeTeamId() + ", ";
		query += _match.getAwayTeamId() + ", ";
		query += _match.getHomeSetWon() + ", '";
		query += _match.getLittlePts() + "', ";
		query += _match.getDate() + ", ";
		query += _match.getAwaySetWon() + ", ";
		query += _match.getRound() + ", ";
		query += _match.getStatus() + ")";
		
		return query;
	}
	
	public static String addMsg(Message _msg)
	{
		String query = "INSERT INTO WM_WIADOMOSCI VALUES ( ";
		
		query += _msg.getId() + ", ";
		query += _msg.getReceiverId() + ", ";
		query += _msg.getSenderId() + ", ";
		query += _msg.getMsgType() + ", '";
		query += _msg.getMsgText() + "', '";
		query += _msg.getDate() + "', ";
		query += _msg.getStatus() + ")";
		
		return query;
	}
	
	public static String addTactic(int teamId, Tactic _tactic)
	{
		String query = "INSERT INTO WM_TAKTYKA VALUES ( ";
		
		query += teamId + ", ";
		query += _tactic.getTeamAttackStr() + ", ";
		query += _tactic.getTeamAttackDir() + ", ";
		query += _tactic.getTeamSerwisStr()+ ", ";
		query += _tactic.getTeamSerwisDir() + ", ";
		query += _tactic.getTeamReceptionQuality() + ", ";
		query += _tactic.getTeamReceptionArea() + ", ";
		query += _tactic.getTeamBlock3() + ", ";
		query += _tactic.getTeamBlockOption() + ", ";
		query += _tactic.getTeamSetStyle() + ", ";
		query += _tactic.getTeamSetPrefer() + ", ";
		
		query += _tactic.getAttAttackStr() + ", ";
		query += _tactic.getAttAttackDir() + ", ";
		query += _tactic.getAttSerwisStr()+ ", ";
		query += _tactic.getAttSerwisDir() + ", ";
		query += _tactic.getAttBlock3() + ", ";
		query += _tactic.getAttBlockOption() + ", ";
		
		query += _tactic.getRec1AttackStr() + ", ";
		query += _tactic.getRec1AttackDir() + ", ";
		query += _tactic.getRec1SerwisStr() + ", ";
		query += _tactic.getRec1SerwisDir() + ", ";
		query += _tactic.getRec1ReceptionQuality() + ", ";
		query += _tactic.getRec1ReceptionArea() + ", ";
		query += _tactic.getRec1Block3() + ", ";
		query += _tactic.getRec1BlockOption() + ", ";
		
		query += _tactic.getBlo1AttackStr() + ", ";
		query += _tactic.getBlo1AttackDir() + ", ";
		query += _tactic.getBlo1SerwisStr()+ ", ";
		query += _tactic.getBlo1SerwisDir() + ", ";
		query += _tactic.getBlo1Block3() + ", ";
		query += _tactic.getBlo1BlockOption() + ", ";
		
		query += _tactic.getSetSerwisStr() + ", ";
		query += _tactic.getSetSerwisDir() + ", ";
		query += _tactic.getSetBlock3() + ", ";
		query += _tactic.getSetBlockOption() + ", ";
		query += _tactic.getSetSetStyle() + ", ";
		query += _tactic.getSetSetPrefer() + ", ";
		
		query += _tactic.getRec2AttackStr() + ", ";
		query += _tactic.getRec2AttackDir() + ", ";
		query += _tactic.getRec2SerwisStr() + ", ";
		query += _tactic.getRec2SerwisDir() + ", ";
		query += _tactic.getRec2ReceptionQuality() + ", ";
		query += _tactic.getRec2ReceptionArea() + ", ";
		query += _tactic.getRec2Block3() + ", ";
		query += _tactic.getRec2BlockOption() + ", ";
		
		query += _tactic.getBlo2AttackStr() + ", ";
		query += _tactic.getBlo2AttackDir() + ", ";
		query += _tactic.getBlo2SerwisStr()+ ", ";
		query += _tactic.getBlo2SerwisDir() + ", ";
		query += _tactic.getBlo2Block3() + ", ";
		query += _tactic.getBlo2BlockOption() + ", ";
		
		query += _tactic.getLibReceptionQuality() + ", ";
		query += _tactic.getLibReceptionArea() + ", ";
		
		if(_tactic.isTeamTacticImportent())
			query += "1)";
		else
			query += "0)";
		
		return query;
	}
	
	public static String updateTactic(int teamId, Tactic _tactic)
	{
		String query = "UPDATE WM_TAKTYKA SET ";
		
		query += "ATAK_SILA_DRUZYNA=" + _tactic.getTeamAttackStr() + ", ";
		query += "ATAK_KIERUNEK_DRUZYNA=" + _tactic.getTeamAttackDir() + ", ";
		query += "SERWIS_SILA_DRUZYNA=" + _tactic.getTeamSerwisStr()+ ", ";
		query += "SERWIS_KIERUNEK_DRUZYNA=" + _tactic.getTeamSerwisDir() + ", ";
		query += "PRZYJECIE_JAKOSC_DRUZYNA=" + _tactic.getTeamReceptionQuality() + ", ";
		query += "PRZYJECIE_OBSZAR_DRUZYNA=" + _tactic.getTeamReceptionArea() + ", ";
		query += "BLOK_NA_3_DRUZYNA=" + _tactic.getTeamBlock3() + ", ";
		query += "BLOK_OPCJA_DRUZYNA=" + _tactic.getTeamBlockOption() + ", ";
		query += "ROZEGRANIE_STYL_DRUZYNA=" + _tactic.getTeamSetStyle() + ", ";
		query += "ROZEGRANIE_OPCJA_DRUZYNA=" + _tactic.getTeamSetPrefer() + ", ";
		
		query += "ATAK_SILA_ATAKUJACY=" + _tactic.getAttAttackStr() + ", ";
		query += "ATAK_KIERUNEK_ATAKUJACY=" + _tactic.getAttAttackDir() + ", ";
		query += "SERWIS_SILA_ATAKUJACY=" + _tactic.getAttSerwisStr()+ ", ";
		query += "SERWIS_KIERUNEK_ATAKUJACY=" + _tactic.getAttSerwisDir() + ", ";
		query += "BLOK_NA_3_ATAKUJACY=" + _tactic.getAttBlock3() + ", ";
		query += "BLOK_OPCJA_ATAKUJACY=" + _tactic.getAttBlockOption() + ", ";
		
		query += "ATAK_SILA_PRZYJMUJACY1=" + _tactic.getRec1AttackStr() + ", ";
		query += "ATAK_KIERUNEK_PRZYJMUJACY1=" + _tactic.getRec1AttackDir() + ", ";
		query += "SERWIS_SILA_PRZYJMUJACY1=" + _tactic.getRec1SerwisStr() + ", ";
		query += "SERWIS_KIERUNEK_PRZYJMUJACY1=" + _tactic.getRec1SerwisDir() + ", ";
		query += "PRZYJECIE_JAKOSC_PRZYJMUJACY1=" + _tactic.getRec1ReceptionQuality() + ", ";
		query += "PRZYJECIE_OBSZAR_PRZYJMUJACY1=" + _tactic.getRec1ReceptionArea() + ", ";
		query += "BLOK_NA_3_PRZYJMUJACY1=" + _tactic.getRec1Block3() + ", ";
		query += "BLOK_OPCJA_PRZYJMUJACY1=" + _tactic.getRec1BlockOption() + ", ";
		
		query += "ATAK_SILA_SRODKOWY1=" + _tactic.getBlo1AttackStr() + ", ";
		query += "ATAK_KIERUNEK_SRODKOWY1=" + _tactic.getBlo1AttackDir() + ", ";
		query += "SERWIS_SILA_SRODKOWY1=" + _tactic.getBlo1SerwisStr()+ ", ";
		query += "SERWIS_KIERUNEK_SRODKOWY1=" + _tactic.getBlo1SerwisDir() + ", ";
		query += "BLOK_NA_3_SRODKOWY1=" + _tactic.getBlo1Block3() + ", ";
		query += "BLOK_OPCJA_SRODKOWY1=" + _tactic.getBlo1BlockOption() + ", ";
		
		query += "SERWIS_SILA_ROZGRYWAJACY=" + _tactic.getSetSerwisStr() + ", ";
		query += "SERWIS_KIERUNEK_ROZGRYWAJACY=" + _tactic.getSetSerwisDir() + ", ";
		query += "BLOK_NA_3_ROZGRYWAJACY=" + _tactic.getSetBlock3() + ", ";
		query += "BLOK_OPCJA_ROZGRYWJACY=" + _tactic.getSetBlockOption() + ", ";
		query += "ROZEGRANIE_STYL_ROZGRYWAJACY=" + _tactic.getSetSetStyle() + ", ";
		query += "ROZEGRANIE_OPCJA_ROZGRYWAJACY=" + _tactic.getSetSetPrefer() + ", ";
		
		query += "ATAK_SILA_PRZYJMUJACY2=" + _tactic.getRec2AttackStr() + ", ";
		query += "ATAK_KIERUNEK_PRZYJMUJACY2=" + _tactic.getRec2AttackDir() + ", ";
		query += "SERWIS_SILA_PRZYJMUJACY2=" + _tactic.getRec2SerwisStr() + ", ";
		query += "SERWIS_KIERUNEK_PRZYJMUJACY2=" + _tactic.getRec2SerwisDir() + ", ";
		query += "PRZYJECIE_JAKOSC_PRZYJMUJACY2=" + _tactic.getRec2ReceptionQuality() + ", ";
		query += "PRZYJECIE_OBSZAR_PRZYJMUJACY2=" + _tactic.getRec2ReceptionArea() + ", ";
		query += "BLOK_NA_3_PRZYJMUJACY2=" + _tactic.getRec2Block3() + ", ";
		query += "BLOK_OPCJA_PRZYJMUJACY2=" + _tactic.getRec2BlockOption() + ", ";
		
		query += "ATAK_SILA_SRODKOWY2=" + _tactic.getBlo2AttackStr() + ", ";
		query += "ATAK_KIERUNEK_SRODKOWY2=" + _tactic.getBlo2AttackDir() + ", ";
		query += "SERWIS_SILA_SRODKOWY2=" + _tactic.getBlo2SerwisStr()+ ", ";
		query += "SERWIS_KIERUNEK_SRODKOWY2=" + _tactic.getBlo2SerwisDir() + ", ";
		query += "BLOK_NA_3_SRODKOWY2=" + _tactic.getBlo2Block3() + ", ";
		query += "BLOK_OPCJA_SRODKOWY2=" + _tactic.getBlo2BlockOption() + ", ";
		
		query += "PRZYJECIE_JAKOSC_LIBERO=" + _tactic.getLibReceptionQuality() + ", ";
		query += "PRZYJECIE_OBSZAR_LIBERO=" + _tactic.getLibReceptionArea() + ", ";
		
		if(_tactic.isTeamTacticImportent())
			query += "TAKTYKA_DRUZYNY_WAZNIEJSZA=1 ";
		else
			query += "TAKTYKA_DRUZYNY_WAZNIEJSZA=0 ";
		
		query += "WHERE ID=" + teamId;
		
		return query;
	}
}
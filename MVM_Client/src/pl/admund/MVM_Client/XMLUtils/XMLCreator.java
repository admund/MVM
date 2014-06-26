package pl.admund.MVM_Client.XMLUtils;

import java.util.ArrayList;
import java.util.List;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import pl.admund.Security.HTTPSecurity;
import pl.admund.MVM_Client.main.PlayerAllInfo;

public class XMLCreator 
{
	static public String createLoginURL(String _mail, String _pass)
	{
		String tmp = "cmd=login&mail=" + _mail + "&pass=" + _pass;
		return HTTPSecurity.encrypt(tmp);
	}
	
	static public String createCrtPlrURL(String _login, String _pass, String _mail)
	{
		String tmp = "cmd=crt_plr&login=" + _login + "&pass=" + _pass + "&mail=" + _mail;
		return HTTPSecurity.encrypt(tmp);
	}
	
	static public String createGetMsgURL()
	{
		String tmp = "cmd=get_msg&pid=" + PlayerAllInfo.getInstance().getPlayerId() + "&pass=" + PlayerAllInfo.getInstance().getToken();
		return HTTPSecurity.encrypt(tmp);
	}
	
	static public String createSendMsgURL(int receiverId, int msgType, String msgText)
	{
		String tmp = "cmd=send_msg&pid=" + PlayerAllInfo.getInstance().getPlayerId() + "&pass=" + PlayerAllInfo.getInstance().getToken() 
					+ "&receiver_id=" + receiverId + "&msg_type=" + msgType + "&msg_txt=" + msgText;
		return HTTPSecurity.encrypt(tmp);
	}
	
	static public String createDelMsgURL(int msgId)
	{
		String tmp = "cmd=del_msg&pid=" + PlayerAllInfo.getInstance().getPlayerId() + "&pass=" + PlayerAllInfo.getInstance().getToken() 
					+ "&msg_id=" + msgId;
		return HTTPSecurity.encrypt(tmp);
	}
	
	static public String createGetPlayerProfileURL(int uid)
	{
		String tmp = "cmd=plr_prof&pid=" + PlayerAllInfo.getInstance().getPlayerId() + "&pass=" + PlayerAllInfo.getInstance().getToken() 
					+ "&uid=" + uid;
		return HTTPSecurity.encrypt(tmp);
	}
	
	static public String createGetLeagueTableURL(int leagueId)
	{
		String tmp = "cmd=get_tab&pid=" + PlayerAllInfo.getInstance().getPlayerId() + "&pass=" + PlayerAllInfo.getInstance().getToken() 
					+ "&league_id=" + leagueId;
		return HTTPSecurity.encrypt(tmp);
	}
	
//-------------------MATCH_PROP_ACCCEPT_NEXT----------------------------
	static public String createMatchPropURL(int matchId, int propCnt, String terms)
	{
		String tmp = "cmd=match_prop&pid=" + PlayerAllInfo.getInstance().getPlayerId() + "&pass=" + PlayerAllInfo.getInstance().getToken() 
				+ "&match_id=" + matchId +"&prop_cnt=" + propCnt + "&terms=" + terms;
		
		return HTTPSecurity.encrypt(tmp);
	}
	
	static public String createMatchAcceptURL(int matchId, String term)
	{
		String tmp = "cmd=match_acc&pid=" + PlayerAllInfo.getInstance().getPlayerId() + "&pass=" + PlayerAllInfo.getInstance().getToken() 
				+ "&match_id=" + matchId + "&term=" + term;
		
		return HTTPSecurity.encrypt(tmp);
	}
	
	static public String createGetNextMatchURL()
	{
		String tmp = "cmd=match_nxt&pid=" + PlayerAllInfo.getInstance().getPlayerId() + "&pass=" + PlayerAllInfo.getInstance().getToken();
		
		return HTTPSecurity.encrypt(tmp);
	}

//------------------------------TEAM_INFO---------------------------------
	static public String createGetTeamInfoURL(int teamId)
	{
		String tmp = "cmd=team_info&pid=" + PlayerAllInfo.getInstance().getPlayerId() + "&pass=" + PlayerAllInfo.getInstance().getToken()
				+ "&team_id=" + teamId;
		
		return HTTPSecurity.encrypt(tmp);
	}
	
//-----------------------------------SQUAD-------------------------------------------------------
	static public String createGetSquadURL(int teamId)
	{
		String tmp = "cmd=get_sqd&pid=" + PlayerAllInfo.getInstance().getPlayerId() + "&pass=" + PlayerAllInfo.getInstance().getToken()
				+ "&team_id=" + teamId;
		return HTTPSecurity.encrypt(tmp);
	}
	
	static public List<NameValuePair> createSetSquadURL(ArrayList<Integer> _squad)
	{
		String tmp = "cmd=set_sqd&pid=" + PlayerAllInfo.getInstance().getPlayerId() + "&pass=" + PlayerAllInfo.getInstance().getToken() 
					+ "&squad=";
		for(int i=0; i<_squad.size(); i++)
		{
			if(i == (_squad.size()-1) )
				tmp += _squad.get(i).intValue();
			else
				tmp += _squad.get(i).intValue() + ",";
		}
		String q = HTTPSecurity.encrypt(tmp);
		
		/*List<NameValuePair> pairs = new ArrayList<NameValuePair>();
		pairs.add(new BasicNameValuePair("cmd", "set_sqd"));
		pairs.add(new BasicNameValuePair("pid", "" + PlayerAllInfo.getInstance().getPlayerId()));
		pairs.add(new BasicNameValuePair("pass", PlayerAllInfo.getInstance().getToken()));
		pairs.add(new BasicNameValuePair("squad", "" + teamId));*/
		
		List<NameValuePair> pairs = new ArrayList<NameValuePair>();
		pairs.add(new BasicNameValuePair("q", q));
		
		return pairs;
	}
	
//-----------------------------------TACITIC-------------------------------------------------------
	static public String createGetTeamTacticURL(int teamId)
	{
		String tmp = "cmd=get_tac&pid=" + PlayerAllInfo.getInstance().getPlayerId() + "&pass=" + PlayerAllInfo.getInstance().getToken()
				+ "&team_id=" + teamId;
		
		return HTTPSecurity.encrypt(tmp);
	}
	
	static public List<NameValuePair> createSetTeamTacticURL(int teamId, String tactic)
	{
		String tmp = "cmd=get_tac&pid=" + PlayerAllInfo.getInstance().getPlayerId() + "&pass=" + PlayerAllInfo.getInstance().getToken()
				+ "&team_id=" + teamId + "&tactic=" + tactic;
		
		String q = HTTPSecurity.encrypt(tmp);
		
		/*List<NameValuePair> pairs = new ArrayList<NameValuePair>();
		pairs.add(new BasicNameValuePair("cmd", "set_tac"));
		pairs.add(new BasicNameValuePair("pid", "" + PlayerAllInfo.getInstance().getPlayerId()));
		pairs.add(new BasicNameValuePair("pass", PlayerAllInfo.getInstance().getToken()));
		pairs.add(new BasicNameValuePair("team_id", "" + teamId));
		pairs.add(new BasicNameValuePair("tactic", tactic));*/
		
		List<NameValuePair> pairs = new ArrayList<NameValuePair>();
		pairs.add(new BasicNameValuePair("q", q));
		
		return pairs;
	}
}

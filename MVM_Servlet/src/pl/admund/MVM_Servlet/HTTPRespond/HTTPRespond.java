package pl.admund.MVM_Servlet.HTTPRespond;

import java.util.HashMap;
import pl.admund.MVM_Servlet.XML.XMLUtils;
import pl.admund.Constans.ErrorCode;

public class HTTPRespond
{
	private final static String CREATE_PLAYER_CMD = "crt_plr";
	private final static String DELETE_PLAYER_CMD = "del_plr";
	private final static String LOGIN_CMD = "login";
	private final static String SET_SQUAD = "set_sqd";
	private final static String GET_SQUAD = "get_sqd";
	private final static String GET_TABLE = "get_tab";
	private final static String GET_MSG = "get_msg";
	private final static String SEND_MSG = "send_msg";
	private final static String DEL_MSG = "del_msg";
	private final static String GET_PROFILE = "plr_prof";
	private final static String MATCH_PROP = "match_prop";
	private final static String MATCH_ACCEPT = "match_acc";
	private final static String MATCH_NEXT = "match_nxt";
	private final static String TEAM_INFO = "team_info";
	private final static String GET_TEAM_TACTIC = "get_tac";
	private final static String SET_TEAM_TACTIC = "set_tac";
	private final static String ACTIVITATION_LINK = "activ";
	
	/**
	 * @param _params
	 * @return
	 */
	public static String createRespondString(String _params)
	{
		if(_params != null)
		{
			HashMap<String, String> hashMap = readParams(_params);
			if(hashMap == null)
			{
				System.out.println( "hashMap " + hashMap);
				return XMLUtils.createErrorXML("Player not found.", ErrorCode.ERR_PLAYER_NOT_FOUND);
			}
			
			String cmd = hashMap.get("cmd");
			System.out.println("cmd=_" + cmd + "_");
			
			if(cmd.equals(CREATE_PLAYER_CMD))
			{
				return PlayerRespond.createNewPlayer(hashMap);
			}
			else if(cmd.equals(DELETE_PLAYER_CMD))
			{
				return PlayerRespond.deletePlayer(hashMap);
			}
			else if(cmd.equals(LOGIN_CMD))
			{
				return PlayerRespond.login(hashMap);
			}
			else if(cmd.equals(SET_SQUAD))
			{
				return SquadRespond.setSquad(hashMap);
			}
			else if(cmd.equals(GET_SQUAD))
			{
				return SquadRespond.getSquad(hashMap);
			}
			else if(cmd.equals(GET_TABLE))
			{
				return TableRespond.getTable(hashMap);
			}
			else if(cmd.equals(GET_MSG))
			{
				return MessagesRespond.getMsg(hashMap);
			}
			else if(cmd.equals(SEND_MSG))
			{
				return MessagesRespond.sendMsg(hashMap);
			}
			else if(cmd.equals(DEL_MSG))
			{
				return MessagesRespond.deleteMsg(hashMap);
			}
			else if(cmd.equals(GET_PROFILE))
			{
				return PlayerRespond.getPlayerProfile(hashMap);
			}
			else if(cmd.equals(ACTIVITATION_LINK))
			{
				return PlayerRespond.checkActivitationLink(hashMap);
			}
			else if(cmd.equals(MATCH_PROP))
			{
				return MatchArrangeRespond.matchProp(hashMap);
			}
			else if(cmd.equals(MATCH_ACCEPT))
			{
				return MatchArrangeRespond.matchAccept(hashMap);
			}
			else if(cmd.equals(MATCH_NEXT))
			{
				return MatchArrangeRespond.matchNext(hashMap);
			}
			else if(cmd.equals(TEAM_INFO))
			{
				return TeamRespond.getTeamInfo(hashMap);
			}
			else if(cmd.equals(GET_TEAM_TACTIC))
			{
				return TacticRespond.getTactic(hashMap);
			}
			else if(cmd.equals(SET_TEAM_TACTIC))
			{
				return TacticRespond.setTactic(hashMap);
			}
			else
			{
				XMLUtils.createErrorXML("CMD: " + cmd + " - not found.", ErrorCode.ERR_NO_PARAMS);
			}
		}
		return XMLUtils.createErrorXML("There is no params.", ErrorCode.ERR_NO_PARAMS);
	}
	
	private static HashMap<String, String> readParams(String _params)
	{
		HashMap<String, String> resultMap = new HashMap<String, String>();
		
		String paramsTab[] = _params.split("&");
		String strTab[][];
		strTab = new String[paramsTab.length][];
		for(int i=0; i<paramsTab.length; i++)
		{
			strTab[i] = paramsTab[i].split("=");
			//System.out.println( "length " + strTab[i].length);
			if(strTab[i].length < 2)
				return null;
			if(strTab[i][0] != null && strTab[i][1] != null)
			{
				//System.out.println( "1 " + strTab[i][0] + " 2: " + strTab[i][1]);
				resultMap.put(strTab[i][0], strTab[i][1]);
			}
		}
		
		return resultMap;
	}
}

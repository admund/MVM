package pl.admund.MVM_Servlet.HTTPRespond;

import java.util.ArrayList;
import java.util.HashMap;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import pl.admund.MVM_Servlet.Player.PlayerDBUtils;
import pl.admund.MVM_Servlet.Team.Team;
import pl.admund.MVM_Servlet.Team.TeamDBUtils;
import pl.admund.MVM_Servlet.Volleyballer.Volleyballer;
import pl.admund.MVM_Servlet.Volleyballer.VolleyballerDBUtils;
import pl.admund.MVM_Servlet.XML.XMLUtils;
import pl.admund.Constans.ErrorCode;

public class TeamRespond 
{
	public static String getTeamInfo(HashMap<String, String> _hashMap)
	{
		int playerId = Integer.parseInt(_hashMap.get("pid"));
		String pass = _hashMap.get("pass");
		
		if( !PlayerDBUtils.checkPass(playerId, pass) )
			return XMLUtils.createErrorXML("Wrong pass.", ErrorCode.ERR_WRONG_PASS);
		
		int teamId = Integer.parseInt(_hashMap.get("team_id"));
		Team tmpTeam = TeamDBUtils.getTeamFromId(teamId);
		if(tmpTeam == null)
			return XMLUtils.createErrorXML("Team not found.", ErrorCode.ERR_TEAM_NOT_FOUND);
		
		ArrayList<Volleyballer> tmpVolleyballerList = VolleyballerDBUtils.getVolleyballerListFromTeamId(tmpTeam.getId());
		System.out.println( "tmpVolleyballerList " + tmpVolleyballerList.size());
		if(tmpVolleyballerList == null || tmpVolleyballerList.isEmpty())
			return XMLUtils.createErrorXML("Volleyballers list is empty.", ErrorCode.ERR_TEAM_NOT_FOUND);//TODO
		
		tmpTeam.setVolleyballerList(tmpVolleyballerList);
		
		Document tmpDoc = XMLUtils.startXMLDocument("team_info");
		Element rootElement = (Element)tmpDoc.getFirstChild();
        
        tmpTeam.addShortInfoToXmlDoc(tmpDoc, rootElement);
		
		return XMLUtils.XMLDocumentToString(tmpDoc);
	}
}

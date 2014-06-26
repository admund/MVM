package pl.admund.MVM_Servlet.HTTPRespond;

import java.util.HashMap;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import pl.admund.MVM_Servlet.Player.PlayerDBUtils;
import pl.admund.MVM_Servlet.Tactic.Tactic;
import pl.admund.MVM_Servlet.Tactic.TacticDBUtils;
import pl.admund.MVM_Servlet.XML.XMLUtils;
import pl.admund.Constans.ErrorCode;

public class TacticRespond
{
	public static String getTactic(HashMap<String, String> _hashMap)
	{
		int playerId = Integer.parseInt(_hashMap.get("pid"));
		String pass = _hashMap.get("pass");
		
		if( !PlayerDBUtils.checkPass(playerId, pass) )
			return XMLUtils.createErrorXML("Wrong pass.", ErrorCode.ERR_WRONG_PASS);
		
		Document tmpDoc = XMLUtils.startXMLDocument("tactic_info");
        Element rootElement = (Element)tmpDoc.getFirstChild();
        
        int teamId = Integer.parseInt(_hashMap.get("team_id"));
        
        Tactic tmpTactic = TacticDBUtils.getTactic(teamId);
        if(tmpTactic == null)
        	tmpTactic = new Tactic();
        
        tmpTactic.addToXmlDoc(tmpDoc, rootElement);
		return XMLUtils.XMLDocumentToString(tmpDoc);
	}
	
	public static String setTactic(HashMap<String, String> _hashMap)
	{
		int playerId = Integer.parseInt(_hashMap.get("pid"));
		String pass = _hashMap.get("pass");
		
		if( !PlayerDBUtils.checkPass(playerId, pass) )
			return XMLUtils.createErrorXML("Wrong pass.", ErrorCode.ERR_WRONG_PASS);
        
		int teamId = Integer.parseInt(_hashMap.get("team_id"));
        String tactic = _hashMap.get("tactic");
        Tactic tmpTactic = Tactic.parseString(tactic);
        
		if(TacticDBUtils.setTactic(teamId, tmpTactic))
		{
	        Document tmpDoc = XMLUtils.startXMLDocument("tactic_info");
	        Element rootElement = (Element)tmpDoc.getFirstChild();
	        
	        tmpTactic.addToXmlDoc(tmpDoc, rootElement);
			return XMLUtils.XMLDocumentToString(tmpDoc);
		}
		else
			return XMLUtils.createErrorXML("DB error.", ErrorCode.ERR_DB_ERROR);
	}
}

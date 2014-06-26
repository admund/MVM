package pl.admund.MVM_Client.XMLUtils;

import java.io.IOException;
import java.io.InputStream;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import pl.admund.MVM_Client.XMLResponds.CreatePlayerRespondXML;
import pl.admund.MVM_Client.XMLResponds.GetLeagueTableXML;
import pl.admund.MVM_Client.XMLResponds.GetMessagesRespondXML;
import pl.admund.MVM_Client.XMLResponds.GetPlayerProfileRespondXML;
import pl.admund.MVM_Client.XMLResponds.GetSquadRespondXML;
import pl.admund.MVM_Client.XMLResponds.GetTacticRespondXML;
import pl.admund.MVM_Client.XMLResponds.GetTeamInfoXML;
import pl.admund.MVM_Client.XMLResponds.HTTPRespond;
import pl.admund.MVM_Client.XMLResponds.LoginRespondXML;
import android.os.AsyncTask;

public class HTTPGetAsyncTask extends AsyncTask<String, Integer, HTTPRespond>
{
	private XMLType flag;
	
	public HTTPGetAsyncTask(XMLType flag)
	{
		this.flag = flag;
	}
	
	@Override
	protected HTTPRespond doInBackground(String... params) 
	{			
		switch(flag)
		{
			/*case SET_SQD_HTTP:
				NetworkHTTP.DownloadGet(params[0]);return null;*/
			case MATCH_PROP_HTTP:
				NetworkHTTP.DownloadGet(params[0]);return null;
			case MATCH_ACCEPT_HTTP:
				NetworkHTTP.DownloadGet(params[0]);return null;
		}
		
		Document doc = null;
		try
		{
			InputStream xmlInputStream = NetworkHTTP.DownloadGet(params[0]);
			
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();

			DocumentBuilder db = dbf.newDocumentBuilder();
			doc = db.parse(new InputSource(xmlInputStream));
	        doc.getDocumentElement().normalize();
		}
		catch(ParserConfigurationException pce)
		{
			pce.printStackTrace();
		}
		catch(SAXException se) 
		{
			se.printStackTrace();
		}
		catch(IOException ioe) 
		{
			ioe.printStackTrace();
		}
		
		switch(flag)
		{
			case CHECK_HTTP:
				return null;
			case LOGIN_HTTP:
				return downloadAndParseLoginXML(doc);
			case CRT_PLR_HTTP:
				return downloadAndParseCreatePlayerXML(doc);
			case GET_SQD_HTTP:
				return downloadAndParseGetSquadXML(doc);
			case GET_MSG_HTTP:
				return downloadAndParseGetMsgXML(doc);
			case GET_TABLE_HTTP:
				return downloadAndParseGetLeagueTable(doc);
			case GET_PROFILE_HTTP:
				return downloadAndParseGetPlayerProfileXML(doc);
			case MATCH_NEXT_HTTP:
				return new HTTPRespond();//TODO narazie fake
			case GET_TEAM_INFO_HTTP:
				return downloadAndParseGetTeamInfo(doc);
			case GET_TEAM_TACTIC_HTTP:
				return downloadAndParseGetTactic(doc);
		}

		return null;
	}
	
	@Override
    protected void onPostExecute(HTTPRespond result)
	{
		/*if(flag == XMLType.CHECK_HTTP || flag == XMLType.LOGIN_HTTP || flag == XMLType.CRT_PLR_HTTP
			|| flag == XMLType.GET_SQD_HTTP || flag == XMLType.GET_MSG_HTTP || flag == XMLType.GET_PROFILE_HTTP
			|| flag == XMLType.GET_TABLE_HTTP || flag == XMLType.MATCH_NEXT_HTTP || flag == XMLType.GET_TEAM_INFO_HTTP
			|| flag == XMLType.GET_TEAM_TACTIC_HTTP)*/
		{
			NetworkHTTP.getInstance().getOnCompleteDownloadListner().downloadComplete(flag, result);
		}
    }
	
	/**
	 * Metoda wysylajaca zapytanie i parsujaca LOGIN_XML
	 * 
	 * @param _file
	 * @return
	 * @throws ParserConfigurationException 
	 * @throws IOException 
	 * @throws SAXException 
	 */
	private HTTPRespond downloadAndParseLoginXML(Document doc)
	{
		LoginRespondXML tmpLoginRespond = new LoginRespondXML();
		return tmpLoginRespond.parseLoginXML(doc);
	}
	
	private HTTPRespond downloadAndParseCreatePlayerXML(Document doc)
	{
		CreatePlayerRespondXML tmpCreatePlayerRespond = new CreatePlayerRespondXML();
		return tmpCreatePlayerRespond.parseCreatePlayerXML(doc);
	}
	
	private HTTPRespond downloadAndParseGetSquadXML(Document doc)
	{
		GetSquadRespondXML tmpGetSquadRespond = new GetSquadRespondXML();
        return tmpGetSquadRespond.parseGetSquadXML(doc);
	}
	
	private HTTPRespond downloadAndParseGetMsgXML(Document doc)
	{
        GetMessagesRespondXML tmpGetSquadRespond = new GetMessagesRespondXML();
        return tmpGetSquadRespond.parseGetMessagesXML(doc);
	}
	
	private HTTPRespond downloadAndParseGetPlayerProfileXML(Document doc)
	{
		GetPlayerProfileRespondXML tmpGetPlayerProfileRespond = new GetPlayerProfileRespondXML();
        return tmpGetPlayerProfileRespond.parseGetPlayerProfileXML(doc);
	}
	
	private HTTPRespond downloadAndParseGetLeagueTable(Document doc)
	{
		GetLeagueTableXML tmpGetLeagueTableRespond = new GetLeagueTableXML();
        return tmpGetLeagueTableRespond.parseGetLeagueTableXML(doc);
	}
	
	private HTTPRespond downloadAndParseGetTeamInfo(Document doc)
	{
		GetTeamInfoXML tmpGetTeamInfoRespond = new GetTeamInfoXML();
        return tmpGetTeamInfoRespond.parseGetTeamInfoXML(doc);
	}
	
	private HTTPRespond downloadAndParseGetTactic(Document doc)
	{
		GetTacticRespondXML tmpGetTacticRespond = new GetTacticRespondXML();
        return tmpGetTacticRespond.parseGetTacticRespondXML(doc);
	}
}
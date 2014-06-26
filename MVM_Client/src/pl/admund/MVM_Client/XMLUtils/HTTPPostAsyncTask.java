package pl.admund.MVM_Client.XMLUtils;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.apache.http.NameValuePair;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import pl.admund.MVM_Client.XMLResponds.GetSquadRespondXML;
import pl.admund.MVM_Client.XMLResponds.GetTacticRespondXML;
import pl.admund.MVM_Client.XMLResponds.HTTPRespond;
import android.os.AsyncTask;

public class HTTPPostAsyncTask extends AsyncTask<List<NameValuePair>, Integer, HTTPRespond>
{
	private XMLType flag;
	
	public HTTPPostAsyncTask(XMLType flag)
	{
		this.flag = flag;
	}
	
	@Override
	protected HTTPRespond doInBackground(List<NameValuePair>... params) 
	{			
		switch(flag)
		{
			default:
				;
		}
		
		Document doc = null;
		try
		{
			InputStream xmlInputStream = NetworkHTTP.DownloadPost(params[0]);
			
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
			case SET_TEAM_TACTIC_HTTP:
				return downloadAndParseSetTactic(doc);
				
			case SET_SQD_HTTP:
				return downloadAndParseSetSquad(doc);
		}

		return null;
	}
	
	@Override
    protected void onPostExecute(HTTPRespond result)
	{
		//if(flag == XMLType.SET_TEAM_TACTIC_HTTP)
		{
			NetworkHTTP.getInstance().getOnCompleteDownloadListner().downloadComplete(flag, result);
		}
    }
	
	private HTTPRespond downloadAndParseSetTactic(Document doc)
	{
		GetTacticRespondXML tmpGetTacticRespond = new GetTacticRespondXML();
        return tmpGetTacticRespond.parseGetTacticRespondXML(doc);
	}
	
	private HTTPRespond downloadAndParseSetSquad(Document doc)
	{
		GetSquadRespondXML tmpGetSquadRespond = new GetSquadRespondXML();
        return tmpGetSquadRespond.parseGetSquadXML(doc);
	}
}

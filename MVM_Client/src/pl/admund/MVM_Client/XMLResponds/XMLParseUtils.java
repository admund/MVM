package pl.admund.MVM_Client.XMLResponds;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

public class XMLParseUtils 
{
	public static int getOneIntValue(Document _doc, String _nodeName)
	{
    	NodeList tmpNodeList = _doc.getElementsByTagName(_nodeName);
        if(tmpNodeList.getLength() != 0)
        {
        	return Integer.parseInt( tmpNodeList.item(0).getTextContent() );
        }
        return -1;
	}
	
	public static String getOneStringValue(Document _doc, String _nodeName)
	{
    	NodeList tmpNodeList = _doc.getElementsByTagName(_nodeName);
        if(tmpNodeList.getLength() != 0)
        {
        	return tmpNodeList.item(0).getTextContent();
        }
        
        return null;
	}
}

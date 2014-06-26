package pl.admund.MVM_Servlet;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import pl.admund.MVM_Servlet.XML.XMLUtils;
import pl.admund.Private.PrivateConst;

public class TCPServerInfo
{
	public static boolean addToXmlDoc(Document doc, Element rootElement)
	{
		Element rootTeam = doc.createElement("server_info");
		rootElement.appendChild(rootTeam);
		
		XMLUtils.addToXMLDoc(doc, rootTeam, "addr", PrivateConst.getTCPAddr());
		XMLUtils.addToXMLDoc(doc, rootTeam, "port", "" + PrivateConst.getTCPPort());
		
		return true;
	}
}

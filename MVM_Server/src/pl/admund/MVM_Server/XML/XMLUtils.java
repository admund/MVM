package pl.admund.MVM_Server.XML;

import java.io.StringWriter;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Text;

public class XMLUtils 
{
	public static Document startXMLDocument(String _firstTag)
	{
		DocumentBuilderFactory dbfac = DocumentBuilderFactory.newInstance();
        DocumentBuilder docBuilder = null;
        Document doc = null;
		try
		{
			docBuilder = dbfac.newDocumentBuilder();
			doc = docBuilder.newDocument();
			
			Element xmlRoot = doc.createElement(_firstTag);
			doc.appendChild(xmlRoot);
			
		}
		catch (ParserConfigurationException e) 
		{
			System.out.println( "C:"  + e.getClass() + "\nM:" + e.getMessage()  );
			return null;
		}
        return doc;
	}
	
	public static String createErrorXML(String _errorMsg, int _errorCode)
	{
		DocumentBuilderFactory dbfac = DocumentBuilderFactory.newInstance();
        DocumentBuilder docBuilder = null;
        Document doc = null;
		try
		{
			docBuilder = dbfac.newDocumentBuilder();
			doc = docBuilder.newDocument();
			
			Element xmlRoot = doc.createElement("error");
			addToXMLDoc(doc, xmlRoot, "error_msg", _errorMsg);
			addToXMLDoc(doc, xmlRoot, "error_code", Integer.toString(_errorCode));
			
			doc.appendChild(xmlRoot);
		}
		catch (ParserConfigurationException e) 
		{
			System.out.println( "C:"  + e.getClass() + "\nM:" + e.getMessage()  );
			return null;
		}

		return XMLUtils.XMLDocumentToString(doc);
	}
	
	public static String createOkResultXML()
	{
		DocumentBuilderFactory dbfac = DocumentBuilderFactory.newInstance();
        DocumentBuilder docBuilder = null;
        Document doc = null;
		try
		{
			docBuilder = dbfac.newDocumentBuilder();
			doc = docBuilder.newDocument();
			
			Element xmlRoot = doc.createElement("result");
			addToXMLDoc(doc, xmlRoot, "result", "OK");
			
			doc.appendChild(xmlRoot);
		}
		catch (ParserConfigurationException e) 
		{
			System.out.println( "C:"  + e.getClass() + "\nM:" + e.getMessage()  );
			return null;
		}

		return XMLUtils.XMLDocumentToString(doc);
	}
	
	public static String XMLDocumentToString(Document _doc)
	{
		TransformerFactory transfac = TransformerFactory.newInstance();
        Transformer trans;
        String xmlString = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n";
		try 
		{
			trans = transfac.newTransformer();
	
	        trans.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
	        trans.setOutputProperty(OutputKeys.INDENT, "yes");
	        StringWriter sw = new StringWriter();
	        StreamResult result = new StreamResult(sw);
	        DOMSource source = new DOMSource(_doc);
	        trans.transform(source, result);
	        xmlString += sw.toString();
		} 
		catch (TransformerConfigurationException e) 
		{
			System.out.println( "C:"  + e.getClass() + "\nM:" + e.getMessage()  );
			return null;
		}
		catch (TransformerException e)
		{
			System.out.println( "C:"  + e.getClass() + "\nM:" + e.getMessage()  );
			return null;
		}
		return xmlString ;
	}
	
	public static void addToXMLDoc(Document _doc, Element _element, String _tag, String _text)
	{
		Element child = _doc.createElement(_tag);
		_element.appendChild(child);
		Text childText = _doc.createTextNode(_text);
		child.appendChild(childText);
	}
}

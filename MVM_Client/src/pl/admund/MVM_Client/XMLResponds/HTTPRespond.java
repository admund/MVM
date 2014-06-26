package pl.admund.MVM_Client.XMLResponds;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

import android.util.Log;

public class HTTPRespond
{
	private int mErrorCode = -1;
	private String mErrorMSG = null;
	
	public HTTPRespond(){}
	
//---------SETTERS & GETTERS------------
	public int getErrorCode() {
		return mErrorCode;
	}
	public void setErrorCode(int mErrorCode) {
		this.mErrorCode = mErrorCode;
	}
	public String getErrorMSG() {
		return mErrorMSG;
	}
	public void setErrorMSG(String mErrorMSG) {
		this.mErrorMSG = mErrorMSG;
	}
	
	public HTTPRespond parseHTTPRespond(Document _doc)
	{
        NodeList errorList = _doc.getElementsByTagName("error");
        if(errorList.getLength() != 0)
        {
        	NodeList errorTextList = _doc.getElementsByTagName("error_msg");
	        if(errorTextList.getLength() != 0)
	        {
	        	setErrorMSG( errorTextList.item(0).getTextContent() );
	        }
	        
        	NodeList errorCodeList = _doc.getElementsByTagName("error_code");
	        if(errorCodeList.getLength() != 0)
	        {
	        	Log.d("INT", errorCodeList.item(0).getTextContent());
	        	int errorCode = Integer.parseInt( errorCodeList.item(0).getTextContent() );
	        	setErrorCode( errorCode );
	        }
        }
        
        NodeList resultList = _doc.getElementsByTagName("result");
        if(resultList.getLength() != 0)
        {
        	NodeList resultTextList = _doc.getElementsByTagName("result");
	        if(resultTextList.getLength() != 0)
	        {
	        	setErrorCode(-1);
	        	setErrorMSG( resultTextList.item(0).getTextContent() );
	        }
        }
        
        return this;
	}
}

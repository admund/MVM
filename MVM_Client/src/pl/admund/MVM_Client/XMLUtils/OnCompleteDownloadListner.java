package pl.admund.MVM_Client.XMLUtils;

import pl.admund.MVM_Client.XMLResponds.HTTPRespond;

public interface OnCompleteDownloadListner
{
	void downloadComplete(XMLType flag, HTTPRespond respond);
}
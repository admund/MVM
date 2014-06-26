package pl.admund.MVM_Client.TCP;

import pl.admund.MVM_Client.main.EmergencyContext;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

public class SetEndParser
{
	public static void parseSetEnd(String str)
	{
		String strTab[] = str.split("_");
		if(strTab.length < 3)
			return;
		
		int setNr = Integer.parseInt(strTab[1]);
		
		Intent broadCastMsg = new Intent();
		Bundle bundle = new Bundle();
		bundle.putInt("CMD", TCPMessageCmd.TCP_MSG_SET_END);
		bundle.putInt("SET_NR", setNr);
		bundle.putString("RESULT", strTab[2]);
		broadCastMsg.putExtras(bundle);
		broadCastMsg.setAction("pl.admund.MVM_Client.tcpnetwork");
		
		//Log.d("TCP", "brod_cast: parseSetEnd");
		EmergencyContext.getInstance().getContext().sendBroadcast(broadCastMsg);
	}
}

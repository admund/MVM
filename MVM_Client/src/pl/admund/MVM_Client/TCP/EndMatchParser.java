package pl.admund.MVM_Client.TCP;

import pl.admund.MVM_Client.main.EmergencyContext;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

public class EndMatchParser 
{
	public static void parseEndMatch(String str)
	{
		String strTab[] = str.split("_");
		if(strTab.length < 2)
			return;
		
		Intent broadCastMsg = new Intent();
		Bundle bundle = new Bundle();
		bundle.putInt("CMD", TCPMessageCmd.TCP_MSG_MATCH_END);
		bundle.putInt("WIN", Integer.parseInt(strTab[1]));
		broadCastMsg.putExtras(bundle);
		broadCastMsg.setAction("pl.admund.MVM_Client.tcpnetwork");
		
		//Log.d("TCP", "brod_cast: parseSetEnd");
		EmergencyContext.getInstance().getContext().sendBroadcast(broadCastMsg);
	}
}

package pl.admund.MVM_Client.TCP;

import pl.admund.MVM_Client.main.EmergencyContext;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

public class SquadChangeParser 
{
	public static void parseSquadChangeEnd(String str)
	{
		String strTab[] = str.split("_");
		if(strTab.length < 3)
			return;
		
		Intent broadCastMsg = new Intent();
		Bundle bundle = new Bundle();
		bundle.putInt("CMD", TCPMessageCmd.TCP_MSG_SQD_CHANGE);
		bundle.putInt("VOLL_1", Integer.parseInt(strTab[1]));
		bundle.putInt("VOLL_2", Integer.parseInt(strTab[2]));
		broadCastMsg.putExtras(bundle);
		broadCastMsg.setAction("pl.admund.MVM_Client.tcpnetwork");
		
		//Log.d("TCP", "brod_cast: parseSquadChange");
		EmergencyContext.getInstance().getContext().sendBroadcast(broadCastMsg);
	}
}

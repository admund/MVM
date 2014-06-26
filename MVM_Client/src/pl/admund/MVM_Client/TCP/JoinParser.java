package pl.admund.MVM_Client.TCP;

import pl.admund.MVM_Client.main.EmergencyContext;
import android.content.Intent;
import android.os.Bundle;

public class JoinParser 
{
	public static void parseJoin(String str)
	{
		String strTab[] = str.split("_");
		if(strTab.length < 2)
			return;
		
		String teamSplit[] = strTab[1].split(":");
		if(teamSplit.length < 2)
			return;
		
		int homeId = Integer.parseInt(teamSplit[0]);
		int awayId = Integer.parseInt(teamSplit[1]);
		
		Intent broadCastMsg = new Intent();
		Bundle bundle = new Bundle();
		bundle.putInt("CMD", TCPMessageCmd.TCP_MSG_JOIN);
		bundle.putInt("HOME_ID", homeId);
		bundle.putInt("AWAY_ID", awayId);
		broadCastMsg.putExtras(bundle);
		broadCastMsg.setAction("pl.admund.MVM_Client.tcpnetwork");
		
		//Log.d("TCP", "brod_cast: h+a " + homeId + " " + awayId);
		EmergencyContext.getInstance().getContext().sendBroadcast(broadCastMsg);
	}
}

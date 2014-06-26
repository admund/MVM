package pl.admund.MVM_Client.TCP;

import pl.admund.MVM_Client.main.EmergencyContext;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

public class ResultParser
{
	public static void parseResult(String str)
	{
		String strTab[] = str.split("_");
		if(strTab.length < 3)
			return;
		
		String setSplit[] = strTab[1].split(":");
		if(setSplit.length < 2)
			return;
		
		int setHomeId = Integer.parseInt(setSplit[0]);
		int setAwayId = Integer.parseInt(setSplit[1]);
		
		String ptsSplit[] = strTab[2].split(":");
		if(ptsSplit.length < 2)
			return;
		
		int ptsHomeId = Integer.parseInt(ptsSplit[0]);
		int ptsAwayId = Integer.parseInt(ptsSplit[1]);
		
		Intent broadCastMsg = new Intent();
		Bundle bundle = new Bundle();
		bundle.putInt("CMD", TCPMessageCmd.TCP_MSG_RESULT);
		bundle.putInt("HOME_SET", setHomeId);
		bundle.putInt("AWAY_SET", setAwayId);
		bundle.putInt("HOME_PTS", ptsHomeId);
		bundle.putInt("AWAY_PTS", ptsAwayId);
		broadCastMsg.putExtras(bundle);
		broadCastMsg.setAction("pl.admund.MVM_Client.tcpnetwork");
		
		//Log.d("TCP", "brod_cast");
		EmergencyContext.getInstance().getContext().sendBroadcast(broadCastMsg);
	}
}

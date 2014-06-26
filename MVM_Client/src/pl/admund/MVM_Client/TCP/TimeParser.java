package pl.admund.MVM_Client.TCP;

import pl.admund.MVM_Client.main.EmergencyContext;
import android.content.Intent;
import android.os.Bundle;

public class TimeParser 
{
	public static void parseTime(String str)
	{
		String strTab[] = str.split("_");
		if(strTab.length < 2)
			return;

		int time = Integer.parseInt(strTab[1]);
		int who = 0;
		if(strTab.length == 3)
			who = Integer.parseInt(strTab[2]);
		
		Intent broadCastMsg = new Intent();
		Bundle bundle = new Bundle();
		bundle.putInt("CMD", TCPMessageCmd.TCP_MSG_TIME);
		bundle.putInt("TIME", time);
		bundle.putInt("WHO", who);
		broadCastMsg.putExtras(bundle);
		broadCastMsg.setAction("pl.admund.MVM_Client.tcpnetwork");
		
		EmergencyContext.getInstance().getContext().sendBroadcast(broadCastMsg);
	}
}

package pl.admund.MVM_Client.TCP;

import pl.admund.MVM_Client.main.EmergencyContext;
import android.content.Intent;
import android.os.Bundle;

public class TacticParser 
{
	public static void parseTactic(String str)
	{
		String strTab[] = str.split("_");
		if(strTab.length < 3)
			return;
		
		int succes = Integer.parseInt(strTab[1]);
		
		Intent broadCastMsg = new Intent();
		Bundle bundle = new Bundle();
		bundle.putInt("CMD", TCPMessageCmd.TCP_MSG_TACTIC);
		if(succes == 0)
			bundle.putBoolean("SUCCES", false);
		else
			bundle.putBoolean("SUCCES", true);
		
		int teamId = Integer.parseInt(strTab[2]);
		
		bundle.putInt("TEAM_ID", teamId);
		
		broadCastMsg.putExtras(bundle);
		broadCastMsg.setAction("pl.admund.MVM_Client.tcpnetwork");
		
		//Log.d("TCP", "brod_cast: parseSetEnd");
		EmergencyContext.getInstance().getContext().sendBroadcast(broadCastMsg);
	}
}

package pl.admund.MVM_Client.TCP;

import java.util.ArrayList;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import pl.admund.MVM_Client.PlayMatch.SingleMatchAction;
import pl.admund.MVM_Client.main.EmergencyContext;

public class ActionParser 
{
	public static void parseAction(String str)
	{
		String strTab[] = str.split("_");
		if(strTab.length < 2)
			return;
		
		ArrayList<SingleMatchAction> actionList = new ArrayList<SingleMatchAction>();
		for(int i=1; i<strTab.length; i++)
		{
			String actionSplit[] = strTab[i].split(":");
			if(actionSplit.length != 3)
				continue;
			else
			{
				int volleyballerId = Integer.parseInt(actionSplit[0]);
				int actionType = Integer.parseInt(actionSplit[1]);
				int actionResult= Integer.parseInt(actionSplit[2]);
				actionList.add(new SingleMatchAction(volleyballerId, actionType, actionResult));
			}
		}
		
		Intent broadCastMsg = new Intent();
		Bundle bundle = new Bundle();
		bundle.putInt("CMD", TCPMessageCmd.TCP_MSG_ACTION);
		bundle.putSerializable("ACTION", actionList);
		broadCastMsg.putExtras(bundle);
		broadCastMsg.setAction("pl.admund.MVM_Client.tcpnetwork");
		
		//Log.d("TCP", "brod_cast: " + actionList.size());
		EmergencyContext.getInstance().getContext().sendBroadcast(broadCastMsg);
	}
}

package pl.admund.MVM_Server.MatchEngine;

import java.util.ArrayList;
import pl.admund.MVM_Server.TCPServer.TCPMessageCmd;

public class MatchAction 
{
	private ArrayList<SingleMatchAction> actions = new ArrayList<SingleMatchAction>();
	
	public void addAction(SingleMatchAction singleAction)
	{
		actions.add(singleAction);
	}
	
	public void addAction(int volleyballerId, int actionType, int actionResult)
	{
		actions.add(new SingleMatchAction(volleyballerId, actionType, actionResult));
	}
	
	public String actionsToString()
	{
		String str = "" + TCPMessageCmd.TCP_MSG_ACTION + "_";
		if(actions.size() > 0)
		{
			str += actions.get(0).getVolleyballerId()+":"+actions.get(0).getActionType()+":"+actions.get(0).getActionResult();
			for(int i=1; i<actions.size(); i++)
			{
				str += "_"+actions.get(i).getVolleyballerId()+":"+actions.get(i).getActionType()+":"+actions.get(i).getActionResult();
			}
		}
		return str;
	}
}

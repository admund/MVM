package pl.admund.MVM_Client.TCP;

import android.util.Log;

public class TCPMessageParser 
{
	public static void parse(String str)
	{
		String strTab[] = str.split("_");
		
		int cmd = Integer.parseInt(strTab[0]);
		Log.d("TCP", " PRZYCHODZI cmd: " + cmd);
		switch(cmd)
		{
			case TCPMessageCmd.TCP_MSG_JOIN:
			{
				JoinParser.parseJoin(str);
			}
			break;
			
			case TCPMessageCmd.TCP_MSG_RESULT:
			{
				ResultParser.parseResult(str);
			}
			break;
			
			case TCPMessageCmd.TCP_MSG_TIME:
			{
				TimeParser.parseTime(str);
			}
			break;
			
			case TCPMessageCmd.TCP_MSG_SET_END:
			{
				SetEndParser.parseSetEnd(str);
			}
			break;
			
			case TCPMessageCmd.TCP_MSG_MATCH_END:
			{
				EndMatchParser.parseEndMatch(str);
			}
			break;
			
			case TCPMessageCmd.TCP_MSG_ACTION:
			{
				ActionParser.parseAction(str);
			}
			break;
			
			case TCPMessageCmd.TCP_MSG_SQD_CHANGE:
			{
				SquadChangeParser.parseSquadChangeEnd(str);
			}
			break;
			
			case TCPMessageCmd.TCP_MSG_TACTIC:
			{
				TacticParser.parseTactic(str);
			}
			break;
		}
	}
}

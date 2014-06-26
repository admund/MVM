package pl.admund.MVM_Client.Utils;

import pl.admund.MVM_Client.main.EmergencyContext;

public class ResString
{
	static public String getString(int resId)
	{
		return EmergencyContext.getInstance().getContext().getString(resId);
	}
}

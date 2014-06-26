package pl.admund.MVM_Client.main;

import android.content.Context;

public class EmergencyContext 
{
	private Context context;
//------------------------------------------------------------------
	private static EmergencyContext myInstance;
	public static EmergencyContext getInstance()
	{
		if(myInstance == null)
			myInstance = new EmergencyContext();
		return myInstance;
	};
	private EmergencyContext()
	{};
//------------------------------------------------------------------	
	
	public Context getContext() {
		return context;
	}
	public void setContext(Context context) {
		this.context = context;
	}
}

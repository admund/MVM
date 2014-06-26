package pl.admund.MVM_Client.Utils;

import pl.admund.MVM_Client.main.PlayerAllInfo;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

public class AppSheredPreferences 
{
	private static AppSheredPreferences myInstance;
	public static AppSheredPreferences getInstance()
	{
		if(myInstance == null)
			myInstance = new AppSheredPreferences();
		return myInstance;
	};
	private AppSheredPreferences()
	{};
	
//--------------------------------------------------------
	private SharedPreferences sheredPreferences;
	private SharedPreferences.Editor sheredPreferencesEditor;
	
	public void init(Activity activity)
	{
		sheredPreferences = activity.getPreferences(Context.MODE_PRIVATE);
		sheredPreferencesEditor = sheredPreferences.edit();
	}
	
	/**
	 * Metoda pobierajaca login i token
	 */
	public void getMailAndPass()
	{
		String tmpLogin = sheredPreferences.getString("mail", null);
		// Log.d("SHAR PREF", "tmpLogin " + tmpLogin);
		if(tmpLogin == null)
		{
			sheredPreferencesEditor.putString("mail", "null");
			sheredPreferencesEditor.commit();
			PlayerAllInfo.getInstance().setMail("null");
		}
		else
			PlayerAllInfo.getInstance().setMail(tmpLogin);

		String tmpToken = sheredPreferences.getString("token", null);
		// Log.d("SHAR PREF", "tmpToken " + tmpToken);
		if (tmpToken == null)
		{
			sheredPreferencesEditor.putString("token", "null");
			sheredPreferencesEditor.commit();
			PlayerAllInfo.getInstance().setToken("null");
		}
		else
			PlayerAllInfo.getInstance().setToken(tmpToken);
	}
	
	public void removeMailAndPass()
	{
		sheredPreferencesEditor.putString("mail", "null");
		sheredPreferencesEditor.commit();
		PlayerAllInfo.getInstance().setMail("null");
		
		sheredPreferencesEditor.putString("token", "null");
		sheredPreferencesEditor.commit();
		PlayerAllInfo.getInstance().setToken("null");
	}
	
	public void setMailAndPass()
	{
		String mail = PlayerAllInfo.getInstance().getMail();
		sheredPreferencesEditor.putString("mail", mail);
		sheredPreferencesEditor.commit();
		
		String pass = PlayerAllInfo.getInstance().getToken();
		sheredPreferencesEditor.putString("token", pass);
		sheredPreferencesEditor.commit();
	}
}

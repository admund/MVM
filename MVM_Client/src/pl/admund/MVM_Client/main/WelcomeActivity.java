package pl.admund.MVM_Client.main;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import pl.admund.MVM_Client.R;
import pl.admund.MVM_Client.Utils.AppSheredPreferences;
import pl.admund.MVM_Client.Utils.ResString;
import pl.admund.MVM_Client.XMLResponds.CreatePlayerRespondXML;
import pl.admund.MVM_Client.XMLResponds.HTTPRespond;
import pl.admund.MVM_Client.XMLUtils.HTTPGetAsyncTask;
import pl.admund.MVM_Client.XMLUtils.OnCompleteDownloadListner;
import pl.admund.MVM_Client.XMLUtils.NetworkHTTP;
import pl.admund.MVM_Client.XMLUtils.XMLCreator;
import pl.admund.MVM_Client.XMLUtils.XMLType;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnDismissListener;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class WelcomeActivity extends MyActivity
{
	private final String WAIT_LOG_ON = "Wait for log on...";
	
	private final int LOGIN_DIALOG = 1;
	private final int REGISTER_DIALOG = 2;
	
	private boolean loginFromSheared = false;

	@Override
	protected void onCreate(Bundle _savedInstanceState)
	{
		super.onCreate(_savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		
		// TAKI  RATUNKOWY CONTEXT :)
		EmergencyContext.getInstance().setContext(getApplicationContext());

		setContentView(R.layout.welcome_activity_layout);
		
		Display display = getWindowManager().getDefaultDisplay();
		Log.d("DISPLAY", ""+display.getWidth() + " "+display.getHeight());
		DisplayMetrics metrics = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(metrics);
		Log.d("DISPLAY", ""+metrics.densityDpi + " "+metrics.xdpi+ " "+metrics.ydpi);
		Log.d("DISPLAY", ""+metrics.heightPixels + " "+metrics.widthPixels+ " "+metrics.scaledDensity);
		//Constans.MAX_WIDTH = display.getWidth();
		//Constans.MAX_HIGHT = display.getHeight();
		
		AppSheredPreferences.getInstance().init(this);
		AppSheredPreferences.getInstance().getMailAndPass();
		if(isOnline())
		{
			String loginURL = XMLCreator.createLoginURL(PlayerAllInfo.getInstance().getMail(), PlayerAllInfo.getInstance().getToken());
			new HTTPGetAsyncTask(XMLType.LOGIN_HTTP).execute(loginURL);
			loginFromSheared = true;
			
			showWaitDialog(R.string.wait_login);
		}
		else
		{
			showErrorDialog("Ni ma interneta...");
		}
		
		// ADD LISTNERS
		createLoginBtnListner();
		createPlayBtnListner();
		createCreditsBtnListner();
		//createDemoBtnListner();
	}
	
	@Override
	protected void onResume() 
	{
		super.onResume();
		
		// set on complete HTTP download listner
		NetworkHTTP.getInstance().setOnCompleteDownloadListner(new WelcomeOnCompleteDownloadListner());
	}
	
	@Override
	protected Dialog onCreateDialog(int _id, Bundle _args) 
	{
		Dialog dialog = super.onCreateDialog(_id, _args);
		if(dialog != null)
			return dialog;
		
		switch(_id)
		{
			case LOGIN_DIALOG:
			{
				return createLoginDialog();
			}
			case REGISTER_DIALOG:
			{
				return createRegisterDialog();
			}
			default:
			{
				return null;
			}
		}
	}
	
	public Dialog createLoginDialog()
	{
		final Dialog dialog = new Dialog(this, R.style.NoTitleDialog);
		dialog.setContentView(R.layout.welcome_login_dialog);
		dialog.setOnDismissListener(new OnDismissListener() {
			@Override
			public void onDismiss(DialogInterface dialog) {
				removeDialog(LOGIN_DIALOG);
			}
		});
		
		Button okBtn = (Button)dialog.findViewById(R.id.login_dialog_ok_btn);
		okBtn.setOnClickListener(new OnClickListener() 
		{
			@Override
			public void onClick(View v)
			{
				EditText mailEditText = (EditText)dialog.findViewById(R.id.login_dialog_mail_edit);
				String tmpMail = mailEditText.getText().toString();
				
				EditText passEditText = (EditText)dialog.findViewById(R.id.login_dialog_pass_edit);
				String tmpPass= passEditText.getText().toString();
				
				if(checkMail(tmpMail))
				{
					if(tmpPass.length() > 4)
					{
						dismissDialog(LOGIN_DIALOG);
						
						String loginURL = XMLCreator.createLoginURL(tmpMail, tmpPass);
						new HTTPGetAsyncTask(XMLType.LOGIN_HTTP).execute(loginURL);
						
						showWaitDialog(R.string.wait_login);
					}
					else
						Toast.makeText(WelcomeActivity.this, "Haslo jest za krotkie. Minimum 5 znakow.", Toast.LENGTH_SHORT).show();
				}
				else
					Toast.makeText(WelcomeActivity.this, "Podales niepoprawny adres mail.", Toast.LENGTH_SHORT).show();
			}
		});
		
		Button registerBtn = (Button)dialog.findViewById(R.id.login_dialog_register_btn);
		registerBtn.setOnClickListener(new OnClickListener() 
		{
			@Override
			public void onClick(View v)
			{
				dismissDialog(LOGIN_DIALOG);
				
				showDialog(REGISTER_DIALOG);
			}
		});
		
		Button cancelBtn = (Button)dialog.findViewById(R.id.login_dialog_cancel_btn);
		cancelBtn.setOnClickListener(new OnClickListener() 
		{
			@Override
			public void onClick(View v) 
			{
				dismissDialog(LOGIN_DIALOG);
			}
		});
		
		return dialog;
	}
	
	public Dialog createRegisterDialog()
	{
		final Dialog dialog = new Dialog(this, R.style.NoTitleDialog);
		dialog.setContentView(R.layout.welcome_register_dialog);
		dialog.setOnDismissListener(new OnDismissListener() {
			@Override
			public void onDismiss(DialogInterface dialog) {
				removeDialog(REGISTER_DIALOG);
			}
		});
		
		Button okBtn = (Button) dialog.findViewById(R.id.register_dialog_ok_btn);
		okBtn.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				EditText loginEditText = (EditText)dialog.findViewById(R.id.register_dialog_login_edit);
				String tmpLogin = loginEditText.getText().toString();
				
				EditText passEditText = (EditText)dialog.findViewById(R.id.register_dialog_pass_edit);
				String tmpPass= passEditText.getText().toString();
				
				EditText tmpRepeatPassEditText = (EditText)dialog.findViewById(R.id.register_dialog_pass_repeat_edit);
				String tmpRepeatPass = tmpRepeatPassEditText.getText().toString();
				
				EditText mailEditText = (EditText)dialog.findViewById(R.id.register_dialog_mail_edit);
				String tmpMail = mailEditText.getText().toString();
				
				if(checkMail(tmpMail))
				{
					if(tmpPass.length() > 4)
					{
						if(tmpPass.equals(tmpRepeatPass))
						{
							if(tmpLogin.length() > 2 && tmpLogin.length() < 13)
							{
								dismissDialog(REGISTER_DIALOG);
								
								String crtPlrURL = XMLCreator.createCrtPlrURL(tmpLogin, tmpPass, tmpMail);
								new HTTPGetAsyncTask(XMLType.CRT_PLR_HTTP).execute(crtPlrURL);
								
								showWaitDialog(R.string.wait_register);
							}
							else
								Toast.makeText(WelcomeActivity.this, "Nick jest za krotki badz za dlugi. Minimum 3 znaki. Maximum 12 znakow.", Toast.LENGTH_SHORT).show();
						}
						else
							Toast.makeText(WelcomeActivity.this, "Hasla nie sa takie same. Wpisz ponownie.", Toast.LENGTH_SHORT).show();
					}
					else
						Toast.makeText(WelcomeActivity.this, "Haslo jest za krotkie. Minimum 5 znakow.", Toast.LENGTH_SHORT).show();
				}
				else
					Toast.makeText(WelcomeActivity.this, "Podales niepoprawny adres mail.", Toast.LENGTH_SHORT).show();
			}
		});
		
		Button cancelBtn = (Button) dialog.findViewById(R.id.register_dialog_cancel_btn);
		cancelBtn.setOnClickListener(new OnClickListener() 
		{
			@Override
			public void onClick(View v) 
			{
				dismissDialog(REGISTER_DIALOG);
			}
		});
		
		return dialog;
	}
	
	public void createPlayBtnListner()
	{
		Button playButon = (Button)findViewById(R.id.welcome_activity_play_btn);
		
		playButon.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				if(PlayerAllInfo.getInstance().isLogenOn())
				{
					Intent intent = new Intent(WelcomeActivity.this, GameScreenActivity.class);
					startActivity(intent);
				}
				else
					showDialog(LOGIN_DIALOG);
			}
		});
	}
	
	/**
	 * Dodanie listnerka do Login Btn
	 */
	public void createLoginBtnListner()
	{
		Button loginButon = (Button)findViewById(R.id.welcome_activity_login_btn);
		
		loginButon.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				showDialog(LOGIN_DIALOG);
			}
		});
	}
	
	public void setWelcomeText(String text)
	{
		TextView welcomeTextView = (TextView)findViewById(R.id.welcome_activity_welcome_txt);
		welcomeTextView.setText("Witaj " + text + " !!!");
		welcomeTextView.invalidate();
	}
	
	public void createCreditsBtnListner()
	{
		Button creditsButon = (Button)findViewById(R.id.welcome_activity_credits_btn);
		
		creditsButon.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				;//Intent intent = new Intent(WelcomeActivity.this, FirstTeamSelectionActivity.class);
				//startActivity(intent);
			}
		});
	}
	
	/*
	public void createDemoBtnListner()
	{
		Button demoButon = (Button)findViewById(R.id.welcome_activity_demo_btn);
		
		demoButon.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				;//
			}
		});
	}
	*/
	
	private boolean checkMail(String _tmpMail)
	{
		Pattern pattern = Pattern.compile("[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,4}", Pattern.CASE_INSENSITIVE | Pattern.UNICODE_CASE);
		Matcher matcher = pattern.matcher(_tmpMail);
		if(matcher.matches())
		{
		    return true;
		}
		return false;
	}

	/**
	 * OnCompleteDownloadListner dla WelcomeActivity wsadzany do NewNetworkHTTP() class.
	 * 
	 * @author user
	 */
	class WelcomeOnCompleteDownloadListner implements OnCompleteDownloadListner
	{
		public void downloadComplete(XMLType _flag, HTTPRespond _respond)
		{
			switch(_flag)
			{
			case LOGIN_HTTP:
				dissmisWaitDialog();
				if(_respond.getErrorCode() != -1)
				{
					//PlayerAllInfo.getInstance().setLogenOn(false);
					
					if(!loginFromSheared)
					{
						showErrorDialog(_respond.getErrorMSG());
					}
				}
				else
				{
					PlayerAllInfo.getInstance().setLogenOn(true);
					
					setWelcomeText(PlayerAllInfo.getInstance().getLogin());
					
					AppSheredPreferences.getInstance().setMailAndPass();
				}
				loginFromSheared = false;
				break;
			case CRT_PLR_HTTP:
				if(_respond.getErrorCode() != -1)
				{
					dissmisWaitDialog();
					showErrorDialog(_respond.getErrorMSG());
				}
				else
				{
					CreatePlayerRespondXML createPlayerRespond = (CreatePlayerRespondXML)_respond;
					
					if(createPlayerRespond.getNick() != null)
					{
						String loginURL = XMLCreator.createLoginURL(createPlayerRespond.getMail(), createPlayerRespond.getToken());
						new HTTPGetAsyncTask(XMLType.LOGIN_HTTP).execute(loginURL);
						
						showWaitDialog(R.string.wait_login);
					}
				}
				break;
			}
		}
	}
	
//--------------------------DO PRZERZUCENIA GDZIES-------------------------
    public boolean isOnline()
    {
    	 ConnectivityManager cm = (ConnectivityManager)this.getSystemService(Context.CONNECTIVITY_SERVICE);
    	 if(cm == null)
    		 return false;
    	 NetworkInfo wifi = cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
    	 NetworkInfo mobile = cm.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
    	 if(wifi.isConnected())
    		 return true; 
    	 else if(mobile.isConnected())
    		 return true;
    	 return false;
    };
}

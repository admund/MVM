package pl.admund.MVM_Client.main;

import pl.admund.MVM_Client.R;
import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnDismissListener;
import android.os.Bundle;
import android.widget.TextView;

public class MyActivity extends Activity
{
	private static final int ERROR_DIALOG = 98;
	private static final int WAIT_DIALOG = 99;
	
	private boolean isWaitDialog = false;
	private boolean isErrorDialog = false;
	
	@Override
	protected Dialog onCreateDialog(int id, Bundle args) 
	{
		switch(id)
		{
			case WAIT_DIALOG:
			{
				return createWaitDialog(args);
			}
			case ERROR_DIALOG:
			{
				return createErrorDialog(args);
			}
			default:
			{
				return null;
			}
		}
	}
	
	public Dialog createErrorDialog(Bundle args)
	{
		Dialog dialog = new Dialog(this, R.style.NoTitleDialog);
		dialog.setContentView(R.layout.std_error_dialog);
		dialog.setOnDismissListener(new OnDismissListener() {
			@Override
			public void onDismiss(DialogInterface dialog) {
				isErrorDialog = false;
				removeDialog(ERROR_DIALOG);
			}
		});
		
		TextView textview = (TextView)dialog.findViewById(R.id.std_error_dialog_text);
		textview.setText(args.getString("MSG"));
		isErrorDialog = true;
		return dialog;
	}
	
	public Dialog createWaitDialog(Bundle args)
	{
		Dialog dialog = new Dialog(this, R.style.NoTitleDialog);
		dialog.setContentView(R.layout.std_wait_dialog);
		dialog.setOnDismissListener(new OnDismissListener() {
			@Override
			public void onDismiss(DialogInterface dialog) {
				isWaitDialog = false;
				removeDialog(WAIT_DIALOG);		
			}
		});
		
		TextView textview = (TextView)dialog.findViewById(R.id.std_wait_dialog_text);
		textview.setText(args.getString("MSG"));
		isWaitDialog = true;
		return dialog;
	}
	
	public void showWaitDialog(int waitTextRes)
	{
		Bundle args = new Bundle();
		args.putString("MSG", getString(waitTextRes));
		showDialog(WAIT_DIALOG, args);
	}
	
	public void showWaitDialog(String waitText)
	{
		Bundle args = new Bundle();
		args.putString("MSG", waitText);
		showDialog(WAIT_DIALOG, args);
	}
	
	public void dissmisWaitDialog()
	{
		if(isWaitDialog)
			dismissDialog(WAIT_DIALOG);
	}
	
	public void showErrorDialog(int errorTextRes)
	{
		Bundle args = new Bundle();
		args.putString("MSG", getString(errorTextRes));
		showDialog(ERROR_DIALOG, args);
	}
	
	public void showErrorDialog(String errorText)
	{
		Bundle args = new Bundle();
		args.putString("MSG", errorText);
		showDialog(ERROR_DIALOG, args);
	}
	
	public void dissmisErrorDialog()
	{
		if(isErrorDialog)
			dismissDialog(ERROR_DIALOG);
	}
}

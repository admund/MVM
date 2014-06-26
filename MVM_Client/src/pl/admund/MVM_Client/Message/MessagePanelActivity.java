package pl.admund.MVM_Client.Message;

import java.util.ArrayList;
import pl.admund.MVM_Client.R;
import pl.admund.MVM_Client.PlayMatch.PlayMatchActivity;
import pl.admund.MVM_Client.Utils.GlobalDate;
import pl.admund.MVM_Client.XMLResponds.GetMessagesRespondXML;
import pl.admund.MVM_Client.XMLResponds.GetPlayerProfileRespondXML;
import pl.admund.MVM_Client.XMLResponds.HTTPRespond;
import pl.admund.MVM_Client.XMLUtils.HTTPGetAsyncTask;
import pl.admund.MVM_Client.XMLUtils.NetworkHTTP;
import pl.admund.MVM_Client.XMLUtils.OnCompleteDownloadListner;
import pl.admund.MVM_Client.XMLUtils.XMLCreator;
import pl.admund.MVM_Client.XMLUtils.XMLType;
import pl.admund.MVM_Client.main.GameScreenActivity;
import pl.admund.MVM_Client.main.MyActivity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnDismissListener;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

public class MessagePanelActivity extends MyActivity
{
	private ListView messageListView = null;
	private ArrayList<MessageClnt> msgList = new ArrayList<MessageClnt>();
	
	final public static int DELETE_MESSAGE_DIALOG = 0;
	final public static int SEND_MESSAGE_DIALOG = 1;
	final public static int VIEW_PROFILE_DIALOG = 2;
	final public static int MSG_NORMAL_DIALOG = 3;
	final public static int MSG_REQ_DIALOG = 4;
	final public static int MSG_PROP_DIALOG = 5;
	final public static int MSG_ACCEPT_DIALOG = 6;
	final public static int MSG_MATCH_DIALOG  = 7;
	
	//private final int ERROR_DIALOG = 98;
	//private final int WAIT_DIALOG = 99;
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		//getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		
		setContentView(R.layout.msg_message_panel_layout);
		
		String url = XMLCreator.createGetMsgURL();
		new HTTPGetAsyncTask(XMLType.GET_MSG_HTTP).execute(url);
		
		showWaitDialog(R.string.wait_get_msg);
	}
	
	@Override
	protected void onResume()
	{
		super.onResume();
		NetworkHTTP.getInstance().setOnCompleteDownloadListner(new MessagePanelOnCompleteDownloadListner());
	}
	
	@Override
	protected Dialog onCreateDialog(int id, Bundle bundle) 
	{ 
		Dialog dialog = super.onCreateDialog(id, bundle);
		if(dialog != null)
			return dialog;
		
		switch (id)
		{
			case SEND_MESSAGE_DIALOG:
			{
				return createSendMsgDialog(bundle);
			}
			case DELETE_MESSAGE_DIALOG:
			{
				return createDeleteMsgDialog(bundle);
			}
			case VIEW_PROFILE_DIALOG:
			{
				return createProfileDialog(bundle);
			}
			case MSG_NORMAL_DIALOG:
			{
				return createNormalMsgDialog(bundle);
			}
			case MSG_REQ_DIALOG:
			{
				return createMatchReqMsgDialog(bundle);
			}
			case MSG_PROP_DIALOG:
			{
				return createMatchPropMsgDialog(bundle);
			}
			case MSG_ACCEPT_DIALOG:
			{
				return createMatchAcceptMsgDialog(bundle);
			}
			case MSG_MATCH_DIALOG:
			{
				return createMatchDialog(bundle);
			}
		}
		return null;
	}
	
	private Dialog createSendMsgDialog(Bundle bundle)
	{
		final Dialog sendDialog = new Dialog(this, R.style.NoTitleDialog);
		sendDialog.setContentView(R.layout.msg_send_message_dialog);
		sendDialog.setOnDismissListener(new OnDismissListener(){
			@Override
			public void onDismiss(DialogInterface dialog) {
				removeDialog(SEND_MESSAGE_DIALOG);
			}
		});
		
		final int uid = bundle.getInt("UID");
		String senderName = bundle.getString("SENDER");
		
		//Log.d("ListVi", "Show " + uid + " " + senderName);
		
		TextView text = (TextView) sendDialog.findViewById(R.id.send_message_text);
		text.setText("Wys³asz wiadomosci do u¿ytkownika:\n" + senderName);
		
		final EditText msgEdit = (EditText)sendDialog.findViewById(R.id.send_message_msg_edit);
		
		Button sendBtn = (Button) sendDialog.findViewById(R.id.send_message_send_button);
		sendBtn.setOnClickListener(new OnClickListener() 
		{
			@Override
			public void onClick(View v) 
			{
				String msg = msgEdit.getText().toString();
				if(msg.length() > 0)
				{
					String url = XMLCreator.createSendMsgURL(uid, MessageClnt.MSG_TYPE_NORMAL, msg);
					new HTTPGetAsyncTask(XMLType.SEND_MSG_HTTP).execute(url);
					
					sendDialog.dismiss();
				}
			}
		});
		
		Button cancelBtn = (Button) sendDialog.findViewById(R.id.send_message_cancel_button);
		cancelBtn.setOnClickListener(new OnClickListener() 
		{
			@Override
			public void onClick(View v)
			{
				sendDialog.dismiss();
			}
		});
		
		return sendDialog;
	}
	
	private Dialog createDeleteMsgDialog(Bundle bundle)
	{
		final Dialog delDialog = new Dialog(this, R.style.NoTitleDialog);
		delDialog.setContentView(R.layout.yes_no_dialog);
		delDialog.setOnDismissListener(new OnDismissListener(){
			@Override
			public void onDismiss(DialogInterface dialog) {
				removeDialog(DELETE_MESSAGE_DIALOG);
			}
		});
		
		final int mid = bundle.getInt("MID");
		
		TextView text = (TextView)delDialog.findViewById(R.id.yes_no_dialog_main_text);
		text.setText("Czy napewno chcesz usun¹æ wiadomoœæ?");

		Button sendBtn = (Button) delDialog.findViewById(R.id.yes_no_dialog_yes_button);
		sendBtn.setOnClickListener(new OnClickListener() 
		{
			@Override
			public void onClick(View v) 
			{
				String url = XMLCreator.createDelMsgURL(mid);
				new HTTPGetAsyncTask(XMLType.DEL_MSG_HTTP).execute(url);
				
				deleteMsgRow(mid);

				messageListView.setAdapter(new MessageListAdapter(MessagePanelActivity.this, R.layout.msg_message_row, 0, msgList));

				dismissDialog(DELETE_MESSAGE_DIALOG);
			}
		});
		
		Button cancelBtn = (Button) delDialog.findViewById(R.id.yes_no_dialog_no_button);
		cancelBtn.setOnClickListener(new OnClickListener() 
		{
			@Override
			public void onClick(View v) 
			{
				dismissDialog(DELETE_MESSAGE_DIALOG);
			}
		});
		
		return delDialog;
	}
	
	private Dialog createProfileDialog(Bundle bundle)
	{
		final Dialog profileDialog = new Dialog(this, R.style.NoTitleDialog);
		profileDialog.setContentView(R.layout.player_profile_layout_dialog);
		profileDialog.setOnDismissListener(new OnDismissListener(){
			@Override
			public void onDismiss(DialogInterface dialog) {
				removeDialog(VIEW_PROFILE_DIALOG);
			}
		});
		
		final int id = bundle.getInt("ID");
		final String nick = bundle.getString("NICK");
		int teamId = bundle.getInt("TID");
		String teamName = bundle.getString("T_NAME");
		int leagueId = bundle.getInt("LID");
		String leagueName = bundle.getString("L_NAME");
		
		TextView idTextView = (TextView)profileDialog.findViewById(R.id.player_profil_dialog_user_id_text);
		idTextView.setText("" + id);
		
		TextView nickTextView = (TextView)profileDialog.findViewById(R.id.player_profil_dialog_nick_text);
		nickTextView.setText("Login:\t\t" + nick);
		
		TextView tidTextView = (TextView)profileDialog.findViewById(R.id.player_profil_dialog_team_id_text);
		tidTextView.setText("" + teamId);
		
		TextView teamTextView = (TextView)profileDialog.findViewById(R.id.player_profil_dialog_team_text);
		teamTextView.setText("Druzyna: \t" + teamName);
		
		TextView lidTextView = (TextView)profileDialog.findViewById(R.id.player_profil_dialog_league_id_text);
		lidTextView.setText("" + leagueId);
		
		TextView leagueTextView = (TextView)profileDialog.findViewById(R.id.player_profil_dialog_league_text);
		leagueTextView.setText("Liga:\t\t" + leagueName);
		
		Button sendBtn = (Button)profileDialog.findViewById(R.id.player_profil_dialog_send_btn);
		sendBtn.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v) 
			{
				Bundle bundle = new Bundle();
				
				bundle.putInt("UID", id);
				bundle.putString("SENDER", nick);
				
				showDialog(MessagePanelActivity.SEND_MESSAGE_DIALOG, bundle);
			}
		});
		
		Button cancelBtn = (Button)profileDialog.findViewById(R.id.player_profil_dialog_cancel_btn);
		cancelBtn.setOnClickListener(new OnClickListener() 
		{
			@Override
			public void onClick(View v) 
			{
				dismissDialog(VIEW_PROFILE_DIALOG);
			}
		});
		
		return profileDialog;
	}
	
	private Dialog createNormalMsgDialog(Bundle bundle)
	{
		final Dialog msgDialog = new Dialog(this, R.style.NoTitleDialog);
		msgDialog.setOnDismissListener(new OnDismissListener(){
			@Override
			public void onDismiss(DialogInterface dialog) {
				removeDialog(MSG_NORMAL_DIALOG);
			}
		});
		
		String msg = bundle.getString("MSG");
		final int id = bundle.getInt("UID");
		final String nick = bundle.getString("SENDER");
		
		LinearLayout lin = new LinearLayout(this);
		lin.setOrientation(LinearLayout.VERTICAL);
		lin.setPadding(15, 15, 15, 15);
		
		TextView msgText = new TextView(this);
		msgText.setText(msg);
		
		Button sendButton = new Button(this);
		sendButton.setText("ODPOWIEDZ");
		sendButton.setOnClickListener(new OnClickListener() {	
			@Override
			public void onClick(View v){
				dismissDialog(MSG_NORMAL_DIALOG);
				
				Bundle bundle = new Bundle();
				
				bundle.putInt("UID", id);
				bundle.putString("SENDER", nick);
				
				showDialog(SEND_MESSAGE_DIALOG, bundle);
			}
		});
		
		Button cancelButton = new Button(this);
		cancelButton.setText("ANULUJ");
		cancelButton.setOnClickListener(new OnClickListener() {	
			@Override
			public void onClick(View v){
				dismissDialog(MSG_NORMAL_DIALOG);
			}
		});
		
		lin.addView(msgText);
		
		LinearLayout linBtn = new LinearLayout(this);
		linBtn.setOrientation(LinearLayout.HORIZONTAL);
		
		linBtn.addView(sendButton);
		linBtn.addView(cancelButton);
		
		lin.addView(linBtn);
		
		msgDialog.setContentView(lin);
		
		return msgDialog;
	}
	
	private Dialog createMatchAcceptMsgDialog(Bundle bundle)
	{
		final Dialog msgDialog = new Dialog(this, R.style.NoTitleDialog);
		msgDialog.setOnDismissListener(new OnDismissListener()
		{
			@Override
			public void onDismiss(DialogInterface dialog) {
				removeDialog(MSG_ACCEPT_DIALOG);
			}
		});
		
		String msg = bundle.getString("MSG");
		//FORMAT:  MATCH_ID # OPPONENT_ID # OPPONENT_NAME # LEAGUE_NAME # SEASON # ROUND # TERM
		String[] strTab = msg.split("#");
		String text = "Termin meczu z " + strTab[2] + " w lidze " + strTab[3] + " sezon " + strTab[4] + " runda " + strTab[5] +
				"\nzostal wybrany na: " + strTab[6] + ". POWODZENIA!!!";
		
		LinearLayout lin = new LinearLayout(this);
		lin.setOrientation(LinearLayout.VERTICAL);
		lin.setPadding(15, 15, 15, 15);
		
		TextView msgText = new TextView(this);
		msgText.setText(text);
		
		Button cancelButton = new Button(this);
		cancelButton.setText("OK");
		cancelButton.setOnClickListener(new OnClickListener() {	
			@Override
			public void onClick(View v){
				dismissDialog(MSG_ACCEPT_DIALOG);
			}
		});
		
		lin.addView(msgText);
		lin.addView(cancelButton);
		
		msgDialog.setContentView(lin);
		
		return msgDialog;
	}
	
	private Dialog createMatchDialog(Bundle bundle)
	{
		final Dialog matchDialog = new Dialog(this, R.style.NoTitleDialog);
		matchDialog.setContentView(R.layout.yes_no_dialog);
		matchDialog.setOnDismissListener(new OnDismissListener(){
			@Override
			public void onDismiss(DialogInterface dialog) {
				removeDialog(MSG_MATCH_DIALOG);
			}
		});
		
		String msg = bundle.getString("MSG");
		
		TextView text = (TextView)matchDialog.findViewById(R.id.yes_no_dialog_main_text);
		text.setText(msg);

		Button sendBtn = (Button) matchDialog.findViewById(R.id.yes_no_dialog_yes_button);
		sendBtn.setText("GRAJ MECZ");
		sendBtn.setOnClickListener(new OnClickListener() 
		{
			@Override
			public void onClick(View v) 
			{
				Intent intent = new Intent(MessagePanelActivity.this, PlayMatchActivity.class);
				startActivity(intent);
				
				dismissDialog(MSG_MATCH_DIALOG);
			}
		});
		
		Button cancelBtn = (Button)matchDialog.findViewById(R.id.yes_no_dialog_no_button);
		cancelBtn.setText("ANULUJ");
		cancelBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v){
				dismissDialog(MSG_MATCH_DIALOG);
			}
		});
		
		return matchDialog;
	}
	
	//private int acceptedTermCnt;
	private ArrayList<String> acceptedTermList = new ArrayList<String>();
	private Dialog createMatchReqMsgDialog(final Bundle bundle)
	{
		acceptedTermList.clear();
		final Dialog msgDialog = new Dialog(this, R.style.NoTitleDialog);
		msgDialog.setOnDismissListener(new OnDismissListener(){
			@Override
			public void onDismiss(DialogInterface dialog) {
				removeDialog(MSG_REQ_DIALOG);
			}
		});
		
		LinearLayout mainLayout = new LinearLayout(this);
		mainLayout.setOrientation(LinearLayout.VERTICAL);
		String msg = bundle.getString("MSG");
		//FORMAT:  MATCH_ID # OPPONENT_ID # OPPONENT_NAME # LEAGUE_NAME # SEASON # ROUND
		String[] strTab = msg.split("#");
		final int matchId = Integer.parseInt(strTab[0]);
		String text = "Wybierz termin meczu z " + strTab[2] + " w lidze " + strTab[3] + " sezon " + strTab[4] + " runda " + strTab[5] + "!!!" +
				"\nAktulana data i czas: " + GlobalDate.getCurrentDateAndTime() + " (mozesz wybrac 3 daty)" +
				"\n\tRok:\t\t\t\tMiesiac:\t\t\t\tDzien\t\t\t\tGodzina:\t\t\t\tMinuta";
		TextView msgText = new TextView(this);
		msgText.setText(text);
		msgText.setPadding(15, 15, 15, 15);
		mainLayout.addView(msgText);
		
		LinearLayout dateLayout = new LinearLayout(this);
		dateLayout.setOrientation(LinearLayout.HORIZONTAL);
		final Spinner yearSpinner = new Spinner(this);
		addToSpiner(yearSpinner, 10, 2012);
		yearSpinner.setSelection(0);
		final Spinner monthSpinner = new Spinner(this);
		addToSpiner(monthSpinner, 12, 1);
		monthSpinner.setSelection(GlobalDate.getCurrentMonth()-1);
		final Spinner daySpinner = new Spinner(this);
		addToSpiner(daySpinner, 31, 1);
		daySpinner.setSelection(GlobalDate.getCurrentDay()-1);
		dateLayout.addView(yearSpinner);
		dateLayout.addView(monthSpinner);
		dateLayout.addView(daySpinner);
		mainLayout.addView(dateLayout);
		
		LinearLayout timeLayout = new LinearLayout(this);
		timeLayout.setOrientation(LinearLayout.HORIZONTAL);
		final Spinner hourSpinner = new Spinner(this);
		addToSpiner(hourSpinner, 24, 0);
		hourSpinner.setSelection(GlobalDate.getCurrentHour());
		final Spinner minSpinner = new Spinner(this);
		addToSpiner(minSpinner, 60, 0);
		minSpinner.setSelection(GlobalDate.getCurrentMin());
		dateLayout.addView(hourSpinner);//timeLayout
		dateLayout.addView(minSpinner);//timeLayout
		//mainLayout.addView(timeLayout);
		
		final TextView term1 = new TextView(this);
		final TextView term2 = new TextView(this);
		final TextView term3 = new TextView(this);
		
		LinearLayout btnLayout = new LinearLayout(this);
		btnLayout.setOrientation(LinearLayout.HORIZONTAL);
		Button addBtn = new Button(this);
		addBtn.setText("DODAJ TERMIN");
		addBtn.setOnClickListener(new OnClickListener() 
		{
			@Override
			public void onClick(View v) 
			{
				if(acceptedTermList.size() == 3)
					return;
				
				boolean accept = false;
				if(GlobalDate.getCurrentYear() < (Integer)(yearSpinner.getSelectedItem()))
					accept = true;
				else if(GlobalDate.getCurrentMonth() < (Integer)(monthSpinner.getSelectedItem()))
					accept = true;
				else if(GlobalDate.getCurrentDay() < (Integer)(daySpinner.getSelectedItem()))
					accept = true;
				else if(GlobalDate.getCurrentHour() < (Integer)(hourSpinner.getSelectedItem()))
					accept = true;
				
				if(accept)
				{
					String hour;
					String min;
					if((Integer)(hourSpinner.getSelectedItem()) < 10)
						hour = "0"+(Integer)(hourSpinner.getSelectedItem());
					else
						hour = ""+(Integer)(hourSpinner.getSelectedItem());
					
					if((Integer)(minSpinner.getSelectedItem()) < 10)
						min = "0"+(Integer)(minSpinner.getSelectedItem());
					else
						min = ""+(Integer)(minSpinner.getSelectedItem());
					
					String date = ""+(Integer)(yearSpinner.getSelectedItem())+"/"+(Integer)(monthSpinner.getSelectedItem()) +
							"/"+(Integer)(daySpinner.getSelectedItem())+" "+hour+":"+min+":00";
					acceptedTermList.add(date);
					if(acceptedTermList.size() == 1)
						term1.setText("Termin 1: " + date);
					else if(acceptedTermList.size() == 2)
						term2.setText("Termin 2: " + date);
					else if(acceptedTermList.size() == 3)
						term3.setText("Termin 3: " + date);
				}
				
			}
		});
		btnLayout.addView(addBtn);
		Button resetBtn = new Button(this);
		resetBtn.setText("USUÑ TERMINY");
		resetBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) 
			{
				acceptedTermList.clear();
				
				term1.setText("Termin 1: ");
				term2.setText("Termin 2: ");
				term3.setText("Termin 3: ");
			}
		});
		btnLayout.addView(resetBtn);
		
		Button sendBtn = new Button(this);
		sendBtn.setText("WYŒLIJ");
		sendBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v)
			{
				if(acceptedTermList.size() == 3)
				{
					int propCnt = bundle.getInt("PROP_CNT");
					/*int receiverId = bundle.getInt("SENDER_ID");
					String msg = "1#" + matchId + "#" + acceptedTermList.get(0) + "#" + acceptedTermList.get(1) + "#" + acceptedTermList.get(2);
					String sendMsg = NetworkHTTP.createSendMsgURL(receiverId, MessageClnt.MSG_TYPE_MATCH_PROP, msg);
					new HTTPAsyncTask(HTTPType.SEND_MSG_HTTP).execute(sendMsg);*/
					String terms = acceptedTermList.get(0) + "#" + acceptedTermList.get(1) + "#" + acceptedTermList.get(2);
					String matchTermReq = XMLCreator.createMatchPropURL(matchId, propCnt, terms);
					new HTTPGetAsyncTask(XMLType.MATCH_PROP_HTTP).execute(matchTermReq);
					
					dismissDialog(MSG_REQ_DIALOG);
				}
			}
		});
		btnLayout.addView(sendBtn);
		mainLayout.addView(btnLayout);
		
		term1.setText("Termin 1: ");
		mainLayout.addView(term1);
		term2.setText("Termin 2: ");
		mainLayout.addView(term2);
		term3.setText("Termin 3: ");
		mainLayout.addView(term3);
		
		msgDialog.setContentView(mainLayout);
		
		return msgDialog;
	}
	
	private Dialog createMatchPropMsgDialog(final Bundle bundle)
	{
		final Dialog msgDialog = new Dialog(this, R.style.NoTitleDialog);
		msgDialog.setOnDismissListener(new OnDismissListener(){
			@Override
			public void onDismiss(DialogInterface dialog) {
				removeDialog(MSG_PROP_DIALOG);
			}
		});
		
		LinearLayout mainLayout = new LinearLayout(this);
		mainLayout.setOrientation(LinearLayout.VERTICAL);
		String msg = bundle.getString("MSG");
		String sender = bundle.getString("SENDER");
		final int senderId = bundle.getInt("SENDER_ID");
		//FORMAT:  MATCH_ID # OPPONENT_ID # OPPONENT_NAME # LEAGUE_NAME # SEASON # ROUND # PROP_CNT # TERM_1 # TERM_2 # TERM_3
		final String[] strTab = msg.split("#");
		final int matchId = Integer.parseInt(strTab[0]);
		
		String text = "Wybierz termin meczu z " + strTab[2] + " w lidze " + strTab[3] + " sezon " 
		+ strTab[4] + " runda " + strTab[5] + "!!! Przeciwnik zaproponowal:";
		TextView msgText = new TextView(this);
		msgText.setText(text);
		msgText.setPadding(15, 15, 15, 15);
		mainLayout.addView(msgText);
		
		LinearLayout dateLayout = new LinearLayout(this);
		dateLayout.setOrientation(LinearLayout.HORIZONTAL);
		
		final RadioGroup termRadioGroup = new RadioGroup(this);
		final RadioButton term1 = new RadioButton(this);
		term1.setText(strTab[7]);
		final RadioButton term2 = new RadioButton(this);
		term2.setText(strTab[8]);
		final RadioButton term3 = new RadioButton(this);
		term3.setText(strTab[9]);
		termRadioGroup.addView(term1);
		termRadioGroup.addView(term2);
		termRadioGroup.addView(term3);
		mainLayout.addView(termRadioGroup);
		
		LinearLayout btnLayout = new LinearLayout(this);
		btnLayout.setOrientation(LinearLayout.HORIZONTAL);
		Button acceptBtn = new Button(this);
		acceptBtn.setText("AKCEPTUJ");
		acceptBtn.setOnClickListener(new OnClickListener() 
		{
			@Override
			public void onClick(View v) 
			{
				int selectedBoxId = termRadioGroup.getCheckedRadioButtonId();
				if(selectedBoxId != -1)
				{
					Log.d("MSG ID", ""+selectedBoxId);
					String selectedTerm = null;
					if(selectedBoxId == term1.getId())
					{
						selectedTerm = strTab[7];
					}
					else if(selectedBoxId == term2.getId())
					{
						selectedTerm = strTab[8];
					}
					else if(selectedBoxId == term3.getId())
					{
						selectedTerm = strTab[9];
					}
					Log.d("MSG str", "term: "+selectedTerm + " match_id: " + matchId);
					
					String url = XMLCreator.createMatchAcceptURL(matchId, selectedTerm);
					new HTTPGetAsyncTask(XMLType.MATCH_ACCEPT_HTTP).execute(url);
					dismissDialog(MSG_PROP_DIALOG);
				}
			}
		});
		btnLayout.addView(acceptBtn);
		Button changeBtn = new Button(this);
		changeBtn.setText("ZMIEÑ");
		changeBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v)
			{
				removeDialog(MSG_PROP_DIALOG);
				
				//bundle.putInt("MSG_TYPE", MessageClnt.MSG_TYPE_MATCH_REQ);
				showDialog(MSG_REQ_DIALOG, bundle);
			}
		});
		btnLayout.addView(changeBtn);
		mainLayout.addView(btnLayout);
		
		msgDialog.setContentView(mainLayout);
		
		return msgDialog;
	}
	
	public void addToSpiner(Spinner spinner, int cnt, int firstValue)
	{
		Integer[] sinnerArray = new Integer[cnt];
		for(int i=0; i < cnt; i++)
		{
			sinnerArray[i] = firstValue + i;
		}
		//ArrayAdapter<Integer> adapter = new ArrayAdapter<Integer>(this, android.R.layout.simple_spinner_item, sinnerArray);
		ArrayAdapter<Integer> adapter = new ArrayAdapter<Integer>(this, R.layout.spinner_item, sinnerArray){
            public View getView(int position, View convertView, ViewGroup parent) 
            {
                View v = super.getView(position, convertView, parent);
                //((TextView) v).setTextSize(16);

                return v;
            }

            public View getDropDownView(int position, View convertView, ViewGroup parent) 
            {
                View v = super.getDropDownView(position, convertView, parent);
                //((TextView) v).setGravity(Gravity.CENTER);
                return v;
            }
        };
		spinner.setAdapter(adapter);
	}
	
	class MessagePanelOnCompleteDownloadListner implements OnCompleteDownloadListner
	{
		@Override
		public void downloadComplete(XMLType flag, HTTPRespond respond)
		{
			switch(flag)
			{
			case GET_MSG_HTTP:
				dissmisWaitDialog();
				if(respond.getErrorCode() != -1)
				{
					showErrorDialog(respond.getErrorMSG());
				}
				else
				{
					GetMessagesRespondXML tmp = (GetMessagesRespondXML)respond;
					if(!tmp.isEmpty())
					{
						Log.d("MSG SIZE", "" + tmp.getSize());
						messageListView = (ListView)findViewById(R.id.message_panel_list);
						msgList = tmp.getMessageList();
						messageListView.setAdapter(new MessageListAdapter(MessagePanelActivity.this, R.layout.msg_message_row, 0, msgList));
					}
					else
					{	
						LinearLayout linLay = (LinearLayout)findViewById(R.id.message_panel_layout_root);
						ImageView image = new ImageView(MessagePanelActivity.this);
						image.setImageResource(R.drawable.no_msg);
						linLay.addView(image);
						linLay.invalidate();
					}
				}
				break;
			case GET_PROFILE_HTTP:
				dissmisWaitDialog();
				if(respond.getErrorCode() != -1)
				{
					showErrorDialog(respond.getErrorMSG());
				}
				else
				{
					GetPlayerProfileRespondXML tmp = (GetPlayerProfileRespondXML)respond;
					if(tmp.getUserId() != 0)
					{
						Log.d("PROFILE", tmp.getUserName() + "_ _" + tmp.getTeamName() + "_ _" + tmp.getLeagueName());

						Bundle bundle = new Bundle();
						
						bundle.putInt("ID", tmp.getUserId());
						bundle.putString("NICK", tmp.getUserName());
						bundle.putInt("TID", tmp.getTeamId());
						bundle.putString("T_NAME", tmp.getTeamName());
						bundle.putInt("LID", tmp.getLeagueId());
						bundle.putString("L_NAME", tmp.getLeagueName());
						
						showDialog(VIEW_PROFILE_DIALOG, bundle);
					}
				}
				break;
			}
		}
	}
	
	public void deleteMsgRow(int msgId)
	{
		for(int i=0; i<msgList.size(); i++)
		{
			Log.d("deleteMsgRow", "" + msgId + " " + msgList.get(i).getMsgId());
			if(msgList.get(i).getMsgId() == msgId)
			{
				Log.d("deleteMsgRow", "" + msgId);
				msgList.remove(i);
				return ;
			}
		}
	}
}
package pl.admund.MVM_Client.PlayMatch;

import java.util.ArrayList;
import pl.admund.MVM_Client.R;
import pl.admund.MVM_Client.TCP.NetworkTCP;
import pl.admund.MVM_Client.TCP.TCPMessageCmd;
import pl.admund.MVM_Client.Tactics.ChangeVolleyballerActivity;
import pl.admund.MVM_Client.Tactics.TacticActivity;
import pl.admund.MVM_Client.Volleyballer.VolleyballerClnt;
import pl.admund.MVM_Client.XMLResponds.GetTeamInfoXML;
import pl.admund.MVM_Client.XMLResponds.HTTPRespond;
import pl.admund.MVM_Client.XMLUtils.HTTPGetAsyncTask;
import pl.admund.MVM_Client.XMLUtils.NetworkHTTP;
import pl.admund.MVM_Client.XMLUtils.OnCompleteDownloadListner;
import pl.admund.MVM_Client.XMLUtils.XMLCreator;
import pl.admund.MVM_Client.XMLUtils.XMLType;
import pl.admund.MVM_Client.main.MyActivity;
import pl.admund.MVM_Client.main.PlayerAllInfo;
import pl.admund.MVM_Client.sportStuff.TeamClnt;
import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

public class PlayMatchActivity extends MyActivity
{
	private NetworkTCP newtworkTCP;
	private ArrayList<MatchAction> actionsList = new ArrayList<MatchAction>();
	private ScrollView actionsScrollView;
	private ActionsListViewAdapter adapter;
	private NetworkReceiver networkReceiver;
	
	private int homeTeamId;
	private TeamClnt homeTeam;
	
	private int awayTeamId;
	private TeamClnt awayTeam;
	
	private int waitTime = 0;
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		//getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		
		setContentView(R.layout.play_match_activity_layout);
		
		String url = XMLCreator.createGetNextMatchURL();
		new HTTPGetAsyncTask(XMLType.MATCH_NEXT_HTTP).execute(url);
		showWaitDialog("WAIT..");
		
		actionsScrollView = (ScrollView)findViewById(R.id.play_match_action_scroll_view);
		
		networkReceiver = new NetworkReceiver();
		
		IntentFilter filter = new IntentFilter("pl.admund.MVM_Client.tcpnetwork");
		this.registerReceiver(networkReceiver, filter);
	}
	
	@Override
	protected void onResume() 
	{
		super.onResume();
		NetworkHTTP.getInstance().setOnCompleteDownloadListner(new PlayMatchOnCompleteDownloadListner());
	}
	
	@Override
	protected Dialog onCreateDialog(int id, Bundle bundle) 
	{ 
		Dialog dialog = super.onCreateDialog(id, bundle);
		if(dialog != null)
			return dialog;
		
		switch (id)
		{
			default:
		}
		return null;
	}
	
	@Override
	protected void onDestroy() 
	{
		this.unregisterReceiver(networkReceiver);
		newtworkTCP.CloseSocket();
		super.onDestroy();
	}
	
	public void addActionToScroollView(MatchAction tmpMatchAction)
	{
		LinearLayout mainLayout = (LinearLayout)findViewById(R.id.play_match_action_scroll_view_lin);//new LinearLayout(this);
		//mainLayout.setOrientation(LinearLayout.VERTICAL);
		
		LinearLayout innerLayout = new LinearLayout(this);
		innerLayout.setOrientation(LinearLayout.VERTICAL);
		
		TextView actionNrTextView = new TextView(this);
        actionNrTextView.setText("Action s:" + actionsList.size() + " lp:" + tmpMatchAction.getLP());
        innerLayout.addView(actionNrTextView);
        
        //MatchAction tmpMatchAction = actionsList.get(position);
        for(int i=0; i<tmpMatchAction.getSingleActionsCnt(); i++)
        {
        	SingleMatchAction tmpSingleAction = tmpMatchAction.getSingleAction(i);
        	
            LinearLayout singleLayout = new LinearLayout(this);
            singleLayout.setOrientation(LinearLayout.HORIZONTAL);
            
            TextView textView = new TextView(this);
            String volleyballerName = getVolleyballerShortName(tmpSingleAction.getVolleyballerId());
            if(isHomeTeam(tmpSingleAction.getVolleyballerId()))
            	textView.setTextColor(Color.rgb(48, 255, 48));
            else
            	textView.setTextColor(Color.rgb(48, 48, 255));
            textView.setText(volleyballerName);
            textView.setMinWidth(200);
            
            ImageView actionImage = new ImageView(this);
            int imgRes = getImgRes(tmpSingleAction);
            actionImage.setBackgroundResource(imgRes);
            
            singleLayout.addView(textView);
            singleLayout.addView(actionImage);
            innerLayout.addView(singleLayout);
        }
        
        TextView endActionTextView = new TextView(this);
        endActionTextView.setText("<----------->\n");
        innerLayout.addView(endActionTextView);
        mainLayout.addView(innerLayout, 0);
        //actionsScrollView.addView(mainLayout, 0);
        actionsScrollView.invalidate();
	}
	
	public void addSetEndToScroollView(String setEndStr)
	{
		LinearLayout mainLayout = (LinearLayout)findViewById(R.id.play_match_action_scroll_view_lin);//new LinearLayout(this);
		//mainLayout.setOrientation(LinearLayout.VERTICAL);
		
		TextView textView = new TextView(this);
		textView.setText("\n" + setEndStr + "\n");
        
        mainLayout.addView(textView, 0);
        actionsScrollView.invalidate();
	}
	
	public void addTimeToScroollView(String timeStr)
	{
		LinearLayout mainLayout = (LinearLayout)findViewById(R.id.play_match_action_scroll_view_lin);//new LinearLayout(this);
		//mainLayout.setOrientation(LinearLayout.VERTICAL);
		
		TextView textView = new TextView(this);
		textView.setText("\n" + timeStr + "\n");
        
        mainLayout.addView(textView, 0);
        actionsScrollView.invalidate();
	}
	
	public void addChangeToScroollView(int volleyballer1, int volleyballer2)
	{
		LinearLayout mainLayout = (LinearLayout)findViewById(R.id.play_match_action_scroll_view_lin);//new LinearLayout(this);
		//mainLayout.setOrientation(LinearLayout.VERTICAL);
		
		String str = "Zmiana:\n" + getVolleyballerShortName(volleyballer1) + " --> " + getVolleyballerShortName(volleyballer2);
		
		TextView textView = new TextView(this);
		textView.setText("\n" + str + "\n");
        
        mainLayout.addView(textView, 0);
        actionsScrollView.invalidate();
	}
	
	public void addTacticToScroollView(int teamId)
	{
		LinearLayout mainLayout = (LinearLayout)findViewById(R.id.play_match_action_scroll_view_lin);//new LinearLayout(this);
		//mainLayout.setOrientation(LinearLayout.VERTICAL);
		
		String str = "Druzyna " + teamId + " zmienila taktyke";
		
		TextView textView = new TextView(this);
		textView.setText("\n" + str + "\n");
        
        mainLayout.addView(textView, 0);
        actionsScrollView.invalidate();
	}
	
	private int getImgRes(SingleMatchAction singleAction)
	{
		switch(singleAction.getActionType())
		{
			case ActionConst.ACTION_TYPE_SERVE:
			{
				if(singleAction.getActionResult() == ActionConst.ACTION_RESULT_CONTINUE)
					return R.drawable.ico_serv;
				else if(singleAction.getActionResult() == ActionConst.ACTION_RESULT_ERROR)
					return R.drawable.ico_serv_e;
				else
					return R.drawable.ico_serv_p;
			}
			case ActionConst.ACTION_TYPE_RECEPTION:
			{
				if(singleAction.getActionResult() == ActionConst.ACTION_RESULT_CONTINUE)
					return R.drawable.ico_recive;
				else if(singleAction.getActionResult() == ActionConst.ACTION_RESULT_ERROR)
					return R.drawable.ico_recive_e;
				else
					return R.drawable.ico_recive_p;
			}
			case ActionConst.ACTION_TYPE_SET:
			{
				if(singleAction.getActionResult() == ActionConst.ACTION_RESULT_CONTINUE)
					return R.drawable.ico_set;
				else if(singleAction.getActionResult() == ActionConst.ACTION_RESULT_ERROR)
					return R.drawable.ico_set_e;
				else
					return R.drawable.ico_set_p;
			}
			case ActionConst.ACTION_TYPE_ATACK:
			{
				if(singleAction.getActionResult() == ActionConst.ACTION_RESULT_CONTINUE)
					return R.drawable.ico_att;
				else if(singleAction.getActionResult() == ActionConst.ACTION_RESULT_ERROR)
					return R.drawable.ico_att_e;
				else
					return R.drawable.ico_att_p;
			}
			case ActionConst.ACTION_TYPE_BLOCK:
			{
				if(singleAction.getActionResult() == ActionConst.ACTION_RESULT_CONTINUE)
					return R.drawable.ico_blo;
				else if(singleAction.getActionResult() == ActionConst.ACTION_RESULT_ERROR)
					return R.drawable.ico_blo_e;
				else
					return R.drawable.ico_blo_p;
			}
		}
		return -1;
	}
	
	private String getVolleyballerShortName(int volleyballerId)
	{
		if(homeTeam != null)
		{
			ArrayList<VolleyballerClnt> homeVolleyballersList = homeTeam.getVolleyballersList();
			for(int i=0; i<homeVolleyballersList.size(); i++)
			{
				if(homeVolleyballersList.get(i).getId() == volleyballerId)
					return homeVolleyballersList.get(i).getShortName();
			}
		}
		
		if(awayTeam != null)
		{
			ArrayList<VolleyballerClnt> awayVolleyballersList = awayTeam.getVolleyballersList();
			for(int i=0; i<awayVolleyballersList.size(); i++)
			{
				if(awayVolleyballersList.get(i).getId() == volleyballerId)
					return awayVolleyballersList.get(i).getShortName();
			}
		}
		
		return "" + volleyballerId;
	}
	
	private boolean isHomeTeam(int volleyballerId)
	{
		if(homeTeam != null)
		{
			ArrayList<VolleyballerClnt> homeVolleyballersList = homeTeam.getVolleyballersList();
			for(int i=0; i<homeVolleyballersList.size(); i++)
			{
				if(homeVolleyballersList.get(i).getId() == volleyballerId)
					return true;
			}
			return false;
		}
		else
		{
			ArrayList<VolleyballerClnt> homeVolleyballersList = homeTeam.getVolleyballersList();
			for(int i=0; i<homeVolleyballersList.size(); i++)
			{
				if(homeVolleyballersList.get(i).getId() == volleyballerId)
					return false;
			}
			return true;
		}
	}
	
	private String getTeamName(int teamId)
	{
		if(homeTeam != null)
		{
			if(homeTeamId == teamId)
				return homeTeam.getTeamName();
			else
				if(awayTeam != null)
					return awayTeam.getTeamName();
		}
		else
		{
			if(awayTeam != null)
			{
				if(awayTeamId == teamId)
					return awayTeam.getTeamName();
			}
		}
		return "NULL";
	}
	
	public void onClickSquadBtn(View v)
	{
		Log.d("PLAY", "onClickSquadBtn()");
		
		Intent intent = new Intent(PlayMatchActivity.this, ChangeVolleyballerActivity.class);
		startActivity(intent);
	}
	
	public void onClickTacticBtn(View v)
	{
		Log.d("PLAY", "onClickTacticBtn()");
		
		Intent intent = new Intent(PlayMatchActivity.this, TacticActivity.class);
		intent.putExtra("FROM_MATCH", true);
		startActivity(intent);
	}
	
	public void onClickTimeBtn(View v)
	{
		Log.d("PLAY", "onClickTimeBtn()");
		if(waitTime == 0)
		{
			String timeMsg = TCPMessageCmd.TCP_MSG_TIME + "_" + PlayerAllInfo.getInstance().getPlayerId();
			newtworkTCP.SendData(timeMsg.getBytes());
		}
	}
	
	class PlayMatchOnCompleteDownloadListner implements OnCompleteDownloadListner
	{
		@Override
		public void downloadComplete(XMLType flag, HTTPRespond respond)
		{
			switch(flag)
			{
			case MATCH_NEXT_HTTP:
				dissmisWaitDialog();
				if(respond.getErrorCode() != -1)
				{
					showErrorDialog(respond.getErrorMSG());
				}
				else
				{
					Log.d("PLAYED", "JEST");
					//GetMessagesRespondXML tmp = (GetMessagesRespondXML)respond;
					newtworkTCP = NetworkTCP.getInstance();
					// TODO newtworkTCP.Init(dstAddr, str);
					newtworkTCP.Construct();
					newtworkTCP.Start();
					
					String joinMsg = TCPMessageCmd.TCP_MSG_JOIN + "_" + PlayerAllInfo.getInstance().getPlayerId();
					newtworkTCP.SendData(joinMsg.getBytes());
				}
				break;
			case GET_TEAM_INFO_HTTP:
				if(respond.getErrorCode() != -1)
				{
					showErrorDialog(respond.getErrorMSG());
				}
				else
				{
					Log.d("PLAYED", "GetTeamInfoXML");
					GetTeamInfoXML tmp = (GetTeamInfoXML)respond;
					if(tmp.getTeamInfo().getTeamId() == homeTeamId)
					{
						homeTeam = tmp.getTeamInfo();
						
						TextView homeIdTextView = (TextView)PlayMatchActivity.this.findViewById(R.id.play_match_home_team_id);
						homeIdTextView.setText("Gospodarz: " + homeTeam.getTeamName());
						homeIdTextView.invalidate();
					}
					else if(tmp.getTeamInfo().getTeamId() == awayTeamId)
					{
						awayTeam = tmp.getTeamInfo();
								
						TextView awayIdTextView = (TextView)PlayMatchActivity.this.findViewById(R.id.play_match_away_team_id);
						awayIdTextView.setText("Goœæ: " + awayTeam.getTeamName());
						awayIdTextView.invalidate();
					}
				}
			}
		}
	}
	
	private int cnt = 0;
	class NetworkReceiver extends BroadcastReceiver
	{
		@Override
		public void onReceive(Context context, Intent intent) 
		{
			Bundle bundle = intent.getExtras();
			int cmd = bundle.getInt("CMD");
			Log.d("TCP", "odebralem brod: " + cmd);
			switch(cmd)
			{
				case TCPMessageCmd.TCP_MSG_JOIN:
				{
					homeTeamId = bundle.getInt("HOME_ID");
					TextView homeIdTextView = (TextView)PlayMatchActivity.this.findViewById(R.id.play_match_home_team_id);
					//homeIdTextView.setText("Home id: " + homeTeamId);
					
					if(homeTeamId == PlayerAllInfo.getInstance().getPlayerId())
					{
						homeTeam = PlayerAllInfo.getInstance().getTeam();
						homeIdTextView.setText("Gospodarz: " + homeTeam.getTeamName());
					}
					else
					{
						String url1 = XMLCreator.createGetTeamInfoURL(homeTeamId);
						new HTTPGetAsyncTask(XMLType.GET_TEAM_INFO_HTTP).execute(url1);
					}
					
					awayTeamId = bundle.getInt("AWAY_ID");
					TextView awayIdTextView = (TextView)PlayMatchActivity.this.findViewById(R.id.play_match_away_team_id);
					//awayIdTextView.setText("Awaye id: " + awayTeamId);
					
					if(awayTeamId == PlayerAllInfo.getInstance().getPlayerId())
					{
						awayTeam = PlayerAllInfo.getInstance().getTeam();
						awayIdTextView.setText("Goœæ: " + awayTeam.getTeamName());
					}
					else
					{
						String url2 = XMLCreator.createGetTeamInfoURL(awayTeamId);
						new HTTPGetAsyncTask(XMLType.GET_TEAM_INFO_HTTP).execute(url2);
					}
				}
				break;
				case TCPMessageCmd.TCP_MSG_RESULT:
				{
					int homeSet = bundle.getInt("HOME_SET");
					int awaySet = bundle.getInt("AWAY_SET");
					int homePts = bundle.getInt("HOME_PTS");
					int awayPts = bundle.getInt("AWAY_PTS");
					
					TextView setTextView = (TextView)PlayMatchActivity.this.findViewById(R.id.play_match_set_result_text);
					setTextView.setText(homeSet + " : " + awaySet);
					setTextView.invalidate();
					
					TextView ptsTextView = (TextView)PlayMatchActivity.this.findViewById(R.id.play_match_little_result_text);
					ptsTextView.setText(homePts + " : " + awayPts);
					ptsTextView.invalidate();
				}
				break;
				case TCPMessageCmd.TCP_MSG_TIME:
				{
					int time = bundle.getInt("TIME");
					int whoTake = bundle.getInt("WHO");
					
					if(time == -1)
					{
						Toast.makeText(PlayMatchActivity.this, "Nie mozesz wziac wiecej czasow frajerze!!!", Toast.LENGTH_LONG).show();
					}
					else
					{
						waitTime = time;
						TextView timeTextView = (TextView)PlayMatchActivity.this.findViewById(R.id.play_match_time_txt);
						timeTextView.setText("Czas: " + time + "s");
						timeTextView.invalidate();
					}
					
					if(whoTake != 0)
					{
						String str = "";
						if(whoTake == -1)
							str = "Przerwa techniczna - " + time + "s";
						else if(whoTake == -2)
							str = "Koniec seta. Przerwa - " + time + "s";
						else
							str = "Druzyna " + getTeamName(whoTake) + " wziela czas - " + time + "s";
						
						addTimeToScroollView(str);
					}
				}
				break;
				case TCPMessageCmd.TCP_MSG_SET_END:
				{
					int setNr = bundle.getInt("SET_NR");
					String result = bundle.getString("RESULT");
					
					LinearLayout lin = (LinearLayout)PlayMatchActivity.this.findViewById(R.id.play_match_left_panel_layout);
					
					String str = "Set nr " + setNr + " : " + result;
					TextView setResultTextView = new TextView(PlayMatchActivity.this);
					setResultTextView.setText(str);
					
					lin.addView(setResultTextView);
					lin.invalidate();
					
					addTimeToScroollView(str);
				}
				break;
				case TCPMessageCmd.TCP_MSG_MATCH_END:
				{
					int winnerId = bundle.getInt("WIN");
					
					if(PlayerAllInfo.getInstance().getPlayerId() == winnerId)
						showErrorDialog("WYGRALES!!! :)");
					else
						showErrorDialog("PRZEGRALES... :(");
					
					NetworkTCP.getInstance().CloseSocket();
				}
				break;
				case TCPMessageCmd.TCP_MSG_ACTION:
				{
					@SuppressWarnings("unchecked")
					ArrayList<SingleMatchAction> action = (ArrayList<SingleMatchAction>)bundle.get("ACTION");
					//Log.d("TCP", "action: " + action);
					if(action != null)
					{
						MatchAction tmpMatchAction = new MatchAction();
						tmpMatchAction.setLP(cnt++);
						tmpMatchAction.addActions(action);
						actionsList.add(0, tmpMatchAction);
						
						addActionToScroollView(tmpMatchAction);
					}
				}
				break;
				case TCPMessageCmd.TCP_MSG_SQD_CHANGE:
				{
					int volleyballer1 = bundle.getInt("VOLL_1");
					int volleyballer2 = bundle.getInt("VOLL_2");
					
					addChangeToScroollView(volleyballer1, volleyballer2);
				}
				break;
				case TCPMessageCmd.TCP_MSG_TACTIC:
				{
					boolean succes = bundle.getBoolean("SUCCES");
					int teamId = bundle.getInt("TEAM_ID");
					
					if(succes)
						addTacticToScroollView(teamId);
				}
				break;
			}
		}
	}
}

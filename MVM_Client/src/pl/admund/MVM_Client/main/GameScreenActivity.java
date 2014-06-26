package pl.admund.MVM_Client.main;

import pl.admund.MVM_Client.R;
import pl.admund.MVM_Client.LeagueTable.LeagueTableActivity;
import pl.admund.MVM_Client.Message.MessagePanelActivity;
import pl.admund.MVM_Client.PlayMatch.PlayMatchActivity;
import pl.admund.MVM_Client.Stadium.StadiumActivity;
import pl.admund.MVM_Client.Tactics.FirstTeamSelectionActivity;
import pl.admund.MVM_Client.Tactics.TacticActivity;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;

public class GameScreenActivity extends Activity
{
	@Override
	protected void onCreate(Bundle _savedInstanceState)
	{
		super.onCreate(_savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		
		setContentView(R.layout.game_screen_activity);
		
		// ADD LISTNERS
		createMsgBtnListner();
		createPlayerBtnListner();
		createTeamBtnListner();
		createTableBtnListner();
		createStadiumBtnListner();
		createMatchBtnListner();
		createTacticBtnListner();
	}
	
	public void createMsgBtnListner()
	{
		Button loginButon = (Button)findViewById(R.id.game_screen_activity_msg_btn);
		
		loginButon.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				Intent intent = new Intent(GameScreenActivity.this, MessagePanelActivity.class);
				startActivity(intent);
			}
		});
	}
	
	public void createPlayerBtnListner()
	{
		Button loginButon = (Button)findViewById(R.id.game_screen_activity_player_btn);
		
		loginButon.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				;
			}
		});
	}
	
	public void createTeamBtnListner()
	{
		Button loginButon = (Button)findViewById(R.id.game_screen_activity_team_btn);
		
		loginButon.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				Intent intent = new Intent(GameScreenActivity.this, FirstTeamSelectionActivity.class);
				startActivity(intent);
			}
		});
	}
	
	public void createTableBtnListner()
	{
		Button loginButon = (Button)findViewById(R.id.game_screen_activity_table_btn);
		
		loginButon.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				Intent intent = new Intent(GameScreenActivity.this, LeagueTableActivity.class);
				startActivity(intent);
			}
		});
	}
	
	public void createStadiumBtnListner()
	{
		Button loginButon = (Button)findViewById(R.id.game_screen_activity_stadium_btn);
		
		loginButon.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				Intent intent = new Intent(GameScreenActivity.this, StadiumActivity.class);
				startActivity(intent);
			}
		});
	}
	
	public void createMatchBtnListner()
	{
		Button loginButon = (Button)findViewById(R.id.game_screen_activity_play_match_btn);
		
		loginButon.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				Intent intent = new Intent(GameScreenActivity.this, PlayMatchActivity.class);
				startActivity(intent);
			}
		});
	}
	
	public void createTacticBtnListner()
	{
		Button tacticButon = (Button)findViewById(R.id.game_screen_activity_tactic_btn);
		
		tacticButon.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				Intent intent = new Intent(GameScreenActivity.this, TacticActivity.class);
				startActivity(intent);
			}
		});
	}
	/*
	public void createLoginBtnListner()
	{
		Button loginButon = (Button)findViewById(R.id.welcome_activity_login_btn);
		
		loginButon.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				if(PlayerAllInfo.getInstance().isLogenOn())
					showDialog(LOGOUT_DIALOG);
				else
					showDialog(LOGIN_DIALOG);
			}
		});
	}
	
	public void createLoginBtnListner()
	{
		Button loginButon = (Button)findViewById(R.id.welcome_activity_login_btn);
		
		loginButon.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				if(PlayerAllInfo.getInstance().isLogenOn())
					showDialog(LOGOUT_DIALOG);
				else
					showDialog(LOGIN_DIALOG);
			}
		});
	}*/
	
	@Override
	protected void onResume() 
	{
		super.onResume();
	}
	
}

package pl.admund.MVM_Client.Tactics;

import java.util.List;
import org.apache.http.NameValuePair;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnDismissListener;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import pl.admund.MVM_Client.R;
import pl.admund.MVM_Client.TCP.NetworkTCP;
import pl.admund.MVM_Client.TCP.TCPMessageCmd;
import pl.admund.MVM_Client.XMLResponds.GetTacticRespondXML;
import pl.admund.MVM_Client.XMLResponds.HTTPRespond;
import pl.admund.MVM_Client.XMLUtils.HTTPGetAsyncTask;
import pl.admund.MVM_Client.XMLUtils.HTTPPostAsyncTask;
import pl.admund.MVM_Client.XMLUtils.NetworkHTTP;
import pl.admund.MVM_Client.XMLUtils.OnCompleteDownloadListner;
import pl.admund.MVM_Client.XMLUtils.XMLCreator;
import pl.admund.MVM_Client.XMLUtils.XMLType;
import pl.admund.MVM_Client.main.MyActivity;
import pl.admund.MVM_Client.main.PlayerAllInfo;

public class TacticActivity extends MyActivity
{
	private enum TacticMode { TACTIC_ATTACK, TACTIC_SERIWS, TACTIC_BLOCK, TACTIC_RECEPTION, TACTIC_SET };
	
	private final int SEND_TACTIC_DIALOG = 1;
	private final int GET_TACTIC_DIALOG = 2;
	
	private TacticMode mode = TacticMode.TACTIC_ATTACK;
	
	private boolean isFromMatch = false;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		
		setContentView(R.layout.tactic_layout);
		
		if(getIntent().getExtras() != null)
			isFromMatch = getIntent().getExtras().getBoolean("FROM_MATCH");
		
		createBtns();
		createProfileBtns();
		updateView();
	}
	
	@Override
	protected void onResume()
	{
		super.onResume();
		NetworkHTTP.getInstance().setOnCompleteDownloadListner(new TacticOnCompleteDownloadListner());
		
		if(PlayerAllInfo.getInstance().getTactic() == null)
		{
			String teamTacticUrl = XMLCreator.createGetTeamTacticURL(PlayerAllInfo.getInstance().getTeam().getTeamId());
			new HTTPGetAsyncTask(XMLType.GET_TEAM_TACTIC_HTTP).execute(teamTacticUrl);
			
			showWaitDialog("Geting tactic from server...");
		}
		else
		{
			setTacticOnSpinners(PlayerAllInfo.getInstance().getTactic());
		}
	}
	
	@Override
	protected Dialog onCreateDialog(int _id, Bundle _args) 
	{
		Dialog dialog = super.onCreateDialog(_id, _args);
		if(dialog != null)
			return dialog;
		
		switch(_id)
		{
			case SEND_TACTIC_DIALOG:
				return createSendTacticDialog();
				
			case GET_TACTIC_DIALOG:
				return createGetTacticDialog();
				
			default:
				return null;
		}
	}
	
	@Override
	protected void onDestroy() 
	{
		super.onDestroy();
	}
	
	
	public Dialog createSendTacticDialog()
	{
		Dialog dialog = new Dialog(this, R.style.NoTitleDialog);
		dialog.setContentView(R.layout.yes_no_dialog);
		dialog.setOnDismissListener(new OnDismissListener() 
		{
			@Override
			public void onDismiss(DialogInterface dialog) 
			{
				removeDialog(SEND_TACTIC_DIALOG);		
			}
		});
		
		TextView textview = (TextView)dialog.findViewById(R.id.yes_no_dialog_main_text);
		textview.setText("Jestes pewny ze chcesz wyslac taktyke na serwer?\nTaktyka zapisana na serwerze zsotanie nadpisana przez nowa.");
		
		Button yesBtn = (Button)dialog.findViewById(R.id.yes_no_dialog_yes_button);
		yesBtn.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v) 
			{
				dismissDialog(SEND_TACTIC_DIALOG);
				
				String tactic = getTacticFromSpinners();
				
				List<NameValuePair> pairs = XMLCreator.createSetTeamTacticURL(PlayerAllInfo.getInstance().getTeam().getTeamId(), tactic);
				new HTTPPostAsyncTask(XMLType.SET_TEAM_TACTIC_HTTP).execute(pairs);
				
				showWaitDialog("Sending tactic to server...");
			}
		});
		
		Button noBtn = (Button)dialog.findViewById(R.id.yes_no_dialog_no_button);
		noBtn.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v) 
			{
				dismissDialog(SEND_TACTIC_DIALOG);
			}
		});
		
		return dialog;
	}
	
	public Dialog createGetTacticDialog()
	{
		Dialog dialog = new Dialog(this, R.style.NoTitleDialog);
		dialog.setContentView(R.layout.yes_no_dialog);
		dialog.setOnDismissListener(new OnDismissListener() {
			@Override
			public void onDismiss(DialogInterface dialog) {
				removeDialog(GET_TACTIC_DIALOG);
			}
		});
		
		TextView textview = (TextView)dialog.findViewById(R.id.yes_no_dialog_main_text);
		textview.setText("Czy jestes pewny ze chcesz pobtrac taktyke z serwera?\nWszsytkie dokonane zmiany zostana stracone.");
		
		Button yesBtn = (Button)dialog.findViewById(R.id.yes_no_dialog_yes_button);
		yesBtn.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v) 
			{
				dismissDialog(GET_TACTIC_DIALOG);
				
				String teamTacticUrl = XMLCreator.createGetTeamTacticURL(PlayerAllInfo.getInstance().getTeam().getTeamId());
				new HTTPGetAsyncTask(XMLType.GET_TEAM_TACTIC_HTTP).execute(teamTacticUrl);
				
				showWaitDialog("Geting tactic from server...");
			}
		});
		
		Button noBtn = (Button)dialog.findViewById(R.id.yes_no_dialog_no_button);
		noBtn.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v) 
			{
				dismissDialog(GET_TACTIC_DIALOG);
			}
		});
		
		return dialog;
	}
	
	private void createBtns()
	{
		Button leftBtn = (Button) findViewById(R.id.tactic_layout_left_btn);
		leftBtn.setOnClickListener(new OnClickListener() 
		{
			@Override
			public void onClick(View v) 
			{
				if(mode == TacticMode.TACTIC_ATTACK)
					mode = TacticMode.TACTIC_SERIWS;
				
				else if(mode == TacticMode.TACTIC_SERIWS)
					mode = TacticMode.TACTIC_RECEPTION;
				
				else if(mode == TacticMode.TACTIC_RECEPTION)
					mode = TacticMode.TACTIC_BLOCK;
				
				else if(mode == TacticMode.TACTIC_BLOCK)
					mode = TacticMode.TACTIC_SET;
				
				else if(mode == TacticMode.TACTIC_SET)
					mode = TacticMode.TACTIC_ATTACK;
				
				updateView();
			}
		});
		
		Button rightBtn = (Button) findViewById(R.id.tactic_layout_right_btn);
		rightBtn.setOnClickListener(new OnClickListener() 
		{
			@Override
			public void onClick(View v) 
			{
				if(mode == TacticMode.TACTIC_ATTACK)
					mode = TacticMode.TACTIC_SET;
				
				else if(mode == TacticMode.TACTIC_SERIWS)
					mode = TacticMode.TACTIC_ATTACK;
				
				else if(mode == TacticMode.TACTIC_RECEPTION)
					mode = TacticMode.TACTIC_SERIWS;
				
				else if(mode == TacticMode.TACTIC_BLOCK)
					mode = TacticMode.TACTIC_RECEPTION;
				
				else if(mode == TacticMode.TACTIC_SET)
					mode = TacticMode.TACTIC_BLOCK;
				
				updateView();
			}
		});
		
		Button sendBtn = (Button) findViewById(R.id.tactic_layout_send_btn);
		sendBtn.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v) 
			{
				showDialog(SEND_TACTIC_DIALOG);
			}
		});
		
		Button getTacticBtn = (Button) findViewById(R.id.tactic_layout_get_from_serwer_btn);
		getTacticBtn.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v) 
			{
				showDialog(GET_TACTIC_DIALOG);
			}
		});
	}
	
	public void createProfileBtns()
	{
		OnClickListener noTeamMatchSquadOnClickListner = new OnClickListener() {
			@Override
			public void onClick(View v) {
				Toast.makeText(TacticActivity.this, "Nie ustaliles jescze skladu meczowego.", Toast.LENGTH_SHORT).show();
			}
		};
		
		TeamMatchSquad tmpTeamMatchSquad = PlayerAllInfo.getInstance().getTeamMatchSquad();
		
		Button attAttackBtn = (Button)findViewById(R.id.tactic_attack_att_profile_btn);
		Button rec1AttackBtn = (Button)findViewById(R.id.tactic_attack_rec1_profile_btn);
		Button blo1AttackBtn = (Button)findViewById(R.id.tactic_attack_blo1_profile_btn);
		Button rec2AttackBtn = (Button)findViewById(R.id.tactic_attack_rec2_profile_btn);
		Button blo2AttackBtn = (Button)findViewById(R.id.tactic_attack_blo2_profile_btn);
		
		Button attBlockBtn = (Button)findViewById(R.id.tactic_block_att_profile_btn);
		Button rec1BlockBtn = (Button)findViewById(R.id.tactic_block_rec1_profile_btn);
		Button blo1BlockBtn = (Button)findViewById(R.id.tactic_block_blo1_profile_btn);
		Button setBlockBtn = (Button)findViewById(R.id.tactic_block_set_profile_btn);
		Button rec2BlockBtn = (Button)findViewById(R.id.tactic_block_rec2_profile_btn);
		Button blo2BlockBtn = (Button)findViewById(R.id.tactic_block_blo2_profile_btn);
		
		Button attSerwisBtn = (Button)findViewById(R.id.tactic_serwis_att_profile_btn);
		Button rec1SerwisBtn = (Button)findViewById(R.id.tactic_serwis_rec1_profile_btn);
		Button blo1SerwisBtn = (Button)findViewById(R.id.tactic_serwis_blo1_profile_btn);
		Button setSerwisBtn = (Button)findViewById(R.id.tactic_serwis_set_profile_btn);
		Button rec2SerwisBtn = (Button)findViewById(R.id.tactic_serwis_rec2_profile_btn);
		Button blo2SerwisBtn = (Button)findViewById(R.id.tactic_serwis_blo2_profile_btn);
		
		Button rec1ReceptionBtn = (Button)findViewById(R.id.tactic_reception_rec1_profile_btn);
		Button rec2ReceptionBtn = (Button)findViewById(R.id.tactic_reception_rec2_profile_btn);
		Button libReceptionBtn = (Button)findViewById(R.id.tactic_reception_lib_profile_btn);
		
		Button setReceptionBtn = (Button)findViewById(R.id.tactic_set_set_profile_btn);
		
		if(tmpTeamMatchSquad == null)
		{
			attAttackBtn.setOnClickListener(noTeamMatchSquadOnClickListner);
			rec1AttackBtn.setOnClickListener(noTeamMatchSquadOnClickListner);
			blo1AttackBtn.setOnClickListener(noTeamMatchSquadOnClickListner);
			rec2AttackBtn.setOnClickListener(noTeamMatchSquadOnClickListner);
			blo2AttackBtn.setOnClickListener(noTeamMatchSquadOnClickListner);
			
			attBlockBtn.setOnClickListener(noTeamMatchSquadOnClickListner);
			rec1BlockBtn.setOnClickListener(noTeamMatchSquadOnClickListner);
			blo1BlockBtn.setOnClickListener(noTeamMatchSquadOnClickListner);
			setBlockBtn.setOnClickListener(noTeamMatchSquadOnClickListner);
			rec2BlockBtn.setOnClickListener(noTeamMatchSquadOnClickListner);
			blo2BlockBtn.setOnClickListener(noTeamMatchSquadOnClickListner);
			
			attSerwisBtn.setOnClickListener(noTeamMatchSquadOnClickListner);
			rec1SerwisBtn.setOnClickListener(noTeamMatchSquadOnClickListner);
			blo1SerwisBtn.setOnClickListener(noTeamMatchSquadOnClickListner);
			setSerwisBtn.setOnClickListener(noTeamMatchSquadOnClickListner);
			rec2SerwisBtn.setOnClickListener(noTeamMatchSquadOnClickListner);
			blo2SerwisBtn.setOnClickListener(noTeamMatchSquadOnClickListner);
			
			rec1ReceptionBtn.setOnClickListener(noTeamMatchSquadOnClickListner);
			rec2ReceptionBtn.setOnClickListener(noTeamMatchSquadOnClickListner);
			libReceptionBtn.setOnClickListener(noTeamMatchSquadOnClickListner);
			
			setReceptionBtn.setOnClickListener(noTeamMatchSquadOnClickListner);
		}
		else
		{
			attAttackBtn.setOnClickListener(new ProfileOnClickListener(tmpTeamMatchSquad.getAttackerId(), TacticActivity.this));
			rec1AttackBtn.setOnClickListener(new ProfileOnClickListener(tmpTeamMatchSquad.getReciver1Id(), TacticActivity.this));
			blo1AttackBtn.setOnClickListener(new ProfileOnClickListener(tmpTeamMatchSquad.getBlcker1Id(), TacticActivity.this));
			rec2AttackBtn.setOnClickListener(new ProfileOnClickListener(tmpTeamMatchSquad.getReciver2Id(), TacticActivity.this));
			blo2AttackBtn.setOnClickListener(new ProfileOnClickListener(tmpTeamMatchSquad.getBlcker2Id(), TacticActivity.this));
			
			attBlockBtn.setOnClickListener(new ProfileOnClickListener(tmpTeamMatchSquad.getAttackerId(), TacticActivity.this));
			rec1BlockBtn.setOnClickListener(new ProfileOnClickListener(tmpTeamMatchSquad.getReciver1Id(), TacticActivity.this));
			blo1BlockBtn.setOnClickListener(new ProfileOnClickListener(tmpTeamMatchSquad.getBlcker1Id(), TacticActivity.this));
			setBlockBtn.setOnClickListener(new ProfileOnClickListener(tmpTeamMatchSquad.getSetterId(), TacticActivity.this));
			rec2BlockBtn.setOnClickListener(new ProfileOnClickListener(tmpTeamMatchSquad.getReciver2Id(), TacticActivity.this));
			blo2BlockBtn.setOnClickListener(new ProfileOnClickListener(tmpTeamMatchSquad.getBlcker2Id(), TacticActivity.this));
			
			attSerwisBtn.setOnClickListener(new ProfileOnClickListener(tmpTeamMatchSquad.getAttackerId(), TacticActivity.this));
			rec1SerwisBtn.setOnClickListener(new ProfileOnClickListener(tmpTeamMatchSquad.getReciver1Id(), TacticActivity.this));
			blo1SerwisBtn.setOnClickListener(new ProfileOnClickListener(tmpTeamMatchSquad.getBlcker1Id(), TacticActivity.this));
			setSerwisBtn.setOnClickListener(new ProfileOnClickListener(tmpTeamMatchSquad.getSetterId(), TacticActivity.this));
			rec2SerwisBtn.setOnClickListener(new ProfileOnClickListener(tmpTeamMatchSquad.getReciver2Id(), TacticActivity.this));
			blo2SerwisBtn.setOnClickListener(new ProfileOnClickListener(tmpTeamMatchSquad.getBlcker2Id(), TacticActivity.this));
			
			rec1ReceptionBtn.setOnClickListener(new ProfileOnClickListener(tmpTeamMatchSquad.getReciver1Id(), TacticActivity.this));
			rec2ReceptionBtn.setOnClickListener(new ProfileOnClickListener(tmpTeamMatchSquad.getReciver2Id(), TacticActivity.this));
			libReceptionBtn.setOnClickListener(new ProfileOnClickListener(tmpTeamMatchSquad.getLiberoId(), TacticActivity.this));
			
			setReceptionBtn.setOnClickListener(new ProfileOnClickListener(tmpTeamMatchSquad.getSetterId(), TacticActivity.this));
		}
	}
	
	private void updateView()
	{
		TextView titleText = (TextView) findViewById(R.id.tactic_layout_title_text);
		
		hideAll();
		
		LinearLayout showLayout = null;
		if(mode == TacticMode.TACTIC_ATTACK)
		{
			showLayout = (LinearLayout)findViewById(R.id.tactic_layout_attack_layout);
			titleText.setText("TAKTYKA - ATAK");
		}
		else if(mode == TacticMode.TACTIC_SERIWS)
		{
			showLayout = (LinearLayout)findViewById(R.id.tactic_layout_serwis_layout);
			titleText.setText("TAKTYKA - SERWIS");
		}
		else if(mode == TacticMode.TACTIC_RECEPTION)
		{
			showLayout = (LinearLayout)findViewById(R.id.tactic_layout_reception_layout);
			titleText.setText("TAKTYKA - PRZYJECIE");
		}
		else if(mode == TacticMode.TACTIC_BLOCK)
		{
			showLayout = (LinearLayout)findViewById(R.id.tactic_layout_block_layout);
			titleText.setText("TAKTYKA - BLOK");
		}
		else if(mode == TacticMode.TACTIC_SET)
		{
			showLayout = (LinearLayout)findViewById(R.id.tactic_layout_set_layout);
			titleText.setText("TAKTYKA - ROZEGRANIE");
		}
		
		showLayout.setVisibility(View.VISIBLE);
	}
	
	private void hideAll()
	{
		LinearLayout attackLayout = (LinearLayout)findViewById(R.id.tactic_layout_attack_layout);
		attackLayout.setVisibility(View.INVISIBLE);
		
		LinearLayout serwisLayout = (LinearLayout)findViewById(R.id.tactic_layout_serwis_layout);
		serwisLayout.setVisibility(View.INVISIBLE);
		
		LinearLayout blockLayout = (LinearLayout)findViewById(R.id.tactic_layout_block_layout);
		blockLayout.setVisibility(View.INVISIBLE);
		
		LinearLayout reciptionLayout = (LinearLayout)findViewById(R.id.tactic_layout_reception_layout);
		reciptionLayout.setVisibility(View.INVISIBLE);
		
		LinearLayout setLayout = (LinearLayout)findViewById(R.id.tactic_layout_set_layout);
		setLayout.setVisibility(View.INVISIBLE);
	}
	
	private String getTacticFromSpinners()
	{
		CheckBox teamTacticImportentCheckBox = (CheckBox)findViewById(R.id.tactic_layout_tactic_checkbox);
		boolean teamTacticImportent = teamTacticImportentCheckBox.isChecked();
	
	// TEAM
		Spinner teamAttackStrSpinner = (Spinner)findViewById(R.id.spinner_team_attack_str);
		int teamAttackStr = Tactic.attStrFromStringToInt((String)teamAttackStrSpinner.getSelectedItem());
		Spinner teamAttackDirSpinner = (Spinner)findViewById(R.id.spinner_team_attack_dir);
		int teamAttackDir = Tactic.attDirFromStringToInt((String)teamAttackDirSpinner.getSelectedItem());
		
		Spinner teamSerwisStrSpinner = (Spinner)findViewById(R.id.spinner_team_serwis_str);
		int teamSerwisStr = Tactic.serwisStrFromStringToInt((String)teamSerwisStrSpinner.getSelectedItem());
		Spinner teamSerwisDirSpinner = (Spinner)findViewById(R.id.spinner_team_serwis_dir);
		int teamSerwisDir = Tactic.serwisDirFromStringToInt((String)teamSerwisDirSpinner.getSelectedItem());
		
		Spinner teamReceptionQualitySpinner = (Spinner)findViewById(R.id.spinner_team_reception_quality);
		int teamReceptionQuality = Tactic.recQualityFromStringToInt((String)teamReceptionQualitySpinner.getSelectedItem());
		Spinner teamReceptionAreaSpinner = (Spinner)findViewById(R.id.spinner_team_reception_area);
		int teamReceptionArea = Tactic.recAreaFromStringToInt((String)teamReceptionAreaSpinner.getSelectedItem());
		
		Spinner teamBlock3Spinner = (Spinner)findViewById(R.id.spinner_team_block_3);
		int teamBlock3 = Tactic.block3FromStringToInt((String)teamBlock3Spinner.getSelectedItem());
		Spinner teamBlockOptionSpinner = (Spinner)findViewById(R.id.spinner_team_block_opt);
		int teamBlockOption = Tactic.blockOptionFromStringToInt((String)teamBlockOptionSpinner.getSelectedItem());
		
		Spinner teamSetStyleSpinner = (Spinner)findViewById(R.id.spinner_team_set_com);
		int teamSetStyle = Tactic.setStyleFromStringToInt((String)teamSetStyleSpinner.getSelectedItem());
		Spinner teamSetPreferSpinner = (Spinner)findViewById(R.id.spinner_team_set_pref);
		int teamSetPrefer = Tactic.setPrefFromStringToInt((String)teamSetPreferSpinner.getSelectedItem());

	// ATTACKER
		Spinner attAttackStrSpinner = (Spinner)findViewById(R.id.spinner_att_attack_str);
		int attAttackStr = Tactic.attStrFromStringToInt((String)attAttackStrSpinner.getSelectedItem());
		Spinner attAttackDirSpinner = (Spinner)findViewById(R.id.spinner_att_attack_dir);
		int attAttackDir = Tactic.attDirFromStringToInt((String)attAttackDirSpinner.getSelectedItem());
		
		Spinner attSerwisStrSpinner = (Spinner)findViewById(R.id.spinner_att_serwis_str);
		int attSerwisStr = Tactic.serwisStrFromStringToInt((String)attSerwisStrSpinner.getSelectedItem());
		Spinner attSerwisDirSpinner = (Spinner)findViewById(R.id.spinner_att_serwis_dir);
		int attSerwisDir = Tactic.serwisDirFromStringToInt((String)attSerwisDirSpinner.getSelectedItem());
		
		Spinner attBlock3Spinner = (Spinner)findViewById(R.id.spinner_att_block_3);
		int attBlock3 = Tactic.block3FromStringToInt((String)attBlock3Spinner.getSelectedItem());
		Spinner attBlockOptionSpinner = (Spinner)findViewById(R.id.spinner_att_block_opt);
		int attBlockOption = Tactic.blockOptionFromStringToInt((String)attBlockOptionSpinner.getSelectedItem());
		
	// RECIVER 1
		Spinner rec1AttackStrSpinner = (Spinner)findViewById(R.id.spinner_rec1_attack_str);
		int rec1AttackStr = Tactic.attStrFromStringToInt((String)rec1AttackStrSpinner.getSelectedItem());
		Spinner rec1AttackDirSpinner = (Spinner)findViewById(R.id.spinner_rec1_attack_dir);
		int rec1AttackDir = Tactic.attDirFromStringToInt((String)rec1AttackDirSpinner.getSelectedItem());
		
		Spinner rec1SerwisStrSpinner = (Spinner)findViewById(R.id.spinner_rec1_serwis_str);
		int rec1SerwisStr = Tactic.serwisStrFromStringToInt((String)rec1SerwisStrSpinner.getSelectedItem());
		Spinner rec1SerwisDirSpinner = (Spinner)findViewById(R.id.spinner_rec1_serwis_dir);
		int rec1SerwisDir = Tactic.serwisDirFromStringToInt((String)rec1SerwisDirSpinner.getSelectedItem());
		
		Spinner rec1ReceptionQualitySpinner = (Spinner)findViewById(R.id.spinner_rec1_reception_quality);
		int rec1ReceptionQuality = Tactic.recQualityFromStringToInt((String)rec1ReceptionQualitySpinner.getSelectedItem());
		Spinner rec1ReceptionAreaSpinner = (Spinner)findViewById(R.id.spinner_rec1_reception_area);
		int rec1ReceptionArea = Tactic.recAreaFromStringToInt((String)rec1ReceptionAreaSpinner.getSelectedItem());
		
		Spinner rec1Block3Spinner = (Spinner)findViewById(R.id.spinner_rec1_block_3);
		int rec1Block3 = Tactic.block3FromStringToInt((String)rec1Block3Spinner.getSelectedItem());
		Spinner rec1BlockOptionSpinner = (Spinner)findViewById(R.id.spinner_rec1_block_opt);
		int rec1BlockOption = Tactic.blockOptionFromStringToInt((String)rec1BlockOptionSpinner.getSelectedItem());
		
	// BLOCKER 1
		Spinner blo1AttackStrSpinner = (Spinner)findViewById(R.id.spinner_blo1_attack_str);
		int blo1AttackStr = Tactic.attStrFromStringToInt((String)blo1AttackStrSpinner.getSelectedItem());
		Spinner blo1AttackDirSpinner = (Spinner)findViewById(R.id.spinner_blo1_attack_dir);
		int blo1AttackDir = Tactic.attDirFromStringToInt((String)blo1AttackDirSpinner.getSelectedItem());
		
		Spinner blo1SerwisStrSpinner = (Spinner)findViewById(R.id.spinner_blo1_serwis_str);
		int blo1SerwisStr = Tactic.serwisStrFromStringToInt((String)blo1SerwisStrSpinner.getSelectedItem());
		Spinner blo1SerwisDirSpinner = (Spinner)findViewById(R.id.spinner_blo1_serwis_dir);
		int blo1SerwisDir = Tactic.serwisDirFromStringToInt((String)blo1SerwisDirSpinner.getSelectedItem());
		
		Spinner blo1Block3Spinner = (Spinner)findViewById(R.id.spinner_blo1_block_3);
		int blo1Block3 = Tactic.block3FromStringToInt((String)blo1Block3Spinner.getSelectedItem());
		Spinner blo1BlockOptionSpinner = (Spinner)findViewById(R.id.spinner_blo1_block_opt);
		int blo1BlockOption = Tactic.blockOptionFromStringToInt((String)blo1BlockOptionSpinner.getSelectedItem());
		
	// SETTER
		Spinner setSerwisStrSpinner = (Spinner)findViewById(R.id.spinner_set_serwis_str);
		int setSerwisStr = Tactic.serwisStrFromStringToInt((String)setSerwisStrSpinner.getSelectedItem());
		Spinner setSerwisDirSpinner = (Spinner)findViewById(R.id.spinner_set_serwis_dir);
		int setSerwisDir = Tactic.serwisDirFromStringToInt((String)setSerwisDirSpinner.getSelectedItem());
		
		Spinner setBlock3Spinner = (Spinner)findViewById(R.id.spinner_set_block_3);
		int setBlock3 = Tactic.block3FromStringToInt((String)setBlock3Spinner.getSelectedItem());
		Spinner setBlockOptionSpinner = (Spinner)findViewById(R.id.spinner_set_block_opt);
		int setBlockOption = Tactic.blockOptionFromStringToInt((String)setBlockOptionSpinner.getSelectedItem());
		
		Spinner setSetStyleSpinner = (Spinner)findViewById(R.id.spinner_set_set_com);
		int setSetStyle = Tactic.setStyleFromStringToInt((String)setSetStyleSpinner.getSelectedItem());
		Spinner setSetPreferSpinner = (Spinner)findViewById(R.id.spinner_set_set_pref);
		int setSetPrefer = Tactic.setPrefFromStringToInt((String)setSetPreferSpinner.getSelectedItem());
		
	// RECIVER 2
		Spinner rec2AttackStrSpinner = (Spinner)findViewById(R.id.spinner_rec2_attack_str);
		int rec2AttackStr = Tactic.attStrFromStringToInt((String)rec2AttackStrSpinner.getSelectedItem());
		Spinner rec2AttackDirSpinner = (Spinner)findViewById(R.id.spinner_rec2_attack_dir);
		int rec2AttackDir = Tactic.attDirFromStringToInt((String)rec2AttackDirSpinner.getSelectedItem());
		
		Spinner rec2SerwisStrSpinner = (Spinner)findViewById(R.id.spinner_rec2_serwis_str);
		int rec2SerwisStr = Tactic.serwisStrFromStringToInt((String)rec2SerwisStrSpinner.getSelectedItem());
		Spinner rec2SerwisDirSpinner = (Spinner)findViewById(R.id.spinner_rec2_serwis_dir);
		int rec2SerwisDir = Tactic.serwisDirFromStringToInt((String)rec2SerwisDirSpinner.getSelectedItem());
		
		Spinner rec2ReceptionQualitySpinner = (Spinner)findViewById(R.id.spinner_rec2_reception_quality);
		int rec2ReceptionQuality = Tactic.recQualityFromStringToInt((String)rec2ReceptionQualitySpinner.getSelectedItem());
		Spinner rec2ReceptionAreaSpinner = (Spinner)findViewById(R.id.spinner_rec2_reception_area);
		int rec2ReceptionArea = Tactic.recAreaFromStringToInt((String)rec2ReceptionAreaSpinner.getSelectedItem());
		
		Spinner rec2Block3Spinner = (Spinner)findViewById(R.id.spinner_rec2_block_3);
		int rec2Block3 = Tactic.block3FromStringToInt((String)rec2Block3Spinner.getSelectedItem());
		Spinner rec2BlockOptionSpinner = (Spinner)findViewById(R.id.spinner_rec2_block_opt);
		int rec2BlockOption = Tactic.blockOptionFromStringToInt((String)rec2BlockOptionSpinner.getSelectedItem());
		
	// BLOCKER 2
		Spinner blo2AttackStrSpinner = (Spinner)findViewById(R.id.spinner_blo2_attack_str);
		int blo2AttackStr = Tactic.attStrFromStringToInt((String)blo2AttackStrSpinner.getSelectedItem());
		Spinner blo2AttackDirSpinner = (Spinner)findViewById(R.id.spinner_blo2_attack_dir);
		int blo2AttackDir = Tactic.attDirFromStringToInt((String)blo2AttackDirSpinner.getSelectedItem());
		
		Spinner blo2SerwisStrSpinner = (Spinner)findViewById(R.id.spinner_blo2_serwis_str);
		int blo2SerwisStr = Tactic.serwisStrFromStringToInt((String)blo2SerwisStrSpinner.getSelectedItem());
		Spinner blo2SerwisDirSpinner = (Spinner)findViewById(R.id.spinner_blo2_serwis_dir);
		int blo2SerwisDir = Tactic.serwisDirFromStringToInt((String)blo2SerwisDirSpinner.getSelectedItem());
		
		Spinner blo2Block3Spinner = (Spinner)findViewById(R.id.spinner_blo2_block_3);
		int blo2Block3 = Tactic.block3FromStringToInt((String)blo2Block3Spinner.getSelectedItem());
		Spinner blo2BlockOptionSpinner = (Spinner)findViewById(R.id.spinner_blo2_block_opt);
		int blo2BlockOption = Tactic.blockOptionFromStringToInt((String)blo2BlockOptionSpinner.getSelectedItem());
		
	// LIBERO
		Spinner libReceptionQualitySpinner = (Spinner)findViewById(R.id.spinner_lib_reception_quality);
		int libReceptionQuality = Tactic.recQualityFromStringToInt((String)libReceptionQualitySpinner.getSelectedItem());
		Spinner libReceptionAreaSpinner = (Spinner)findViewById(R.id.spinner_lib_reception_area);
		int libReceptionArea = Tactic.recAreaFromStringToInt((String)libReceptionAreaSpinner.getSelectedItem());
		
		Tactic tmpTactic = new Tactic(teamTacticImportent, teamAttackStr, teamAttackDir, teamSerwisStr, teamSerwisDir, 
				teamReceptionQuality, teamReceptionArea, teamBlock3, teamBlockOption, 
				teamSetStyle, teamSetPrefer, 
				attAttackStr, attAttackDir, attSerwisStr, attSerwisDir, 
				attBlock3, attBlockOption, 
				rec1AttackStr, rec1AttackDir, rec1SerwisStr, rec1SerwisDir, 
				rec1ReceptionQuality, rec1ReceptionArea, rec1Block3, rec1BlockOption, 
				blo1AttackStr,  blo1AttackDir, blo1SerwisStr, blo1SerwisDir, 
				blo1Block3, blo1BlockOption, 
				setSerwisStr, setSerwisDir, setBlock3, setBlockOption, 
				setSetStyle, setSetPrefer, 
				rec2AttackStr, rec2AttackDir, rec2SerwisStr, rec2SerwisDir, 
				rec2ReceptionQuality, rec2ReceptionArea, rec2Block3, rec2BlockOption, 
				blo2AttackStr, blo2AttackDir, blo2SerwisStr, blo2SerwisDir, 
				blo2Block3, blo2BlockOption, 
				libReceptionQuality, libReceptionArea);
		
		return tmpTactic.toString();
	}
	
	public void setTacticOnSpinners(Tactic tactic)
	{
		CheckBox teamTacticImportentCheckBox = (CheckBox)findViewById(R.id.tactic_layout_tactic_checkbox);
		teamTacticImportentCheckBox.setChecked(tactic.isTeamTacticImportent());
	
	// TEAM
		Spinner teamAttackStrSpinner = (Spinner)findViewById(R.id.spinner_team_attack_str);
		teamAttackStrSpinner.setSelection(tactic.getTeamAttackStr());
		Spinner teamAttackDirSpinner = (Spinner)findViewById(R.id.spinner_team_attack_dir);
		teamAttackDirSpinner.setSelection(tactic.getTeamAttackDir());
		
		Spinner teamSerwisStrSpinner = (Spinner)findViewById(R.id.spinner_team_serwis_str);
		teamSerwisStrSpinner.setSelection(tactic.getTeamSerwisStr());
		Spinner teamSerwisDirSpinner = (Spinner)findViewById(R.id.spinner_team_serwis_dir);
		teamSerwisDirSpinner.setSelection(tactic.getTeamSerwisDir());
		
		Spinner teamReceptionQualitySpinner = (Spinner)findViewById(R.id.spinner_team_reception_quality);
		teamReceptionQualitySpinner.setSelection(tactic.getTeamReceptionQuality());
		Spinner teamReceptionAreaSpinner = (Spinner)findViewById(R.id.spinner_team_reception_area);
		teamReceptionAreaSpinner.setSelection(tactic.getTeamReceptionArea());
		
		Spinner teamBlock3Spinner = (Spinner)findViewById(R.id.spinner_team_block_3);
		teamBlock3Spinner.setSelection(tactic.getTeamBlock3());
		Spinner teamBlockOptionSpinner = (Spinner)findViewById(R.id.spinner_team_block_opt);
		teamBlockOptionSpinner.setSelection(tactic.getTeamBlockOption());
		
		Spinner teamSetStyleSpinner = (Spinner)findViewById(R.id.spinner_team_set_com);
		teamSetStyleSpinner.setSelection(tactic.getTeamSetStyle());
		Spinner teamSetPreferSpinner = (Spinner)findViewById(R.id.spinner_team_set_pref);
		teamSetPreferSpinner.setSelection(tactic.getTeamSetPrefer());

	// ATTACKER
		Spinner attAttackStrSpinner = (Spinner)findViewById(R.id.spinner_att_attack_str);
		attAttackStrSpinner.setSelection(tactic.getAttAttackStr());
		Spinner attAttackDirSpinner = (Spinner)findViewById(R.id.spinner_att_attack_dir);
		attAttackDirSpinner.setSelection(tactic.getAttAttackDir());
		
		Spinner attSerwisStrSpinner = (Spinner)findViewById(R.id.spinner_att_serwis_str);
		attSerwisStrSpinner.setSelection(tactic.getAttSerwisStr());
		Spinner attSerwisDirSpinner = (Spinner)findViewById(R.id.spinner_att_serwis_dir);
		attSerwisDirSpinner.setSelection(tactic.getAttSerwisDir());
		
		Spinner attBlock3Spinner = (Spinner)findViewById(R.id.spinner_att_block_3);
		attBlock3Spinner.setSelection(tactic.getAttBlock3());
		Spinner attBlockOptionSpinner = (Spinner)findViewById(R.id.spinner_att_block_opt);
		attBlockOptionSpinner.setSelection(tactic.getAttBlockOption());
		
	// RECIVER 1
		Spinner rec1AttackStrSpinner = (Spinner)findViewById(R.id.spinner_rec1_attack_str);
		rec1AttackStrSpinner.setSelection(tactic.getRec1AttackStr());
		Spinner rec1AttackDirSpinner = (Spinner)findViewById(R.id.spinner_rec1_attack_dir);
		rec1AttackDirSpinner.setSelection(tactic.getRec1AttackDir());
		
		Spinner rec1SerwisStrSpinner = (Spinner)findViewById(R.id.spinner_rec1_serwis_str);
		rec1SerwisStrSpinner.setSelection(tactic.getRec1SerwisStr());
		Spinner rec1SerwisDirSpinner = (Spinner)findViewById(R.id.spinner_rec1_serwis_dir);
		rec1SerwisDirSpinner.setSelection(tactic.getRec1AttackDir());
		
		Spinner rec1ReceptionQualitySpinner = (Spinner)findViewById(R.id.spinner_rec1_reception_quality);
		rec1ReceptionQualitySpinner.setSelection(tactic.getRec1ReceptionQuality());
		Spinner rec1ReceptionAreaSpinner = (Spinner)findViewById(R.id.spinner_rec1_reception_area);
		rec1ReceptionAreaSpinner.setSelection(tactic.getRec1ReceptionArea());
		
		Spinner rec1Block3Spinner = (Spinner)findViewById(R.id.spinner_rec1_block_3);
		rec1Block3Spinner.setSelection(tactic.getRec1Block3());
		Spinner rec1BlockOptionSpinner = (Spinner)findViewById(R.id.spinner_rec1_block_opt);
		rec1BlockOptionSpinner.setSelection(tactic.getRec1BlockOption());
		
	// BLOCKER 1
		Spinner blo1AttackStrSpinner = (Spinner)findViewById(R.id.spinner_blo1_attack_str);
		blo1AttackStrSpinner.setSelection(tactic.getBlo1AttackStr());
		Spinner blo1AttackDirSpinner = (Spinner)findViewById(R.id.spinner_blo1_attack_dir);
		blo1AttackDirSpinner.setSelection(tactic.getBlo1AttackDir());
		
		Spinner blo1SerwisStrSpinner = (Spinner)findViewById(R.id.spinner_blo1_serwis_str);
		blo1SerwisStrSpinner.setSelection(tactic.getBlo1SerwisStr());
		Spinner blo1SerwisDirSpinner = (Spinner)findViewById(R.id.spinner_blo1_serwis_dir);
		blo1SerwisDirSpinner.setSelection(tactic.getBlo1SerwisDir());
		
		Spinner blo1Block3Spinner = (Spinner)findViewById(R.id.spinner_blo1_block_3);
		blo1Block3Spinner.setSelection(tactic.getBlo1Block3());
		Spinner blo1BlockOptionSpinner = (Spinner)findViewById(R.id.spinner_blo1_block_opt);
		blo1BlockOptionSpinner.setSelection(tactic.getBlo1BlockOption());
		
	// SETTER
		Spinner setSerwisStrSpinner = (Spinner)findViewById(R.id.spinner_set_serwis_str);
		setSerwisStrSpinner.setSelection(tactic.getSetSerwisStr());
		Spinner setSerwisDirSpinner = (Spinner)findViewById(R.id.spinner_set_serwis_dir);
		setSerwisDirSpinner.setSelection(tactic.getSetSerwisDir());
		
		Spinner setBlock3Spinner = (Spinner)findViewById(R.id.spinner_set_block_3);
		setBlock3Spinner.setSelection(tactic.getSetBlock3());
		Spinner setBlockOptionSpinner = (Spinner)findViewById(R.id.spinner_set_block_opt);
		setBlockOptionSpinner.setSelection(tactic.getSetBlockOption());
		
		Spinner setSetStyleSpinner = (Spinner)findViewById(R.id.spinner_set_set_com);
		setSetStyleSpinner.setSelection(tactic.getSetSetStyle());
		Spinner setSetPreferSpinner = (Spinner)findViewById(R.id.spinner_set_set_pref);
		setSetPreferSpinner.setSelection(tactic.getSetSetPrefer());
		
	// RECIVER 2
		Spinner rec2AttackStrSpinner = (Spinner)findViewById(R.id.spinner_rec2_attack_str);
		rec2AttackStrSpinner.setSelection(tactic.getRec2AttackStr());
		Spinner rec2AttackDirSpinner = (Spinner)findViewById(R.id.spinner_rec2_attack_dir);
		rec2AttackDirSpinner.setSelection(tactic.getRec2AttackDir());
		
		Spinner rec2SerwisStrSpinner = (Spinner)findViewById(R.id.spinner_rec2_serwis_str);
		rec2SerwisStrSpinner.setSelection(tactic.getRec2SerwisStr());
		Spinner rec2SerwisDirSpinner = (Spinner)findViewById(R.id.spinner_rec2_serwis_dir);
		rec2SerwisDirSpinner.setSelection(tactic.getRec2SerwisDir());
		
		Spinner rec2ReceptionQualitySpinner = (Spinner)findViewById(R.id.spinner_rec2_reception_quality);
		rec2ReceptionQualitySpinner.setSelection(tactic.getRec2ReceptionQuality());
		Spinner rec2ReceptionAreaSpinner = (Spinner)findViewById(R.id.spinner_rec2_reception_area);
		rec2ReceptionAreaSpinner.setSelection(tactic.getRec2ReceptionArea());
		
		Spinner rec2Block3Spinner = (Spinner)findViewById(R.id.spinner_rec2_block_3);
		rec2Block3Spinner.setSelection(tactic.getRec2Block3());
		Spinner rec2BlockOptionSpinner = (Spinner)findViewById(R.id.spinner_rec2_block_opt);
		rec2BlockOptionSpinner.setSelection(tactic.getRec2BlockOption());
		
	// BLOCKER 2
		Spinner blo2AttackStrSpinner = (Spinner)findViewById(R.id.spinner_blo2_attack_str);
		blo2AttackStrSpinner.setSelection(tactic.getBlo2AttackStr());
		Spinner blo2AttackDirSpinner = (Spinner)findViewById(R.id.spinner_blo2_attack_dir);
		blo2AttackDirSpinner.setSelection(tactic.getBlo2AttackDir());
		
		Spinner blo2SerwisStrSpinner = (Spinner)findViewById(R.id.spinner_blo2_serwis_str);
		blo2SerwisStrSpinner.setSelection(tactic.getBlo2SerwisStr());
		Spinner blo2SerwisDirSpinner = (Spinner)findViewById(R.id.spinner_blo2_serwis_dir);
		blo2SerwisDirSpinner.setSelection(tactic.getBlo2SerwisDir());
		
		Spinner blo2Block3Spinner = (Spinner)findViewById(R.id.spinner_blo2_block_3);
		blo2Block3Spinner.setSelection(tactic.getBlo2Block3());
		Spinner blo2BlockOptionSpinner = (Spinner)findViewById(R.id.spinner_blo2_block_opt);
		blo2BlockOptionSpinner.setSelection(tactic.getBlo2BlockOption());
		
	// LIBERO
		Spinner libReceptionQualitySpinner = (Spinner)findViewById(R.id.spinner_lib_reception_quality);
		libReceptionQualitySpinner.setSelection(tactic.getLibReceptionQuality());
		Spinner libReceptionAreaSpinner = (Spinner)findViewById(R.id.spinner_lib_reception_area);
		libReceptionAreaSpinner.setSelection(tactic.getLibReceptionArea());
	}
	
	class TacticOnCompleteDownloadListner implements OnCompleteDownloadListner
	{
		public void downloadComplete(XMLType _flag, HTTPRespond _respond)
		{
			switch(_flag)
			{
			case GET_TEAM_TACTIC_HTTP:
				dissmisWaitDialog();
				if(_respond.getErrorCode() != -1)
				{
					showErrorDialog(_respond.getErrorMSG());
				}
				else
				{
					Log.d("TACTIC", "get tactic()");
					GetTacticRespondXML tmp = (GetTacticRespondXML)_respond;
					Tactic tmpTactic = Tactic.parseTactic(tmp.getTactic());
					setTacticOnSpinners(tmpTactic);
					
					PlayerAllInfo.getInstance().setTactic(tmpTactic);
				}
				break;
			case SET_TEAM_TACTIC_HTTP:
				dissmisWaitDialog();
				if(_respond.getErrorCode() != -1)
				{
					showErrorDialog(_respond.getErrorMSG());
				}
				else
				{
					Log.d("TACTIC", "set tactic()");
					GetTacticRespondXML tmp = (GetTacticRespondXML)_respond;
					Tactic tmpTactic = Tactic.parseTactic(tmp.getTactic());
					setTacticOnSpinners(tmpTactic);
					
					PlayerAllInfo.getInstance().setTactic(tmpTactic);
					
					if(isFromMatch)
					{
						String tacticMsg = TCPMessageCmd.TCP_MSG_TACTIC  + "_" + PlayerAllInfo.getInstance().getPlayerId();
						NetworkTCP.getInstance().SendData(tacticMsg.getBytes());
						Toast.makeText(TacticActivity.this, "Taktyka zostala pomyslnie wyslana na serwer\nTaktyka zostanie wprowadzona po przerwie (czas, przerwa techniczna lub koniec seta)", Toast.LENGTH_SHORT).show();
					}
					else
						Toast.makeText(TacticActivity.this, "Taktyka zostala pomyslnie wyslana na serwer", Toast.LENGTH_SHORT).show();
				}
				break;
			}
		}
	}
}

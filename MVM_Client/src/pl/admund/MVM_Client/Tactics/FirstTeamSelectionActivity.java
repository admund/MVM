package pl.admund.MVM_Client.Tactics;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;

import pl.admund.Constans.ErrorCode;
import pl.admund.MVM_Client.R;
import pl.admund.MVM_Client.Volleyballer.VolleyballerClnt;
import pl.admund.MVM_Client.Volleyballer.VolleyballerInfoActivity;
import pl.admund.MVM_Client.XMLResponds.GetSquadRespondXML;
import pl.admund.MVM_Client.XMLResponds.HTTPRespond;
import pl.admund.MVM_Client.XMLUtils.HTTPGetAsyncTask;
import pl.admund.MVM_Client.XMLUtils.HTTPPostAsyncTask;
import pl.admund.MVM_Client.XMLUtils.NetworkHTTP;
import pl.admund.MVM_Client.XMLUtils.OnCompleteDownloadListner;
import pl.admund.MVM_Client.XMLUtils.XMLCreator;
import pl.admund.MVM_Client.XMLUtils.XMLType;
import pl.admund.MVM_Client.main.MyActivity;
import pl.admund.MVM_Client.main.PlayerAllInfo;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class FirstTeamSelectionActivity extends MyActivity
{
	private VolleyballersListAdapter mAdapter = null;
	private ArrayList<LinearLayout> squadFieldList = new ArrayList<LinearLayout>();
	private ArrayList<Integer> teamSquad = new ArrayList<Integer>();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		
		setContentView(R.layout.first_team_activity_layout);
		
		ListView volleyballerListView = (ListView) findViewById(R.id.first_team_all_list_view);
		mAdapter = new VolleyballersListAdapter(this, R.layout.volleybaler_list_row, PlayerAllInfo.getInstance().getTeam().getVolleyballersList());
		volleyballerListView.setAdapter(mAdapter);
		volleyballerListView.invalidate();
		
		LinearLayout rootLayout = (LinearLayout)findViewById(R.id.first_team_activity_layout_root);
		rootLayout.invalidate();
		
		setFieldsListeners();
		setBtnsListeners();
	}
	
	@Override
	protected void onResume() 
	{
		super.onResume();
		NetworkHTTP.getInstance().setOnCompleteDownloadListner(new FirstTeamOnCompleteDownloadListner());
		
		if(PlayerAllInfo.getInstance().getTeamMatchSquad() == null)
		{
			String getSquadURL = XMLCreator.createGetSquadURL(PlayerAllInfo.getInstance().getTeam().getTeamId());
			new HTTPGetAsyncTask(XMLType.GET_SQD_HTTP).execute(getSquadURL);
			
			showWaitDialog("Geting match squad from server...");
		}
		else
		{
			fillSquad(PlayerAllInfo.getInstance().getTeamMatchSquad());
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
			default:
			{
				return null;
			}
		}
	}
	
	private void setBtnsListeners()
	{
		Button sendButton = (Button) findViewById(R.id.first_team_send_btn);
		sendButton.setOnClickListener(new OnClickListener() 
		{
			@Override
			public void onClick(View v) 
			{
				if( fillTeamSquad(teamSquad) )
				{
					List<NameValuePair> setSquadURL = XMLCreator.createSetSquadURL(teamSquad);
					new HTTPPostAsyncTask(XMLType.SET_SQD_HTTP).execute(setSquadURL);
					
					showWaitDialog("WAIT...");
				}
				else
					Toast.makeText(FirstTeamSelectionActivity.this, "Musisz wybrac caly sklad!", Toast.LENGTH_LONG).show();
			}
		});
		
		Button profilButton = (Button) findViewById(R.id.first_team_view_btn);
		profilButton.setOnClickListener(new OnClickListener() 
		{
			@Override
			public void onClick(View v) 
			{
				VolleyballerClnt selectedVolleyballerClnt = mAdapter.getSelectedVolleybaler();
				if(selectedVolleyballerClnt != null)
				{
					Intent intent = new Intent(FirstTeamSelectionActivity.this, VolleyballerInfoActivity.class);
					intent.putExtra("VOLLEYBALLER", selectedVolleyballerClnt);
					startActivity(intent);
				}
			}
		});
	}

	private void setFieldsListeners()
	{
		LinearLayout linLayout = (LinearLayout)findViewById(R.id.first_team_attacker_row);
		linLayout.setOnClickListener(new ClickListner());
		squadFieldList.add(linLayout);
		
		linLayout = (LinearLayout)findViewById(R.id.first_team_reciver1_row);
		linLayout.setOnClickListener(new ClickListner());
		squadFieldList.add(linLayout);
		
		linLayout = (LinearLayout)findViewById(R.id.first_team_blocker1_row);
		linLayout.setOnClickListener(new ClickListner());
		squadFieldList.add(linLayout);
		
		linLayout = (LinearLayout)findViewById(R.id.first_team_setter_row);
		linLayout.setOnClickListener(new ClickListner());
		squadFieldList.add(linLayout);
		
		linLayout = (LinearLayout)findViewById(R.id.first_team_reciver2_row);
		linLayout.setOnClickListener(new ClickListner());
		squadFieldList.add(linLayout);
		
		linLayout = (LinearLayout)findViewById(R.id.first_team_blocker2_row);
		linLayout.setOnClickListener(new ClickListner());
		squadFieldList.add(linLayout);
		
		linLayout = (LinearLayout)findViewById(R.id.first_team_libero_row);
		linLayout.setOnClickListener(new ClickListner());
		squadFieldList.add(linLayout);
		
		linLayout = (LinearLayout)findViewById(R.id.first_team_attacker_res_row);
		linLayout.setOnClickListener(new ClickListner());
		squadFieldList.add(linLayout);
		
		linLayout = (LinearLayout)findViewById(R.id.first_team_reciver1_res_row);
		linLayout.setOnClickListener(new ClickListner());
		squadFieldList.add(linLayout);
		
		linLayout = (LinearLayout)findViewById(R.id.first_team_blocker1_res_row);
		linLayout.setOnClickListener(new ClickListner());
		squadFieldList.add(linLayout);
		
		linLayout = (LinearLayout)findViewById(R.id.first_team_setter_res_row);
		linLayout.setOnClickListener(new ClickListner());
		squadFieldList.add(linLayout);
		
		linLayout = (LinearLayout)findViewById(R.id.first_team_reciver2_res_row);
		linLayout.setOnClickListener(new ClickListner());
		squadFieldList.add(linLayout);
	}
	
	private boolean fillTeamSquad(ArrayList<Integer> _teamSquad)
	{
		_teamSquad.clear();
		for(int i=0; i<squadFieldList.size(); i++)
		{
			TextView tmpTextView = (TextView)squadFieldList.get(i).findViewById(R.id.vol_list_row_text_nr);
			
			Log.d("FILL_TEAM_SQUAD", "" + tmpTextView.getText().toString());
			
	        if( tmpTextView.getText().toString().equals(""))
	        	return false;
	        else
	        {
	        	int volleyballerId = Integer.parseInt(tmpTextView.getText().toString() );
	        	_teamSquad.add(volleyballerId);
	        }
		}
		
		return true;
	}
	
	private void clearSquadField(LinearLayout _lin)
	{
		TextView nrTextView = (TextView) _lin.findViewById(R.id.vol_list_row_text_nr);
        nrTextView.setText("");
        
        TextView nameTextView = (TextView) _lin.findViewById(R.id.vol_list_row_text_name);
        nameTextView.setText("");
        
        TextView overallTextView = (TextView) _lin.findViewById(R.id.vol_list_row_text_overall);
        overallTextView.setText("");
	}
	
	private void fillSquadField(LinearLayout _lin, VolleyballerClnt _voll)
	{
		TextView nrTextView = (TextView)_lin.findViewById(R.id.vol_list_row_text_nr);
        nrTextView.setText("" + _voll.getId());
        
        TextView nameTextView = (TextView) _lin.findViewById(R.id.vol_list_row_text_name);
        nameTextView.setText(" " + _voll.getShortName() + " ");
        
        TextView overallTextView = (TextView) _lin.findViewById(R.id.vol_list_row_text_overall);
        DecimalFormat df = new DecimalFormat("##.00");
        overallTextView.setText(" " + df.format(_voll.getOverall()) + " ");
	}
	
	private void clearOthersFields(int id)
	{
		for(int i=0; i<squadFieldList.size(); i++)
		{
			int tmpId = getVolleyballerIdFromText( squadFieldList.get(i) );
			//Log.d("COMP", ""+tmpId +" "+ id);
			if( tmpId == id)
				clearSquadField(squadFieldList.get(i));
		}
	}
	
	private int getVolleyballerIdFromText(LinearLayout _linLayout)
	{
		TextView idTextView = (TextView)_linLayout.findViewById(R.id.vol_list_row_text_nr);
        if(idTextView.getText().toString().equals(""))
        	return -1;
        else
        	return Integer.parseInt(idTextView.getText().toString());
	}
	
	public void fillSquad(TeamMatchSquad tmp)
	{
		for(int i=0; i<squadFieldList.size(); i++)
		{
			VolleyballerClnt tmpVolleyballer = PlayerAllInfo.getInstance().getVolleyballerFromId( tmp.getVolleyballerIdFromPos(i));
			if(tmpVolleyballer != null)
				fillSquadField(squadFieldList.get(i), tmpVolleyballer);
		}
	}
	
	class ClickListner implements OnClickListener
	{
		@Override
		public void onClick(View v)
		{
			VolleyballerClnt selectedVolleyballer = mAdapter.getSelectedVolleybaler();
			Log.d("LIST", "" + selectedVolleyballer);
			TextView nrTextView = (TextView) v.findViewById(R.id.vol_list_row_text_nr);
			if(!nrTextView.getText().equals(""))
			{
				clearSquadField( (LinearLayout)v );
			}
			else if(selectedVolleyballer != null)
			{
				clearOthersFields(selectedVolleyballer.getId());
				
				fillSquadField((LinearLayout)v, selectedVolleyballer);
			}
		}
	}
	
	/**
	 * OnCompleteDownloadListner dla WelcomeActivity wsadzany do NewNetworkHTTP() class.
	 * 
	 * @author user
	 */
	class FirstTeamOnCompleteDownloadListner implements OnCompleteDownloadListner
	{
		public void downloadComplete(XMLType _flag, HTTPRespond _respond)
		{
			dissmisWaitDialog();
			switch(_flag)
			{
			case GET_SQD_HTTP: // TODO DODAC "SET_SQD" PRZYCHWYCIC POWODZENIE
				Log.d("HTTP", "GET_SQD_HTTP");
				if(_respond.getErrorCode() == ErrorCode.ERR_SQUAD_NOT_SET)
				{
					;//DO NOTHING
				}
				else if(_respond.getErrorCode() != -1)
				{
					showErrorDialog(_respond.getErrorMSG());
				}
				else
				{
					GetSquadRespondXML tmp = (GetSquadRespondXML)_respond;
					
					if(tmp.getTeamMatchSquad() != null)
					{
						fillSquad(tmp.getTeamMatchSquad());
					}
				}
				break;
			case SET_SQD_HTTP:
				Log.d("HTTP", "SET_SQD_HTTP");
				if(_respond.getErrorCode() == ErrorCode.ERR_SQUAD_NOT_SET)
				{
					;//DO NOTHING
				}
				else if(_respond.getErrorCode() != -1)
				{
					showErrorDialog(_respond.getErrorMSG());
				}
				else
				{
					GetSquadRespondXML tmp = (GetSquadRespondXML)_respond;
					
					if(tmp.getTeamMatchSquad() != null)
					{
						fillSquad(tmp.getTeamMatchSquad());
					}
				}
				break;
			}
		}
	}
}

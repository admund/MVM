package pl.admund.MVM_Client.Tactics;

import java.text.DecimalFormat;
import java.util.ArrayList;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import pl.admund.MVM_Client.R;
import pl.admund.MVM_Client.TCP.NetworkTCP;
import pl.admund.MVM_Client.TCP.TCPMessageCmd;
import pl.admund.MVM_Client.Volleyballer.VolleyballerClnt;
import pl.admund.MVM_Client.main.MyActivity;
import pl.admund.MVM_Client.main.PlayerAllInfo;
import pl.admund.MVM_Client.sportStuff.TeamClnt;

public class ChangeVolleyballerActivity extends MyActivity
{
	private ArrayList<LinearLayout> firstTeamList = new ArrayList<LinearLayout>();
	private ArrayList<LinearLayout> reserveList = new ArrayList<LinearLayout>();
	
	private ArrayList<Change> changeList = new ArrayList<Change>();
	
	private TeamMatchSquad teamMatchSquad;
	private TeamClnt team;
	
	private int firstTeamVolleyballerChoosen = -1;
	private int reserveTeamVolleyballerChoosen = -1;
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		
		setContentView(R.layout.change_volleyballer_activity_layout);
		
		setFieldsListeners();
		
		team = PlayerAllInfo.getInstance().getTeam();
		teamMatchSquad = PlayerAllInfo.getInstance().getTeamMatchSquad();
		Log.d("CHANGE_VOLL", team + " " + teamMatchSquad);
		if(teamMatchSquad != null)
			fillSquad(teamMatchSquad);
	}

	private void setFieldsListeners()
	{
		LinearLayout linLayout = (LinearLayout)findViewById(R.id.change_volleyballer_attacker_row);
		linLayout.setOnClickListener(new ClickListner());
		firstTeamList.add(linLayout);
		
		linLayout = (LinearLayout)findViewById(R.id.change_volleyballer_reciver1_row);
		linLayout.setOnClickListener(new ClickListner());
		firstTeamList.add(linLayout);
		
		linLayout = (LinearLayout)findViewById(R.id.change_volleyballer_blocker1_row);
		linLayout.setOnClickListener(new ClickListner());
		firstTeamList.add(linLayout);
		
		linLayout = (LinearLayout)findViewById(R.id.change_volleyballer_setter_row);
		linLayout.setOnClickListener(new ClickListner());
		firstTeamList.add(linLayout);
		
		linLayout = (LinearLayout)findViewById(R.id.change_volleyballer_reciver2_row);
		linLayout.setOnClickListener(new ClickListner());
		firstTeamList.add(linLayout);
		
		linLayout = (LinearLayout)findViewById(R.id.change_volleyballer_blocker2_row);
		linLayout.setOnClickListener(new ClickListner());
		firstTeamList.add(linLayout);
		
		linLayout = (LinearLayout)findViewById(R.id.change_volleyballer_libero_row);
		linLayout.setOnClickListener(new ClickListner());
		firstTeamList.add(linLayout);
		
		linLayout = (LinearLayout)findViewById(R.id.change_volleyballer_attacker_res_row);
		linLayout.setOnClickListener(new ClickListner());
		reserveList.add(linLayout);
		
		linLayout = (LinearLayout)findViewById(R.id.change_volleyballer_reciver1_res_row);
		linLayout.setOnClickListener(new ClickListner());
		reserveList.add(linLayout);
		
		linLayout = (LinearLayout)findViewById(R.id.change_volleyballer_blocker1_res_row);
		linLayout.setOnClickListener(new ClickListner());
		reserveList.add(linLayout);
		
		linLayout = (LinearLayout)findViewById(R.id.change_volleyballer_setter_res_row);
		linLayout.setOnClickListener(new ClickListner());
		reserveList.add(linLayout);
		
		linLayout = (LinearLayout)findViewById(R.id.change_volleyballer_reciver2_res_row);
		linLayout.setOnClickListener(new ClickListner());
		reserveList.add(linLayout);
	}
	
	public void onClickAddChangeBtn(View v)
	{
		Log.d("PLAY", "onClickAddChangeBtn()");
		if(firstTeamVolleyballerChoosen != -1 && reserveTeamVolleyballerChoosen != -1)
		{
			Log.d("PLAY", isAlreadyAddTochangeList(firstTeamVolleyballerChoosen) + " " + isAlreadyAddTochangeList(reserveTeamVolleyballerChoosen) + " " + (isAlreadyAddTochangeList(firstTeamVolleyballerChoosen) || isAlreadyAddTochangeList(reserveTeamVolleyballerChoosen)));
			if(isAlreadyAddTochangeList(firstTeamVolleyballerChoosen) || isAlreadyAddTochangeList(reserveTeamVolleyballerChoosen))
			{
				Toast.makeText(ChangeVolleyballerActivity.this, "Jedne z zawodnikow zostal juz dodany do listy zmian", Toast.LENGTH_SHORT).show();
				return;
			}
			
			changeList.add(new Change(firstTeamVolleyballerChoosen, reserveTeamVolleyballerChoosen));
			
			LinearLayout changeListLayout = (LinearLayout)findViewById(R.id.change_volleyballer_change_list);
			String text = team.getVolleyballerById(firstTeamVolleyballerChoosen).getShortName() + " --> " + team.getVolleyballerById(reserveTeamVolleyballerChoosen).getShortName();
			Log.d("PLAY", text);
			TextView changeTextView = new TextView(ChangeVolleyballerActivity.this);
			changeTextView.setText(text);
			changeListLayout.addView(changeTextView);
			changeListLayout.invalidate();
			
			clear(firstTeamList);
			clear(reserveList);
			
			firstTeamVolleyballerChoosen = -1;
			reserveTeamVolleyballerChoosen = -1;
		}
		else
		{
			Toast.makeText(ChangeVolleyballerActivity.this, "Musisz wybrac dwoch zawodnikow.", Toast.LENGTH_SHORT).show();
		}
	}
	
	public void onClickDeleteChangesBtn(View v)
	{
		Log.d("PLAY", "onClickDeleteChangesBtn()");
		
		changeList.clear();
		LinearLayout changeListLayout = (LinearLayout)findViewById(R.id.change_volleyballer_change_list);
		changeListLayout.removeAllViewsInLayout();
		changeListLayout.invalidate();
	}
	
	public void onClickDoChangesBtn(View v)
	{
		Log.d("PLAY", "onClickDoChangesBtn()");
		
		for(int i=0; i<changeList.size(); i++)
		{
			Log.d("PLAY", changeList.get(i).getVolleyballerId1() + " " + changeList.get(i).getVolleyballerId2());
			teamMatchSquad.doChange(changeList.get(i).getVolleyballerId1(), changeList.get(i).getVolleyballerId2());
			
			String changeStr = TCPMessageCmd.TCP_MSG_SQD_CHANGE + "_" + PlayerAllInfo.getInstance().getPlayerId() + "_"
						+ changeList.get(i).getVolleyballerId1() + "_" + changeList.get(i).getVolleyballerId2();
			NetworkTCP.getInstance().SendData(changeStr.getBytes());
		}
		
		fillSquad(teamMatchSquad);
		
		changeList.clear();
		LinearLayout changeListLayout = (LinearLayout)findViewById(R.id.change_volleyballer_change_list);
		changeListLayout.removeAllViewsInLayout();
		changeListLayout.invalidate();
	}
	
	private boolean isAlreadyAddTochangeList(int volleybalerId)
	{
		for(int i=0; i<changeList.size(); i++)
		{
			if(changeList.get(i).getVolleyballerId1() == volleybalerId)
				return true;
			
			if(changeList.get(i).getVolleyballerId2() == volleybalerId)
				return true;
		}
		return false;
	}
	
	public void fillSquad(TeamMatchSquad tmp)
	{
		for(int i=0; i<firstTeamList.size(); i++)
		{
			VolleyballerClnt tmpVolleyballer = PlayerAllInfo.getInstance().getVolleyballerFromId( tmp.getVolleyballerIdFromPos(i));
			Log.d("CHANG", "F: " + tmp.getVolleyballerIdFromPos(i) + " " + tmpVolleyballer);
			if(tmpVolleyballer != null)
				fillSquadField(firstTeamList.get(i), tmpVolleyballer);
		}
		
		for(int i=0; i<reserveList.size(); i++)
		{
			VolleyballerClnt tmpVolleyballer = PlayerAllInfo.getInstance().getVolleyballerFromId( tmp.getVolleyballerIdFromPos( firstTeamList.size() + i));
			Log.d("CHANG", "R: " + tmp.getVolleyballerIdFromPos(firstTeamList.size() + i) + " " + tmpVolleyballer);
			if(tmpVolleyballer != null)
				fillSquadField(reserveList.get(i), tmpVolleyballer);
		}
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
	
	private boolean isFromFirstTeam(View v)
	{
		for(int i=0; i<firstTeamList.size(); i++)
		{
			if(firstTeamList.get(i) == v)
				return true;
		}
		return false;
	}
	
	private boolean isFromReserve(View v)
	{
		for(int i=0; i<reserveList.size(); i++)
		{
			if(reserveList.get(i) == v)
				return true;
		}
		return false;
	}
	
	private void clear(ArrayList<LinearLayout> list)
	{
		for(int i=0; i<list.size(); i++)
			list.get(i).setBackgroundResource(R.drawable.vol_row_bg);
	}
	
	class ClickListner implements OnClickListener
	{
		@Override
		public void onClick(View v)
		{
			if(isFromReserve(v))
			{
				clear(reserveList);
				v.setBackgroundResource(R.drawable.vol_row_bg_red);
				
				TextView nrTextView = (TextView)v.findViewById(R.id.vol_list_row_text_nr);
				int volleyballerId = Integer.parseInt(nrTextView.getText().toString());
				reserveTeamVolleyballerChoosen = volleyballerId;
			}
			else if(isFromFirstTeam(v))
			{
				clear(firstTeamList);
				v.setBackgroundResource(R.drawable.vol_row_bg_red);
				
				TextView nrTextView = (TextView)v.findViewById(R.id.vol_list_row_text_nr);
				int volleyballerId = Integer.parseInt(nrTextView.getText().toString());
				firstTeamVolleyballerChoosen = volleyballerId;
			}
		}
	}
	
	class Change
	{
		private int volleyballerId1;
		private int volleyballerId2;
		
		public Change(int volleyballerId1, int volleyballerId2)
		{
			this.volleyballerId1 = volleyballerId1;
			this.volleyballerId2 = volleyballerId2;
		}
		
		public int getVolleyballerId1() {
			return volleyballerId1;
		}

		public int getVolleyballerId2() {
			return volleyballerId2;
		}
	}
}

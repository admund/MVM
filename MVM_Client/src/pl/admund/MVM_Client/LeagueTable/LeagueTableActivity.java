package pl.admund.MVM_Client.LeagueTable;

import pl.admund.MVM_Client.R;
import pl.admund.MVM_Client.XMLResponds.GetLeagueTableXML;
import pl.admund.MVM_Client.XMLResponds.HTTPRespond;
import pl.admund.MVM_Client.XMLUtils.HTTPGetAsyncTask;
import pl.admund.MVM_Client.XMLUtils.NetworkHTTP;
import pl.admund.MVM_Client.XMLUtils.OnCompleteDownloadListner;
import pl.admund.MVM_Client.XMLUtils.XMLCreator;
import pl.admund.MVM_Client.XMLUtils.XMLType;
import pl.admund.MVM_Client.main.MyActivity;
import pl.admund.MVM_Client.main.PlayerAllInfo;
import android.app.Dialog;
import android.os.Bundle;
import android.view.Window;
import android.widget.ListView;
import android.widget.TextView;

public class LeagueTableActivity extends MyActivity 
{
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		
		setContentView(R.layout.league_table_activity);
		setLegend();
		
		String tableUrl = XMLCreator.createGetLeagueTableURL(PlayerAllInfo.getInstance().getTeam().getLeagueId());
		new HTTPGetAsyncTask(XMLType.GET_TABLE_HTTP).execute(tableUrl);
		showWaitDialog("Get table...");
	}
	
	@Override
	protected void onResume()
	{
		super.onResume();
	
		NetworkHTTP.getInstance().setOnCompleteDownloadListner(new LeagueTableOnCompleteDownloadListner());
	}
	
	@Override
	protected Dialog onCreateDialog(int id, Bundle bundle) 
	{
		Dialog dialog = super.onCreateDialog(id, bundle);
		if(dialog != null)
			return dialog;
		
		switch(id)
		{
			default:
			{
				return null;
			}
		}
	}
	
	private void setLegend()
	{
        TextView posTextView = (TextView)findViewById(R.id.league_table_row_text_pos);
        posTextView.setText("Pos");
        
        TextView nameTextView = (TextView)findViewById(R.id.league_table_row_text_name);
        nameTextView.setText("Nazwa druzyny");
        
        TextView pointsTextView = (TextView)findViewById(R.id.league_table_row_text_pkt);
        pointsTextView.setText("Pkt");
        
        TextView matchCntTextView = (TextView)findViewById(R.id.league_table_row_text_match_cnt);
        matchCntTextView.setText("M");
        
        TextView matchWinTextView = (TextView)findViewById(R.id.league_table_row_text_match_win);
        matchWinTextView.setText("W");
        
        TextView matchLooseTextView = (TextView)findViewById(R.id.league_table_row_text_match_loose);
        matchLooseTextView.setText("L");
        
        TextView setWinTextView = (TextView)findViewById(R.id.league_table_row_text_set_win);
        setWinTextView.setText("SW");
        
        TextView setLooseTextView = (TextView)findViewById(R.id.league_table_row_text_set_loose);
        setLooseTextView.setText("SS");
        
        TextView setRationTextView = (TextView)findViewById(R.id.league_table_row_text_set_ratio);
        setRationTextView.setText("SR");
        
        TextView littleWinTextView = (TextView)findViewById(R.id.league_table_row_text_little_win);
        littleWinTextView.setText("MW");
        
        TextView littleLooseTextView = (TextView)findViewById(R.id.league_table_row_text_little_loose);
        littleLooseTextView.setText("MS");
        
        TextView littleRationTextView = (TextView)findViewById(R.id.league_table_row_text_little_ratio);
        littleRationTextView.setText("MR");
	}
	
	/**
	 * OnCompleteDownloadListner dla WelcomeActivity wsadzany do NewNetworkHTTP() class.
	 * 
	 * @author user
	 */
	class LeagueTableOnCompleteDownloadListner implements OnCompleteDownloadListner
	{
		public void downloadComplete(XMLType _flag, HTTPRespond _respond)
		{
			dissmisWaitDialog();
			switch(_flag)
			{
			case GET_TABLE_HTTP:
				if(_respond.getErrorCode() != -1)
				{
					showErrorDialog(_respond.getErrorMSG());
				}
				else
				{
					GetLeagueTableXML tmp = (GetLeagueTableXML)_respond;
					
					if(tmp.getSize() != 0)
					{
						ListView teamListView = (ListView)findViewById(R.id.league_table_activity_list);
						teamListView.setAdapter(new LeagueteamListAdapter(LeagueTableActivity.this, R.layout.league_table_row, tmp.getTable()));
					}
					else
					{
						String tableUrl = XMLCreator.createGetLeagueTableURL(PlayerAllInfo.getInstance().getTeam().getLeagueId());
						new HTTPGetAsyncTask(XMLType.GET_TABLE_HTTP).execute(tableUrl);
					}
				}
				break;
			}
		}
	}
}

package pl.admund.MVM_Client.LeagueTable;

import java.text.DecimalFormat;
import java.util.ArrayList;
import pl.admund.MVM_Client.R;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

public class LeagueteamListAdapter extends ArrayAdapter<TeamTableInfoClnt>
{
	private ArrayList<TeamTableInfoClnt> teamList;
	private Context context;
	
	public LeagueteamListAdapter(Context context, int textViewResourceId, ArrayList<TeamTableInfoClnt> objects) 
	{
		super(context, textViewResourceId, objects);
		
		this.context = context;
		this.teamList = objects;
	}
	
	@Override
    public View getView(int position, View convertView, final ViewGroup parent) 
	{
        View v = convertView;
        if (v == null) 
        {
            LayoutInflater vi = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = vi.inflate(R.layout.league_table_row, null);
        }
        TeamTableInfoClnt teamInfo = teamList.get(position);
        if(teamInfo != null) 
        {
        		LinearLayout rootLL = (LinearLayout)v.findViewById(R.id.league_table_row_layout_root);
        		/*rootLL.setMinimumHeight(30);
        		rootLL.setBackgroundColor(Color.BLACK);
        		rootLL.setOnClickListener(new OnClickListener()
        		{
					@Override
					public void onClick(View v)
					{
						selectOneItemList(parent, v);
						
						TextView nrTextView = (TextView) v.findViewById(R.id.vol_list_row_text_nr);
						int voloId = Integer.parseInt( (String)nrTextView.getText() );
						//Log.d("LIST", "click adapter: " + voloId);
						selectedVolleybaler = PlayerAllInfo.getInstance().getVolleyballerFromId(voloId);
					}
				});*/
        	
                TextView posTextView = (TextView) v.findViewById(R.id.league_table_row_text_pos);
                posTextView.setText("" + (position + 1));
                
                TextView nameTextView = (TextView)v.findViewById(R.id.league_table_row_text_name);
                nameTextView.setText(teamInfo.getTeamName());
                
                TextView pointsTextView = (TextView) v.findViewById(R.id.league_table_row_text_pkt);
                pointsTextView.setText("" + teamInfo.getPoints());
                
                TextView matchCntTextView = (TextView) v.findViewById(R.id.league_table_row_text_match_cnt);
                matchCntTextView.setText("" + teamInfo.getMatchCnt());
                
                TextView matchWinTextView = (TextView) v.findViewById(R.id.league_table_row_text_match_win);
                matchWinTextView.setText("" + teamInfo.getMatchWin());
                
                TextView matchLooseTextView = (TextView) v.findViewById(R.id.league_table_row_text_match_loose);
                matchLooseTextView.setText("" + teamInfo.getMatchLoose());
                
                TextView setWinTextView = (TextView) v.findViewById(R.id.league_table_row_text_set_win);
                setWinTextView.setText("" + teamInfo.getSetWin());
                
                TextView setLooseTextView = (TextView) v.findViewById(R.id.league_table_row_text_set_loose);
                setLooseTextView.setText("" + teamInfo.getSetLoose());
                
                DecimalFormat df = new DecimalFormat("0.000");
                TextView setRationTextView = (TextView) v.findViewById(R.id.league_table_row_text_set_ratio);
                setRationTextView.setText("" + df.format(teamInfo.getSetRatio()));
                
                TextView littleWinTextView = (TextView) v.findViewById(R.id.league_table_row_text_little_win);
                littleWinTextView.setText("" + teamInfo.getLittleWin());
                
                TextView littleLooseTextView = (TextView) v.findViewById(R.id.league_table_row_text_little_loose);
                littleLooseTextView.setText("" + teamInfo.getLittleLoose());
                
                TextView littleRationTextView = (TextView) v.findViewById(R.id.league_table_row_text_little_ratio);
                littleRationTextView.setText("" + df.format(teamInfo.getLittleRatio()));
        }
        return v;
    }
}

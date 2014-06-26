package pl.admund.MVM_Client.Tactics;

import java.text.DecimalFormat;
import java.util.ArrayList;
import pl.admund.MVM_Client.R;
import pl.admund.MVM_Client.Volleyballer.VolleyballerClnt;
import pl.admund.MVM_Client.main.PlayerAllInfo;
import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class VolleyballersListAdapter extends ArrayAdapter<VolleyballerClnt>
{
	private ArrayList<VolleyballerClnt> volleyballerList; 
	private Context context;
	private VolleyballerClnt selectedVolleybaler = null;
	
	public VolleyballersListAdapter(Context context, int textViewResourceId, ArrayList<VolleyballerClnt> objects) 
	{
		super(context, textViewResourceId, objects);
		
		this.context = context;
		this.volleyballerList = objects;
	}

	@Override
    public View getView(int position, View convertView, final ViewGroup parent) 
	{
        View v = convertView;
        if (v == null) 
        {
            LayoutInflater vi = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = vi.inflate(R.layout.volleybaler_list_row, null);
        }
        VolleyballerClnt volleyballer = volleyballerList.get(position);
        if(volleyballer != null) 
        {
        		LinearLayout rootLL = (LinearLayout)v.findViewById(R.id.vol_list_row_layout_root);
        		rootLL.setMinimumHeight(30);
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
				});
        		
                TextView nrTextView = (TextView) v.findViewById(R.id.vol_list_row_text_nr);
                nrTextView.setText("" + volleyballer.getId());
                
                ImageView flagImageView = (ImageView)v.findViewById(R.id.vol_list_row_flag);
                flagImageView.setImageResource(R.drawable.flag_poland);
                
                TextView nameTextView = (TextView) v.findViewById(R.id.vol_list_row_text_name);
                nameTextView.setText(" " + volleyballer.getShortName() + " ");
                
                TextView positionTextView = (TextView) v.findViewById(R.id.vol_list_row_text_position);
                positionTextView.setText(" " + volleyballer.getPossition().toString().substring(0, 3) + " ");
                
                ImageView injuryImageView = (ImageView)v.findViewById(R.id.vol_list_row_trim);
                setTrimImage(injuryImageView, volleyballer.getTrim());
                
                TextView overallTextView = (TextView) v.findViewById(R.id.vol_list_row_text_overall);
                DecimalFormat df = new DecimalFormat("##.00");
                overallTextView.setText(" " + df.format(volleyballer.getOverall()) + " ");
                
                ImageView moraleImage = (ImageView) v.findViewById(R.id.vol_list_row_morale);
                setMoraleImage(moraleImage, volleyballer.getMorale());
        }
        return v;
    }
	
	private void setTrimImage(ImageView imView, int _trim)
	{
		if(_trim > 80)
			imView.setImageResource(R.drawable.vol_trim_up);
		else if(_trim > 50)
			imView.setImageResource(R.drawable.vol_trim_med);
		else if(_trim > 5)
			imView.setImageResource(R.drawable.vol_trim_low);
		else
			imView.setImageResource(R.drawable.vol_injury);
	}
	
	private void setMoraleImage(ImageView imView, int _morale)
	{
		if(_morale > 70)
			imView.setImageResource(R.drawable.vol_morale_up);
		else if(_morale > 40)
			imView.setImageResource(R.drawable.vol_morale_med);
		else
			imView.setImageResource(R.drawable.vol_morale_low);
	}

	public VolleyballerClnt getSelectedVolleybaler() {
		return selectedVolleybaler;
	}
	
	private void selectOneItemList(ViewGroup viewGroup, View v)
	{
		for(int i=0; i<viewGroup.getChildCount(); i++)
		{
			if(viewGroup.getChildAt(i) != v)
				viewGroup.getChildAt(i).setBackgroundColor(Color.BLACK);
			else
				viewGroup.getChildAt(i).setBackgroundColor(Color.RED);
		}
	}
}

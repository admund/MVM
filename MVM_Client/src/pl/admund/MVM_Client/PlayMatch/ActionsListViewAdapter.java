package pl.admund.MVM_Client.PlayMatch;

import java.util.List;
import pl.admund.MVM_Client.R;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ActionsListViewAdapter extends ArrayAdapter<MatchAction>
{
	private Context context;
	private List<MatchAction> actionsList;
	
	public ActionsListViewAdapter(Context context, int textViewResourceId, List<MatchAction> objects) 
	{
		super(context, textViewResourceId, objects);
		this.context = context;
		this.actionsList = objects;
	}

	@Override
    public View getView(int position, View convertView, ViewGroup parent) 
	{
        View v = convertView;
        if (v == null)
        {
            LayoutInflater vi = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = vi.inflate(R.layout.action_list_row, null);
        }
        LinearLayout mainLayout = (LinearLayout)v.findViewById(R.id.action_list_row_activity_layout_root);
        
        MatchAction tmpMatchAction = actionsList.get(position);
        
        TextView actionNrTextView = new TextView(context);
        actionNrTextView.setText("Action pos: " + position + " s:" + actionsList.size() + " lp:" + tmpMatchAction.getLP());
        mainLayout.addView(actionNrTextView);
        
        //MatchAction tmpMatchAction = actionsList.get(position);
        for(int i=0; i<tmpMatchAction.getSingleActionsCnt(); i++)
        {
        	SingleMatchAction tmpSingleAction = tmpMatchAction.getSingleAction(i);
        	
            LinearLayout singleLayout = new LinearLayout(context);
            singleLayout.setOrientation(LinearLayout.HORIZONTAL);
            
            TextView textView = new TextView(context);
            textView.setText("\tGracz: " + tmpSingleAction.getVolleyballerId() + " ");
            
            ImageView actionImage = new ImageView(context);
            int imgRes = getImgRes(tmpSingleAction);
            actionImage.setBackgroundResource(imgRes);
            
            singleLayout.addView(textView);
            singleLayout.addView(actionImage);
            mainLayout.addView(singleLayout);
        }
        
        TextView endActionTextView = new TextView(context);
        endActionTextView.setText("<----------->");
        mainLayout.addView(endActionTextView);
        
        return v;
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
}

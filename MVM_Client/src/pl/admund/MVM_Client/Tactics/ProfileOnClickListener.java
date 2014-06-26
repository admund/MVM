package pl.admund.MVM_Client.Tactics;

import pl.admund.MVM_Client.Volleyballer.VolleyballerClnt;
import pl.admund.MVM_Client.Volleyballer.VolleyballerInfoActivity;
import pl.admund.MVM_Client.main.PlayerAllInfo;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Toast;

public class ProfileOnClickListener implements OnClickListener
{
	private int volleyballerId;
	private Context context;
	
	public ProfileOnClickListener(int volleyballerId, Context context)
	{
		this.volleyballerId = volleyballerId;
		this.context = context;
	}

	@Override
	public void onClick(View v) 
	{
		VolleyballerClnt tmpVolleyballerClnt = PlayerAllInfo.getInstance().getTeam().getVolleyballerById(volleyballerId);
		if(tmpVolleyballerClnt == null)
		{
			Toast.makeText(context, "Nie ma na liscie tego goscia...", Toast.LENGTH_SHORT).show();
			return;
		}
		
		Intent intent = new Intent(context, VolleyballerInfoActivity.class);
		intent.putExtra("VOLLEYBALLER", tmpVolleyballerClnt);
		context.startActivity(intent);
	}
}


package pl.admund.MVM_Client.Stadium;

import pl.admund.MVM_Client.R;
import pl.admund.MVM_Client.Utils.BitmapUtils;
import pl.admund.MVM_Client.main.MyActivity;
import pl.admund.MVM_Client.main.PlayerAllInfo;
import android.app.Dialog;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.os.Bundle;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

public class StadiumActivity extends MyActivity
{
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		
		setContentView(R.layout.stadium_activity);
		
		addStadiumImage();
		addStadiumInfo();
	}
	
	@Override
	protected void onResume() 
	{
		super.onResume();
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
			{
				return null;
			}
		}
	}
	
	private void addStadiumImage()
	{
		ImageView stadiumImage = (ImageView)findViewById(R.id.stadium_activity_stadium_image);
		
		Bitmap mainBmp = Bitmap.createBitmap(450, 450, Config.ARGB_8888);
		Canvas canvas = new Canvas(mainBmp);
		Bitmap bmp = BitmapUtils.getBitmapFromResId(this.getResources(), R.drawable.background);
		canvas.drawBitmap(bmp, new Rect(0, 0, bmp.getWidth(), bmp.getHeight()),
						new Rect(0, 0, 450, 450), null);
		
		bmp = BitmapUtils.getBitmapFromName(this, "stadium_lv3");
		canvas.drawBitmap(bmp, new Rect(0, 0, bmp.getWidth(), bmp.getHeight()),
						new Rect(100, 100, 350, 350), null);
		
		bmp = BitmapUtils.getBitmapFromName(this, "shop_lv3");
		canvas.drawBitmap(bmp, new Rect(0, 0, bmp.getWidth(), bmp.getHeight()),
						new Rect(50, 250, 200, 400), null);
		
		bmp = BitmapUtils.getBitmapFromName(this, "snack_bar_lv3");
		canvas.drawBitmap(bmp, new Rect(0, 0, bmp.getWidth(), bmp.getHeight()),
						new Rect(250, 250, 400, 400), null);
		
		canvas.save();
		
		stadiumImage.setImageBitmap(mainBmp);
	}
	
	private void addStadiumInfo()
	{
		TextView stadiumName = (TextView)findViewById(R.id.stadium_activity_stadium_name);
		stadiumName.setText("\t"+PlayerAllInfo.getInstance().getStadium().getName());
		
		TextView stadiumLvName = (TextView)findViewById(R.id.stadium_activity_stadium_lv);
		stadiumLvName.setText("\t"+PlayerAllInfo.getInstance().getStadium().getStadiumUpgLevel());
		
		TextView foodLvName = (TextView)findViewById(R.id.stadium_activity_snack_bar_lv);
		foodLvName.setText("\t"+PlayerAllInfo.getInstance().getStadium().getFoodUpgLevel());
		
		TextView shopLvName = (TextView)findViewById(R.id.stadium_activity_shop_lv);
		shopLvName.setText("\t"+PlayerAllInfo.getInstance().getStadium().getShopUpgLevel());
	}
}

package pl.admund.MVM_Client.Utils;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class BitmapUtils 
{
	private static BitmapFactory.Options opt = new BitmapFactory.Options();
	 
	public static Bitmap getBitmapFromResId(Resources res, int resId)
	{
		opt.inPurgeable = true;
		return BitmapFactory.decodeResource(res, resId, opt);
	}
	
	public static Bitmap getBitmapFromName(Context context, String resName)
	{
		opt.inPurgeable = true;
		int resID = context.getResources().getIdentifier(resName , "drawable", context.getPackageName());
		return BitmapFactory.decodeResource(context.getResources(), resID, opt);
	}
}

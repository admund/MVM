package pl.admund.MVM_Server.Utils;

import java.util.Random;

public class GlobalRandom 
{
	static Random ran = new Random(System.currentTimeMillis());
	
	public static int getInt()
	{
		return ran.nextInt();
	}
	
	public static int getInt(int range)
	{
		return ran.nextInt(range);
	}
	
	public static double getDouble()
	{
		return ran.nextDouble();
	}
	
	public static boolean getBoolean()
	{
		if(getInt(2) == 0)
			return true;
		else
			return false;
	}
}


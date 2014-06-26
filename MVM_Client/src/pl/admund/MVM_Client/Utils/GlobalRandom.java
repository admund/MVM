package pl.admund.MVM_Client.Utils;

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
}


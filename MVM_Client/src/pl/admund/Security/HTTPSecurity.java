package pl.admund.Security;

import pl.admund.Private.PrivateConst;

import android.util.Base64;

public class HTTPSecurity 
{
	public static String encrypt(String _str)
	{
		byte[] tmpStr = _str.getBytes();
		byte[] result = new byte[tmpStr.length];
		
		for(int i=0; i<tmpStr.length; i++)
		{
			result[i] = (byte)(tmpStr[i] ^ PrivateConst.getSecurityKey()[i % PrivateConst.getSecurityKey().length]);
		}
		
		return Base64.encodeToString(result, Base64.DEFAULT);//Base64.URL_SAFE);
	}
	
	public static String decrypt(String _str)
	{
		byte[] tmpStr = Base64.decode(_str, Base64.URL_SAFE);
		byte[] result = new byte[tmpStr.length];
		
		for(int i=0; i<tmpStr.length; i++)
		{
			result[i] = (byte)(tmpStr[i] ^ PrivateConst.getSecurityKey()[i % PrivateConst.getSecurityKey().length]);
		}
		
		return new String(result);
	}
}

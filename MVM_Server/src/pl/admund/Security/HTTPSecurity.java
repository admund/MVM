package pl.admund.Security;

import pl.admund.Private.PrivateConst;
import com.twiek.Utils.Base64;

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
		
		return Base64.encode( new String(result) );
	}
	
	public static String decrypt(String _str)
	{
		byte[] tmpStr = Base64.decode(_str).getBytes();
		byte[] result = new byte[tmpStr.length];
		
		for(int i=0; i<tmpStr.length; i++)
		{
			result[i] = (byte)(tmpStr[i] ^ PrivateConst.getSecurityKey()[i % PrivateConst.getSecurityKey().length]);
		}
		
		return new String(result);
	}
}

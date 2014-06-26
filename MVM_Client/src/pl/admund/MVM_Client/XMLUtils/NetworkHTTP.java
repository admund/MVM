package pl.admund.MVM_Client.XMLUtils;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;

import pl.admund.Private.PrivateConst;

import android.util.Log;

public class NetworkHTTP
{
//----------------------------------------------------------------------------
	private static NetworkHTTP myInstance;
	public static NetworkHTTP getInstance()
	{
		if(myInstance == null)
			myInstance = new NetworkHTTP();
		return myInstance;
	};
	private NetworkHTTP()
	{};
	
//----------------------------------------------------------------------------
	
	/**
	 * Metoda otwiera URLConnetion i pobiera z niego IputStream i zwraca go do parsowania
	 * 
	 * @param file
	 * @return
	 */
	public static InputStream DownloadGet(String file)
	{
		Log.d("URL STR", "" + file);
		String basicUrl = "http://" + PrivateConst.getHTTPAddr() + ":" + PrivateConst.getHTTPPort() + "/AVMServlet/WelcomeServlet";
		if(!file.equals(""))
			;//basicUrl += file;
			
		InputStream in = null;
		HttpURLConnection urlHttpConnection;
		try
		{
			/*
			Log.i("HTTP URL", "\t" + basicUrl);
		   	URL url = new URL(basicUrl);
		   	URLConnection urlConnection = url.openConnection();
		   	//in = new BufferedInputStream()
		   	//urlConnection.setConnectTimeout(10000);
			*/
			basicUrl += "?q=" + file;
			Log.d("URL STR", "" + basicUrl);
			
			URL url = new URL(basicUrl);
			urlHttpConnection = (HttpURLConnection)url.openConnection();
			
			/*urlHttpConnection.setRequestMethod("GET");
			urlHttpConnection.setDoOutput(true);
			Log.d("http msg", file);
			
			OutputStreamWriter out = new OutputStreamWriter(urlHttpConnection.getOutputStream());
			out.write(file);
			out.flush();*/
			
		   	in = new BufferedInputStream(urlHttpConnection.getInputStream());
		   	/*
		   	in.mark(10000);
		   	{
			   	BufferedReader rd = new BufferedReader(new InputStreamReader(in));
			   	String line;
			   	String s = "";
			   	while ((line = rd.readLine()) != null) 
			   	{
			   		s += line;
			   		s += "\n";
			   	}
			   	Log.d("HTTP rs", "->" + s + "<-");
		   	}
		   	in.reset();
		   	*/
		   	return in;
		 }
		 catch (Exception e) 
		 {
			 Log.e("XML", "C:" + e.getClass() + "\nM:" + e.getMessage()   );
			 e.printStackTrace();
			 return null;
		 }
	}
	
	public static InputStream DownloadPost(List<NameValuePair> pairs)
	{
		Log.d("URL STR", "send post: " + pairs.size() + " params cnt");
		String basicUrl = "http://" + PrivateConst.getHTTPAddr() + ":" + PrivateConst.getHTTPPort() + "/AVMServlet/WelcomeServlet";
			
		InputStream in = null;
		try
		{
			HttpClient client = new DefaultHttpClient();
			HttpPost post = new HttpPost(basicUrl);
			
			post.setEntity(new UrlEncodedFormEntity(pairs));
			
			HttpResponse response = client.execute(post);
			in = response.getEntity().getContent();
			
			return in;
		}
		catch (Exception e) 
		{
			 Log.e("XML", "C:" + e.getClass() + "\nM:" + e.getMessage()   );
			 e.printStackTrace();
			 return null;
		}
	}
	
	private OnCompleteDownloadListner onCompleteDownloadListner = null;
	
	public void setOnCompleteDownloadListner(OnCompleteDownloadListner onCompleteDownloadListner){
		this.onCompleteDownloadListner = onCompleteDownloadListner;
	}
	public void removeOnCompleteDownloadListner(){
		onCompleteDownloadListner = null;
	}
	public OnCompleteDownloadListner getOnCompleteDownloadListner(){
		return onCompleteDownloadListner;
	}
}

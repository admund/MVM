package pl.admund.MVM_Client.TCP;

import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InterruptedIOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import pl.admund.Private.PrivateConst;
import android.util.Log;

public class NetworkTCP
{
	static private String LOGTAG = "NETWORK";
	private static boolean DEBUG = true;
	
	public boolean isSocketReady;
	public Socket pSocket;
	
	private DataInputStream inStream;
	private ByteArrayOutputStream outStream;
	
	private String dstAddr = PrivateConst.getTCPAddr();
	private int peerPort = PrivateConst.getTCPPort();
	
////////////////////////////////////////////////////////////////////////////////////////////
	private static NetworkTCP myInstance;
	public static NetworkTCP getInstance()
	{
		if(myInstance == null)
			myInstance = new NetworkTCP();
		return myInstance;
	};
	private NetworkTCP()
	{};
////////////////////////////////////////////////////////////////////////////////////////////
	public void Init()
	{
		isSocketReady = false;
	};
	
	public void Init(String dstAddr, int port) 
	{
		isSocketReady = false;
		this.dstAddr = dstAddr;
		this.peerPort = port;
	};
	
////////////////////////////////////////////////////////////////////////////////////////////
	public void Start() 
	{
		Log.i("NET RECIVE", "\tLISTNER was STARTED");
		new Thread()
		{
			public void run()
			{
				while(pSocket != null)
				{		
					ReceiveData();
				}
			}
		}.start();
	};

////////////////////////////////////////////////////////////////////////////////////////////
	public Boolean Construct() 
	{
		isSocketReady = false;
		Log.d(LOGTAG, "Construct(): try");
		try 
		{
			pSocket = new Socket();
			pSocket.connect(new InetSocketAddress(dstAddr, peerPort), 5000);
			pSocket.setTcpNoDelay(true);
			pSocket.setKeepAlive(true);
			//pSocket.setSoTimeout(20000);//TODO socket timeout
			if( pSocket.isConnected() )
			{
				Log.d(LOGTAG, "Construct(): Socket is connected");
				isSocketReady = true;
				return true;
			}
		} 
		catch (IOException e) 
		{
			Log.e(LOGTAG, "Construct(): " + e.toString());
			CloseSocket();
		}
		return false;
	};
	
////////////////////////////////////////////////////////////////////////////////////////////
	public void CloseSocket() 
	{
		Log.d(LOGTAG, "CloseSocket(): try");
		if(pSocket != null)
		{
			try 
			{
				if(!pSocket.isClosed())
					pSocket.close();
			} 
			catch (IOException e)
			{
				Log.e(LOGTAG, "CloseSocket(): " + e.toString());
				pSocket = null;
			}
			catch (NullPointerException e) 
			{
				Log.e(LOGTAG, "CloseSocket(): " + e.toString());
				pSocket = null;
			}
		}
		pSocket = null;
		isSocketReady = false;
	};

////////////////////////////////////////////////////////////////////////////////////////////
	public boolean SendData(byte[] pMsg) 
	{
		Log.i("NET SEND", "\tMSG was SEND " + pMsg.length);
		if(isSocketReady != false)
		{
			Log.i("NET SEND", "\tMSG was SEND " + pMsg.length);
			try
			{
				outStream = new ByteArrayOutputStream(pMsg.length + 1);
				outStream.write( addSizeToByteTab(pMsg) );
				outStream.writeTo(pSocket.getOutputStream());
				return true;
			}
			catch (IOException e)
			{
				isSocketReady = false;
				Log.e(LOGTAG, "SendData(): " + e.toString());
			}
			catch(Exception e)
			{
				isSocketReady = false;
				Log.e(LOGTAG, "SendData(): " + e.toString());
			}
		}
		return false;
	};
		
////////////////////////////////////////////////////////////////////////////////////////////
	public void ReceiveData()
	{
		try 
		{
			if(pSocket == null)
				return;
			else
			{
				InputStream in = pSocket.getInputStream();
				int result = in.read();
				byte[] buff = new byte[result];
				in.read(buff);
				Log.d("TCP", "end: " + new String(buff));
				TCPMessageParser.parse(new String(buff));
			}
		} 
		catch (InterruptedIOException  e) 
		{
			Log.e(LOGTAG, "ReceiveData() InterruptedIOException: " + e.toString());
			e.getStackTrace();
			CloseSocket();
		}
		catch(IOException e) 
		{
			Log.e(LOGTAG, "ReceiveData(): " + e.toString());
			e.getStackTrace();
			CloseSocket();
		}
	}
	
////////////////////////////////////////////////////////////////////////////////////////////
	private byte[] addSizeToByteTab(byte[] tab)
	{
		byte[] newTab = new byte[tab.length + 1];
		newTab[0] = (byte)tab.length;
		for(int i=0; i < tab.length; i++)
			newTab[i+1] = tab[i];
		
		return newTab;
	}
	
	public boolean isSocketReady() {
		return isSocketReady;
	}
}
////////////////////////////////////////////////////////////////////////////////////////////
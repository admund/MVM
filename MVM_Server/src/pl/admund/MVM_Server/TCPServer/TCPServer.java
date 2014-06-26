package pl.admund.MVM_Server.TCPServer;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

import pl.admund.Private.PrivateConst;

public class TCPServer
{
	private ServerSocket serverSocket;
	private Socket tmpSocket;
	
	private static TCPServer myInstance;
	public static TCPServer getInstance()
	{
		if(myInstance == null)
			myInstance = new TCPServer();
		return myInstance;
	};
	private TCPServer(){};
	
	public boolean init()
	{
		System.out.println("TCP_Server init()");
		try 
		{
			InetAddress locIP = InetAddress.getByName(PrivateConst.getTCPAddr());
			serverSocket = new ServerSocket(PrivateConst.getTCPPort(), 0, locIP);
			System.out.println( "port: " + serverSocket.getLocalPort() + " addr:" + serverSocket.getLocalSocketAddress());
		} 
		catch (IOException e) 
		{
			System.out.println( "C:" + e.getClass() + "\nM:" + e.getMessage());
			return false;
		}
		return true;
	}
	
	public void runServer()
	{
		while(true)
		{
			try
			{
				System.out.println("TCP_Server run()");
				tmpSocket = serverSocket.accept();
				System.out.println("tmpSocket: " + tmpSocket);
				InputStream in = tmpSocket.getInputStream();
				System.out.println("in: " + in);
				
				int result = in.read();
				System.out.println("size: " + result);
				byte[] buff = new byte[result];
				in.read(buff);
				System.out.println("end: " + new String(buff));
				
				new TCPMessageParser(tmpSocket, new String(buff)).run();
			} 
			catch (IOException e)
			{
				System.out.println( "C:" + e.getClass() + "\nM:" + e.getMessage());
				e.printStackTrace();
			}
		}
	}
	
	
	public int byteCnt = 0;
	public boolean send(Socket socket, String msg)
	{
		if(socket != null && socket.isConnected())
		{
			try 
			{
				OutputStream out = socket.getOutputStream();
				byte msgPlusSize[] = addSizeToByteTab(msg.getBytes());
				
				byteCnt += msgPlusSize.length;
				
				System.out.println("\n\t\tmsg: " + msgPlusSize.length + " all: " + byteCnt);
				
				out.write(msgPlusSize);
				out.flush();
				return true;
			} 
			catch (IOException e) 
			{
				System.out.println( "C:" + e.getClass() + "\nM:" + e.getMessage());
				e.printStackTrace();
				try 
				{
					socket.close();
				} 
				catch (IOException e1) 
				{
					System.out.println( "C:" + e.getClass() + "\nM:" + e.getMessage());
					e1.printStackTrace();
				}
				return false;
			}
		}
		return false;
	}
	
	public void close()
	{
		System.out.println("TCP_Server close()");
		try 
		{
			serverSocket.close();
		} 
		catch (IOException e) 
		{
			System.out.println( "C:" + e.getClass() + "\nM:" + e.getMessage());
			e.printStackTrace();
		}
	}
	
////////////////////////////////////////////////////////////////////////////////////////////
	public byte[] addSizeToByteTab(byte[] tab)
	{
		byte[] newTab = new byte[tab.length + 1];
		newTab[0] = (byte)tab.length;
		for(int i=0; i < tab.length; i++)
		newTab[i+1] = tab[i];
		
		return newTab;
	}
////////////////////////////////////////////////////////////////////////////////////////////

}

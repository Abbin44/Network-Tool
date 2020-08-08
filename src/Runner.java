import java.util.List;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.ArrayList;

class Runner implements Runnable 
{
    static int port;
    final private String host;
    final private int startPort;
    
    public Runner(String host, int startPort) throws IOException, InterruptedException 
    {
        this.host = host;
        this.startPort = startPort;
    }

    public static List<Integer> openPorts = new ArrayList<Integer>();    
    public static List<Integer> commonPorts = new ArrayList<Integer>();    

    
    public static boolean foundPort = false;
    public void run() 
    {
    	//CreateCommonList();

        for (int port = startPort; port < 65535; port+=PortScanner.count) 
        {
            Socket socket;
                try 
                {
                    socket = new Socket();
                    socket.connect(new InetSocketAddress(host, port ), 500);
                    
                    openPorts.add(port);
                    
                    foundPort = true;
                    String temp = "Port " + port + " is open.\n";
                    System.out.write(temp.getBytes());
                    
                    socket.close();
                }
                catch (IOException ioEx) 
                {
                	
                }
         }
         Thread.yield();
    }
    
    public void CreateCommonList() 
    {
    	commonPorts.add(1);
    	commonPorts.add(5);
    	commonPorts.add(7);
    	commonPorts.add(18);
    	commonPorts.add(20);
    	commonPorts.add(21);
    	commonPorts.add(22);
    	commonPorts.add(23);
    	commonPorts.add(25);
    	commonPorts.add(29);
    	commonPorts.add(37);
    	commonPorts.add(42);
    	commonPorts.add(43);
    	commonPorts.add(49);
    	commonPorts.add(53);
    	commonPorts.add(69);
    	commonPorts.add(70);
    	commonPorts.add(79);
    	commonPorts.add(80);
    	commonPorts.add(103);
    	commonPorts.add(108);
    	commonPorts.add(109);
    	commonPorts.add(110);
    	commonPorts.add(115);
    	commonPorts.add(118);
    	commonPorts.add(119);
    	commonPorts.add(137);
    	commonPorts.add(139);
    	commonPorts.add(143);
    	commonPorts.add(150);
    	commonPorts.add(156);
    	commonPorts.add(161);
    	commonPorts.add(179);
    	commonPorts.add(190);
    	commonPorts.add(194);
    	commonPorts.add(197);
    	commonPorts.add(389);
    	commonPorts.add(396);
    	commonPorts.add(443);
    	commonPorts.add(444);
    	commonPorts.add(445);
    	commonPorts.add(458);
    	commonPorts.add(546);
    	commonPorts.add(547);
    	commonPorts.add(563);
    	commonPorts.add(569);
    	commonPorts.add(1080);
    }
}
import java.io.IOException;
import java.util.Scanner;

class PortScanner 
{
   static String ip = null;
   static int threadCount = 1;
   
   public static int count = 0;
   static void StartThreads(String host) throws InterruptedException, IOException 
   {
       Thread[] threads = new Thread[threadCount];
       System.out.println("Creating threads...\nRunning at " + threadCount + " threads.");
	   //LoadingBar.loading("Scanning..."); 

       for (int i = 0; i < threads.length; i++) 
       {
    	   threads[i] = new Thread(new Runner(host, count));
    	   threads[i].start();
    	   count++;
       }
       for (int i = 0; i < threads.length; i++) 
       {
           threads[i].join();
       }

       System.out.println("All ports are scanned");
       //System.out.println(Arrays.toString(Runner.openPorts.toArray()) + "\n");
   }
   
   
   public static void GetOptions() 
   {	   
	   Scanner sc = new Scanner(System.in);
	   
	   System.out.println("Enter IP address to scan");
	   ip = sc.nextLine();

	   System.out.println("Enter ammount of threads (15-50 is recommended)");	   
	   threadCount = sc.nextInt();

	   sc.close();
   }
}
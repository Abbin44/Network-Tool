import java.io.IOException;
import java.util.Scanner;

public class Controller
{
	   public static boolean onlyDomain = false;
	   public static boolean lookForKeyWord = false;

	   public static String domain;
	   public static String keyword = " ";
	   public static void main(String []args) throws InterruptedException, IOException 
	   {	
		   while(true) 
		   {
			   Scanner sc = new Scanner(System.in);
			   
			   System.out.println("Enter 1) for port scanner\nEnter 2) for web crawler\nEnter 3) for UDP Flood");
			   
			   String response = sc.nextLine();
			   System.out.flush(); 
			   
			   if(response.equals("1")) 
			   {
				   PortScanner.GetOptions(); //Request input to get IP address	   	   
				   PortScanner.StartThreads(PortScanner.ip);	 
			   }
			   else if(response.equals("2")) 
			   {
				   Spider spider = new Spider();
				   
				   System.out.println("Enter a url to scan: (https://example.com)");			   
				   domain = sc.nextLine();
				   System.out.flush(); 
				   
				   System.out.println("Do you want to search for a keyword? (Y/N)");			   
				   response = sc.nextLine();
				   System.out.flush(); 
				   
				   if(response.equals("Y") || response.equals("y"))
				   {
					   lookForKeyWord = true;
					   System.out.println("Enter keyword: ");			   
					   keyword = sc.nextLine();
				   }
				   
			       System.out.println("Do you want to show links outside of the entered domain? (Y/N)");
				   response = sc.nextLine();
				   System.out.flush(); 
				   
				   if(response.equals("Y") || response.equals("y")) 
					   onlyDomain = false;
				   else if(response.equals("N") || response.equals("n")) 
					   onlyDomain = true;
				   else
				       System.out.println("Plese enter a valid answer");	
				   
				   System.out.println("Enter the maximum ammount of pages you want to crawl through");			   
				   Spider.maxPagesToScan = sc.nextInt();	
				   System.out.flush(); 
				   
				   System.out.println("Scanning on: " + domain);
				   spider.search(domain, keyword);
				   
				   WantToSave();
			   }
			   else if(response.equals("3")) 
			   {
				   UdpFlood flooder = new UdpFlood();
			   }
			   else
				   System.out.println("Error...");			   
		   }		   
	   }
	   
	   static void WantToSave() throws IOException 
	   {
	            System.out.println("\n**Done** Visited " + Spider.pagesVisited.size() + " web page(s)");       
	            System.out.println("\nDo you want to save all found data in text files? (Y/N)");
	            
	            Scanner sc = new Scanner(System.in);
	            String response = sc.nextLine();
	            
	            if(response.equals("Y") || response.equals("y")) 
	            {			   
	            	System.out.println("Saving files...");
	            	FileCreator fc = new FileCreator(domain);
	            }        
	    		System.out.flush();
	   }
}

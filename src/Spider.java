
import java.io.IOException;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public class Spider
{
    public static int maxPagesToScan;
    public static Set<String> pagesVisited = new HashSet<String>(); //A list of unique links to visit //Set == a list without duplicates
    public static List<String> pagesToVisit = new LinkedList<String>(); //All possible urls to visit
    
    public void search(String url, String searchWord) throws IOException
    {
        while(pagesVisited.size() < maxPagesToScan)
        {
            String currentUrl;
            SpiderLeg leg = new SpiderLeg();                        
            
            if(pagesToVisit.isEmpty())
            {
                currentUrl = url;
                pagesVisited.add(url);
            }
            else
            {
                currentUrl = this.GetNextURL();
            }
            
            leg.crawl(currentUrl);
            
            if(Controller.lookForKeyWord == true) 
            {            	
            	boolean success = leg.searchForWord(searchWord);            
            	if(success)
            	{
            		System.out.println(String.format("**Success** Word %s found at %s", searchWord, currentUrl));
            		//break;
            	}
            }
            
            pagesToVisit.addAll(leg.getLinks());
        }     
    }
    
    private String GetNextURL() 
    {
    	String nextUrl;
    	do 
    	{
    		nextUrl = pagesToVisit.remove(0);
    	}
    	while(pagesVisited.contains(nextUrl));
    	{ 
    		pagesVisited.add(nextUrl);
            return nextUrl;    		
    	}
    }
}


import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.DataNode;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class SpiderLeg
{
    //fake USER_AGENT so the web server thinks the robot is a normal web browser.
    private static final String USER_AGENT =
            "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/535.1 (KHTML, like Gecko) Chrome/13.0.782.112 Safari/535.1";
    
    //Urls
    public static Set<String> urls = new HashSet<String>();
    private Set<String> urlList = new HashSet<String>();
       
    //PrintedLists
    public static Set<String> proccessedUrls = new HashSet<String>();
    public static Set<String> proccessedLinkTags = new HashSet<String>();
    public static Set<String> proccessedInputTags = new HashSet<String>();
    public static Set<String> proccessedScriptTags = new HashSet<String>();

    public static Set<String> scripts = new HashSet<String>();//Scripts   
    public static Set<String> inputTags = new HashSet<String>();//Input
    public static Set<String> linkTags = new HashSet<String>();//LinkTags

    private Document htmlDocument;

    public boolean crawl(String url) throws IOException
    {
        try
        {
        	System.out.println(url);
            Connection connection = Jsoup.connect(url).userAgent(USER_AGENT);
            Document htmlDocument = connection.get();
            this.htmlDocument = htmlDocument;

            
            if(connection.response().statusCode() == 200) // 200 is the HTTP OK status code                                                          // indicating that everything is great.
            {
                //System.out.println("\nVisiting web page: " + url);
            }
            
            if(!connection.response().contentType().contains("text/html"))
            {
                System.out.println("Retrieved something other than HTML");
                return false;
            }
            
            GrabInputTag();
            GrabScriptTag();
            GrabUrls();
            GrabLinkRel();
            
            return true;
        }
        catch(IOException ioe)
        {        	
            return false;
        }
    }
    
    private void GrabLinkRel() 
    {
    	Elements linkRelOnPage = htmlDocument.getElementsByTag("link");

        for(Element linkTag : linkRelOnPage)
        {
            	try 
            	{            		
            		if(linkTag.absUrl("href").contains(Controller.domain)) 
            		{
                		boolean alreadyPrinted = proccessedLinkTags.contains(linkTag.absUrl("href")); 
                        if (alreadyPrinted == false)
                    		System.out.println("LinkRel: --> " + linkTag.absUrl("href"));

                        proccessedLinkTags.add(linkTag.absUrl("href"));                        
                		linkTags.add(linkTag.absUrl("href"));  
            		}
            	}
            	catch(Exception e) 
            	{
                    System.out.println("Error:"); 
        	    }          
        }
    }
    
    private void GrabInputTag() 
    {
        Elements inputsOnPage = htmlDocument.getElementsByTag("input");

        for(Element inputs : inputsOnPage)
        {
            	try 
            	{        
            		boolean alreadyPrinted = proccessedInputTags.contains(inputs.outerHtml()); 
                    if (alreadyPrinted == false)
                		System.out.println(inputs.outerHtml());
                    
                    proccessedInputTags.add(inputs.outerHtml());                     
            		inputTags.add(inputs.outerHtml());              	
            	}
            	catch(Exception e) 
            	{
                    System.out.println("Error:"); 
        	    }          
        }
    }
    
    private void GrabScriptTag()
    {
        Elements scriptsOnPage = htmlDocument.getElementsByTag("script");

        for(Element script : scriptsOnPage)
        {
        	for(DataNode node : script.dataNodes()) 
        	{
            	try 
            	{            
            		boolean alreadyPrinted = proccessedUrls.contains(node.getWholeData()); 
                    if (alreadyPrinted == false)
                        //System.out.println(node.getWholeData());

                    proccessedScriptTags.add(node.getWholeData());                    
            		scripts.add(node.getWholeData()); 
            		
            		//System.out.println(node.getWholeData());
            	}
            	catch(Exception e) 
            	{
                    System.out.println("Error: Cannot add script to list");
            	}    
        	}          
        }
    }
       
    private void GrabUrls() 
    {
    	//Getting links from element object
        Elements linksOnPage = htmlDocument.select("a[href]");

        for(Element link : linksOnPage)
        {
        	try 
        	{            		
        		urls.add(link.absUrl("href"));                		       
        	}
        	catch(Exception e) {
                System.out.println("Error: Cannot add link to list");
        	}                
        }

        //Printing list
        for(String link : urls)
        {
        	urlList.add(link);
            if(Controller.onlyDomain == true)
            {
            	if(link.contains(Controller.domain)) 
                {
            		boolean alreadyPrinted = proccessedUrls.contains(link); 
                    if (alreadyPrinted == false)
                        System.out.println(link);

                    proccessedUrls.add(link);
                }
            }
            else if(Controller.onlyDomain == false) 
        	{
        		boolean alreadyPrinted = proccessedUrls.contains(link); 
                if (alreadyPrinted == false) 
                    System.out.println(link);

                proccessedUrls.add(link);
        	}                       	
        }         
    }
    
    public boolean searchForWord(String searchWord)
    {
        if(this.htmlDocument == null)
        {
            System.out.println("ERROR! Call crawl() before performing analysis on the document");
            return false;
        }
        System.out.println("Searching for the word " + searchWord + "...");
        String bodyText = this.htmlDocument.body().text();
        return bodyText.toLowerCase().contains(searchWord.toLowerCase());
    }


    public Set<String> getLinks()
    {
        return this.urlList;
    }
}
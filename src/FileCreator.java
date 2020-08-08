import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FileCreator
{
	public FileCreator(String domain) throws IOException
	{
		System.out.println(domain);		
		domain = domain.replaceAll("[^a-zA-Z0-9öÖäÄüÜß@\\s]", "");
		System.out.println(domain);	
		
		if(domain.contains("https"))
			domain = domain.replace("https", "");
		else if(domain.contains("http"))
			domain = domain.replace("http", "");
		
		System.out.println(domain);	

				
        String folderPath = "/Website Data";
        Path path = Paths.get(folderPath);
        if (!Files.exists(path)) 
        {            
            Files.createDirectory(path);
        }
        
		try
		{
			FileWriter w = new FileWriter(new File("/Website Data/" + domain +" - Urls.txt")); 
			for(String url: SpiderLeg.proccessedUrls) 
			{
			  w.write(url + System.lineSeparator());
			}
			w.close();
			
			FileWriter w1 = new FileWriter(new File("/Website Data/" + domain +" - LinkTags.txt"));
			for(String linkTag: SpiderLeg.proccessedLinkTags) 
			{
			  w1.write(linkTag + System.lineSeparator());
			}
			w1.close();

			FileWriter w2 = new FileWriter(new File("/Website Data/" + domain + " - InputTags.txt"));
			for(String inputTag: SpiderLeg.proccessedInputTags) 
			{
			  w2.write(inputTag + System.lineSeparator());
			}
			w2.close();
			
			FileWriter w3 = new FileWriter(new File("/Website Data/" + domain + " - ScriptTags.txt"));
			for(String scriptTag: SpiderLeg.proccessedScriptTags) 
			{
			  w3.write(scriptTag + System.lineSeparator());
			}
			w3.close();
			
			System.out.println("All files saved in root:/Crawler Scan/");		
		}
		catch(IOException e) 
		{
			System.out.println("Couldn't save files...");
		}
	}
}

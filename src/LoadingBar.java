import java.io.IOException;

public class LoadingBar
{
    static synchronized void loading(String msg) throws IOException, InterruptedException 
    {
        Thread th = new Thread() 
        {
            @Override
            public void run() 
            {
                try 
                {
                   System.out.write("\r|".getBytes());            			   
             	   if(Runner.foundPort == false) 
             	   {
                  	   while(true)
                  	   {
                  		   System.out.write("-".getBytes());
                  		   Thread.sleep(700);                                   		   
                  	   }
             	   }
             	   else if(Runner.foundPort == true) 
             	   {
                 	   Runner.foundPort = false;
             	   }
                    
                } 
                catch (IOException e) 
                {
                    e.printStackTrace();
                } 
                catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        th.start();
    }
}

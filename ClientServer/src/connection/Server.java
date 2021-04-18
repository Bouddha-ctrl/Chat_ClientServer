package connection;


import java.io.IOException;
import java.net.*;

public class Server implements Runnable {

	private  ServerSocket server = null;
	private  Thread       thread = null;
	private int clientCount = 0;

	private ServerThread clients[] = new ServerThread[50];    //tableau des clients connecter

	
	public Server(int port) throws Exception {
		try {
			System.out.println("[SERVER] starting ...");
			server = new ServerSocket(port);
			System.out.println("[SERVER] Server Started!   :   "+server);

			start();

			
		}catch(Exception ex){
			System.out.println("[SERVER] Connection error :"+ex);
			throw ex;
        }
		
		
	}
	
	public void addClients(Socket socket)
	   {  
		if (clientCount < clients.length)
		{
			try
			{
				clients[clientCount] = new ServerThread(this, socket);
				System.out.println("[SERVER] CLient connected! ID : "+clients[clientCount].getID());
				clients[clientCount].open();
				clients[clientCount].start();
				clientCount++;
						
			}catch(IOException e)
			{
				System.out.println("Error opening thread: " + e);
			}
			
		}else
		{
		    System.out.println("Client refused: maximum " + clients.length + " reached.");	
		}

	   }
	 private int findClient(int ID)
	   {  for (int i = 0; i < clientCount; i++)
	         if (clients[i].getID() == ID)
	            return i;
	      return -1;
	   }
	 
	public synchronized void handle(int ID, String input)
	   {  
		System.out.println("Msg received from  "+ID+" : "+input);
		if (input.contains(".bye"))
	      {  
	       remove(ID); 
	       }
	      else 
	      {
	         for (int i = 0; i < clientCount; i++) {
	            clients[i].send(input);   
	         }
	         System.out.println("Msg broadcasted");
	      }
	   }
	
	public void run()
	{  
		while (thread != null)
	      {   
			try
	         {  
				System.out.println("[SERVER] Waiting for new Client..."); 
	            addClients(server.accept());
	         }
	         catch(IOException ie)
	         {  
	        	 System.out.println("[SERVER] Acceptance Error: " + ie);  
	         }
			 catch(Exception ex) 
			 {
	        	 System.out.println("[SERVER] Error: " + ex);  
			 }
	      }
	   }
	
	public synchronized void remove(int ID)
	   {  
		  int pos = findClient(ID);
	      if (pos >= 0)
	      {  
	    	 try {
	    	 ServerThread toTerminate = clients[pos];
	         //System.out.println("[SERVER] Client disconnected :" + ID + " at " + pos);
	         if (pos < clientCount-1)
	            for (int i = pos+1; i < clientCount; i++)
	               clients[i-1] = clients[i];
	         clientCount--;
	    	
	         
	        	 toTerminate.close(); 
	         }catch(IOException ioe)
	         {  
	        	 System.out.println("Error closing thread: " + ioe); 
	         }catch(Exception e) {
	        	 System.out.println("test");
	         }
	       }
	   }
	
	public void start()
	   {  
		if (thread == null)
	      {  
			thread = new Thread(this);
	        thread.start();
	      }
	   }
	
	@SuppressWarnings("deprecation")
	public void stop()
	   {  
		System.out.println("closing server!");
		if (thread != null)
	      {  thread.stop(); 
	         thread = null;
	      }
	   try {
			server.close();
			//System.exit(0);
		} catch (IOException e) {
			e.printStackTrace();
		}
	   }

	

	
	
}

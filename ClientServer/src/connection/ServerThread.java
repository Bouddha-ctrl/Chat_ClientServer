package connection;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.*;

public class ServerThread extends Thread{
	
		private Socket      socket   = null;
		private Server    		  server   = null;

		private DataInputStream din =  null;
		private DataOutputStream dout = null;

		private int ID = -1;


	   public ServerThread(Server _server, Socket _socket)
	   {  
		   server = _server;  
		   socket = _socket;  
		   ID = socket.getPort();
	   }
	   
	   public void send(String msg)
	   {   
		   try
	       {  
			  dout.writeUTF(msg);
	          dout.flush();
	       }
	       catch(IOException ioe)
	       {  System.out.println(ID + " ERROR sending: " + ioe.getMessage());
	          server.remove(ID);
	          stop();
	       }
	   }
	   public int getID()
	   {  return ID;
	   }
	   
	   
	   public void run()
	   {  
	       while (true)
	       {  
	    	   try
	    	   {  
	    		String line = din.readUTF();
	    		   
	    		server.handle(ID, line);  // broadcast in handle with send()
	    		   
	           }
	           catch(IOException ioe) 
	    	   {
	        	   System.out.println(ID + " ERROR reading: " + ioe.getMessage());
	               server.remove(ID);
	               stop();
	    	   
	    	   }
	       }
	   }
	   
	   
	   public void open() throws IOException
	   {  
		   din=new DataInputStream(socket.getInputStream());
		   dout = new DataOutputStream(socket.getOutputStream());

	   }
	   

		public void close() throws IOException {
		    if (din != null)  din.close();
		    if (dout != null)  dout.close();
		    if (socket != null)  socket.close();
			this.stop();


		}
		
		
	   

}

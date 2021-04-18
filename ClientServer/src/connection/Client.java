package connection;

import java.io.*;
import java.net.*;


import GUI.JClient;


public class Client {
	Socket socket = null;

	DataOutputStream dout = null;
	BufferedReader sc = null;
	ClientThread client    = null;
	JClient frame;

	private String username = null;
	
	public Client(String Server_Ip,int port,String user,JClient framee) throws IOException {
		try {
			this.frame =  framee;
			socket = new Socket(Server_Ip,port);
			System.out.println("Vous etes connecté au serveur :"+socket);
			username = user;
			System.out.println("Now you can start chating "+username+" !");
			start();
		} catch (IOException ioe) {
	         System.out.println("Connection error: " + ioe.getMessage());
	         throw ioe;
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
	}
	 public void handle(String msg)
	   {  
		
		  frame.setChat(msg);
		  System.out.println(msg);

	   }
	 
	public void start() {
		open();
		send(".start");
		client = new ClientThread(this, socket);
		
	}
	public void stop()
	   {  
	      try
	      {  
	    	  close();
	    	  client.close(); 

	      }
	      catch(IOException ioe)
	      {  System.out.println("Error closing ..."); }
	      
	   }
	
	public void open()  {
		try {
			
			dout = new DataOutputStream(socket.getOutputStream());
			sc = new BufferedReader(new InputStreamReader(System.in)); 
		
		} catch (IOException e) {
		    System.out.println("Error getting output stream: " + e);
			e.printStackTrace();
		}  
	}
	
	public void close()  throws IOException {
        System.out.println("Disonnected! ");
        System.exit(0);
		dout.close();
		sc.close();
		socket.close();
		
	}

	public void send(String line) {
		
			try
	         {  
				if (line.contains(".bye")) 
				{
				   line = "[SERVER] "+username+" is disconnected !";
				   System.out.println("Good bye.");
				   dout.writeUTF(line);

				   line = ".bye";
				   dout.writeUTF(line);
				   dout.flush();
			       stop();
				}
				else if (line.equals(".start")) {
					dout.writeUTF("[SERVER] "+username+" is connected ");
					dout.flush();
				}
				else {
					line = username + ": "+line;
					dout.writeUTF(line);
					dout.flush();
				}
	         }
	         catch(IOException ioe)
	         {  System.out.println("Sending error: " + ioe.getMessage());
	            stop();
	         }
			
	}
	
	

}

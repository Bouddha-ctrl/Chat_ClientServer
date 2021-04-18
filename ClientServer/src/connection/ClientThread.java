package connection;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.*;

public class ClientThread extends Thread{

	private Socket           socket   = null;
	private Client       client   = null;
	private DataInputStream  din = null;

	public ClientThread(Client client, Socket socket) {
		this.client = client;
		this.socket = socket;
		open();
		start();
	}
	public void open() {
		try {
			din = new DataInputStream(socket.getInputStream());
			
		} catch (IOException e) {
		    System.out.println("Error getting input stream: " + e);
	        client.stop();
		    e.printStackTrace();
		}
	}

	public void close() {
		try {
			din.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void run() {
		while(true) {
			try {
				client.handle(din.readUTF());
				
			} catch (IOException ioe) {
				System.out.println("Listening error: " + ioe.getMessage());
	            client.stop();
			}
		}
	}
}

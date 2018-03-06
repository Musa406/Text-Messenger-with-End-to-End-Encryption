package clientServerClient;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Vector;

public class Server extends Thread{
static Vector<ClientControlar> ar = new Vector<>();

  public static void main(String args[]) {
	  
	  		
	  		try {
	  			ServerSocket severMainSocket = new ServerSocket(9999);
	  			
	  			while(true) {
	  					
	  					Socket socketForClient = severMainSocket.accept();
	  					
	  					
	  					//input,output
	  					DataInputStream is = new DataInputStream(socketForClient.getInputStream());
	  					DataOutputStream os = new DataOutputStream(socketForClient.getOutputStream());
	  					//input,output
	  					
	  					
	  					String username = is.readUTF();
	  					
	  					System.out.println("server class::"+username);
	  					System.out.println("Accepted client..."+username);
	  					
	  					ClientControlar clients = new ClientControlar(socketForClient,username,is,os);
	  					Thread newClient = new Thread(clients); 
	  					
	  					
	  					
	  					//adding client into the vector for list
	  					ar.add(clients);
	  					
	  					for(ClientControlar ct: Server.ar) { 
	  						//System.out.println("online user...");
	  						ct.os.writeUTF(username);
	  						//ar.
	  						System.out.println("online user..."+ct.clientName);
	  						try {
								Thread.sleep(50);
							} catch (InterruptedException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
	  					}
	  					
	  					
	  					//starting thread
	  					newClient.start();
	  					
	  					//i++;
	  			}
	  			
	  			
	  		}catch(IOException e1) {
	  			System.out.println("connection problem occured...");
	  		}
	  }
}
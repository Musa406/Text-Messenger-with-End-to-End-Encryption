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

static int i = 1; //counter for clients

  public static void main(String args[]) {
	  
	  		
	  		try {
	  			ServerSocket severMainSocket = new ServerSocket(9999);
	  			
	  			while(true) {
	  					
	  					Socket socketForClient = severMainSocket.accept();
	  					System.out.println("Accepted client..."+i);
	  					
	  					//input,output
	  					//BufferedReader is = new BufferedReader(new InputStreamReader(socketForClient.getInputStream()));
	  					DataInputStream is = new DataInputStream(socketForClient.getInputStream());
	  					DataOutputStream os = new DataOutputStream(socketForClient.getOutputStream());
	  					//input,output
	  					
	  					//creating a new thread for this client
	  					ClientControlar clients = new ClientControlar(socketForClient,"client "+i,is,os);
	  					Thread newClient = new Thread(clients);
	  				
	  					//adding client into the vector for list
	  					ar.add(clients);
	  				
	  					//starting thread
	  					newClient.start();
	  					
	  					i++;
	  			}
	  			
	  			
	  		}catch(IOException e1) {
	  			System.out.println("connection problem occured...");
	  		}
	  }
}
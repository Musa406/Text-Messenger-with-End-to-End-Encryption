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
static Vector<String>userList = new Vector();
static int i=0;
  public static void main(String args[]) {
	  		
	  		
	  		
	  		try {
	  			ServerSocket severMainSocket = new ServerSocket(9999);
	  			//ServerSocket severMainSocketList = new ServerSocket(9999);//for user list sending
	  		
	  			while(true) {
	  				    
	  				
	  					
	  					Socket socketForClient = severMainSocket.accept();
	  					//Socket socketList = severMainSocketList.accept();
	  					
	  					
	  					//input,output
	  					DataInputStream is = new DataInputStream(socketForClient.getInputStream());
	  					DataOutputStream os = new DataOutputStream(socketForClient.getOutputStream());
	  					//input,output
	  					
	  					
	  					String username = is.readUTF();
	  					
	  					userList.add(username);
	  					System.out.println("server class::"+username);
	  					System.out.println("Accepted client..."+username);
	  					
	  					ClientControlar clients = new ClientControlar(socketForClient,username,is,os);
	  					Thread newClient = new Thread(clients); 
	  					
	  					
	  					
	  					//adding client into the vector for list
	  					ar.add(clients);
	  					
	  					newClient.start();
	  					i++;
	  					
	  					for(ClientControlar ct: Server.ar) { 
	  						//if(ct.clientName.equals(msgReceiver) && (ct.isLogIn==true)) {
	  							for(String j: userList)
	  								ct.os.writeUTF(" #"+j);
	  							
	  					}
	  					
	  					
	  			}
	  			
	  			
	  		}catch(IOException e1) {
	  			System.out.println("connection problem occured...");
	  		}
	  }
}
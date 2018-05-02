package serverSide;

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
static Vector<ClientController> ar = new Vector<>();

static Vector<Thread>threadVec = new Vector<>();

static Vector<String>userList = new Vector();
static int i=0;
  public static void main(String args[]) {
	  		
	  ReadXML authentic = new ReadXML();
	  		
	  		
	  		try {
	  			ServerSocket severLogin = new ServerSocket(9999);
	  	
	  		
	  			while(true) {
	  				    
	  				
	  					
	  					Socket socketForClient = severLogin.accept();
	  					
	  					
	  					DataInputStream is = new DataInputStream(socketForClient.getInputStream());
	  					DataOutputStream os = new DataOutputStream(socketForClient.getOutputStream());
	  					//input,output
	  					
	  					
	  					String username = is.readUTF();
	  					
	  					String [] info = username.split("#!#");
	  					
	  					
	  			//sign up............................................		
	  			if(info[0].equals("$%")) {
	  				
	  				System.out.println("Sign up Sccessful.!!");
	  				
	  				writeXMLfile writeInfo = new writeXMLfile();
	  				try {
						writeInfo.writeXML(info[1], info[2], info[3], info[4],info[5]);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
	  				
	  				
	  			}		
	  					
	  			//signup..end............................................
	  			
	  			//sign in...................................................
	  			
	  			else {
	  					
	  					String [] signInfo = username.split("@!@");
	  					String [] signInfo2 = signInfo[1].split("               ");
	  					
	  					String uName = signInfo2[0];
	  					String pass = signInfo[0];
	  					
	  					authentic.ReadXml(uName, pass);
	  					
	  				if(authentic.flag==true) {	
		  					
		  					os.writeUTF("Login Successful!!!#!# " );
		  					userList.add(signInfo[1]);
		  					
		  					
		  					System.out.println("server class::"+username);
		  					//System.out.println("Accepted client..."+username);
		  					
		  					
		  					
		  					ClientController clients = new ClientController(socketForClient,signInfo[1],is,os,i);
		  					Thread newClient = new Thread(clients);
		  					
		  					
		  				    
		  					//System.out.println("thread id"+newClient.getId());
		  					
		  					ar.add(clients);
		  					threadVec.add(newClient);
		  					
		  					newClient.start();
		  					i++;
		  					
		  					//sending active user list to every client.........
		  					
		  					int x=0;
		  					for(ClientController ct: Server.ar)
		  					{ 
		  			
		  						
		  							for(String j: userList) {
		  							
		  								ct.os.writeUTF(" #!#"+j);
		  							
		  							}
		  						
		  					}
		  					
		  					//end.................................................
	  			      }
	  				
	  				else {
	  					os.writeUTF("username or password is incorrect #!# " );
	  					
	  				}
	  				
	  			   }
	  			//signin...end............................................................
	  			
	  					
	  			}
	  			
	  			
	  		}catch(IOException e1) {
	  			System.out.println("connection problem occured...");
	  		}
	  }
}


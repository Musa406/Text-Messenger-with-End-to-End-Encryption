package clientServerClient;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class Client 
{
   final static int ServerPort = 9999;
   static boolean flag = true;

   public static void main(String args[]) throws UnknownHostException, IOException{
	   		
	            
		   Socket clientSocket = new Socket("localhost",ServerPort); //creating client socket
		   
		   //input output stream
		   //BufferedReader inputFromKeyboard = new BufferedReader(new InputStreamReader(System.in));  
		   Scanner keyInput = new Scanner(System.in);
		  // BufferedReader is = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));	
		   DataInputStream is = new DataInputStream(clientSocket.getInputStream());
		   DataOutputStream os = new DataOutputStream(clientSocket.getOutputStream());
		  // PrintStream ps = new PrintStream(clientSocket.getOutputStream());
		  // PrintWriter pr = new PrintWriter(ps);
		   //input output stream
		   
	   Thread receive = new Thread(new Runnable () {
		  
		   
		   public void run() {
			   while(true) {
					try {
						  
						String receiveMsg =  is.readUTF();
						System.out.println(receiveMsg); 
						
					} catch (IOException e) {
						// TODO Auto-generated catch block
						System.out.println("Problem occured while receiving message...");
					}		
			   }
		   }});
	   
	   Thread sendMsg = new Thread(new Runnable() {
		 
		   @Override
			public void run() {
			// TODO Auto-generated method stub
				while(true){
						String input = keyInput.nextLine();
						try {
							os.writeUTF(input);
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
				
						if(input.equals("logout")) {
							flag = false;
							System.out.println("log out from client..");
							break;
						}
					
				}
			}
		   
	   });
	   
	   receive.start();
	   sendMsg.start();
	  
   }
    
}

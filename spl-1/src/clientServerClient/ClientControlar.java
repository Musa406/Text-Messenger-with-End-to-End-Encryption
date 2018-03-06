package clientServerClient;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.StringTokenizer;
import java.util.Vector;

//import secureChat.ClientControlar;
//import secureChat.Server;

public class ClientControlar implements Runnable{
	
	Socket socketForClient;
	static String clientName;
	static DataInputStream is;
	static DataOutputStream os;
	boolean isLogIn;
	
	Vector<String> userList = new Vector<String>(10);

	public ClientControlar(Socket socketForClient, String clientName, DataInputStream is, DataOutputStream os) {
		this.socketForClient=socketForClient;
		this.clientName=clientName;
		this.is=is;
		this.os=os;
		this.isLogIn = true;
	}

	@Override
	public void run() {
		String receive;
		
		while(true) {
			try {
				receive = is.readUTF();
				System.out.println("client controlar"+receive);		
				
				
				StringTokenizer sti = new StringTokenizer(receive, "#");  // break string two part... 1)Message 2)Receiver
				String str[] = receive.split("#");
				
				String msgToSend = sti.nextToken();
				String msgReceiver = sti.nextToken();
				
				userList.add(str[0]);
			
				
				for(ClientControlar ct: Server.ar) { 
					if(ct.clientName.equals(msgReceiver) && (ct.isLogIn==true)) {
						ct.os.writeUTF(msgToSend);
						break;
					}
				}
				
				
			
			}catch(IOException ex){
				System.out.println("problem occurrs in thread client");
			}
		}         
	       
	}

}

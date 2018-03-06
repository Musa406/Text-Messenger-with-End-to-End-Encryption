package clientServerClient;



import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.StringTokenizer;

public class ClientControlar implements Runnable{
	
	Socket socketForClient;
	private String clientName;
	final DataInputStream is;
	final DataOutputStream os;
	boolean isLogIn;

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
				System.out.println(receive);		
				//log out
				
				if(receive.equals("logout")) {
					this.isLogIn = false;
			
					System.out.println("log out successfully...");
					
					//kick out this client.....
						try {
							this.socketForClient.close();
						}catch(Exception e1){
							System.out.println("problem occured to close this particular client...");
						}
					break;
					
				}
				
				StringTokenizer sti = new StringTokenizer(receive, "#");  // break string two part... 1)Message 2)Receiver
				
				String msgToSend = sti.nextToken();
				String msgReceiver = sti.nextToken();
				
				//Searching receiver...
				for(ClientControlar ct: Server.ar) { 
					if(ct.clientName.equals(msgReceiver) && (ct.isLogIn==true)) {
						ct.os.writeUTF(clientName+": "+msgToSend+"# ");
						break;
					}
				}
			
			}catch(IOException ex){
				System.out.println("problem occurrs in thread client");
			}
		}
		
		//closing stream
		 try {
	            this.is.close();
	            this.os.close();
	             
	        }catch(IOException e){
	            System.out.println("problem occured to closing stream..");;
	        }
	}

}

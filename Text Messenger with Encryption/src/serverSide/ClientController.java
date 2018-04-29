package serverSide;



import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.StringTokenizer;

public class ClientController implements Runnable{
	
	Socket socketForClient;
	private String clientName;
	final DataInputStream is;
	final DataOutputStream os;
	boolean isLogIn;

	public ClientController(Socket socketForClient, String clientName, DataInputStream is, DataOutputStream os) {
		this.socketForClient=socketForClient;
		this.clientName=clientName;
		this.is=is;
		this.os=os;
		this.isLogIn = true;
	}

	@Override
	public void run() {
		String receiveAll;
		
		while(true) {
			try {
				receiveAll = is.readUTF();
				System.out.println(receiveAll);		
				
				//Logout............................................................................
				if(receiveAll.equals("logout")) {
					this.isLogIn = false;
					Server sr = new Server();
					int x=sr.i-1;
					System.out.println(" .. "+x);
					
					
					
					sr.ar.remove(x);
					sr.userList.remove(x);
					
                  
				    sr.i=x;
			
					System.out.println("log out successfully...");				
					        break;			
				}
				//..............................................................................
				
				//Sending messege to targeted user..............................................
				else {
				
						StringTokenizer sti = new StringTokenizer(receiveAll, "#!#");  // break string two part... 1)Message 2)Receiver
						
						String msgToSend = sti.nextToken();
						String msgReceiver = sti.nextToken();
						
						//Searching receiver...
						for(ClientController ct: Server.ar) { 
							if(ct.clientName.equals(msgReceiver) && (ct.isLogIn==true)) {
								String str[] = clientName.split("               ");
								ct.os.writeUTF(str[0]+"::"+msgToSend+"#!# ");
								break;
							}
						}
			
				}
				//...................................................................................
				
			}catch(IOException ex){
				System.out.println("problem occurrs in thread client");
			}
		}
		
		//closing stream..............................................................
		try {
			os.close();
			is.close();
			this.socketForClient.close();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//closing stream..............................................................
		
	}

}

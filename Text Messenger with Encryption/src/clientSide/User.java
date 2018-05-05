package clientSide;


import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextArea;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.net.Socket;
import java.util.ArrayList;
import java.util.StringTokenizer;
import java.util.Vector;
import java.util.concurrent.SynchronousQueue;
import java.awt.event.ActionEvent;
import javax.swing.JToggleButton;
import javax.swing.ListSelectionModel;
import javax.swing.JScrollBar;
import javax.swing.JList;




public class User {

	private JFrame frame;
	static Vector<String>userlist = new Vector();
	
	
	Thread receive;
	static Socket sock;
	static String username;
	//ArrayList<String>userList = new ArrayList();
	static Boolean isConnected = false;
	static DataInputStream reader ;
	static  DataOutputStream writer;
	static boolean flag=false;
	
	SignUpClass signup;
	
    RSAencryption rsa;
	

	//Graphical part..............................................................................
	public User(Socket sock,DataInputStream reader,DataOutputStream writer,RSAencryption rsa) {
		
		this.sock = sock;
		this.reader = reader;
		this.writer = writer;
		this.rsa = rsa;
		

		frame = new JFrame();
		frame.getContentPane().setBackground(new Color(95, 158, 160));
		frame.setBounds(100, 100, 640, 497);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		frame.getContentPane().setLayout(null);
		
		JTextArea textArea = new JTextArea();
		textArea.setBounds(26, 79, 289, 176);
		textArea.setBackground(new Color(192, 192, 192));
		frame.getContentPane().add(textArea);
		
		JLabel lblNewLabel = new JLabel("Message");
		lblNewLabel.setBounds(333, 161, 81, 55);
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 16));
		frame.getContentPane().add(lblNewLabel);
		
		JTextArea inputTextArea = new JTextArea();
		inputTextArea.setBounds(24, 373, 289, 55);
		inputTextArea.setBackground(Color.LIGHT_GRAY);
		frame.getContentPane().add(inputTextArea);
		
		
		
		JButton sendButton = new JButton("Send");
		sendButton.setBounds(331, 382, 108, 31);
		sendButton.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 18));
		sendButton.setBackground(new Color(176, 224, 230));
		frame.getContentPane().add(sendButton);
		
		JLabel lblActiveUser = new JLabel("Active user");
		lblActiveUser.setBounds(489, 51, 122, 31);
		lblActiveUser.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 16));
		frame.getContentPane().add(lblActiveUser);
		
		JTextArea cipherField = new JTextArea();
		cipherField.setBounds(24, 289, 289, 54);
		cipherField.setBackground(Color.LIGHT_GRAY);
		frame.getContentPane().add(cipherField);
		
		
		
		
		
		final DefaultListModel activeUserList = new DefaultListModel();
		
		
		JList list = new JList(activeUserList);
		list.setBounds(499, 94, 89, 284);
		list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
	    list.setSelectedIndex(0);
	    list.setVisibleRowCount(10);
		frame.getContentPane().add(list);
	
		//JScrollPane fruitListScrollPane = new JScrollPane(list);
		//.....................................................................................................................
		
		
		//log in//................................................................................................................................
		
		
		
		

		//Message receiving//............................................................
		 receive = new Thread(new Runnable () {
			
			
			   public void run() {
				   
				   while(true) {
					   
						try {
							boolean flagToCheckUser = true;
						
							String receiveMsg =  reader.readUTF();
							System.out.println("message received::"+receiveMsg);
							
							StringTokenizer sti = new StringTokenizer(receiveMsg,"#!#");  // break string two part... 1)Message 2)Receiver
							
							String message = sti.nextToken();
							String newActiveUser = sti.nextToken();
							
							
							
							
							//cipher to plain
							if(!message.equals(" ")) {
								textArea.setEditable(true);
								//textArea.setText(message);
								String nameMsg[] = message.split("::");
								
								if(nameMsg.length>1) {
									
									String plainMsg = rsa.decrypt(nameMsg[1]);
									textArea.setText(nameMsg[0]+" : "+plainMsg);
									
									
									BigInteger messageInt = new BigInteger(nameMsg[1]);
									String cipherTextReceive = new String(messageInt.toByteArray());
									
									cipherField.setText(cipherTextReceive);
								
									//System.out.println("cipher msg is "+nameMsg[1]);
								}
								
								else if(nameMsg.length<2){
									JOptionPane.showMessageDialog(frame, message);
									
									if(message.equals("username or password is incorrect ")){
										
										isConnected = false;
									}
								}
							}
							
							//
							
							
							//textArea.setEditable(true);
							
							//if(!message.equals(" "))
								//textArea.append(message+"\n");
							
							
							//Active user list//..............................................
							
							
							
							int x = userlist.size();
							
							if(!newActiveUser.equals(" ")){
								
								System.out.println("newActiveuser::"+newActiveUser);
								
								String checkUserRemove[] = newActiveUser.split("@@@@");
								System.out.println("remove found..."+checkUserRemove[0]);
								
								if(checkUserRemove[0].equals("remove")) {
								    System.out.println("if condition remove");
									
									for(int i=0; i<x; i++) {
										if(checkUserRemove[1].equals(userlist.get(i))) {
											userlist.remove(i);
											activeUserList.removeElementAt(i);
											break;
										}
									}
								}
								
								
							
								else {
										for(int i=0; i<x; i++) {									
											if(newActiveUser.equals(userlist.get(i))) {
												//System.out.println(i+userlist.get(i));	
												flagToCheckUser=false;
												break;			
											}
										}	
									
									
										if(flagToCheckUser==true) {   //true means this user isn't already existed
										//System.out.println(" not existed..");
											activeUserList.addElement(newActiveUser);
										
											userlist.add(newActiveUser);
										}	
								}
							}
							//.................................................................
							
							
						} catch (IOException e) {
							// TODO Auto-generated catch block
							System.out.println("Problem occured while receiving message...");
						}		
				   }
			   }});
			receive.start();
			
		      //Message receiving//..............................................................
		
		
		
		
		
				
		//Message sending//............................................................................................................................................
		 sendButton.addActionListener(new ActionListener() {
			 
				public void actionPerformed(ActionEvent arg0) {

					 String receipent = " ";
			            if (list.getSelectedIndex() != -1) {                     
			               receipent =  (String) list.getSelectedValue(); 
			        
			            }
			            		
					String msg = inputTextArea.getText();
					try {
						if(!receipent.equals(" "))
						{
							//encryption..................................................
								String str[] = receipent.split("               ");
								
								String cipherMsg = rsa.encrypt(msg,str[1],str[2]);
								
							//...........................................................
								
							 writer.writeUTF(cipherMsg+"#!#"+receipent);
						}
						
						
						else JOptionPane.showMessageDialog(frame, "Select Receiver, please");
						
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						System.out.println("problem occurred in sending");
					}
					
				}});
		
		 ///Message sending//..........................................................................................................................................
		
		//Logout.....................................................................................
		 JButton btnLogOut = new JButton("Log out");
		 btnLogOut.setBounds(499, 411, 89, 23);
			btnLogOut.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					try {
						
						
						
							writer.writeUTF("logout");
							receive.stop();
							reader.close();
							writer.close();
	
							frame.setVisible(false);
							JOptionPane.showMessageDialog(frame, "Logout succeessfully");
							System.exit(0);
						
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			});
			frame.getContentPane().add(btnLogOut);
			
			JLabel lblCipherText_1 = new JLabel("Cipher Text");
			lblCipherText_1.setBounds(333, 308, 106, 15);
			lblCipherText_1.setFont(new Font("Dialog", Font.BOLD, 16));
			frame.getContentPane().add(lblCipherText_1);
			
			JLabel lblSecuredMessenger = new JLabel("Secured Messenger");
			lblSecuredMessenger.setBounds(184, 39, 277, 28);
			lblSecuredMessenger.setFont(new Font("Dialog", Font.BOLD | Font.ITALIC, 24));
			frame.getContentPane().add(lblSecuredMessenger);
			
			
			
			
	
		//................................................................................................
		
	}
}
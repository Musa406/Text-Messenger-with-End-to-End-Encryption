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
import java.awt.event.ActionEvent;
import javax.swing.JToggleButton;
import javax.swing.ListSelectionModel;
import javax.swing.JScrollBar;
import javax.swing.JList;




public class User {

	private JFrame frame;
	private JTextField usernameField;
	private JPasswordField password;
	static Vector<String>userlist = new Vector();
	
	
	Thread receive;
	static Socket sock;
	static String username;
	//ArrayList<String>userList = new ArrayList();
	static Boolean isConnected = false;
	static DataInputStream reader ;
	static DataOutputStream writer;
	static boolean flag=false;
	private JTextField ipField;

	SignUpClass signup;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					User window = new User();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	//Graphical part..............................................................................
	public User() {
		
		RSAencryption rsa = new RSAencryption();

		frame = new JFrame();
		frame.getContentPane().setBackground(new Color(95, 158, 160));
		frame.setBounds(100, 100, 640, 497);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		frame.getContentPane().setLayout(null);
		
		usernameField = new JTextField();
		usernameField.setBounds(24, 24, 95, 25);
		frame.getContentPane().add(usernameField);
		usernameField.setColumns(10);
		
		JButton loginButton = new JButton("Log in");
		loginButton.setBounds(378, 26, 89, 23);
		
		loginButton.setFont(new Font("Tahoma", Font.PLAIN, 14));
		frame.getContentPane().add(loginButton);
		
		JLabel lblUsername = new JLabel("username");
		lblUsername.setBounds(34, 60, 71, 14);
		lblUsername.setFont(new Font("Tahoma", Font.PLAIN, 14));
		frame.getContentPane().add(lblUsername);
		
		JLabel lblPassword = new JLabel("password");
		lblPassword.setBounds(153, 61, 81, 14);
		lblPassword.setFont(new Font("Tahoma", Font.PLAIN, 14));
		frame.getContentPane().add(lblPassword);
		
		password = new JPasswordField();
		password.setBounds(139, 24, 95, 25);
		frame.getContentPane().add(password);
		
		JTextArea textArea = new JTextArea();
		textArea.setBounds(24, 119, 289, 136);
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
		sendButton.setBackground(new Color(32, 178, 170));
		frame.getContentPane().add(sendButton);
		
		JLabel lblActiveUser = new JLabel("Active user");
		lblActiveUser.setBounds(490, 75, 122, 31);
		lblActiveUser.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 16));
		frame.getContentPane().add(lblActiveUser);
		
		JTextArea cipherField = new JTextArea();
		cipherField.setBounds(24, 289, 289, 54);
		cipherField.setBackground(Color.LIGHT_GRAY);
		frame.getContentPane().add(cipherField);
		
		//JLabel lblCipherText = new JLabel("Cipher Text");
		JButton lblCipherText = new JButton("Create new Account");
		lblCipherText.setBounds(364, 429, 234, 31);
		lblCipherText.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 14));
		frame.getContentPane().add(lblCipherText);
		
		
		
		//...
		lblCipherText.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
			
				frame.setVisible(false);
				signup = new SignUpClass();
				
			}
		});
		
		//.......................
		
		//.......................
		
		
		ipField = new JTextField();
		ipField.setBounds(248, 25, 108, 23);
		frame.getContentPane().add(ipField);
		ipField.setColumns(10);
		
		JLabel lblServerIp = new JLabel("server ip");
		lblServerIp.setBounds(258, 59, 71, 20);
		frame.getContentPane().add(lblServerIp);
		
		
		
		
		
		final DefaultListModel activeUserList = new DefaultListModel();
		
		
		JList list = new JList(activeUserList);
		list.setBounds(501, 118, 89, 284);
		list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
	    list.setSelectedIndex(0);
	    list.setVisibleRowCount(10);
		frame.getContentPane().add(list);
	
		//JScrollPane fruitListScrollPane = new JScrollPane(list);
		//.....................................................................................................................
		
		
		//log in//................................................................................................................................
		
		loginButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

					flag=true;
					if(isConnected==false) {
						
						try {
							
							
						isConnected=true;
						username = usernameField.getText();
						usernameField.setEditable(false);
						
						
							String serverIp = ipField.getText();
							sock = new Socket(serverIp,9999);
							reader = new DataInputStream (sock.getInputStream());
							writer = new DataOutputStream(sock.getOutputStream());
							
							String myKey = rsa.getPublicKey();
							String pass = password.getText();
							writer.writeUTF(pass+"@!@"+username+"               "+myKey);
							
						} catch (IOException e) {
							e.printStackTrace();
							usernameField.setEditable(true);
						}
						
					}
					
					else if(isConnected==true) {
						System.out.println("Already exist this client...");
					}
					
					
					
					
					
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
													usernameField.setEditable(true);
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
											
											//String str[] = newActiveUser.split("          ");
											//System.out.println("testing"+str[1]);
											
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
										//.................................................................
										
										
									} catch (IOException e) {
										// TODO Auto-generated catch block
										System.out.println("Problem occured while receiving message...");
									}		
							   }
						   }});
						receive.start();
						
					      //Message receiving//..............................................................
					
					
			}
		});
		
		//log in//...................................................................................................................................................
		
				
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
		 btnLogOut.setBounds(501, 25, 89, 23);
			btnLogOut.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					try {
						
						if(isConnected==true) {
						
							writer.writeUTF("logout");
							receive.stop();
							reader.close();
							writer.close();
	
							frame.setVisible(false);
							JOptionPane.showMessageDialog(frame, "Logout succeessfully");
							System.exit(0);
						}
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
			
			
			
			
	
		//................................................................................................
		
	}
}
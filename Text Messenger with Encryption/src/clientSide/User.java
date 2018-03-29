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

		frame = new JFrame();
		frame.getContentPane().setBackground(new Color(95, 158, 160));
		frame.setBounds(100, 100, 640, 497);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		usernameField = new JTextField();
		usernameField.setBounds(24, 24, 95, 25);
		frame.getContentPane().add(usernameField);
		usernameField.setColumns(10);
		
		JButton loginButton = new JButton("Log in");
		
		loginButton.setFont(new Font("Tahoma", Font.PLAIN, 14));
		loginButton.setBounds(378, 26, 89, 23);
		frame.getContentPane().add(loginButton);
		
		JLabel lblUsername = new JLabel("username");
		lblUsername.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblUsername.setBounds(34, 60, 71, 14);
		frame.getContentPane().add(lblUsername);
		
		JLabel lblPassword = new JLabel("password");
		lblPassword.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblPassword.setBounds(153, 61, 81, 14);
		frame.getContentPane().add(lblPassword);
		
		password = new JPasswordField();
		password.setBounds(139, 24, 95, 25);
		frame.getContentPane().add(password);
		
		JTextArea textArea = new JTextArea();
		textArea.setBackground(new Color(192, 192, 192));
		textArea.setBounds(24, 119, 289, 136);
		frame.getContentPane().add(textArea);
		
		JLabel lblNewLabel = new JLabel("Message");
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 16));
		lblNewLabel.setBounds(333, 161, 81, 55);
		frame.getContentPane().add(lblNewLabel);
		
		JTextArea inputTextArea = new JTextArea();
		inputTextArea.setBackground(Color.LIGHT_GRAY);
		inputTextArea.setBounds(24, 373, 289, 55);
		frame.getContentPane().add(inputTextArea);
		
		JButton sendButton = new JButton("Send");
		sendButton.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 18));
		sendButton.setBackground(new Color(32, 178, 170));
		sendButton.setBounds(331, 382, 108, 31);
		frame.getContentPane().add(sendButton);
		
		JLabel lblActiveUser = new JLabel("Active user");
		lblActiveUser.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 16));
		lblActiveUser.setBounds(468, 72, 122, 31);
		frame.getContentPane().add(lblActiveUser);
		
		JTextArea textArea_1 = new JTextArea();
		textArea_1.setBackground(Color.LIGHT_GRAY);
		textArea_1.setBounds(24, 289, 289, 54);
		frame.getContentPane().add(textArea_1);
		
		JLabel lblCipherText = new JLabel("Cipher Text");
		lblCipherText.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 14));
		lblCipherText.setBounds(337, 304, 102, 39);
		frame.getContentPane().add(lblCipherText);
		
		ipField = new JTextField();
		ipField.setBounds(248, 25, 108, 23);
		frame.getContentPane().add(ipField);
		ipField.setColumns(10);
		
		JLabel lblServerIp = new JLabel("server ip");
		lblServerIp.setBounds(258, 59, 71, 20);
		frame.getContentPane().add(lblServerIp);
		
		
		
		
		
		final DefaultListModel userNames = new DefaultListModel();
		
		
		JList list = new JList(userNames);
		list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
	    list.setSelectedIndex(0);
	    list.setVisibleRowCount(10);
		list.setBounds(468, 123, 118, 284);
		frame.getContentPane().add(list);
	
		//JScrollPane fruitListScrollPane = new JScrollPane(list);
		//.....................................................................................................................
		
		
		//log in//................................................................................................................................
		
		loginButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

					flag=true;
					if(isConnected==false) {
						
						try {
							
							System.out.println("flag checking:::"+flag);
						isConnected=true;
						username = usernameField.getText();
						usernameField.setEditable(false);
						
							String serverIp = ipField.getText();
							sock = new Socket(serverIp,9999);
							reader = new DataInputStream (sock.getInputStream());
							writer = new DataOutputStream(sock.getOutputStream());
							
							writer.writeUTF(username);
							
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
										
										StringTokenizer sti = new StringTokenizer(receiveMsg,"#");  // break string two part... 1)Message 2)Receiver
										
										String message = sti.nextToken();
										String activelist = sti.nextToken();

										
										textArea.setEditable(true);
										textArea.append(message+"\n");
										
										
										//Active user list//..............................................
										
										
										
										int x = userlist.size();
										
										for(int i=0;i<x; i++) {									
											if(activelist.equals(userlist.get(i))) {
												System.out.println(i+userlist.get(i));	
												flagToCheckUser=false;
												break;			
											}
										}	
										
										
										if(flagToCheckUser==true) {
											System.out.println(" not existed..");
											userNames.addElement(activelist);
											userlist.add(activelist);
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

					 String data = " ";
			            if (list.getSelectedIndex() != -1) {                     
			               data =  (String) list.getSelectedValue(); 
			        
			            }
			            		
					String input = inputTextArea.getText();
					try {
						if(!data.equals(" "))
						 writer.writeUTF(input+"#"+data);
						
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
			btnLogOut.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					try {
						
						writer.writeUTF("logout");
						reader.close();
						writer.close();
						receive.stop();
						frame.setVisible(false);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			});
			
			btnLogOut.setBounds(501, 25, 89, 23);
			frame.getContentPane().add(btnLogOut);
			
			
			
			
	
		//................................................................................................
		
	}
}
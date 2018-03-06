package clientServerClient;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JLabel;
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
import java.awt.event.ActionEvent;

public class User {

	private JFrame frame;
	private JTextField usernameField;
	private JPasswordField password;
	
	
	static Socket sock;
	static String username;
	ArrayList<String>userList = new ArrayList();
	static Boolean isConnected = false;
	static DataInputStream reader ;
	static DataOutputStream writer;
	static boolean flag=false;

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

	/**
	 * Create the application.
	 */
	public User() {
		frame = new JFrame();
		frame.getContentPane().setBackground(new Color(95, 158, 160));
		frame.setBounds(100, 100, 564, 496);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		usernameField = new JTextField();
		usernameField.setBounds(24, 24, 95, 25);
		frame.getContentPane().add(usernameField);
		usernameField.setColumns(10);
		
		JButton loginButton = new JButton("Log in");
		
		loginButton.setFont(new Font("Tahoma", Font.PLAIN, 14));
		loginButton.setBounds(255, 25, 89, 23);
		frame.getContentPane().add(loginButton);
		
		JLabel lblUsername = new JLabel("username");
		lblUsername.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblUsername.setBounds(34, 60, 71, 14);
		frame.getContentPane().add(lblUsername);
		
		JLabel lblPassword = new JLabel("password");
		lblPassword.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblPassword.setBounds(170, 61, 64, 14);
		frame.getContentPane().add(lblPassword);
		
		password = new JPasswordField();
		password.setBounds(143, 24, 95, 25);
		frame.getContentPane().add(password);
		
		JTextArea textArea = new JTextArea();
		textArea.setBackground(new Color(192, 192, 192));
		textArea.setBounds(24, 119, 241, 230);
		frame.getContentPane().add(textArea);
		
		JLabel lblNewLabel = new JLabel("Message");
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 16));
		lblNewLabel.setBounds(275, 219, 81, 55);
		frame.getContentPane().add(lblNewLabel);
		
		JTextArea inputTextArea = new JTextArea();
		inputTextArea.setBounds(24, 373, 241, 50);
		frame.getContentPane().add(inputTextArea);
		
		JButton sendButton = new JButton("Send");
		sendButton.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 18));
		sendButton.setBackground(new Color(32, 178, 170));
		sendButton.setBounds(275, 382, 81, 31);
		frame.getContentPane().add(sendButton);
		
		JLabel lblActiveUser = new JLabel("Active user");
		lblActiveUser.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 16));
		lblActiveUser.setBounds(415, 77, 106, 31);
		frame.getContentPane().add(lblActiveUser);
		
		JTextArea activeTextArea = new JTextArea();
		activeTextArea.setBackground(new Color(192, 192, 192));
		activeTextArea.setBounds(392, 119, 129, 294);
		frame.getContentPane().add(activeTextArea);
		
		
		
		//log in//................................................................................................................................
		loginButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				//public void actionPerformed(ActionEvent arg0) {
					System.out.println("connection ok...");
					flag=true;
					if(isConnected==false) {
						
						try {
							System.out.println("connection ok.2...");
							System.out.println("flag checking:::"+flag);
						isConnected=true;
						username = usernameField.getText();
						usernameField.setEditable(false);
						
						
							sock = new Socket("localhost", 9999);
							reader = new DataInputStream (sock.getInputStream());
							writer = new DataOutputStream(sock.getOutputStream());
							
							writer.writeUTF(username);
							
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
							//chatTextArea.append("can't connect ");
							usernameField.setEditable(true);
						}
						
					}
					
					else if(isConnected==true) {
						System.out.println("Already exist this client...");
					}
				
			}
		});
		
		//log in//...................................................................................................................................................
		
		
		
		
		
		//Message sending//............................................................................................................................................
		 sendButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					String input = inputTextArea.getText();
					try {
						writer.writeUTF(input);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						System.out.println("problem occurred in sending");
					}
					
				}});
		
		 ///Message sending//..........................................................................................................................................
		
		
		
		
		//Message receiving//............................................................
		Thread receive = new Thread(new Runnable () {
			  		   
			   public void run() {
				   while(true) {
						try {
							System.out.println("receiving");
							  
							String receiveMsg =  reader.readUTF();
							textArea.setEditable(true);
							textArea.setText(receiveMsg);
							System.out.println("receive thread"+receiveMsg); 
							
						} catch (IOException e) {
							// TODO Auto-generated catch block
							System.out.println("Problem occured while receiving message...");
						}		
				   }
			   }});
		
		      //Message receiving//..............................................................
		
		
		
		//checking login//........................................................................
		 Thread checkingConnection = new Thread(new Runnable() {
				@Override
				public void run() {
					while(true) {
						//System.out.println("befor");
						if(flag==true) {
							
							//sendThread.start();
							receive.start();
							break;						
						
						}
								
					}
				}
				  
			  });
		 
			  
			  checkingConnection.start();
		
			//checking login//........................................................................
			
		
	}

}

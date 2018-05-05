package clientSide;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPasswordField;

import java.awt.Font;
import javax.swing.UIManager;
import java.awt.Color;
import java.awt.event.ActionListener;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Vector;
import java.awt.event.ActionEvent;

public class UserMainClass {

	private JFrame frame;
	private JTextField userNameField;
	private JTextField passwordField;
	private JTextField serverIpField;
	

	//ArrayList<String>userList = new ArrayList();
	static Boolean isConnected = false;

	static boolean flag=false;

	
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					UserMainClass window = new UserMainClass();
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
	public UserMainClass() {
		
		RSAencryption rsa2 = new RSAencryption();
		
		
		frame = new JFrame();
		frame.getContentPane().setBackground(new Color(0, 191, 255));
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		userNameField = new JTextField();
		userNameField.setBounds(28, 90, 114, 19);
		frame.getContentPane().add(userNameField);
		userNameField.setColumns(10);
		
		passwordField = new JTextField();
		passwordField.setBounds(165, 90, 114, 19);
		frame.getContentPane().add(passwordField);
		passwordField.setColumns(10);
		
		
		JButton btnCreateANew = new JButton("Create a new Account");
		btnCreateANew.setBounds(94, 227, 224, 25);
		frame.getContentPane().add(btnCreateANew);
		
		JLabel lblUsername = new JLabel("username");
		lblUsername.setBounds(28, 121, 114, 15);
		frame.getContentPane().add(lblUsername);
		
		JLabel lblPassword = new JLabel("password");
		lblPassword.setBounds(188, 121, 70, 15);
		frame.getContentPane().add(lblPassword);
		
		serverIpField = new JTextField();
		serverIpField.setBounds(312, 90, 114, 19);
		frame.getContentPane().add(serverIpField);
		serverIpField.setColumns(10);
		
		JLabel lblServerIp = new JLabel("server ip");
		lblServerIp.setBounds(322, 121, 96, 15);
		frame.getContentPane().add(lblServerIp);
		
		JLabel lblSecuredMessenger = new JLabel("Secured Messenger");
		lblSecuredMessenger.setBounds(94, 23, 291, 25);
		lblSecuredMessenger.setFont(new Font("Dialog", Font.BOLD | Font.ITALIC, 24));
		frame.getContentPane().add(lblSecuredMessenger);
		
		
		

		//login.....................................................................................
		JButton btnNewButton = new JButton("Login");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				flag=true;
				if(isConnected==false) {
					
					try {
						
						
					isConnected=true;
					String username =userNameField.getText();
					userNameField.setEditable(false);
					
					
						String serverIp = serverIpField.getText();
					   Socket sock = new Socket(serverIp,9999);
					   DataInputStream reader = new DataInputStream (sock.getInputStream());
					   DataOutputStream writer = new DataOutputStream(sock.getOutputStream());
						
						String myKey = rsa2.getPublicKey();
						String pass = passwordField.getText();
						
					
						writer.writeUTF(pass+"@!@"+username+"               "+myKey);
						
						System.out.println(pass+"@!@"+username+"               "+myKey);
						
						
						frame.setVisible(false);
						User userMsg = new User(sock,reader,writer,rsa2);
						
						
					} catch (IOException e2) {
						e2.printStackTrace();
						userNameField.setEditable(true);
					}
					
				}
				
				else if(isConnected==true) {
					System.out.println("Already exist this client...");
				}
			}
		});
		btnNewButton.setBounds(151, 170, 117, 25);
		frame.getContentPane().add(btnNewButton);
		//login....................................................................................
		
	}

	
}

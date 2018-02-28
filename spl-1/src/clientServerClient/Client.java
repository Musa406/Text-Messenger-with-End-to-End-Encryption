package clientServerClient;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class Client extends JFrame{
	static Socket sock;
	static String username;
	ArrayList<String>userList = new ArrayList();
	static Boolean isConnected = false;
	static DataInputStream reader ;
	static DataOutputStream writer;
	static boolean flag=false;

	public static void main(String[] args) {
		
		
		//String username;
		
		JFrame frame = new JFrame();
		
		frame = new JFrame();
		frame.getContentPane().setFont(new Font("Verdana", Font.PLAIN, 24));
		frame.setBounds(100, 100, 587, 429);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		frame.setVisible(true);
		
		JLabel lblNewLabel = new JLabel("Username");
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 11));
		lblNewLabel.setBounds(10, 36, 68, 22);
		frame.getContentPane().add(lblNewLabel);
		
		JTextField usernameField = new JTextField();
		usernameField.setBounds(74, 38, 86, 20);
		frame.getContentPane().add(usernameField);
		usernameField.setColumns(10);
		
		JButton connectButton = new JButton("Connect");
		connectButton.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 11));
		
		connectButton.setBounds(169, 37, 89, 23);
		frame.getContentPane().add(connectButton);
		
		JButton disconnectButton = new JButton("Disconnect");
		disconnectButton.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 11));
		
		disconnectButton.setBounds(268, 37, 101, 23);
		frame.getContentPane().add(disconnectButton);
		
		JLabel lblActiveUser = new JLabel("active user");
		lblActiveUser.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 11));
		lblActiveUser.setBounds(444, 41, 86, 19);
		frame.getContentPane().add(lblActiveUser);
		
		JTextArea chatTextArea = new JTextArea();
		chatTextArea.setBackground(Color.LIGHT_GRAY);
		chatTextArea.setBounds(23, 81, 258, 208);
		frame.getContentPane().add(chatTextArea);
		
		JTextArea onlineUsersArea = new JTextArea();
		onlineUsersArea.setBackground(Color.LIGHT_GRAY);
		onlineUsersArea.setLineWrap(true);
		onlineUsersArea.setColumns(1);
		onlineUsersArea.setBounds(427, 82, 122, 227);
		frame.getContentPane().add(onlineUsersArea);
		
		JButton sendButton = new JButton("send");
		sendButton.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 11));
		sendButton.setBounds(298, 327, 89, 35);
		frame.getContentPane().add(sendButton);
		
		JTextArea textInputArea = new JTextArea();
		textInputArea.setBackground(Color.LIGHT_GRAY);
		textInputArea.setBounds(23, 313, 258, 55);
		frame.getContentPane().add(textInputArea);
		
		JLabel lblMessage = new JLabel("Message");
		lblMessage.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 22));
		lblMessage.setBounds(291, 181, 96, 55);
		frame.getContentPane().add(lblMessage);
		
		
		
		
		//connecting
		
		
		connectButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
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
						
						//writer.println(username);//for chatlist
						//.flush();
						//reader.close();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						chatTextArea.append("can't connect ");
						usernameField.setEditable(true);
					}
					
				}
				
				else if(isConnected==true) {
					System.out.println("Already exist this client...");
				}
			}
		});
		
		
		//disconnecting
			
		 Thread receive = new Thread(new Runnable () {
			  
			   
			   public void run() {
				   while(true) {
						try {
							//System.out.println("receiving");
							  
							String receiveMsg =  reader.readUTF();
							chatTextArea.setText(receiveMsg);
							System.out.println(receiveMsg); 
							
						} catch (IOException e) {
							// TODO Auto-generated catch block
							System.out.println("Problem occured while receiving message...");
						}		
				   }
			   }});
		
		
		
		  
		 sendButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					String input = textInputArea.getText();
					try {
						writer.writeUTF(input);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						System.out.println("problem occurred in sending");
					}
					
				}});
		
		  
		  
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
					try {
						Thread.sleep(100);
					} catch (InterruptedException e) {
					// TODO Auto-generated catch block
						e.printStackTrace();
					}			
				}
			}
			  
		  });
		  
		  checkingConnection.start();
			

	}

}

package clientSide;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JTextField;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

import javax.swing.JPasswordField;
import javax.swing.JComboBox;
import javax.swing.ButtonGroup;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JRadioButton;
import javax.swing.JButton;
import java.awt.Color;

public class SignUpClass {
	
	public static int counter=0;
	private JFrame frame;
	private JTextField usernameField;
	private JTextField emailField;
	private JPasswordField passwordField;
	File file = new File("information.txt");
	private JPasswordField passwordField2;
	
	boolean isGenderSelect = false;
	
	static DataInputStream reader ;
	static DataOutputStream writer;
	
    //writeXMLfile xmlFile = new writeXMLfile();
	/**
	 * Launch the application.
	 */
	
	
	
	
	
	public SignUpClass(){

	/**
	 * Create the application.
	 */
	
	/**
	 * Initialize the contents of the frame.
	 */
	
		frame = new JFrame();
		frame.getContentPane().setBackground(new Color(72, 209, 204));
		frame.setBounds(100, 100, 567, 487);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		frame.setVisible(true);
		
		JLabel lblSignIn = new JLabel("Create a new account");
		lblSignIn.setBounds(129, 47, 260, 26);
		lblSignIn.setFont(new Font("Tahoma", Font.PLAIN, 24));
		frame.getContentPane().add(lblSignIn);
		
		JLabel lblUsername = new JLabel("Username");
		lblUsername.setBounds(88, 109, 88, 20);
		frame.getContentPane().add(lblUsername);
		
		usernameField = new JTextField();
		usernameField.setBounds(209, 106, 166, 26);
		frame.getContentPane().add(usernameField);
		usernameField.setColumns(10);
		
		JLabel lblEmail = new JLabel("E-mail");
		lblEmail.setBounds(88, 155, 66, 20);
		frame.getContentPane().add(lblEmail);
		
		emailField = new JTextField();
		emailField.setBounds(209, 143, 166, 26);
		frame.getContentPane().add(emailField);
		emailField.setColumns(10);
		
		JLabel lblPassword = new JLabel("Password");
		lblPassword.setBounds(88, 186, 88, 14);
		frame.getContentPane().add(lblPassword);
		
		passwordField = new JPasswordField();
		passwordField.setBounds(209, 180, 166, 26);
		frame.getContentPane().add(passwordField);
		
		JLabel lblBirthday = new JLabel("Birthday");
		lblBirthday.setBounds(90, 264, 86, 26);
		lblBirthday.setFont(new Font("Tahoma", Font.PLAIN, 20));
		frame.getContentPane().add(lblBirthday);
		
		JComboBox comboBox = new JComboBox();
		comboBox.setBounds(207, 271, 54, 19);
		comboBox.setModel(new DefaultComboBoxModel(new String[] {"Day","1","2","3","4,","5","6","7","8","9","10","11","12","13","14,","15","16","17","18","19","20"}));
		frame.getContentPane().add(comboBox);
		
		JComboBox comboBox_1 = new JComboBox();
		comboBox_1.setBounds(271, 271, 66, 19);
		comboBox_1.setModel(new DefaultComboBoxModel(new String[] {"Month","1","2","3","4,","5","6","7","8","9","10","11","12"}));
		frame.getContentPane().add(comboBox_1);
		
		JComboBox comboBox_2 = new JComboBox();
		comboBox_2.setBounds(347, 271, 55, 20);
		comboBox_2.setModel(new DefaultComboBoxModel(new String[] {"Year","1996","1996","1997","1998","1999","2000"}));
		frame.getContentPane().add(comboBox_2);
		
		JRadioButton rdbtnMale = new JRadioButton("male");
		rdbtnMale.setBounds(206, 313, 55, 20);
		frame.getContentPane().add(rdbtnMale);
		
		JRadioButton rdbtnFemale = new JRadioButton("female");
		rdbtnFemale.setBounds(297, 313, 66, 21);
		frame.getContentPane().add(rdbtnFemale);
		
		ButtonGroup myButtonGroup = new ButtonGroup();
        myButtonGroup.add(rdbtnMale);
        myButtonGroup.add(rdbtnFemale);
        
       // if(myButtonGroup.equals(rdbtnMale))
		
		JButton signUp2 = new JButton("Sign Up");
		signUp2.setBackground(new Color(0, 191, 255));
		signUp2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				counter++;
				String gendar;
				 if(myButtonGroup.equals(rdbtnMale)) {
					 gendar = "male";
				 }
				 else gendar = "female";
				
				String username=usernameField.getText();
				String email=emailField.getText();
				String password=passwordField.getText();
				String password2=passwordField2.getText();
				
				String day = (String) comboBox.getSelectedItem();
				String month = (String) comboBox_1.getSelectedItem();
				String year = (String) comboBox_2.getSelectedItem();
				//String gendar = myButtonGroup.
				
				
				int user_len= username.length();
				int email_len = email.length();
				int pass_len = password.length();
				
				
				
				

			
				
				//String user = "^[a-zA-Z0-9_]{5,14}$";
				//String mail = "^[a-z0-9._]+@[a-z.]+\\.[a-z]{2,}$";
				//String pass = "^[a-zA-Z0-9_]{8,14}$";
				
				if(user_len>0 && email_len>0 && pass_len>0 && day!="Day" && month!="Month" && year!="Year" && (gendar.equals("male") || gendar.equals("female"))) {
				
					//Pattern p = Pattern.compile(user);
					//Pattern p2 = Pattern.compile(mail);
					//Pattern p3 = Pattern.compile(pass);
					
					//Matcher m1 = p.matcher(username);
					//Matcher m2 = p.matcher(email);
					//Matcher m3 = p.matcher(password);
					
					
					//System.out.println("username check "+m1.find());
					//System.out.println("mail check "+m2.find());
					//System.out.println("pass check "+m3.find());
					
					//int []arr = new int[3];
					
					//if(m1.find() && m2.find() && m3.find()) {
				
					
								//2222222222222222222222222222222222222222222222222
								if(password.equals(password2)) {
									JOptionPane.showMessageDialog(frame,"Successfully SignUp");
									
									try {
										//xmlFile.writeXML(username,email,password,gendar, day+"/"+month+"/"+year);
										Socket socket = new Socket("localhost", 9999);
										writer = new DataOutputStream(socket.getOutputStream());
										
										writer.writeUTF("$%"+"#!#"+username+"#!#"+email+"#!#"+password+"#!#"+gendar+"#!#"+day+"/"+month+"/"+year);
										
									} catch (Exception e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									} 
									
								}
				
								else JOptionPane.showMessageDialog(frame,"Password missmatched...");
								//222222222222222222222222222222222222222222222222222
								
					//}
					
					//else JOptionPane.showMessageDialog(frame, "username or email or password is't fill up in a proper way..");
			
				}
				
				else JOptionPane.showMessageDialog(frame, "Please fill up all the field.");
			}
		});
		signUp2.setBounds(198, 373, 118, 35);
		signUp2.setFont(new Font("Tahoma", Font.PLAIN, 18));
		frame.getContentPane().add(signUp2);
		
		JButton signIn2 = new JButton("Sign in");
		signIn2.setBackground(new Color(0, 139, 139));
		signIn2.setBounds(435, 53, 89, 23);
		frame.getContentPane().add(signIn2);
		
		JLabel lblGender = new JLabel("Gender");
		lblGender.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblGender.setBounds(100, 311, 92, 17);
		frame.getContentPane().add(lblGender);
		
		JLabel lblRetypePassword = new JLabel("Re-Type password");
		lblRetypePassword.setBounds(65, 226, 131, 20);
		frame.getContentPane().add(lblRetypePassword);
		
		passwordField2 = new JPasswordField();
		passwordField2.setBounds(209, 217, 166, 29);
		frame.getContentPane().add(passwordField2);
		
		JButton btnShowAll = new JButton("show all");
		
		btnShowAll.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
							
				
				
				
			}
		});
		btnShowAll.setBounds(396, 382, 89, 23);
		frame.getContentPane().add(btnShowAll);
		
		
		signIn2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				frame.setVisible(false);
				User signUp = new User();
				//signUp.createAccount();
				
			}
		});
		
	}
	
}

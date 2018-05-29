package clientSide;

import java.awt.Color;
import java.awt.Container;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;







public class UserMainClass
{
  Thread receiveMsg;
  User userMsg;
  private JFrame frame;
  private JTextField userNameField;
  private JTextField serverIpField;
  static Boolean isConnected = Boolean.valueOf(false);
  
  static boolean flag = false;
  

  private JPasswordField passwordField;
  


  public static void main(String[] args)
  {
    EventQueue.invokeLater(new Runnable() {
      public void run() {
        try {
          UserMainClass window = new UserMainClass();
          //frame.setVisible(true);
        } catch (Exception e) {
          e.printStackTrace();
        }
      }
    });
  }
  



  public UserMainClass()
  {
    final RSAencryption rsa2 = new RSAencryption();
    

    frame = new JFrame();
    frame.getContentPane().setBackground(new Color(0, 191, 255));
    frame.setBounds(100, 100, 450, 300);
    frame.setDefaultCloseOperation(3);
    frame.setVisible(true);
    frame.getContentPane().setLayout(null);
    
    userNameField = new JTextField();
    userNameField.setBounds(28, 90, 114, 19);
    frame.getContentPane().add(userNameField);
    userNameField.setColumns(10);
    

    JButton btnCreateANew = new JButton("Create a new Account");
    btnCreateANew.setBounds(94, 227, 224, 25);
    btnCreateANew.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        frame.setVisible(false);
        SignUpClass signUp = new SignUpClass();
      }
    });
    frame.getContentPane().add(btnCreateANew);
    
    JLabel lblUsername = new JLabel("username");
    lblUsername.setBounds(38, 121, 114, 15);
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
    lblSecuredMessenger.setFont(new Font("Dialog", 3, 24));
    frame.getContentPane().add(lblSecuredMessenger);
    




    JButton btnNewButton = new JButton("Login");
    btnNewButton.setBounds(151, 170, 117, 25);
    btnNewButton.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        UserMainClass.flag = true;
        if (!UserMainClass.isConnected.booleanValue())
        {
          try
          {

            UserMainClass.isConnected = Boolean.valueOf(true);
            String username = userNameField.getText();
            


            String serverIp = serverIpField.getText();
            final Socket sock = new Socket(serverIp, 9999);
            final DataInputStream reader = new DataInputStream(sock.getInputStream());
            final DataOutputStream writer = new DataOutputStream(sock.getOutputStream());
            
            String myKey = rsa2.getPublicKey();
            String pass = passwordField.getText();
            





            writer.writeUTF(pass + "@!@" + username + "               " + myKey);
            

            receiveMsg = new Thread(new Runnable()
            {
              public void run()
              {
                try {
                  for (;;) {
                    String loginCheck = reader.readUTF();
                    
                    if (!loginCheck.equals("Login Successful!!"))
                      break;
                    frame.setVisible(false);
                    userMsg = new User(sock, reader, writer, rsa2);
                    

                    receiveMsg.stop();
                  }
                  


                  UserMainClass.isConnected = Boolean.valueOf(false);
                  JOptionPane.showMessageDialog(null, "username or password is incorrect..");


                }
                catch (IOException e)
                {

                  JOptionPane.showMessageDialog(null, "problem occured in Login");
                  System.exit(0);
                }
                
              }
              

            });
            receiveMsg.start();


          }
          catch (IOException e2)
          {

            e2.printStackTrace();
            System.exit(0);

          }
          
        }
        else if (UserMainClass.isConnected.booleanValue()) {
          System.out.println("Already exist this client...");
        }
      }
    });
    frame.getContentPane().add(btnNewButton);
    
    passwordField = new JPasswordField();
    passwordField.setBounds(171, 90, 96, 19);
    frame.getContentPane().add(passwordField);
  }
}

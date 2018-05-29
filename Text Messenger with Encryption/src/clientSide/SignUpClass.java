package clientSide;

import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.net.Socket;
import javax.swing.ButtonGroup;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JRadioButton;
import javax.swing.JTextField;


public class SignUpClass
{
  public static int counter = 0;
  private JFrame frame;
  private JTextField usernameField;
  private JTextField emailField;
  private JPasswordField passwordField;
  File file = new File("information.txt");
  
  private JPasswordField passwordField2;
  boolean isGenderSelect = false;
  





  static DataInputStream reader;
  





  static DataOutputStream writer;
  






  public SignUpClass()
  {
    frame = new JFrame();
    frame.getContentPane().setBackground(new Color(72, 209, 204));
    frame.setBounds(100, 100, 567, 487);
    frame.setDefaultCloseOperation(3);
    frame.setVisible(true);
    frame.getContentPane().setLayout(null);
    
    JLabel lblSignIn = new JLabel("Create a new account");
    lblSignIn.setBounds(129, 47, 260, 26);
    lblSignIn.setFont(new Font("Tahoma", 0, 24));
    frame.getContentPane().add(lblSignIn);
    
    JLabel lblUsername = new JLabel("Username");
    lblUsername.setBounds(88, 109, 103, 20);
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
    lblPassword.setBounds(88, 186, 103, 14);
    frame.getContentPane().add(lblPassword);
    
    passwordField = new JPasswordField();
    passwordField.setBounds(209, 180, 166, 26);
    frame.getContentPane().add(passwordField);
    
    JLabel lblBirthday = new JLabel("Birthday");
    lblBirthday.setBounds(90, 264, 86, 26);
    lblBirthday.setFont(new Font("Tahoma", 0, 20));
    frame.getContentPane().add(lblBirthday);
    
    final JComboBox comboBox = new JComboBox();
    comboBox.setBounds(207, 271, 71, 19);
    comboBox.setModel(new DefaultComboBoxModel(new String[] { "Day", "1", "2", "3", "4,", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14,", "15", "16", "17", "18", "19", "20" }));
    frame.getContentPane().add(comboBox);
    
    final JComboBox comboBox_1 = new JComboBox();
    comboBox_1.setBounds(292, 270, 83, 19);
    comboBox_1.setModel(new DefaultComboBoxModel(new String[] { "Month", "1", "2", "3", "4,", "5", "6", "7", "8", "9", "10", "11", "12" }));
    frame.getContentPane().add(comboBox_1);
    
    final JComboBox comboBox_2 = new JComboBox();
    comboBox_2.setBounds(394, 269, 72, 20);
    comboBox_2.setModel(new DefaultComboBoxModel(new String[] { "Year", "1996", "1996", "1997", "1998", "1999", "2000" }));
    frame.getContentPane().add(comboBox_2);
    
    final JRadioButton rdbtnMale = new JRadioButton("male");
    rdbtnMale.setBounds(206, 313, 72, 20);
    frame.getContentPane().add(rdbtnMale);
    
    JRadioButton rdbtnFemale = new JRadioButton("female");
    rdbtnFemale.setBounds(297, 313, 92, 21);
    frame.getContentPane().add(rdbtnFemale);
    
    final ButtonGroup myButtonGroup = new ButtonGroup();
    myButtonGroup.add(rdbtnMale);
    myButtonGroup.add(rdbtnFemale);
    


    JButton signUp2 = new JButton("Sign Up");
    signUp2.setBounds(198, 373, 118, 35);
    signUp2.setBackground(new Color(238, 238, 238));
    signUp2.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent arg0) {
        SignUpClass.counter += 1;
        String gendar;
         if (myButtonGroup.equals(rdbtnMale)) {
          gendar = "male";
        } else {
          gendar = "female";
        }
        String username = usernameField.getText();
        String email = emailField.getText();
        String password = passwordField.getText();
        String password2 = passwordField2.getText();
        
        String day = (String)comboBox.getSelectedItem();
        String month = (String)comboBox_1.getSelectedItem();
        String year = (String)comboBox_2.getSelectedItem();
        


        int user_len = username.length();
        int email_len = email.length();
        int pass_len = password.length();
        










        if ((user_len > 0) && (email_len > 0) && (pass_len > 0) && (day != "Day") && (month != "Month") && (year != "Year") && ((gendar.equals("male")) || (gendar.equals("female"))))
        {



















          if (password.equals(password2)) {
            JOptionPane.showMessageDialog(frame, "Successfully SignUp");
            
            try
            {
              Socket socket = new Socket("localhost", 9999);
              SignUpClass.writer = new DataOutputStream(socket.getOutputStream());
              
              SignUpClass.writer.writeUTF("$%#!#" + username + "#!#" + email + "#!#" + password + "#!#" + gendar + "#!#" + day + "/" + month + "/" + year);
            }
            catch (Exception e) {
              System.exit(0);
              e.printStackTrace();
            }
          }
          else
          {
            JOptionPane.showMessageDialog(frame, "Password missmatched...");

          }
          


        }
        else
        {
          JOptionPane.showMessageDialog(frame, "Please fill up all the field."); }
      }
    });
    signUp2.setFont(new Font("Tahoma", 0, 18));
    frame.getContentPane().add(signUp2);
    
    JButton signIn2 = new JButton("Sign in");
    signIn2.setBounds(430, 22, 89, 23);
    signIn2.setBackground(new Color(238, 238, 238));
    frame.getContentPane().add(signIn2);
    
    JLabel lblGender = new JLabel("Gender");
    lblGender.setBounds(100, 311, 91, 17);
    lblGender.setFont(new Font("Tahoma", 0, 20));
    frame.getContentPane().add(lblGender);
    
    JLabel lblRetypePassword = new JLabel("Re-Type password");
    lblRetypePassword.setBounds(59, 226, 137, 20);
    frame.getContentPane().add(lblRetypePassword);
    
    passwordField2 = new JPasswordField();
    passwordField2.setBounds(209, 217, 166, 29);
    frame.getContentPane().add(passwordField2);
    



    signIn2.addActionListener(new ActionListener()
    {
      public void actionPerformed(ActionEvent arg0) {
        frame.setVisible(false);
        UserMainClass umc = new UserMainClass();
      }
    });
  }
}

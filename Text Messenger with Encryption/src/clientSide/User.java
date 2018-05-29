package clientSide;

import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.math.BigInteger;
import java.net.Socket;
import java.util.StringTokenizer;
import java.util.Vector;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;












public class User
{
  private JFrame frame;
  static Vector<String> userlist = new Vector();
  
  Thread receive;
  
  static Socket sock;
  
  static String username;
  static Boolean isConnected = Boolean.valueOf(false);
  static DataInputStream reader;
  static DataOutputStream writer;
  static boolean flag = false;
  

  SignUpClass signup;
  
  RSAencryption rsa;
  

  public User(final Socket sock, final DataInputStream reader, final DataOutputStream writer, final RSAencryption rsa)
  {
    this.sock = sock;
    this.reader = reader;
    this. writer = writer;
    this.rsa = rsa;
    

    frame = new JFrame();
    frame.getContentPane().setBackground(new Color(95, 158, 160));
    frame.setBounds(100, 100, 640, 497);
    frame.setDefaultCloseOperation(3);
    frame.setVisible(true);
    frame.getContentPane().setLayout(null);
    
    final JTextArea textArea = new JTextArea();
    textArea.setBounds(26, 79, 289, 176);
    textArea.setBackground(new Color(192, 192, 192));
    frame.getContentPane().add(textArea);
    
    JLabel lblNewLabel = new JLabel("Message");
    lblNewLabel.setBounds(333, 161, 81, 55);
    lblNewLabel.setFont(new Font("Tahoma", 3, 16));
    frame.getContentPane().add(lblNewLabel);
    
    final JTextArea inputTextArea = new JTextArea();
    inputTextArea.setBounds(24, 373, 289, 55);
    inputTextArea.setBackground(Color.LIGHT_GRAY);
    frame.getContentPane().add(inputTextArea);
    


    JButton sendButton = new JButton("Send");
    sendButton.setBounds(331, 382, 108, 31);
    sendButton.setFont(new Font("Tahoma", 3, 18));
    sendButton.setBackground(new Color(176, 224, 230));
    frame.getContentPane().add(sendButton);
    
    JLabel lblActiveUser = new JLabel("Active user");
    lblActiveUser.setBounds(489, 51, 122, 31);
    lblActiveUser.setFont(new Font("Tahoma", 3, 16));
    frame.getContentPane().add(lblActiveUser);
    
    final JTextArea cipherField = new JTextArea();
    cipherField.setBounds(24, 289, 289, 54);
    cipherField.setBackground(Color.LIGHT_GRAY);
    frame.getContentPane().add(cipherField);
    




    final DefaultListModel activeUserList = new DefaultListModel();
    

    final JList list = new JList(activeUserList);
    list.setBounds(499, 94, 89, 284);
    list.setSelectionMode(0);
    list.setSelectedIndex(0);
    list.setVisibleRowCount(10);
    frame.getContentPane().add(list);
    











    receive = new Thread(new Runnable()
    {
      public void run()
      {
        try
        {
          for (;;)
          {
            boolean flagToCheckUser = true;
            
            String receiveMsg = reader.readUTF();
            System.out.println("message received::" + receiveMsg);
            
            StringTokenizer sti = new StringTokenizer(receiveMsg, "#!#");
            
            String message = sti.nextToken();
            String newActiveUser = sti.nextToken();
            




            if (!message.equals(" ")) {
              textArea.setEditable(true);
              
              String[] nameMsg = message.split("::");
              
              if (nameMsg.length > 1)
              {
                String plainMsg = rsa.decrypt(nameMsg[1]);
                textArea.setText(nameMsg[0] + " : " + plainMsg);
                

                BigInteger messageInt = new BigInteger(nameMsg[1]);
                String cipherTextReceive = new String(messageInt.toByteArray());
                
                cipherField.setText(cipherTextReceive);



              }
              else if (nameMsg.length < 2) {
                JOptionPane.showMessageDialog(frame, message);
                
                if (message.equals("username or password is incorrect "))
                {
                  User.isConnected = Boolean.valueOf(false);
                }
              }
            }
            













            int x = User.userlist.size();
            
            if (!newActiveUser.equals(" "))
            {
              System.out.println("newActiveuser::" + newActiveUser);
              
              String[] checkUserRemove = newActiveUser.split("@@@@");
              System.out.println("remove found..." + checkUserRemove[0]);
              
              if (checkUserRemove[0].equals("remove")) {
                System.out.println("if condition remove");
                
                for (int i = 0; i < x; i++) {
                  if (checkUserRemove[1].equals(User.userlist.get(i))) {
                    User.userlist.remove(i);
                    activeUserList.removeElementAt(i);
                    break;
                  }
                  
                }
                
              }
              else
              {
                for (int i = 0; i < x; i++) {
                  if (newActiveUser.equals(User.userlist.get(i)))
                  {
                    flagToCheckUser = false;
                    break;
                  }
                }
                

                if (flagToCheckUser)
                {
                  activeUserList.addElement(newActiveUser);
                  
                  User.userlist.add(newActiveUser);
                }
                
              }
            }
          }
        }
        catch (IOException e)
        {
          System.out.println("Problem occured while receiving message...");
          System.exit(0);
        }
      }
    });
    receive.start();
    








    sendButton.addActionListener(new ActionListener()
    {
      public void actionPerformed(ActionEvent arg0)
      {
        String receipent = " ";
        if (list.getSelectedIndex() != -1) {
          receipent = (String)list.getSelectedValue();
        }
        

        String msg = inputTextArea.getText();
        try {
          if (!receipent.equals(" "))
          {

            String[] str = receipent.split("               ");
            
            String cipherMsg = rsa.encrypt(msg, str[1], str[2]);
            


            writer.writeUTF(cipherMsg + "#!#" + receipent);
          }
          else
          {
            JOptionPane.showMessageDialog(frame, "Select Receiver, please");
          }
        }
        catch (IOException e) {
          e.printStackTrace();
          System.out.println("problem occurred in sending");

        }
        
      }
      

    });
    JButton btnLogOut = new JButton("Log out");
    btnLogOut.setBounds(499, 411, 89, 23);
    btnLogOut.addActionListener(new ActionListener()
    {
      public void actionPerformed(ActionEvent arg0)
      {
        try
        {
          writer.writeUTF("logout");
          receive.stop();
          reader.close();
          writer.close();
          sock.close();
          frame.setVisible(false);
          System.exit(0);

        }
        catch (IOException e)
        {
          e.printStackTrace();
        }
      }
    });
    frame.getContentPane().add(btnLogOut);
    
    JLabel lblCipherText_1 = new JLabel("Cipher Text");
    lblCipherText_1.setBounds(333, 308, 106, 15);
    lblCipherText_1.setFont(new Font("Dialog", 1, 16));
    frame.getContentPane().add(lblCipherText_1);
    
    JLabel lblSecuredMessenger = new JLabel("Secured Messenger");
    lblSecuredMessenger.setBounds(184, 39, 277, 28);
    lblSecuredMessenger.setFont(new Font("Dialog", 3, 24));
    frame.getContentPane().add(lblSecuredMessenger);
  }
}

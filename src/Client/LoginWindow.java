package Client;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.Socket;
import java.util.ArrayList;

/**
 * Created by Lawrence on 3/23/16.
 *
 */
public class LoginWindow {
    private JPanel LoginPanel;
    private JButton Login;
    private JLabel UsernameLabel;
    private JLabel PasswordLabel;
    private JTextField username;
    private JPasswordField password;
    private JLabel TipArea;
    private static JFrame frame;
    public LoginWindow() {
        Login.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Socket userSocket;
                BufferedReader reader;
                PrintWriter writer;
                String response;
                String uname = username.getText();
                String upass = new String(password.getPassword());
                String request = "101|" + uname + "," + upass;
                try {
                    userSocket = new Socket("127.0.0.1", 2333);
                    writer = new PrintWriter(new OutputStreamWriter(userSocket.getOutputStream()));
                    reader = new BufferedReader(new InputStreamReader(userSocket.getInputStream()));
                    writer.println(request);
                    writer.flush();
//                    System.out.println(reader.readLine());
                    response = reader.readLine();
                    if(StringUtils.getCode(response).equals("1")) {
                        UserInfo.userName = uname;
                        UserInfo.loginTime = System.currentTimeMillis() + "";
                        UserInfo.messageQuene = new ArrayList<>();
                        UserInfo.initAttr();
                        frame.dispose(); //Hide the frame
                        MainWindow.generateMainWindow(userSocket, reader, writer);
                    } else {
                        TipArea.setText(StringUtils.getDetail(response));
//                        System.out.println(StringUtils.getDetail(response));
                    }
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        });
    }
    public static void generateLoginWindow() {
        frame = new JFrame("Chatting Room: Login");
        frame.setContentPane(new LoginWindow().LoginPanel);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}

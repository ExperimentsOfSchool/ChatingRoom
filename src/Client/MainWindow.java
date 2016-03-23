package Client;

import Server.User;
import Server.UserList;

import javax.swing.*;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * Created by Lawrence on 3/23/16.
 *
 */
public class MainWindow {
    private JLabel currentUser;
    private JPanel settingWindow;
    private JPanel SendWindow;
    private JTextPane receiveTextArea;
    private JComboBox<String> UserList;
    private JTextPane sendTextArea;
    private JButton sendButton;
    private JButton emojiButton;
    private JButton fileButton;
    private JPanel ReceiveWindow;
    private JPanel functionWindow;
    private JPanel MainWindow;
    private JScrollPane scrollPane;
    private BufferedReader reader;
    private PrintWriter writer;
    public MainWindow(PrintWriter writer, BufferedReader reader) {
        sendButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String request;
                String content = sendTextArea.getText();
                if(!content.equals("")) {
                    request = "103|" + UserList.getSelectedItem() + "|" + System.currentTimeMillis() + "|" + content;
                    writer.println(request);
                    writer.flush();
                    UserInfo.messageQuene.add(new Message(UserInfo.userName, (String)UserList.getSelectedItem(), System.currentTimeMillis() + "", content));
                }
                Document receiveDoc = receiveTextArea.getDocument();
                try {
                    receiveDoc.insertString(receiveDoc.getLength(), UserInfo.userName + ": " + "(" + System.currentTimeMillis() + ")\n", UserInfo.title);
                    receiveDoc.insertString(receiveDoc.getLength(),content + "\n", UserInfo.content);
                } catch (BadLocationException ex) {
                    ex.printStackTrace();
                }
                sendTextArea.setText("");
            }
        });
        UserList.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                receiveTextArea.setText("");
                for(Message message: UserInfo.messageQuene) {
                    if(message.sender.equals(UserList.getSelectedItem()) || message.receiver.equals(UserList.getSelectedItem())) {
                        Document receiveDoc = receiveTextArea.getDocument();
                        try {
                            receiveDoc.insertString(receiveDoc.getLength(), message.sender + ": " + "(" + message.time + ")\n", UserInfo.title);
                            receiveDoc.insertString(receiveDoc.getLength(), message.content + "\n", UserInfo.content);
                        } catch (BadLocationException ex) {
                            ex.printStackTrace();
                        }
                    }
                }
            }
        });
    }

    //    private static PrintWriter writerA;
    public static void generateMainWindow(Socket userSocket, BufferedReader reader, PrintWriter writer) {
        JFrame frame = new JFrame("Chatting Room");
        MainWindow mainWindow = new MainWindow(writer, reader);
        String request = "102|" + UserInfo.userName;
        String response;
        try {
            writer.println(request);
            writer.flush();
            while(true) {
                response = reader.readLine();
                if(StringUtils.getCode(response).equals("102")) {
                    UserInfo.userList = StringUtils.getNameList(response);
                    for(String user: UserInfo.userList) {
                        mainWindow.UserList.addItem(user);
                    }
                    break;
                } else if(StringUtils.getCode(response).equals("103")) {
                    UserInfo.messageQuene.add(new Message(StringUtils.getSender(response), UserInfo.userName, StringUtils.getTime(response), StringUtils.getContent(response)));
                }
            }
            new ReceiveThread(userSocket, reader, mainWindow).start();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        mainWindow.currentUser.setText(UserInfo.userName);
        frame.setContentPane(mainWindow.MainWindow);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
//        writerA = writer;
//        System.out.println(writerA == null);
    }
    public JComboBox<String> getUserList() {
        return this.UserList;
    }
    public JTextPane getReceiveTextArea() {
        return this.receiveTextArea;
    }
}

package Client;

import javax.swing.*;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import java.io.BufferedReader;
import java.io.IOException;
import java.net.Socket;

/**
 * Created by Lawrence on 3/23/16.
 *
 */
public class ReceiveThread extends Thread {
    private Socket userSocket;
    private BufferedReader reader;
    private MainWindow window;
    public ReceiveThread(Socket userSocket, BufferedReader reader, MainWindow window) {
        this.userSocket = userSocket;
        this.reader = reader;
        this.window = window;
    }
    @Override
    public void run() {
        while(userSocket != null && userSocket.isConnected()) {
            try {
                String response = reader.readLine();
//                System.out.println(response);
                if(StringUtils.getCode(response).equals("103")) {
                    UserInfo.messageQuene.add(new Message(StringUtils.getSender(response), UserInfo.userName, StringUtils.getTime(response), StringUtils.getContent(response)));
                    if(window.getUserList().getSelectedItem().equals(StringUtils.getSender(response))) {
                        JTextPane jTextPane = window.getReceiveTextArea();
                        Document receiveDoc = jTextPane.getDocument();
                        try {
                            receiveDoc.insertString(receiveDoc.getLength(), StringUtils.getSender(response) + ": " + "(" + StringUtils.getTime(response) + ")\n", UserInfo.title);
                            receiveDoc.insertString(receiveDoc.getLength(), StringUtils.getContent(response) + "\n", UserInfo.content);
                        } catch (BadLocationException e) {
                            e.printStackTrace();
                        }
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }
}

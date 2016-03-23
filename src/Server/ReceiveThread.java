package Server;

import java.io.BufferedReader;
import java.net.Socket;
import java.util.ArrayList;

/**
 * Created by Lawrence on 3/23/16.
 *
 */
public class ReceiveThread extends Thread {
    private Socket userSocket;
    private BufferedReader reader;
    private ArrayList<String> sendQuene;
    private String name;
    private UserList userList;
    public ReceiveThread(Socket userSocket, BufferedReader reader, ArrayList<String> sendQuene, String name) {
        this.userSocket = userSocket;
        this.reader = reader;
        this.sendQuene = sendQuene;
        this.name = name;
        this.userList = UserList.getInstance();
    }
    @Override
    public void run() {
        while(userSocket != null && userSocket.isConnected()) {
            try {
                String message = reader.readLine();
                if(message == null) break; //Bugfix
                String response;
                switch(StringUtils.getCode(message)) {
                    case "102":
                        response = "102|" + name + "," + name + "|" + System.currentTimeMillis() + "|" + userList.getUserList(name);
                        System.out.println(response);
                        sendQuene.add(response);
                        break;
                    case "103":
                        response = "103|" + name + "," + StringUtils.getContent(message);
                        sendQuene.add(response);
                        break;
                    default:
                        response = "500|" + name + "," + name + "|" +System.currentTimeMillis() + "|" + "Bad Request";
                        sendQuene.add(response);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        userList.userLogout(name);
    }
}

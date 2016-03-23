package Server;

import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;

/**
 * Created by Lawrence on 3/23/16.
 *
 */
public class SendThread extends Thread {
    private Socket userSocket;
    private PrintWriter writer;
    private ArrayList<String> sendQuene;
    private String name;
    private UserList userList;
    public SendThread(Socket userSocket, PrintWriter writer, ArrayList<String> sendQuene, String name) {
        this.userSocket = userSocket;
        this.writer = writer;
        this.sendQuene = sendQuene;
        this.name = name;
        this.userList = UserList.getInstance();
    }
    @Override
    public void run() {
        while(userSocket != null && userSocket.isConnected()) {
            try {
                if(!userList.getUserStatus(name)) {
                    break;
                }
                if(sendQuene.isEmpty()) {
                    Thread.sleep(1000);
                    continue;
                }
                String execMsg = null;
                for(String message: sendQuene) {
                    String[] pieces = message.split("\\|");
                    if(pieces[1].split(",")[1].equals(name)) {
                        execMsg = message;
                        switch(pieces[0]) {
                            case "102":
                                writer.println("102|" + pieces[2] + "|" + pieces[3]); //Get User List: 102|Time|List
                                break;
                            case "103":
                                writer.println("103|" + pieces[1].split(",")[0] + "|" + pieces[2] + "|" + pieces[3]); //Send Message
                                break;
                            default:
                                writer.println("500|Bad Request!"); //Error Tip
                        }
                        writer.flush();
                    }
                }
                if(execMsg != null) sendQuene.remove(execMsg);
                Thread.sleep(1000);
            } catch (Exception e) {
                e.printStackTrace();
                break;
            }
        }
    }
}

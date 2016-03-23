package Server;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;

/**
 * Created by Lawrence on 3/23/16.
 *
 */
public class UserThread extends Thread {
    private ArrayList<String> sendQuene;
    private Socket userSocket;
    private BufferedReader reader;
    private PrintWriter writer;
    private boolean authStatus;
    private UserList userList;
    private String userName;
    public UserThread(ArrayList<String> sendQuene, Socket userSocket) {
        this.sendQuene = sendQuene;
        this.userSocket = userSocket;
        this.authStatus = false;
        this.userList = UserList.getInstance();
    }
    @Override
    public void run() {
        try {
            reader = new BufferedReader(new InputStreamReader(userSocket.getInputStream()));
            writer = new PrintWriter(new OutputStreamWriter(userSocket.getOutputStream()));
            String message = reader.readLine();
            String code = StringUtils.getCode(message);
            String content = StringUtils.getContent(message);
            if(code.equals("101")) {
                //101
                String[] userInfo = StringUtils.getNameAndPassword(content);
                switch (userList.authUser(userInfo[0], userInfo[1])) {
                    case 0:
                        this.userName = userInfo[0];
                        this.authStatus = true;
                        writer.println("1|Login Succeed!");
                        break;
                    case 1:
                        //Already Login.
                        writer.println("0|Already Login!");
                        this.authStatus = false;
                        break;
                    case 2:
                        //Wrong password.
                        writer.println("0|Wrong Password!");
                        this.authStatus = false;
                        break;
                    case 3:
                        //No such user.
                        writer.println("0|No such user!");
                        this.authStatus = false;
                        break;
                    default:
                        //Unknown Error.
                        writer.println("0|Unknown Error!");
                        this.authStatus = false;
                }
            } else {
                writer.println("500|Bad Request!");
            }
            writer.flush();
            if(this.authStatus) {
//                System.out.println(sendQuene.size());
                new ReceiveThread(userSocket, reader, sendQuene, userName).start();
//                try {
//                    Thread.sleep(2000);
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
                new SendThread(userSocket, writer, sendQuene, userName).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}

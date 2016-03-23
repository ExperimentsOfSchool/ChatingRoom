package Server;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.ArrayList;

/**
 * Created by Lawrence on 3/21/16.
 *
 */
public class Server {
    public static void main(String[] args) {
        ArrayList<String> sendQuene = new ArrayList<>(); //code|from, to|time|content
        ServerSocket ss;
        try {
            ss = new ServerSocket(2333);
            while(true) {
                new UserThread(sendQuene, ss.accept()).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

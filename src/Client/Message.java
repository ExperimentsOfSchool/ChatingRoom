package Client;

/**
 * Created by Lawrence on 3/23/16.
 *
 */
public class Message {
    public String sender;
    public String receiver;
    public String time;
    public String content;
    public Message(String sender, String receiver, String time, String content) {
        this.sender = sender;
        this.receiver = receiver;
        this.time = time;
        this.content = content;
    }
}

package Client;

import javax.swing.*;
import java.util.ArrayList;

/**
 * Created by Lawrence on 3/24/16.
 *
 */
public class IconList {
    private static IconList ourInstance = new IconList();
    private ArrayList<EmojiIcon> emojiIcons;
    public static IconList getInstance() {
        return ourInstance;
    }
    private IconList() {
        this.emojiIcons = new ArrayList<>();
        this.emojiIcons.add(new EmojiIcon(new ImageIcon("./Emoji/[5g5].gif"), "5g5"));
        this.emojiIcons.add(new EmojiIcon(new ImageIcon("./Emoji/[5g5d].gif"), "5g5d"));
        this.emojiIcons.add(new EmojiIcon(new ImageIcon("./Emoji/[11].gif"), "11"));
        this.emojiIcons.add(new EmojiIcon(new ImageIcon("./Emoji/[22].gif"), "22"));
        this.emojiIcons.add(new EmojiIcon(new ImageIcon("./Emoji/[33].gif"), "33"));
        this.emojiIcons.add(new EmojiIcon(new ImageIcon("./Emoji/[44].gif"), "44"));
        this.emojiIcons.add(new EmojiIcon(new ImageIcon("./Emoji/[45g].gif"), "45g"));
        this.emojiIcons.add(new EmojiIcon(new ImageIcon("./Emoji/[55].gif"), "55"));
        this.emojiIcons.add(new EmojiIcon(new ImageIcon("./Emoji/[65f].gif"), "65f"));
        this.emojiIcons.add(new EmojiIcon(new ImageIcon("./Emoji/[343].gif"), "343"));
        this.emojiIcons.add(new EmojiIcon(new ImageIcon("./Emoji/[1112].gif"), "1112"));
        this.emojiIcons.add(new EmojiIcon(new ImageIcon("./Emoji/[3454].gif"), "3454"));
        this.emojiIcons.add(new EmojiIcon(new ImageIcon("./Emoji/[4343].gif"), "4343"));
        this.emojiIcons.add(new EmojiIcon(new ImageIcon("./Emoji/[4546r].gif"), "4546r"));
        this.emojiIcons.add(new EmojiIcon(new ImageIcon("./Emoji/[5567g1].gif"), "5567g1"));
        this.emojiIcons.add(new EmojiIcon(new ImageIcon("./Emoji/[6565].gif"), "6565"));
    }
    public ArrayList<EmojiIcon> getEmojiIcons() {
        return this.emojiIcons;
    }
}

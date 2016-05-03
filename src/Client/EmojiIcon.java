package Client;

import javax.swing.*;

/**
 * Created by Lawrence on 3/24/16.
 *
 */
public class EmojiIcon {
    private ImageIcon imageIcon;
    private String iconName;
    public EmojiIcon(ImageIcon imageIcon, String iconName) {
        this.imageIcon = imageIcon;
        this.iconName = iconName;
    }
    public ImageIcon getImageIcon() {
        return this.imageIcon;
    }
    public String getIconName() {
        return this.iconName;
    }
}

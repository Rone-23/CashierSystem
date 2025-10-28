package utility;

import assets.Constants;
import views.Components.ChonkyArrowButton;
import views.Components.ChonkyButton;

import javax.swing.*;
import java.awt.*;

public class ButtonBuilder {

    public static JButton buildChonkyButton(String text, Color color){
        JButton jButton = new ChonkyButton(text, color);
        jButton.setName(text.toLowerCase());
        return jButton;
    }

    public static JButton buildChonkyButtonDisabled(Color color) {
        JButton jButton = new ChonkyButton(color);
        jButton.setEnabled(false);
//        jButton.setPreferredSize(new Dimension(0,0));
        return jButton;
    }

    public static JButton buildChonkyArrowButton(Color color, Constants direction){
        JButton jButton = new ChonkyArrowButton(color, direction);
        return jButton;
    }
}

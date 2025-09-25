package utility;

import viewsRework.GP.Display;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.text.MutableAttributeSet;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import java.awt.*;

public class DisplayBuilder {

//    public static JTextArea buildDisplay(Color color){
//        JTextArea display = new JTextArea();
//        display.setBackground(color);
//        display.setEditable(false);
//        return display;
//    }
//    public static JTextArea buildDisplay(Color color, Border border){
//        JTextArea display = new JTextArea();
//        display.setBackground(color);
//        display.setBorder(border);
//        display.setEditable(false);
//        return display;
//    }

    public static JScrollPane buildDisplay (Color color){
        Display display = new Display();
        display.setBackground(color);
        display.setEditable(false);
        display.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
//        display.setFont(new Font("Roboto", Font.PLAIN, 50));
        display.setMargin(new Insets(0, 0, 0, 0));
        JScrollPane jScrollPane = new JScrollPane(display);
        jScrollPane.setBorder(null);
        jScrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants. HORIZONTAL_SCROLLBAR_NEVER);
        return jScrollPane;
    }

    public void updateContent(){

    }
}

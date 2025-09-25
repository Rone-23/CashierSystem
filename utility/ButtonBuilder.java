package utility;

import assets.Colors;
import com.fasterxml.jackson.databind.ser.impl.FailingSerializer;
import viewsRework.GP.ChonkyButton;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class ButtonBuilder {

    //TODO: maybe make it to .setText to the same value as .setText
    public static JButton buildButton(){
        JButton jButton = new JButton();
        jButton.setBorder(new BevelBorder(0));
        jButton.setBackground(Colors.DEFAULT_BUTTON.getColor());
        jButton.setForeground(Colors.BLACK_TEXT.getColor());
        return jButton;
    }
    public static JButton buildButton(Color color){
        JButton jButton = new JButton()
//        {
//            @Override
//            protected void paintComponent(Graphics g){
//
//            }
//        }
        ;
        jButton.setBorder(new BevelBorder(0));
        jButton.setBackground(color);
        jButton.setForeground(Colors.BLACK_TEXT.getColor());

        return jButton;
    }
    public static JButton buildButton(Color color, Border border){
        JButton jButton = new JButton();
        jButton.setBackground(color);
        jButton.setForeground(Colors.BLACK_TEXT.getColor());
        jButton.setBorder(border);
        return jButton;
    }
    public static JButton buildButton(Color color, String text){
        JButton jButton = new JButton();
        jButton.setText(text);
        jButton.setBackground(color);
        jButton.setForeground(Colors.BLACK_TEXT.getColor());
        jButton.setBorder(new BevelBorder(0));
        return jButton;
    }
    public static JButton buildButton(Color color, String text,Border border){
        JButton jButton = new JButton();
        jButton.setText(text);
        jButton.setBackground(color);
        jButton.setForeground(Colors.BLACK_TEXT.getColor());
        jButton.setBorder(border);
        return jButton;
    }

    public static JButton buildButtonBlank(){
        JButton jButton = new JButton();
        jButton.setBorder(new BevelBorder(0));
        jButton.setBackground(Colors.GRAY.getColor());
        jButton.setForeground(Colors.BLACK_TEXT.getColor());
        jButton.setEnabled(false);
        return jButton;
    }
    public static JButton buildChonkyButton(String text){
        JButton jButton = new ChonkyButton(text);
        return jButton;
    }
}

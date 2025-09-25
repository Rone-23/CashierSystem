package viewsRework.GP;

import assets.Colors;

import javax.swing.*;
import java.awt.*;

public class DisplayScrollable extends JScrollPane {
    JScrollPane mainScrollPane = new JScrollPane();
    public DisplayScrollable(){
        this.mainScrollPane.setBackground(Colors.BACKGROUND_WHITE.getColor());
        this.mainScrollPane.setPreferredSize(new Dimension(400,200));
    }

    public void addItem(String text){
        JPanel item = new JPanel();
        item.add(new JLabel(text));
        this.mainScrollPane.add(item);

    }

    public JScrollPane getMainPanel(){
        return this.mainScrollPane;
    }
}

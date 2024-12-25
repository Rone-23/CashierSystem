package utility;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

public class PanelBuilder {

    public static JPanel createPanel(LayoutManager layout) {
        JPanel panel = new JPanel(layout);
        return panel;
    }
    public static JPanel createPanel(LayoutManager layout, Border border) {
        JPanel panel = new JPanel(layout);
        panel.setBorder(border);
        return panel;
    }
    public static JPanel createPanel(LayoutManager layout, Color background, Border border) {
        JPanel panel = new JPanel(layout);
        panel.setBackground(background);
        panel.setBorder(border);
        return panel;
    }


}

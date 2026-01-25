package views.panels;

import javax.swing.*;

public interface ButtonFoundable {
    JButton getButton(String key);
    JButton[] getButtons(String key);
}

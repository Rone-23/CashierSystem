package views.leftPanel;

import utility.GridBagConstraintsBuilder;
import utility.PanelBuilder;

import javax.swing.*;
import java.awt.*;

public class LeftPanel {
    JPanel mainPanel = PanelBuilder.createPanel(new GridBagLayout());
    KeyboardPanel keyboardPanel;
    DisplayPanel displayPanel;
    public LeftPanel(){
        GridBagConstraints gbc = GridBagConstraintsBuilder.buildGridBagConstraints();
        gbc.gridy=0;
        gbc.gridx=0;
        this.displayPanel = new DisplayPanel();
        mainPanel.add(this.displayPanel.getMainPanel(),gbc);

        gbc.gridy=1;
        this.keyboardPanel =new KeyboardPanel();
        mainPanel.add(this.keyboardPanel.getMainPanel(),gbc);
    }
    public JPanel getPanel(){
        return mainPanel;
    }

    public KeyboardPanel getKeyboardPanel(){return this.keyboardPanel;}
    public DisplayPanel getDisplayPanel(){return this.displayPanel;}
}

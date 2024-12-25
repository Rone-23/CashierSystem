package views.leftPanel;

import assets.Constants;
import utility.GridBagConstraintsBuilder;
import utility.PanelBuilder;

import javax.swing.*;
import java.awt.*;

public class KeyboardPanel {
    private final JPanel mainPanel = PanelBuilder.createPanel(new GridBagLayout());
    private final GridBagConstraints gbc = GridBagConstraintsBuilder.buildGridBagConstraints(0,0);
    private Keyboard keyboard;
    private Utility utility;
    public KeyboardPanel(){
        gbc.weighty = Constants.KEYBOARD_HEIGHT.getValue();
        gbc.weightx = Constants.KEYBOARD_WIDTH.getValue();
        this.keyboard = new Keyboard();
        this.mainPanel.add(this.keyboard.getMainPanel(),gbc);

        gbc.gridx = 1;
        gbc.weighty = Constants.UTILITY_HEIGHT.getValue();
        gbc.weightx = Constants.UTILITY_WIDTH.getValue();
        this.utility = new Utility();
        this.mainPanel.add(utility.getMainPanel(),gbc);

    }
    public JPanel getMainPanel(){return this.mainPanel;}

    public Keyboard getKeyboard(){return this.keyboard;}

    public Utility getUtility(){return this.utility;}
}

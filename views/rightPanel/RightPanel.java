package views.rightPanel;

import assets.Constants;
import utility.GridBagConstraintsBuilder;
import utility.PanelBuilder;

import javax.swing.*;
import java.awt.*;

public class RightPanel {
    JPanel mainPanel = PanelBuilder.createPanel(new GridBagLayout());

    public RightPanel(){
        GridBagConstraints gbc = GridBagConstraintsBuilder.buildGridBagConstraints(0,0);

        gbc.weighty = Constants.RIGHT_TOP_PART_HEIGHT.getValue();
        gbc.weightx = Constants.RIGHT_TOP_PART_WIDTH.getValue();
        mainPanel.add(new RightTopPanel().getMainPanel(),gbc);

        gbc.gridy = 1;
        gbc.weighty = Constants.BUTTONS_HEIGHT.getValue();
        gbc.weightx = Constants.BUTTONS_WIDTH.getValue();
        mainPanel.add(new Buttons().getMainPanel(),gbc);
    }
    public JPanel getPanel(){
        return mainPanel;
    }

}

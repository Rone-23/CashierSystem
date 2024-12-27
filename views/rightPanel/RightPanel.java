package views.rightPanel;

import assets.Constants;
import utility.GridBagConstraintsBuilder;
import utility.PanelBuilder;

import javax.swing.*;
import java.awt.*;

public class RightPanel {
    JPanel mainPanel = PanelBuilder.createPanel(new GridBagLayout());
    RightTopPanel rightTopPanel;
    RightBottomPanel rightBottomPanel;
    public RightPanel(){
        GridBagConstraints gbc = GridBagConstraintsBuilder.buildGridBagConstraints(0,0);

        gbc.weighty = Constants.RIGHT_TOP_PART_HEIGHT.getValue();
        gbc.weightx = Constants.RIGHT_TOP_PART_WIDTH.getValue();
        this.rightTopPanel =new RightTopPanel();
        mainPanel.add(this.rightTopPanel.getMainPanel(),gbc);

        gbc.gridy = 1;
        gbc.weighty = Constants.BUTTONS_HEIGHT.getValue();
        gbc.weightx = Constants.BUTTONS_WIDTH.getValue();
        this.rightBottomPanel = new RightBottomPanel();
        mainPanel.add(this.rightBottomPanel.getMainPanel(),gbc);
    }
    public JPanel getPanel(){
        return mainPanel;
    }

    public RightTopPanel getRightTopPanel(){ return this.rightTopPanel;}

    public RightBottomPanel getRightBottomPanel(){ return this.rightBottomPanel;}
}

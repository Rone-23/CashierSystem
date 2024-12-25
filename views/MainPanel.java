package views;

import assets.Constants;
import utility.GridBagConstraintsBuilder;
import utility.PanelBuilder;
import views.leftPanel.LeftPanel;
import views.rightPanel.RightPanel;

import javax.swing.*;
import java.awt.*;

public class MainPanel {
    private final JPanel mainPanel = PanelBuilder.createPanel(new GridBagLayout());
    private LeftPanel leftPanel;
    public MainPanel(){
        GridBagConstraints gbc = GridBagConstraintsBuilder.buildGridBagConstraints(0,0);
        gbc.weighty = Constants.TOP_PANEL_HEIGHT.getValue();
        gbc.weightx = Constants.LEFT_PANEL_WIDTH.getValue();
        this.leftPanel = new LeftPanel();
        this.mainPanel.add(this.leftPanel.getPanel(),gbc);

        gbc.weighty = Constants.TOP_PANEL_HEIGHT.getValue();
        gbc.weightx = Constants.RIGHT_PANEL_WIDTH.getValue();
        gbc.gridx = 1;
        this.mainPanel.add(new RightPanel().getPanel(),gbc);

    }
    public JPanel getMainPanel(){
        return this.mainPanel;
    }
    public LeftPanel getLeftPanel(){return this.leftPanel;}
}

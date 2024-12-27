package views;

import assets.Constants;
import utility.GridBagConstraintsBuilder;
import utility.PanelBuilder;
import views.leftPanel.LeftPanel;
import views.rightPanel.RightPanel;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.concurrent.Flow;

public class MainPanel {
    private final JLayeredPane stackedPanel;
    private final JPanel mainPanel = PanelBuilder.createPanel(new GridBagLayout());
    private final LeftPanel leftPanel;
    private final RightPanel rightPanel;
    public MainPanel(){
        GridBagConstraints gbc = GridBagConstraintsBuilder.buildGridBagConstraints(0,0);
        gbc.weighty = Constants.TOP_PANEL_HEIGHT.getValue();
        gbc.weightx = Constants.LEFT_PANEL_WIDTH.getValue();
        this.leftPanel = new LeftPanel();
        this.mainPanel.add(this.leftPanel.getPanel(),gbc);

        gbc.weighty = Constants.TOP_PANEL_HEIGHT.getValue();
        gbc.weightx = Constants.RIGHT_PANEL_WIDTH.getValue();
        gbc.gridx = 1;
        this.rightPanel = new RightPanel();
        this.mainPanel.add(this.rightPanel.getPanel(),gbc);
        this.mainPanel.setVisible(true);

        this.stackedPanel = new JLayeredPane();
        this.stackedPanel.setLayout(new GridBagLayout());
        gbc.gridx=0;
        gbc.gridy=0;
        gbc.weightx=1;
        gbc.weighty=1;
        this.stackedPanel.add(this.mainPanel,gbc,JLayeredPane.DEFAULT_LAYER);
    }
    public JLayeredPane getMainPanel(){ return this.stackedPanel;}

    public LeftPanel getLeftPanel(){return this.leftPanel;}

    public RightPanel getRightPanel(){return this.rightPanel;}

    public void setVisible(Boolean condition){this.mainPanel.setVisible(condition);}

}

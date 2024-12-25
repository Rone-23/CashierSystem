package views.rightPanel;

import assets.Colors;
import utility.PanelBuilder;

import javax.swing.*;
import java.awt.*;

public class RightTopPanel {
    private final JPanel mainPanel = PanelBuilder.createPanel(new GridBagLayout(), Colors.GRAY.getColor(),null);

    public JPanel getMainPanel(){
        return mainPanel;
    }
}



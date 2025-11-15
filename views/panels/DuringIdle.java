package views.panels;

import assets.Colors;
import utility.GridBagConstraintsBuilder;
import views.Components.ButtonCluster;

import javax.swing.*;
import java.awt.*;

public class DuringIdle extends JPanel {
    ButtonCluster buttonCluster;
    public DuringIdle(){
        GridBagConstraints gbc = GridBagConstraintsBuilder.buildGridBagConstraints(1,1);
        setLayout(new GridBagLayout());
        setBackground(Colors.BACKGROUND_WHITE.getColor());

        JPanel fillerPanel = new JPanel();
        fillerPanel.setOpaque(false);

        String[] buttonNames = {"Zacat (Artikle)", "Vybavit kartu", "Kopia blocku", "Vratka", "Poukážky", "Pauza", "Zaučenie"};
        buttonCluster = new ButtonCluster(buttonNames);

        add(fillerPanel, gbc);
        gbc.weightx=0.2;
        gbc.gridx++;
        add(buttonCluster, gbc);

    }

    public ButtonCluster getButtonCluster() {
        return buttonCluster;
    }
}

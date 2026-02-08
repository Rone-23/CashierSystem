package views.panels;

import assets.ButtonSet;
import assets.Colors;
import utility.GridBagConstraintsBuilder;
import views.Components.ButtonCluster;
import views.Components.StatusBar;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class DuringIdle extends JPanel implements ButtonFoundable {
    private final ButtonCluster buttonCluster;
    private final StatusBar statusBar = new StatusBar();

    public DuringIdle() {
        setLayout(new GridBagLayout());
        setBackground(Colors.BACKGROUND_WHITE.getColor());

        GridBagConstraints gbc = GridBagConstraintsBuilder.buildGridBagConstraints(1, 1);
        gbc.gridy = 0;
        gbc.gridx = 0;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;

        JPanel fillerPanel = new JPanel();
        fillerPanel.setOpaque(false);
        add(fillerPanel, gbc);

        gbc.weightx = 0.2;
        gbc.gridx = 1;
        buttonCluster = new ButtonCluster(ButtonSet.IDLE_UTILITY_NAMES.getStringLabels());
        add(buttonCluster, gbc);

        GridBagConstraints gbcStatus = GridBagConstraintsBuilder.buildGridBagConstraints(1, 1);
        gbcStatus.gridy = 1;
        gbcStatus.gridx = 0;
        gbcStatus.gridwidth = 2;
        gbcStatus.weighty = 0;
        gbcStatus.fill = GridBagConstraints.HORIZONTAL;
        add(statusBar, gbcStatus);
    }

    @Override
    public JButton getButton(String key) {
        for (Component c : buttonCluster.getComponents()) {
            if (c instanceof JButton && c.getName() != null && c.getName().equals(key.toLowerCase())) {
                return (JButton) c;
            }
        }
        throw new ArrayIndexOutOfBoundsException();
    }

    @Override
    public JButton[] getButtons(String key) {
        ArrayList<JButton> jButtons = new ArrayList<>();

        for (Component c : buttonCluster.getComponents()) {
            if (c instanceof JButton && c.getName() != null && c.getName().equals(key.toLowerCase())) {
                jButtons.add((JButton) c);
            }
        }
        if (!jButtons.isEmpty()) {
            return jButtons.toArray(new JButton[0]);
        }
        throw new ArrayIndexOutOfBoundsException();
    }
}
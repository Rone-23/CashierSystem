package views.panels;

import assets.ButtonSet;
import assets.Colors;
import assets.ThemeManager;
import assets.ThemeObserver;
import utility.ButtonBuilder;
import utility.GridBagConstraintsBuilder;
import views.Components.ButtonCluster;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class DuringIdle extends JPanel implements ButtonFoundable, ThemeObserver {
    private final ButtonCluster buttonCluster;
    JButton themeButton = ButtonBuilder.buildChonkyButton(ButtonSet.ButtonLabel.THEME_BUTTON.toString(), Colors.BUTTON_LIGHT_BLUE);

    public DuringIdle() {
        setLayout(new GridBagLayout());

        GridBagConstraints gbc = GridBagConstraintsBuilder.buildGridBagConstraints(1, 1);
        gbc.gridy = 0;
        gbc.gridx = 0;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;

        JPanel fillerPanel = new JPanel(new GridBagLayout());
        fillerPanel.setOpaque(false);

        themeButton.setPreferredSize(new Dimension(200, 124));
        themeButton.setFont(new Font("Roboto", Font.BOLD, 24));

        GridBagConstraints themeGbc = new GridBagConstraints();
        themeGbc.anchor = GridBagConstraints.SOUTHWEST;
        themeGbc.weightx = 1.0;
        themeGbc.weighty = 1.0;

        fillerPanel.setOpaque(false);
        fillerPanel.add(themeButton, themeGbc);

        add(fillerPanel, gbc);

        gbc.weightx = 0.2;
        gbc.gridx = 1;
        buttonCluster = new ButtonCluster(ButtonSet.IDLE_UTILITY_NAMES.getLabels());
        add(buttonCluster, gbc);

        onThemeChange();
        ThemeManager.getInstance().addObserver(this);
    }


    @Override
    public JButton getButton(String key) {
        for (Component c : buttonCluster.getComponents()) {
            if (c instanceof JButton && c.getName() != null && c.getName().equals(key.toLowerCase())) {
                return (JButton) c;
            }
        }
        if(key.equals(ButtonSet.ButtonLabel.THEME_BUTTON.toString())){
            return themeButton;
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
        if(key.equals(ButtonSet.ButtonLabel.THEME_BUTTON.toString())){
            jButtons.add(themeButton);
        }
        if (!jButtons.isEmpty()) {
            return jButtons.toArray(new JButton[0]);
        }
        throw new ArrayIndexOutOfBoundsException();
    }


    @Override
    public void onThemeChange() {
        setBackground(Colors.BACKGROUND_WHITE.getColor());
        repaint();
    }
}
package views.panels;

import assets.*;
import utility.ButtonBuilder;
import utility.GridBagConstraintsBuilder;
import views.Components.ButtonCluster;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class DuringIdle extends JPanel implements ButtonFoundable, ThemeObserver {

    private final ButtonCluster buttonCluster = new ButtonCluster(ButtonSet.IDLE_UTILITY_NAMES.getLabels());
    private final JButton themeButton = ButtonBuilder.buildChonkyButton(ButtonSet.ButtonLabel.THEME_BUTTON.toString(), Colors.BUTTON_LIGHT_BLUE);

    public DuringIdle() {
        setLayout(new GridBagLayout());
        onThemeChange();
        ThemeManager.getInstance().addObserver(this);

        JPanel leftPanel = createLeftPanel();
        JPanel rightPanel = createRightPanel();

        GridBagConstraints gbcMain = GridBagConstraintsBuilder.buildGridBagConstraints(1, 1);
        gbcMain.fill = GridBagConstraints.BOTH;
        gbcMain.weighty = 1.0;

        gbcMain.weightx = 0.70;
        leftPanel.setPreferredSize(new Dimension(0, 0));
        add(leftPanel, gbcMain);

        gbcMain.gridx++;
        gbcMain.weightx = 0.30;
        rightPanel.setPreferredSize(new Dimension(0, 0));
        add(rightPanel, gbcMain);


    }

    public JPanel createLeftPanel(){
        themeButton.setPreferredSize(new Dimension(200, 124));
        themeButton.setFont(new Font("Roboto", Font.BOLD, 24));

        GridBagConstraints themeGbc = new GridBagConstraints();
        themeGbc.anchor = GridBagConstraints.SOUTHWEST;
        themeGbc.weightx = 1.0;
        themeGbc.weighty = 1.0;

        final JPanel leftPanel = new JPanel(new GridBagLayout());
        leftPanel.setName("leftPanel");
        leftPanel.setOpaque(false);
        leftPanel.add(themeButton, themeGbc);
        return leftPanel;
    }

    public JPanel createRightPanel(){
        JPanel rightPanel = new JPanel(new BorderLayout());
        rightPanel.setOpaque(false);

        rightPanel.add(buttonCluster, BorderLayout.CENTER);

        return rightPanel;
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
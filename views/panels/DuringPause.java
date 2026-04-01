package views.panels;

import assets.*;
import utility.GridBagConstraintsBuilder;
import views.Components.ButtonCluster;
import views.Components.Display;
import views.Components.Keyboard;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.ArrayList;

public class DuringPause extends JPanel implements ButtonFoundable, ThemeObserver {

    private final Dimension dimension = new Dimension(500, 114);

    private final Display inputDisplay = new Display(Constants.CODE,"Zadajte kód");
    private final Keyboard keyboard = new Keyboard();
    private ButtonCluster actionButtons;

    public DuringPause() {
        setLayout(new BorderLayout());

        JPanel centerPanel = new JPanel(new GridBagLayout());
        centerPanel.setOpaque(false);

        JPanel leftPanel = createLeftPanel();
        JPanel middlePanel = createMiddlePanel();
        JPanel rightPanel = createRightPanel();

        GridBagConstraints gbcMain = GridBagConstraintsBuilder.buildGridBagConstraints(1, 1);
        gbcMain.fill = GridBagConstraints.BOTH;
        gbcMain.weighty = 1.0;

        centerPanel.add(leftPanel, gbcMain);
        gbcMain.gridx++;
        gbcMain.weightx = 0.35;
        centerPanel.add(middlePanel, gbcMain);
        gbcMain.gridx++;
        gbcMain.weightx = 2.0;
        centerPanel.add(rightPanel, gbcMain);

        add(centerPanel, BorderLayout.CENTER);

        onThemeChange();
        ThemeManager.getInstance().addObserver(this);
    }

    private JPanel createLeftPanel(){
        final JPanel leftPanel = new JPanel();
        leftPanel.setName("leftPanel");
        leftPanel.setOpaque(false);
        leftPanel.setPreferredSize(dimension);
        return leftPanel;
    }

    private JPanel createMiddlePanel() {
        final GridBagConstraints gbc = GridBagConstraintsBuilder.buildGridBagConstraints(1,1);
        final JPanel middlePanel = new JPanel();
        middlePanel.setName("middlePanel");

        middlePanel.setBorder(new EmptyBorder(20, 0, 20, 0));
        middlePanel.setLayout(new GridBagLayout());
        middlePanel.setOpaque(false);

        gbc.weightx = 0.0;
        gbc.weighty = 0.0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        inputDisplay.setPreferredSize(dimension);
        middlePanel.add(inputDisplay, gbc);

        gbc.gridy++;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        JPanel fillerPanel = new JPanel();
        fillerPanel.setOpaque(false);
        middlePanel.add(fillerPanel, gbc);

        gbc.gridy++;
        gbc.weighty = 0.0;
        gbc.weightx = 0.0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        middlePanel.add(keyboard, gbc);

        return middlePanel;
    }

    private JPanel createRightPanel() {
        JPanel rightPanel = new JPanel(new BorderLayout());
        rightPanel.setOpaque(false);

        actionButtons = new ButtonCluster(new ButtonSet.ButtonLabel[]{ButtonSet.ButtonLabel.ADD, ButtonSet.ButtonLabel.LOGIN}, Constants.VERTICAL);

        rightPanel.add(actionButtons, BorderLayout.NORTH);

        return rightPanel;
    }

    @Override
    public JButton getButton(String key) {
        for (Component c : keyboard.getComponentsInside()) {
            if (c instanceof JButton && c.getName() != null && c.getName().equals(key.toLowerCase())) {
                return (JButton) c;
            }
        }

        for (Component c : actionButtons.getComponents()) {
            if (c instanceof JButton && c.getName() != null && c.getName().equals(key.toLowerCase())) {
                return (JButton) c;
            }
        }
        throw new ArrayIndexOutOfBoundsException();
    }

    @Override
    public JButton[] getButtons(String key) {
        ArrayList<JButton> jButtons = new ArrayList<>();


        for (Component c : keyboard.getComponentsInside()) {
            if (c instanceof JButton && c.getName() != null && c.getName().equals(key.toLowerCase())) {
                jButtons.add((JButton) c);
            }
        }

        for (Component c : actionButtons.getComponents()) {
            if (c instanceof JButton && c.getName() != null && c.getName().equals(key.toLowerCase())) {
                jButtons.add((JButton) c);
            }
        }
        if (!jButtons.isEmpty()) {
            return jButtons.toArray(new JButton[0]);
        }

        throw new ArrayIndexOutOfBoundsException();
    }

    public Display getInputDisplay() {
        return inputDisplay;
    }

    @Override
    public void onThemeChange() {
        setBackground(Colors.BACKGROUND_GRAY.getColor());
        repaint();
    }
}
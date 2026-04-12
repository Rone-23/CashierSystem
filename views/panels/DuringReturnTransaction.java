package views.panels;

import assets.*;
import utility.GridBagConstraintsBuilder;
import utility.ThemeMatteBorder;
import views.Components.*;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;

public class DuringReturnTransaction extends JPanel implements ButtonFoundable, ThemeObserver {

    private final Dimension dimension = new Dimension(500, 114);

    private final DisplayItems displayItems = new DisplayItems();
    private final Display displayTotal = new Display(Constants.TOTAL);
    private final Display displayTopAmount = new Display(Constants.WEIGHT);
    private final Display displayTopTotal = new Display(Constants.TOTAL, "Ku výdavku");
    private final Keypad keypad = new Keypad();
    private final ButtonCluster utilityButtonCluster = new ButtonCluster(ButtonSet.RETURN_TRANSACTION_UTILITY_NAMES.getLabels(), Constants.VERTICAL);
    private final ButtonCluster commonButtonCluster = new ButtonCluster(ButtonSet.RETURN_TRANSACTION_RETURN_MONEY.getLabels(), Constants.VERTICAL);
    private final CardLayout cardLayout = new CardLayout();
    private final JPanel rightPanel;
    private final JPanel leftPanel;
    private final JPanel middlePanel;
    private final JPanel displayPanel = new JPanel();
    private final CardLayout cardLayoutDisplay = new CardLayout();

    public DuringReturnTransaction() {
        setLayout(new GridBagLayout());

        JPanel mainContent = new JPanel(new GridBagLayout());
        mainContent.setOpaque(false);

        this.rightPanel = createRightPanel();
        this.leftPanel = createLeftPanel();
        this.middlePanel = createMiddlePanel();

        GridBagConstraints gbcMain = GridBagConstraintsBuilder.buildGridBagConstraints(1, 1);
        gbcMain.fill = GridBagConstraints.BOTH;
        gbcMain.weighty = 1.0;

        gbcMain.gridx = 0;
        gbcMain.weightx = 0.35;
        leftPanel.setPreferredSize(new Dimension(0, 0));
        mainContent.add(leftPanel, gbcMain);

        gbcMain.gridx++;
        gbcMain.weightx = 0.35;
        middlePanel.setPreferredSize(new Dimension(0, 0));
        mainContent.add(middlePanel, gbcMain);

        gbcMain.gridx++;
        gbcMain.weightx = 0.30;
        rightPanel.setPreferredSize(new Dimension(0, 0));
        mainContent.add(rightPanel, gbcMain);

        GridBagConstraints gbc = GridBagConstraintsBuilder.buildGridBagConstraints(1, 1);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        add(mainContent, gbc);

        onThemeChange();
        ThemeManager.getInstance().addObserver(this);
    }

    private JPanel createLeftPanel() {
        final GridBagConstraints gbc = GridBagConstraintsBuilder.buildGridBagConstraints(1, 1);
        final JPanel leftPanel = new JPanel();
        leftPanel.setName("leftPanel");

        Border rightBorder = new ThemeMatteBorder(
                0,
                0,
                0,
                3,
                Colors.BUTTON_LIGHT_BLUE
        );
        leftPanel.setBorder(rightBorder);

        leftPanel.setLayout(new GridBagLayout());
        leftPanel.setOpaque(false);

        leftPanel.add(displayItems, gbc);

        gbc.weighty = 0;
        gbc.weightx = 0;
        gbc.gridy++;
        displayTotal.setPreferredSize(dimension);
        leftPanel.add(displayTotal, gbc);

        return leftPanel;
    }

    private JPanel createMiddlePanel() {
        final GridBagConstraints gbc = GridBagConstraintsBuilder.buildGridBagConstraints();
        final JPanel middlePanel = new JPanel();
        middlePanel.setName("middlePanel");

        middlePanel.setBorder(new EmptyBorder(20, 0, 20, 0));
        middlePanel.setLayout(new GridBagLayout());
        middlePanel.setOpaque(false);

        gbc.gridx = 0;

        displayPanel.setOpaque(false);
        displayPanel.setLayout(cardLayoutDisplay);

        displayPanel.add(displayTopAmount, "AMOUNT");
        displayPanel.add(displayTopTotal, "TOTAL");

        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 0;
        gbc.weighty = 0;
        gbc.gridy = 0;
        displayTopAmount.setPreferredSize(dimension);
        middlePanel.add(displayPanel, gbc);

        gbc.gridy++;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        JPanel fillerPanel = new JPanel();
        fillerPanel.setOpaque(false);
        middlePanel.add(fillerPanel, gbc);

        gbc.gridy++;
        gbc.weighty = 0;
        gbc.weightx = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        middlePanel.add(keypad, gbc);

        return middlePanel;
    }

    private JPanel createRightPanel() {
        final JPanel rightPanel = new JPanel();
        rightPanel.setName("rightPanel");
        rightPanel.setLayout(cardLayout);
        rightPanel.setOpaque(false);

        JPanel commonButtonClusterPanel = new JPanel(new BorderLayout());
        commonButtonClusterPanel.setOpaque(false);
        commonButtonClusterPanel.add(commonButtonCluster, BorderLayout.NORTH);

        rightPanel.add(utilityButtonCluster, "UTILITY_BUTTON_CLUSTER");
        rightPanel.add(commonButtonClusterPanel, "COMMON_BUTTON_CLUSTER");

        return rightPanel;
    }

    @Override
    public JButton getButton(String key) {
        for (Component c : keypad.getComponentsInside()) {
            if (c instanceof JButton && c.getName() != null && c.getName().equals(key.toLowerCase())) {
                return (JButton) c;
            }
        }
        for (Component c : utilityButtonCluster.getComponents()) {
            if (c instanceof JButton && c.getName() != null && c.getName().equals(key.toLowerCase())) {
                return (JButton) c;
            }
        }
        for (Component c : commonButtonCluster.getComponents()) {
            if (c instanceof JButton && c.getName() != null && c.getName().equals(key.toLowerCase())) {
                return (JButton) c;
            }
        }
        throw new ArrayIndexOutOfBoundsException();
    }

    @Override
    public JButton[] getButtons(String key) {
        ArrayList<JButton> jButtons = new ArrayList<>();

        for (Component c : keypad.getComponentsInside()) {
            if (c instanceof JButton && c.getName() != null && c.getName().equals(key.toLowerCase())) {
                jButtons.add((JButton) c);
            }
        }
        for (Component c : utilityButtonCluster.getComponents()) {
            if (c instanceof JButton && c.getName() != null && c.getName().equals(key.toLowerCase())) {
                jButtons.add((JButton) c);
            }
        }
        for (Component c : commonButtonCluster.getComponents()) {
            if (c instanceof JButton && c.getName() != null && c.getName().equals(key.toLowerCase())) {
                jButtons.add((JButton) c);
            }
        }

        if (!jButtons.isEmpty()) {
            return jButtons.toArray(new JButton[0]);
        }

        throw new ArrayIndexOutOfBoundsException();
    }

    public void replaceButton(String name, String replaceName) {
        utilityButtonCluster.replaceButton(name, replaceName);
    }

    public void switchState(ActionEvent actionEvent) {
        switch (actionEvent.getActionCommand()) {
            case "Hotovosť" -> {
                cardLayout.show(rightPanel, "COMMON_BUTTON_CLUSTER");
                cardLayoutDisplay.show(displayPanel, "TOTAL");
            }
            case "Karta" -> {
                cardLayout.show(rightPanel, "COMMON_BUTTON_CLUSTER");
                cardLayoutDisplay.show(displayPanel, "TOTAL");
            }
            case "Naspäť" -> {
                cardLayout.show(rightPanel,"UTILITY_BUTTON_CLUSTER");
                cardLayoutDisplay.show(displayPanel, "AMOUNT");
            }
        }
    }

    public void returnToDefault(){
        cardLayout.show(rightPanel,"UTILITY_BUTTON_CLUSTER");
        cardLayoutDisplay.show(displayPanel, "AMOUNT");
    }

    public DisplayItems getDisplayScrollableItems() { return displayItems; }
    public Display getDisplayTotal() { return displayTotal; }
    public Display getDisplayTopAmount() { return displayTopAmount; }
    public Display getDisplayTopTotal() { return displayTopTotal; }
    public JPanel getLeftPanel(){return leftPanel;}
    public JPanel getMiddlePanel(){return this.middlePanel;}
    public JPanel getRightPanel(){return this.rightPanel;}

    @Override
    public void onThemeChange() {
        setBackground(Colors.BACKGROUND_WHITE.getColor());
        repaint();
    }
}
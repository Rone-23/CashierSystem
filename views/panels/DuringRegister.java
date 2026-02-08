package views.panels;

import assets.ButtonSet;
import assets.Colors;
import assets.Constants;
import utility.GridBagConstraintsBuilder;
import views.Components.*;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.border.MatteBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;

public class DuringRegister extends JPanel implements ButtonFoundable {

    private final Dimension dimension = new Dimension(500, 114);

    private final DisplayItems displayItems = new DisplayItems();
    private final Display displayTotal = new Display(Constants.TOTAL);
    private final Display displayTopAmount = new Display(Constants.WEIGHT);
    private final Display displayTopTotal = new Display(Constants.TOTAL);
    private final Keyboard keyboard = new Keyboard();
    private final ButtonCluster utilityButtonCluster = new ButtonCluster(ButtonSet.UTILITY_NAMES.getStringLabels(), Constants.VERTICAL);
    private final ButtonCluster cashButtonCluster = new ButtonCluster(ButtonSet.CASH_NAMES.getStringLabels(), Constants.VERTICAL);
    private final ButtonCluster commonButtonCluster = new ButtonCluster(ButtonSet.COMMON_NAMES.getStringLabels(), Constants.VERTICAL);
    private final CardLayout cardLayout = new CardLayout();
    final JPanel rightPanel = new JPanel();
    JPanel displayPanel = new JPanel();
    CardLayout cardLayoutDisplay = new CardLayout();
    private final StatusBar statusBar = new StatusBar();

    public DuringRegister() {
        setLayout(new GridBagLayout());
        setBackground(Colors.BACKGROUND_WHITE.getColor());

        JPanel mainContent = new JPanel(new GridBagLayout());
        mainContent.setOpaque(false);

        JPanel leftPanel = createLeftPanel();
        JPanel middlePanel = createMiddlePanel();
        JPanel rightPanel = createRightPanel();

        GridBagConstraints gbcMain = GridBagConstraintsBuilder.buildGridBagConstraints(1, 1);
        gbcMain.fill = GridBagConstraints.BOTH;
        gbcMain.weighty = 1.0;

        mainContent.add(leftPanel, gbcMain);
        gbcMain.gridx++;
        gbcMain.weightx = 0.3;
        mainContent.add(middlePanel, gbcMain);
        gbcMain.gridx++;
        gbcMain.weightx = 0.3;
        mainContent.add(rightPanel, gbcMain);

        GridBagConstraints gbc = GridBagConstraintsBuilder.buildGridBagConstraints(1, 1);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        add(mainContent, gbc);

        gbc.gridy = 1;
        gbc.weighty = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        add(statusBar, gbc);
    }

    private JPanel createLeftPanel() {
        final GridBagConstraints gbc = GridBagConstraintsBuilder.buildGridBagConstraints(1, 1);
        final JPanel leftPanel = new JPanel();
        leftPanel.setName("leftPanel");

        Border rightBorder = new MatteBorder(
                0,
                0,
                0,
                3,
                Colors.BUTTON_LIGHT_BLUE.getColor()
        );
        leftPanel.setBorder(rightBorder);

        leftPanel.setLayout(new GridBagLayout());
        leftPanel.setBackground(Colors.BACKGROUND_WHITE.getColor());

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
        middlePanel.setBackground(Colors.BACKGROUND_GRAY.getColor());

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
        middlePanel.add(keyboard, gbc);

        return middlePanel;
    }

    private JPanel createRightPanel() {
        rightPanel.setName("rightPanel");
        rightPanel.setLayout(cardLayout);
        rightPanel.setBackground(Colors.BACKGROUND_GRAY.getColor());

        rightPanel.add(utilityButtonCluster, "UTILITY_BUTTON_CLUSTER");
        rightPanel.add(cashButtonCluster, "CASH_BUTTON_CLUSTER");
        rightPanel.add(commonButtonCluster, "COMMON_BUTTON_CLUSTER");

        return rightPanel;
    }

    @Override
    public JButton getButton(String key) {
        for (Component c : keyboard.getComponentsInside()) {
            if (c instanceof JButton && c.getName() != null && c.getName().equals(key.toLowerCase())) {
                return (JButton) c;
            }
        }
        for (Component c : utilityButtonCluster.getComponents()) {
            if (c instanceof JButton && c.getName() != null && c.getName().equals(key.toLowerCase())) {
                return (JButton) c;
            }
        }
        for (Component c : cashButtonCluster.getComponents()) {
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

        for (Component c : keyboard.getComponentsInside()) {
            if (c instanceof JButton && c.getName() != null && c.getName().equals(key.toLowerCase())) {
                jButtons.add((JButton) c);
            }
        }
        for (Component c : utilityButtonCluster.getComponents()) {
            if (c instanceof JButton && c.getName() != null && c.getName().equals(key.toLowerCase())) {
                jButtons.add((JButton) c);
            }
        }
        for (Component c : cashButtonCluster.getComponents()) {
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

    public void replaceButton(String[] name, String[] replaceName) {
        utilityButtonCluster.replaceButton(name, replaceName);
    }

    public void switchState(ActionEvent actionEvent) {
        switch (actionEvent.getActionCommand()) {
            case "Naspäť" -> {
                cardLayout.show(rightPanel, "UTILITY_BUTTON_CLUSTER");
                cardLayoutDisplay.show(displayPanel, "AMOUNT");
            }
            case "Hotovost" -> {
                cardLayout.show(rightPanel, "CASH_BUTTON_CLUSTER");
                cardLayoutDisplay.show(displayPanel, "TOTAL");
            }
            case "Karta", "Stravenky", "Poukážky" -> {
                cardLayout.show(rightPanel, "COMMON_BUTTON_CLUSTER");
                cardLayoutDisplay.show(displayPanel, "TOTAL");
            }
        }
    }

    public DisplayItems getDisplayScrollableItems() { return displayItems; }
    public Display getDisplayTotal() { return displayTotal; }
    public Display getDisplayTopAmount() { return displayTopAmount; }
    public Display getDisplayTopTotal() { return displayTopTotal; }
}
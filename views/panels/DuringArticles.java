package views.panels;

import assets.*;
import utility.ButtonBuilder;
import utility.GridBagConstraintsBuilder;
import utility.ThemeMatteBorder;
import views.Components.*;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.ArrayList;

public class DuringArticles extends JPanel implements ButtonFoundable, ThemeObserver {
    private final DisplayItems displayItems = new DisplayItems();
    private final Display display = new Display(Constants.SPLIT);
    private final Keypad keypad = new Keypad();
    private final DisplayArticles displayArticles = new DisplayArticles();
    private final ArticleFilterButtonCluster articleFilterButtonCluster = new ArticleFilterButtonCluster();
    int dividerThickness = Math.max(1, assets.Scaler.getPadding(0.004));
    private final JButton plusButton = ButtonBuilder.buildChonkyButton(ButtonSet.ButtonLabel.ADD.toString(), ButtonSet.ButtonLabel.ADD.getColor());
    private final JButton minusButton = ButtonBuilder.buildChonkyButton(ButtonSet.ButtonLabel.REMOVE.toString(), ButtonSet.ButtonLabel.REMOVE.getColor());
    private final JButton searchButton = ButtonBuilder.buildChonkyButton(ButtonSet.ButtonLabel.SEARCH.toString(), ButtonSet.ButtonLabel.SEARCH.getColor());
    private final JButton cancelButton = ButtonBuilder.buildChonkyButton(ButtonSet.ButtonLabel.EXIT.toString(), ButtonSet.ButtonLabel.EXIT.getColor());
    private final JLabel subsectionLabel = new JLabel(" ", SwingConstants.CENTER);

    private final JPanel clusterBottom = createBottomCluster();


    public DuringArticles() {
        GridBagConstraints gbcMain = GridBagConstraintsBuilder.buildGridBagConstraints(0, 0);
        setLayout(new GridBagLayout());

        JPanel leftPanel = createLeftPanel();
        JPanel rightPanel = createRightPanel();

        gbcMain.fill = GridBagConstraints.BOTH;
        gbcMain.weighty = 1.0;

        gbcMain.gridx = 0;
        gbcMain.weightx = 0.35;
        leftPanel.setPreferredSize(new Dimension(0, 0));
        add(leftPanel, gbcMain);

        gbcMain.gridx++;
        gbcMain.weightx = 0.65;
        rightPanel.setPreferredSize(new Dimension(0, 0));
        add(rightPanel, gbcMain);

        onThemeChange();
        ThemeManager.getInstance().addObserver(this);
    }

    private JPanel createLeftPanel() {
        JPanel main = new JPanel();
        main.setLayout(new GridBagLayout());
        main.setOpaque(false);
        GridBagConstraints gbcLeftPanel = GridBagConstraintsBuilder.buildGridBagConstraints(0, 0);
        gbcLeftPanel.weighty = 1.0;
        main.add(displayItems, gbcLeftPanel);


        gbcLeftPanel.weightx = 0;
        gbcLeftPanel.weighty = 0.0;
        gbcLeftPanel.gridy++;
        main.add(display, gbcLeftPanel);

        gbcLeftPanel.weighty = 0.0;
        gbcLeftPanel.gridy++;
        main.add(keypad, gbcLeftPanel);

        Border rightBorder = new ThemeMatteBorder(0, 0, 0, dividerThickness, Colors.BUTTON_LIGHT_BLUE);
        main.setBorder(rightBorder);

        return main;
    }

    private JPanel createRightPanel() {
        JPanel main = new JPanel();
        main.setLayout(new GridBagLayout());
        main.setOpaque(false);
        GridBagConstraints gbcRightPanel = GridBagConstraintsBuilder.buildGridBagConstraints(0, 0);

        gbcRightPanel.weighty = 0;
        Border bottomBorder = new ThemeMatteBorder(0, 0, dividerThickness, 0, Colors.BUTTON_LIGHT_BLUE);
        articleFilterButtonCluster.setBorder(bottomBorder);
        main.add(articleFilterButtonCluster, gbcRightPanel);

        gbcRightPanel.weighty = 1;
        gbcRightPanel.gridy++;
        main.add(displayArticles, gbcRightPanel);

        gbcRightPanel.weighty = 0;
        gbcRightPanel.gridy++;
        main.add(clusterBottom, gbcRightPanel);

        return main;
    }

    private JPanel createBottomCluster() {
        JPanel main = new JPanel();
        main.setLayout(new GridBagLayout());
        main.setOpaque(false);

        JPanel subsection = new JPanel();
        subsection.setLayout(new GridBagLayout());
        subsection.setBorder(new EmptyBorder(5, 5, 5, 5));
        subsection.setOpaque(false);

        Border topBorder = new ThemeMatteBorder(dividerThickness, 0, 0, 0, Colors.BUTTON_LIGHT_BLUE);
        main.setBorder(topBorder);
        final Dimension buttonDimensions = new Dimension(183, 136);
        GridBagConstraints gbcBottomCluster = GridBagConstraintsBuilder.buildGridBagConstraints(0, 0);
        GridBagConstraints gbcSubsection = GridBagConstraintsBuilder.buildGridBagConstraints(0, 0);

        subsectionLabel.setText("Množstvo tovaru");
        subsectionLabel.setForeground(Colors.BLACK_TEXT.getColor());
        subsectionLabel.setFont(assets.Scaler.getFont(0.025, Font.BOLD));

        gbcSubsection.gridwidth = 2;
        subsection.add(subsectionLabel, gbcSubsection);

        gbcSubsection.gridwidth = 1;
        gbcSubsection.gridy++;
        subsection.add(plusButton, gbcSubsection);

        gbcSubsection.gridx++;
        subsection.add(minusButton, gbcSubsection);

        gbcBottomCluster.gridwidth = 2;
        main.add(subsection, gbcBottomCluster);

        gbcBottomCluster.gridwidth = 1;
        gbcBottomCluster.gridx += 2;
        main.add(searchButton, gbcBottomCluster);

        gbcBottomCluster.gridx++;
        main.add(cancelButton, gbcBottomCluster);

        return main;
    }

    public DisplayItems getDisplayScrollableItems() { return displayItems; }
    public DisplayArticles getDisplayScrollableArticles() { return displayArticles; }
    public Display getDisplay() { return display; }
    public ArticleFilterButtonCluster getArticleFilterButtonCluster() { return articleFilterButtonCluster; }
    public Keypad getKeypad(){return keypad;}

    @Override
    public JButton getButton(String key) {
        for (Component c : keypad.getComponentsInside()) {
            if (c instanceof JButton && c.getName() != null && c.getName().equals(key.toLowerCase())) {
                return (JButton) c;
            }
        }
        for (Component c : clusterBottom.getComponents()) {
            if (c instanceof JButton && c.getName() != null && c.getName().equals(key.toLowerCase())) {
                return (JButton) c;
            }
        }
        if (plusButton.getName() != null && plusButton.getName().equals(key.toLowerCase())) {
            return plusButton;
        }
        if (minusButton.getName() != null && minusButton.getName().equals(key.toLowerCase())) {
            return minusButton;
        }
        if (searchButton.getName() != null && searchButton.getName().equals(key.toLowerCase())) {
            return searchButton;
        }
        if (cancelButton.getName() != null && cancelButton.getName().equals(key.toLowerCase())) {
            return cancelButton;
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
        for (Component c : clusterBottom.getComponents()) {
            if (c instanceof JButton && c.getName() != null && c.getName().equals(key.toLowerCase())) {
                jButtons.add((JButton) c);
            }
        }
        if (plusButton.getName() != null && plusButton.getName().equals(key.toLowerCase())) {
            jButtons.add(plusButton);
        }
        if (minusButton.getName() != null && minusButton.getName().equals(key.toLowerCase())) {
            jButtons.add(minusButton);
        }
        if (searchButton.getName() != null && searchButton.getName().equals(key.toLowerCase())) {
            jButtons.add(searchButton);
        }
        if (cancelButton.getName() != null && cancelButton.getName().equals(key.toLowerCase())) {
            jButtons.add(cancelButton);
        }
        if (!jButtons.isEmpty()) {
            return jButtons.toArray(new JButton[0]);
        }
        throw new ArrayIndexOutOfBoundsException();
    }


    @Override
    public void onThemeChange() {
        setBackground(Colors.BACKGROUND_WHITE.getColor());

        if (subsectionLabel != null) {
            subsectionLabel.setForeground(Colors.BLACK_TEXT.getColor());
        }

        repaint();
    }
}
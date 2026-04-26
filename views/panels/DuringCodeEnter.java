package views.panels;

import assets.*;
import utility.GridBagConstraintsBuilder;
import views.Components.*;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.ArrayList;

public class DuringCodeEnter extends JPanel implements ButtonFoundable, ThemeObserver {

    private final Dimension dimension = new Dimension(500, 114);

    private final Display inputDisplay = new Display(Constants.CODE);
    private final Keypad keypad = new Keypad();
    private ButtonCluster actionButtons;

    private final JPanel leftPanel;
    private final JPanel middlePanel;
    private final JPanel rightPanel;

    private final JLabel explanationLabel = new JLabel();


    public DuringCodeEnter() {
        inputDisplay.setText("");
        setLayout(new BorderLayout());

        JPanel centerPanel = new JPanel(new GridBagLayout());
        centerPanel.setOpaque(false);

        leftPanel = createLeftPanel();
        middlePanel = createMiddlePanel();
        rightPanel = createRightPanel();

        GridBagConstraints gbcMain = GridBagConstraintsBuilder.buildGridBagConstraints(1, 1);
        gbcMain.fill = GridBagConstraints.BOTH;
        gbcMain.weighty = 1.0;

        gbcMain.gridx = 0;
        gbcMain.weightx = 0.35;
        leftPanel.setPreferredSize(new Dimension(0, 0));
        centerPanel.add(leftPanel, gbcMain);

        gbcMain.gridx++;
        gbcMain.weightx = 0.35;
        middlePanel.setPreferredSize(new Dimension(0, 0));
        centerPanel.add(middlePanel, gbcMain);

        gbcMain.gridx++;
        gbcMain.weightx = 0.30;
        rightPanel.setPreferredSize(new Dimension(0, 0));
        centerPanel.add(rightPanel, gbcMain);

        add(centerPanel, BorderLayout.CENTER);

        onThemeChange();
        ThemeManager.getInstance().addObserver(this);
    }

    private JPanel createLeftPanel(){
        final JPanel leftPanel = new JPanel(new BorderLayout());
        leftPanel.setName("leftPanel");
        leftPanel.setOpaque(false);
        leftPanel.setPreferredSize(dimension);

        int pad = Scaler.getPadding(0.02);
        leftPanel.setBorder(new EmptyBorder(pad, pad, pad, pad));

        explanationLabel.setFont(Scaler.getFont(0.02, Font.PLAIN));
        explanationLabel.setForeground(Colors.BLACK_TEXT.getColor());
        explanationLabel.setVerticalAlignment(SwingConstants.TOP); // Align text to the top

        leftPanel.add(explanationLabel, BorderLayout.CENTER);

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
        middlePanel.add(keypad, gbc);

        return middlePanel;
    }

    private JPanel createRightPanel() {
        JPanel rightPanel = new JPanel(new BorderLayout());
        rightPanel.setOpaque(false);

        actionButtons = new ButtonCluster(ButtonSet.DURING_CODE_NAMES.getLabels(), Constants.VERTICAL);

        rightPanel.add(actionButtons, BorderLayout.NORTH);

        return rightPanel;
    }

    public void setExplanationText(String title, String text) {
        int width = Scaler.getDimension(0.25, 0).width;
        explanationLabel.setText(
                "<html><div style='width: " + width + "px;'>" +
                        "<b style='font-size: 1.2em;'>" + title + "</b><br><br>" +
                        text +
                        "</div></html>"
        );
    }

    @Override
    public JButton getButton(String key) {
        for (Component c : keypad.getComponentsInside()) {
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


        for (Component c : keypad.getComponentsInside()) {
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

    public void setMode(Constants mode) {
        inputDisplay.setSuggestionText(mode);
        inputDisplay.setText("");
    }

    public JPanel getRightPanel(){return rightPanel;}
    public JPanel getMiddlePanel(){return middlePanel;}
    public JPanel getLeftPanel(){return leftPanel;}

    public Display getInputDisplay() {
        return inputDisplay;
    }

    @Override
    public void onThemeChange() {
        setBackground(Colors.BACKGROUND_GRAY.getColor());
        explanationLabel.setForeground(Colors.BLACK_TEXT_LIGHT.getColor());
        repaint();
    }
}
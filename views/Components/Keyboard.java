package views.Components;

import assets.Colors;
import utility.ButtonBuilder;
import utility.GridBagConstraintsBuilder;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class Keyboard extends JPanel{
    final String[] buttonNames = {"7", "8", "9", "4", "5", "6", "1", "2", "3", "0", "00", ","};
    private final JPanel topPanel = new JPanel();
    private final JPanel bottomPanel = new JPanel();
    private final Map<String, JButton> buttons = new HashMap<>();
    private final Dimension numberButtonDimensions = new Dimension(183,136);
    private final Dimension utilityButtonDimensions = new Dimension(280,136);
    private final JButton deleteButton;
    private final JButton backspaceButton;

    public Keyboard(){
        topPanel.setLayout( new GridBagLayout());
        bottomPanel.setLayout( new GridBagLayout());

        topPanel.setOpaque(false);
        bottomPanel.setOpaque(false);

        setOpaque(false);
        Color color = Colors.BUTTON_LIGHT_BLUE.getColor();
        GridBagConstraints gbc = GridBagConstraintsBuilder.buildGridBagConstraints(0,0);

        //Adding delete button
        deleteButton = ButtonBuilder.buildChonkyButton("Delete", color);
        deleteButton.setPreferredSize(utilityButtonDimensions);
        topPanel.add(deleteButton,gbc);

        //Adding delete button
        gbc.gridx = 1;
        backspaceButton = ButtonBuilder.buildChonkyButton("Backspace", color);
        backspaceButton.setPreferredSize(utilityButtonDimensions);
        topPanel.add(backspaceButton,gbc);

        //Making and Adding buttons
        gbc.gridy = 1;
        gbc.gridx = 0;
        for (int numberIndex = 0; numberIndex < 12; numberIndex++) {
            JButton[] keypadButtons = new JButton[12];
            keypadButtons[numberIndex] = ButtonBuilder.buildChonkyButton(buttonNames[numberIndex], color);
            keypadButtons[numberIndex].setPreferredSize(numberButtonDimensions);
            buttons.put(buttonNames[numberIndex], keypadButtons[numberIndex]);
            bottomPanel.add(keypadButtons[numberIndex],gbc);

            gbc.gridx++;
            if ((numberIndex + 1) % 3 == 0) {
                gbc.gridx=0;
                gbc.gridy++;
            }
        }

        this.setLayout(new GridBagLayout());
        gbc.gridwidth = 1;
        gbc.gridy = 1;
        gbc.gridx = 0;
        this.add(topPanel,gbc);
        gbc.gridy = 2;
        gbc.gridx = 0;
        this.add(bottomPanel,gbc);

    }

    public JButton getButton(String key){
        switch (key.toLowerCase()){
            case "delete" -> {
                return deleteButton;
            }
            case "backspace" -> {
                return  backspaceButton;
            }
            default -> {
                return buttons.get(key);
            }
        }
    }
}

package views.leftPanel;

import assets.Colors;
import assets.Constants;
import utility.ButtonBuilder;
import utility.DisplayBuilder;
import utility.GridBagConstraintsBuilder;
import utility.PanelBuilder;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class Keyboard {
    final String[] buttonNames = {"7", "8", "9", "4", "5", "6", "1", "2", "3", "0", "00", ","};
    private final JPanel mainPanel = PanelBuilder.createPanel(new GridBagLayout());
//    private final JTextArea topCountDisplay = DisplayBuilder.buildDisplay(Colors.YELLOW_DISPLAY.getColor());
    private final JScrollPane topCountDisplay = DisplayBuilder.buildDisplay(Colors.YELLOW_DISPLAY.getColor());
    private final Map<String, JButton> buttons = new HashMap<>();

    protected Keyboard(){


        GridBagConstraints gbc = GridBagConstraintsBuilder.buildGridBagConstraints(0,0);

        //Adding display to show amount to add or pay
        gbc.gridwidth = 3;
        gbc.weighty = Constants.TOP_COUNT_DISPLAY_HEIGHT.getValue();
        gbc.weightx = Constants.TOP_COUNT_DISPLAY_WIDTH.getValue();
        mainPanel.add(topCountDisplay,gbc);

        //Making and Adding buttons
        gbc.gridwidth = 1;
        gbc.gridy = 1;
        gbc.gridx = 0;
        gbc.weighty = Constants.KEYBOARD_BUTTON_HEIGHT.getValue();
        gbc.weightx = Constants.KEYBOARD_BUTTON_WIDTH.getValue();
        for (int numberIndex = 0; numberIndex < 12; numberIndex++) {
            JButton[] keypadButtons = new JButton[12];
            keypadButtons[numberIndex] = ButtonBuilder.buildButton(Colors.BLACK_BUTTONS.getColor(),buttonNames[numberIndex]);
            keypadButtons[numberIndex].setForeground(Color.white);
            this.buttons.put(buttonNames[numberIndex], keypadButtons[numberIndex]);
            mainPanel.add(keypadButtons[numberIndex],gbc);

            gbc.gridx++;
            if ((numberIndex + 1) % 3 == 0) {
                gbc.gridx=0;
                gbc.gridy++;
            }
        }

    }
    protected JPanel getMainPanel(){
        return mainPanel;
    }

    public  JButton getButton(String key){
        return this.buttons.get(key);
    }

    public void updateTopCountDisplay(String newAmount){
        JTextArea display = (JTextArea) this.topCountDisplay.getViewport().getView();
        display.setText(newAmount);
    }
}

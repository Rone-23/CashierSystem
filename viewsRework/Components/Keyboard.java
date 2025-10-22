package viewsRework.Components;

import assets.Colors;
import utility.ButtonBuilder;
import utility.GridBagConstraintsBuilder;
import utility.PanelBuilder;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class Keyboard extends JPanel{
    final String[] buttonNames = {"7", "8", "9", "4", "5", "6", "1", "2", "3", "0", "00", ","};
//    private final JPanel mainPanel = PanelBuilder.createPanel(new GridBagLayout());
    private final JPanel topPanel = PanelBuilder.createPanel(new GridBagLayout());
    private final JPanel bottomPanel = PanelBuilder.createPanel(new GridBagLayout());
    private final Map<String, JButton> buttons = new HashMap<>();
    private final Dimension numberButtonDimensions = new Dimension(183,136);
    private final Dimension utilityButtonDimensions = new Dimension(280,136);

    public Keyboard(){
        topPanel.setOpaque(false);
        bottomPanel.setOpaque(false);

        setOpaque(false);
        Color color = Colors.BUTTON_LIGHT_BLUE.getColor();
        GridBagConstraints gbc = GridBagConstraintsBuilder.buildGridBagConstraints(0,0);

        //Adding delete button
        gbc.gridwidth = 1;
        JButton delete = ButtonBuilder.buildChonkyButton("Delete", color);
        delete.setPreferredSize(utilityButtonDimensions);
        topPanel.add(delete,gbc);

        //Adding delete button
        gbc.gridwidth = 1;
        gbc.gridx = 1;
        JButton backspace = ButtonBuilder.buildChonkyButton("Backspace", color);
        backspace.setPreferredSize(utilityButtonDimensions);
        topPanel.add(backspace,gbc);

        //Making and Adding buttons
        gbc.gridwidth = 1;
        gbc.gridy = 1;
        gbc.gridx = 0;
        for (int numberIndex = 0; numberIndex < 12; numberIndex++) {
            JButton[] keypadButtons = new JButton[12];
            keypadButtons[numberIndex] = ButtonBuilder.buildChonkyButton(buttonNames[numberIndex], color);
            keypadButtons[numberIndex].setPreferredSize(numberButtonDimensions);
            this.buttons.put(buttonNames[numberIndex], keypadButtons[numberIndex]);
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
        return this.buttons.get(key);
    }
}

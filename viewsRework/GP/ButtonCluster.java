package viewsRework.GP;

import utility.ButtonBuilder;
import utility.GridBagConstraintsBuilder;
import utility.PanelBuilder;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class ButtonCluster {
    final String[] buttonNames = {"Artikle", "Posledna polozka", "Storno", "Hotovost", "Karta", "Stravenky", "Poukazky"};
    private final JPanel mainPanel = PanelBuilder.createPanel(new GridBagLayout());
    private final Map<String, JButton> buttons = new HashMap<>();

    public ButtonCluster(){
        GridBagConstraints gbc = GridBagConstraintsBuilder.buildGridBagConstraints(0,0);

        //Making and Adding buttons
        gbc.gridwidth = 1;
        for (int numberIndex = 0; numberIndex < 7; numberIndex++) {
            JButton[] keypadButtons = new JButton[7];
            keypadButtons[numberIndex] = ButtonBuilder.buildChonkyButton(buttonNames[numberIndex]);
            this.buttons.put(buttonNames[numberIndex], keypadButtons[numberIndex]);
            mainPanel.add(keypadButtons[numberIndex],gbc);

            gbc.gridy++;
        }
    }

    public JPanel getMainPanel(){
        return mainPanel;
    }

    public  JButton getButton(String key){
        return this.buttons.get(key);
    }
}

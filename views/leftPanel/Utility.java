package views.leftPanel;

import assets.Colors;
import assets.Constants;
import utility.ButtonBuilder;
import utility.GridBagConstraintsBuilder;
import utility.PanelBuilder;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class Utility {
    final String[] buttonNames = {"<-BKSP", "C/ Zmaza", "Množstvo", "Tlač Dane"};
    private final JPanel mainPanel = PanelBuilder.createPanel(new GridBagLayout());
    private final Map<String, JButton> buttons = new HashMap<>();

    public Utility(){
        GridBagConstraints gbc = GridBagConstraintsBuilder.buildGridBagConstraints(0,0);

        //Making and Adding buttons
        gbc.gridwidth = 1;
        gbc.gridy = 0;
        gbc.gridx = 0;
        gbc.weighty = Constants.UTILITY_BUTTON_HEIGHT.getValue();
        gbc.weightx = Constants.UTILITY_BUTTON_WIDTH.getValue();
        for (int numberIndex = 0; numberIndex < 4; numberIndex++) {
            JButton[] utilityButtons = new JButton[4];
            utilityButtons[numberIndex] = ButtonBuilder.buildButton(Colors.DEFAULT_BUTTON.getColor(),buttonNames[numberIndex]);
            this.buttons.put(buttonNames[numberIndex], utilityButtons[numberIndex]);

            mainPanel.add(utilityButtons[numberIndex],gbc);
            gbc.gridy++;
        }

    }
    public JPanel getMainPanel(){
        return mainPanel;
    }

    public JButton getButton(String key) {
        return this.buttons.get(key);
    }
}

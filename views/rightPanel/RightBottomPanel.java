package views.rightPanel;

import assets.Colors;
import assets.Constants;
import utility.ButtonBuilder;
import utility.GridBagConstraintsBuilder;
import utility.PanelBuilder;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class RightBottomPanel {
    final String[] keypadNames = {};
    private final JPanel mainPanel = PanelBuilder.createPanel(new GridBagLayout());
    private final JButton[] actionButtons = new JButton[25];
    private Map<String, JButton> buttons = new HashMap<>();
    private final String[] buttonNames = {
            "ODHLÁSENIE",
            "PAUZA",
            "Mobilný",
            "MASO",
            "OSTATNE",
            "PREDAJ POUKAZOK",
            "VRATKA",
            "",
            "LAHODKY",
            "OVOCIE",
            "KRATKE CISLO",
            "STORNO",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "VKLAD",
            "",
            "PAUZA NAKUP",
            "CISLO ZAKAZNIKA",
            "",
    };

    protected RightBottomPanel(){
        GridBagConstraints gbc = GridBagConstraintsBuilder.buildGridBagConstraints(0,0);

        //Making and Adding buttons
        gbc.gridwidth = 1;
        gbc.weighty = Constants.BUTTONS_FOR_USE_HEIGHT.getValue();
        gbc.weightx = Constants.BUTTONS_FOR_USE_WIDTH.getValue();
        for (int numberIndex = 0; numberIndex < 25; numberIndex++) {
            this.actionButtons[numberIndex] = ButtonBuilder.buildButton(Colors.DEFAULT_BUTTON.getColor(), buttonNames[numberIndex]);
            this.buttons.put(buttonNames[numberIndex], this.actionButtons[numberIndex]);
            mainPanel.add(actionButtons[numberIndex],gbc);
            gbc.gridx++;
            if ((numberIndex + 1) % 5 == 0) {
                gbc.gridx=0;
                gbc.gridy++;
            }
        }
    }
    public JPanel getMainPanel(){
        return mainPanel;
    }

    public JButton getButton(String key){
        return this.buttons.get(key);
    }
}

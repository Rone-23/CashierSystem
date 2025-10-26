package viewsRework.Components;

import assets.Colors;
import utility.ButtonBuilder;
import utility.GridBagConstraintsBuilder;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class ButtonCluster extends JPanel{
    final String[] buttonNames = {"Artikle", "Posledna polozka", "Storno", "Hotovost", "Karta", "Stravenky", "Poukazky"};
    private final Map<String, JButton> buttons = new HashMap<>();

    public ButtonCluster(){
        setOpaque(false);
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = GridBagConstraintsBuilder.buildGridBagConstraints(0,0);

        //Making and Adding buttons
        for( String name : buttonNames){
            JButton button = ButtonBuilder.buildChonkyButton(name,  Colors.DEFAULT_BLUE.getColor());
            buttons.put(name.toLowerCase(), button);
            add(button,gbc);
            gbc.gridy++;
        }
    }

    public  JButton getButton(String key){
        return buttons.get(key.toLowerCase());
    }
}

package views.Components;

import assets.ButtonSet;
import assets.Constants;
import utility.ButtonBuilder;
import utility.GridBagConstraintsBuilder;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class ButtonCluster extends JPanel{
    private final Map<String, JButton> buttons = new HashMap<>();

    public ButtonCluster(ButtonSet.ButtonLabel[] buttonLabels){
        setOpaque(false);
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = GridBagConstraintsBuilder.buildGridBagConstraints(0,0);

        //Making and Adding buttons
        for( ButtonSet.ButtonLabel label : buttonLabels){
            JButton button = ButtonBuilder.buildChonkyButton(label.toString(), label.getColor());
            buttons.put(label.toString().toLowerCase(), button);
            add(button,gbc);
            gbc.gridy++;
        }
    }

    public ButtonCluster(ButtonSet.ButtonLabel[] buttonLabels, Constants constant){
        switch (constant){
            case HORIZONTAL -> {
                setOpaque(false);
                setLayout(new GridBagLayout());
                GridBagConstraints gbc = GridBagConstraintsBuilder.buildGridBagConstraints(0,0);

                //Making and Adding buttons
                for( ButtonSet.ButtonLabel label : buttonLabels){
                    JButton button = ButtonBuilder.buildChonkyButton(label.toString(), label.getColor());
                    buttons.put(label.toString().toLowerCase(), button);
                    add(button,gbc);
                    gbc.gridx++;
                }
            }
            case VERTICAL -> {
                 setOpaque(false);
                 setLayout(new GridBagLayout());
                 GridBagConstraints gbc = GridBagConstraintsBuilder.buildGridBagConstraints(0,0);

                 //Making and Adding buttons
                 for( ButtonSet.ButtonLabel label : buttonLabels){
                     JButton button = ButtonBuilder.buildChonkyButton(label.toString(), label.getColor());
                     buttons.put(label.toString().toLowerCase(), button);
                     add(button,gbc);
                     gbc.gridy++;
                 }
            }
        }
    }

    public JButton getButton(String key){
        return buttons.get(key.toLowerCase());
    }

    public void replaceButton(String name, String replaceName){
        try {
            JButton button = getButton(name);
            button.setName(replaceName);
            button.setText(replaceName);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void replaceButton(String[] names, String[] replaceNames){
        int i = 0;
        for(String name : names){
            try {
                JButton button = getButton(name);
                button.setName(replaceNames[i]);
                button.setText(replaceNames[i]);
                i++;
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }



}

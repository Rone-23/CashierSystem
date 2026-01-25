package views.Components;

import assets.Colors;
import assets.Constants;
import utility.ButtonBuilder;
import utility.GridBagConstraintsBuilder;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class ButtonCluster extends JPanel{
    String[] buttonNames;
//    private static final Map<String, JButton> buttonsMemory = new HashMap<>();
    private final Map<String, JButton> buttons = new HashMap<>()
//    {
//        @Override
//        public JButton put(String key, JButton value){
//            if (buttonsMemory.containsKey(key)) {
//                return super.put(key, buttonsMemory.get(key));
//            } else {
//                buttonsMemory.put(key, value);
//                return super.put(key, value);
//            }
//        }
//    }
    ;

    public ButtonCluster(String[] buttonNames){
        this.buttonNames =  buttonNames;
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

    public ButtonCluster(String[] buttonNames, Constants constant){
        switch (constant){
            case HORIZONTAL -> {
                this.buttonNames =  buttonNames;
                setOpaque(false);
                setLayout(new GridBagLayout());
                GridBagConstraints gbc = GridBagConstraintsBuilder.buildGridBagConstraints(0,0);

                //Making and Adding buttons
                for( String name : buttonNames){
                    JButton button = ButtonBuilder.buildChonkyButton(name,  Colors.DEFAULT_BLUE.getColor());
                    buttons.put(name.toLowerCase(), button);
                    add(button,gbc);
                    gbc.gridx++;
                }
            }
            case VERTICAL -> {
                 this.buttonNames =  buttonNames;
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

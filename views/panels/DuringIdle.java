package views.panels;

import assets.ButtonSet;
import assets.Colors;
import utility.GridBagConstraintsBuilder;
import views.Components.ButtonCluster;

import javax.swing.*;
import java.awt.*;
import java.lang.reflect.Array;
import java.util.ArrayList;

public class DuringIdle extends JPanel implements ButtonFoundable{
    ButtonCluster buttonCluster;
    public DuringIdle(){
        GridBagConstraints gbc = GridBagConstraintsBuilder.buildGridBagConstraints(1,1);
        setLayout(new GridBagLayout());
        setBackground(Colors.BACKGROUND_WHITE.getColor());

        JPanel fillerPanel = new JPanel();
        fillerPanel.setOpaque(false);

        buttonCluster = new ButtonCluster(ButtonSet.IDLE_UTILITY_NAMES.getStringLabels());

        add(fillerPanel, gbc);
        gbc.weightx=0.2;
        gbc.gridx++;
        add(buttonCluster, gbc);

    }


    @Override
    public JButton getButton(String key) {
        for(Component c : buttonCluster.getComponents()){
            if(c instanceof JButton && c.getName().equals(key.toLowerCase())){
                return (JButton) c;
            }
        }
        throw new ArrayIndexOutOfBoundsException();
    }

    @Override
    public JButton[] getButtons(String key){
        ArrayList<JButton> jButtons = new ArrayList<>();

        for(Component c : buttonCluster.getComponents()){
            if(c instanceof JButton && c.getName().equals(key.toLowerCase())){
                jButtons.add((JButton) c);
            }
        }
        if(!jButtons.isEmpty()){
            return jButtons.toArray(new JButton[0]);
        }
        throw new ArrayIndexOutOfBoundsException();

    }
}

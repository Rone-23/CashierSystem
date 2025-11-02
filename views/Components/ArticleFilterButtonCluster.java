package views.Components;

import assets.Colors;
import assets.Constants;
import utility.ButtonBuilder;
import utility.GridBagConstraintsBuilder;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class ArticleFilterButtonCluster extends JPanel {
    private final String[] buttonNamesMainFilter = {"Oblubene", "Ovocie", "Zelenina", "Pecivo", "Instorky"};

    private final JPanel mainSecondaryPanel = new JPanel();
    private final GridBagConstraints gbcSecondaryFilterPanel = GridBagConstraintsBuilder.buildGridBagConstraints(1,1);
    private final GridBagConstraints gbcMainPanel = GridBagConstraintsBuilder.buildGridBagConstraints(1,1);
    private final JButton leftButton = ButtonBuilder.buildChonkyArrowButton( Colors.BUTTON_LIGHT_BLUE.getColor(),Constants.LEFT);
    private final JButton rightButton = ButtonBuilder.buildChonkyArrowButton( Colors.BUTTON_LIGHT_BLUE.getColor(),Constants.RIGHT);
    private final Dimension buttonDimensions = new Dimension(183,136);
    private final List<JButton> mainFilterButtons = new ArrayList<>();
    private final List<String> secondaryFilterButtonsNames = new ArrayList<>();
    private final List<JButton> secondaryFilterButtons = new ArrayList<>();

    private int lastUsedPosition = 3;

    public ArticleFilterButtonCluster(){
        setOpaque(false);
        setLayout(new GridBagLayout());
        add(makeMainFilterPanel(),gbcMainPanel);
        gbcMainPanel.gridy++;
        add(makeSecondaryFilterPanel(),gbcMainPanel);

    }

    private JPanel makeMainFilterPanel(){
        final JPanel main = new JPanel();
        main.setLayout(new GridBagLayout());
        main.setOpaque(false);
        final GridBagConstraints gbc = GridBagConstraintsBuilder.buildGridBagConstraints();

        for(String name : buttonNamesMainFilter){
            JButton button = ButtonBuilder.buildChonkyButton(name, Colors.BUTTON_LIGHT_BLUE.getColor());
            button.setPreferredSize(buttonDimensions);
            main.add(button,gbc);
            mainFilterButtons.add(button);
        }
        return main;
    }

    private JPanel makeSecondaryFilterPanel(){
        JPanel main = mainSecondaryPanel;
        main.removeAll();
        GridBagConstraints gbc = gbcSecondaryFilterPanel;
        String[] buttonNamesSecondaryFilter = secondaryFilterButtonsNames.toArray(new String[0]);
        main.setOpaque(false);
        main.setLayout(new GridBagLayout());
        if(buttonNamesSecondaryFilter.length==5){

            for(String name : buttonNamesSecondaryFilter){
                JButton button = ButtonBuilder.buildChonkyButton(name, Colors.BUTTON_LIGHT_BLUE.getColor());
                button.setPreferredSize(buttonDimensions);
                secondaryFilterButtons.add(button);
                main.add(button,gbc);
                gbc.gridx++;
            }

        }else if(buttonNamesSecondaryFilter.length<5){

            int buttonCounter = 0;
            for(String name : buttonNamesSecondaryFilter){
                JButton button = ButtonBuilder.buildChonkyButton(name, Colors.BUTTON_LIGHT_BLUE.getColor());
                button.setPreferredSize(buttonDimensions);
                secondaryFilterButtons.add(button);
                main.add(button,gbc);
                buttonCounter++;
                gbc.gridx++;
            }
            for (int i = 0; i < (5-buttonCounter); i++) {
                JButton button = ButtonBuilder.buildChonkyButtonDisabled(Colors.BUTTON_LIGHT_BLUE.getColor());
                secondaryFilterButtons.add(button);
                button.setPreferredSize(buttonDimensions);
                main.add(button,gbc);
                gbc.gridx++;
            }

        } else {

            gbc.weightx=0;
            main.add(leftButton,gbc);

            gbc.gridx=5;
            main.add(rightButton,gbc);

            gbc.weightx=1;
            gbc.gridx=2;
            for (lastUsedPosition = 0; lastUsedPosition < 3; lastUsedPosition++) {
                JButton button = ButtonBuilder.buildChonkyButton(buttonNamesSecondaryFilter[lastUsedPosition], Colors.BUTTON_LIGHT_BLUE.getColor());
                button.setPreferredSize(buttonDimensions);
                secondaryFilterButtons.add(button);
                main.add(button,gbc);
                gbc.gridx++;
            }
        }
        return main;
    }

    public void scrollRight(){
        leftButton.setEnabled(true);
        JPanel main = mainSecondaryPanel;
        GridBagConstraints gbc = gbcSecondaryFilterPanel;
        String[] buttonNamesSecondaryFilter = secondaryFilterButtonsNames.toArray(new String[0]);

        gbc.gridx = 2;
        try {
            for (int newPosition=lastUsedPosition; newPosition < lastUsedPosition+3; newPosition++) {
                JButton button = ButtonBuilder.buildChonkyButton(buttonNamesSecondaryFilter[newPosition], Colors.BUTTON_LIGHT_BLUE.getColor());
                main.remove(main.getComponent(2));
                main.add(button,gbc);
                gbc.gridx++;
            }
        } catch (IndexOutOfBoundsException e) {
            main.remove(main.getComponent(2));
            main.add(ButtonBuilder.buildChonkyButtonDisabled(Colors.BUTTON_LIGHT_BLUE.getColor()),gbc);
            rightButton.setEnabled(false);
        }
        lastUsedPosition+=3;
    }

    public void scrollLeft(){
        rightButton.setEnabled(true);
        JPanel main = mainSecondaryPanel;
        GridBagConstraints gbc = gbcSecondaryFilterPanel;
        String[] buttonNamesSecondaryFilter = secondaryFilterButtonsNames.toArray(new String[0]);

        gbc.gridx = 4;
            main.remove(main.getComponent(2));
            main.remove(main.getComponent(2));
            main.remove(main.getComponent(2));
            for (int newPosition=lastUsedPosition; newPosition > lastUsedPosition-3; newPosition--) {
                JButton button = ButtonBuilder.buildChonkyButton(buttonNamesSecondaryFilter[newPosition-4], Colors.BUTTON_LIGHT_BLUE.getColor());
                main.add(button,gbc);
                gbc.gridx--;
            }

        lastUsedPosition-=3;
        if(lastUsedPosition==3){
            leftButton.setEnabled(false);
        }
    }

    public void setButtonNamesSecondaryFilter(String[] names){
        secondaryFilterButtonsNames.clear();
        secondaryFilterButtonsNames.addAll(List.of(names));
        add(makeSecondaryFilterPanel(),gbcMainPanel);
        revalidate();
    }
    public JButton getRightButton(){return rightButton;}
    public JButton getLeftButton(){return leftButton;}
    public JButton[] getMainFilterButtons(){return mainFilterButtons.toArray(new JButton[0]);}
    public JButton[] getSecondaryFilterButtons(){
        return secondaryFilterButtons.toArray(new JButton[0]);
    }

}

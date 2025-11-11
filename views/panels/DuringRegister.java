package views.panels;

import assets.Colors;
import assets.Constants;
import utility.GridBagConstraintsBuilder;
import views.Components.ButtonCluster;
import views.Components.Display;
import views.Components.DisplayScrollableItems;
import views.Components.Keyboard;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.border.MatteBorder;
import java.awt.*;

public class DuringRegister extends JPanel {

    private final Dimension dimension = new Dimension(500,114);

    private final DisplayScrollableItems displayScrollableItems = new DisplayScrollableItems();
    private final Display displayTotal = new Display(Constants.TOTAL);
    private final Display displayTop = new Display(Constants.WEIGHT);
    private final Keyboard keyboard = new Keyboard();
    private final String[] buttonNames = {"Artikle", "Posledna polozka", "Storno", "Hotovost", "Karta", "Stravenky", "Poukazky"};
    private final ButtonCluster buttonCluster = new ButtonCluster(buttonNames);



    public DuringRegister(){
        setLayout(new GridBagLayout());
        JPanel leftPanel = createLeftPanel();
        JPanel middlePanel = createMiddlePanel();
        JPanel rightPanel = createRightPanel();

        //Assign to DuringRegister panels with components
        GridBagConstraints gbc = GridBagConstraintsBuilder.buildGridBagConstraints(1,1);
        add(leftPanel, gbc);
        gbc.gridx++;
        gbc.weightx=0.3;
        add(middlePanel, gbc);
        gbc.gridx++;
        gbc.weightx=0.3;
        add(rightPanel, gbc);

    }

    private JPanel createLeftPanel(){
        final GridBagConstraints gbc = GridBagConstraintsBuilder.buildGridBagConstraints(1,1);
        final JPanel leftPanel = new JPanel();

        Border rightBorder = new MatteBorder(
                0,
                0,
                0,
                3,
                Colors.BUTTON_LIGHT_BLUE.getColor()
        );
        leftPanel.setBorder(rightBorder);

        leftPanel.setLayout(new GridBagLayout());
        leftPanel.setBackground(Colors.BACKGROUND_WHITE.getColor());

        leftPanel.add(displayScrollableItems, gbc);

        gbc.weighty = 0;
        gbc.weightx = 0;
        gbc.gridy++;
        displayTotal.setPreferredSize(dimension);
        leftPanel.add(displayTotal, gbc);


        return leftPanel;
    }

    private JPanel createMiddlePanel(){
        final GridBagConstraints gbc = GridBagConstraintsBuilder.buildGridBagConstraints();
        final JPanel middlePanel = new JPanel();

        middlePanel.setBorder(new EmptyBorder(20,0,20,0));
        middlePanel.setLayout(new GridBagLayout());
        middlePanel.setBackground(Colors.BACKGROUND_GRAY.getColor());

        gbc.gridx=0;

        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 0;
        gbc.weighty = 0;
        gbc.gridy=0;
        displayTop.setPreferredSize(dimension);
        middlePanel.add(displayTop,gbc);


        gbc.gridy=1;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        JPanel fillerPanel = new JPanel();
        fillerPanel.setOpaque(false);
        middlePanel.add(fillerPanel,gbc);


        gbc.weightx = 0;
        gbc.weighty = 0;
        gbc.gridy=2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        middlePanel.add(keyboard,gbc);

        return middlePanel;
    }

    private JPanel createRightPanel(){
        final GridBagConstraints gbc = GridBagConstraintsBuilder.buildGridBagConstraints();
        final JPanel rightPanel = new JPanel();

        rightPanel.setLayout(new GridBagLayout());
        rightPanel.setBackground(Colors.BACKGROUND_GRAY.getColor());

        gbc.weightx=1;
        gbc.weighty=1;

        gbc.gridy=0;
        gbc.gridx=0;

        rightPanel.add(buttonCluster,gbc);
        return rightPanel;
    }


    public Keyboard getKeyboard(){return keyboard;}
    public DisplayScrollableItems getDisplayScrollableItems(){return displayScrollableItems;}
    public Display getDisplayTotal(){return displayTotal;}
    public Display getDisplayTop(){return displayTop;}
    public ButtonCluster getButtonCluster(){return buttonCluster;}


}

package viewsRework.panels;

import assets.Colors;
import utility.GridBagConstraintsBuilder;
import viewsRework.GP.ButtonCluster;
import viewsRework.GP.Display;
import viewsRework.GP.DisplayScrollable;
import viewsRework.GP.Keyboard;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

public class DuringRegister extends JPanel {

//    private final JPanel mainPanel = new JPanel();
    private final Display display1 = new Display();
    private final Display display2 = new Display();
    private final Keyboard keyboard = new Keyboard();
    private final DisplayScrollable displayScrollable = new DisplayScrollable();
    private final ButtonCluster buttonCluster = new ButtonCluster();

    private final JPanel leftPanel = new JPanel();
    private final JPanel middlePanel = new JPanel();
    private final JPanel rightPanel = new JPanel();
    private final GridBagConstraints gbc = GridBagConstraintsBuilder.buildGridBagConstraints();


    public DuringRegister(){
        setLayout(new GridBagLayout());

        leftPanel.setLayout(new GridBagLayout());
        middlePanel.setLayout(new GridBagLayout());
        rightPanel.setLayout(new GridBagLayout());

//        leftPanel.setBackground(Colors.BACKGROUND_WHITE.getColor());
//        middlePanel.setBackground(Colors.BACKGROUND_GRAY.getColor());
//        rightPanel.setBackground(Colors.BACKGROUND_GRAY.getColor());

        leftPanel.setBackground(Color.yellow);
        middlePanel.setBackground(Color.blue);
        rightPanel.setBackground(Color.red);


        gbc.weightx = 1;

        //Left panel
        gbc.weighty = 1;
        leftPanel.add(displayScrollable, gbc);
        gbc.weighty = 0;
        gbc.weightx = 1;
        gbc.gridy=1;
        leftPanel.add(display1, gbc);


        //Middle panel
        gbc.weightx = 0;
        gbc.gridy=0;
        middlePanel.add(display2,gbc);
        gbc.gridy=1;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        middlePanel.add(new JPanel());
        gbc.weightx = 0;
        gbc.weighty = 0;
        gbc.gridy=2;
        gbc.fill = GridBagConstraints.NONE;
        middlePanel.add(keyboard,gbc);

        //Right panel
        gbc.gridy=0;
        rightPanel.add(buttonCluster,gbc);

        //Assign to DuringRegister panels with components
        GridBagConstraints gbc2 = GridBagConstraintsBuilder.buildGridBagConstraints();
        add(leftPanel, gbc2);
        add(middlePanel, gbc2);
        add(rightPanel, gbc2);

    }

    public JComponent getKeyboard(){return keyboard;};
    public JComponent getDisplayScrollable(){return displayScrollable;};
}

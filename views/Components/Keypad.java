package views.Components;

import assets.ButtonSet;
import utility.ButtonBuilder;
import utility.GridBagConstraintsBuilder;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;

public class Keypad extends JPanel {
    final String[] buttonNames = {"7", "8", "9", "4", "5", "6", "1", "2", "3", "0", "00", ","};
    private final JPanel topPanel = new JPanel();
    private final JPanel bottomPanel = new JPanel();
    private final JButton deleteButton;
    private final JButton backspaceButton;

    public Keypad() {
        topPanel.setLayout(new GridLayout(1, 2));
        bottomPanel.setLayout(new GridLayout(4, 3));

        topPanel.setOpaque(false);
        bottomPanel.setOpaque(false);
        setOpaque(false);

        deleteButton = ButtonBuilder.buildChonkyButton(ButtonSet.ButtonLabel.DELETE.toString(), ButtonSet.ButtonLabel.DELETE.getColor());
        topPanel.add(deleteButton);

        backspaceButton = ButtonBuilder.buildChonkyButton(ButtonSet.ButtonLabel.BACKSPACE.toString(), ButtonSet.ButtonLabel.BACKSPACE.getColor());
        topPanel.add(backspaceButton);

        for (int numberIndex = 0; numberIndex < 12; numberIndex++) {
            JButton btn = ButtonBuilder.buildChonkyButton(buttonNames[numberIndex], ButtonSet.ButtonLabel.NUMBERS.getColor());
            bottomPanel.add(btn);
        }

        this.setLayout(new GridBagLayout());

        GridBagConstraints gbcMain = GridBagConstraintsBuilder.buildGridBagConstraints(0, 0);

        gbcMain.weighty = 0.2;
        this.add(topPanel, gbcMain);

        gbcMain.gridy = 1;
        gbcMain.weighty = 0.8;
        this.add(bottomPanel, gbcMain);
    }

    public Component[] getComponentsInside() {
        ArrayList<Component> components = new ArrayList<>();
        components.addAll(Arrays.asList(topPanel.getComponents()));
        components.addAll(Arrays.asList(bottomPanel.getComponents()));
        return components.toArray(new Component[0]);
    }
}
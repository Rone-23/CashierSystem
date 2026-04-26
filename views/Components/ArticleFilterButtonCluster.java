package views.Components;

import assets.Colors;
import assets.Constants;
import assets.Scaler;
import utility.ButtonBuilder;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class ArticleFilterButtonCluster extends JPanel {
    private final String[] buttonNamesMainFilter = {"Oblubene", "Ovocie", "Zelenina", "Pecivo"};

    private final JPanel mainSecondaryPanel = new JPanel();
    private final JButton leftButton = ButtonBuilder.buildChonkyArrowButton(Colors.BUTTON_LIGHT_BLUE, Constants.LEFT);
    private final JButton rightButton = ButtonBuilder.buildChonkyArrowButton(Colors.BUTTON_LIGHT_BLUE, Constants.RIGHT);

    private final List<JButton> mainFilterButtons = new ArrayList<>();
    private final List<String> secondaryFilterButtonsNames = new ArrayList<>();
    private final List<JButton> secondaryFilterButtons = new ArrayList<>();

    private int lastUsedPosition = 3;

    public ArticleFilterButtonCluster() {
        setOpaque(false);
        int gap = Scaler.getPadding(0.005);
        setLayout(new GridLayout(2, 1, 0, gap));
        add(makeMainFilterPanel());
        add(makeSecondaryFilterPanel());
        leftButton.addActionListener(e -> scrollLeft());
        rightButton.addActionListener(e -> scrollRight());
    }

    private JPanel makeMainFilterPanel() {
        JPanel main = new JPanel();
        main.setOpaque(false);

        int gap = Scaler.getPadding(0.005);
        main.setLayout(new GridLayout(1, 5, gap, gap));

        int pad = Scaler.getPadding(0.005);
        main.setBorder(new EmptyBorder(pad, pad, pad, pad));

        for (String name : buttonNamesMainFilter) {
            JButton button = ButtonBuilder.buildChonkyButton(name, Colors.BUTTON_LIGHT_BLUE);
            mainFilterButtons.add(button);
            main.add(button);
        }
        return main;
    }

    private JPanel makeSecondaryFilterPanel() {
        JPanel main = mainSecondaryPanel;
        main.removeAll();
        main.setOpaque(false);

        int gap = Scaler.getPadding(0.005);
        main.setLayout(new GridLayout(1, 5, gap, gap));

        int pad = Scaler.getPadding(0.005);
        main.setBorder(new EmptyBorder(pad, pad, pad, pad));

        String[] names = secondaryFilterButtonsNames.toArray(new String[0]);
        secondaryFilterButtons.clear();

        if (names.length <= 5) {
            for (int i = 0; i < 5; i++) {
                if (i < names.length) {
                    JButton button = ButtonBuilder.buildChonkyButton(names[i], Colors.BUTTON_LIGHT_BLUE);
                    secondaryFilterButtons.add(button);
                    main.add(button);
                } else {
                    JButton disabledBtn = ButtonBuilder.buildChonkyButtonDisabled(Colors.BUTTON_LIGHT_BLUE);
                    secondaryFilterButtons.add(disabledBtn);
                    main.add(disabledBtn);
                }
            }
        }
        else {
            main.add(leftButton);

            for (int i = 0; i < 3; i++) {
                int itemIndex = lastUsedPosition - 3 + i;

                if (itemIndex >= 0 && itemIndex < names.length) {
                    JButton button = ButtonBuilder.buildChonkyButton(names[itemIndex], Colors.BUTTON_LIGHT_BLUE);
                    secondaryFilterButtons.add(button);
                    main.add(button);
                } else {
                    JButton disabledBtn = ButtonBuilder.buildChonkyButtonDisabled(Colors.BUTTON_LIGHT_BLUE);
                    secondaryFilterButtons.add(disabledBtn);
                    main.add(disabledBtn);
                }
            }

            main.add(rightButton);

            leftButton.setEnabled(lastUsedPosition > 3);
            rightButton.setEnabled(lastUsedPosition < names.length);
        }

        return main;
    }

    public void scrollRight() {
        String[] names = secondaryFilterButtonsNames.toArray(new String[0]);
        if (lastUsedPosition < names.length) {
            lastUsedPosition += 3;
            makeSecondaryFilterPanel();
            mainSecondaryPanel.revalidate();
            mainSecondaryPanel.repaint();
        }
    }

    public void scrollLeft() {
        if (lastUsedPosition > 3) {
            lastUsedPosition -= 3;
            makeSecondaryFilterPanel();
            mainSecondaryPanel.revalidate();
            mainSecondaryPanel.repaint();
        }
    }

    public void setButtonNamesSecondaryFilter(String[] names) {
        secondaryFilterButtonsNames.clear();
        secondaryFilterButtonsNames.addAll(List.of(names));

        lastUsedPosition = 3;
        makeSecondaryFilterPanel();
        mainSecondaryPanel.revalidate();
        mainSecondaryPanel.repaint();
    }

    public JButton getRightButton() { return rightButton; }
    public JButton getLeftButton() { return leftButton; }
    public JButton[] getMainFilterButtons() { return mainFilterButtons.toArray(new JButton[0]); }
    public JButton[] getSecondaryFilterButtons() { return secondaryFilterButtons.toArray(new JButton[0]); }
}
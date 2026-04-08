package views.Components;

import assets.Colors;
import services.SQL_Connect;
import utility.ButtonBuilder;
import utility.ColorManipulation;

import javax.swing.*;
import java.awt.*;
import java.util.List;

import static javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER;
import static javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS;

public class Keyboard extends JDialog {
    private final JLabel inputDisplay;
    private final JPanel resultsPanel;
    private String currentSearchText = "";

    public Keyboard(JFrame parent) {
        super(parent, "Kde nájdem položku?", true);
        setSize(800, 600);
        setLocationRelativeTo(parent);
        setLayout(new BorderLayout(10, 10));
        getContentPane().setBackground(Colors.BACKGROUND_GRAY.getColor());
        setUndecorated(true);

        inputDisplay = new JLabel("Začnite písať...", SwingConstants.CENTER);
        inputDisplay.setFont(new Font("Roboto", Font.BOLD, 32));
        inputDisplay.setForeground(Colors.BLACK_TEXT.getColor());
        inputDisplay.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        add(inputDisplay, BorderLayout.NORTH);

        resultsPanel = new JPanel();
        resultsPanel.setLayout(new BoxLayout(resultsPanel, BoxLayout.Y_AXIS));
        resultsPanel.setOpaque(false);

        JScrollPane scrollPane = new JScrollPane(resultsPanel);
        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false);
        scrollPane.setBorder(BorderFactory.createMatteBorder(2, 0, 2, 0, Colors.GRAY.getColor()));
        scrollPane.setVerticalScrollBarPolicy(VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setHorizontalScrollBarPolicy(HORIZONTAL_SCROLLBAR_NEVER);

        JScrollBar verticalScrollBar = scrollPane.getVerticalScrollBar();
        verticalScrollBar.setUI(new CustomScrollBar());
        verticalScrollBar.setPreferredSize(new Dimension(20, 0));
        verticalScrollBar.setOpaque(false);
        verticalScrollBar.setUnitIncrement(20);
        add(scrollPane, BorderLayout.CENTER);

        add(createQwertyKeyboard(), BorderLayout.SOUTH);
    }

    private JPanel createQwertyKeyboard() {
        JPanel kbPanel = new JPanel(new GridBagLayout());
        kbPanel.setOpaque(false);
        kbPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        String[][] keys = {
                {"Q", "W", "E", "R", "T", "Y", "U", "I", "O", "P"},
                {"A", "S", "D", "F", "G", "H", "J", "K", "L"},
                {"Z", "X", "C", "V", "B", "N", "M", "<-"},
                {"Medzera", "Zavrieť"}
        };

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.insets = new Insets(5, 5, 5, 5);

        for (int row = 0; row < keys.length; row++) {
            JPanel rowPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 2, 3));
            rowPanel.setOpaque(false);

            for (String key : keys[row]) {
                JButton btn;


                if (key.equals("Zavrieť")) {
                    btn = ButtonBuilder.buildChonkyButton(key, Colors.DANGER_RED);
                    btn.setPreferredSize(new Dimension(120, 65));
                } else if (key.equals("Medzera")) {
                    btn = ButtonBuilder.buildChonkyButton(key, Colors.BUTTON_LIGHT_BLUE);
                    btn.setPreferredSize(new Dimension(300, 65));
                } else if (key.equals("<-")) {
                    btn = ButtonBuilder.buildChonkyButton(key, Colors.DEFAULT_BLUE);
                    btn.setPreferredSize(new Dimension(70, 70));
                } else {
                    btn = ButtonBuilder.buildChonkyButton(key, Colors.BUTTON_LIGHT_BLUE);
                    btn.setPreferredSize(new Dimension(70, 70));
                }
                btn.setMargin(new Insets(1, 1, 1, 1));
                btn.setFont(new Font("Roboto", Font.BOLD, 22));
                btn.addActionListener(e -> handleKeyPress(key));
                rowPanel.add(btn);
            }
            gbc.gridy = row;
            kbPanel.add(rowPanel, gbc);
        }
        return kbPanel;
    }

    private void handleKeyPress(String key) {
        if (key.equals("Zavrieť")) {
            dispose();
            return;
        } else if (key.equals("<-")) {
            if (!currentSearchText.isEmpty()) {
                currentSearchText = currentSearchText.substring(0, currentSearchText.length() - 1);
            }
        } else if (key.equals("Medzera")) {
            currentSearchText += " ";
        } else {
            currentSearchText += key;
        }

        if (currentSearchText.isEmpty()) {
            inputDisplay.setText("Začnite písať...");
            resultsPanel.removeAll();
        } else {
            inputDisplay.setText(currentSearchText);
            updateSearchResults();
        }

        resultsPanel.revalidate();
        resultsPanel.repaint();
    }

    private void updateSearchResults() {
        resultsPanel.removeAll();
        List<String> paths = SQL_Connect.getInstance().searchItemPathsByName(currentSearchText);

        if (paths.isEmpty()) {
            JLabel noResult = new JLabel("Nenašli sa žiadne položky.");
            noResult.setFont(new Font("Roboto", Font.BOLD, 18));
            resultsPanel.add(noResult);
            return;
        }

        for (String pathInfo : paths) {
            JLabel label = new JLabel(pathInfo);
            label.setFont(new Font("Roboto", Font.BOLD, 22));
            label.setForeground(Colors.BLACK_TEXT.getColor());
            label.setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20));
            resultsPanel.add(label);
        }
    }
}
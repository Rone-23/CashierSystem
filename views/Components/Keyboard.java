package views.Components;

import assets.Colors;
import assets.Scaler;
import services.SQL_Connect;
import utility.ButtonBuilder;

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
        setSize(Scaler.getDimension(0.6, 0.7));
        setLocationRelativeTo(parent);
        int gap = Scaler.getPadding(0.01);
        setLayout(new BorderLayout(gap, gap));
        getContentPane().setBackground(Colors.BACKGROUND_GRAY.getColor());
        setUndecorated(true);

        inputDisplay = new JLabel("Začnite písať...", SwingConstants.CENTER);
        inputDisplay.setFont(Scaler.getFont(0.04, Font.BOLD));
        inputDisplay.setForeground(Colors.BLACK_TEXT.getColor());
        int pad20 = Scaler.getPadding(0.02);
        inputDisplay.setBorder(BorderFactory.createEmptyBorder(pad20, pad20, pad20, pad20));
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
        int guaranteedWidth = assets.Scaler.getDimension(0.15, 0).width;
        scrollPane.setPreferredSize(new Dimension(guaranteedWidth, 0));
        scrollPane.setMinimumSize(new Dimension(guaranteedWidth, 0));

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
            Dimension uniformButtonSize = Scaler.getDimension(0.045, 0.07);
            Dimension utilityButtonSize = Scaler.getDimension(0.1, 0.07);
            JPanel rowPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 2, 3));
            rowPanel.setOpaque(false);

            for (String key : keys[row]) {
                JButton btn;


                if (key.equals("Zavrieť")) {
                    btn = ButtonBuilder.buildChonkyButton(key, Colors.DANGER_RED);
                    btn.setPreferredSize(utilityButtonSize);
                    btn.setFont(Scaler.getFont(0.03,Font.BOLD));
                } else if (key.equals("Medzera")) {
                    btn = ButtonBuilder.buildChonkyButton(key, Colors.BUTTON_LIGHT_BLUE);
                    btn.setPreferredSize(utilityButtonSize);
                    btn.setFont(Scaler.getFont(0.03,Font.BOLD));
                } else if (key.equals("<-")) {
                    btn = ButtonBuilder.buildChonkyButton(key, Colors.DEFAULT_BLUE);
                    btn.setPreferredSize(utilityButtonSize);
                    btn.setFont(Scaler.getFont(0.03,Font.BOLD));
                } else {
                    btn = ButtonBuilder.buildChonkyButton(key, Colors.BUTTON_LIGHT_BLUE);
                    btn.setPreferredSize(uniformButtonSize);
                    btn.setFont(Scaler.getFont(0.04,Font.BOLD));
                }
                btn.setMargin(new Insets(1, 1, 1, 1));
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
            noResult.setFont(Scaler.getFont(0.02, Font.BOLD));
            resultsPanel.add(noResult);
            return;
        }

        int pad10 = Scaler.getPadding(0.01);
        for (String pathInfo : paths) {
            JLabel label = new JLabel(pathInfo);
            label.setFont(Scaler.getFont(0.025, Font.BOLD));
            label.setForeground(Colors.BLACK_TEXT.getColor());

            label.setBorder(BorderFactory.createEmptyBorder(pad10, pad10, pad10, pad10));
            resultsPanel.add(label);
        }
    }
}
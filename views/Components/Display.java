package views.Components;

import assets.Colors;
import assets.Constants;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.geom.RoundRectangle2D;

public class Display extends JPanel {
    private String[] text = {"0.00", "1"};
    private final Constants displayType;

    private JLabel topTitleLabel;
    private JLabel topValueLabel;
    private JLabel bottomTitleLabel;
    private JLabel bottomValueLabel;

    public Display(Constants displayType) {
        this.displayType = displayType;
        initUI();
        setSuggestionText(displayType);
    }

    public Display(Constants displayType, String textToDisplay) {
        this.displayType = displayType;
        initUI();
        setTopText(textToDisplay);
    }

    private void initUI() {
        setOpaque(false);
        setLayout(new GridLayout(displayType == Constants.SPLIT ? 2 : 1, 1));
        setPreferredSize(new Dimension(400, 100));

        setBorder(BorderFactory.createEmptyBorder(10, 35, 10, 35));

        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setOpaque(false);

        topTitleLabel = new JLabel("", SwingConstants.LEFT);
        topTitleLabel.setForeground(Colors.BLACK_TEXT.getColor());

        topValueLabel = new JLabel(formatValue(text[0], true), SwingConstants.RIGHT);
        topValueLabel.setForeground(Colors.BLACK_TEXT.getColor());

        topPanel.add(topTitleLabel, BorderLayout.WEST);
        topPanel.add(topValueLabel, BorderLayout.EAST);
        add(topPanel);

        if (displayType == Constants.SPLIT) {
            JPanel bottomPanel = new JPanel(new BorderLayout());
            bottomPanel.setOpaque(false);

            bottomTitleLabel = new JLabel("", SwingConstants.LEFT);
            bottomTitleLabel.setForeground(Colors.BLACK_TEXT.getColor());

            bottomValueLabel = new JLabel(formatValue(text[1], false), SwingConstants.RIGHT);
            bottomValueLabel.setForeground(Colors.BLACK_TEXT.getColor());

            bottomPanel.add(bottomTitleLabel, BorderLayout.WEST);
            bottomPanel.add(bottomValueLabel, BorderLayout.EAST);
            add(bottomPanel);
        }

        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                updateFonts();
            }
        });
    }

    private void updateFonts() {
        int h = getHeight();
        int panelCount = displayType == Constants.SPLIT ? 2 : 1;
        int sectionHeight = h / panelCount;

        Font titleFont = getResponsiveFont(sectionHeight * 0.40f, 12, 50);
        Font valueFont = getResponsiveFont(sectionHeight * 0.60f, 30, 60);

        topTitleLabel.setFont(titleFont);
        topValueLabel.setFont(valueFont);

        if (displayType == Constants.SPLIT) {
            bottomTitleLabel.setFont(titleFont);
            bottomValueLabel.setFont(valueFont);
        }
    }

    private Font getResponsiveFont(float targetSize, int minSize, int maxSize) {
        int size = (int) targetSize;
        size = Math.max(minSize, Math.min(maxSize, size));
        return new Font("Roboto", Font.BOLD, size);
    }

    private String formatValue(String rawValue, boolean isTopRow) {
        if (displayType == Constants.TOTAL) return rawValue + " €";
        if (displayType == Constants.WEIGHT) return rawValue + " ks";
        if (displayType == Constants.SPLIT) {
            return isTopRow ? rawValue + " €" : rawValue + " ks";
        }
        return rawValue;
    }

    public void setTopText(String text) {
        topTitleLabel.setText(text);
    }

    public void setText(String text) {
        this.text[0] = text;
        topValueLabel.setText(formatValue(text, true));
    }

    public void setText(String[] text) {
        this.text = text;
        topValueLabel.setText(formatValue(text[0], true));
        if (displayType == Constants.SPLIT && text.length > 1) {
            bottomValueLabel.setText(formatValue(text[1], false));
        }
    }

    public void setSuggestionText(Constants suggestionText) {
        switch (suggestionText) {
            case TOTAL -> topTitleLabel.setText("Cena");
            case WEIGHT -> topTitleLabel.setText("Množstvo");
            case SPLIT -> {
                topTitleLabel.setText("Cena");
                if (bottomTitleLabel != null) bottomTitleLabel.setText("Množstvo");
            }
            case RECEIPT -> topTitleLabel.setText("Číslo bloku");
            case CUSTOMER -> topTitleLabel.setText("Zadajte ID");
            case CODE -> topTitleLabel.setText("Zadajte kód");
        }
    }

    public Constants getDisplayType() {
        return displayType;
    }

    public String[] getTextArray() {
        return text;
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        int arc = getHeight();
        int inset = 5;
        Shape pill = new RoundRectangle2D.Double(
                inset, inset, getWidth() - 2 * inset, getHeight() - 2 * inset, arc, arc
        );
        g2.setColor(Colors.DEFAULT_BLUE.getColor());
        g2.fill(pill);

        if (displayType == Constants.SPLIT) {
            g2.setColor(Colors.BLACK_TEXT.getColor());

            float[] dottedPattern = {15.0f, 15.0f};
            Stroke dottedStroke = new BasicStroke(
                    2.0f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND,
                    10.0f, dottedPattern, 8.0f
            );

            g2.setStroke(dottedStroke);
            int y = getHeight() / 2;
            g2.drawLine(35, y, getWidth() - 35, y);
        }

        g2.dispose();
    }
}
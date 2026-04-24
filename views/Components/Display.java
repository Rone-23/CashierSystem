package views.Components;

import assets.*;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;

public class Display extends JPanel implements ThemeObserver{
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
        ThemeManager.getInstance().addObserver(this);
        onThemeChange();
    }

    public Display(Constants displayType, String textToDisplay) {
        this.displayType = displayType;
        initUI();
        setTopText(textToDisplay);
        ThemeManager.getInstance().addObserver(this);
        onThemeChange();
    }

    private void initUI() {
        setOpaque(false);
        setLayout(new GridLayout(displayType == Constants.SPLIT ? 2 : 1, 1));

        int vPad = displayType == Constants.SPLIT ? Scaler.getPadding(0.015) : Scaler.getPadding(0.03);
        int hPad = displayType == Constants.SPLIT ? Scaler.getPadding(0.055) : Scaler.getPadding(0.045);
        setBorder(BorderFactory.createEmptyBorder(vPad, hPad, vPad, hPad));

        double titleFontScale = displayType == Constants.SPLIT ? 0.03 : 0.05;
        double valueFontScale = displayType == Constants.SPLIT ? 0.03 : 0.05;

        if (displayType == Constants.SPLIT) {
            bottomTitleLabel = new JLabel();
            bottomValueLabel = new JLabel("", SwingConstants.RIGHT);

            bottomTitleLabel.setFont(Scaler.getFont(titleFontScale, Font.BOLD));
            bottomValueLabel.setFont(Scaler.getFont(valueFontScale, Font.BOLD));

            JPanel bottomPanel = new JPanel(new BorderLayout());
            bottomPanel.setOpaque(false);
            bottomPanel.add(bottomTitleLabel, BorderLayout.WEST);
            bottomPanel.add(bottomValueLabel, BorderLayout.EAST);
            add(bottomPanel);
        }

        topTitleLabel = new JLabel();
        topValueLabel = new JLabel("", SwingConstants.RIGHT);

        topTitleLabel.setFont(Scaler.getFont(titleFontScale, Font.BOLD));
        topValueLabel.setFont(Scaler.getFont(valueFontScale, Font.BOLD));

        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setOpaque(false);
        topPanel.add(topTitleLabel, BorderLayout.WEST);
        topPanel.add(topValueLabel, BorderLayout.EAST);
        add(topPanel, 0);
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
        int inset = Math.max(1, (int)(getHeight() * 0.05));
        Shape pill = new RoundRectangle2D.Double(
                inset, inset, getWidth() - 2 * inset, getHeight() - 2 * inset, arc, arc
        );
        g2.setColor(Colors.DEFAULT_BLUE.getColor());
        g2.fill(pill);

        if (displayType == Constants.SPLIT) {
            g2.setColor(Colors.BLACK_TEXT.getColor());

            float strokeThickness = Math.max(1.0f, getHeight() * 0.02f);
            float[] dottedPattern = {15.0f, 15.0f};
            Stroke dottedStroke = new BasicStroke(
                    strokeThickness, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND,
                    10.0f, dottedPattern, 8.0f
            );

            g2.setStroke(dottedStroke);
            int y = getHeight() / 2;
            int lineOffset = (int)(getWidth() * 0.06);
            g2.drawLine(lineOffset, y, getWidth() - lineOffset, y);
        }

        g2.dispose();
    }

    @Override
    public void onThemeChange() {
        Color standardText = Colors.BLACK_TEXT.getColor();
        if (topTitleLabel != null) topTitleLabel.setForeground(standardText);
        if (topValueLabel != null) topValueLabel.setForeground(standardText);
        if (bottomTitleLabel != null) bottomTitleLabel.setForeground(standardText);
        if (bottomValueLabel != null) bottomValueLabel.setForeground(standardText);

        repaint();
    }
}
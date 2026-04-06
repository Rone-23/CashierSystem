package views.Components;

import assets.Colors;
import services.Item;
import utility.ColorManipulation;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Line2D;
import java.text.DecimalFormat;

public class ListItemButton extends JToggleButton implements ContainsItem {
    private final Item item;
    private final DecimalFormat priceFormat = new DecimalFormat("0.00 €");

    private Color BACKGROUND_COLOR;
    private final Color TEXT_COLOR = Colors.BLACK_TEXT.getColor();
    private final Color BORDER_COLOR = Colors.GRAY.getColor();

    private final Font MAIN_FONT = new Font("Roboto", Font.BOLD, 18);
    private final Font SMALL_FONT = new Font("Roboto", Font.BOLD, 12);
    private final int BUTTON_HEIGHT = 65;
    private final int HORIZONTAL_MARGIN = 25;

    private int returningAmount = 0;
    private boolean isReturnMode = false;

    public ListItemButton(Color color, Item item) {
        this.item = item;
        this.BACKGROUND_COLOR = color;
        setContentAreaFilled(false);
        setFocusPainted(false);
        setBorderPainted(false);
        setCursor(new Cursor(Cursor.HAND_CURSOR));
        setPreferredSize(new Dimension(500, BUTTON_HEIGHT));
        setMaximumSize(new Dimension(Integer.MAX_VALUE, BUTTON_HEIGHT));
        setName(item.getName());
    }

    public void setReturnAmount(int amount) {
        this.returningAmount = amount;
        this.isReturnMode = amount > 0;
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        int w = getWidth();
        int h = getHeight();

        if (isReturnMode) {
            Color lightRed = new Color(255, 230, 230);
            Color darkRed = new Color(255, 200, 200);
            g2.setColor(isSelected() ? darkRed : lightRed);
        } else {
            if(Colors.isDarkMode()) {
                g2.setColor(isSelected() ? ColorManipulation.lighten(BACKGROUND_COLOR, 2.3f) : BACKGROUND_COLOR);
            }else{
                g2.setColor(isSelected() ? ColorManipulation.darken(BACKGROUND_COLOR, 0.93f) : BACKGROUND_COLOR);
            }
        }
        g2.fillRect(0, 0, w, h);

        g2.setColor(BORDER_COLOR);
        float[] dash = {4f, 4f};
        Stroke dashed = new BasicStroke(1.2f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, 10f, dash, 0f);
        g2.setStroke(dashed);
        g2.draw(new Line2D.Float(0, h - 1, w, h - 1));

        g2.setFont(MAIN_FONT);
        g2.setColor(TEXT_COLOR);
        FontMetrics fm = g2.getFontMetrics();
        int textY = ((h - fm.getHeight()) / 2) + fm.getAscent();

        g2.drawString(item.getName(), HORIZONTAL_MARGIN, textY);

        int savedAmountPerItem = item.getBasePrice() - item.getPrice();
        int totalSaved = savedAmountPerItem * item.getAmount();
        boolean hasDiscount = totalSaved > 0;

        String priceStr = priceFormat.format(item.getAmount() * item.getPrice() * 0.01);
        int priceWidth = fm.stringWidth(priceStr);

        g2.setFont(SMALL_FONT);
        FontMetrics sfm = g2.getFontMetrics();
        String savedStr = hasDiscount ? priceFormat.format(totalSaved * 0.01) : "";
        int savedWidth = hasDiscount ? sfm.stringWidth(savedStr) : 0;

        int maxRightWidth = Math.max(priceWidth, savedWidth);

        g2.setFont(MAIN_FONT);
        g2.setColor(TEXT_COLOR);
        g2.drawString(priceStr, w - priceWidth - HORIZONTAL_MARGIN, textY);

        String amountStr = item.getAmount() + " ks";
        int amountWidth = fm.stringWidth(amountStr);
        int amountX = w - maxRightWidth - amountWidth - 70;
        g2.drawString(amountStr, amountX, textY);

        if (isReturnMode && returningAmount > 0) {
            g2.setFont(SMALL_FONT);
            g2.setColor(Colors.DANGER_RED.getColor());
            String subStr = "-" + returningAmount +" ks";
            int subX = amountX + (amountWidth / 2) - (sfm.stringWidth(subStr) / 2);
            g2.drawString(subStr, subX, textY + 15);
        }

        if (hasDiscount) {
            g2.setFont(SMALL_FONT);
            g2.setColor(Colors.CUSTOMER_ORANGE.getColor());
            g2.drawString(savedStr, w - savedWidth - HORIZONTAL_MARGIN, textY + 16);
        }

        g2.dispose();
    }

    @Override
    public void setBackground(Color bg) {
        BACKGROUND_COLOR = bg;
        repaint();
    }

    public String getItemName() { return item.getName(); }

    @Override
    public int getItemPrice() {
        return item.getPrice();
    }

    @Override
    public int getItemAmount() {
        return item.getAmount();
    }

    @Override
    public Item getItem() { return item; }
}
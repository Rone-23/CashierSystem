package views.Components;

import assets.Colors;
import services.Item;
import utility.ColorManipulation;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Line2D;
import java.text.DecimalFormat;


public class ListItemButton extends JToggleButton implements ContainsItem{
    private final Item item;
    private final DecimalFormat priceFormat = new DecimalFormat("0.00 €");

    private Color BACKGROUND_COLOR;
    private final Color TEXT_COLOR = Colors.BLACK_TEXT.getColor();
    private final Color BORDER_COLOR = Colors.GRAY.getColor();

    private final Font MAIN_FONT =new Font("Roboto", Font.BOLD, 18);
    private final int BUTTON_HEIGHT = 65; // Increased height for larger margins
    private final int HORIZONTAL_MARGIN = 25;

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

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

        int w = getWidth();
        int h = getHeight();

        if (isSelected()) {
            g2.setColor(ColorManipulation.lighten(BACKGROUND_COLOR, 28));
            g2.fillRect(0, 0, w, h);
            g2.setColor(ColorManipulation.darken(BACKGROUND_COLOR, 28));
            g2.fillRect(0, 0, 10, h);
        } else {
            g2.setColor(BACKGROUND_COLOR);
            g2.fillRect(0, 0, w, h);
        }

        g2.setColor(BORDER_COLOR);
        float[] dash = {3f, 2f};
        BasicStroke dashed = new BasicStroke(1.2f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, 10f, dash, 0f);
        g2.setStroke(dashed);

        g2.draw(new Line2D.Float(0, h - 1, w, h - 1));

        g2.setFont(MAIN_FONT);
        g2.setColor(TEXT_COLOR);

        FontMetrics fm = g2.getFontMetrics();
        int textY = ((h - fm.getHeight()) / 2) + fm.getAscent();

        g2.drawString(item.getName(), HORIZONTAL_MARGIN, textY);

        String priceStr = priceFormat.format(item.getAmount()*item.getPrice()*0.01);
        int priceWidth = fm.stringWidth(priceStr);
        g2.drawString(priceStr, w - priceWidth - HORIZONTAL_MARGIN, textY);

        String amountStr = item.getAmount() + " ks";
        int amountWidth = fm.stringWidth(amountStr);
        // We leave a specific gap (e.g., 80px) between amount and price start
        g2.drawString(amountStr, w - priceWidth - amountWidth - 70, textY);

        g2.dispose();
    }

    @Override
    public void setBackground(Color bg) {
        BACKGROUND_COLOR = bg;
        repaint();

    }

    @Override
    public Item getItem() {
        return item;
    }

    @Override
    public String getItemName(){
        return item.getName();
    }

    @Override
    public int getItemPrice(){
        return item.getPrice();
    }

    @Override
    public int getItemAmount(){
        return item.getAmount();
    }

    public void setAmount(int amount){
        item.setAmount(amount);
    }
}

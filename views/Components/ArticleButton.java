package views.Components;

import assets.Colors;
import services.Item;
import utility.ColorManipulation;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.geom.Path2D;
import java.awt.geom.Rectangle2D;
import java.awt.geom.RoundRectangle2D;

public class ArticleButton extends JToggleButton implements ContainsItem {
    private Color color;
    private final Color colorActive;
    private final Color colorDisabled;
    private final Item item;

    private boolean isStarred = false;
    private Rectangle2D starBounds;

    public ArticleButton(Color color, Item item) {
        this.item = item;
        this.color = color;
        this.colorActive = color;
        this.colorDisabled = ColorManipulation.lighten(color, 28);

        setContentAreaFilled(false);
        setOpaque(false);
        setFocusPainted(false);
        setBorderPainted(false);
        setFont(new Font("Roboto", Font.BOLD, 12));
        setForeground(Colors.BLACK_TEXT.getColor());
        setMargin(new Insets(75, 50, 75, 50));
    }

    /**
     * This is the key fix. By intercepting the mouse event here,
     * we prevent the ButtonModel from seeing the click if it's on the star.
     */
    @Override
    protected void processMouseEvent(MouseEvent e) {
        if (starBounds != null && starBounds.contains(e.getPoint())) {
            if (e.getID() == MouseEvent.MOUSE_PRESSED) {
                isStarred = !isStarred;
                repaint();
            }
            // Consuming and returning here prevents the button from toggling
            e.consume();
            return;
        }
        super.processMouseEvent(e);
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        int arc = getWidth() / 9;
        int inset = getWidth() / 18;

        // Background
        if (isSelected()) {
            g2.setPaint(ColorManipulation.darken(color, 0.88f));
        } else {
            g2.setPaint(color);
        }

        Shape main = new RoundRectangle2D.Double(inset, inset, getWidth() - inset * 2, getHeight() - inset * 2, arc, arc);
        g2.fill(main);

        // Inner Rectangle (Image/Icon Placeholder)
        int insetTop = getHeight() / 26;
        int insetBottom = getHeight() / 3;
        int insetSide = getWidth() / 6;
        int width = getWidth() - inset * 2 - insetSide * 2;
        int height = getHeight() - inset * 2 - insetBottom - insetTop;

        g2.setPaint(ColorManipulation.lighten(color, 10f));
        Shape inner = new RoundRectangle2D.Double(inset + insetSide, inset + insetTop, width, height - insetTop, arc, arc);
        g2.fill(inner);

        // Star Icon
        drawStar(g2, inset);

        // Typography
        FontMetrics fm = getFontMetrics(getFont());
        g2.setPaint(Colors.BLACK_TEXT.getColor());

        // Name
        int textX = insetSide + inset;
        int nameY = insetTop + inset + height + fm.getAscent();
        g2.drawString(item.getName(), textX, nameY);

        // Price & Amount (Lower half)
        int priceY = nameY + fm.getHeight() + 4;
        String priceText = String.format("%.2f€", item.getPrice() * 0.01);
        String amountText = "x " + item.getAmount();

        g2.drawString(priceText, textX, priceY);

        // Align amount to the right edge of the text area
        int amountWidth = fm.stringWidth(amountText);
        int amountX = (inset + insetSide + width) - amountWidth;
        g2.drawString(amountText, amountX, priceY);

        g2.dispose();
    }

    private void drawStar(Graphics2D g2, int inset) {
        int starSize = getWidth() / 8;
        int starX = getWidth() - inset - starSize - 8;
        int starY = inset + 8;

        // Define click area
        starBounds = new Rectangle2D.Double(starX, starY, starSize, starSize);

        Path2D star = new Path2D.Double();
        double centerX = starX + (double) starSize / 2;
        double centerY = starY + (double) starSize / 2;
        double outerRadius = (double) starSize / 2;
        double innerRadius = outerRadius * 0.45;

        for (int i = 0; i < 10; i++) {
            double angle = Math.toRadians(-90 + i * 36);
            double r = (i % 2 == 0) ? outerRadius : innerRadius;
            double x = centerX + r * Math.cos(angle);
            double y = centerY + r * Math.sin(angle);
            if (i == 0) star.moveTo(x, y);
            else star.lineTo(x, y);
        }
        star.closePath();

        if (isStarred) {
            g2.setPaint(new Color(255, 215, 0)); // Gold
            g2.fill(star);
        }

        g2.setPaint(isStarred ? new Color(180, 140, 0) : new Color(0, 0, 0, 120));
        g2.setStroke(new BasicStroke(1.2f));
        g2.draw(star);
    }

    public boolean isStarred() { return isStarred; }
    public void setStarred(boolean starred) { this.isStarred = starred; repaint(); }

    @Override public String getItemName() { return item.getName(); }
    @Override public int getItemPrice() { return item.getPrice(); }
    @Override public int getItemAmount() { return item.getAmount(); }
    public void setItemAmount(int amount) { item.setAmount(amount); }

    @Override
    public void setEnabled(boolean b) {
        super.setEnabled(b);
        color = b ? colorActive : colorDisabled;
        repaint();
    }
}
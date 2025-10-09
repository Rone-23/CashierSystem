package viewsRework.GP;

import assets.Colors;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;

public class Display extends JTextArea {
    String text;
    Dimension dimension = new Dimension(400,100);
    public Display() {
        this.setPreferredSize(dimension);
        this.setOpaque(false);
        this.setBorder(new EmptyBorder(10,10,10,10));
        this.setMargin(new Insets(30,30,30,30));
    }

    public void setText(String text) {
        this.text = text;
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g.create();

        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        int arc = getHeight();
        int inset = 5;

        Shape pill = new RoundRectangle2D.Double(
                inset, inset, getWidth() - 2 * inset, getHeight() - 2 * inset, arc, arc
        );

        // Fill
        g2d.setColor(Colors.DEFAULT_BLUE.getColor());
        g2d.fill(pill);
        super.paintComponent(g);


        g2d.dispose();
    }

}

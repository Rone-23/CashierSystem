package viewsRework.GP;

import assets.Colors;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;

public class Display extends JTextArea {
    private String text = "Total 23.80";

    public Display() {
        setPreferredSize(new Dimension(200, 25)); // default size
    }

    public void setText(String text) {
        this.text = text;
        repaint(); // tells Swing to redraw with new text
    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g.create();

        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        Shape pill = new RoundRectangle2D.Double(
                0, 0, getWidth(), getHeight(), getHeight(), getHeight()
        );

        g2d.setColor(Colors.DEFAULT_BLUE.getColor());
        g2d.fill(pill);

        g2d.setColor(Colors.BLACK_TEXT.getColor());
        g2d.draw(pill);

        g2d.dispose();
    }
}

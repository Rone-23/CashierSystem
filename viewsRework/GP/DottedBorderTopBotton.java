package viewsRework.GP;

import javax.swing.border.AbstractBorder;
import java.awt.*;

class DottedBorderTopBottom extends AbstractBorder {
    private final Color color;
    private final float[] dashPattern = {2f, 2f}; // 2px dot, 2px gap
    private final int thickness;

    public DottedBorderTopBottom(Color color, int thickness) {
        this.color = color;
        this.thickness = thickness;
    }

    @Override
    public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setColor(color);

        // use stroke with dash pattern
        Stroke dashed = new BasicStroke(thickness,
                BasicStroke.CAP_BUTT,
                BasicStroke.JOIN_BEVEL,
                0,
                dashPattern,
                0);
        g2.setStroke(dashed);

        // draw top line
        g2.drawLine(x, y, x + width, y);

        // draw bottom line
        g2.drawLine(x, y + height - 1, x + width, y + height - 1);

        g2.dispose();
    }
}

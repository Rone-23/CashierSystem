package utility;

import assets.Colors;
import javax.swing.border.AbstractBorder;
import java.awt.*;

public class ThemeMatteBorder extends AbstractBorder {
    private final int top, left, bottom, right;
    private final Colors themeColor;

    public ThemeMatteBorder(int top, int left, int bottom, int right, Colors themeColor) {
        this.top = top;
        this.left = left;
        this.bottom = bottom;
        this.right = right;
        this.themeColor = themeColor;
    }

    @Override
    public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
        g.setColor(themeColor.getColor());

        if (top > 0) g.fillRect(x, y, width, top);
        if (left > 0) g.fillRect(x, y, left, height);
        if (bottom > 0) g.fillRect(x, y + height - bottom, width, bottom);
        if (right > 0) g.fillRect(x + width - right, y, right, height);
    }

    @Override
    public Insets getBorderInsets(Component c) {
        return new Insets(top, left, bottom, right);
    }

    @Override
    public Insets getBorderInsets(Component c, Insets insets) {
        insets.top = top;
        insets.left = left;
        insets.bottom = bottom;
        insets.right = right;
        return insets;
    }
}
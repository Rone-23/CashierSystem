package views.Components;

import assets.Colors;
import assets.ThemeManager;
import assets.ThemeObserver;
import utility.ColorManipulation;

import javax.swing.*;
import javax.swing.plaf.basic.BasicScrollBarUI;
import java.awt.*;

public class CustomScrollBar extends BasicScrollBarUI implements ThemeObserver {

    private Color restingColor;
    private Color hoverColor;
    private Color dragColor;

    public CustomScrollBar() {
        ThemeManager.getInstance().addObserver(this);
        onThemeChange();
    }

    @Override
    public void onThemeChange() {
        restingColor = Colors.DEFAULT_BLUE.getColor();
        hoverColor = ColorManipulation.darken(restingColor, 0.98f);
        dragColor = ColorManipulation.darken(restingColor, 0.95f);


        if (scrollbar != null) {
            scrollbar.repaint();
        }
    }

    @Override
    public void uninstallUI(JComponent c) {
        super.uninstallUI(c);
        ThemeManager.getInstance().removeObserver(this);
    }

    @Override
    protected void configureScrollBarColors() {
    }

    @Override
    protected JButton createDecreaseButton(int orientation) {
        return createZeroButton();
    }

    @Override
    protected JButton createIncreaseButton(int orientation) {
        return createZeroButton();
    }

    private JButton createZeroButton() {
        JButton button = new JButton();
        button.setPreferredSize(new Dimension(0, 0));
        button.setMinimumSize(new Dimension(0, 0));
        button.setMaximumSize(new Dimension(0, 0));
        return button;
    }

    @Override
    protected void paintTrack(Graphics g, JComponent c, Rectangle trackBounds) {
    }

    @Override
    protected void paintThumb(Graphics g, JComponent c, Rectangle thumbBounds) {
        if (thumbBounds.isEmpty() || !scrollbar.isEnabled()) {
            return;
        }

        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        if (isDragging) {
            g2.setColor(dragColor);
        } else if (isThumbRollover()) {
            g2.setColor(hoverColor);
        } else {
            g2.setColor(restingColor);
        }

        int padding = 3;
        int x = thumbBounds.x + padding;
        int y = thumbBounds.y + padding;
        int width = thumbBounds.width - 2 * padding;
        int height = thumbBounds.height - 2 * padding;

        g2.fillRoundRect(x, y, width, height, 10, 10);
        g2.dispose();
    }
}
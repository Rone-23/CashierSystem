package viewsRework.GP;

import assets.Colors;

import javax.swing.*;
import java.awt.*;

public class ChonkyButton extends JButton {
    private final int arc = 20; // roundness of corners

    public ChonkyButton(String text) {
        super(text);
        setContentAreaFilled(false); // prevent default fill
        setFocusPainted(false);      // remove ugly focus rectangle
        setBorderPainted(false);     // we will draw our own border
        setFont(new Font("Roboto", Font.PLAIN, 40)); // style text
        setForeground(Colors.BLACK_BUTTONS.getColor());
        setMargin(new Insets(25,25,25,25));

    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();

        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        Color bg = getModel().isArmed() ? new Color(180, 200, 230) : Colors.LIGHT_BLUE.getColor();
        g2.setColor(bg);

        g2.fillRoundRect(0, 0, getWidth(), getHeight(), arc, arc);

        g2.setColor(new Color(150, 170, 200));
        g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, arc, arc);

        g2.dispose();

        super.paintComponent(g);
    }
}

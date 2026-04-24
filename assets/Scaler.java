package assets;

import java.awt.*;

public class Scaler {
    private static final Dimension SCREEN_SIZE = Toolkit.getDefaultToolkit().getScreenSize();


    public static Dimension getDimension(double widthPercent, double heightPercent) {
        int width = (int) (SCREEN_SIZE.width * widthPercent);
        int height = (int) (SCREEN_SIZE.height * heightPercent);
        return new Dimension(width, height);
    }


    public static Font getFont(double heightPercent, int style) {
        int calculatedSize = Math.max(2, (int) (SCREEN_SIZE.height * heightPercent));
        return new Font("Roboto", style, calculatedSize);
    }


    public static int getPadding(double screenHeightPercent) {
        return (int) (SCREEN_SIZE.height * screenHeightPercent);
    }
}
package utility;

import java.awt.*;

public class ColorManipulation {
    public static Color darken(Color color, float factor){
        float[] hsb = Color.RGBtoHSB(color.getRed(), color.getGreen(), color.getBlue(), null);
        hsb[1] = Math.max(0.25f, hsb[1] * (1/factor));
        hsb[2] = Math.max(0f, hsb[2] * factor); // reduce brightness
        return Color.getHSBColor(hsb[0], hsb[1], hsb[2]);
    }
}

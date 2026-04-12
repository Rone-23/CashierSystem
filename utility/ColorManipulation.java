package utility;

import java.awt.*;

public class ColorManipulation {

    public static Color darken(Color color, float factor){
        float[] hsb = Color.RGBtoHSB(color.getRed(), color.getGreen(), color.getBlue(), null);
        hsb[1] = Math.min(1.0f, Math.max(0.25f, hsb[1] * (1/factor)));
        hsb[2] = Math.max(0f, Math.min(1.0f, hsb[2] * factor));

        return Color.getHSBColor(hsb[0], hsb[1], hsb[2]);
    }

    public static Color lighten(Color color, float factor){
        float[] hsb = Color.RGBtoHSB(color.getRed(), color.getGreen(), color.getBlue(), null);
        hsb[2] = Math.min(1.0f, hsb[2] * factor);
        hsb[1] = Math.max(0.05f, hsb[1] * (1/factor));

        return Color.getHSBColor(hsb[0], hsb[1], hsb[2]);
    }
}
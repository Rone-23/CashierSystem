package utility;

import java.awt.*;

public class ColorManipulation {
    public static Color darken(Color color, float factor){
        float[] hsb = Color.RGBtoHSB(color.getRed(), color.getGreen(), color.getBlue(), null);
        hsb[1] = Math.max(0.25f, hsb[1] * (1/factor));
        hsb[2] = Math.max(0f, hsb[2] * factor);
        return Color.getHSBColor(hsb[0], hsb[1], hsb[2]);
    }
    public static Color lighten(Color color, float factor){
        float[] hsb = Color.RGBtoHSB(color.getRed(), color.getGreen(), color.getBlue(), null);

        // Lightening increases brightness (hsb[2]). Cap at 1.0f (max brightness).
        hsb[2] = Math.min(1.0f, hsb[2] * factor);

        // Reduce saturation (hsb[1]) slightly to prevent the color from becoming overly aggressive/artificial at high brightness.
        // Cap the minimum saturation at 0.1f to prevent pure white/gray unless the original color was already extremely pale.
        hsb[1] = Math.max(0.05f, hsb[1] * (1/factor));

        return Color.getHSBColor(hsb[0], hsb[1], hsb[2]);
    }
}

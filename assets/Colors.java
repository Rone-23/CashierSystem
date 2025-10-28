package assets;

import java.awt.Color;

public enum Colors {
    DEFAULT_BUTTON("#C8D3E0"),
    GRAY("#736E7E"),
    BLACK_TEXT("#43484E"),
    BACKGROUND_WHITE("#FAFCFE"),
    BUTTON_BACKGROUND_WHITE_ELEVATED("#F0F2F4"),
    BUTTON_LIGHT_BLUE("#DFE8F4"),
    BACKGROUND_GRAY("#F0F2F4"),
    DEFAULT_BLUE("#87B5F5");

    private final String hexCode;

    Colors(String hexCode) {
        this.hexCode = hexCode;
    }

    public Color getColor() {
        return Color.decode(hexCode);
    }

//    public static Color getColor(String hexCode){
//        return Color.decode(hexCode);
//    }

}

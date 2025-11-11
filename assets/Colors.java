package assets;

import java.awt.Color;

public enum Colors {
    //LIGHT MODE
//    GRAY("#736E7E"),
//    BLACK_TEXT("#43484E"),
//    BACKGROUND_WHITE("#FAFCFE"),
//    BUTTON_BACKGROUND_WHITE_ELEVATED("#F0F2F4"),
//    BUTTON_LIGHT_BLUE("#DFE8F4"),
//    BACKGROUND_GRAY("#F0F2F4"),
//    DEFAULT_BLUE("#87B5F5");

    //DARK MODE
    GRAY("#A8AAB9"),
    BLACK_TEXT("#F0F4F7"),
    BACKGROUND_WHITE("#1A1A22"),
    BUTTON_BACKGROUND_WHITE_ELEVATED("#3C3C4D"),
    BUTTON_LIGHT_BLUE("#2F435F"),
    BACKGROUND_GRAY("#282836"),
    DEFAULT_BLUE("#1D546C");

    private final String hexCode;

    Colors(String hexCode) {
        this.hexCode = hexCode;
    }

    public Color getColor() {
        return Color.decode(hexCode);
    }

}

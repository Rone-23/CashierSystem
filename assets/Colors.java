package assets;

import java.awt.Color;

public enum Colors {
    DEFAULT_BUTTON("#C8D3E0"),
    BLACK_BUTTONS("#362B36"),
    GRAY("#736E7E"),
    YELLOW_DISPLAY("#ECD996"),
    YELLOW_BUTTON("#E7C328"),
    RED_BUTTON("#F03320"),
    GREEN_STATUS("#398C5C"),
    BLACK_TEXT("#43484E"),
    BACKGROUND_WHITE("#FAFCFE"),
    LIGHT_BLUE("#DFE8F4"),
    DEFAULT_BLUE("#87B5F5");

    //darkmode
//    DEFAULT_BUTTON("#3B4A54"),
//    BLACK_BUTTONS("#1B1A1B"),
//    GRAY("#444349"),
//    YELLOW_DISPLAY("#B39D4A"),
//    YELLOW_BUTTON("#D4A017"),
//    RED_BUTTON("#A6201A"),
//    GREEN_STATUS("#1E6539");

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

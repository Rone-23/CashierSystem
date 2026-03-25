package assets;

import java.awt.Color;

public enum Colors {
    //LIGHT MODE
//    EURO_5("#A6B1C2"),
//    EURO_10("#E59A9A"),
//    EURO_20("#92B4E0"),
//    EURO_50("#F2C08A"),
//    EURO_100("#A8D0A4"),

    GRAY("#736E7E"),
    BLACK_TEXT("#43484E"),
    BACKGROUND_WHITE("#FAFCFE"),
    BUTTON_BACKGROUND_WHITE_ELEVATED("#F0F2F4"),
    BUTTON_LIGHT_BLUE("#DFE8F4"),
    BACKGROUND_GRAY("#F0F2F4"),
    ARTICLE_BUTTON("#D3D7E0"),
    SUCCESS_GREEN("#add5a8"),
    ALT_PAYMENT_TEAL("#a8d5ba"),
    DANGER_RED("#E8A5A5"),
    MILD_YELLOW("#F2E0A1"),
    CUSTOMER_ORANGE("#E6BA93"),
    DEFAULT_BLUE("#87B5F5"),
    PURPLE("#C5B4E3"),
    ;



    //NEW DARK MODE
//    GRAY("#8E95A2"),
//    BLACK_TEXT("#E2E4E9"),
//    BACKGROUND_WHITE("#12141C"),
//    BUTTON_BACKGROUND_WHITE_ELEVATED("#2A2F3D"),
//    BUTTON_LIGHT_BLUE("#343D4E"),
//    BACKGROUND_GRAY("#1E212B"),
//    ARTICLE_BUTTON("#3D4455"),
//    SUCCESS_GREEN("#2D4B38"),
//    DANGER_RED("#5C3232"),
//    MILD_YELLOW("#4D4322"),
//    DEFAULT_BLUE("#404F80");

    private final String hexCode;

    Colors(String hexCode) {
        this.hexCode = hexCode;
    }

    public Color getColor() {
        return Color.decode(hexCode);
    }

}

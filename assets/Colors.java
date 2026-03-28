package assets;

import java.awt.Color;

public enum Colors {
    //LIGHT MODE
//    EURO_5("#A6B1C2"),
//    EURO_10("#E59A9A"),
//    EURO_20("#92B4E0"),
//    EURO_50("#F2C08A"),
//    EURO_100("#A8D0A4"),

    GRAY("#736E7E", "#8E95A2"),
    BLACK_TEXT("#43484E", "#E2E4E9"),
    BACKGROUND_WHITE("#FAFCFE", "#0F111A"),
    BACKGROUND_GRAY("#F0F2F4", "#161922"),
    BUTTON_BACKGROUND_WHITE_ELEVATED("#F0F2F4", "#232734"),
    BUTTON_LIGHT_BLUE("#DFE8F4", "#2D3446"),
    ARTICLE_BUTTON("#D3D7E0", "#343B4D"),
    SUCCESS_GREEN("#add5a8", "#3A5A40"),
    ALT_PAYMENT_TEAL("#a8d5ba", "#31525B"),
    DANGER_RED("#E8A5A5", "#7A3E3E"),
    MILD_YELLOW("#F2E0A1", "#6B5E32"),
    CUSTOMER_ORANGE("#E6BA93", "#7A533E"),
    DEFAULT_BLUE("#87B5F5", "#3E5A8E"),
    PURPLE("#C5B4E3", "#5A4E7A")
    ;

    private final Color lightColor;
    private final Color darkColor;
    private static boolean isDarkMode = true;

    Colors(String lightHex, String darkHex) {
        this.lightColor = Color.decode(lightHex);
        this.darkColor = Color.decode(darkHex);
    }

    public Color getColor() {
        return isDarkMode ? darkColor : lightColor;
    }

    public static void setDarkMode(boolean darkMode) {
        isDarkMode = darkMode;
    }

    public static boolean isDarkMode() {
        return isDarkMode;
    }

    public static void switchState(){
        setDarkMode(!isDarkMode);
    }
}

package assets;

public enum Constants {
    //MAIN
    //
    MAIN_BORDER(0.014),
    LEFT_PANEL_WIDTH(0.41),
    RIGHT_PANEL_WIDTH(0.576),
    TOP_PANEL_HEIGHT(0.945),
    BOTTOM_PANEL_HEIGHT(0.041),

    //TOP LEFT CONST
    TOP_DISPLAY_HEIGHT(0.072),
    TOP_DISPLAY_WIDTH(1.0),
    ARTICLE_DISPLAY_HEIGHT(0.776+0.1),
    ARTICLE_DISPLAY_WIDTH(1.0),
    TO_PAY_DISPLAY_HEIGHT(0.152),
    TO_PAY_DISPLAY_WIDTH(0.643),
    NAVIGATION_BUTTONS_HEIGHT(0.152),
    NAVIGATION_BUTTONS_WIDTH(0.191),

    //BOTTOM LEFT CONST
    KEYBOARD_HEIGHT(1.0),
    KEYBOARD_WIDTH(0.767),
    UTILITY_HEIGHT(1.0),
    UTILITY_WIDTH(0.233),
    TOP_COUNT_DISPLAY_HEIGHT(0.118),
    TOP_COUNT_DISPLAY_WIDTH(0.99),
    KEYBOARD_BUTTON_HEIGHT(0.294),
    KEYBOARD_BUTTON_WIDTH(0.33),
    UTILITY_BUTTON_HEIGHT(0.25),
    UTILITY_BUTTON_WIDTH(0.25),

    //BOTTOM RIGHT SIDE
    RIGHT_TOP_PART_HEIGHT(0.432),
    RIGHT_TOP_PART_WIDTH(1.0),
    BUTTONS_HEIGHT(0.568),
    BUTTONS_WIDTH(1.0),
    BUTTONS_FOR_USE_HEIGHT(0.2),
    BUTTONS_FOR_USE_WIDTH(0.2),

    //ARTICLES
    //
    ARTICLE_SECTION_BUTTONS_HEIGHT(0.15),
    ARTICLE_BUTTONS_HEIGHT(0.75),
    ARTICLE_UTILITY_BUTTONS_HEIGHT(0.1),

    //TOP PANEL
    ARROW_WIDTH(0.6),

    //BOTTOM PANEL
    MATH_OPERATION_BUTTON_HEIGHT(0.4),

    ;
    private final double value;
    Constants(double constant){
        this.value  = constant;
    }
    public double getValue(){
        return value;
    }

}

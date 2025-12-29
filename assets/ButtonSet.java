package assets;

import java.awt.*;

public enum ButtonSet {

    // --- Define the Button Sets (Configurations) ---

    /**
     * UTILITY_NAMES : Names of buttons on the control panel
     */
    UTILITY_NAMES(new ButtonLabel[]{
            ButtonLabel.ARTICLES,
            ButtonLabel.LAST_ARTICLE,
            ButtonLabel.STORNO,
            ButtonLabel.CASH,
            ButtonLabel.CARD,
            ButtonLabel.FOOD_TICKETS,
            ButtonLabel.VOUCHER
    }),

    /**
     * CASH_NAMES : Examples of available paper currency
     */
    CASH_NAMES(new ButtonLabel[]{
            ButtonLabel.FIVE,
            ButtonLabel.TEN,
            ButtonLabel.TWENTY,
            ButtonLabel.FIFTY,
            ButtonLabel.HUNDRED,
            ButtonLabel.ADD,
            ButtonLabel.EXIT,
    }),

    /**
     * IDLE_UTILITY_NAMES : Names of buttons on the control panel
     */
    IDLE_UTILITY_NAMES(new ButtonLabel[]{
            ButtonLabel.BEGIN,
            ButtonLabel.CREATE_CARD,
            ButtonLabel.COPY_RECEIPT,
            ButtonLabel.RETURN,
            ButtonLabel.VOUCHER,
            ButtonLabel.PAUSE,
            ButtonLabel.PADAVAN,
    }),

;
    // --- Internal Properties and Constructor ---

    private final ButtonLabel[] labels;

    ButtonSet(ButtonLabel[] labels) {
        this.labels = labels;
    }

    /**
     * Gets the array of ButtonLabel enums for this set.
     * @return An array of ButtonLabel constants.
     */
    public ButtonLabel[] getLabels() {
        return labels;
    }

    /**
     * Gets the actual String names associated with this button set.
     * This is useful for directly populating UI elements.
     * @return An array of String labels.
     */
    public String[] getStringLabels() {
        String[] stringLabels = new String[labels.length];
        for (int i = 0; i < labels.length; i++) {
            // Use the custom toString() method of ButtonLabel
            stringLabels[i] = labels[i].toString();
        }
        return stringLabels;
    }

    // --- Nested Enum for Individual Button Labels ---

    /**
     * Defines all possible button labels in the application.
     * Each constant stores the actual String value to be displayed on the button.
     */
    public enum ButtonLabel {
        // UTILITY_BUTTON Labels
        ARTICLES("Artikle", Colors.DEFAULT_BLUE),
        LAST_ARTICLE("Posledna polozka", Colors.DEFAULT_BLUE),
        STORNO("Storno", Colors.DEFAULT_BLUE),
        CASH("Hotovost", Colors.DEFAULT_BLUE),
        CARD("Karta", Colors.DEFAULT_BLUE),
        FOOD_TICKETS("Stravenky", Colors.DEFAULT_BLUE),
        VOUCHER("Poukazky", Colors.DEFAULT_BLUE),

        //CASH_BUTTON Labels
        FIVE("5€", Colors.DEFAULT_BLUE),
        TEN("10€", Colors.DEFAULT_BLUE),
        TWENTY("20€", Colors.DEFAULT_BLUE),
        FIFTY("50€", Colors.DEFAULT_BLUE),
        HUNDRED("100€", Colors.DEFAULT_BLUE),

        //IDLE_UTILITY_BUTTONS
        BEGIN("Zacat (Artikle)", Colors.DEFAULT_BLUE),
        CREATE_CARD("Vybavit kartu", Colors.DEFAULT_BLUE),
        COPY_RECEIPT("Kopia blocku", Colors.DEFAULT_BLUE),
        RETURN("Vratka", Colors.DEFAULT_BLUE),
        PAUSE("Pauza", Colors.DEFAULT_BLUE),
        PADAVAN("Zaucenie", Colors.DEFAULT_BLUE),

        //KEYBOARD
        DELETE("Delete", Colors.BUTTON_LIGHT_BLUE),
        BACKSPACE("Backspace", Colors.BUTTON_LIGHT_BLUE),
        NUMBERS("_number_", Colors.BUTTON_LIGHT_BLUE),


        // Shared Labels
        EXIT("Naspäť", Colors.DEFAULT_BLUE),
        ADD("Pridat", Colors.DEFAULT_BLUE);

        private final String displayValue;
        private final Colors color;

        ButtonLabel(String displayValue, Colors color) {
            this.displayValue = displayValue;
            this.color = color;
        }

        /**
         * Overrides the default enum name to return the display value.
         * This is what you use to set the button text in your UI.
         */
        @Override
        public String toString() {
            return displayValue;
        }

        public Color getColor(){
            return color.getColor();
        }
    }
}
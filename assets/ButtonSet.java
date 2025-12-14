package assets;

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
        ARTICLES("Artikle"),
        LAST_ARTICLE("Posledna polozka"),
        STORNO("Storno"),
        CASH("Hotovost"),
        CARD("Karta"),
        FOOD_TICKETS("Stravenky"),
        VOUCHER("Poukazky"),

        //CASH_BUTTON Labels
        FIVE("5"),
        TEN("10"),
        TWENTY("20"),
        FIFTY("50"),
        HUNDRED("100"),

        //IDLE_UTILITY_BUTTONS
        BEGIN("Zacat (Artikle)"),
        CREATE_CARD("Vybavit kartu"),
        COPY_RECEIPT("Kopia blocku"),
        RETURN("Vratka"),
        PAUSE("Pauza"),
        PADAVAN("Zaucenie"),

        //KEYBOARD
        DELETE("Delete"),
        BACKSPACE("Backspace"),


        // Shared Labels
        EXIT("Naspäť"),
        ADD("Pridat");

        private final String displayValue;

        ButtonLabel(String displayValue) {
            this.displayValue = displayValue;
        }

        /**
         * Overrides the default enum name to return the display value.
         * This is what you use to set the button text in your UI.
         */
        @Override
        public String toString() {
            return displayValue;
        }
    }
}
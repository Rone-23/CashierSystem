package controllers.display;


import controllers.transaction.ContentController;

import java.util.HashMap;
import java.util.Map;

public class DisplayDispatcher{
    private static final Map<String,DisplayController> displayMap = new HashMap<>();

    public static void addActiveDisplay(String constraint) {
        ContentController.addObserver(displayMap.get(constraint));
    }

    public static void removeActiveDisplay(String constraint) {
        ContentController.removeObserver(displayMap.get(constraint));
    }

    public static void addDisplay(String constraint, DisplayController display){
        displayMap.put(constraint,display);
    }

    public static void removeDisplay(String constraint, DisplayController display){
        displayMap.remove(constraint,display);
    }

    public static void activeDisplayForAmount(){
        try {
            addActiveDisplay("REGISTER-AMOUNT");
            addActiveDisplay("ARTICLES-SPLIT");
            removeActiveDisplay("REGISTER-TOTAL");
            removeActiveDisplay("CODE-DISPLAY");
        } catch (Exception _) {
        }
    }

    public static void activeDisplayForPayment(){
        try {
            removeActiveDisplay("REGISTER-AMOUNT");
            removeActiveDisplay("ARTICLES-SPLIT");
            removeActiveDisplay("CODE-DISPLAY");
            addActiveDisplay("REGISTER-TOTAL");
        } catch (Exception _) {
        }
    }

    public static void activeDisplayForCode(){
        try {
            removeActiveDisplay("REGISTER-AMOUNT");
            removeActiveDisplay("ARTICLES-SPLIT");
            removeActiveDisplay("REGISTER-TOTAL");
            addActiveDisplay("CODE-DISPLAY");
        } catch (Exception _) {
        }
    }

}

package controllers.buttons;
import assets.ButtonSet;
import views.panels.ButtonFoundable;

import java.awt.event.ActionEvent;
import java.util.ArrayList;


public class KeyboardController {
    private final static ArrayList<KeyboardListener> keyboardListeners = new ArrayList<>();

    public KeyboardController(ButtonFoundable keyboard){

        keyboard.getButton(ButtonSet.ButtonLabel.BACKSPACE.toString()).addActionListener(this::notifyListeners);
        keyboard.getButton(ButtonSet.ButtonLabel.DELETE.toString()).addActionListener(this::notifyListeners);

        for (int keyName=0; keyName<10; keyName++) {
            keyboard.getButton(String.valueOf(keyName)).addActionListener(this::notifyListeners);
        }
    }

    // Observer
    public static void addListener(KeyboardListener keyboardListener){
        keyboardListeners.add(keyboardListener);
    }

    public static void removeListener(KeyboardListener keyboardListener){
        keyboardListeners.remove(keyboardListener);
    }

    private void notifyListeners(ActionEvent e){
        System.out.printf(e.getActionCommand());
        keyboardListeners.forEach(keyboardListener -> keyboardListener.keyboardPress(e.getActionCommand()));
    }

}

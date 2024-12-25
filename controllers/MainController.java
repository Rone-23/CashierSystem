package controllers;

import services.display.AmountToUse;
import views.leftPanel.LeftPanel;

import javax.swing.*;

public class MainController {
    public MainController(LeftPanel leftPanel){
        AmountToUse amountToUse = new AmountToUse();
        new KeyboardController(leftPanel.getKeyboardPanel().getKeyboard(), leftPanel.getDisplayPanel(), amountToUse);
        new UtilityController(leftPanel.getKeyboardPanel().getKeyboard(), leftPanel.getKeyboardPanel().getUtility(), amountToUse);
    }
}

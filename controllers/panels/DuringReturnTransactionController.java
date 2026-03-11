package controllers.panels;

import controllers.buttons.KeyboardController;
import controllers.display.DisplayController;
import controllers.display.DisplayDispatcher;
import views.panels.DuringReturnTransaction;

public class DuringReturnTransactionController {
    public DuringReturnTransactionController(){
        DuringReturnTransaction duringReturn = ViewManager.getInstance().getDuringReturn();

        DisplayController displayController = new DisplayController(duringReturn.getInputDisplay());
        DisplayDispatcher.addDisplay("CODE-DISPLAY", displayController);

        new KeyboardController(duringReturn);

    }
}

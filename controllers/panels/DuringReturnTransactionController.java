package controllers.panels;

import controllers.buttons.KeyboardController;
import controllers.display.DisplayController;
import controllers.display.DisplayDispatcher;
import services.OpenTransaction;
import views.panels.DuringReturnTransaction;

public class DuringReturnTransactionController {
    public DuringReturnTransactionController() {

        DuringReturnTransaction duringReturnTransaction = ViewManager.getInstance().getDuringReturn();

        DisplayController displayControllerTotal = new DisplayController(duringReturnTransaction.getDisplayTotal());
        OpenTransaction.addObserver(displayControllerTotal);

        DisplayController displayControllerTopAmount = new DisplayController(duringReturnTransaction.getDisplayTopAmount());
        DisplayDispatcher.addDisplay("RETURN-REGISTER-AMOUNT", displayControllerTopAmount);

        DisplayController displayControllerTopTotal = new DisplayController(duringReturnTransaction.getDisplayTopTotal());
        DisplayDispatcher.addDisplay("RETURN-REGISTER-TOTAL", displayControllerTopTotal);

        new KeyboardController(duringReturnTransaction);
    }
}

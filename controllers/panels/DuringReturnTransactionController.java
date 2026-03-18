package controllers.panels;

import controllers.buttons.KeyboardController;
import controllers.display.DisplayController;
import controllers.display.DisplayDispatcher;
import controllers.display.DisplayReturnController;
import services.OpenTransaction;
import views.panels.DuringReturnTransaction;

public class DuringReturnTransactionController {
    public DuringReturnTransactionController() {

        DuringReturnTransaction duringReturnTransaction = ViewManager.getInstance().getDuringReturn();

        DisplayController displayControllerTotal = new DisplayController(duringReturnTransaction.getDisplayTotal());
        OpenTransaction.addObserver(displayControllerTotal);

        DisplayController displayControllerTopAmount = new DisplayController(duringReturnTransaction.getDisplayTopAmount());
        DisplayDispatcher.addDisplay("RETURN-REGISTER-AMOUNT", displayControllerTopAmount);

        DisplayController displayControllerTopTotal = new DisplayReturnController(duringReturnTransaction.getDisplayTopTotal());
        OpenTransaction.addObserver(displayControllerTopTotal);
        DisplayDispatcher.addDisplay("RETURN-REGISTER-TOTAL", displayControllerTopTotal);

        new KeyboardController(duringReturnTransaction);
    }
}

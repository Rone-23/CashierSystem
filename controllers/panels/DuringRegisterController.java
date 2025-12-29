package controllers.panels;

import controllers.buttons.KeyboardController;
import controllers.display.DisplayController;
import controllers.display.DisplayDispatcher;
import services.OpenTransaction;
import views.panels.DuringRegister;

public class DuringRegisterController {
    public DuringRegisterController() {

        DuringRegister duringRegister = ViewManager.getInstance().getDuringRegister();

        DisplayController displayControllerTotal = new DisplayController(duringRegister.getDisplayTotal());
//        DisplayDispatcher.addDisplay("REGISTER-TOTAL", displayControllerTotal);
        OpenTransaction.addObserver(displayControllerTotal);

        DisplayController displayControllerTopAmount = new DisplayController(duringRegister.getDisplayTopAmount());
        DisplayDispatcher.addDisplay("REGISTER-AMOUNT", displayControllerTopAmount);

        DisplayController displayControllerTopTotal = new DisplayController(duringRegister.getDisplayTopTotal());
        DisplayDispatcher.addDisplay("REGISTER-TOTAL", displayControllerTopTotal);

        new KeyboardController(duringRegister);


    }
}

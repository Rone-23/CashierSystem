package controllers.panelControllers;

import controllers.ViewManager;
import controllers.buttons.KeyboardController;
import controllers.display.ContentAmountController;
import controllers.display.DisplayController;
import services.OpenTransaction;
import views.panels.DuringRegister;

public class DuringRegisterController {
    public DuringRegisterController(ContentAmountController contentAmountController) {
        DuringRegister duringRegister = ViewManager.getInstance().getDuringRegister();

        DisplayController displayController = new DisplayController(duringRegister.getDisplayTotal());
        new KeyboardController(duringRegister.getKeyboard(), duringRegister.getDisplayTop(), contentAmountController);

        OpenTransaction.addObserver(displayController);



    }
}

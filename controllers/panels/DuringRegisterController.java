package controllers.panels;

import controllers.buttons.KeyboardController;
import controllers.display.ContentController;
import controllers.display.DisplayController;
import services.OpenTransaction;
import views.panels.DuringRegister;

public class DuringRegisterController {
    public DuringRegisterController(ContentController contentController) {

        DuringRegister duringRegister = ViewManager.getInstance().getDuringRegister();

        DisplayController displayController = new DisplayController(duringRegister.getDisplayTotal());
        OpenTransaction.addObserver(displayController);

        KeyboardController keyboardController = new KeyboardController(duringRegister.getKeyboard(), duringRegister.getDisplayTop(), contentController);
        ContentController.addObserver(keyboardController);



    }
}

package controllers.panels;

import controllers.buttons.KeyboardController;
import controllers.display.DisplayController;
import controllers.display.DisplayDispatcher;
import views.panels.DuringCodeEnter;

public class DuringCodeEnterController {
    public DuringCodeEnterController(){
        DuringCodeEnter duringCodeEnter = ViewManager.getInstance().getDuringCodeEnter();

        DisplayController displayController = new DisplayController(duringCodeEnter.getInputDisplay());
        DisplayDispatcher.addDisplay("CODE-DISPLAY", displayController);

        new KeyboardController(duringCodeEnter);

    }
}

package controllers.panels;

import controllers.buttons.KeyboardController;
import controllers.display.DisplayController;
import controllers.display.DisplayDispatcher;
import views.panels.DuringPause;

public class DuringPauseController {
    public DuringPauseController(){
        DuringPause duringPause = ViewManager.getInstance().getDuringPause();

        DisplayController displayController = new DisplayController(duringPause.getInputDisplay());
        DisplayDispatcher.addDisplay("PAUSE-CODE-DISPLAY", displayController);

        new KeyboardController(duringPause);

    }
}

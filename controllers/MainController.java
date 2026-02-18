package controllers;

import controllers.buttons.KeyboardController;
import controllers.buttons.UtilityController;
import controllers.display.ContentController;
import controllers.display.DisplayDispatcher;
import controllers.display.DisplayItemController;
import controllers.panels.DuringArticlesController;
import controllers.panels.DuringRegisterController;
import services.OpenTransaction;

import java.sql.SQLException;

public class MainController {
    public MainController() throws SQLException {
        ContentController contentController = new ContentController();
        OpenTransaction.addObserver(contentController);
        new DuringRegisterController();
        new DuringArticlesController();

        DisplayItemController displayItemController = new DisplayItemController();
        OpenTransaction.addObserver(displayItemController);

        new UtilityController();
        DisplayDispatcher.activeDisplayForAmount();
        KeyboardController.addListener(contentController);
        ContentController.clearContent();
    }


}

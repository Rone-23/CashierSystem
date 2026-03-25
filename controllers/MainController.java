package controllers;

import controllers.buttons.KeyboardController;
import controllers.buttons.UtilityController;
import controllers.panels.*;
import controllers.transaction.ContentController;
import controllers.display.DisplayDispatcher;
import controllers.display.DisplayItemController;
import services.OpenTransaction;

import java.sql.SQLException;

public class MainController {
    public MainController() throws SQLException {
        ContentController contentController = new ContentController();
        OpenTransaction.addObserver(contentController);
        new DuringRegisterController();
        new DuringArticlesController();
        new DuringCodeEnterController();
        new DuringReturnTransactionController();
        new DuringPauseController();

        DisplayItemController displayItemController = new DisplayItemController();
        OpenTransaction.addObserver(displayItemController);

        new UtilityController();
        DisplayDispatcher.activeDisplayForAmount();
        KeyboardController.addListener(contentController);
        ContentController.clearContent();
    }


}

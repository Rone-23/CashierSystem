package controllers;

import controllers.buttons.UtilityController;
import controllers.display.ContentController;
import controllers.display.DisplayItemController;
import controllers.panels.DuringArticlesController;
import controllers.panels.DuringRegisterController;
import services.OpenTransaction;

import java.sql.SQLException;

public class MainController {
    public MainController() throws SQLException {
        ContentController contentController = new ContentController();
        OpenTransaction.addObserver(contentController);
        new DuringRegisterController(contentController);
        new DuringArticlesController(contentController);

        DisplayItemController displayItemController = new DisplayItemController();
        OpenTransaction.addObserver(displayItemController);

        new UtilityController();
    }


}

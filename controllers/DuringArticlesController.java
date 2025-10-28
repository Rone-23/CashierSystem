package controllers;

import controllers.buttons.KeyboardController;
import controllers.display.ContentAmountController;
import controllers.display.DisplayController;
import services.OpenTransaction;
import views.panels.DuringArticles;

public class DuringArticlesController {
    public DuringArticlesController(ContentAmountController contentAmountController){
        DuringArticles duringArticles = ViewManager.getInstance().getDuringArticles();

        DisplayController displayController = new DisplayController(duringArticles.getDisplay());
        new KeyboardController(duringArticles.getKeyboard(), duringArticles.getDisplay(), contentAmountController);

        OpenTransaction.addObserver(displayController);

    }
}

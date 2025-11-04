package controllers.panels;

import controllers.buttons.FilterController;
import controllers.display.DisplayArticleController;
import controllers.buttons.KeyboardController;
import controllers.display.ContentController;
import controllers.display.DisplayController;
import services.OpenTransaction;
import views.panels.DuringArticles;



public class DuringArticlesController {
    public DuringArticlesController(ContentController contentController){

        DuringArticles duringArticles = ViewManager.getInstance().getDuringArticles();

        DisplayController displayController = new DisplayController(duringArticles.getDisplay());
        OpenTransaction.addObserver(displayController);

        KeyboardController keyboardController = new KeyboardController(duringArticles.getKeyboard(), duringArticles.getDisplay(), contentController);
        ContentController.addObserver(keyboardController);

        DisplayArticleController displayArticleController = new DisplayArticleController();
        OpenTransaction.addObserver(displayArticleController);
        FilterController.addObserver(displayArticleController);

        new FilterController(duringArticles.getArticleFilterButtonCluster());



    }
}

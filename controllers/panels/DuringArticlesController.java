package controllers.panels;

import controllers.buttons.FilterController;
import controllers.display.DisplayArticleController;
import controllers.buttons.KeyboardController;
import controllers.display.DisplayController;
import controllers.display.DisplayDispatcher;
import services.OpenTransaction;
import views.panels.DuringArticles;



public class DuringArticlesController {
    public DuringArticlesController(){

        DuringArticles duringArticles = ViewManager.getInstance().getDuringArticles();

        DisplayController displayController = new DisplayController(duringArticles.getDisplay());
        DisplayDispatcher.addDisplay("ARTICLES-SPLIT", displayController);
        OpenTransaction.addObserver(displayController);

        new KeyboardController(duringArticles);

        DisplayArticleController displayArticleController = new DisplayArticleController();
        OpenTransaction.addObserver(displayArticleController);
        FilterController.addObserver(displayArticleController);

        new FilterController(duringArticles.getArticleFilterButtonCluster());



    }
}

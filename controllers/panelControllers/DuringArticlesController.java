package controllers.panelControllers;

import controllers.ViewManager;
import controllers.buttons.ArticleFilterButtonClusterController;
import controllers.buttons.DisplayScrollableArticlesButtonsController;
import controllers.buttons.KeyboardController;
import controllers.display.ContentAmountController;
import controllers.display.DisplayController;
import services.OpenTransaction;
import views.panels.DuringArticles;


public class DuringArticlesController {
    public DuringArticlesController(ContentAmountController contentAmountController){
        DuringArticles duringArticles = ViewManager.getInstance().getDuringArticles();

        DisplayController displayController = new DisplayController(duringArticles.getDisplay());
        OpenTransaction.addObserver(displayController);
        new KeyboardController(duringArticles.getKeyboard(), duringArticles.getDisplay(), contentAmountController);
        DisplayScrollableArticlesButtonsController displayScrollableArticlesButtonsController = new DisplayScrollableArticlesButtonsController();
        ArticleFilterButtonClusterController.addObserver(displayScrollableArticlesButtonsController);
        OpenTransaction.addObserver(displayScrollableArticlesButtonsController);
        new ArticleFilterButtonClusterController(duringArticles.getArticleFilterButtonCluster());



    }
}

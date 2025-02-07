package controllers;

import services.display.AmountToUse;
import views.ArticlesPanel;
import views.MainPanel;
import views.leftPanel.LeftPanel;
import views.rightPanel.RightPanel;

public class MainController {
        LeftPanel leftPanel;
        RightPanel rightPanel;
    public MainController(MainPanel mainPanel, ArticlesPanel articlesPanel){

        AmountToUse amountToUse = new AmountToUse();
        this.leftPanel = mainPanel.getLeftPanel();
        this.rightPanel = mainPanel.getRightPanel();
        new KeyboardController(this.leftPanel.getKeyboardPanel().getKeyboard(), leftPanel.getDisplayPanel(), amountToUse);
        new UtilityController(this.leftPanel.getKeyboardPanel().getKeyboard(), leftPanel.getKeyboardPanel().getUtility(), amountToUse);
        new TopRightController(mainPanel,articlesPanel);
        new ArticlePanelController(mainPanel,articlesPanel);
    }
}

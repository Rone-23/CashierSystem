package controllers;

import controllers.display.AmountToUse;
import services.OpenTransaction;
import services.SQL_Connect;
import views.ArticlesPanel;
import views.MainPanel;
import views.leftPanel.LeftPanel;
import views.rightPanel.RightPanel;

public class MainController {
        LeftPanel leftPanel;
        RightPanel rightPanel;
    public MainController(MainPanel mainPanel, ArticlesPanel articlesPanel){
        //TODO: This OpenTransaction.class need to be created every time that you start transaction not to make it here in main controller
        OpenTransaction openTransaction = new OpenTransaction(Integer.parseInt(SQL_Connect.getInstance().getLastTimeStamp().split(" ")[0].split("-")[2]));
        AmountToUse amountToUse = new AmountToUse();
        this.leftPanel = mainPanel.getLeftPanel();
        this.rightPanel = mainPanel.getRightPanel();
        new KeyboardController(this.leftPanel.getKeyboardPanel().getKeyboard(), leftPanel.getDisplayPanel(), amountToUse);
        new UtilityController(this.leftPanel.getKeyboardPanel().getKeyboard(), leftPanel.getKeyboardPanel().getUtility(), amountToUse, openTransaction);
        new TopRightController(mainPanel,articlesPanel);
        new ArticlePanelController(mainPanel,articlesPanel,openTransaction);
    }
}

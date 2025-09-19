package controllers;

import controllers.display.DisplayAmountController;
import controllers.display.DisplayItemsController;
import services.Item;
import services.OpenTransaction;
import services.SQL_Connect;
import views.ArticlesPanel;
import views.MainPanel;
import views.leftPanel.LeftPanel;
import views.rightPanel.RightPanel;

public class MainController {
    private static OpenTransaction openTransaction;
    private static final MakeTransaction makeTransaction = new MakeTransaction();
    public MainController(MainPanel mainPanel, ArticlesPanel articlesPanel){
        //TODO: This OpenTransaction.class need to be created every time that you start transaction not to make it here in main controller
        LeftPanel leftPanel = mainPanel.getLeftPanel();
        DisplayAmountController displayAmountController = new DisplayAmountController();
        DisplayItemsController displayItemsController = new DisplayItemsController(leftPanel.getDisplayPanel());
        OpenTransaction.addObserver(displayItemsController);
        createOpenTransaction();
        //TODO: Right panel should implement all the buttons like vklad vratka etc
        RightPanel rightPanel = mainPanel.getRightPanel();
        new KeyboardController(leftPanel.getKeyboardPanel().getKeyboard(), leftPanel.getDisplayPanel(), displayAmountController);
        new UtilityController(leftPanel.getKeyboardPanel().getKeyboard(), leftPanel.getKeyboardPanel().getUtility(), displayAmountController);
        new TopRightController(mainPanel,articlesPanel);
        new ArticlePanelController(mainPanel,articlesPanel);
    }

    public static void createOpenTransaction(){
        openTransaction = new OpenTransaction(Integer.parseInt(SQL_Connect.getInstance().getLastTimeStamp().split(" ")[0].split("-")[2]));
    }

    public static void makeTransaction(){
        makeTransaction.makeTransaction(openTransaction);
        createOpenTransaction();
    }

    public static void addItem(Item item){
        openTransaction.addItem(item);
    }
}

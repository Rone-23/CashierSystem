package controllers;

import controllers.display.ContentAmountController;
import controllers.display.DisplayItemsController;
import services.Item;
import services.OpenTransaction;
import services.SQL_Connect;
import viewsRework.Components.Display;
import viewsRework.Components.DisplayScrollableItems;
import viewsRework.Components.Keyboard;
import viewsRework.panels.DuringArticles;
import viewsRework.panels.DuringRegister;

public class MainController {
    private static OpenTransaction openTransaction;
    private static final MakeTransaction makeTransaction = new MakeTransaction();
    public MainController(DuringRegister duringRegister, DuringArticles duringArticles){
        Keyboard keyboard = (Keyboard) duringRegister.getKeyboard();
        Display display = (Display) duringRegister.getDisplay();
        //TODO: This OpenTransaction.class need to be created every time that you start transaction not to make it here in main controller
        ContentAmountController contentAmountController = new ContentAmountController();
        DisplayItemsController displayItemsController = new DisplayItemsController((DisplayScrollableItems) duringRegister.getDisplayScrollableItems());
        OpenTransaction.addObserver(displayItemsController);
        createOpenTransaction();
        //TODO: Right panel should implement all the buttons like vklad vratka etc
        new KeyboardController(keyboard, display, contentAmountController);
        new ButtonClusterController(duringRegister,duringArticles);
        new ArticleController(duringArticles);
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

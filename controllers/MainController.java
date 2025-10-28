package controllers;

import controllers.buttons.ButtonClusterController;
import controllers.buttons.DisplayScrollableArticlesButtonsController;
import controllers.display.ContentAmountController;
import controllers.display.DisplayItemsController;
import services.Item;
import services.OpenTransaction;
import services.SQL_Connect;

import java.sql.SQLException;

public class MainController {
    private static final MakeTransaction makeTransaction = new MakeTransaction();
    private static OpenTransaction openTransaction;

    public MainController() throws SQLException {
        //TODO: This OpenTransaction.class need to be created every time that you start transaction not to make it here in main controller



        ContentAmountController contentAmountController = new ContentAmountController();
        new DuringRegisterController(contentAmountController);
        new DuringArticlesController(contentAmountController);

        DisplayItemsController displayItemsController = new DisplayItemsController();

        new ButtonClusterController();
        DisplayScrollableArticlesButtonsController displayScrollableArticlesButtonsController = new DisplayScrollableArticlesButtonsController();

        OpenTransaction.addObserver(displayItemsController);
        OpenTransaction.addObserver(displayScrollableArticlesButtonsController);
        createOpenTransaction();
    }

    public static void createOpenTransaction() {
        openTransaction = new OpenTransaction(Integer.parseInt(SQL_Connect.getInstance().getLastTimeStamp().split(" ")[0].split("-")[2]));
    }

    public static void makeTransaction() {
        makeTransaction.makeTransaction(openTransaction);
        createOpenTransaction();
    }

    public static void addItem(Item item) {
        openTransaction.addItem(item);
    }
}

package controllers;

import controllers.buttons.UtilityController;
import controllers.display.ContentController;
import controllers.display.DisplayItemController;
import controllers.transaction.MakeTransaction;
import controllers.panels.DuringArticlesController;
import controllers.panels.DuringRegisterController;
import services.OpenTransaction;
import services.SQL_Connect;

import java.sql.SQLException;

public class MainController {
    private static final MakeTransaction makeTransaction = new MakeTransaction();
    private static OpenTransaction openTransaction;

    public MainController() throws SQLException {
        //TODO: This OpenTransaction.class need to be created every time that you start transaction not to make it here in main controller

        ContentController contentController = new ContentController();
        OpenTransaction.addObserver(contentController);
        new DuringRegisterController(contentController);
        new DuringArticlesController(contentController);

        DisplayItemController displayItemController = new DisplayItemController();
        OpenTransaction.addObserver(displayItemController);

        new UtilityController();

        createOpenTransaction();
    }

    public static void createOpenTransaction() {
        openTransaction = new OpenTransaction(Integer.parseInt(SQL_Connect.getInstance().getLastTimeStamp().split(" ")[0].split("-")[2]));
        ContentController.addObserver(openTransaction);
    }

    public static void makeTransaction() {
        makeTransaction.makeTransaction(openTransaction);
        createOpenTransaction();
    }

}

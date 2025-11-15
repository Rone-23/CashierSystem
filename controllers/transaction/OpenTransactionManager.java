package controllers.transaction;

import controllers.display.ContentController;
import services.OpenTransaction;
import services.SQL_Connect;

public class OpenTransactionManager implements OpenTransactionObserver{
    private static OpenTransactionManager instance;
    private OpenTransaction openTransaction;
    private OpenTransactionManager(){
        openTransaction = createOpenTransaction();
        OpenTransaction.addObserver(this);
    }

    public static OpenTransactionManager getInstance(){
        if (instance == null) {
            instance = new OpenTransactionManager();
        }
        return instance;
    }

    private OpenTransaction createOpenTransaction(){
        try {
            openTransaction = new OpenTransaction(Integer.parseInt(SQL_Connect.getInstance().getLastTimeStamp().split(" ")[0].split("-")[2]));
        } catch (NumberFormatException e) {

        }
        ContentController.addObserver(openTransaction);
        return openTransaction;
    }
    public OpenTransaction getOpenTransaction(){
        return openTransaction;
    }

    @Override
    public void onDestroy() {
        openTransaction = createOpenTransaction();
    }
}

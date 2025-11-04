package controllers.transaction;

import services.OpenTransaction;
import services.SQL_Connect;

public class OpenTransactionManager implements OpenTransactionObserver{
    private static OpenTransactionManager instance;
    private OpenTransaction openTransaction;
    private OpenTransactionManager(){
        openTransaction = new OpenTransaction(Integer.parseInt(SQL_Connect.getInstance().getLastTimeStamp().split(" ")[0].split("-")[2]));
        OpenTransaction.addObserver(this);
    }

    public static OpenTransactionManager getInstance(){
        if (instance == null) {
            instance = new OpenTransactionManager();
        }
        return instance;
    }

    public OpenTransaction getOpenTransaction(){
        return openTransaction;
    }

    @Override
    public void onDestroy() {
        openTransaction = new OpenTransaction(Integer.parseInt(SQL_Connect.getInstance().getLastTimeStamp().split(" ")[0].split("-")[2]));
    }
}

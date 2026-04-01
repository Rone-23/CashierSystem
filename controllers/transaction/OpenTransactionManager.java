package controllers.transaction;

import controllers.display.DisplayDispatcher;
import controllers.panels.ViewManager;
import services.Item;
import services.OpenTransaction;
import services.Users.CashierObserver;
import services.Users.CashierSession;

import java.awt.event.ActionEvent;

public class OpenTransactionManager implements OpenTransactionObserver, CashierObserver {
    private static OpenTransactionManager instance;
    private OpenTransaction openTransaction;
    private int cashierId = -1;

    private OpenTransactionManager(){
        OpenTransaction.addObserver(this);
        CashierSession.addObserver(this);
    }

    public static OpenTransactionManager getInstance(){
        if (instance == null) {
            instance = new OpenTransactionManager();
        }
        return instance;
    }

    private OpenTransaction createOpenTransaction(){
        try {
            openTransaction = new OpenTransaction(cashierId);
            ViewManager.getInstance().getStatusBar().setStatus("Nie");
        } catch (NumberFormatException ignored) {
        }
        ContentController.addObserver(openTransaction);

        return openTransaction;
    }

    public OpenTransaction getOpenTransaction(){
        if(openTransaction == null){
            return createOpenTransaction();
        }
        return openTransaction;
    }

    public void addPayment(ActionEvent actionEvent){
        getOpenTransaction().pay(actionEvent);
    }

    public void addItem(Item item){
        getOpenTransaction().addItem(item);
        System.out.printf("Name %s Amount %d Price %d\n",item.getName(), item.getAmount(), item.getPrice());
    }

    public void removeItem(Item item){
        getOpenTransaction().removeItem(item);
    }

    public void loadHistoricalTransaction(int transactionID, String date, Item[] items){
        if (this.openTransaction != null) {
            ContentController.removeObserver(this.openTransaction);
        }

        if(items.length==0){
            throw new IllegalStateException("Dopytovaná transakcia neobsahuje žiaden tovar určený na vrátenie.");
        }

        this.openTransaction = new OpenTransaction(transactionID, date);

        ContentController.addObserver(openTransaction);

        this.openTransaction.loadItemsIntoTransaction(items);
    }

    @Override
    public void paymentDone() {
        MakeTransaction makeTransaction = new MakeTransaction();
        makeTransaction.makeTransaction(openTransaction);
        ViewManager.getInstance().showIdle();
        ViewManager.getInstance().returnToDefault();
        DisplayDispatcher.activeDisplayForAmount();
    }

    @Override
    public void onDestroy() {
        openTransaction = null;
        ViewManager.getInstance().getStatusBar().setStatus("---");
        ViewManager.getInstance().getStatusBar().setTransactionId("---");
    }

    @Override
    public void onCreate(OpenTransaction openTransaction) {
        ViewManager.getInstance().getStatusBar().setTransactionId(String.valueOf(openTransaction.getTransactionID()));
    }

    @Override
    public void onCashierLogin(int cashierId) {
        this.cashierId = cashierId;
        ViewManager.getInstance().getStatusBar().setCashierId(String.valueOf(cashierId));
    }
}

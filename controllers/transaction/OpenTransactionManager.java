package controllers.transaction;

import services.ContentController;
import controllers.panels.ViewManager;
import services.Item;
import services.OpenTransaction;

import java.awt.event.ActionEvent;

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
            openTransaction = new OpenTransaction();
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

    @Override
    public void paymentDone() {
        MakeTransaction makeTransaction = new MakeTransaction();
        makeTransaction.makeTransaction(openTransaction);
        ViewManager.getInstance().showIdle();

    }

    @Override
    public void onDestroy() {
        openTransaction = null;
    }
}

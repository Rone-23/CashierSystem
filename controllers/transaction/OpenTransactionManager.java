package controllers.transaction;

import controllers.display.ContentController;
import controllers.panels.ViewManager;
import services.Item;
import services.OpenTransaction;

import java.awt.event.ActionEvent;
import java.sql.SQLException;

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
        System.out.printf("Action event when addPayment: %s \n",actionEvent.getActionCommand());
        getOpenTransaction().pay(actionEvent);
    }

    public void addItem(Item item){
        try{
            getOpenTransaction().addItem(item);
            System.out.printf("Name %s Amount %d Price %d\n",item.getName(), item.getAmount(), item.getPrice());
        }catch (SQLException e) {
            System.out.printf(String.valueOf(e));
        }
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

package controllers.transaction;

import controllers.display.ContentController;
import controllers.panels.ViewManager;
import services.OpenTransaction;
import services.SQL_Connect;

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
            openTransaction = new OpenTransaction(Integer.parseInt(SQL_Connect.getInstance().getLastTimeStamp().split(" ")[0].split("-")[2]));
        } catch (NumberFormatException ignored) {
        }
        ContentController.addObserver(openTransaction);
        return openTransaction;
    }

    public OpenTransaction getOpenTransaction(){
        return openTransaction;
    }

    public void addPayment(ActionEvent actionEvent){
        System.out.printf("Action event when addPayment: %s \n",actionEvent.getActionCommand());
        switch (actionEvent.getActionCommand()){
            case "Hotovost" -> {
                openTransaction.payCash();
            }

            case "Karta" -> {
                openTransaction.payCard();
            }

            case "Stravenky" -> {
                openTransaction.payFoodTicket();
            }

            case "Poukážky" -> {
                openTransaction.payVoucher();
            }
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
        openTransaction = createOpenTransaction();
    }
}

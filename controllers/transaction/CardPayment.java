package controllers.transaction;

import controllers.panels.ViewManager;
import services.OpenTransaction;

public class CardPayment implements OpenTransactionObserver {
    OpenTransaction openTransaction;
    public CardPayment(){
        openTransaction = OpenTransactionManager.getInstance().getOpenTransaction();

        MakeTransaction makeTransaction = new MakeTransaction();

        makeTransaction.makeTransaction(openTransaction);

        ViewManager.getInstance().showIdle();
    }

//    @Override
//    public void onCreate(OpenTransaction openTransaction) {
//        this.openTransaction = openTransaction;
//    }
}

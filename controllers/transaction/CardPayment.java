package controllers.transaction;

import controllers.panels.ViewManager;

public class CardPayment {
    public CardPayment(){
        MakeTransaction makeTransaction = new MakeTransaction();

        makeTransaction.makeTransaction(OpenTransactionManager.getInstance().getOpenTransaction());

        ViewManager.getInstance().showIdle();
    }
}

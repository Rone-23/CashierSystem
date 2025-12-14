package controllers.transaction;

import assets.ButtonSet;
import controllers.panels.ViewManager;
import services.OpenTransaction;

public class CashPayment implements OpenTransactionObserver{
    OpenTransaction openTransaction;

    public CashPayment(){
        openTransaction = OpenTransactionManager.getInstance().getOpenTransaction();
        ViewManager.getInstance().getDuringRegister().replaceButton(ButtonSet.UTILITY_NAMES.getStringLabels(), ButtonSet.CASH_NAMES.getStringLabels());


    }

    private void closeTransaction(){
        MakeTransaction makeTransaction = new MakeTransaction();
        makeTransaction.makeTransaction(openTransaction);
        ViewManager.getInstance().showIdle();
    }
}

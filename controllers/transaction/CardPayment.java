package controllers.transaction;

public class CardPayment {
    public CardPayment(){
        MakeTransaction makeTransaction = new MakeTransaction();

        makeTransaction.makeTransaction(OpenTransactionManager.getInstance().getOpenTransaction());

    }
}

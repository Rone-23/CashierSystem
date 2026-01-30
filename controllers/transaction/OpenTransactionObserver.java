package controllers.transaction;

import services.Item;
import services.OpenTransaction;

public interface OpenTransactionObserver {
    default void onCreate(OpenTransaction openTransaction) {}
    default void onItemAdd(Item item) {}
    default void onItemRemove(Item item){}
    default void onDestroy() {}
    default void onAddedPayment(int toPayLeft,String typeOfPayment, int addedAmount) {}
    default void paymentDone(){}
}

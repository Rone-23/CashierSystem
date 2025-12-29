package controllers.transaction;

import services.Item;
import services.OpenTransaction;

public interface OpenTransactionObserver {
    default void onCreate(OpenTransaction openTransaction) {}
    default void onItemAdd(Item item) {}
    default void onItemRemove(Item item){}
    default void onDestroy() {}
    default void onAddedPayment(Double toPayLeft) {}
    default void paymentDone(){}
}

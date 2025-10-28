package controllers.openTransaction;

import services.Item;
import services.OpenTransaction;

public interface OpenTransactionObserver {
    default void onCreate(OpenTransaction openTransaction) {}
    default void onItemAdd(Item item) {}
    default void onDestroy() {}
}

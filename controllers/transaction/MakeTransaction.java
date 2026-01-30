package controllers.transaction;

import services.Item;
import services.OpenTransaction;
import utility.Receipt;


public class MakeTransaction{
    public void makeTransaction( OpenTransaction openTransaction ){


        Item[] itemArray=openTransaction.getItemsInTransaction().values().toArray(new Item[0]);
        Item.setTotalAmountZero();
        Receipt.makeReceipt(itemArray, openTransaction.getTransactionDateTime(),openTransaction.getTransactionID());
        openTransaction.openTransactionDestroy();
    }

}

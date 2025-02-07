package controllers;

import services.Item;
import services.OpenTransaction;
import services.SQL_Connect;
import utility.JSON;
import utility.Receipt;

public class MakeTransaction {
    SQL_Connect sql;
    public MakeTransaction(SQL_Connect sql, OpenTransaction openTransaction ){
        this.sql = sql;
        Item[] itemArray=openTransaction.getItemsInTransaction().values().toArray(new Item[0]);
        String itemsJSON =JSON.makeJSON(openTransaction.getItemsInTransaction());
        sql.logToDB(itemsJSON, itemArray.length);
        itemArray[0].setTotalAmountZero();
        Receipt.makeReceipt(itemArray, openTransaction.getTransactionDateTime(),openTransaction.getTransactionID());
    }



    //TODO: Remove from article stock
}

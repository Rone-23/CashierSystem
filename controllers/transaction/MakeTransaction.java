package controllers.transaction;

import services.Item;
import services.OpenTransaction;
import services.SQL_Connect;
import utility.Receipt;

import java.sql.SQLException;

public class MakeTransaction{
    public void makeTransaction( OpenTransaction openTransaction ){


        Item[] itemArray=openTransaction.getItemsInTransaction().values().toArray(new Item[0]);
        for (Item item : itemArray){
            try {
                int amount = (int) Math.round(item.getAmount());
                int cents = (int) Math.round(item.getPrice() * 100.0);
                SQL_Connect.getInstance().logToDB(openTransaction.getTransactionID(),SQL_Connect.getInstance().getArticleID(item.getName()), amount, cents);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }

        Item.setTotalAmountZero();
        Receipt.makeReceipt(itemArray, openTransaction.getTransactionDateTime(),openTransaction.getTransactionID());
        openTransaction.openTransactionDestroy();
    }

}

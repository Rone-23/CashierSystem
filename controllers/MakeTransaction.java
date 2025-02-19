package controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import services.Item;
import services.OpenTransaction;
import services.SQL_Connect;
import utility.Receipt;

public class MakeTransaction {
    public MakeTransaction(){
    }
    public void makeTransaction( OpenTransaction openTransaction ){
        ObjectMapper objectMapper = new ObjectMapper();
        Item[] itemArray=openTransaction.getItemsInTransaction().values().toArray(new Item[0]);
        for (Item item : itemArray){
            SQL_Connect.getInstance().removeFromStock(item.getAmount(),item.getName());
        }
        try {
            SQL_Connect.getInstance().logToDB(objectMapper.writeValueAsString(openTransaction.getItemsInTransaction()), itemArray.length, openTransaction.getTransactionID());
        }catch (JsonProcessingException e){
            System.out.println(e);
        }
        Item.setTotalAmountZero();
        Receipt.makeReceipt(itemArray, openTransaction.getTransactionDateTime(),openTransaction.getTransactionID());
    }

}

package controllers.transaction;

import services.Item;
import services.OpenTransaction;
import utility.Receipt;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;


public class MakeTransaction{
    private static final String FILE_PATH = "./Receipts";

    public void makeTransaction( OpenTransaction openTransaction ){
        Item[] itemArray=openTransaction.getItemsInTransaction().values().toArray(new Item[0]);
        Item.setTotalAmountZero();
        StringBuilder receipt = Receipt.makeReceipt(itemArray, openTransaction.getTransactionDateTime(),openTransaction.getTransactionID());
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(String.format("%s/receipt_%d.txt",FILE_PATH,openTransaction.getTransactionID())))) {
            writer.write(receipt.toString());
            System.out.println("Receipt successfully saved to " + FILE_PATH);
        } catch (IOException e) {
            System.err.println("Error writing receipt file: " + e.getMessage());
        }
        openTransaction.openTransactionDestroy();
    }

}

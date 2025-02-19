package services;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

public class OpenTransaction {
    private static int transactionID;
    private final LocalDateTime  transactionDateTime;
    private final Map<String,Item> itemsInTransaction= new HashMap<>();
    private final DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
    //day is stored using sql.getLastTimeStamp() and split it into day ("dd-MM-yyyy HH:mm:ss")
    public OpenTransaction(int day ){

        this.transactionDateTime = LocalDateTime.now();
        this.transactionDateTime.format(dtf);

        try{if(day==this.transactionDateTime.getDayOfMonth()){
            transactionID++;
        }else{
            transactionID = 0;
        }}catch (NullPointerException e){
            transactionID = 0;
        }
    }

    public void addItem(Item item){
        if (!this.itemsInTransaction.containsValue(item)){
            this.itemsInTransaction.put(item.getName(),item);
        }else if(this.itemsInTransaction.containsValue(item) && item.getClass()==ItemCountable.class){
            ItemCountable itemCountable = (ItemCountable) this.itemsInTransaction.get(item.getName());
            itemCountable.addAmount(item.getAmount());
        }else {
            ItemUncountable itemUncountable = (ItemUncountable) this.itemsInTransaction.get(item.getName());
            ItemUncountable itemUncountableToAdd = (ItemUncountable) item;
            itemUncountable.addWeight(itemUncountableToAdd.getWeight());

        }
    }

    public Map<String,Item> getItemsInTransaction(){return this.itemsInTransaction; }

    public String getTransactionDateTime(){return this.transactionDateTime.format(this.dtf); }

    public int getTransactionID(){return transactionID;}
}

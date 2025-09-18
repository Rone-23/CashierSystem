package services;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

public class OpenTransaction {
    private static int transactionID;
    private final LocalDateTime  transactionDateTime;
    private final Map<String,Item> itemsInTransaction= new HashMap<>();
    private final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
    private static Item latestItem;
    //day is stored using sql.getLastTimeStamp() and split it into day ("dd-MM-yyyy HH:mm:ss")
    public OpenTransaction(int lastTransactionDay ){

        this.transactionDateTime = LocalDateTime.now();
        this.transactionDateTime.format(dateTimeFormatter);

        try{if(lastTransactionDay==this.transactionDateTime.getDayOfMonth()){
            transactionID++;
        }else{
            transactionID = 0;
        }}catch (NullPointerException e){
            transactionID = 0;
        }
    }

    public void addItem(Item item){
        latestItem = item;
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

    public static Item getLatestItem(){return latestItem;}

    public String getTransactionDateTime(){return this.transactionDateTime.format(this.dateTimeFormatter); }

    public int getTransactionID(){return transactionID;}
}

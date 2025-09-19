package services;

import controllers.display.OpenTransactionObserver;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OpenTransaction {
    private static int transactionID;
    private final LocalDateTime  transactionDateTime;
    private final static List<OpenTransactionObserver> observerList = new ArrayList<>();
    private final Map<String,Item> itemsInTransaction= new HashMap<>();
    private final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
    //**day is stored using sql.getLastTimeStamp() and split it into day ("dd-MM-yyyy HH:mm:ss")**//
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
        for(OpenTransactionObserver observer : observerList){
            observer.onCreate(this);
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
        for(OpenTransactionObserver observer : observerList){
            observer.onItemAdd(item);
        }
    }

    public Map<String,Item> getItemsInTransaction(){return this.itemsInTransaction; }

    public String getTransactionDateTime(){return this.transactionDateTime.format(this.dateTimeFormatter); }

    public int getTransactionID(){return transactionID;}

    public static void addObserver(OpenTransactionObserver openTransactionObserver){observerList.add(openTransactionObserver);}

    public static void removeObserver(OpenTransactionObserver openTransactionObserver){observerList.remove(openTransactionObserver);}

    public void openTransactionDestroy(){
        for(OpenTransactionObserver observer : observerList){
            observer.onDestroy();
        }
    }
}

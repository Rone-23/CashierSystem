package services;

import controllers.display.ContentObserver;
import controllers.transaction.OpenTransactionObserver;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OpenTransaction implements ContentObserver {
    private static int transactionID;
    private final LocalDateTime  transactionDateTime;
    private final static List<OpenTransactionObserver> observerList = new ArrayList<>();
    private final Map<String,Item> itemsInTransaction= new HashMap<>();
    private final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
    private Double content = 1.0;
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
        if (!this.itemsInTransaction.containsKey(item.getName())){
            item.setAmount(content);
            this.itemsInTransaction.put(item.getName(),item);
        }else if(this.itemsInTransaction.containsKey(item.getName()) && item.getClass()==ItemCountable.class){
            ItemCountable itemCountable = (ItemCountable) this.itemsInTransaction.get(item.getName());
            itemCountable.addAmount(content);
            item=itemCountable;
        }else {
            ItemUncountable itemUncountable = (ItemUncountable) this.itemsInTransaction.get(item.getName());
            ItemUncountable itemUncountableToAdd = (ItemUncountable) item;
            itemUncountable.addWeight(itemUncountableToAdd.getWeight());
            item = itemUncountable;
        }
        for(OpenTransactionObserver observer : observerList){
            observer.onItemAdd(item);
        }
    }

    public Map<String,Item> getItemsInTransaction(){return this.itemsInTransaction; }

    public String getTransactionDateTime(){return this.transactionDateTime.format(this.dateTimeFormatter); }

    public int getTransactionID(){return transactionID;}

    public double getTotal(){
        double sum = 0;

        for(Item item : itemsInTransaction.values().toArray(new Item[0])){
            sum += item.getPrice()*item.getAmount();
        }
        return sum;
    }

    //Observer
    public static void addObserver(OpenTransactionObserver openTransactionObserver){observerList.add(openTransactionObserver);}

    public static void removeObserver(OpenTransactionObserver openTransactionObserver){observerList.remove(openTransactionObserver);}

    public void openTransactionDestroy(){
        for(OpenTransactionObserver observer : observerList){
            observer.onDestroy();
        }
    }

    @Override
    public void notifyContentUpdate(String content) {
        try {
            this.content = Double.valueOf(content);
        } catch (NumberFormatException e) {
            this.content = 1.0;
        }
    }
}

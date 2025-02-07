package services;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

public class OpenTransaction {
    private static int transactionID;
    private final LocalDateTime  transactionDateTime;
    private final Map<String,Item> itemsInTransaction= new HashMap<>();
    private final SQL_Connect sql;
    private final DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
    public OpenTransaction( SQL_Connect sql){

        this.sql = sql;
        this.transactionDateTime = LocalDateTime.now();
        this.transactionDateTime.format(dtf);

        if(Integer.parseInt(this.sql.getLastTimeStamp().split(" ")[0].split("-")[2])==this.transactionDateTime.getDayOfMonth()){
            transactionID++;
        }else{
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
            ItemUncountable itemUncountable = (ItemUncountable) item;

            //TODO: item. add vaha to list
        }
    }

    public Map<String,Item> getItemsInTransaction(){return this.itemsInTransaction; }

    public String getTransactionDateTime(){return this.transactionDateTime.format(this.dtf); }

    public int getTransactionID(){return transactionID;}



    //TODO: Make transaction
    //TODO: Create receipt
}

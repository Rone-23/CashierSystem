package services;

import controllers.notifications.NotificationController;
import controllers.transaction.ContentObserver;
import controllers.transaction.OpenTransactionObserver;

import java.awt.event.ActionEvent;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OpenTransaction implements ContentObserver {
    private final int transactionID;
    private final LocalDateTime  transactionDateTime;
    private final static List<OpenTransactionObserver> observerList = new ArrayList<>();
    private final Map<String,Item> itemsInTransaction= new HashMap<>();
    private Map<String,Item> returnedItems;
    private final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    private int content = 1;
    private int payedCard = 0;
    private int payedCash = 0;
    private int payedFoodTicket = 0;
    private int payedVoucher = 0;
    private boolean isReturn;
    private int total;
    //**EPSILON is there because of using Double**//
    private final Double EPSILON = 0.0000001;
    //**day is stored using sql.getLastTimeStamp() and split it into day ("dd-MM-yyyy HH:mm:ss")**//

    public OpenTransaction(){
        this.transactionDateTime = LocalDateTime.now();
        this.transactionDateTime.format(dateTimeFormatter);

        try {
            transactionID = SQL_Connect.getInstance().createTransaction(1,1);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        isReturn(false);
        for(OpenTransactionObserver observer : observerList){
            observer.onCreate(this);
        }
    }

    public OpenTransaction(int transactionID, String date){
        returnedItems = new HashMap<>();
        this.transactionDateTime = LocalDateTime.parse(date,dateTimeFormatter);
        this.transactionID = transactionID;
        isReturn(true);
        for(OpenTransactionObserver observer : observerList){
            observer.onCreate(this);
        }
    }

    public void addItem(Item item){
        if(item == null){return;}

        if (!itemsInTransaction.containsKey(item.getName())){
            try {
                SQL_Connect.getInstance().addToTransaction(
                        transactionID,
                        SQL_Connect.getInstance().getArticleID(item.getName()),
                        content,
                        item.getPrice()
                );
            } catch (SQLException e) {
                NotificationController.notifyObservers(e.toString(),5000);
                return;
            }
            item.setAmount(content);
            itemsInTransaction.put(item.getName(),item);
        }else {
            ItemCountable itemCountable = (ItemCountable) itemsInTransaction.get(item.getName());
            item=itemCountable;
            try {
                if(isReturn){
                    ItemCountable returnedItem = (ItemCountable) returnedItems.get(item.getName());
                    returnedItem.addAmount(-content);
                }else {
                    SQL_Connect.getInstance().addToTransaction(
                            transactionID,
                            SQL_Connect.getInstance().getArticleID(item.getName()),
                            content,
                            item.getPrice());
                }
            } catch (SQLException e) {
                NotificationController.notifyObservers(e.toString(),5000);
                return;
            }
            itemCountable.addAmount(content);
        }

        for(OpenTransactionObserver observer : observerList){
            observer.onItemAdd(item);
        }
    }

    public void removeItem(Item item){
        if(item==null){return;}

        if(itemsInTransaction.containsKey(item.getName())){
            try {
                if(isReturn){
                    if(!returnedItems.containsKey(item.getName())){
                        returnedItems.put(item.getName(),new ItemCountable(item.getName(),item.getPrice(),content));
                    }else{
                        ItemCountable itemInReturn = (ItemCountable) returnedItems.get(item.getName());
                        itemInReturn.addAmount(+content);
                    }
                } else {
                    SQL_Connect.getInstance().removeFromTransaction(
                            transactionID,
                            SQL_Connect.getInstance().getArticleID(item.getName()),
                            content
                    );
                }

                if (item.getAmount() - content <= 0) {
                    itemsInTransaction.remove(item.getName());

                    for (OpenTransactionObserver observer : observerList) {
                        observer.onItemRemove(item);
                    }
                } else {
                    ItemCountable itemCountable = (ItemCountable) itemsInTransaction.get(item.getName());
                    itemCountable.addAmount(-content);

                    for (OpenTransactionObserver observer : observerList) {
                        observer.onItemAdd(item);
                    }
                }
            }catch (SQLException e){
                NotificationController.notifyObservers(e.toString(),5000);
            }
        }
    }

    public void loadItemsIntoTransaction(Item[] items){
        for(Item item : items){
            itemsInTransaction.put(item.getName(),item);
            for(OpenTransactionObserver observer : observerList){
                observer.onItemAdd(item);
            }
        }
        total = getTotal();
    }

    public Map<String,Item> getItemsInTransaction(){return itemsInTransaction; }

    public Map<String,Item> getReturnedItems(){return returnedItems; }

    public String getTransactionDateTime(){return this.transactionDateTime.format(this.dateTimeFormatter); }

    public int getTransactionID(){return transactionID;}

    public int getTotal(){
        int sum = 0;

        for(Item item : itemsInTransaction.values().toArray(new Item[0])){
            sum += item.getPrice()*item.getAmount();
        }
        return sum;
    }

    public int getTotalWhenReturn(){
        return total;
    }

    public int getMissing(){
        return getTotal() - payedCard - payedCash - payedFoodTicket - payedVoucher;
    }

    public void pay(ActionEvent actionEvent){
        try {
            if(content>0){
                switch (actionEvent.getActionCommand()){
                    case "Hotovost" -> {
                        SQL_Connect.getInstance().registerPayment(content,"card", transactionID);
                        payedCash += content;
                    }
                    case "Karta" -> {
                        SQL_Connect.getInstance().registerPayment(content,"card", transactionID);
                        payedCard += content;
                    }
                    case "Stravenky" -> {
                        SQL_Connect.getInstance().registerPayment(content,"food_ticket", transactionID);
                        payedFoodTicket += content;
                    }
                    case "Poukážky" -> {
                        SQL_Connect.getInstance().registerPayment(content,"voucher", transactionID);
                        payedVoucher += content;
                    }
                }
                checkSum(actionEvent.getActionCommand());
            }
        } catch (SQLException e) {
            NotificationController.notifyObservers(e.toString(),5000);
        }
    }

    private void checkSum(String typeOfPayment){
        if(isReturn){
            for(OpenTransactionObserver observer : observerList){
                observer.paymentDone();
            }
            return;
        }

        for(OpenTransactionObserver observer : observerList){
            observer.onAddedPayment(getMissing(), typeOfPayment, content);
        }
        if(getMissing()<=EPSILON){
            for(OpenTransactionObserver observer : observerList){
                observer.paymentDone();
            }
        }
        System.out.printf("Missing cash in transaction is: %d\n", getTotal() - payedCard - payedCash - payedFoodTicket - payedVoucher);
    }

    private void isReturn(Boolean status){
        for(OpenTransactionObserver observer : observerList){
            observer.onReturnTransaction(status);
        }
        isReturn = status;
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
            this.content = Integer.parseInt(content);
        } catch (NumberFormatException e) {
            this.content = 1;
        }
    }
}

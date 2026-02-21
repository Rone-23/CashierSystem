package services;

import controllers.display.ContentObserver;
import controllers.notifications.NotificationController;
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
    private static int transactionID;
    private final LocalDateTime  transactionDateTime;
    private final static List<OpenTransactionObserver> observerList = new ArrayList<>();
    private final Map<String,Item> itemsInTransaction= new HashMap<>();
    private final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
    private int content = 1;
    private int payedCard = 0;
    private int payedCash = 0;
    private int payedFoodTicket = 0;
    private int payedVoucher = 0;
    //**EPSILON is there because of using Double**//
    private final Double EPSILON = 0.0000001;
    //**day is stored using sql.getLastTimeStamp() and split it into day ("dd-MM-yyyy HH:mm:ss")**//

    public OpenTransaction(){
        this.transactionDateTime = LocalDateTime.now();
        this.transactionDateTime.format(dateTimeFormatter);

        transactionID = SQL_Connect.getInstance().createTransaction(1,1);

        for(OpenTransactionObserver observer : observerList){
            observer.onCreate(this);
        }
    }

    public void addItem(Item item){
        if(item == null){return;}

        if (!itemsInTransaction.containsKey(item.getName())){
            try {
                SQL_Connect.getInstance().addToTransaction(transactionID,SQL_Connect.instance.getArticleID(item.getName()), item.getAmount(),item.getPrice());
            } catch (SQLException e) {
                NotificationController.notifyObservers(e.toString(),5000);
            }
            item.setAmount(content);
            itemsInTransaction.put(item.getName(),item);
        }else {
            ItemCountable itemCountable = (ItemCountable) itemsInTransaction.get(item.getName());
            item=itemCountable;
            try {
                SQL_Connect.getInstance().addToTransaction(transactionID,SQL_Connect.instance.getArticleID(item.getName()), item.getAmount(),item.getPrice());
            } catch (SQLException e) {
                NotificationController.notifyObservers(e.toString(),5000);
            }
            itemCountable.addAmount(content);
        }

        for(OpenTransactionObserver observer : observerList){
            observer.onItemAdd(item);
        }
    }

//    public void removeItem(Item item){
//        if(item == null){return;}
//        if(!itemsInTransaction.containsKey(item.getName())){
//        }else if(itemsInTransaction.containsKey(item.getName()) && item.getClass()==ItemCountable.class){
//            if(item.getAmount()-content<=0){
//                itemsInTransaction.remove(item.getName());
//
//                for(OpenTransactionObserver observer : observerList){
//                    observer.onItemRemove(item);
//                }
//            }else{
//                ItemCountable itemCountable = (ItemCountable) itemsInTransaction.get(item.getName());
//                itemCountable.addAmount(-content);
//
//                for(OpenTransactionObserver observer : observerList){
//                    observer.onItemAdd(item);
//                }
//            }
//        }else{
//            if(item.getAmount()-content<0){
//                itemsInTransaction.remove(item.getName());
//
//                for(OpenTransactionObserver observer : observerList) {
//                    observer.onItemRemove(item);
//                }
//            }else{
//                ItemUncountable itemCountable = (ItemUncountable) itemsInTransaction.get(item.getName());
//                itemCountable.addWeight(-content);
//
//                for(OpenTransactionObserver observer : observerList){
//                    observer.onItemAdd(item);
//                }
//            }
//        }
//    }

    public void removeItem(Item item){
        if(item==null){return;}

        if(itemsInTransaction.containsKey(item.getName())){
            try {
                SQL_Connect.getInstance().removeFromTransaction(transactionID, SQL_Connect.instance.getArticleID(item.getName()), content);

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

    public Map<String,Item> getItemsInTransaction(){return itemsInTransaction; }

    public String getTransactionDateTime(){return this.transactionDateTime.format(this.dateTimeFormatter); }

    public int getTransactionID(){return transactionID;}

    public int getTotal(){
        int sum = 0;

        for(Item item : itemsInTransaction.values().toArray(new Item[0])){
            sum += item.getPrice()*item.getAmount();
        }
        return sum;
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

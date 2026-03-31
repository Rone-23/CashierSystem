package services;

import assets.Constants;
import controllers.notifications.NotificationController;
import controllers.transaction.ContentObserver;
import controllers.transaction.OpenTransactionObserver;
import services.Customers.CustomerCardObserver;
import services.Customers.ValidateCustomerAction;

import java.awt.event.ActionEvent;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OpenTransaction implements ContentObserver, CustomerCardObserver {
    private final int transactionID;
    private int customerID = -1;
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
        ValidateCustomerAction.addObserver(this);
        try {
            transactionID = SQL_Connect.getInstance().createTransaction(1);
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

    public void addItem(Item item) {
        if (item == null) return;

        String name = item.getName();

        try {
            int stock = SQL_Connect.getInstance().getStock(SQL_Connect.getInstance().getArticleID(name));
            int inTransaction = itemsInTransaction.containsKey(name) ? itemsInTransaction.get(name).getAmount() : 0;

            if (stock - content - inTransaction < 0) {
                NotificationController.notifyObservers("Nedostatok tovaru v sklade, aktualny pocet je " + stock, 5000);
                return;
            }
        } catch (SQLException e) {
            throw new RuntimeException("Database error during stock check", e);
        }

        ItemCountable itemToNotify = null;


        if (!isReturn) {
            int price = (item.getDiscountType() == Constants.GENERAL || (item.getDiscountType() == Constants.CUSTOMER && customerID > 0)) ? item.discountPrice : item.getPrice();
            itemToNotify = (ItemCountable) itemsInTransaction.computeIfAbsent(name, k ->
                    item.clone()
            );
            itemToNotify.setPrice(price);
            itemToNotify.addAmount(content);

        } else {
            ItemCountable returnedItem = (ItemCountable) returnedItems.get(name);
            ItemCountable itemInTransaction = (ItemCountable) itemsInTransaction.get(name);

            if (returnedItem == null || returnedItem.getAmount() - content < 0) {
                NotificationController.notifyObservers("Nemozes nablokovat viac tovaru ako bolo povodne.", 5000);
                return;
            }

            returnedItem.addAmount(-content);
            itemInTransaction.addAmount(content);
            itemToNotify = itemInTransaction;
        }

        if (itemToNotify != null) {
            for (OpenTransactionObserver observer : observerList) {
                observer.onItemAdd(itemToNotify);
            }
        }
    }

    public void removeItem(Item item) {
        if (item == null) return;


        String itemName = item.getName();
        ItemCountable itemInTransaction = (ItemCountable) itemsInTransaction.get(itemName);

        if (itemInTransaction == null) {
            NotificationController.notifyObservers("Nemozte vratit tovar ktorý nie je v transakcii", 5000);
            return;
        }

        int remainingAmount = itemInTransaction.getAmount() - content;

        if (remainingAmount < 0) {
            String msg = isReturn ? "Nemozte vratit tovar ktorý nie je v transakcii" : "Nemozte vratit viac tovaru ako je v transakcii";
            NotificationController.notifyObservers(msg, 5000);
            return;
        }

        if (!isReturn) {
            if (remainingAmount == 0) {
                itemsInTransaction.remove(itemName);
                for (OpenTransactionObserver observer : observerList) {
                    observer.onItemRemove(itemInTransaction);
                }
                return;
            }

            itemInTransaction.addAmount(-content);

        } else {
            ItemCountable returnedItem = (ItemCountable) returnedItems.get(itemName);

            if (returnedItem != null) {
                returnedItem.addAmount(content);
            } else {
                int price = (item.getDiscountType() == Constants.GENERAL || (item.getDiscountType() == Constants.CUSTOMER && customerID > 0)) ? item.discountPrice : item.getPrice();
                returnedItems.put(itemName, new ItemCountable(itemName, price, content));
            }

            itemInTransaction.addAmount(-content);
        }

        for (OpenTransactionObserver observer : observerList) {
            observer.onItemAdd(itemInTransaction);
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

    private void checkSum(String typeOfPayment) {
        if(isReturn){
            for(Item returnedItem: returnedItems.values().toArray(new Item[0])){
                try{
                    SQL_Connect.getInstance().returnItem(
                            transactionID,
                            SQL_Connect.getInstance().getArticleID(returnedItem.getName()),
                            returnedItem.getAmount()
                    );
                }catch (SQLException e){
                    NotificationController.notifyObservers(e.toString(), 5000);
                }
            }
            for(OpenTransactionObserver observer : observerList){
                observer.paymentDone();
            }
            return;
        }

        for(OpenTransactionObserver observer : observerList){
            observer.onAddedPayment(getMissing(), typeOfPayment, content);
        }
        if(getMissing()<=EPSILON){
            for(Item item : itemsInTransaction.values().toArray(new Item[0])){
                try{
                    SQL_Connect.getInstance().addToTransaction(
                            transactionID,
                            SQL_Connect.getInstance().getArticleID(item.getName()),
                            item.getAmount(),
                            item.getPrice()
                    );
                }catch (SQLException e){
                    NotificationController.notifyObservers(e.toString(), 5000);
                }
            }
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

    public void setCustomerID(int customerID){
        try{
            SQL_Connect.getInstance().setCustomerId(transactionID, customerID);
            this.customerID = customerID;
        }catch (SQLException e){
            NotificationController.notifyObservers(e.getMessage(),5000);
        }
    }

    @Override
    public void onCardValidation() {
        setCustomerID(content);
        for(Item item : itemsInTransaction.values()){
            item.applyDiscount();
        }
        for(OpenTransactionObserver observer : observerList){
            observer.onTransactionUpdate();
        }
    }

    public int getCustomerID(){
        return customerID;
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

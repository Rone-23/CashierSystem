package services;


public class ItemCountable extends Item{
    public ItemCountable(String name, Double price, Double amount){
        this.name = name;
        this.price = price;
        this.amount = amount;
    }
    public void addAmount(double amountToAdd){
        this.amount+=amountToAdd;
    }

}

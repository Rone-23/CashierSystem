package services;


public class ItemCountable extends Item{
    public ItemCountable(String name, int price, int amount){
        this.name = name;
        this.price = price;
        this.amount = amount;
    }
    public void addAmount(int amountToAdd){
        this.amount+=amountToAdd;
    }

}

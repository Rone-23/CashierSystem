package services;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import utility.ItemDeserializer;

import java.util.Objects;

@JsonDeserialize(using = ItemDeserializer.class)
public abstract class Item {
    protected String name;
    protected int price,amount;
    private static double totalAmount;
    public Item(){
        //TODO: Remake so it shows how many items went through not how many types went through
        totalAmount++;
    }

    public void setName(String name){
        this.name=name;
    }
    public void setPrice(int price){
        this.price=price;
    }
    public void setAmount(int amount){
        this.amount=amount;
    }
    public String getName(){
        return this.name;
    }
    public int getPrice(){
        return this.price;
    }
    public int getAmount(){
        return this.amount;
    }
    public double getTotalAmount(){return totalAmount; }
    public static void setTotalAmountZero(){totalAmount = 0;}
    String getInfo(){return String.format("Name: %s Price: %s Amount: %s",this.name, this.price, this.amount);}

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Item item = (Item) obj;
        return Objects.equals(this.name, item.name);
    }

    // Override hashCode() to ensure consistency with equals()
    @Override
    public int hashCode() {
        return Objects.hash(this.name);
    }
}

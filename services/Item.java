package services;

import java.util.Objects;

public abstract class Item {
    protected String name;
    protected Double price,amount;
    private static double totalAmount;
    public Item(){
        //TODO: Remake so it shows how many items went through not how many types went through
        totalAmount++;
    }

    protected void setName(String name){
        this.name=name;
    }
    protected void setPrice(double price){
        this.price=price;
    }
    protected void setAmount(double amount){
        this.amount=amount;
    }
    public String getName(){
        return this.name;
    }
    public Double getPrice(){
        return this.price;
    }
    public Double getAmount(){
        return this.amount;
    }
    public double getTotalAmount(){return totalAmount; }
    public void setTotalAmountZero(){totalAmount = 0;}
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

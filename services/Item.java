package services;

import assets.Constants;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import utility.ItemDeserializer;

import java.util.Objects;

@JsonDeserialize(using = ItemDeserializer.class)
public abstract class Item implements Cloneable {
    protected String name;
    protected int price, discountPrice, amount;
    protected int basePrice = -1;
    protected Constants discountType = null;
    private static double totalAmount;
    protected boolean isFavorite;

    protected String category;
    protected String subcategory;
    public Item(){
        //TODO: Remake so it shows how many items went through not how many types went through
        totalAmount++;
    }

    public void setName(String name){
        this.name=name;
    }
    public void setPrice(int price){
        this.price=price;
        if (this.basePrice == -1) {
            this.basePrice = price;
        }
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
    public int getBasePrice() {
        return this.basePrice == -1 ? this.price : this.basePrice;
    }
    public void setBasePrice(int basePrice) {
        this.basePrice = basePrice;
    }
    public int getAmount(){
        return this.amount;
    }
    public void applyDiscount() {
        if (this.basePrice == -1) {
            this.basePrice = this.price;
        }

        if (discountType == null) {
            this.price = this.basePrice;
            return;
        }

        this.price = this.discountPrice;
    }
    public void setIsFavorite(boolean isFavorite){this.isFavorite = isFavorite;}
    public boolean getIsFavorite(){return isFavorite;}
    public double getTotalAmount(){return totalAmount; }
    public static void setTotalAmountZero(){totalAmount = 0;}
    public String toString(){return String.format("Name: %s Price: %s Amount: %s",this.name, this.price, this.amount);}
    public String getSubcategory() {
        return subcategory;
    }
    public String getCategory() {
        return category;
    }
    public void setCategory(String category) {
        this.category = category;
    }
    public void setSubcategory(String subcategory) {
        this.subcategory = subcategory;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Item item = (Item) obj;
        return Objects.equals(this.name, item.name);
    }

    public void setDiscountPrice(int discountPrice) {
        this.discountPrice = discountPrice;
    }

    public void setDiscountType(Constants discountType) {
        this.discountType = discountType;
    }

    public int getDiscountPrice() {
        return discountPrice;
    }

    public Constants getDiscountType() {
        return discountType;
    }

    // Override hashCode() to ensure consistency with equals()
    @Override
    public int hashCode() {
        return Objects.hash(this.name);
    }

    @Override
    public Item clone() {
        try {
            return (Item) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError("Cloning failed", e);
        }
    }
}

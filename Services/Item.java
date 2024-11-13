package Services;

import java.util.ArrayList;

public abstract class Item {
    String name;
    Double price,amount;
    static double totalAmount;

    void setName(String name){
        this.name=name;
    }
    void setPrice(double price){
        this.price=price;
    }
    void setAmount(double amount){
        this.amount=amount;
    }
    String getName(){
        return this.name;
    }
    Double getPrice(){
        return this.price;
    }
    Double getAmount(){
        return this.amount;
    }
    String getInfo(){return String.format("Name: %s Price: %s Amount: %s",this.name, this.price, this.amount);}
    public Item[] refactor(ArrayList<Item> incomingListOfItems){
        ArrayList<Item> finalArray = new ArrayList<>();
        for (int i = 0;i<incomingListOfItems.size();i++){
            for (int j = 1; j<incomingListOfItems.size();j++){
                if (incomingListOfItems.get(i).getName().equals(incomingListOfItems.get(j).getName())){
                    this.setName(incomingListOfItems.get(i).getName());
                    this.setPrice(incomingListOfItems.get(i).getPrice());
                    this.setAmount(incomingListOfItems.get(i).getAmount()+incomingListOfItems.get(j).getAmount());
                    incomingListOfItems.remove(j);
                    finalArray.add(this);
                }
            }

        }
        return finalArray.toArray(new Item[finalArray.size()]);
    }
}

package services;

import java.util.ArrayList;
import java.util.List;


public class ItemUncountable extends Item{
    private double weight;
    private List<Double> weightList= new ArrayList<>();
    public ItemUncountable(String name, int price, int weight){
        this.name = name;
        this.price = price;
        this.weight = weight;
        addWeight(this.weight);
    }
    public ItemUncountable(String name, int price, ArrayList<Double> weightList){
        this.name = name;
        this.price = price;
        this.weightList = weightList;
    }

    public double getWeight(){return this.weight;}

    public void addWeight(double weight){
        this.weightList.add(weight);
    }

    public List<Double> getWeightList() {
        return this.weightList;
    }

    @Override
    public int getAmount(){
        int resultWeight = 0;
        for (Double element: this.weightList.toArray(new Double[0])){
            resultWeight += element;
        }
        return resultWeight;
    }
}

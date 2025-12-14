package assets;

public enum Constants {
    //DISPLAY TYPE
    TOTAL(10),
    WEIGHT(11),
    SPLIT(12),

    //ARROW TYPE
    LEFT(20),
    RIGHT(21),
    UP(22),
    DOWN(23),

    //BUTTON CLUSTER TYPE
    HORIZONTAL(0),
    VERTICAL(1),

    ;
    private final double value;
    Constants(double constant){
        this.value  = constant;
    }
    public double getValue(){
        return value;
    }

}

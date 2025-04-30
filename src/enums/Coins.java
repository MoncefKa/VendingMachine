package enums;

public enum Coins {

    HALF(0.5),
    ONE(1),
    TWO(2),
    FIFTH(5),
    TENTH(10);

    private final double value;

    Coins(double value){
        this.value = value;
    }

    public double getValue() {
        return value;
    }
}

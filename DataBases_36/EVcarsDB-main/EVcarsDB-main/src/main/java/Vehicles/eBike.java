package Vehicles;

public class eBike extends Vehicle{
    private final int eBikeID;
    public eBike(String brand, String model, String color, int range, int rentPrice, int insurancePrice,
                 int eBikeID) {
        super(brand, model, color, range, rentPrice, insurancePrice);
        this.eBikeID = eBikeID;
    }

    public int getBikeID() {
        return eBikeID;
    }

}

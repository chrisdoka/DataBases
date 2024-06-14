package Vehicles;

public class Scooter extends Vehicle{
    private int ScooterID;
    public Scooter(String brand, String model, String color, int range, int rentPrice, int insurancePrice,
                   int scooterID) {
        super(brand, model, color, range, rentPrice, insurancePrice);
        ScooterID = scooterID;
    }

    public int getScooterID() {
        return ScooterID;
    }

    public void setScooterID(int scooterID) {
        ScooterID = scooterID;
    }
}

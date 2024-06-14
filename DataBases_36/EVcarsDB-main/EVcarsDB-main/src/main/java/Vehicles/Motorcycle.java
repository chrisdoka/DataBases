package Vehicles;

public class Motorcycle extends Vehicle{
    private final int LicenseNumber;
    public Motorcycle(String brand, String Model, String color, int range, int RentPrice, int insurancePrice, int licenseNumber, int passengers) {
        super(brand, Model, color, range, RentPrice, insurancePrice);
        this.LicenseNumber = licenseNumber;
    }

    public int getLicenseNumber() {
        return LicenseNumber;
    }

}

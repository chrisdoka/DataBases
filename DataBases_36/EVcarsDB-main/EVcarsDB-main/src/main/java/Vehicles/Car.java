package Vehicles;

public class Car extends Vehicle{
    private String CarType;
    private int LicenseNumber;
    private int Passengers;
    /**
     * @param licenseNumber Άδεια κυκλοφορίας
     * @param carType Τύπος οχήματος (SUV/Sedan etc)
     * @param passengers Αριθμός επιβατών*/
    public Car(String brand, String model, String color, int range, int rentPrice, int insurancePrice,
               String carType, int licenseNumber, int passengers) {
        super(brand, model, color, range, rentPrice, insurancePrice);
        CarType = carType;
        LicenseNumber = licenseNumber;
        Passengers = passengers;
    }

    public String getCarType() {
        return CarType;
    }

    public void setCarType(String carType) {
        CarType = carType;
    }

    public int getLicenseNumber() {
        return LicenseNumber;
    }

    public void setLicenseNumber(int licenseNumber) {
        LicenseNumber = licenseNumber;
    }

    public int getPassengers() {
        return Passengers;
    }

    public void setPassengers(int passengers) {
        Passengers = passengers;
    }
}

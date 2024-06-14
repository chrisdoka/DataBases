package Vehicles;

/**Μια αφαιρετική Κλάση Όχημα
 * @author Lefteris */
public abstract class Vehicle {
    private String Brand;
    private String Model;
    private String Color;
    private int Range;
    private int RentPrice;
    private int InsurancePrice;
    /**
     * @param brand Η μάρκα
     * @param model Το μοντέλο
     * @param color Το χρώμα
     * @param range Η αυτονομία του οχήματος (χιλιόμετρα/φόρτιση)
     * @param insurancePrice Το κόστος ασφάλισης οχήματος <b>ανά μέρα</b>
     * @param rentPrice Το κόστος ενοικίασης <b>ανά μέρα</b> **/
    public Vehicle(String brand, String model, String color, int range, int rentPrice, int insurancePrice) {
        Brand = brand;
        Model = model;
        Color = color;
        Range = range;
        RentPrice = rentPrice;
        InsurancePrice = insurancePrice;
    }

    public String getBrand() {
        return Brand;
    }

    public void setBrand(String brand) {
        Brand = brand;
    }

    public String getModel() {
        return Model;
    }

    public void setModel(String model) {
        Model = model;
    }

    public String getColor() {
        return Color;
    }

    public void setColor(String color) {
        Color = color;
    }

    public int getRange() {
        return Range;
    }

    public void setRange(int range) {
        Range = range;
    }

    public int getRentPrice() {
        return RentPrice;
    }

    public void setRentPrice(int rentPrice) {
        RentPrice = rentPrice;
    }

    public int getInsurancePrice() {
        return InsurancePrice;
    }

    public void setInsurancePrice(int insurancePrice) {
        InsurancePrice = insurancePrice;
    }
}

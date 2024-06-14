package Rentals;

import java.util.Date;

public class Rental {
    private int RentalID;
    private String CustomerName;
    private String RentalDate;
    private String ReturnDate;
    private int PaymentAmount;
    private final int RenterID;//client id
    private int VehicleID;//vehicle id

    public String getVehicleType() {
        return VehicleType;
    }

    private String VehicleType;
    private final int assignedDriverID;
    private final boolean hasAssignedDriver;


    public Rental(String customerName, String rentalDate, String returnDate, String VehicleType, int VehicleID, int assignedDriverID, int RenterID, int paymentAmount) {
        CustomerName = customerName;
        RentalDate = rentalDate;
        ReturnDate = returnDate;
        this.VehicleID = VehicleID;
        this.assignedDriverID = assignedDriverID;
        this.VehicleType = VehicleType;
        if(assignedDriverID == 0){
            hasAssignedDriver = false;
        }
        else{
            hasAssignedDriver = true;
        }
        PaymentAmount = paymentAmount;
        this.RenterID = RenterID;
    }

    public String getCustomerName() {
        return CustomerName;
    }

    public void setCustomerName(String customerName) {
        CustomerName = customerName;
    }
    public void setRentalID(int rentalID) {
        RentalID = rentalID;
    }
    public int getRentalID() {
        return RentalID;
    }
    public int getRenterID() {
        return RenterID;
    }
    public int getVehicleID() {
        return VehicleID;
    }

    public int getAssignedDriverID() {
        return assignedDriverID;
    }
    public boolean hasAssignedDriver() {
        return hasAssignedDriver;
    }

    public String getRentalDate() {
        return RentalDate;
    }

    public void setRentalDate(String rentalDate) {
        RentalDate = rentalDate;
    }

    public String getReturnDate() {
        return ReturnDate;
    }

    public void setReturnDate(String returnDate) {
        ReturnDate = returnDate;
    }

    public int getPaymentAmount() {
        return PaymentAmount;
    }

    public void setPaymentAmount(int paymentAmount) {
        PaymentAmount = paymentAmount;
    }

}

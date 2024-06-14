package Clients;

import java.util.Date;

public class Client {

    private final int ClientID;
    private final String Firstname;
    private final String Lastname;
    private final String address;
    private final String birthdate;
    private final int DriverLicenseNumber;
    private int creditCardNumber;
    private String creditCardExpirationDate;

    public String getCreditCardExpirationDate() {
        return creditCardExpirationDate;
    }

    public void setCreditCardExpirationDate(String creditCardExpirationDate) {
        this.creditCardExpirationDate = creditCardExpirationDate;
    }

    public int getCvv() {
        return cvv;
    }

    public void setCvv(int cvv) {
        this.cvv = cvv;
    }

    private int cvv;

    public int getCreditCardNumber() {
        return creditCardNumber;
    }

    public void setCreditCardNumber(int creditCardNumber) {
        this.creditCardNumber = creditCardNumber;
    }


    public int getClientID() {
        return ClientID;
    }

    public String getFirstname() {
        return Firstname;
    }

    public String getLastname() {
        return Lastname;
    }

    public String getAddress() {
        return address;
    }

    public String getBirthdate() {
        return birthdate;
    }


    public int getDriverLicenseNumber() {
        return DriverLicenseNumber;
    }

    // Constructor
    public Client(int clientID, String firstname, String lastname, String address,
            String birthdate, int driverLicenseNumber, int creditCardNumber,
            String creditCardExpirationDate, int cvv) {
        this.ClientID = clientID;
        this.Firstname = firstname;
        this.Lastname = lastname;
        this.address = address;
        this.birthdate = birthdate;
        this.DriverLicenseNumber = driverLicenseNumber;
        this.creditCardNumber= creditCardNumber;
        this.creditCardExpirationDate= creditCardExpirationDate;
        this.cvv= cvv;
    }

    /**
     * Κατασκευαστής για χρήστη χωρίς πιστωτική κάρτα
     */
    //ΔΕΝ ΤΟΝ ΚΑΛΩ ΠΟΤΕ ΑΥΤΟΝ ΤΟΝ ΚΑΤΑΣΚΕΥΑΣΤΗ
}

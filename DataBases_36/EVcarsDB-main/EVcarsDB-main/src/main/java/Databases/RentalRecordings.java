package Databases;

import Rentals.Rental;
import com.google.gson.Gson;

import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class RentalRecordings {
    public void createRentalRecordingsTable() {
        Connection connect;
        Statement statement;
        String query = null;
        try{
            connect = DB_Connection.getConnection();
            statement = connect.createStatement();
            query = "CREATE TABLE IF NOT EXISTS RentalRecordings" +
                    "(RentalID INT NOT NULL AUTO_INCREMENT," +
                    "CustomerName VARCHAR(55) NOT NULL," +
                    "RentalDate DATE NOT NULL," +
                    "ReturnDate DATE NOT NULL," +
                    "VehicleType VARCHAR(55) NOT NULL," +
                    "PaymentAmount INT NOT NULL," +
                    "RenterID INT NOT NULL," +
                    "VehicleID INT NOT NULL," +
                    "assignedDriverID INT," +
                    "FOREIGN KEY (RenterID) REFERENCES ClientRecordings(ClientID)," +
                    "CONSTRAINT KEY1 FOREIGN KEY (VehicleID) REFERENCES carrecordings(LicenseNumber)," +
                    "CONSTRAINT KEY2 FOREIGN KEY (VehicleID) REFERENCES MotorcycleRecordings(LicenseNumber)," +
                    "CONSTRAINT KEY3 FOREIGN KEY (VehicleID) REFERENCES eBikeRecordings(BikeID)," +
                    "CONSTRAINT KEY4 FOREIGN KEY (VehicleID) REFERENCES ScooterRecordings(ScooterID)," +
                    "PRIMARY KEY (RentalID))";
            statement.execute(query);
            statement.close();
            connect.close();//maybe problematic
        }
        catch (Exception ex){
            ex.printStackTrace(System.err);
        }
    }
    public void addNewRental(Rental rental){
        Connection connect;
        PreparedStatement statement;
        Statement stmt;
        String query;
        ResultSet rs;
        try {
            connect = DB_Connection.getConnection();
            query = "INSERT IGNORE INTO RentalRecordings (CustomerName, RentalDate, ReturnDate, VehicleType, PaymentAmount, RenterID, VehicleID, assignedDriverID) VALUES ('" +
                    rental.getCustomerName() + "', '" +
                    rental.getRentalDate() + "', '" +
                    rental.getReturnDate() + "', '" +
                    rental.getVehicleType() + "', '" +
                    rental.getPaymentAmount() + "', '" +
                    rental.getRenterID() + "', '" +
                    rental.getVehicleID() + "', '" +
                    rental.getAssignedDriverID() + "')";
            System.out.println(query);
            statement = connect.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            statement.execute();
            rs = statement.getGeneratedKeys();
            if (rs.next()) {
                rental.setRentalID(rs.getInt(1));
            }
            statement.close();
            if(rental.getVehicleType().equals("Car"))
                query= "UPDATE carrecordings SET TimesRented = TimesRented + 1 WHERE LicenseNumber= " + rental.getVehicleID();
            else if(rental.getVehicleType().equals("Motorcycle"))
                query= "UPDATE MotorcycleRecordings SET TimesRented = TimesRented + 1 WHERE LicenseNumber= " + rental.getVehicleID();
            else if(rental.getVehicleType().equals("eBike"))
                query= "UPDATE eBikeRecordings SET TimesRented = TimesRented + 1 WHERE BikeID= " + rental.getVehicleID();
            else if(rental.getVehicleType().equals("Scooter"))
                query= "UPDATE ScooterRecordings SET TimesRented = TimesRented + 1 WHERE ScooterID= " + rental.getVehicleID();
            stmt= connect.createStatement();
            stmt.execute(query);
            System.out.println("Rental added successfully");
            connect.close();//maybe problematic
        }
        catch (Exception ex){
            ex.printStackTrace(System.err);
        }
    }
    /**
     * @param actualReturn Η ημερομηνία πραγματική επιστροφής του οχήματος
     * @param rental Η ενοικίαση που θα επιστραφεί
     * Επιστρέφει την πιθανή χρέωση λόγω καθυστέρησης */
    public int returnVehicle(String actualReturn, Rental rental) {
        Connection connect;
        Statement statement;
        int diffHours=0;
        String query;
        Date actualReturnDate=null;
        Date ReturnDate=null;
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        try {
            actualReturnDate = formatter.parse(actualReturn);
            ReturnDate = formatter.parse(rental.getReturnDate());
        }
        catch (ParseException e){
            e.printStackTrace(System.err);
        }
        try {
            connect = DB_Connection.getConnection();
            statement = connect.createStatement();
            query = "DELETE FROM RentalRecordings WHERE RentalID="+rental.getRentalID();
            statement.execute(query);
            statement.close();
            if(ReturnDate.getTime()<actualReturnDate.getTime()){
                System.out.println("You have exceeded the rental duration");
                Calendar startCalendar = new GregorianCalendar();
                startCalendar.setTime(ReturnDate);
                Calendar endCalendar = new GregorianCalendar();
                endCalendar.setTime(actualReturnDate);
                long diffMillis = endCalendar.getTimeInMillis() - startCalendar.getTimeInMillis();
                diffHours = (int) diffMillis / (60 * 60 * 1000);
            }
            System.out.println("Rental removed successfully");
            connect.close();
        } catch (SQLException e) {
            e.printStackTrace(System.err);
        }
        return diffHours;
    }
     public void addNewRentalFromJSON(String rentalJson) {
        Gson gson = new Gson();
        Rental rental = gson.fromJson(rentalJson, Rental.class);
        RentalRecordings rentalRecordings = new RentalRecordings();
        rentalRecordings.addNewRental(rental);
    }

    public int returnVehicleFromJSON(String actualReturnDate, String rentalJson) {
        Gson gson = new Gson();
        Rental rental = gson.fromJson(rentalJson, Rental.class);
        RentalRecordings rentalRecordings = new RentalRecordings();
        return rentalRecordings.returnVehicle(actualReturnDate, rental);
    }
}

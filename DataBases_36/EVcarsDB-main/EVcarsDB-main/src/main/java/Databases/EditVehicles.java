package Databases;

import Clients.Client;
import Rentals.Rental;
import Vehicles.*;
import com.google.gson.Gson;
import java.sql.*;
import java.util.ArrayList;

public class EditVehicles {
   public ArrayList<Vehicle> getAvailableVehicles() {
        Connection con = DB_Connection.getConnection();
        Statement stmt;
        String query1 = "SELECT * FROM CarRecordings WHERE NOT EXISTS (SELECT * FROM RentalRecordings WHERE CarRecordings.LicenseNumber = RentalRecordings.VehicleID) AND ";
        String query2 = "SELECT * FROM eBikeRecordings WHERE NOT EXISTS (SELECT * FROM RentalRecordings WHERE eBikeRecordings.BikeID = RentalRecordings.VehicleID) AND";
        String query3 = "SELECT * FROM MotorcycleRecordings WHERE NOT EXISTS (SELECT * FROM RentalRecordings WHERE MotorcycleRecordings.LicenseNumber = RentalRecordings.VehicleID) AND";
        String query4 = "SELECT * FROM ScooterRecordings WHERE NOT EXISTS (SELECT * FROM RentalRecordings WHERE ScooterRecordings.ScooterID = RentalRecordings.VehicleID)";
        ArrayList<Vehicle> allAvailableVehicles = new ArrayList<>();
        ResultSet rs;
        try {
            stmt = con.createStatement();
            rs = stmt.executeQuery(query1+query2+query3+query4);
            while (rs.next()) {
                String json = DB_Connection.getResultsToJSON(rs);
                Gson gson = new Gson();
                Vehicle veh = gson.fromJson(json, Vehicle.class);
                allAvailableVehicles.add(veh);
            }
        }
            catch(SQLException e){
                e.printStackTrace(System.err);
       }
       if (!allAvailableVehicles.isEmpty()) {
           return allAvailableVehicles;
       } else {
           System.out.println("There are no available vehicles");
       }
        return null;
    }

    public void RentVehicle(Client client, String rentalDate, String VehicleType, int paymentAmount, String returnDate, int rentedVehicleID) {
        Rental rental = new Rental(client.getFirstname() + client.getLastname(), rentalDate, returnDate, VehicleType, rentedVehicleID, 0, client.getClientID(), paymentAmount);
        new RentalRecordings().addNewRental(rental);
    }

    /**Για τη περίπτωση που γίνεται ανάθεση κάποιου άλλου οδηγού*/
    public void RentVehicle(Client client, String rentalDate, String VehicleType, int paymentAmount, int assignedDriverID, String returnDate, int rentedVehicleID) {
        Rental rental = new Rental(client.getFirstname() + client.getLastname(), rentalDate, returnDate, VehicleType, rentedVehicleID, assignedDriverID, client.getClientID(), paymentAmount);
        new RentalRecordings().addNewRental(rental);
    }

     public ArrayList<Vehicle> getAvailableVehiclesFromJSON() {
        EditVehicles editVehicles = new EditVehicles();
        return editVehicles.getAvailableVehicles();
    }

    public void rentVehicleFromJSON(String clientJson, String rentalDate, String vehicleType, int paymentAmount, String returnDate, int rentedVehicleID) {
        Gson gson = new Gson();
        Client client = gson.fromJson(clientJson, Client.class);
        EditVehicles editVehicles = new EditVehicles();
        editVehicles.RentVehicle(client, rentalDate, vehicleType, paymentAmount, returnDate, rentedVehicleID);
    }

    public void rentVehicleFromJSON(String clientJson, String rentalDate, String vehicleType, int paymentAmount, int assignedDriverID, String returnDate, int rentedVehicleID) {
        Gson gson = new Gson();
        Client client = gson.fromJson(clientJson, Client.class);
        EditVehicles editVehicles = new EditVehicles();
        editVehicles.RentVehicle(client, rentalDate, vehicleType, paymentAmount, assignedDriverID, returnDate, rentedVehicleID);
    }
}

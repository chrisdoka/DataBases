package Databases;

import Rentals.Rental;
import Vehicles.*;
import com.google.gson.Gson;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public class Statistics {
public ArrayList<ArrayList<?>> getAvailableOrRentedVehicles(){
    Connection con = DB_Connection.getConnection();
    Statement stmt;
    String query1 = "SELECT * FROM CarRecordings WHERE NOT EXISTS (SELECT * FROM RentalRecordings WHERE CarRecordings.LicenseNumber = RentalRecordings.VehicleID)";
    String query2 = "SELECT * FROM eBikeRecordings WHERE NOT EXISTS (SELECT * FROM RentalRecordings WHERE eBikeRecordings.BikeID = RentalRecordings.VehicleID)";
    String query3 = "SELECT * FROM MotorcycleRecordings WHERE NOT EXISTS (SELECT * FROM RentalRecordings WHERE MotorcycleRecordings.LicenseNumber = RentalRecordings.VehicleID)";
    String query4 = "SELECT * FROM ScooterRecordings WHERE NOT EXISTS (SELECT * FROM RentalRecordings WHERE ScooterRecordings.ScooterID = RentalRecordings.VehicleID)";
    String query5= "SELECT * FROM RentalRecordings GROUP BY rentalrecordings.VehicleType";
    ArrayList<Car> allAvailableCars = new ArrayList<>();
    ArrayList<Motorcycle> allAvailableMotorcycles = new ArrayList<>();
    ArrayList<eBike> allAvailableEBikes = new ArrayList<>();
    ArrayList<Scooter> allAvailableScooters = new ArrayList<>();
    ArrayList<Vehicle> allRentedVehicles = new ArrayList<>();
    ArrayList<ArrayList<?>> allAvailableVehicles = new ArrayList<>();
    ResultSet rs;
    try {
        stmt = con.createStatement();
        rs = stmt.executeQuery(query1);
        while (rs.next()) {
            String json = DB_Connection.getResultsToJSON(rs);
            Gson gson = new Gson();
            Car veh = gson.fromJson(json, Car.class);
            allAvailableCars.add(veh);
        }
        rs = stmt.executeQuery(query2);
        while (rs.next()) {
            String json = DB_Connection.getResultsToJSON(rs);
            Gson gson = new Gson();
            Motorcycle veh = gson.fromJson(json, Motorcycle.class);
            allAvailableMotorcycles.add(veh);
        }
        rs = stmt.executeQuery(query3);
        while (rs.next()) {
            String json = DB_Connection.getResultsToJSON(rs);
            Gson gson = new Gson();
            eBike veh = gson.fromJson(json, eBike.class);
            allAvailableEBikes.add(veh);
        }
        rs = stmt.executeQuery(query4);
        while (rs.next()) {
            String json = DB_Connection.getResultsToJSON(rs);
            Gson gson = new Gson();
            Scooter veh = gson.fromJson(json, Scooter.class);
            allAvailableScooters.add(veh);
        }
        rs = stmt.executeQuery(query5);
        while (rs.next()) {
            String json = DB_Connection.getResultsToJSON(rs);
            Gson gson = new Gson();
            Vehicle veh = gson.fromJson(json, Vehicle.class);
            allRentedVehicles.add(veh);
        }
        allAvailableVehicles.add(allAvailableCars);
        allAvailableVehicles.add(allAvailableMotorcycles);
        allAvailableVehicles.add(allAvailableEBikes);
        allAvailableVehicles.add(allAvailableScooters);
        allAvailableVehicles.add(allRentedVehicles);
    }
    catch(SQLException e){
        e.printStackTrace(System.err);
    }
    return allAvailableVehicles;
}
/**Επιστρέφει τη κατάσταση των ενοικιάσεων ανά χρονική περίοδο*/
public ArrayList<Rental> RentalStatus(Date From, Date To){
    Connection con = DB_Connection.getConnection();
    Statement stmt;
    String query = "SELECT * FROM RentalRecordings WHERE RentalDate >= '" + From + "' AND ReturnDate <='" + To + "'";
    ResultSet rs;
    ArrayList<Rental> rentStatus = new ArrayList<>();
    try {
        stmt = con.createStatement();
        rs = stmt.executeQuery(query);
        while (rs.next()) {
            String json = DB_Connection.getResultsToJSON(rs);
            Gson gson = new Gson();
            Rental rentStatusJSON = gson.fromJson(json, Rental.class);
            rentStatus.add(rentStatusJSON);
            System.out.println(json);
        }
    }
    catch(SQLException e){
        e.printStackTrace(System.err);
    }
    return rentStatus;
}
public HashMap<String,ArrayList<Integer>> RentalAverages(){
        Connection con = DB_Connection.getConnection();
        ResultSet rs;
        Statement stmt;
        HashMap<String, ArrayList<Integer>> averagesMap = new HashMap<>();
        ArrayList<Integer> statsArray = new ArrayList<>();
        try {
            stmt = con.createStatement();
            String query = "SELECT DISTINCT MAX(DATEDIFF(ReturnDate,RentalDate)), MIN(DATEDIFF(ReturnDate,RentalDate))" +
                    ",AVG(DATEDIFF(ReturnDate,RentalDate)) FROM RentalRecordings WHERE VehicleType = 'Car'";
            rs = stmt.executeQuery(query);
            while (rs.next()) {
                statsArray.add(rs.getInt(1));//max
                statsArray.add(rs.getInt(2));//min
                statsArray.add(rs.getInt(3));//avg
            }
            averagesMap.put("Car", statsArray);
            statsArray.clear();
            query = "SELECT DISTINCT MAX(DATEDIFF(ReturnDate,RentalDate)), MIN(DATEDIFF(ReturnDate,RentalDate))," +
                    "AVG(DATEDIFF(ReturnDate,RentalDate)) FROM RentalRecordings WHERE VehicleType = 'Motorcycle'";
            rs = stmt.executeQuery(query);
            while (rs.next()) {
                statsArray.add(rs.getInt(1));//max
                statsArray.add(rs.getInt(2));//min
                statsArray.add(rs.getInt(3));//avg
            }
            averagesMap.put("Motorcycle", statsArray);
            statsArray.clear();
            query = "SELECT DISTINCT MAX(DATEDIFF(ReturnDate,RentalDate)), MIN(DATEDIFF(ReturnDate,RentalDate)),AVG(DATEDIFF(ReturnDate,RentalDate)) FROM RentalRecordings WHERE VehicleType = 'eBike'";
            rs = stmt.executeQuery(query);
            while (rs.next()) {
                statsArray.add(rs.getInt(1));//max
                statsArray.add(rs.getInt(2));//min
                statsArray.add(rs.getInt(3));//avg
            }
            averagesMap.put("eBike", statsArray);
            statsArray.clear();
            query = "SELECT DISTINCT MAX(DATEDIFF(ReturnDate,RentalDate)), MIN(DATEDIFF(ReturnDate,RentalDate))" +
                    ",AVG(DATEDIFF(ReturnDate,RentalDate)) FROM RentalRecordings WHERE VehicleType = 'Scooter'";
            rs = stmt.executeQuery(query);
            while (rs.next()) {
                statsArray.add(rs.getInt(1));//max
                statsArray.add(rs.getInt(2));//min
                statsArray.add(rs.getInt(3));//avg
            }
            averagesMap.put("Scooter", statsArray);
            statsArray.clear();
        } catch (SQLException e) {
            e.printStackTrace(System.err);
        }
        return averagesMap;
    }
    public HashMap<String,ArrayList<Integer>> RentalTotals(Date From, Date To){
        Connection con = DB_Connection.getConnection();
        ResultSet rs;
        Statement stmt;
        HashMap<String, ArrayList<Integer>> totalsMap = new HashMap<>();
        ArrayList<Integer> statsArray = new ArrayList<>();
        try {
            stmt = con.createStatement();
            String query = "SELECT SUM(PaymentAmount) FROM RentalRecordings WHERE " +
                    "VehicleType = 'Car' AND RentalDate >= '" + From + "' AND ReturnDate <='" + To + "'";
            rs = stmt.executeQuery(query);
            while (rs.next()) {
                statsArray.add(rs.getInt(1));//max
            }
            totalsMap.put("Car", statsArray);
            statsArray.clear();
            query = "SELECT SUM(PaymentAmount) FROM RentalRecordings WHERE VehicleType " +
                    "= 'Motorcycle' AND RentalDate >= '" + From + "' AND ReturnDate <='" + To + "'";
            rs = stmt.executeQuery(query);
            while (rs.next()) {
                statsArray.add(rs.getInt(1));//max
            }
            totalsMap.put("Motorcycle", statsArray);
            statsArray.clear();
            query = "SELECT SUM(PaymentAmount) FROM RentalRecordings WHERE VehicleType " +
                    "= 'eBike' AND RentalDate >= '" + From + "' AND ReturnDate <='" + To + "'";
            rs = stmt.executeQuery(query);
            while (rs.next()) {
                statsArray.add(rs.getInt(1));//max
            }
            totalsMap.put("eBike", statsArray);
            statsArray.clear();
            query = "SELECT SUM(PaymentAmount) FROM RentalRecordings WHERE" +
                    " VehicleType = 'Scooter' AND RentalDate >= '" + From + "' AND ReturnDate <='" + To + "'";
            rs = stmt.executeQuery(query);
            while (rs.next()) {
                statsArray.add(rs.getInt(1));//max
            }
            totalsMap.put("Scooter", statsArray);
            statsArray.clear();
        } catch (SQLException e) {
            e.printStackTrace(System.err);
        }
        return totalsMap;
    }
    public ArrayList<String> popularVehicles(){
        Connection con = DB_Connection.getConnection();
        ResultSet rs;
        Statement stmt;
        ArrayList<String> popularVehicles = new ArrayList<>();
        try {
            stmt = con.createStatement();
            String query = "SELECT Brand,Model FROM RentalRecordings,carrecordings" +
                    " WHERE RentalRecordings.VehicleID = carrecordings.LicenseNumber AND (SELECT " +
                    "MAX(TimesRented) FROM carrecordings)";
            rs = stmt.executeQuery(query);
            while (rs.next()) {
                popularVehicles.add(rs.getString(1)+" "+rs.getString(2));
            }
            query = "SELECT Brand,Model FROM RentalRecordings,motorcyclerecordings" +
                    " WHERE RentalRecordings.VehicleID = motorcyclerecordings.LicenseNumber AND " +
                    "(SELECT MAX(TimesRented) FROM evol_db.motorcyclerecordings)";
            rs = stmt.executeQuery(query);
            while (rs.next()) {
                popularVehicles.add(rs.getString(1)+" "+rs.getString(2));
            }
            query = "SELECT Brand,Model FROM RentalRecordings,ebikerecordings WHERE" +
                    " RentalRecordings.VehicleID = ebikerecordings.BikeID AND (SELECT " +
                    "MAX(TimesRented) FROM ebikerecordings)";
            rs = stmt.executeQuery(query);
            while (rs.next()) {
                popularVehicles.add(rs.getString(1)+" "+rs.getString(2));
            }
            query = "SELECT Brand,Model FROM RentalRecordings,scooterrecordings WHERE " +
                    "RentalRecordings.VehicleID =scooterrecordings.ScooterID AND " +
                    "(SELECT MAX(TimesRented) FROM scooterrecordings)";
            rs = stmt.executeQuery(query);
            while (rs.next()) {
                popularVehicles.add(rs.getString(1)+" "+rs.getString(2));
            }
        } catch (SQLException e) {
            e.printStackTrace(System.err);
        }
        return popularVehicles;
    }
}

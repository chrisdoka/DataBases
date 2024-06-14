package Databases;

import Vehicles.Car;
import Vehicles.Motorcycle;
import Vehicles.Scooter;
import Vehicles.eBike;
import com.google.gson.Gson;
import Databases.CarRecordings;
import java.sql.Connection;
import java.sql.Statement;

public class DamagedRecordings {
    public void createDamagedVehiclesTable() {
        Connection connect;
        Statement statement;
        String query = null;
        try{
            connect = DB_Connection.getConnection();
            statement = connect.createStatement();
            query = "CREATE TABLE IF NOT EXISTS DamagedVehicles" +
                    "(DamagedVehicleID INT NOT NULL AUTO_INCREMENT," +
                    "VehicleID INT NOT NULL," +
                    "PRIMARY KEY (DamagedVehicleID))";
            statement.execute(query);
            statement.close();
            connect.close();//maybe problematic
        }
        catch (Exception ex){
            ex.printStackTrace(System.err);
        }
    }
    public void addNewDamagedVehicle(int vehicleID, Class<?> VehicleType){
        Connection connect;
        Statement statement;
        String query;
        try {
            connect = DB_Connection.getConnection();
            statement = connect.createStatement();
            query = "INSERT IGNORE INTO DamagedVehicles (VehicleID) VALUES ('" + vehicleID + "')";
            statement.execute(query);
            query ="DELETE FROM RentalRecordings WHERE VehicleID="+vehicleID;
            statement.execute(query);
            if (VehicleType.equals(Car.class) || VehicleType.equals(Motorcycle.class)){
                query = "DELETE FROM CarRecordings WHERE LicenseNumber=" + vehicleID;
                statement.execute(query);
            }
            else if(VehicleType.equals(eBike.class)){
                query = "DELETE FROM eBikeRecordings WHERE BikeID=" + vehicleID;
                statement.execute(query);
            }
            else if(VehicleType.equals(Scooter.class)){
                query = "DELETE FROM ScooterRecordings WHERE ScooterID=" + vehicleID;
                statement.execute(query);
            }
            statement.close();
            System.out.println("Damaged Vehicle added successfully");
            connect.close();//maybe problematic
        }
        catch (Exception ex){
            ex.printStackTrace(System.err);
        }
    }
     public void addDamagedVehicleFromJSON(String json, Class<?> vehicleType){
        Gson gson = new Gson();
         if (vehicleType.equals(Car.class)) {
            Car car = gson.fromJson(json, Car.class);
            addNewDamagedVehicle(car.getLicenseNumber(), vehicleType);
         }
            else if (vehicleType.equals(Motorcycle.class)) {
                Motorcycle motorcycle = gson.fromJson(json, Motorcycle.class);
                addNewDamagedVehicle(motorcycle.getLicenseNumber(), vehicleType);
            }
            else if (vehicleType.equals(eBike.class)) {
                eBike ebike = gson.fromJson(json, eBike.class);
                addNewDamagedVehicle(ebike.getBikeID(), vehicleType);
            }
            else if (vehicleType.equals(Scooter.class)) {
                Scooter scooter = gson.fromJson(json, Scooter.class);
                addNewDamagedVehicle(scooter.getScooterID(), vehicleType);
            }

    }

    public DamagedRecordings jsonToDamagedRecordings(String json) {
        Gson gson = new Gson();
        DamagedRecordings damagedRecordings = gson.fromJson(json, DamagedRecordings.class);
        return damagedRecordings;
    }

    public String damagedRecordingsToJSON(DamagedRecordings damagedRecordings) {
        Gson gson = new Gson();
        String json = gson.toJson(damagedRecordings, DamagedRecordings.class);
        return json;
    }
}

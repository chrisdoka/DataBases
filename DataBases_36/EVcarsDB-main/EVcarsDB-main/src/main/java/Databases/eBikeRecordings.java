package Databases;

import Vehicles.eBike;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import com.google.gson.Gson;

import Clients.Client;

public class eBikeRecordings {
    public void createEBikeRecordingsTable() {
        Connection connect = null;
        Statement statement = null;
        String query = null;
        try {
            connect = DB_Connection.getConnection();
            statement = connect.createStatement();
            query = "CREATE TABLE IF NOT EXISTS eBikeRecordings" +
                    "(BikeID INT NOT NULL AUTO_INCREMENT," +
                    "Brand VARCHAR(55) NOT NULL," +
                    "Model VARCHAR(55) NOT NULL," +
                    "Color VARCHAR(55) NOT NULL," +
                    "DistanceRange INT NOT NULL," +
                    "RentPrice INT NOT NULL," +
                    "InsurancePrice INT NOT NULL," +
                    "TimesRented INT NOT NULL DEFAULT 0," +
                    "PRIMARY KEY (BikeID))";
            statement.execute(query);
            statement.close();
            connect.close();//maybe problematic
        }
        catch (SQLException ex){
            ex.printStackTrace(System.err);
        }
    }
    public void addNewEBikeRecording(eBike newBike) {
        Connection connect = null;
        Statement statement = null;
        String query = null;
        try {
            connect = DB_Connection.getConnection();
            statement = connect.createStatement();
            query = "INSERT IGNORE INTO eBikeRecordings (Brand, Model, Color, DistanceRange, " +
                    "RentPrice, InsurancePrice) VALUES ('" +
                    newBike.getBrand() + "', '" +
                    newBike.getModel() + "', '" +
                    newBike.getColor() + "', " +
                    newBike.getRange() + ", " +
                    newBike.getRentPrice() + ", " +
                    newBike.getInsurancePrice() + ")";
            statement.execute(query);
            System.out.println(query+"\neBike added successfully");
            statement.close();
            connect.close();//maybe problematic
        }
        catch (SQLException ex){
            ex.printStackTrace(System.err);
        }
    }
       public void addeBikeFromJSON(String json){
        eBike user = jsonToeBike(json);
        
         addNewEBikeRecording(user);
    }
    
     public eBike jsonToeBike(String json){
         Gson gson = new Gson();

        eBike user = gson.fromJson(json, eBike.class);
        return user;
    }
    
    public String eBikeToJSON(eBike user){
         Gson gson = new Gson();

        String json = gson.toJson(user, eBike.class);
        return json;
    }
    
}

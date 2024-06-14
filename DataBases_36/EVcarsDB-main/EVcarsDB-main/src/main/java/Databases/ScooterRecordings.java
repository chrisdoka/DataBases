package Databases;

import Vehicles.Scooter;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import com.google.gson.Gson;

public class ScooterRecordings {
    public void createScooterRecordingsTable() {
        Connection connect = null;
        Statement statement = null;
        String query = null;
        try {
            connect = DB_Connection.getConnection();
            statement = connect.createStatement();
            query = "CREATE TABLE IF NOT EXISTS ScooterRecordings" +
                    "(ScooterID INT NOT NULL AUTO_INCREMENT," +
                    "Brand VARCHAR(55) NOT NULL," +
                    "Model VARCHAR(55) NOT NULL," +
                    "Color VARCHAR(55) NOT NULL," +
                    "DistanceRange INT NOT NULL," +
                    "RentPrice INT NOT NULL," +
                    "InsurancePrice INT NOT NULL," +
                    "TimesRented INT NOT NULL DEFAULT 0,"+
                    "PRIMARY KEY (ScooterID))";
            statement.execute(query);
            statement.close();
            connect.close();//maybe problematic
        }
        catch (SQLException ex){
            ex.printStackTrace(System.err);
        }
    }
    public void addNewScooter(Scooter newScooter){
        Connection connect = null;
        Statement statement = null;
        String query = null;
        try {
            connect = DB_Connection.getConnection();
            statement = connect.createStatement();
            query = "INSERT IGNORE INTO ScooterRecordings (Brand, Model, Color, DistanceRange, RentPrice, " +
                    "InsurancePrice) VALUES ('" +
                    newScooter.getBrand() + "', '" +
                    newScooter.getModel() + "', '" +
                    newScooter.getColor() + "', " +
                    newScooter.getRange() + ", " +
                    newScooter.getRentPrice() + ", " +
                    newScooter.getInsurancePrice() + ")";
            statement.execute(query);
            System.out.println(query+"\nScooter added successfully");
            statement.close();
            connect.close();//maybe problematic
        }
        catch (SQLException ex){
            ex.printStackTrace(System.err);
        }
    }
     public void addScooterFromJSON(String json){
        Scooter user = jsonToScooter(json);
       
         addNewScooter(user);
    }
    
     public Scooter jsonToScooter(String json){
         Gson gson = new Gson();

        Scooter user = gson.fromJson(json, Scooter.class);
        return user;
    }
    
    public String ScooterToJSON(Scooter user){
         Gson gson = new Gson();

        String json = gson.toJson(user, Scooter.class);
        return json;
    }
    
   
}

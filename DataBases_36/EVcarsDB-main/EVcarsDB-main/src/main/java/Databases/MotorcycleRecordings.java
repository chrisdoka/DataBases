package Databases;

import Vehicles.Motorcycle;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import com.google.gson.Gson;

public class MotorcycleRecordings {
    public void createMotorcycleRecordingsTable() {
        Connection connect = null;
        Statement statement = null;
        String query = null;
        try {
            connect = DB_Connection.getConnection();
            statement = connect.createStatement();
            query = "CREATE TABLE IF NOT EXISTS MotorcycleRecordings" +
                    "(Brand VARCHAR(55) NOT NULL," +
                    "Model VARCHAR(55) NOT NULL," +
                    "Color VARCHAR(55) NOT NULL," +
                    "DistanceRange INT NOT NULL," +
                    "RentPrice INT NOT NULL," +
                    "InsurancePrice INT NOT NULL," +
                    "LicenseNumber INT NOT NULL," +
                    "TimesRented INT NOT NULL DEFAULT 0," +
                    "PRIMARY KEY (LicenseNumber))";
            statement.execute(query);
            statement.close();
            connect.close();//maybe problematic
        }
        catch (SQLException ex){
            ex.printStackTrace(System.err);
        }
    }
    public void addNewMotorcycle(Motorcycle newMoto){
        Connection connect = null;
        Statement statement = null;
        String query = null;
        try {
            connect = DB_Connection.getConnection();
            statement = connect.createStatement();
            query = "INSERT IGNORE INTO MotorcycleRecordings (Brand, Model, Color, DistanceRange," +
                    " RentPrice, InsurancePrice, LicenseNumber) VALUES ('" +
                    newMoto.getBrand() + "','" +
                    newMoto.getModel() + "','" +
                    newMoto.getColor() + "','" +
                    newMoto.getRange() + "',' " +
                    newMoto.getRentPrice() + "',' " +
                    newMoto.getInsurancePrice() +"',"+
                    newMoto.getLicenseNumber() +")";
            statement.execute(query);
            System.out.println(query+"\nMotorcycle added successfully");
            statement.close();
            connect.close();//maybe problematic
        }
        catch (SQLException ex){
            ex.printStackTrace(System.err);
        }
    }
    public void addMotorcycleFromJSON(String json){
        Motorcycle bt = jsonToMotorcycle(json);
        addNewMotorcycle(bt);
    }

    public Motorcycle jsonToMotorcycle(String json) {
        Gson gson = new Gson();
        Motorcycle btest = gson.fromJson(json, Motorcycle.class);
        return btest;
    }

    public String MotorcycleToJSON(Motorcycle bt) {
        Gson gson = new Gson();

        String json = gson.toJson(bt, Motorcycle.class);
        return json;
    }
}

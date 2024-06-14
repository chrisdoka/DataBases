
package Databases;

import Vehicles.Car;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import com.google.gson.Gson;

public class CarRecordings {
    public void createCarRecordingsTable() {
        Connection connect = null;
        Statement statement = null;
        String query = null;
        try {
            connect = DB_Connection.getConnection();
            statement = connect.createStatement();
            query = "CREATE TABLE IF NOT EXISTS CarRecordings" +
                    "(Brand VARCHAR(55) NOT NULL," +
                    "Model VARCHAR(55) NOT NULL," +
                    "Color VARCHAR(55) NOT NULL," +
                    "DistanceRange INT NOT NULL," +
                    "RentPrice INT NOT NULL," +
                    "InsurancePrice INT NOT NULL," +
                    "CarType VARCHAR(55) NOT NULL," +
                    "LicenseNumber INT NOT NULL," +
                    "Passengers INT NOT NULL," +
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
    public void addNewCar(Car newCar){
        Connection connect;
        Statement statement;
        try {
            connect = DB_Connection.getConnection();
            statement = connect.createStatement();
            String query = "INSERT IGNORE INTO CarRecordings (Brand, Model, Color, DistanceRange, RentPrice, InsurancePrice, CarType, Passengers, LicenseNumber) VALUES ('" +
                    newCar.getBrand() + "', '" +
                    newCar.getModel() + "', '" +
                    newCar.getColor() + "', " +
                    newCar.getRange() + ", " +
                    newCar.getRentPrice() + ", " +
                    newCar.getInsurancePrice() + ", '" +
                    newCar.getCarType() + "', " +
                    newCar.getPassengers() +",'"+
                    newCar.getLicenseNumber() +"')";
            statement.execute(query);
            System.out.println(query+"\nCar added successfully");
            statement.close();
            connect.close();
        }
        catch (SQLException ex){
            ex.printStackTrace(System.err);
        }
    }
    public void addCarFromJSON(String json){
        Car bt = jsonToCar(json);
        addNewCar(bt);
    }

    public Car jsonToCar(String json) {
        Gson gson = new Gson();
        Car btest = gson.fromJson(json, Car.class);
        return btest;
    }

    public String CarToJSON(Car bt) {
        Gson gson = new Gson();

        String json = gson.toJson(bt, Car.class);
        return json;
    }
}

package Databases;

import com.google.gson.Gson;

import java.sql.Connection;
import java.sql.Statement;

public class AccidentRecordings {
    private int RenterID;
    private int VehicleID;
    public void createAccidentRecordingsTable() {
        Connection connect;
        Statement statement;
        String query;
        try{
            connect = DB_Connection.getConnection();
            statement = connect.createStatement();
            query = "CREATE TABLE IF NOT EXISTS AccidentRecordings" +
                    "(AccidentID INT NOT NULL AUTO_INCREMENT," +
                    "VehicleID INT NOT NULL," +
                    "RenterID INT NOT NULL," +
                    "FOREIGN KEY (RenterID) REFERENCES ClientRecordings(ClientID)," +
                    "CONSTRAINT KEY1 FOREIGN KEY (VehicleID) REFERENCES carrecordings(LicenseNumber)," +
                    "CONSTRAINT KEY2 FOREIGN KEY (VehicleID) REFERENCES MotorcycleRecordings(LicenseNumber)," +
                    "CONSTRAINT KEY3 FOREIGN KEY (VehicleID) REFERENCES eBikeRecordings(BikeID)," +
                    "CONSTRAINT KEY4 FOREIGN KEY (VehicleID) REFERENCES ScooterRecordings(ScooterID)," +
                    "PRIMARY KEY (AccidentID))";
            statement.execute(query);
            statement.close();
            connect.close();
        }
        catch (Exception ex){
            ex.printStackTrace(System.err);
        }
    }
    public void ReportAccident(int vehicleID, int renterID){
        Connection connect;
        Statement statement;
        String query;
        try {
            connect = DB_Connection.getConnection();
            statement = connect.createStatement();
            query = "INSERT IGNORE INTO AccidentRecordings (VehicleID, RenterID) VALUES ('" + vehicleID + "', '" + renterID + "')";
            statement.execute(query);
            statement.execute(query);
            statement.close();
            System.out.println("Accident added successfully");
            connect.close();//maybe problematic
        }
        catch (Exception ex){
            ex.printStackTrace(System.err);
        }
    }
   

    public void addAccidentFromJSON(String json){
        AccidentRecordings accident = jsonToAccident(json);
        ReportAccident(accident.VehicleID, accident.RenterID);
    }

    public AccidentRecordings jsonToAccident(String json) {
        Gson gson = new Gson();
        AccidentRecordings accident = gson.fromJson(json, AccidentRecordings.class);
        return accident;
    }

    public String accidentToJSON(AccidentRecordings accident) {
        Gson gson = new Gson();
        String json = gson.toJson(accident, AccidentRecordings.class);
        return json;
    }



}

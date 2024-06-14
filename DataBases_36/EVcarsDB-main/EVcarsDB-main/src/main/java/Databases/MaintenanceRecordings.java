package Databases;

import com.google.gson.Gson;

import java.sql.Connection;
import java.util.Date;
import java.sql.Statement;
import java.util.Calendar;

public class MaintenanceRecordings {
    public void createMaintenanceRecordingsTable() {
        Connection connect;
        Statement statement;
        String query = null;
        try{
            connect = DB_Connection.getConnection();
            statement = connect.createStatement();
            query = "CREATE TABLE IF NOT EXISTS MaintenanceRecordings" +
                    "(MaintenanceID INT NOT NULL AUTO_INCREMENT," +
                    "MaintenanceDay DATE NOT NULL,"+
                    "VehicleID INT NOT NULL," +
                    "CONSTRAINT KEY1 FOREIGN KEY (VehicleID) REFERENCES carrecordings(LicenseNumber)," +
                    "CONSTRAINT KEY2 FOREIGN KEY (VehicleID) REFERENCES MotorcycleRecordings(LicenseNumber)," +
                    "CONSTRAINT KEY3 FOREIGN KEY (VehicleID) REFERENCES eBikeRecordings(BikeID)," +
                    "CONSTRAINT KEY4 FOREIGN KEY (VehicleID) REFERENCES ScooterRecordings(ScooterID)," +
                    "MaintenanceUntil DATE NOT NULL," +
                    "PRIMARY KEY (MaintenanceID))";
            statement.execute(query);
            statement.close();
            connect.close();//maybe problematic
        }
        catch (Exception ex){
            ex.printStackTrace(System.err);
        }
    }
    public void addNewMaintenance(int vehicleID, Date MaintenanceDay, boolean isRegularMaintenance){
        Connection connect;
        Statement statement;
        String query;
        Calendar cal = Calendar.getInstance();
        Date AvailableDay;
        cal.setTime(MaintenanceDay);
        try {
            connect = DB_Connection.getConnection();
            statement = connect.createStatement();
            if(isRegularMaintenance) {
                cal.add(Calendar.DAY_OF_MONTH, 1);
            }
            else{
                cal.add(Calendar.DAY_OF_MONTH, 3);
            }
            AvailableDay=cal.getTime();
            query = "INSERT IGNORE INTO MaintenanceRecordings (VehicleID,MaintenanceDay, MaintenanceUntil) VALUES ('" + vehicleID + "',' "+MaintenanceDay+"', '" + AvailableDay + "')";
            statement.execute(query);
            statement.close();
            System.out.println(query+"\nMaintenance added successfully");
            connect.close();//maybe problematic
        }
        catch (Exception ex){
            ex.printStackTrace(System.err);
        }
    }
     
    public void addNewMaintenanceFromJSON(int vehicleID, String maintenanceDayJson, boolean isRegularMaintenance) {
        Gson gson = new Gson();
        Date maintenanceDay = gson.fromJson(maintenanceDayJson, Date.class);
        MaintenanceRecordings maintenanceRecordings = new MaintenanceRecordings();
        maintenanceRecordings.addNewMaintenance(vehicleID, maintenanceDay, isRegularMaintenance);
    }
}

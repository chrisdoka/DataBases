package Databases;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Connection;
import java.sql.Statement;
import java.util.Scanner;

import static Databases.DB_Connection.getConnection;
import static Databases.DB_Connection.getInitialConnection;

/**
 * @author xrist,Lefteris,Demi
 */
public class InitDatabase {

    public static void main(String[] args) {
        InitDatabase init = new InitDatabase();

        if(!init.DatabaseExists()) {
            init.initDatabase();
        }
        init.initTables();
        init.DemoInserts();
        //init.dropDatabase();
    }
    public void initDatabase() {
        try {
            Connection conn = getInitialConnection();
            Statement stmt = conn.createStatement();
            stmt.execute("CREATE DATABASE EVOL_DB");
            stmt.close();
            conn.close();
        }
        catch (SQLException e) {
            e.printStackTrace(System.err);
        }
    }
    public void initTables(){
        new MotorcycleRecordings().createMotorcycleRecordingsTable();
        new CarRecordings().createCarRecordingsTable();
        new ClientRecordings().createClientRecordingsTable();
        new eBikeRecordings().createEBikeRecordingsTable();
        new ScooterRecordings().createScooterRecordingsTable();
        new RentalRecordings().createRentalRecordingsTable();
        new DamagedRecordings().createDamagedVehiclesTable();
        new AccidentRecordings().createAccidentRecordingsTable();
        new MaintenanceRecordings().createMaintenanceRecordingsTable();
    }
    public boolean DatabaseExists() {
        Connection connection;
        ResultSet resultSet;
        boolean exists = false;
        String databaseName;
        try {
            connection = getConnection();
            if(connection == null) {
                return false;
            }
            resultSet = connection.getMetaData().getCatalogs();
            while (resultSet.next()) {
                databaseName = resultSet.getString(1);
                if(databaseName.equals("evol_db")){
                    exists = true;
                    break;
                }
            }
            resultSet.close();
        }
        catch (SQLException e) {
            e.printStackTrace(System.err);
        }
        return exists;
    }
    public void dropDatabase() {
        try {
            Connection conn = getInitialConnection();
            Statement stmt = conn.createStatement();
            String drop = "DROP DATABASE EVOL_DB";
            stmt.executeUpdate(drop);
            stmt.close();
            conn.close();
        }
        catch (SQLException e) {
            e.printStackTrace(System.err);
        }
    }
    public void DemoInserts(){
        ClientRecordings demoClient = new ClientRecordings();
        demoClient.addClientFromJSON(Resources.client1JSON);
        demoClient.addClientFromJSON(Resources.client2JSON);
        demoClient.addClientFromJSON(Resources.client3JSON);
        demoClient.addClientFromJSON(Resources.client4JSON);
        demoClient.addClientFromJSON(Resources.client5JSON);
        CarRecordings car = new CarRecordings();
        car.addCarFromJSON(Resources.car1JSON);
        car.addCarFromJSON(Resources.car2JSON);
        car.addCarFromJSON(Resources.car3JSON);
        car.addCarFromJSON(Resources.car4JSON);
        car.addCarFromJSON(Resources.car5JSON);
        MotorcycleRecordings moto = new MotorcycleRecordings();
        moto.addMotorcycleFromJSON(Resources.motorcycle1JSON);
        moto.addMotorcycleFromJSON(Resources.motorcycle2JSON);
        moto.addMotorcycleFromJSON(Resources.motorcycle3JSON);
        moto.addMotorcycleFromJSON(Resources.motorcycle4JSON);
        moto.addMotorcycleFromJSON(Resources.motorcycle5JSON);
        ScooterRecordings scooter = new ScooterRecordings();
        scooter.addScooterFromJSON(Resources.scooter1JSON);
        scooter.addScooterFromJSON(Resources.scooter2JSON);
        scooter.addScooterFromJSON(Resources.scooter3JSON);
        scooter.addScooterFromJSON(Resources.scooter4JSON);
        scooter.addScooterFromJSON(Resources.scooter5JSON);
        eBikeRecordings ebike = new eBikeRecordings();
        ebike.addeBikeFromJSON(Resources.eBike1JSON);
        ebike.addeBikeFromJSON(Resources.eBike2JSON);
        ebike.addeBikeFromJSON(Resources.eBike3JSON);
        ebike.addeBikeFromJSON(Resources.eBike4JSON);
        ebike.addeBikeFromJSON(Resources.eBike5JSON);
        /*RentalRecordings rental = new RentalRecordings();
        rental.addNewRentalFromJSON(Resources.rental1JSON);
        rental.addNewRentalFromJSON(Resources.rental2JSON);
        rental.addNewRentalFromJSON(Resources.rental3JSON);
        rental.addNewRentalFromJSON(Resources.rental4JSON);
        rental.addNewRentalFromJSON(Resources.rental5JSON);
        AccidentRecordings accident = new AccidentRecordings();
        accident.addAccidentFromJSON(Resources.accident1JSON);
        accident.addAccidentFromJSON(Resources.accident2JSON);
        accident.addAccidentFromJSON(Resources.accident3JSON);
        accident.addAccidentFromJSON(Resources.accident4JSON);
        accident.addAccidentFromJSON(Resources.accident5JSON);*/
    }

}

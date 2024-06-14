package Databases;

import Clients.Client;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import com.google.gson.Gson;

public class ClientRecordings {
    public void createClientRecordingsTable() {
        Connection connect;
        Statement statement;
        String query = null;
        try {
            connect = DB_Connection.getConnection();
            statement = connect.createStatement();
            query = "CREATE TABLE IF NOT EXISTS ClientRecordings" +
                    "(ClientID INT NOT NULL AUTO_INCREMENT," +
                    "Firstname VARCHAR(55) NOT NULL," +
                    "Lastname VARCHAR(55) NOT NULL," +
                    "Address VARCHAR(55) NOT NULL," +
                    "Birthdate DATE NOT NULL," +
                    "DriverLicenseNumber INT," +
                    /*"CreditCardNumber INT," +
                    "CreditCardExpirationDate DATE," +
                    "Cvv INT," +*/
                    "PRIMARY KEY (ClientID))";
            statement.execute(query);
            statement.close();
            connect.close();//maybe problematic
        }
        catch (SQLException ex){
            ex.printStackTrace(System.err);
        }
    }
    /** Προσθέτει νέο @params client πελάτη*/
    public void addNewClient(Client client){
        Connection connect;
        Statement statement;
        String query;
        try {
            connect = DB_Connection.getConnection();
            statement = connect.createStatement();
            query = "INSERT IGNORE INTO ClientRecordings (Firstname, Lastname, Address, Birthdate, DriverLicenseNumber " +
                    "/*CreditCardNumber, CreditCardExpirationDate, Cvv*/) VALUES ('" +
                    client.getFirstname() + "', '" +
                    client.getLastname() + "','" +
                    client.getAddress() + "', '" +
                    client.getBirthdate() + "', '" +
                    client.getDriverLicenseNumber() /*+  "', '" +
                    client.getCreditCardNumber() + "', '" +
                    client.getCreditCardExpirationDate() + "', '" +
                    client.getCvv() */+"')";
            System.out.println(query);
            statement.execute(query);
            statement.close();
            System.out.println("Client added successfully");
            connect.close();//maybe problematic
        }
        catch (SQLException ex){
            ex.printStackTrace(System.err);
        }
    }

     public void addClientFromJSON(String json){
        Client user = jsonToClient(json);
        
         addNewClient(user);
    }
    
     public Client jsonToClient(String json){
         Gson gson = new Gson();

        Client user = gson.fromJson(json, Client.class);
        return user;
    }
    
    public String clientToJSON(Client user){
         Gson gson = new Gson();

        String json = gson.toJson(user, Client.class);
        return json;
    }
    
   
}

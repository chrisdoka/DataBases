package Databases;

import java.sql.*;
import com.google.gson.JsonObject;
public class DB_Connection {
    private static final String url = "jdbc:mysql://localhost";
    private static final String databaseName = "EVOL_DB";
    private static final int port =3306;
    private static final String username = "root";
    private static final String password = "";

    public static Connection getConnection() {
        Connection returnConnection = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            returnConnection = DriverManager.getConnection(url+":"+port+"/"+databaseName, username, password);
        } catch (SQLException | ClassNotFoundException | NullPointerException e) {
            e.printStackTrace(System.err);
        }
        return returnConnection;
    }
    public static Connection getInitialConnection() {
        Connection returnConnection = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            returnConnection = DriverManager.getConnection(url+":"+port, username, password);
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace(System.err);
        }
        return returnConnection;
    }
    public static String getResultsToJSON(ResultSet rs) {
        ResultSetMetaData metadata;
        JsonObject object=null;
        try {
            metadata = rs.getMetaData();
            int columnCount = metadata.getColumnCount();
            object = new JsonObject();
            for (int i = 1; i <= columnCount; i++) {
                String name = metadata.getColumnName(i);
                String value = rs.getString(i);
                object.addProperty(name, value);
            }
        }
        catch (SQLException e) {
            e.printStackTrace(System.err);
        }
        return object.toString();
    }

}

package activeRecord;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DBConnection {

    private static DBConnection dbConnection;
    private String userName;
    private String password;
    private String serverName;
    private String portNumber;
    private String dbName = "testpersonne";
    private static Connection connect;
    private Properties connectionProps;
    private DBConnection() throws SQLException {
        userName = "root";
        password = "";
        serverName = "localhost";
        portNumber = "3306";
        connectionProps = new Properties();
        connectionProps.put("user", userName);
        connectionProps.put("password", password);
        String urlDB = "jdbc:mysql://" + serverName + ":";
        urlDB += portNumber + "/" + dbName;
        connect = DriverManager.getConnection(urlDB, connectionProps);
    }


    public static synchronized Connection getConnection() {
        if (connect == null) {
            try {
                dbConnection = new DBConnection();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return connect;
    }

    public static DBConnection getDBConnection() {
        return dbConnection;
    }

    public void setNomDB(String nomDB){
        this.dbName = nomDB;
    }

    public String getNomDB() {
        return this.dbName;
    }
}


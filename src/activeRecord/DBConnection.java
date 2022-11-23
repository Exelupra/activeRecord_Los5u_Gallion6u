package activeRecord;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public static class DBConnection {

    private static DBConnection dbConnection;
    String userName;
    String password;
    String serverName;
    String portNumber;
    String dbName;
    private DBConnection() throws SQLException {
        userName = "root";
        password = "";
        serverName = "localhost";
        portNumber = "3306";
        dbName = "testpersonne";
        Properties connectionProps = new Properties();
        connectionProps.put("user", userName);
        connectionProps.put("password", password);
        String urlDB = "jdbc:mysql://" + serverName + ":";
        urlDB += portNumber + "/" + dbName;
        Connection connect = DriverManager.getConnection(urlDB, connectionProps);
    }

    public static synchronized DBConnection getConnection() {
        if (dbConnection == null) {
            try {
                dbConnection = new DBConnection();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return dbConnection;
    }

    public void setNomDB(String nomDB){
        this.dbName = nomDB;
    }
}


package activeRecord;

import java.sql.*;
import java.util.Properties;

public class Personne {

    int id;
    String nom;
    String prenom;

    public Personne(String nom, String prenom) {
        this.nom = nom;
        this.prenom = prenom;
        this.id = -1;
    }

    public void findAll() throws SQLException {
        String userName = "root";
        String password = "";
        String serverName = "localhost";
        //Attention, sous MAMP, le port est 8889
        String portNumber = "3306";
        String tableName = "personne";

        // iL faut une base nommee testPersonne !
        String dbName = "testpersonne";

        // creation de la connection
        Properties connectionProps = new Properties();
        connectionProps.put("user", userName);
        connectionProps.put("password", password);
        String urlDB = "jdbc:mysql://" + serverName + ":";
        urlDB += portNumber + "/" + dbName;


        Connection connect = DriverManager.getConnection(urlDB, connectionProps);
        String SQLPrep = "SELECT * FROM Personne;";
        PreparedStatement prep1 = connect.prepareStatement(SQLPrep);
        prep1.execute();
        ResultSet rs = prep1.getResultSet();
        // s'il y a un resultat
        while (rs.next()) {
            String nom = rs.getString("nom");
            String prenom = rs.getString("prenom");
            int id = rs.getInt("id");
            System.out.println("  -> (" + id + ") " + nom + ", " + prenom);
        }


    }

    public void findById(int Id) throws SQLException {
        String userName = "root";
        String password = "";
        String serverName = "localhost";
        //Attention, sous MAMP, le port est 8889
        String portNumber = "3306";
        String tableName = "personne";

        // iL faut une base nommee testPersonne !
        String dbName = "testpersonne";

        // creation de la connection
        Properties connectionProps = new Properties();
        connectionProps.put("user", userName);
        connectionProps.put("password", password);
        String urlDB = "jdbc:mysql://" + serverName + ":";
        urlDB += portNumber + "/" + dbName;
        Connection connect = DriverManager.getConnection(urlDB, connectionProps);


        String SQLPrep = "SELECT id FROM Personne where id=?;";
        PreparedStatement prep1 = connect.prepareStatement("SELECT * FROM Personne where id=?");
        prep1.setInt(1, Id);
        prep1.execute();
        ResultSet rs = prep1.getResultSet();
        // s'il y a un resultat
        while (rs.next()) {

            String nom = rs.getString("nom");
            String prenom = rs.getString("prenom");
            int id = rs.getInt("id");

                System.out.println("  -> (" + id + ") " + nom + ", " + prenom);
            }
        }



    public void findByName(String nom) throws SQLException {
        String userName = "root";
        String password = "";
        String serverName = "localhost";
        //Attention, sous MAMP, le port est 8889
        String portNumber = "3306";
        String tableName = "personne";

        // iL faut une base nommee testPersonne !
        String dbName = "testpersonne";

        // creation de la connection
        Properties connectionProps = new Properties();
        connectionProps.put("user", userName);
        connectionProps.put("password", password);
        String urlDB = "jdbc:mysql://" + serverName + ":";
        urlDB += portNumber + "/" + dbName;
        Connection connect = DriverManager.getConnection(urlDB, connectionProps);


        String SQLPrep = "SELECT id FROM Personne where id=?;";
        PreparedStatement prep1 = connect.prepareStatement("SELECT * FROM Personne where nom=?");
        prep1.setString(1, nom);
        prep1.execute();
        ResultSet rs = prep1.getResultSet();
        // s'il y a un resultat
        while (rs.next()) {
            String nomt = rs.getString("nom");
            String prenom = rs.getString("prenom");
            int id = rs.getInt("id");
            System.out.println("  -> (" + id + ") " + nomt + ", " + prenom);
        }
    }
}

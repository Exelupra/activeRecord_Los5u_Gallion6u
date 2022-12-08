package activeRecord;

import java.sql.*;
import java.util.List;
import java.util.Objects;

public class Personne {

    int id;
    String nom;
    String prenom;

    public Personne(String nom, String prenom) {
        this.nom = nom;
        this.prenom = prenom;
        this.id = -1;
    }


    public static List<Personne> findAll() throws SQLException {
        Connection connect = DBConnection.getConnection();

        String SQLPrep = "SELECT * FROM Personne;";
        PreparedStatement prep1 = connect.prepareStatement(SQLPrep);
        prep1.execute();
        ResultSet rs = prep1.getResultSet();
        // s'il y a un resultat
        List<Personne> personnes = null;
        while (rs.next()) {
            String nom = rs.getString("nom");
            String prenom = rs.getString("prenom");
            int id = rs.getInt("id");
            Personne personne = new Personne(nom, prenom);
            personnes = List.of(personne);

        }
        return personnes;

    }

    public static Personne findById(int Id) throws SQLException {
        boolean trouve = false;
        Connection connect = DBConnection.getConnection();
        PreparedStatement prep1 = connect.prepareStatement("SELECT * FROM Personne where id=?");
        prep1.setInt(1, Id);
        prep1.execute();
        ResultSet rs = prep1.getResultSet();
        // s'il y a un resultat
        Personne personne = null;
        while (rs.next()) {
            String nom = rs.getString("nom");
            String prenom = rs.getString("prenom");
            int id = rs.getInt("id");
            trouve = true;
            personne = new Personne(nom, prenom);
        }
        if (!trouve) {
            return null;
        }
        return personne;
    }


    public static List<Personne> findByName(String nom) throws SQLException {
        Connection connect = DBConnection.getConnection();

        PreparedStatement prep1 = connect.prepareStatement("SELECT * FROM Personne where nom=?");
        prep1.setString(1, nom);
        prep1.execute();
        ResultSet rs = prep1.getResultSet();
        // s'il y a un resultat
        Personne personne;
        List<Personne> personnes = null;
        while (rs.next()) {
            String nomt = rs.getString("nom");
            String prenom = rs.getString("prenom");
            int id = rs.getInt("id");
            personne = new Personne(nomt, prenom);
            personnes = List.of(personne);
        }
        return personnes;
    }

    public static void createTable() {
        try {
            Connection connect = DBConnection.getConnection();
            Statement stmt = connect.createStatement();
            String createString = "CREATE TABLE Personne ( " + "ID INTEGER  AUTO_INCREMENT, "
                    + "NOM varchar(40) NOT NULL, " + "PRENOM varchar(40) NOT NULL, " + "PRIMARY KEY (ID))";
            stmt.executeUpdate(createString);
            System.out.println("1) creation table Personne\n");
        }catch (SQLException e) {
            System.out.println("TA UN PUTAI N DE PROBLEME");
            e.printStackTrace();
        }
    }

    public static void deleteTable() {

        try {
            Connection connect = DBConnection.getConnection();
            Statement stmt = connect.createStatement();
            String sql = "DROP TABLE Personne";
            stmt.executeUpdate(sql);
            System.out.println("Table supprimee");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void saveNew() {

        try {
            Connection connect = DBConnection.getConnection();
            Statement stmt = connect.createStatement();
            this.id = this.getMaxId() + 1;
            String sql = "INSERT INTO Personne (id, nom, prenom) VALUES (" + this.id + ", '" + this.nom + "', '" + this.prenom + "')";
            stmt.executeUpdate(sql);
            System.out.println("Personne sauvegardee");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void update() {

        try {
            Connection connect = DBConnection.getConnection();
            Statement stmt = connect.createStatement();
            String sql = "UPDATE Personne SET nom = '" + this.nom + "', prenom = '" + this.prenom + "' WHERE id = " + this.id;
            stmt.executeUpdate(sql);
            System.out.println("Personne modifiee");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void save() {
        if (this.id == -1) {
            this.saveNew();
        } else {
            this.update();
        }
    }

    public int getId() {
        return id;
    }
    public int getMaxId() throws SQLException {
        Connection connect = DBConnection.getConnection();
        Statement stmt = connect.createStatement();
        String sql = "SELECT MAX(id) FROM Personne";
        ResultSet rs = stmt.executeQuery(sql);
        int maxId = 0;
        while (rs.next()) {
            maxId = rs.getInt("MAX(id)");
        }
        return maxId;
    }
    public void setNom(String nom) {
        this.nom = nom;
    }
    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }
    public String getNom() {
       return nom;
    }
    public String getPrenom() {
        return this.prenom;
    }
    public void delete() {
        try {
            Connection connect = DBConnection.getConnection();
            Statement stmt = connect.createStatement();
            String sql = "DELETE FROM Personne WHERE id = " + this.id;
            stmt.executeUpdate(sql);
            this.id = -1;
            System.out.println("Personne supprimee");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

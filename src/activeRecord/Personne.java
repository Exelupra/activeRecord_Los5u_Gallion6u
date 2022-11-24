package activeRecord;

import java.sql.*;
import java.util.List;
import java.util.Objects;

public class Personne {

    int id;
    String nom;
    String prenom;

    public Personne(String nom, String prenom, Integer id) {
        this.nom = nom;
        this.prenom = prenom;
        this.id = Objects.requireNonNullElse(id, -1);
    }

    public Personne() {
        this.nom = "test";
        this.prenom = "test";
        this.id = -1;
    }

    public List<Personne> findAll() throws SQLException {
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
            Personne personne = new Personne(nom, prenom, id);
            personnes = List.of(personne);

        }
        return personnes;

    }

    public Personne findById(int Id) throws SQLException {
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
            personne = new Personne(nom, prenom, id);
        }
        if (!trouve) {
            return null;
        }
        return personne;
    }


    public List<Personne> findByName(String nom) throws SQLException {
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
            personne = new Personne(nomt, prenom, id);
            personnes = List.of(personne);
        }
        return personnes;
    }

    public static void createTable() {
        try {
            Connection connect = DBConnection.getConnection();
            Statement stmt = connect.createStatement();
            String sql = "CREATE TABLE Personne " +
                    "(id INTEGER not NULL, " +
                    " nom VARCHAR(255), " +
                    " prenom VARCHAR(255), " +
                    " PRIMARY KEY ( id ))";
            stmt.executeUpdate(sql);
            System.out.println("Table creee");
        } catch (SQLException e) {
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

    public void save() {

        try {
            Connection connect = DBConnection.getConnection();
            Statement stmt = connect.createStatement();
            String sql = "INSERT INTO Personne (id, nom, prenom) VALUES (" + this.id + ", '" + this.nom + "', '" + this.prenom + "')";
            stmt.executeUpdate(sql);
            System.out.println("Personne sauvegardee");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

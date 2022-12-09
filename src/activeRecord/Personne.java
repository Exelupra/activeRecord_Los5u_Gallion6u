package activeRecord;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Personne {

    int id;
    String nom;
    String prenom;

    /**
     * Constructeur de la classe Personne
     * @param nom nom de la personne
     * @param prenom prenom de la personne
     */
    public Personne(String nom, String prenom) {
        this.nom = nom;
        this.prenom = prenom;
        this.id = -1;
    }

    /**
     * Retourne la liste de toutes les personnes dans la table Personne
     * @return ArrayList<Personne> liste de toutes les personnes
     * @throws SQLException quand la requete SQL est incorrecte
     */
    public static ArrayList<Personne> findAll() throws SQLException {
        Connection connect = DBConnection.getConnection();

        String SQLPrep = "SELECT * FROM Personne;";
        PreparedStatement prep1 = connect.prepareStatement(SQLPrep);
        prep1.execute();
        ResultSet rs = prep1.getResultSet();
        // s'il y a un resultat
        ArrayList<Personne> personnes = new ArrayList<>();
        int nb=0;
        while (rs.next()) {
            String nom = rs.getString("nom");
            String prenom = rs.getString("prenom");
            Personne personne = new Personne(nom, prenom);
            personne.id = rs.getInt(1);
            personnes.add(nb, personne);
            nb++;

        }
        return personnes;

    }

    /**
     * Retourne la personne à qui appartient l'id
     * @param Id id de la personne recherchée
     * @return Personne personne à qui appartient l'id
     * @throws SQLException quand la requete SQL est incorrecte
     */
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
            trouve = true;
            personne = new Personne(nom, prenom);
            personne.id = rs.getInt(1);
        }
        if (!trouve) {
            return null;
        }
        return personne;
    }

    /**
     * Retourne la liste des personnes qui ont le nom passé en paramètre
     * @param nom nom des personnes recherchées
     * @return ArrayList<Personne> liste des personnes qui ont le nom passé en paramètre
     * @throws SQLException quand la requete SQL est incorrecte
     */
    public static ArrayList<Personne> findByName(String nom) throws SQLException {
        Connection connect = DBConnection.getConnection();

        PreparedStatement prep1 = connect.prepareStatement("SELECT * FROM Personne where nom=?");
        prep1.setString(1, nom);
        prep1.execute();
        ResultSet rs = prep1.getResultSet();
        // s'il y a un resultat
        Personne personne;
        ArrayList<Personne> personnes = new ArrayList<>();
        int nb = 0;
        while (rs.next()) {
            String nomt = rs.getString("nom");
            String prenom = rs.getString("prenom");
            personne = new Personne(nomt, prenom);
            personne.id = rs.getInt(1);
            personnes.add(nb, personne);
            nb++;
        }
        return personnes;
    }

    /**
     * crée la table Personne
     */
    public static void createTable() {
        try {
            Connection connect = DBConnection.getConnection();
            Statement stmt = connect.createStatement();
            String createString = "CREATE TABLE Personne ( " + "ID int  AUTO_INCREMENT, "
                    + "NOM varchar(40) NOT NULL, " + "PRENOM varchar(40) NOT NULL, " + "PRIMARY KEY (ID))";
            stmt.executeUpdate(createString);
            System.out.println("1) creation table Personne\n");
        }catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Supprime la table Personne
     */
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

    /**
     * Insert une nouvelle personne dans la table Personne
     */
    public void saveNew() {

        try {
            Connection connect = DBConnection.getConnection();
            Statement stmt = connect.createStatement();
            String sql = "INSERT INTO Personne ( nom, prenom) VALUES (" + " '" + this.nom + "', '" + this.prenom + "')";
            stmt.executeUpdate(sql, Statement.RETURN_GENERATED_KEYS);
            System.out.println("Personne sauvegardee");
            ResultSet rs = stmt.getGeneratedKeys();
            rs.next();
            this.id = rs.getInt(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Met à jour la personne dans la base de données
     */
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

    /**
     * permet d'enregistrer une nouvelle personne
     * ou de modifier les informations d'une personne deja presente dans la base de donnees
     */
    public void save() {
        if (this.id == -1) {
            this.saveNew();
        } else {
            this.update();
        }
    }

    /**
     * getter qui renvoie l'id de la personne
     * @return int id de la personne
     */
    public int getId() {
        return id;
    }

    /**
     * setter qui modifie le nom de la personne
     * @param nom nouveau nom de la personne
     */
    public void setNom(String nom) {
        this.nom = nom;
    }

    /**
     * setter qui modifie le prenom de la personne
     * @param prenom nouveau prenom de la personne
     */
    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    /**
     * getter qui renvoie le nom de la personne
     * @return String nom de la personne
     */
    public String getNom() {
        return nom;
    }

    /**
     * getter qui renvoie le prenom de la personne
     * @return String prenom de la personne
     */
    public String getPrenom() {
        return this.prenom;
    }

    /**
     * supprime la personne de la base de données
     */
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
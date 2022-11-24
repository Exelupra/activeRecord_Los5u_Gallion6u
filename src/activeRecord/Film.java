package activeRecord;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

//Chaque objet Film sera censé représenter un tuple de la classe Film selon le patron active record
public class Film {

    private int id;
    private String titre;
    private int id_rea;

    public Film() {
        this.id = -1;
        this.titre = "";
        this.id_rea = -1;
    }

    public Film(int id, String titre, int id_rea) {
        this.id = id;
        this.titre = titre;
        this.id_rea = id_rea;
    }
    public Film findById(int id_recherche) throws SQLException {
        Connection connect = DBConnection.getConnection();
        PreparedStatement prep1 = connect.prepareStatement("SELECT * FROM Film where id=?");
        prep1.setInt(1, id_recherche);
        prep1.execute();
        ResultSet rs = prep1.getResultSet();
        while (rs.next()) {
            String titre = rs.getString("titre");
            int id_rea = rs.getInt("id_rea");
            int id = rs.getInt("id");
        }
        Film film = new Film(id, titre, id_rea);
        return film;
    }

    public Personne getRealisateur() throws SQLException {
        Personne realisateur = new Personne();
        realisateur.findById(this.id_rea);
        return realisateur;
    }

    public static void createTable(){
        Connection connect = DBConnection.getConnection();
        try {
            PreparedStatement prep1 = connect.prepareStatement("CREATE TABLE Film (id INT NOT NULL AUTO_INCREMENT, titre VARCHAR(255), id_rea INT, PRIMARY KEY (id));");
            prep1.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void deleteTable(){
        Connection connect = DBConnection.getConnection();
        try {
            PreparedStatement prep1 = connect.prepareStatement("DROP TABLE Film;");
            prep1.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }



}

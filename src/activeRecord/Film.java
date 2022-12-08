package activeRecord;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

//Chaque objet Film sera censé représenter un tuple de la classe Film selon le patron active record
public class Film {

    private int id;
    private String titre;
    private int id_real;

    public Film(String titre, Personne p){
        this.titre = titre;
        this.id_real = p.getId();
        this.id = -1;
    }

    private Film(String titre, int id_real, int id){
        this.titre = titre;
        this.id_real = id_real;
        this.id = id;
    }
    public static Film findById(int id_recherche) throws SQLException {
        Connection connect = DBConnection.getConnection();
        PreparedStatement prep1 = connect.prepareStatement("SELECT * FROM Film where id=?");
        prep1.setInt(1, id_recherche);
        prep1.execute();
        ResultSet rs = prep1.getResultSet();
        Film film = null;
        while (rs.next()) {
            String titre = rs.getString("titre");
            int id_real = rs.getInt("id_rea");
            int id = rs.getInt("id");
            film = new Film(titre, id_real, id);
        }
        return film;
    }

    public Personne getRealisateur() throws SQLException {
        Personne realisateur = Personne.findById(this.id_real);
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

    public void save() throws SQLException, RealisateurAbsentException {
        if (this.id_real == -1) {
            throw new RealisateurAbsentException("Le realisateur n'est pas enregistre dans la base");
        }
        if (this.id == -1) {
            this.saveNew();
        }else{
            this.update();
        }
    }

    public void update() throws SQLException {
        Connection connect = DBConnection.getConnection();
        PreparedStatement prep1 = connect.prepareStatement("UPDATE Film SET titre=?, id_rea=? WHERE id=?");
        prep1.setString(1, this.titre);
        prep1.setInt(2, this.id_real);
        prep1.setInt(3, this.id);
        prep1.execute();
    }

    public void saveNew(){
        Connection connect = DBConnection.getConnection();
        try {
            PreparedStatement prep1 = connect.prepareStatement("INSERT INTO Film (titre, id_rea) VALUES (?, ?);");
            prep1.setString(1, this.titre);
            prep1.setInt(2, this.id_real);
            prep1.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static List<Film> findByRealisateur(Personne p) throws SQLException {
        List<Film> films = null;
        Connection connect = DBConnection.getConnection();
        PreparedStatement prep1 = connect.prepareStatement("SELECT * FROM Film where id_rea=?");
        prep1.setInt(1, p.getId());
        prep1.execute();
        ResultSet rs = prep1.getResultSet();
        while (rs.next()) {
            String titre = rs.getString("titre");
            int id_real = rs.getInt("id_rea");
            int id = rs.getInt("id");
            Film film = new Film(titre, id_real, id);
            films.add(film);
        }
        return films;
    }

}

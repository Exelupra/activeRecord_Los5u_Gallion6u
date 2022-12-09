package activeRecord;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

//Chaque objet Film sera censé représenter un tuple de la classe Film selon le patron active record
public class Film {

    private int id;
    private String titre;
    private int id_real;

    /**
     * Constructeur de la classe Film avec deux paramètres
     * @param titre titre du film
     * @param p réalisateur du film
     */
    public Film(String titre, Personne p){
        this.titre = titre;
        this.id_real = p.getId();
        this.id = -1;
    }

    /**
     * constructeur de la classe Film avec trois paramètres, constructeur utilisé uniquement dans la classe film
     * @param titre titre du film
     * @param id_real id du réalisateur du film
     * @param id id du film
     */
    private Film(String titre, int id_real, int id){
        this.titre = titre;
        this.id_real = id_real;
        this.id = id;
    }

    /**
     * Methode qui recherche un fillm dans la base de données à partir de son id
     * @param id_recherche id du film recherché
     * @return Film film recherché correpsondant à l'id
     * @throws SQLException si la requête ne peut pas être exécutée
     */
    public static Film findById(int id_recherche) throws SQLException {
        // on se connecte à la base de données
        Connection connect = DBConnection.getConnection();
        // on prépare la requête
        PreparedStatement prep1 = connect.prepareStatement("SELECT * FROM Film where id=?");
        // on remplace le ? par l'id recherché
        prep1.setInt(1, id_recherche);
        // on exécute la requête
        prep1.execute();
        // on récupère le résultat de la requête
        ResultSet rs = prep1.getResultSet();
        Film film = null;
        // si le résultat n'est pas vide
        while (rs.next()) {
            String titre = rs.getString("titre");
            int id_real = rs.getInt("id_rea");
            int id = rs.getInt("id");
            // on crée un objet film avec les informations récupérées
            film = new Film(titre, id_real, id);
        }
        return film;
    }

    /**
     * Getter qui renvoit le réalisateur d'un film
     * @return Personne réalisateur du film
     * @throws SQLException si la requête ne peut pas être exécutée
     */
    public Personne getRealisateur() throws SQLException {
        // grace a l'id du réalisateur on récupère le réalisateur avec la methode findById de la classe Personne
        Personne realisateur = Personne.findById(this.id_real);
        return realisateur;
    }

    /**
     * methode de création de la table film dans la base de données
     */
    public static void createTable(){
        // on se connecte à la base de données
        Connection connect = DBConnection.getConnection();
        try {
            // on prépare la requête de création de la table
            PreparedStatement prep1 = connect.prepareStatement("CREATE TABLE Film (id INT NOT NULL AUTO_INCREMENT, titre VARCHAR(255), id_rea INT, PRIMARY KEY (id));");
            prep1.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * methode de suppression de la table film dans la base de données
     */
    public static void deleteTable(){
        // on se connecte à la base de données
        Connection connect = DBConnection.getConnection();
        try {
            // on prépare la requête de suppression de la table
            PreparedStatement prep1 = connect.prepareStatement("DROP TABLE Film;");
            prep1.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * methode qui permet d'insérer un film dans la base de données ou de le mettre à jour si il existe déjà
     * @throws SQLException
     * @throws RealisateurAbsentException
     */
    public void save() throws SQLException, RealisateurAbsentException {
        // si l'id du réalisateur est égal à -1 alors le réalisateur n'existe pas dans la base de données
        if (this.id_real == -1) {
            // on lance une exception de type RealisateurAbsentException pour indiquer que le réalisateur n'existe pas dans la base de données
            throw new RealisateurAbsentException("Le realisateur n'est pas enregistre dans la base");
        }
        // si l'id du film est égal à -1 alors le film n'existe pas dans la base de données
        if (this.id == -1) {
            // on enregistre le film dans la base de données
            this.saveNew();
        }else{
            // sinon on met à jour le film dans la base de données
            this.update();
        }
    }

    /**
     * methode qui permet de modifier un film dans la base de données
     * @throws SQLException si la requête ne peut pas être exécutée
     */
    public void update() throws SQLException {
        // on se connecte à la base de données
        Connection connect = DBConnection.getConnection();
        // on prépare la requête de mise à jour du film dans la base de données
        PreparedStatement prep1 = connect.prepareStatement("UPDATE Film SET titre=?, id_rea=? WHERE id=?");
        prep1.setString(1, this.titre);
        prep1.setInt(2, this.id_real);
        prep1.setInt(3, this.id);
        prep1.execute();
    }

    /**
     * methode qui permet d'enregistrer un film dans la base de données
     */
    public void saveNew(){
        // on se connecte à la base de données
        Connection connect = DBConnection.getConnection();
        try {
            // on prépare la requête d'enregistrement du film dans la base de données
            PreparedStatement prep1 = connect.prepareStatement("INSERT INTO Film (titre, id_rea) VALUES (?, ?);", Statement.RETURN_GENERATED_KEYS);
            prep1.setString(1, this.titre);
            prep1.setInt(2, this.id_real);
            prep1.executeUpdate();
            ResultSet rs = prep1.getGeneratedKeys();
            rs.next();
            this.id = rs.getInt(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * methode qui permet de retourner une liste de film en fonction d'un réalisateur
     * @param p Personne réalisateur
     * @return ArrayList de Film
     * @throws SQLException si la requête ne peut pas être exécutée
     */
    public static ArrayList<Film> findByRealisateur(Personne p) throws SQLException {
        ArrayList<Film> films = new ArrayList<Film>();
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

    /**
     * getter de titre du film
     * @return String titre du film
     */
    public String getTitre() {
        return this.titre;
    }

    /**
     * getter de l'id du film
     * @return int id du film
     */
    public int getId() {
        return this.id;
    }

    /**
     * setter de tire du film, pour modifier le titre du film
     */
    public void setTitre(String s) {
        this.titre = s;
    }
}

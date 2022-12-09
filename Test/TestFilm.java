import activeRecord.Film;
import activeRecord.Personne;
import activeRecord.RealisateurAbsentException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class TestFilm {

    Personne colombus;
    Personne cuaron;
    Film film;
    Film film2;
    Film film3;

    /**
     * creation de la table personne et ajout de 2 réalisateurs
     * creation de la table film et ajout de 3 films
     */
    @BeforeEach
    public void BeforeEach() throws SQLException, RealisateurAbsentException {
        Film.createTable();
        Personne.createTable();
        colombus = new Personne("Chris", "Colombus");
        colombus.save();
        cuaron = new Personne("Alfonso", "Cuaron");
        cuaron.save();
        film = new Film("Harry Potter à l'école des sorciers", colombus);
        film.save();
        film2 = new Film("Harry Potter et la chambre des secrets", colombus);
        film2.save();
        film3 = new Film("Harry Potter et le prisonnier d'Azkaban", cuaron);
        film3.save();
    }


    /**
     * Test de la methode findByID quand le film est enregistré dans la base de données
     * @throws SQLException quand la requete ne fonctionne pas
     */
    @Test
    public void testfindById1() throws SQLException {
        Film film = Film.findById(1);
        assertEquals("Harry Potter à l'école des sorciers", film.getTitre());
    }

    /**
     * Test de la methode findByID quand le film n'est pas enregistré dans la base de données
     * @throws SQLException quand la requete ne fonctionne pas
     */
    @Test
    public void testfindById2() throws SQLException {
        Film film = Film.findById(15);
        assertEquals(null, film);
    }

    /**
     * Test de la methode findByRealisateur quand le réalisateur est enregistré dans la base de données
     * @throws SQLException quand la requete ne fonctionne pas
     */
    @Test
    public void testfindByRealisateur1() throws SQLException {
        ArrayList<Film> film = Film.findByRealisateur(cuaron);
        assertEquals(1, film.size());
    }

    /**
     * Test de la methode findByRealisateur quand le réalisateur n'est pas enregistré dans la base de données
     * @throws SQLException quand la requete ne fonctionne pas
     */
    @Test
    public void testfindByRealisateur2() throws SQLException {
        Personne personne = Personne.findById(3);
        // verification de levée d'exception quand le réalisateur n'est pas enregistré dans la base de données
        assertThrows(NullPointerException.class, () -> {
            Film.findByRealisateur(personne);
        });
    }

    /**
     * Test de la methode getRealisateur quand le réalisateur est enregistré dans la base de données
     * @throws SQLException quand la requete ne fonctionne pas
     */
    @Test
    public void testgetRealisateur1() throws SQLException {
        Personne personne = film.getRealisateur();
        assertEquals("Chris", personne.getNom());
        assertEquals("Colombus", personne.getPrenom());
    }

    /**
     * Test de la methode save quand on insere un film dans la base de données
     * @throws SQLException quand la requete ne fonctionne pas
     * @throws RealisateurAbsentException quand le réalisateur n'est pas enregistré dans la base de données
     */
    @Test
    public void testsave1() throws SQLException, RealisateurAbsentException {
        Film film = new Film("Harry Potter et la coupe de feu", colombus);
        film.save();
        assertEquals(4, film.getId());
    }

    /**
     * Test de la methode save quand on modifie un film deja enregistré dans la base de données
     * @throws SQLException quand la requete ne fonctionne pas
     * @throws RealisateurAbsentException quand le réalisateur n'est pas enregistré dans la base de données
     */
    @Test
    public void testsave2() throws SQLException, RealisateurAbsentException {
        film.setTitre("Harry Potter et la coupe de feu");
        film.save();
        Film film2 = Film.findById(1);
        assertEquals("Harry Potter et la coupe de feu", film2.getTitre());
    }

    /**
     * Test de la methode save quand le réalisateur n'est pas enregistré dans la base de données
     * @throws SQLException quand la requete ne fonctionne pas
     * @throws RealisateurAbsentException quand le réalisateur n'est pas enregistré dans la base de données
     */
    @Test
    public void testsave3(){
        Film film = new Film("Harry Potter et la coupe de feu", new Personne("Mike", "Newell"));
        assertThrows(RealisateurAbsentException.class, () -> {
            film.save();
        });
    }

    /**
     * suppression des tables film et personne
     */
    @AfterEach
    public void AfterEach() {
        Film.deleteTable();
        Personne.deleteTable();
    }
}
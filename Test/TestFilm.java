import activeRecord.Film;
import activeRecord.Personne;
import activeRecord.RealisateurAbsentException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class TestFilm {

    Personne colombus;
    Personne cuaron;
    Film film;
    Film film2;
    Film film3;
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


    @Test
    public void testfindById1() throws SQLException {
        Film film = Film.findById(1);
        assertEquals("Harry Potter à l'école des sorciers", film.getTitre());
    }

    @Test
    public void testfindById2() throws SQLException {
        Film film = Film.findById(15);
        assertEquals(null, film);
    }

    @Test
    public void testfindByRealisateur1() throws SQLException {
        List<Film> film = Film.findByRealisateur(cuaron);
        assertEquals(1, film.size());
    }

    @Test
    public void testfindByRealisateur2() throws SQLException {
        Personne personne = Personne.findById(3);
        assertThrows(NullPointerException.class, () -> {
            Film.findByRealisateur(personne);
        });
    }

    @Test
    public void testgetRealisateur1() throws SQLException {
        Personne personne = film.getRealisateur();
        assertEquals("Chris", personne.getNom());
        assertEquals("Colombus", personne.getPrenom());
    }

    @Test
    public void testsave1() throws SQLException, RealisateurAbsentException {
        Film film = new Film("Harry Potter et la coupe de feu", colombus);
        film.save();
        assertEquals(4, film.getId());
    }

    @Test
    public void testsave2() throws SQLException, RealisateurAbsentException {
        film.setTitre("Harry Potter et la coupe de feu");
        film.save();
        Film film2 = Film.findById(1);
        assertEquals("Harry Potter et la coupe de feu", film2.getTitre());
    }
    @Test
    public void testsave3(){
        Film film = new Film("Harry Potter et la coupe de feu", new Personne("Mike", "Newell"));
        assertThrows(RealisateurAbsentException.class, () -> {
            film.save();
        });
    }

    @AfterEach
    public void AfterEach() {
        Film.deleteTable();
        Personne.deleteTable();
    }
}
import activeRecord.Film;
import activeRecord.Personne;
import activeRecord.RealisateurAbsentException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

import java.sql.SQLException;

public class TestFilm {

    @BeforeEach
    public void BeforeEach() throws SQLException, RealisateurAbsentException {
        Film.deleteTable();
        Film.createTable();
        Film film = new Film("Harry Potter à l'école des sorciers", new Personne("Chris", "Colombus", 1));
        film.save();
        Film film2 = new Film("Harry Potter et la chambre des secrets", new Personne("Chris", "Colombus", 1));
        film2.save();
        Film film3 = new Film("Harry Potter et le prisonnier d'Azkaban", new Personne("Alfonso", "Cuaron", 1));
        film3.save();
    }



    @AfterEach
    public void AfterEach() {
        Film.deleteTable();
    }
}
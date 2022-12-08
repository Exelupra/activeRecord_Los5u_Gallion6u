import activeRecord.Film;
import activeRecord.Personne;
import activeRecord.RealisateurAbsentException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

import java.sql.SQLException;

public class TestFilm {

    @BeforeEach
    public void BeforeEach() throws SQLException, RealisateurAbsentException {
        Film.createTable();
        Personne.createTable();
        Personne colombus = new Personne("Chris", "Colombus");
        colombus.save();
        Personne cuaron = new Personne("Alfonso", "Cuaron");
        cuaron.save();
        Film film = new Film("Harry Potter à l'école des sorciers", colombus);
        film.save();
        Film film2 = new Film("Harry Potter et la chambre des secrets", colombus);
        film2.save();
        Film film3 = new Film("Harry Potter et le prisonnier d'Azkaban", cuaron);
        film3.save();
    }



    @AfterEach
    public void AfterEach() {
        Film.deleteTable();
        Personne.deleteTable();
    }
}
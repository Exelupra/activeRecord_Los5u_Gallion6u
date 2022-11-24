import activeRecord.Film;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

public class TestFilm {

    @BeforeEach
    public void BeforeEach() {
        Film.createTable();
    }

    @AfterEach
    public void AfterEach() {
        Film.deleteTable();
    }
}
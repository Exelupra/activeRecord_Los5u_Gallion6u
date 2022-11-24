import activeRecord.DBConnection;
import activeRecord.Personne;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;
public class TestPersonne {
Personne personne;
    @BeforeEach
    public void BeforeEach() {
        personne.createTable();
        personne = new Personne("Dupont", "Jean", 1);
        personne.save();
    }
    @AfterEach
        public void AfterEach() {
        personne.deleteTable();
    }

}

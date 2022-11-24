import activeRecord.DBConnection;
import activeRecord.Personne;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
public class TestPersonne {
Personne personne;
    @BeforeEach
    public void BeforeEach() {
        personne.createTable();
    }
}

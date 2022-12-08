import activeRecord.DBConnection;
import activeRecord.Personne;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.List;


import static org.junit.Assert.assertEquals;

public class TestPersonne {
//before each pour crÃ©er la table

    Personne personne1;
    Personne personne2;

    @BeforeEach
    public void BeforeEach() {
        Personne.createTable();
        personne1 = new Personne("Durand", "Pierre");
        personne1.save();
        personne2 = new Personne("Durand", "PierreA");
        personne2.save();
    }


    @Test
    public void testSave() throws SQLException {
        List<Personne> personnes = Personne.findAll();
        assertEquals(2, personnes.size());
        Personne personne = new Personne("Durand", "Pierre");
        personne.save();
        personnes = Personne.findAll();
        assertEquals(3, personnes.size());
    }

    @Test
    public void testUpdate() {
        Personne personne = new Personne("Durand", "Pierre");
        personne.save();
        personne.setNom("Durand");
        personne.setPrenom("PierreB");
        personne.save();
        assertEquals("Durand", personne.getNom());
        assertEquals("PierreB", personne.getPrenom());

    }

    @Test
    public void testDelete() {
        personne1.delete();
        assertEquals(-1, personne1.getId());
    }

    @Test
    public void testFindById() throws SQLException {
        Personne personne2 = Personne.findById(2);
        assertEquals("PierreA", personne2.getPrenom());
        assertEquals("Durand", personne2.getNom());
    }

    @Test
    public void testFindAll() throws SQLException {
        ;
        List<Personne> p = Personne.findAll();
        assertEquals(2, p.size());
    }

    @Test
    public void testFindlByName() throws SQLException {
        List<Personne> p = Personne.findByName("Durand");
        assertEquals(2, p.size());
    }

    @AfterEach
    public void AfterEach() {
        Personne.deleteTable();
    }

}
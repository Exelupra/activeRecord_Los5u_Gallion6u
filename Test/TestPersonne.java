import activeRecord.Personne;
import org.junit.Test;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

import java.sql.SQLException;
import java.util.List;


import static org.junit.Assert.assertEquals;

public class TestPersonne {
//before each pour créer la table
    @BeforeEach
    public void BeforeEach() {
        Personne.createTable();
        Personne personne1 = new Personne("Durand", "Pierre");
        personne1.save();
        Personne personne = new Personne("Durand", "PierreA");
        personne.save();
    }

    @AfterEach
    public void AfterEach() {
        Personne.deleteTable();
    }

    @Test
    public void testSave() throws SQLException {
        //Personne personne = new Personne("Duransd", "Pierre");
        //personne.save();
        //assertEquals(3, personne.getMaxId());
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
        Personne personne = new Personne("Durand", "Pierre");
        personne.save();
        personne.delete();
        assertEquals(-1, personne.getId());
    }

    @Test
    public void testFind() throws SQLException {
        Personne personne = new Personne("Durand", "Pierre");
        personne.save();
        Personne personne2 = Personne.findById(7);
        assertEquals("Pierre", personne2.getPrenom());
        assertEquals("Durand", personne2.getNom());
    }

    @Test
    public void testFindAll() throws SQLException {
        Personne personne = new Personne("Durand", "Pierre");
        personne.save();
        List<Personne> p = Personne.findAll();
        assertEquals(29, p.size());
    }

    @Test
    public void testFindAllByNom() throws SQLException {
        Personne personne = new Personne("Durand", "Pierre");
        personne.save();
        List<Personne> p = Personne.findByName("Durand");
        assertEquals(22, p.size());
    }
}


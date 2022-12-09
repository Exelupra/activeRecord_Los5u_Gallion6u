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

    /**
     * creation de la table personne et ajout de 2 personnes
     */
    @BeforeEach
    public void BeforeEach() {
        Personne.createTable();
        personne1 = new Personne("Durand", "Pierre");
        personne1.save();
        personne2 = new Personne("Durand", "PierreA");
        personne2.save();
    }

    /**
     * Test pour la methode save quand la personne n'est pas enregistrée dans la base de données
     * @throws SQLException quand la requete ne fonctionne pas
     */
    @Test
    public void testSave() throws SQLException {
        List<Personne> personnes = Personne.findAll();
        assertEquals(2, personnes.size());
        Personne personne = new Personne("Durand", "Pierre");
        personne.save();
        personnes = Personne.findAll();
        assertEquals(3, personnes.size());
    }

    /**
     * Test pour la methode update
     */
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

    /**
     * Test pour la methode delete
     */
    @Test
    public void testDelete() {
        personne1.delete();
        assertEquals(-1, personne1.getId());
    }

    /**
     * Test pour la methode find by id
     * @throws SQLException quand la requete ne fonctionne pas
     */
    @Test
    public void testFindById() throws SQLException {
        Personne personne2 = Personne.findById(2);
        assertEquals("PierreA", personne2.getPrenom());
        assertEquals("Durand", personne2.getNom());
    }

    /**
     * Test pour la methode find all
     * @throws SQLException quand la requete ne fonctionne pas
     */
    @Test
    public void testFindAll() throws SQLException {
        ;
        List<Personne> p = Personne.findAll();
        assertEquals(2, p.size());
    }

    /**
     * Test pour la methode find by name
     * @throws SQLException quand la requete ne fonctionne pas
     */
    @Test
    public void testFindlByName() throws SQLException {
        List<Personne> p = Personne.findByName("Durand");
        assertEquals(2, p.size());
    }

    /**
     * After each pour supprimer la table
     */
    @AfterEach
    public void AfterEach() {
        Personne.deleteTable();
    }

}
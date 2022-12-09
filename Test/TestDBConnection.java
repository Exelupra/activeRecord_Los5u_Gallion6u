import activeRecord.DBConnection;
import org.junit.jupiter.api.Test;

import java.sql.Connection;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;

public class TestDBConnection {

    /**
     * Test de la methode getConnection pour vérifier qu'elle utlise bien le patron singleton (une seule instace)
     */
    @Test
    public void testConnection() {
        Connection dbConnection = DBConnection.getConnection();
        Connection dbConnection2 = DBConnection.getConnection();
        // on verifie que les deux connexions sont les mêmes
        assertSame(dbConnection, dbConnection2);
    }

    /**
     * Test de la methode setNonDB pour changer le nom de la base à laquelle on se connecte
     */
    @Test
    public void testNomDB() {
        Connection connection = DBConnection.getConnection();
        DBConnection dbConnection =  DBConnection.getDBConnection();
        assertEquals("testpersonne", dbConnection.getNomDB());
        dbConnection.setNomDB("test");
        assertEquals("test", dbConnection.getNomDB());
    }
}

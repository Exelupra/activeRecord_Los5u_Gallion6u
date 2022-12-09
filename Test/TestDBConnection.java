import activeRecord.DBConnection;
import org.junit.jupiter.api.Test;

import java.sql.Connection;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;

public class TestDBConnection {

    @Test
    public void testConnection() {
        Connection dbConnection = DBConnection.getConnection();
        Connection dbConnection2 = DBConnection.getConnection();
        assertSame(dbConnection, dbConnection2);
    }

    @Test
    public void testNomDB() {
        Connection connection = DBConnection.getConnection();
        DBConnection dbConnection =  DBConnection.getDBConnection();
        assertEquals("testpersonne", dbConnection.getNomDB());
        dbConnection.setNomDB("test");
        assertEquals("test", dbConnection.getNomDB());
    }
}

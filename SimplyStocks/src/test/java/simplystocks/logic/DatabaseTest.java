/*
 */
package simplystocks.logic;

import java.sql.Connection;
import java.sql.SQLException;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Fredrik Grönqvist <fredrik.groqvist+nb@gmail.com>
 */
public class DatabaseTest {
    
    public DatabaseTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    @Test
    public void testCreatingDatabase() throws SQLException {
        Database db = new Database();
    }
    
    @Test
    public void testCreatingTransactionTable() throws SQLException{
        Database db = new Database();
        db.createTransactionTable();
    }
    
    @Test
    public void testCreatingStocksTable() throws SQLException{
        Database db = new Database();
        db.createStocksTable();
    }
    
    @Test
    public void testCreateCurrencyTable() throws SQLException{
        Database db = new Database();
        db.createCurrencyTable();
    }
}

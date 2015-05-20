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
    public void testCreatingTransactionTable() throws SQLException{
        Database db = Database.getInstance();
        db.createTransactionTable();
    }
    
    @Test
    public void testCreatingStocksTable() throws SQLException{
        Database db = Database.getInstance();
        db.createStocksTable();
    }
    
    @Test
    public void testCreateCurrencyTable() throws SQLException{
        Database db = Database.getInstance();
        db.createCurrencyTable();
    }
    
    @Test
    public void testGetAmountOfStockOwned() throws Exception{
        Stock stock = new Stock("TestTicker", "TestName", "TestExchange");
        Database db = Database.getInstance();
        int result = db.getAmountOfStockOwned(stock);
        int expected = 0;
        assertEquals(expected, result);
    }
    
    @Test
    public void testGetStockAmountByBuyType() throws Exception{
        Stock stock = new Stock("TestTicker", "TestName", "TestExchange");
        Database db = Database.getInstance();
        int result = db.getStockAmountByType(stock, Transaction.TRANSACTION_TYPES.BUY);
        int expected = 0;
        assertEquals(expected, result);
    }
    
    @Test
    public void testTruncateTableTransaction(){
        
    }
}

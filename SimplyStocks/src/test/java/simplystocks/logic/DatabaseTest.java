/*
 */
package simplystocks.logic;

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
        Database db = Database.getInstance();
        db.truncateTransactionTable();
        
        TransactionBuy transaction = PortfolioTest.getTestTransactionBuy();
        db.addTransaction(transaction);
        
        int result = db.getAmountOfStockOwned(transaction.getStock());
        int expected = transaction.getStockAmount();
        assertEquals(expected, result);
    }
        
    @Test
    public void testTruncateTableTransaction() throws SQLException, Exception{
        TransactionBuy transaction = PortfolioTest.getTestTransactionBuy();        
        Database db = Database.getInstance();
        db.addTransaction(transaction);
        
        db.truncateTransactionTable();
        
        int result = db.getAmountOfStockOwned(transaction.getStock());        
        int expected = 0;
        assertEquals(expected, result);
    }
       
    @Test
    public void testGetStockAmountByBuyType() throws Exception{
        Database db = Database.getInstance();
        db.truncateTransactionTable();
        
        TransactionBuy transaction = PortfolioTest.getTestTransactionBuy();
        db.addTransaction(transaction);        
        
        int result = db.getStockAmountByType(transaction.getStock(), 
                TransactionBase.TRANSACTION_TYPES.BUY);
        int expected = transaction.getStockAmount();
        
        assertEquals(expected, result);
    }    
}

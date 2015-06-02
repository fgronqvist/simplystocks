/*
 */
package simplystocks.helpers;

import java.sql.SQLException;
import java.util.ArrayList;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import simplystocks.logic.Database;
import simplystocks.logic.Portfolio;
import simplystocks.logic.PortfolioTest;
import simplystocks.logic.Stock;
import simplystocks.logic.TransactionBuy;

/**
 *
 * @author Fredrik Grönqvist <fredrik.groqvist+nb@gmail.com>
 */
public class StockHandlerTest {
    
    StockHandler instance;
    
    public StockHandlerTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        instance = new StockHandler();
    }
    
    @After
    public void tearDown() {
    }

    @Test
    public void testGetStockByTicker() throws Exception {
        Stock stock = instance.getStockByTicker("TESTTICKER");
        assertEquals("TESTTICKER", stock.getTicker());
    }
    
    @Test
    public void testGetStockByTickerEmpty() throws Exception{
        Stock stock = instance.getStockByTicker("");
        assertEquals(null, stock.getTicker());
    }
     
    @Test
    public void testGetStockTickers() throws SQLException, Exception{
        Database db = Database.getInstance();
        db.truncateTransactionTable();
        TransactionBuy transaction = PortfolioTest.getTestTransactionBuy();
        Portfolio portfolio = new Portfolio(new GenericErrorHandler());
        portfolio.addTransaction(transaction);
        
        ArrayList<String> expected = new ArrayList<>();
        expected.add(transaction.getStock().getTicker());
        assertEquals(expected, instance.getStockTickers());
    }
    
}

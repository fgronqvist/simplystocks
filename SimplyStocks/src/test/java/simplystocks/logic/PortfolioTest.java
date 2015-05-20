/*
 */
package simplystocks.logic;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import simplystocks.helpers.ErrorHandler;
import simplystocks.helpers.GenericErrorHandler;

/**
 *
 * @author Fredrik Grönqvist <fredrik.groqvist+nb@gmail.com>
 */
public class PortfolioTest {
    
    Portfolio instance;
    
    public PortfolioTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        ErrorHandler errorHandler = new GenericErrorHandler();
        instance = new Portfolio(errorHandler);
    }
    
    @After
    public void tearDown() {
    }

    @Test
    public void testAddTransactionBuy() throws Exception {
        Stock stock = new Stock("TestTicker", "TestName", "TestExchange");
        
        TransactionBuy transaction = new TransactionBuy();
        transaction.setCurrency("EUR");
        transaction.setCurrencyAmount(
                new BigDecimal(500.554));
        transaction.setDate(new Date());
        transaction.setStock(stock);
        transaction.setStockAmount(100);
        transaction.setTransactionType(Transaction.TRANSACTION_TYPES.BUY);
        
        ErrorHandler result = instance.addTransaction(transaction);
        assertFalse(result.hasErrors());
    }
    
}

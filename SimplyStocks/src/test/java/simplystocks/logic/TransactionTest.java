/*
 */
package simplystocks.logic;

import java.math.BigDecimal;
import java.util.Date;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Test the transactions
 * @author Fredrik Grönqvist <fredrik.groqvist+nb@gmail.com>
 */
public class TransactionTest {
    
    public TransactionSell instanceSell;
    public TransactionBuy instanceBuy;
    
    public TransactionTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        instanceSell = new TransactionSell();
        instanceBuy = new TransactionBuy();
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test that a date can be added to the transaction.
     */
    @Test
    public void testAddDate(){
        Date now = new Date();
        instanceSell.setDate(now);
        assertEquals(now, instanceSell.getDate());
    }    
    
    /**
     * Test that a stock can be added to the transaction.
     */
    @Test
    public void testAddStock(){
        try {
            Stock stock = new Stock("TestTicker", "TestName", "TestExchange");            
            instanceSell.setStock(stock);
            assertEquals(stock, instanceSell.getStock());
        }
        catch (Exception e) {
            fail();
        }        
    }
    
    /**
     * Test a null value stock amount transaction.
     * @throws java.lang.Exception
     */
    @Test(expected = Exception.class)
    public void testNullValueAmount() throws Exception{
        instanceSell.setStockAmount(null);
    }
    
    /**
     * Test a zero value stock amount transaction.
     * @throws Exception 
     */
    @Test(expected = Exception.class)
    public void testZeroStockAmount() throws Exception{
        instanceSell.setStockAmount(0);
    }
    
    /**
     * Test a negative value stock amount transaction.
     * @throws java.lang.Exception
     */
    @Test(expected = Exception.class)
    public void testNegativeStockAmount() throws Exception{
        instanceSell.setStockAmount(-3);
    }
    
    /**
     * Test a null value for currencyAmount
     * @throws Exception
     */
    @Test(expected = Exception.class)
    public void testNullCurrencyAmount() throws Exception{
        instanceSell.setCurrencyAmount(null);
    }
    
    /**
     * Test a zero value for currencyAmount
     * @throws Exception
     */
    @Test(expected = Exception.class)
    public void testZeroCurrencyAmount() throws Exception{
        instanceSell.setCurrencyAmount(BigDecimal.ZERO);
    }
    
    /**
     * Test a negative amount for currencyAmount
     * @throws Exception 
     */
    @Test(expected = Exception.class)
    public void testNegativeCurrencyAmount() throws Exception{
        BigDecimal neg = new BigDecimal(-3);
        instanceSell.setCurrencyAmount(neg);
    }
}

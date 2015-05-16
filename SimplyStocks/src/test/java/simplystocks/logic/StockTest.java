/*
 */
package simplystocks.logic;

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
public class StockTest {

    private Stock instance;

    public StockTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
        instance = new Stock();
    }

    @After
    public void tearDown() {
    }

    /**
     * Test that an exception is thrown with all empty args.
     * @throws java.lang.Exception
     */
    @Test(expected = Exception.class)
    public void testStockEmptyArgs() throws Exception {
        String ticker = "";
        String name = "";
        String exchange = "";
        Stock linstance = new Stock(ticker, name, exchange);
    }

    /**
     * Test that an exception is thrown with an empty ticker.
     * @throws java.lang.Exception
     */
    @Test(expected = Exception.class)
    public void testStockEmptyTicker() throws Exception {
        String ticker = "";
        String name = "Stock one";
        String exchange = "Nasdaq OMX Helsinki";
        Stock linstance = new Stock(ticker, name, exchange);
    }

    /**
     * Test that an exception is thrown with an empty name.
     * @throws java.lang.Exception
     */
    @Test(expected = Exception.class)
    public void testStockEmptyName() throws Exception {
        String ticker = "KESAV";
        String name = "";
        String exchange = "Nasdaq OMX Helsinki";
        Stock linstance = new Stock(ticker, name, exchange);
    }

    /**
     * Test that an exception is thrown with an empty exchange.
     * @throws java.lang.Exception
     */
    @Test(expected = Exception.class)
    public void testStockEmptyExchange() throws Exception {
        String ticker = "KESAV";
        String name = "Kesko A";
        String exchange = "";
        Stock linstance = new Stock(ticker, name, exchange);
    }
    
    /**
     * Test that ticker is an empty string on initialization.
     */
    @Test
    public void testGetTicker() {
        String res = instance.getTicker();
        String expRes = null;
        assertEquals(res, expRes);
    }

    /**
     * Test that ticker cannot be an empty string.
     * @throws java.lang.Exception
     */
    @Test(expected=Exception.class)
    public void testSetTickerEmptyString() throws Exception {
        String ticker = "";
        instance.setTicker(ticker);
    }
    
    /**
     * Test that ticker cannot be an null value.
     * @throws java.lang.Exception
     */
    @Test(expected=Exception.class)
    public void testSetTickerNullValue() throws Exception {
        String ticker = null;
        instance.setTicker(ticker);
    }
    

    /**
     * Test that name is an null value on initialization.
     */
    @Test
    public void testGetName() {
        String expResult = null;
        String result = instance.getName();
        assertEquals(expResult, result);
    }

    /**
     * Test that setName cannot be an empty string.
     * @throws java.lang.Exception
     */
    @Test(expected=Exception.class)
    public void testSetNameEmptyValue() throws Exception {
        String name = "";
        instance.setName(name);
    }
    
    /**
     * Test that setName cannot be a null value.
     * @throws java.lang.Exception
     */
    @Test(expected=Exception.class)
    public void testSetNameNullValue() throws Exception {
        String name = null;
        instance.setName(name);
    }   
    
    /**
     * Test that getExchange is a null value on initialization.
     */
    @Test
    public void testGetExchange() {
        String expResult = null;
        String result = instance.getExchange();
        assertEquals(expResult, result);
    }

    /**
     * Test that setExchange cannot be an empty string.
     * @throws java.lang.Exception
     */
    @Test(expected=Exception.class)
    public void testSetExchangeEmptyValue() throws Exception {
        String exchange = "";
        instance.setExchange(exchange);
    }

    /**
     * Test that setExchange cannot be a null value.
     * @throws java.lang.Exception
     */
    @Test(expected=Exception.class)
    public void testSetExchangeNullValue() throws Exception {
        String exchange = null;
        instance.setExchange(exchange);
    }
}

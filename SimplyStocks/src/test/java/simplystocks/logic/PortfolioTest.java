/*
 */
package simplystocks.logic;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.Date;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
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
    
    public void setupTestData() throws SQLException{        
        Database.getInstance().truncateTransactionTable();
        Database.getInstance().addTestTransactionData();
    }
    
    public static TransactionBuy getTestTransactionBuy() throws Exception{
        TransactionBuy transaction = new TransactionBuy();
        transaction = (TransactionBuy) setTestTransactionData(transaction);
        return transaction;
    }
    
    public static TransactionSell getTestTransactionSell() throws Exception {
        TransactionSell transaction = new TransactionSell();
        transaction = (TransactionSell) setTestTransactionData(transaction);
        return transaction;
    }
    
    public static TransactionBase setTestTransactionData(
            TransactionBase transaction) throws Exception {
        Stock stock = new Stock("TestTicker", "TestName", "TestExchange");                            
        transaction.setCurrency("EUR");
        transaction.setCurrencyAmount(
                new BigDecimal(500.554));
        transaction.setDate(new Date());
        transaction.setStock(stock);
        transaction.setStockAmount(100);      
        return transaction;
    }
    
    @Test
    public void testAddTransactionBuy() throws Exception {
        TransactionBuy transaction = getTestTransactionBuy();
        ErrorHandler result = instance.addTransaction(transaction);
        assertFalse(result.hasErrors());
    }    
    
    @Test
    public void testAddTransactionSell() throws Exception{
        TransactionSell transaction = getTestTransactionSell();
        ErrorHandler result = instance.addTransaction(transaction);
        assertFalse(result.hasErrors());
    }
    
    @Test(expected = AssertionError.class)
    public void testEmptyTransactionType() throws Exception {
        TransactionEmptyType transaction = new TransactionEmptyType();
        transaction = (TransactionEmptyType) setTestTransactionData(transaction);
        ErrorHandler result = instance.addTransaction(transaction);
    }
    
    @Test
    public void testGetErrorHandler() {
        ErrorHandler errorHandler = instance.getErrorHandler();
        assertTrue(errorHandler instanceof ErrorHandler);
    }
    
    @Test
    public void testCheckTransactionSell() throws Exception{
        TransactionSell transaction = getTestTransactionSell();
        transaction.setStockAmount(5000);
        boolean result = instance.checkTransactionSell(transaction);
        assertFalse(result);
    }
    
    @Test
    public void testCheckTransactionSellError() throws Exception{
        TransactionSell transaction = getTestTransactionSell();
        transaction.setStockAmount(5000);
        boolean result = instance.checkTransactionSell(transaction);
        ErrorHandler errorHandler = instance.getErrorHandler();
        assertTrue(errorHandler.hasErrors());
    }
    
    @Test
    public void testMainFormTableData() throws SQLException{
        setupTestData();
        TableModel model = new DefaultTableModel();        
        DefaultTableModel model2 = 
                (DefaultTableModel) instance.setMainFormTableData(
                        (DefaultTableModel) model
                );
        
        assertTrue(model2.getRowCount() > 0);
    }
    
    @Test
    public void testPortfolioPurchasePrice() throws SQLException{
        setupTestData();        
        int res = instance.getPortfolioPurchasePrice();        
        assertTrue(res > 0);
    }
        
    @Test
    public void testPortfolioStockAmount() throws SQLException{
        setupTestData();        
        int res = instance.getPortfolioStockAmount();        
        assertTrue(res > 0);
    }
    
    @Test
    public void testPortfolioStockCount() throws SQLException{
        setupTestData();        
        int res = instance.getPortfolioStockCount();        
        assertTrue(res > 0);
    }    
}

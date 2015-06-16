/*
 */
package simplystocks.helpers;

import java.sql.SQLException;
import java.util.ArrayList;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
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
    
    public Stock getStockByTickerData() throws Exception{
        Database db = Database.getInstance();
        db.truncateTransactionTable();
        db.addTestTransactionData();
        Stock result = instance.getStockByTicker("TESTTICKER");
        return result;
    }
    
    @Test
    public void testGetStockByTickersDataTicker() throws SQLException, Exception{        
        assertEquals("TESTTICKER", getStockByTickerData().getTicker());
    }
    
    @Test
    public void testGetStockByTickersDataName() throws SQLException, Exception{        
        assertEquals("Test Inc", getStockByTickerData().getName());
    }

    @Test
    public void testGetStockByTickersDataExchange() throws SQLException, Exception{        
        assertEquals("NYSEARCA", getStockByTickerData().getExchange());
    }
    
    @Test
    public void testGetInstance(){
        StockHandler st = StockHandler.getInstance();
        StockHandler stb = StockHandler.getInstance();        
        assertSame(st, stb);
    }
    
    @Test
    public void testGetStockPrice() throws Exception{
        Stock stock = instance.getStockByTicker("TESTTICKER");
        stock.setExchange("NYSEARCA");
        stock.setTicker("SDIV");
        
        JSONObject obj = instance.getStockPrice(stock);
        assertTrue(obj.containsKey("t"));
    }
    
    @Test
    public void testUpdateStockPrices() throws SQLException{
        Database db = Database.getInstance();
        db.addTestTransactionData();
        boolean result = instance.updateStockPrices();
        assertTrue(result);
    }
        
    public String getJsonString(){
        String str = "[\n{\n"
                + "\"id\": \"3034647\"\n"
                + ",\"t\" : \"SDIV\"\n"
                + ",\"e\" : \"NYSEARCA\"\n"
                + ",\"l\" : \"22.51\"\n"
                + ",\"l_fix\" : \"22.51\"\n"
                + ",\"l_cur\" : \"22.51\"\n"
                + ",\"s\": \"0\"\n"
                + ",\"ltt\":\"4:00PM EDT\"\n"
                + ",\"lt\" : \"Jun 15, 4:00PM EDT\"\n"
                + ",\"lt_dts\" : \"2015-06-15T16:00:00Z\"\n"
                + ",\"c\" : \"-0.11\"\n"
                + ",\"c_fix\" : \"-0.11\"\n"
                + ",\"cp\" : \"-0.49\"\n"
                + ",\"cp_fix\" : \"-0.49\"\n"
                + ",\"ccol\" : \"chr\"\n"
                + ",\"pcls_fix\" : \"22.62\"\n"
                + "}\n"
                + "]";
        return str;                
    }
    
    public JSONObject getJsonObject(String JsonString) throws ParseException {
        JSONParser jsonParser = new JSONParser();
        Object obj = jsonParser.parse(JsonString); 
        JSONArray array = (JSONArray) obj; 
        JSONObject obj2 = (JSONObject) array.get(0);
        return obj2;
    }
    
    @Test
    public void testInsertStockDataTEmpty() throws ParseException, SQLException{
        String json = getJsonString();        
        json = json.replaceFirst("\"t\" : \"SDIV\"", "\"t\" : \"\"");
        JSONObject jsonObj = getJsonObject(json);
        boolean result = instance.insertStockData(jsonObj);
        assertFalse(result);
    }
    
    @Test
    public void testInsertStockDataLEmpty() throws ParseException, SQLException{
        String json = getJsonString();
        json = json.replaceFirst("\"l\" : \"22.51\"", "\"l\" : \"\"");
        JSONObject jsonObj = getJsonObject(json);
        boolean result = instance.insertStockData(jsonObj);
        assertFalse(result);
    }
    
    @Test
    public void testInsertStockDataEEmpty() throws ParseException, SQLException{
        String json = getJsonString();
        json = json.replaceFirst("\"e\" : \"NYSEARCA\"", "\"e\" : \"\"");
        JSONObject jsonObj = getJsonObject(json);
        boolean result = instance.insertStockData(jsonObj);
        assertFalse(result);
    }
    
    @Test
    public void testInsertStockDataOk() throws ParseException, SQLException{
        String json = getJsonString();
        JSONObject jsonObj = getJsonObject(json);
        boolean result = instance.insertStockData(jsonObj);
        assertTrue(result);
    }
    
}

/*
 */
package simplystocks.helpers;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.TimeUnit;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import simplystocks.logic.Database;
import simplystocks.logic.Stock;

/**
 * The class works as a simple translator between the database and the GUI for
 * the stocks.
 *
 * This helps de-couple the database from the GUI, as this class provides simple
 * helper methods to generate the kind of objects the GUI might need.
 *
 * @author Fredrik Grönqvist <fredrik.groqvist+nb@gmail.com>
 */
public class StockHandler {

    private static StockHandler instance = null;

    protected StockHandler() {

    }

    /**
     * A singleton implementation.
     *
     * @return this
     */
    public static StockHandler getInstance() {
        if (instance == null) {
            instance = new StockHandler();
        }
        return instance;
    }

    /**
     * Returns a list of stock tickers.
     *
     * This is usually used by the GUI to display ticker lists.
     *
     * @return an ArrayList of ticker strings
     * @throws SQLException
     */
    public ArrayList<String> getStockTickers() throws SQLException {
        Database db = Database.getInstance();
        ResultSet result = db.getStockTickers();
        ArrayList<String> sResult = new ArrayList<>();
        while (result.next()) {
            sResult.add(result.getString("stock_ticker"));
        }
        return sResult;
    }

    /**
     * Returns a stock by its ticker.
     *
     * @param ticker of the stock to be retrieved
     * @return Stock by the ticker
     * @throws SQLException
     * @throws Exception
     */
    public Stock getStockByTicker(String ticker) throws SQLException, Exception {
        Database db = Database.getInstance();
        ResultSet result = db.getStockByTicker(ticker);
        Stock stock = new Stock();
        while (result.next()) {
            stock.setExchange(result.getString("exchange"));
            stock.setName(result.getString("name"));
            stock.setTicker(result.getString("ticker"));
        }
        return stock;
    }

    /**
     * Updates the stock prices from the internet.
     *
     * @return
     * @throws java.sql.SQLException
     */
    public boolean updateStockPrices() throws SQLException {
        ArrayList<String> stocks = getStockTickers();
        String errorMsg = null;
        for (String ticker : stocks) {
            try {
                Stock stock = getStockByTicker(ticker);
                if(checkUpdateIntervall(stock)){
                    JSONObject priceData = getStockPrice(stock);
                    // Test if priceData is null
                    insertStockData(priceData);
                }
            }
            catch (Exception e) {
                errorMsg = "An unexpected error occured!\n" + e.getMessage();
            }
        }
        return true;
    }

    /**
     * Returns an object with the stock price details.
     * 
     * The keys are:
     * l = current price
     * t = ticker
     * e = exchange
     * lt_dts = GMT timestamp for the price
     * 
     * @param stock
     * @return an JSONObject with the data
     * @throws ParseException 
     */
    public JSONObject getStockPrice(Stock stock) throws 
            ParseException, MalformedURLException, IOException {
        String u = "http://www.google.com/finance/info?q="+stock.getTicker()
                + "&e="+stock.getExchange();
        
        URL site = new URL(u);
        BufferedReader in = new BufferedReader(
        new InputStreamReader(site.openStream()));

        String inputLine;
        String content = "";
        while ((inputLine = in.readLine()) != null)
            content += inputLine;
        in.close();
        
        // Remove "//" from the beginning
        content = content.substring(3, content.length());
        
        JSONParser jsonParser = new JSONParser();
        
        // parse returns a generic Object!?!        
        Object obj = jsonParser.parse(content); 
        
        // Object is converted to an JSONArray
        JSONArray array = (JSONArray) obj; 
        
        //All the data is in the first "cell" of the array
        JSONObject obj2 = (JSONObject) array.get(0);
        
        return obj2;
    }
    
    /**
     * Checks if the stock ticker price has been updated in the last 30 minutes.
     * 
     * The function will return false (!) if the stock price has been updated in
     * the last 30 minutes. This should be thought of as "does the stock price
     * need updating". The intervall is so the Finance API won't block the
     * connection.
     * 
     * @param stock
     * @return true if the last update was over 30 min ago. False otherwise.
     * @throws SQLException 
     */
    public boolean checkUpdateIntervall(Stock stock) throws SQLException, 
            java.text.ParseException{
        Database db = Database.getInstance();
        ResultSet res = db.getLatestHistoryInput(stock);
        long MAX_INTERVALL = TimeUnit.MILLISECONDS.convert(30, TimeUnit.MINUTES);        
        Date updateDate = null;
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Date now = new Date();
        while(res.next()){
            String tmp = res.getString("maxtime");
            if(tmp != null){
                updateDate = dateFormat.parse(tmp);            
            }
        }
        if(updateDate == null){
            return true;
        }
        
        long dateDiff = now.getTime() - updateDate.getTime();
        return dateDiff >= MAX_INTERVALL;
    }
    
    /**
     * Add stock price history to the database.
     * 
     * @param tickerData
     * @return false if tickerData does not contain ticker, lastprice or exchange
     * @throws SQLException 
     */
    public boolean insertStockData(JSONObject tickerData) throws SQLException{
        if(tickerData.get("t").equals("") || tickerData.get("t") == null) {
            return false;
        }
        if(tickerData.get("l").equals("") || tickerData.get("l") == null) {
            return false;
        }
        if(tickerData.get("e").equals("") || tickerData.get("e") == null) {
            return false;
        }
        
        Database db = Database.getInstance();
        db.addStockHistory((String)tickerData.get("t"), 
                (String)tickerData.get("e"), (String)tickerData.get("l"));
        return true;
    }
}

/*
 */
package simplystocks.helpers;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import simplystocks.logic.Database;
import simplystocks.logic.Stock;

/**
 * The class works as a simple translator between the database and the GUI
 * for the stocks.
 * 
 * This helps de-couple the database from the GUI, as this class provides
 * simple helper methods to generate the kind of objects the GUI might need.
 * 
 * @author Fredrik Grönqvist <fredrik.groqvist+nb@gmail.com>
 */
public class StockHandler {

    private static StockHandler instance = null;
    
    protected StockHandler() {
        
    }
    
    public static StockHandler getInstance(){
        if(instance == null){
            instance = new StockHandler();
        }
        return instance;
    }
    
    public ArrayList<String> getStockTickers() throws SQLException{
        Database db = Database.getInstance();
        ResultSet result = db.getStockTickers();
        ArrayList<String> sResult = new ArrayList<>();
        while(result.next()){
            sResult.add(result.getString("stock_ticker"));    
        }
        return sResult;
    } 
    
    public Stock getStockByTicker(String ticker) throws SQLException, Exception{
        Database db = Database.getInstance();
        ResultSet result = db.getStockByTicker(ticker);
        Stock stock = new Stock();
        while(result.next()){
            stock.setExchange(result.getString("exchange"));
            stock.setName(result.getString("name"));
            stock.setTicker(result.getString("ticker"));
        }
        return stock;
    }
}

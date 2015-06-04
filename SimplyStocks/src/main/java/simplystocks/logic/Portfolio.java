/*
 */
package simplystocks.logic;

import java.sql.SQLException;
import javax.swing.table.TableModel;
import simplystocks.helpers.ErrorHandler;

/**
 * The class handles all methods relating to the portfolio.
 * 
 * The class can be regarded as a simple controller.
 * 
 * @author Fredrik Grönqvist <fredrik.groqvist+nb@gmail.com>
 */
public class Portfolio {

    ErrorHandler errorHandler = null;
    
    public Portfolio(ErrorHandler errorHandler){
        this.errorHandler = errorHandler;
    }
    
    public ErrorHandler getErrorHandler(){
        return this.errorHandler;
    }
    
    public ErrorHandler addTransaction(Transaction transaction) throws SQLException, Exception {
        boolean result;
                
        switch(transaction.getType()) {
            case BUY:
                result = true;
                break;
            case SELL:
                result = checkTransactionSell((TransactionSell) transaction);
                break;
            default:
                throw new AssertionError(transaction.getType().name());            
        }
        
        if(result){
            Database db = Database.getInstance();
            db.addTransaction(transaction);
            db.addStock(transaction.getStock());
        }
        
        return errorHandler;
    }
    
    public void setMainFormTableData(TableModel tableModel){
        //ResultSet res = Database.getInstance().
        
    }
        
    public boolean checkTransactionSell(TransactionSell transaction) throws Exception {
        Database db = Database.getInstance();
        int stocksOwned = db.getAmountOfStockOwned(transaction.getStock());
        if(stocksOwned < transaction.getStockAmount()){
            errorHandler.addError(ErrorHandler.ERROR_CODES.VALIDATION_FAILED, 
                    "Not enough stocks to sell");
            return false;
        }
        return true;
    }
}

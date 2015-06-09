/*
 */
package simplystocks.logic;

import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.table.DefaultTableModel;
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

    public Portfolio(ErrorHandler errorHandler) {
        this.errorHandler = errorHandler;
    }

    public ErrorHandler getErrorHandler() {
        return this.errorHandler;
    }

    public ErrorHandler addTransaction(Transaction transaction) throws SQLException, Exception {
        boolean result;

        switch (transaction.getType()) {
            case BUY:
                result = true;
                break;
            case SELL:
                result = checkTransactionSell((TransactionSell) transaction);
                break;
            default:
                throw new AssertionError(transaction.getType().name());
        }

        if (result) {
            Database db = Database.getInstance();
            db.addTransaction(transaction);
            db.addStock(transaction.getStock());
        }

        return errorHandler;
    }

    public TableModel setMainFormTableData(DefaultTableModel tableModel) throws SQLException {
        ResultSet res = Database.getInstance().getMainTableTransactionData();
        while (res.next()) {
            if (res.getInt("stock_count") > 0) {
                tableModel.addRow(new Object[]{
                    res.getString("stock_ticker"),
                    res.getInt("stock_count"),
                    res.getDouble("currency_sum"),
                    0,
                    res.getInt("transaction_count")
                });
            }
        }
        return tableModel;
    }

    public int getPortfolioPurchasePrice() throws SQLException {
        ResultSet res = Database.getInstance().getPortfolioPurchacePrice();
        int sum = 0;
        while (res.next()) {
            sum = res.getInt("currency_sum");
        }
        return sum;
    }

    public int getPortfolioCurrentValue() throws SQLException {
        ResultSet res = Database.getInstance().getPortfolioCurrentValue();
        int sum = 0;
        while (res.next()) {
            sum = res.getInt("current_value");
        }
        return sum;
    }

    public int getPortfolioStockAmount() throws SQLException {
        ResultSet res = Database.getInstance().getPortfolioStockAmount();
        int sum = 0;
        while (res.next()) {
            sum = res.getInt("stock_amount");
        }
        return sum;
    }
    
    public int getPortfolioStockCount() throws SQLException{
        ResultSet res = Database.getInstance().getMainTableTransactionData();
        int stockCount = 0;
        while(res.next()){
            if(res.getInt("stock_count") > 0){
                stockCount++;
            }
        }
        return stockCount;
    }

    public boolean checkTransactionSell(TransactionSell transaction) throws Exception {
        Database db = Database.getInstance();
        int stocksOwned = db.getAmountOfStockOwned(transaction.getStock());
        if (stocksOwned < transaction.getStockAmount()) {
            errorHandler.addError(ErrorHandler.ERROR_CODES.VALIDATION_FAILED,
                    "Not enough stocks to sell");
            return false;
        }
        return true;
    }
}

/*
 */
package simplystocks.logic;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * The class handles most of the database functions.
 *
 * @author Fredrik Grönqvist <fredrik.groqvist+nb@gmail.com>
 */
public class Database {
    /**
     * Singleton instance of this class.
     */
    private static Database instance = null;    
    
    /**
     * Connection instance.
     */
    private Connection connection;
    
    protected Database() throws SQLException{
        openConnection();
    }
    
    /**
     * Singleton getter.
     * @return The Database singleton object
     * @throws java.sql.SQLException if the connection fails
     */
    public static Database getInstance() throws SQLException{
        if(instance == null){
            instance = new Database();
        }
        return instance;
    }
    
    private void openConnection() throws SQLException{
        if (connection == null) {
            connection = DriverManager.getConnection("jdbc:sqlite:simplystocks.db");
        }
    }
        
    /**
     * 
     * @throws SQLException 
     */
    public void truncateTransactionTable() throws SQLException{
        String sql = "DELETE FROM [transaction]";
        Statement stmt = connection.createStatement();
        stmt.execute(sql);         
    }
        
    public void createTransactionTable() throws SQLException{
        String sql = "CREATE TABLE IF NOT EXISTS 'transaction' "+
                "('id' INTEGER PRIMARY KEY NOT NULL, "+
                "'transaction_date' TEXT NOT NULL, "+
                "'transaction_type' TEXT NOT NULL, "+
                "'stock_ticker' TEXT NOT NULL, "+
                "'stock_amount' INTEGER NOT NULL, "+
                "'currency' TEXT NOT NULL, "+
                "'currency_amount' INTEGER NOT NULL)";
        
        
        Statement stmt = connection.createStatement();
        stmt.executeUpdate(sql);
        stmt.close();
    }
    
    public void createStockTable() throws SQLException {
        String sql = "CREATE TABLE IF NOT EXISTS 'stock' "+
                "('ticker' TEXT NOT NULL, "+
                "'name' TEXT NOT NULL, "+
                "'exchange' TEXT NOT NULL)";
        
        Statement stmt = connection.createStatement();
        stmt.executeUpdate(sql);
        stmt.close();
    }
    
    public void createCurrencyTable() throws SQLException{
        String sql = "CREATE TABLE IF NOT EXISTS 'currency' "+
                "('symbol' TEXT NOT NULL, "+
                "'name' TEXT NOT NULL)";
        
        Statement stmt = connection.createStatement();
        stmt.executeUpdate(sql);
        stmt.close();
    }
    
    public boolean addTransaction(Transaction transaction) throws SQLException {
        String sql = "INSERT INTO 'transaction' ("+
                "'transaction_date', "+
                "'transaction_type', "+
                "'stock_ticker', "+
                "'stock_amount', "+
                "'currency', "+
                "'currency_amount' "+
                ") VALUES (?,?,?,?,?,?)";        
        
        PreparedStatement stmt = connection.prepareStatement(sql);
        stmt.setString(1, new SimpleDateFormat("yyyy-MM-dd").format(transaction.getDate()));
        stmt.setString(2, String.valueOf(transaction.getType()));
        stmt.setString(3, transaction.getStock().getTicker());
        stmt.setInt(4, transaction.getStockAmount());
        stmt.setString(5, transaction.getCurrency());
        BigDecimal currencyAmount = 
                transaction.getCurrencyAmount().setScale(2, BigDecimal.ROUND_HALF_UP);
        stmt.setDouble(6, currencyAmount.doubleValue());
        return stmt.execute();        
    }
    
    public boolean addStock(Stock stock){
        boolean retValue = false;
        
        try {
            ResultSet res = this.getStockByTicker(stock.getTicker());
            if(!res.next()){
                String sql = "INSERT INTO 'stock' "+
                "('ticker','name','exchange') VALUES (?,?,?)";
                PreparedStatement stmt = connection.prepareStatement(sql);
                stmt.setString(1, stock.getTicker().toUpperCase());
                stmt.setString(2, stock.getName());
                stmt.setString(3, stock.getExchange());
                retValue = stmt.execute();
            }
        }
        catch (SQLException ex) {
            // No exception throwing for now
        }
        return retValue;
    }
        
    public int getAmountOfStockOwned(Stock stock) throws SQLException, Exception{
        int buyCount = this.getStockAmountByType(stock, 
                TransactionBase.TRANSACTION_TYPES.BUY);
        int sellCount = this.getStockAmountByType(stock, 
                TransactionBase.TRANSACTION_TYPES.SELL);
        if(buyCount - sellCount < 0){
            throw new Exception("stock amount less than zero?");
        }
        return (buyCount - sellCount);
    }
    
    public int getStockAmountByType(Stock stock,
            TransactionBase.TRANSACTION_TYPES type) throws SQLException {
        // Get the amount of stock we have bought.
        String sql = "SELECT SUM ([stock_amount]) as stock_amount "
                + "FROM [transaction] "
                + "WHERE [stock_ticker] = ? AND [transaction_type] = ?";

        PreparedStatement stmt = connection.prepareStatement(sql);
        stmt.setString(1, stock.getTicker());
        stmt.setString(2, type.toString());
        ResultSet result = stmt.executeQuery();
        int amount = 0;
        while(result.next()){
            amount = result.getInt("stock_amount");        
        }
        return amount;
    }
    
    public ResultSet getStockTickers() throws SQLException{
        String sql = "SELECT distinct stock_ticker FROM [transaction]";
        PreparedStatement stmt = connection.prepareStatement(sql);
        ResultSet result = stmt.executeQuery();
        return result;
    }
    
    public ResultSet getStockByTicker(String ticker) throws SQLException{
        String sql = "SELECT * FROM stock WHERE ticker = ?";
        PreparedStatement stmt = connection.prepareStatement(sql);
        stmt.setString(1, ticker);
        ResultSet result = stmt.executeQuery();
        return result;
    }
    
    /*
    public ResultSet getMainTableTransactionData(){
        
    }
    */
}

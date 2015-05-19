/*
 */
package simplystocks.logic;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * The class handles most of the database functions.
 *
 * @author Fredrik Grönqvist <fredrik.groqvist+nb@gmail.com>
 */
public class Database {
    private Connection connection;
    
    public Database() throws SQLException{
        openConnection();
    }
    
    private void openConnection() throws SQLException{
        if (connection == null) {
            connection = DriverManager.getConnection("jdbc:sqlite:simplystocks.db");
        }
    }
    
    private void closeConnection() throws SQLException{
        connection.close();
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
    
    public void createStocksTable() throws SQLException {
        String sql = "CREATE TABLE IF NOT EXISTS 'stocks' "+
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
        stmt.setInt(6, transaction.getCurrencyAmount().intValueExact());
        return stmt.execute();        
    }
}

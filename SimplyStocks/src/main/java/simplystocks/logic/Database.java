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

    protected Database() throws SQLException {
        openConnection();
    }

    /**
     * Singleton getter.
     *
     * @return The Database singleton object
     * @throws java.sql.SQLException if the connection fails
     */
    public static Database getInstance() throws SQLException {
        if (instance == null) {
            instance = new Database();
        }
        return instance;
    }

    private void openConnection() throws SQLException {
        if (connection == null) {
            connection = DriverManager.getConnection("jdbc:sqlite:simplystocks.db");
        }
    }

    /**
     * Empties the transaction table.
     * 
     * This is usually called by unit-tests.
     * @throws SQLException
     */
    public void truncateTransactionTable() throws SQLException {
        String sql = "DELETE FROM 'transaction'";
        Statement stmt = connection.createStatement();
        stmt.execute(sql);
    }
    
    /**
     * Adds data to the database for testruns.
     * @throws SQLException 
     */
    public void addTestTransactionData() throws SQLException {
        String sql = "INSERT INTO 'transaction' "
                + "('transaction_date', "
                + "'transaction_type', "
                + "'stock_ticker', "
                + "'stock_amount', "
                + "'currency', "
                + "'currency_amount') VALUES "
                + "('2015-06-01', 'BUY', 'TESTTICKER', 101, 'EUR', 501),"
                + "('2015-06-02', 'BUY', 'TESTTICKER', 102, 'EUR', 502),"
                + "('2015-06-03', 'BUY', 'TESTTICKER', 103, 'EUR', 503),"
                + "('2015-06-03', 'SELL', 'TESTTICKER', 5, 'EUR', 5),"
                + "('2015-06-04', 'SELL', 'TESTTICKER', 5, 'EUR', 10),"
                + "('2015-06-05', 'BUY', 'TICKER2', 50, 'EUR', 10),"
                + "('2015-06-06', 'BUY', 'TICKER2', 25, 'EUR', 20)"
                ;
        Statement stmt = connection.createStatement();
        stmt.executeUpdate(sql);
        stmt.close();
    }

    /**
     * Creates the transaction table.
     * 
     * Usually only called if a completely new database has to be created.
     * @throws SQLException 
     */
    public void createTransactionTable() throws SQLException {
        String sql = "CREATE TABLE IF NOT EXISTS 'transaction' "
                + "('id' INTEGER PRIMARY KEY NOT NULL, "
                + "'transaction_date' TEXT NOT NULL, "
                + "'transaction_type' TEXT NOT NULL, "
                + "'stock_ticker' TEXT NOT NULL, "
                + "'stock_amount' INTEGER NOT NULL, "
                + "'currency' TEXT NOT NULL, "
                + "'currency_amount' INTEGER NOT NULL)";

        Statement stmt = connection.createStatement();
        stmt.executeUpdate(sql);
        stmt.close();
    }

    /**
     * Crate the stock table.
     * 
     * Usually only called if a completely new database has to be created.
     * @throws SQLException 
     */
    public void createStockTable() throws SQLException {
        String sql = "CREATE TABLE IF NOT EXISTS 'stock' "
                + "('ticker' TEXT NOT NULL, "
                + "'name' TEXT NOT NULL, "
                + "'exchange' TEXT NOT NULL)";

        Statement stmt = connection.createStatement();
        stmt.executeUpdate(sql);
        stmt.close();
    }

    /**
     * Create the currency table.
     * 
     * Usually only called if a completely new database has to be created.
     * @throws SQLException 
     */
    public void createCurrencyTable() throws SQLException {
        String sql = "CREATE TABLE IF NOT EXISTS 'currency' "
                + "('symbol' TEXT NOT NULL, "
                + "'name' TEXT NOT NULL)";

        Statement stmt = connection.createStatement();
        stmt.executeUpdate(sql);
        stmt.close();
    }

    /**
     * Add a new transaction to the database.
     * 
     * @param transaction The transaction to be added
     * @return true if adding was successful, otherwise false.
     * @throws SQLException 
     */
    public boolean addTransaction(Transaction transaction) throws SQLException {
        String sql = "INSERT INTO 'transaction' ("
                + "'transaction_date', "
                + "'transaction_type', "
                + "'stock_ticker', "
                + "'stock_amount', "
                + "'currency', "
                + "'currency_amount' "
                + ") VALUES (?,?,?,?,?,?)";

        PreparedStatement stmt = connection.prepareStatement(sql);
        stmt.setString(1, new SimpleDateFormat("yyyy-MM-dd").format(transaction.getDate()));
        stmt.setString(2, String.valueOf(transaction.getType()));
        stmt.setString(3, transaction.getStock().getTicker());
        stmt.setInt(4, transaction.getStockAmount());
        stmt.setString(5, transaction.getCurrency());
        BigDecimal currencyAmount
                = transaction.getCurrencyAmount().setScale(2, BigDecimal.ROUND_HALF_UP);
        stmt.setDouble(6, currencyAmount.doubleValue());
        return stmt.execute();
    }

    /**
     * Adds the stock info to the database.
     * 
     * The stock info is only added if the stock isn't already present.
     * This is checked by looking up the stock ticker. Returns false if
     * the stock data wasn't added, regardless of the cause.
     * 
     * @param stock
     * @return true if the adding was successful, false otherwise.
     */
    public boolean addStock(Stock stock) {
        boolean retValue = false;

        try {
            ResultSet res = this.getStockByTicker(stock.getTicker());
            if (!res.next()) {
                String sql = "INSERT INTO 'stock' "
                        + "('ticker','name','exchange') VALUES (?,?,?)";
                PreparedStatement stmt = connection.prepareStatement(sql);
                stmt.setString(1, stock.getTicker().toUpperCase());
                stmt.setString(2, stock.getName());
                stmt.setString(3, stock.getExchange());
                retValue = stmt.execute();
            }
        }
        catch (SQLException ex) {
            return false;
        }
        return retValue;
    }

    /**
     * Returns the amount of stock currently owned.
     * 
     * This is usually used to check that the amount of stock that is being
     * sold is actually available.
     * 
     * @param stock
     * @return the stock amount. Note, may be a negative integer!
     * @throws SQLException
     * @throws Exception 
     */
    public int getAmountOfStockOwned(Stock stock) throws SQLException, Exception {
        int buyCount = this.getStockAmountByType(stock,
                TransactionBase.TRANSACTION_TYPES.BUY);
        int sellCount = this.getStockAmountByType(stock,
                TransactionBase.TRANSACTION_TYPES.SELL);
        if (buyCount - sellCount < 0) {
            throw new Exception("stock amount less than zero?");
        }
        return (buyCount - sellCount);
    }

    /**
     * Returns the amount of stock for a specific ticker and the transaction
     * type.
     * 
     * This is usually used to easily retreave the amount of bought or sold 
     * stock for a specific ticker.
     * 
     * @param stock
     * @param type
     * @return the amount of stock
     * @throws SQLException 
     */
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
        while (result.next()) {
            amount = result.getInt("stock_amount");
        }
        return amount;
    }

    /**
     * Returns the current distinct stock tickers.
     * 
     * @return a ResultSet
     * @throws SQLException 
     */
    public ResultSet getStockTickers() throws SQLException {
        String sql = "SELECT distinct stock_ticker FROM [transaction]";
        PreparedStatement stmt = connection.prepareStatement(sql);
        ResultSet result = stmt.executeQuery();
        return result;
    }

    /**
     * Returns a stocks data by the ticker.
     * 
     * @param ticker
     * @return a ResultSet for the stock
     * @throws SQLException 
     */
    public ResultSet getStockByTicker(String ticker) throws SQLException {
        String sql = "SELECT * FROM stock WHERE ticker = ?";
        PreparedStatement stmt = connection.prepareStatement(sql);
        stmt.setString(1, ticker);
        ResultSet result = stmt.executeQuery();
        return result;
    }

    /**
     * Returns the transaction table data for the main GUI table.
     * 
     * @return a ResultSet
     * @throws SQLException 
     */
    public ResultSet getMainTableTransactionData() throws SQLException {
        String sql = "select \n"
                + "    total.stock_ticker as stock_ticker, \n"
                + "    sum(total.stock_amount) as stock_count, \n"
                + "    sum (total.currency_sum) as currency_sum, \n"
                + "    (select\n"
                + "		count(*) from 'transaction' where "
                + "stock_ticker = total.stock_ticker\n"
                + "	) as transaction_count\n"
                + "from (\n"
                + "    select stock_ticker, sum(stock_amount) as stock_amount, "
                + "sum(currency_amount) as currency_sum\n"
                + "    from 'transaction' where transaction_type = 'BUY' group "
                + "by stock_ticker\n"
                + "    UNION\n"
                + "    select stock_ticker, sum(stock_amount * -1) as "
                + "stock_amount, sum(currency_amount * -1) as currency_sum\n"
                + "    from 'transaction' where transaction_type = 'SELL' "
                + "group by stock_ticker\n"
                + ") as total group by stock_ticker;";

        PreparedStatement stmt = connection.prepareStatement(sql);
        ResultSet result = stmt.executeQuery();
        return result;
    }

    /**
     * Returns the portfolio purchace price for the main GUI window.
     * 
     * @return a ResultSet
     * @throws SQLException 
     */
    public ResultSet getPortfolioPurchacePrice() throws SQLException {
        String sql = "select \n"
                + "    sum (total.currency_sum) as currency_sum\n"
                + "from (\n"
                + "    select sum(currency_amount) as currency_sum from "
                + "'transaction' where transaction_type = 'BUY'\n"
                + "    UNION\n"
                + "    select sum(currency_amount * -1) as currency_sum "
                + "from 'transaction' where transaction_type = 'SELL'\n"
                + ") as total;";

        PreparedStatement stmt = connection.prepareStatement(sql);
        ResultSet result = stmt.executeQuery();
        return result;
    }

    /**
     * Returns the portfolios current value for the main GUI window.
     * 
     * NOTE! DOES NOT WORK AT THE MOMENT!
     * 
     * @return a ResultSet
     * @throws SQLException 
     */
    public ResultSet getPortfolioCurrentValue() throws SQLException {
        String sql = "select 0 as current_value from 'transaction'";

        PreparedStatement stmt = connection.prepareStatement(sql);
        ResultSet result = stmt.executeQuery();
        return result;
    }

    /**
     * Returns the portfolio stock amount for the main GUI window.
     * 
     * @return a ResultSet
     * @throws SQLException 
     */
    public ResultSet getPortfolioStockAmount() throws SQLException {
        String sql = "select \n"
                + "sum (total.stock_amount) as stock_amount\n"
                + "from (\n"
                + "    select sum(stock_amount) as stock_amount from "
                + "'transaction' where transaction_type = 'BUY'\n"
                + "    UNION\n"
                + "    select sum(stock_amount * -1) as stock_amount from "
                + "'transaction' where transaction_type = 'SELL'\n"
                + ") as total;";

        PreparedStatement stmt = connection.prepareStatement(sql);
        ResultSet result = stmt.executeQuery();
        return result;
    }    
}

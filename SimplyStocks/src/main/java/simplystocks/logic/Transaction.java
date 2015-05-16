/*
 */
package simplystocks.logic;

import java.math.BigDecimal;
import java.util.Date;

/**
 * A specific transaction.
 * 
 * A transaction is a buying or selling of a specific stock. This
 * includes the date, stock, amount of stocks, sum and currency.
 * Note that the stock is attached to a specific market, ie exchange.
 * 
 * @author Fredrik Grönqvist <fredrik.groqvist+nb@gmail.com>
 */
public class Transaction {
    protected Date date;
    protected Stock stock;
    protected Integer stockAmount;
    protected BigDecimal currencyAmount;

    /**
     * @return the date
     */
    public Date getDate() {
        return date;
    }

    /**
     * @param date the date to set
     */
    public void setDate(Date date) {
        this.date = date;
    }

    /**
     * @return the stock
     */
    public Stock getStock() {
        return stock;
    }

    /**
     * @param stock the stock to set
     */
    public void setStock(Stock stock) {
        this.stock = stock;
    }

    /**
     * @return the stockAmount
     */
    public Integer getStockAmount() {
        return stockAmount;
    }

    /**
     * @param stockAmount the stockAmount to set
     */
    public void setStockAmount(Integer stockAmount) {
        this.stockAmount = stockAmount;
    }

    /**
     * @return the currencyAmount
     */
    public BigDecimal getCurrencyAmount() {
        return currencyAmount;
    }

    /**
     * @param currencyAmount the currencyAmount to set
     */
    public void setCurrencyAmount(BigDecimal currencyAmount) {
        this.currencyAmount = currencyAmount;
    }
    
    
}

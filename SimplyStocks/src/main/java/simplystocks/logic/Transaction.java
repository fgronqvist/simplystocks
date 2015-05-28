/*
 */
package simplystocks.logic;

import java.math.BigDecimal;
import java.util.Date;

/**
 *
 * @author Fredrik Grönqvist <fredrik.groqvist+nb@gmail.com>
 */
public interface Transaction {

    public enum TRANSACTION_TYPES {
        /**
         * The transaction is one where stocks are bought.
         */
        BUY, 
        
        /**
         * The transaction is one where stocks are sold.
         */
        SELL
    }
    
    /**
     * @return the currency string
     */
    String getCurrency();

    /**
     * @return the transaction currencyAmount
     */
    BigDecimal getCurrencyAmount();

    /**
     * @return the transaction date
     */
    Date getDate();

    /**
     * @return the transaction stock
     */
    Stock getStock();

    /**
     * @return the transaction stockAmount
     */
    Integer getStockAmount();

    /**
     * @return the transaction type
     */
    TRANSACTION_TYPES getType();

    /**
     * An init method.
     *
     * This method has to be over-ridden in a concrete subclass. Use this
     * method to set the TRANSACTION_TYPE as follows.
     *
     * <pre>
     * public void init(){
     *  setTransactionType(TRANSACTION_TYPES.BUY);
     * }
     * </pre>
     *
     */
    void init();

    /**
     * Currency cannot be null or empty.
     *
     * The currency should be international abbreviations of currencies.
     * @see http://en.wikipedia.org/wiki/ISO_4217
     *
     * @param currency
     * @throws Exception if currency is null or empty
     */
    void setCurrency(String currency) throws Exception;

    /**
     * @param currencyAmount the amount
     * @throws java.lang.Exception
     */
    void setCurrencyAmount(BigDecimal currencyAmount) throws Exception;

    /**
     * @param date set the transaction date
     */
    void setDate(Date date);

    /**
     * @param stock set the transaction stock
     */
    void setStock(Stock stock);

    /**
     * Only allows amounts greater than 1.
     * @param stockAmount the amount of stocks
     * @throws java.lang.Exception
     */
    void setStockAmount(Integer stockAmount) throws Exception;

    /**
     * Set the transaction type.
     * @param newType to set
     */
    void setTransactionType(TRANSACTION_TYPES newType);
    
}

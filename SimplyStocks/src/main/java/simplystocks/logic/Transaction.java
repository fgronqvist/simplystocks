/*
 */
package simplystocks.logic;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * A specific transaction.
 * 
 * A transaction is a buying or selling of a specific stock. This
 * includes the date, stock, amount of stocks, sum and currency.
 * Note that the stock is attached to a specific market, ie exchange.
 * 
 * @author Fredrik Grönqvist <fredrik.groqvist+nb@gmail.com>
 */
abstract class Transaction {
    private Date date;
    private Stock stock;
    private Integer stockAmount;
    private String currency;
    private BigDecimal currencyAmount;
    private TRANSACTION_TYPES type;
    
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
     * Constructor that checks if the type is
     * a part of TRANSACTION_TYPES.
     * 
     * @throws AssertionError if type is incorrect.
     */
    public Transaction() throws AssertionError{       
        init();
        switch(this.type){
            case BUY:
                break;
                
            case SELL:
                break;
                
            default:
                throw new AssertionError(this.type.name());
        }
    }
    
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
    public void init(){
    }
    
    /**
     * @return the transaction type
     */
    public TRANSACTION_TYPES getType(){
        return type;
    }
    
    /**
     * Set the transaction type.
     * @param newType to set
     */
    public void setTransactionType(TRANSACTION_TYPES newType){
        type = newType;
    }
    
    /**
     * @return the transaction date
     */
    public Date getDate() {
        return date;
    }

    /**
     * @param date set the transaction date
     */
    public void setDate(Date date) {
        this.date = date;
    }

    /**
     * @return the transaction stock
     */
    public Stock getStock() {
        return stock;
    }

    /**
     * @param stock set the transaction stock
     */
    public void setStock(Stock stock) {
        this.stock = stock;
    }

    /**
     * @return the transaction stockAmount
     */
    public Integer getStockAmount() {
        return stockAmount;
    }

    /**
     * Only allows amounts greater than 1.
     * @param stockAmount the amount of stocks
     * @throws java.lang.Exception
     */
    public void setStockAmount(Integer stockAmount) throws Exception {
        if(stockAmount == null || stockAmount < 1){
            throw new Exception("stockAmount cannot be null or <1");
        }
        this.stockAmount = stockAmount;
    }
    
    /**
     * Currency cannot be null or empty.
     * 
     * The currency should be international abbreviations of currencies.
     * @see http://en.wikipedia.org/wiki/ISO_4217
     * 
     * @param currency
     * @throws Exception if currency is null or empty
     */
    public void setCurrency(String currency) throws Exception{
        if(currency == null || currency.isEmpty()){
            throw new Exception("currency cannot be null or empty");
        }
        this.currency = currency;
    }
    
    /**
     * @return the currency string
     */
    public String getCurrency(){
        return this.currency;
    }

    /**
     * @return the transaction currencyAmount
     */
    public BigDecimal getCurrencyAmount() {
        return currencyAmount;
    }

    /**
     * @param currencyAmount the amount
     * @throws java.lang.Exception
     */
    public void setCurrencyAmount(BigDecimal currencyAmount) throws Exception {
        if(currencyAmount == null) {
            throw new Exception("currencyAmount cannot be null");
        }
        if(currencyAmount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new Exception("currencyAmount cannot be zero or less");
        }
        
        this.currencyAmount = currencyAmount;
    }
    
    
}

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
abstract class TransactionBase implements Transaction{
    private Date date;
    private Stock stock;
    private Integer stockAmount;
    private String currency;
    private BigDecimal currencyAmount;
    private TRANSACTION_TYPES type;
             
    /**
     * Constructor that checks if the type is
     * a part of TRANSACTION_TYPES.
     * 
     * @throws AssertionError if type is incorrect.
     */
    public TransactionBase() throws AssertionError {       
        init();
        try {            
            switch(this.type){
                case BUY:
                    break;

                case SELL:
                    break;

                default:
                    throw new AssertionError(this.type.name());
            }
        }
        catch (NullPointerException e) {
            throw new AssertionError("NULL Transaction.type value", e);
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
    @Override
    public void init() {        
    }
    
    /**
     * @return the transaction type
     */
    @Override
    public TRANSACTION_TYPES getType(){
        return type;
    }
    
    /**
     * Set the transaction type.
     * @param newType to set
     */
    @Override
    public void setTransactionType(TRANSACTION_TYPES newType){
        type = newType;
    }
    
    /**
     * @return the transaction date
     */
    @Override
    public Date getDate() {
        return date;
    }

    /**
     * @param date set the transaction date
     */
    @Override
    public void setDate(Date date) {
        this.date = date;
    }

    /**
     * @return the transaction stock
     */
    @Override
    public Stock getStock() {
        return stock;
    }

    /**
     * @param stock set the transaction stock
     */
    @Override
    public void setStock(Stock stock) {
        this.stock = stock;
    }

    /**
     * @return the transaction stockAmount
     */
    @Override
    public Integer getStockAmount() {
        return stockAmount;
    }

    /**
     * Only allows amounts greater than 1.
     * @param stockAmount the amount of stocks
     * @throws java.lang.Exception
     */
    @Override
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
    @Override
    public void setCurrency(String currency) throws Exception{
        if(currency == null || currency.isEmpty()){
            throw new Exception("currency cannot be null or empty");
        }
        this.currency = currency;
    }
    
    /**
     * @return the currency string
     */
    @Override
    public String getCurrency(){
        return this.currency;
    }

    /**
     * @return the transaction currencyAmount
     */
    @Override
    public BigDecimal getCurrencyAmount() {
        return currencyAmount;
    }

    /**
     * @param currencyAmount the amount
     * @throws java.lang.Exception
     */
    @Override
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

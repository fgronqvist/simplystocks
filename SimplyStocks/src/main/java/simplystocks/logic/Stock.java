/*
 */
package simplystocks.logic;

/**
 * The stock class represents a single stock.
 * 
 * An instance does not represent transactions (buys or sells) of stock,
 * just the information about a specific stock. A stock is therefore a
 * part of a number of different transactions.
 * 
 * @author Fredrik Grönqvist <fredrik.groqvist+nb@gmail.com>
 */
public class Stock {

    protected String ticker;
    protected String name;
    protected String exchange;

    public Stock(){
    }
    
    /**
     * Creates a Stock with the provided values.
     * 
     * @param ticker
     * @param name
     * @param exchange 
     * @throws java.lang.Exception 
     */
    public Stock(String ticker, String name, String exchange) throws Exception {
        this.setTicker(ticker);
        this.setName(name);
        this.setExchange(exchange);
    }
    
    /**
     * @return the ticker
     */
    public String getTicker() {
        return ticker;
    }

    /**
     * The ticker cannot be empty or null.
     * @param ticker the ticker to set
     * @throws java.lang.Exception
     */
    public void setTicker(String ticker) throws Exception {
        if(ticker == null || ticker.isEmpty()){
            throw new Exception("ticker cannot be empty");
        }
        this.ticker = ticker;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * The name cannot be empty or null.
     * @param name the name to set
     * @throws java.lang.Exception
     */
    public void setName(String name) throws Exception {
        if(name == null || name.isEmpty()){
            throw new Exception("name cannot be empty");
        }
        this.name = name;
    }

    /**
     * @return the exchange
     */
    public String getExchange() {
        return exchange;
    }

    /**
     * The exchange cannot be empty or null.
     * @param exchange the exchange to set
     * @throws java.lang.Exception
     */
    public void setExchange(String exchange) throws Exception {
        if(exchange == null || exchange.isEmpty()){
            throw new Exception("exchange cannot be empty");
        }
        this.exchange = exchange;
    }        
}

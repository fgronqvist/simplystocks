/*
 */
package simplystocks.logic;

/**
 * The transaction handles the buying of one type of stock.
 * 
 * @author Fredrik Grönqvist <fredrik.groqvist+nb@gmail.com>
 */
public class TransactionBuy extends TransactionBase {
    @Override
    public void init() {
        setTransactionType(TRANSACTION_TYPES.BUY);
    }    
}

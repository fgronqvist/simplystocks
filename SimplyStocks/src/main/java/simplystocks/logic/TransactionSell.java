/*
 */
package simplystocks.logic;

/**
 *
 * @author Fredrik Grönqvist <fredrik.groqvist+nb@gmail.com>
 */
public class TransactionSell extends TransactionBase {
    @Override
    public void init() {
        setTransactionType(TransactionBase.TRANSACTION_TYPES.SELL);
    }
    
}

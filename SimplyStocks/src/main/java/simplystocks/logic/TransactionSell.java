/*
 */
package simplystocks.logic;

/**
 *
 * @author Fredrik Grönqvist <fredrik.groqvist+nb@gmail.com>
 */
public class TransactionSell extends Transaction {
    @Override
    public void init() {
        setTransactionType(Transaction.TRANSACTION_TYPES.SELL);
    }
    
}

/*
 */
package simplystocks.logic;

import java.sql.SQLException;

/**
 *
 * @author Fredrik Grönqvist <fredrik.groqvist+nb@gmail.com>
 */
public class Portfolio {

    public boolean addTransaction(Transaction transaction) throws SQLException, Exception {
        boolean result;
        
        if(transaction instanceof TransactionBuy){
            result = checkTransactionBuy((TransactionBuy) transaction);
        } else if (transaction instanceof TransactionSell){
            result = checkTransactionSell((TransactionSell) transaction);
        } else {
            throw new Exception("unknown transactiontype.");
        }
        
        Database db = new Database();
        return false;
    }
    
    private boolean checkTransactionBuy(TransactionBuy transaction) {
        // nothing to check for now
        return true;
    }
    
    private boolean checkTransactionSell(TransactionSell transaction) {
        //@todo Chceck that we actually have enough stock to sell
        return false;
    }
}

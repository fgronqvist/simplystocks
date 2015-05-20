/*
 */
package simplystocks.helpers;

/**
 * A simple interface for passing error messages between objects.
 * 
 * @author Fredrik Grönqvist <fredrik.groqvist+nb@gmail.com>
 */
public interface ErrorHandler {
    public enum ERROR_CODES {
        /**
         * Indicates that a validation failed.
         */
        VALIDATION_FAILED        
    }
    void addError(ERROR_CODES code, String message);
    boolean hasErrors();
    String getErrorMessage();
}

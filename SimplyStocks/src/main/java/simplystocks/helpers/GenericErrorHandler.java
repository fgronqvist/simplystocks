/*
 */
package simplystocks.helpers;

/**
 * A generic error handler for passing error messages between objects.
 * 
 * @author Fredrik Grönqvist <fredrik.groqvist+nb@gmail.com>
 */
public class GenericErrorHandler implements ErrorHandler {
    ERROR_CODES errorCode = null;
    String errorMessage = null;
    private boolean errors = false;
    
    @Override
    public void addError(ERROR_CODES code, String message) {
        errorCode = code;
        errorMessage = message;
        errors = true;
    }

    @Override
    public boolean hasErrors() {
        return errors;
    }
    
    @Override
    public String getErrorMessage(){
        return errorMessage;
    }
}

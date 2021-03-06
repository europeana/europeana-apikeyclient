package eu.europeana.apikey.client.exception;

/**
 * Created by luthien on 19/06/2017.
 */
public class ApiKeyValidationException extends Exception {

    /**
     * 
     */
    private static final long serialVersionUID = 6387090087297106543L;
    private final String errorMessage;
    
    public ApiKeyValidationException (String errorMessage){
        this.errorMessage = errorMessage;
    }

    public ApiKeyValidationException (String errorMessage, Exception cause){
        super.initCause(cause);
        this.errorMessage = errorMessage;
    }

    public String getErrorMessage() {
        return errorMessage;
    }


}

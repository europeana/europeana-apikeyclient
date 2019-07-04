package eu.europeana.apikey.client;

/**
 * Created by luthien on 11/05/2017.
 * @author GordeaS
 *
 * Contains the HTTP status and query results of a call to the Apikey Service
 * Also see the javadoc comments for the individual methods
 */
public class ApiKeyValidationResult {

    private Integer httpStatus = null;
    private String apiKey = null;
    private boolean validApiKey = false;
    private String errorMessage;
    
    
    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    ApiKeyValidationResult(String apiKey, int httpStatus, String errorMessage){
   	this.apiKey = apiKey;
   	this.httpStatus = httpStatus;
   	this.errorMessage = errorMessage;
     }
    
    ApiKeyValidationResult(String apiKey, int httpStatus, boolean valid){
	this.apiKey = apiKey;
	this.httpStatus = httpStatus;
	this.validApiKey = valid;
    }
    
    public int getHttpStatus() {
        return httpStatus;
    }
    public void setHttpStatus(int httpStatus) {
        this.httpStatus = httpStatus;
    }
    public String getApiKey() {
        return apiKey;
    }
    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }
    public boolean isValidApiKey() {
        return validApiKey;
    }
    public void setValidApiKey(boolean validApiKey) {
        this.validApiKey = validApiKey;
    }
    
}

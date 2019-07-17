package eu.europeana.apikey.client;

import org.apache.http.HttpHeaders;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import eu.europeana.apikey.client.exception.ApiKeyValidationException;

/**
 * Created by luthien on 11/05/2017.
 * 
 * @author GordeaS
 *
 *         Utility to facilitate connecting to the Apikey Service and checking
 *         validity of Apikeys. For performance reasons it is adviced that Applications instatiate this class once for a given URL (service endpoint).
 */
public class Client {
    private static final Logger LOG = LogManager.getLogger(Client.class);
    private static final String AUTHETICATION_APIKEY_PREFIX = "APIKEY ";
    String apiKeyServiceUrl;
    private HttpClient client;
	

    public Client() {

    }

    public Client(String apiKeyServiceUrl) {
	this.apiKeyServiceUrl = apiKeyServiceUrl;
    }

    /**
     * Wrapper method without the method parameter
     *
     * @param apikey the apikey to be validated
     * @return ApiKeyValidationResult object
     * @throws ApiKeyValidationException
     */
    public ApiKeyValidationResult validateApiKey(String apikey) throws ApiKeyValidationException {
	LOG.debug("Validate Apikey");
	ApiKeyValidationResult validationResult;
	String requestUrl = getApiKeyServiceUrl() + "/validate";

	HttpPost request = new HttpPost(requestUrl);
	request.setHeader(HttpHeaders.AUTHORIZATION, AUTHETICATION_APIKEY_PREFIX + apikey);
	HttpResponse response;

	try {
	    response = getHttpClient().execute(request);

	    // not sure if occurs
	    if (response == null) {
		throw new ApiKeyValidationException("Could not connect to Apikeyservice: " + requestUrl);
	    }

	    int httpStatus = response.getStatusLine().getStatusCode();
	    switch (httpStatus) {
	    case HttpStatus.SC_NO_CONTENT:
		// apikey validated
		validationResult = new ApiKeyValidationResult(apikey, httpStatus, true);
		break;
	    case HttpStatus.SC_BAD_REQUEST:
	    case HttpStatus.SC_UNAUTHORIZED:
	    case HttpStatus.SC_FORBIDDEN:
	    case HttpStatus.SC_GONE:
	    case HttpStatus.SC_INTERNAL_SERVER_ERROR:
	    default:
		validationResult = new ApiKeyValidationResult(apikey, httpStatus,
			response.getStatusLine().getReasonPhrase());
		break;
	    }
	} catch (Exception e) {
	    throw new ApiKeyValidationException(
		    "Unexpected exception occured when trying to validate API KEY: " + apikey, e);
	}
	return validationResult;
    }

    private HttpClient getHttpClient() {
	if(client == null) {
	    client = HttpClientBuilder.create().build();
	}
	return client;
    }

    protected String getApiKeyServiceUrl() throws ApiKeyValidationException {
	//if default constructor was used, take the url from configs
	if(apiKeyServiceUrl == null) {
	    apiKeyServiceUrl = PropertyReader.getInstance().getApiKeyServiceUrl();
	}
	return apiKeyServiceUrl;
    }
}

/*
 * Copyright 2007-2017 The Europeana Foundation
 *
 *  Licenced under the EUPL, Version 1.1 (the "Licence") and subsequent versions as approved
 *  by the European Commission;
 *  You may not use this work except in compliance with the Licence.
 *
 *  You may obtain a copy of the Licence at:
 *  http://joinup.ec.europa.eu/software/page/eupl
 *
 *  Unless required by applicable law or agreed to in writing, software distributed under
 *  the Licence is distributed on an "AS IS" basis, without warranties or conditions of
 *  any kind, either express or implied.
 *  See the Licence for the specific language governing permissions and limitations under
 *  the Licence.
 */

package eu.europeana.apikey.client;

import eu.europeana.apikey.client.exception.ApiKeyValidationException;
import org.apache.commons.codec.binary.Base64;
import org.apache.http.HttpHeaders;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.message.BasicNameValuePair;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * Created by luthien on 11/05/2017.
 */

/**
 * Utility to facilitate connecting to the Apikey Service and checking Apikeys for validity
 */
 public class Connector {

    /**
     * Takes a ValidationRequest object containing the authentication details, the apikey to be checked, the calling API
     * and the method (optional). Returns a ValidationResult object containing the HTTP status returned by the Apikey
     * Service, the remaining number of requests for this period, the number of seconds until that number is reset,
     * possible error messages plus booleans representing the validity of the ApiKey, the connection status and a
     * discriminator between a 404 status caused by 'page not found' and one caused by 'Apikey not found'
     *
     * @param validationRequest object
     * @return ValidationResult object
     * @throws IOException
     * @throws NumberFormatException
     */

    public ValidationResult validateApiKey(ValidationRequest validationRequest) throws ApiKeyValidationException {
        ValidationResult validationResult = new ValidationResult();
        String apiKeyServiceUrl = PropertyReader.getInstance().getApiKeyServiceUrl();
        String requestUrl = apiKeyServiceUrl + "/" + validationRequest.getApikey() + "/validate";

        List<NameValuePair> params = new ArrayList<>(2);
        params.add(new BasicNameValuePair("api", validationRequest.getApi()));
        if (StringUtils.isNotBlank(validationRequest.getMethod())) {
            params.add(new BasicNameValuePair("method", validationRequest.getMethod()));
        }

        HttpPost request = new HttpPost(requestUrl);
        try {
            request.setEntity(new UrlEncodedFormEntity(params, "UTF-8"));
        } catch (UnsupportedEncodingException e) {
            throw new ApiKeyValidationException("UnsupportedEncodingException thrown when creating HTTP request", e);
        }

        String auth = validationRequest.getAdminApikey() + ":" + validationRequest.getAdminSecretkey();
        byte[] encodedAuth = Base64.encodeBase64(
                auth.getBytes(Charset.forName("ISO-8859-1")));

        String authHeader = "Basic " + new String(encodedAuth);
        request.setHeader(HttpHeaders.AUTHORIZATION, authHeader);

        HttpClient   client   = HttpClientBuilder.create().build();
        HttpResponse response;

        try {
            response = client.execute(request);
        } catch (IOException e) {
            validationResult.setConnected(false);
            throw new ApiKeyValidationException("IOexception thrown while trying to connect to Apikeyservice", e);
        }
        if (response != null) validationResult.setConnected(true);

        if (validationResult.hasConnected()) {
            validationResult.setReturnStatus(String.valueOf(response.getStatusLine().getStatusCode()));
            if (response.getStatusLine().getStatusCode() == HttpStatus.SC_NO_CONTENT){
                validationResult.setValidKey(true);
            }
            if (response.getStatusLine().getStatusCode() == HttpStatus.SC_NOT_FOUND &&
                    !response.containsHeader("Apikey-not-found")){
                validationResult.setPageNotFound_404(true);
                validationResult.setValidKey(false);
                throw new ApiKeyValidationException("Apikey service was not found on URL " + apiKeyServiceUrl);
            }
            if (response.containsHeader("X-Ratelimit-Remaining")) {
                try {
                    validationResult.setRemaining(Integer.valueOf(
                            response.getFirstHeader("X-Ratelimit-Remaining").getValue()));
                } catch (NumberFormatException e) {
                    String errorMessage = "Error parsing integer value for X-Ratelimit-Remaining";
                    validationResult.setMessage(errorMessage);
                    validationResult.setValidKey(false);
                    throw new ApiKeyValidationException(errorMessage, e);
                }
            }
            if (response.containsHeader("X-Ratelimit-Reset")) {
                try {
                    validationResult.setSecondsToReset(Integer.valueOf(
                            response.getFirstHeader("X-Ratelimit-Reset").getValue()));
                } catch (NumberFormatException e) {
                    String errorMessage = "Error parsing integer value for X-Ratelimit-Reset";
                    validationResult.setMessage(errorMessage);
                    validationResult.setValidKey(false);
                    throw new ApiKeyValidationException(errorMessage, e);
                }
            }
        }
        return validationResult;
    }

    /**
     * convenience method for validateApiKey() method that creates the ValidationRequest object with separate parameters
     *
     * @param adminKey       apikey with level set to ADMIN
     * @param adminSecretKey secret key associated with adminKey
     * @param apikey         the apikey to be validated
     * @param api            the API for which the apikey needs to be validated
     * @param method         the method for which the apikey needs to be validated
     * @return ValidationResult object
     * @throws ApiKeyValidationException
     */

    public ValidationResult validateApiKey(String adminKey, String adminSecretKey, String apikey, String api, String method)
            throws ApiKeyValidationException {
        return validateApiKey(new ValidationRequest(adminKey, adminSecretKey, apikey, api, method));
    }

    /**
     * Wrapper method without the method parameter
     *
     * @param adminKey       apikey with level set to ADMIN
     * @param adminSecretKey secret key associated with adminKey
     * @param apikey         the apikey to be validated
     * @param api            the API for which the apikey needs to be validated
     * @return ValidationResult object
     * @throws ApiKeyValidationException
     */
    public ValidationResult validateApiKey(String adminKey, String adminSecretKey, String apikey, String api)
            throws ApiKeyValidationException {
        return validateApiKey(new ValidationRequest(adminKey, adminSecretKey, apikey, api));
    }



}

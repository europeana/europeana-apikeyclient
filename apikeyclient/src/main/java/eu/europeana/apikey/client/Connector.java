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

public class Connector {
    private String apiKeyServiceUrl = "";
    String propFileName = "config.properties";

    public Connector() throws IOException {
        try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream(propFileName)) {
            Properties prop = new Properties();

            if (inputStream != null) {
                prop.load(inputStream);
            } else {
                throw new FileNotFoundException("Error: property file '" + propFileName + "' not found in the classpath");
            }

            apiKeyServiceUrl = prop.getProperty("apikeyserviceurl");

        } catch (IOException e) {
            throw new IOException(e.getMessage());
//            System.out.println("Error reading property file: " + e.getMessage());
        }
    }

    public ValidationResult validateApiKey(ValidationRequest validationRequest){
        ValidationResult validationResult = new ValidationResult();
        String url = apiKeyServiceUrl + "/" + validationRequest.getApikey() + "/validate";

        List<NameValuePair> params = new ArrayList<>(2);
        params.add(new BasicNameValuePair("api", validationRequest.getApi()));
        if (StringUtils.isNotBlank(validationRequest.getMethod())) {
            params.add(new BasicNameValuePair("method", validationRequest.getMethod()));
        }

        HttpPost request = new HttpPost(url);
        try {
            request.setEntity(new UrlEncodedFormEntity(params, "UTF-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        String auth = validationRequest.getAdminApikey() + ":" + validationRequest.getAdminSecretkey();
        byte[] encodedAuth = Base64.encodeBase64(
                auth.getBytes(Charset.forName("ISO-8859-1")));

        String authHeader = "Basic " + new String(encodedAuth);
        request.setHeader(HttpHeaders.AUTHORIZATION, authHeader);

        HttpClient   client   = HttpClientBuilder.create().build();
        HttpResponse response = null;

        try {
            response = client.execute(request);
        } catch (IOException e) {
            validationResult.setConnected(false);
        }
        if (response != null) validationResult.setConnected(true);

        if (validationResult.hasConnected()) {
            validationResult.setReturnStatus(String.valueOf(response.getStatusLine().getStatusCode()));
            if (response.getStatusLine().getStatusCode() == HttpStatus.SC_NOT_FOUND &&
                    !response.containsHeader("Apikey-not-found")){
                validationResult.setPageNotFound_404(true);
            }
            if (response.containsHeader("X-Ratelimit-Remaining")) {
                validationResult.setRemaining(response.getFirstHeader("X-Ratelimit-Remaining").getValue());
            }
            if (response.containsHeader("X-Ratelimit-Reset")) {
                validationResult.setSecondsToReset(response.getFirstHeader("X-Ratelimit-Reset").getValue());
            }
        }
        return validationResult;
    }


}

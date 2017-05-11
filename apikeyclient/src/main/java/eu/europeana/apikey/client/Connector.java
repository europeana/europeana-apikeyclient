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
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClientBuilder;

import java.io.IOException;
import java.nio.charset.Charset;

/**
 * Created by luthien on 11/05/2017.
 */
public class Connector {


    //    private static final String APIKEYSERVICEURL = "https://apikeyservice.cfapps.io/apikey";
    private static final String APIKEYSERVICEURL = "http://localhost:8081/apikey";

    public ValidationResult validateApiKey(ValidationRequest validationRequest){
        ValidationResult validationResult = new ValidationResult();
        String url = APIKEYSERVICEURL + "/" + validationRequest.getApikey() + "/validate";

        HttpPost request = new HttpPost(url);
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
            validationResult.setSuccess(false);
//            e.printStackTrace();
        }
        if (response != null) validationResult.setSuccess(true);

        if (validationResult.isSuccess()) {
            validationResult.setReturnStatus(String.valueOf(response.getStatusLine().getStatusCode()));
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

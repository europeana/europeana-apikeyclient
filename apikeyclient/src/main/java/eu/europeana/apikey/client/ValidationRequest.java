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

/**
 * Created by luthien on 11/05/2017.
 */
public class ValidationRequest {

    private String api;
    private String method;
    private String adminApikey;
    private String adminSecretkey;
    private String apikey;

    public ValidationRequest(String adminApikey, String adminSecretkey, String apikey, String api){
        setAdminApikey(adminApikey);
        setAdminSecretkey(adminSecretkey);
        setApikey(apikey);
        setApi(api);
    }

    public ValidationRequest(String adminApikey, String adminSecretkey, String apikey, String api, String method){
        this(adminApikey, adminSecretkey, apikey, api);
        setMethod(method);
    }

    public String getApi() {
        return api;
    }

    public void setApi(String api) {
        this.api = api;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getAdminApikey() {
        return adminApikey;
    }

    public void setAdminApikey(String adminApikey) {
        this.adminApikey = adminApikey;
    }

    public String getAdminSecretkey() {
        return adminSecretkey;
    }

    public void setAdminSecretkey(String adminSecretkey) {
        this.adminSecretkey = adminSecretkey;
    }

    public String getApikey() {
        return apikey;
    }

    public void setApikey(String apikey) {
        this.apikey = apikey;
    }



}

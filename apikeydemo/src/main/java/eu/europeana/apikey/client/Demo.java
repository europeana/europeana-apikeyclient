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

import org.apache.commons.lang3.StringUtils;

/**
 * Created by luthien on 10/05/2017.
 */
public class Demo {

    private String apikey;
    private String status;
    private String api;
    private String method;
    private String remaining;
    private String secondsToReset;
    private String message = null;

    public String getApi() {
        return StringUtils.isNotBlank(api) ? api : "--";
    }

    public void setApi(String api) {
        this.api = api;
    }

    public String getMethod() {
        return StringUtils.isNotBlank(method) ? method : "--";
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getRemaining() {
        return StringUtils.isNotBlank(remaining) ? remaining : "--";
    }

    public void setRemaining(String remaining) {
        this.remaining = remaining;
    }

    public String getSecondsToReset() {
        return StringUtils.isNotBlank(secondsToReset) ? secondsToReset : "--";
    }

    public void setSecondsToReset(String secondsToReset) {
        this.secondsToReset = secondsToReset;
    }

    public String getApikey() {
        return apikey;
    }

    public void setApikey(String apikey) {
        this.apikey = apikey;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}

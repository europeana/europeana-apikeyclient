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

/*
 * Contains the HTTP status and query results of a call to the Apikey Service
 * Also see the javadoc comments for the individual methods
 */
public class ValidationResult {

    private String returnStatus;
    private Integer remaining;
    private Integer secondsToReset;
    private String message;
    private boolean connected        = false;
    private boolean pageNotFound_404 = false;
    private boolean validKey = false;

    /*
     * @return HTTP status code of the response to a call to the Apikey Service. Possible values:
     * 204 (apikey valid)
     * 400 (bad Request: missing or wrong parameter(s))
     * 404 (apikey not found OR Apikeyservice not found => see isPageNotFound_404)
     * 410 (apikey deprecated)
     * 429 (request quota reached)
     */
    public String getReturnStatus() {
        return returnStatus;
    }

    void setReturnStatus(String returnStatus) {
        this.returnStatus = returnStatus;
    }

    /*
     * @return the remaining number of requests left for this period
     */
    public Integer getRemaining() {
        return remaining;
    }

    void setRemaining(int remaining) {
        this.remaining = remaining;
    }

    /*
     * @return the number of seconds until the request quota is reset
     */
    public Integer getSecondsToReset(){
        return secondsToReset;
    }

    void setSecondsToReset(int secondsToReset) {
        this.secondsToReset = secondsToReset;
    }

    /*
     * @return additional error message
     */
    public String getMessage() {
        return message;
    }

    void setMessage(String message) {
        this.message = message;
    }

    /*
     * @return whether or not the client has successfully connected to the Apikey Service
     */
    public boolean hasConnected() {
        return connected;
    }

    void setConnected(boolean connected) {
        this.connected = connected;
    }

    /*
     * @return whether a HTTP 404 status is caused by a regular 'Page not found' rather than the Apikey not being
     * found by the Apikey Service
     */
    public boolean isPageNotFound_404() {
        return pageNotFound_404;
    }

    void setPageNotFound_404(boolean pageNotFound_404) {
        this.pageNotFound_404 = pageNotFound_404;
    }

    /*
     * @return whether the submitted Apikey is valid
     */
    public boolean isValidKey() {
        return validKey;
    }

    void setValidKey(boolean validKey) {
        this.validKey = validKey;
    }

}

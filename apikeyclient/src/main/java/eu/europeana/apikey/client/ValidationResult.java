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
public class ValidationResult {

    private String returnStatus;
    private String remaining;
    private String secondsToReset;
    private String message;
    private boolean connected        = false;
    private boolean pageNotFound_404 = false;

    public String getReturnStatus() {
        return returnStatus;
    }

    public void setReturnStatus(String returnStatus) {
        this.returnStatus = returnStatus;
    }

    public String getRemaining() {
        return remaining;
    }

    public void setRemaining(String remaining) {
        this.remaining = remaining;
    }

    public String getSecondsToReset() {
        return secondsToReset;
    }

    public void setSecondsToReset(String secondsToReset) {
        this.secondsToReset = secondsToReset;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean hasConnected() {
        return connected;
    }

    public void setConnected(boolean connected) {
        this.connected = connected;
    }

    public boolean isPageNotFound_404() {
        return pageNotFound_404;
    }

    public void setPageNotFound_404(boolean pageNotFound_404) {
        this.pageNotFound_404 = pageNotFound_404;
    }
}

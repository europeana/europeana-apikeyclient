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

package eu.europeana.apikey.client.exception;

/**
 * Created by luthien on 19/06/2017.
 */
public class ApiKeyValidationException extends Exception {

    private String errorMessage;

    public ApiKeyValidationException (String errorMessage){
        this.errorMessage = errorMessage;
    }

    public ApiKeyValidationException (String errorMessage, Exception cause){
        this.initCause(cause);
        this.errorMessage = errorMessage;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

}

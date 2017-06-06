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
 * Created by luthien on 10/05/2017.
 */
public interface FormValues {

    String[] demokeys = { "ApiKey2", "ApiKey3", "ApiKey4", "ApiKey5", "ApiKey6", "ApiKey7", "ApiKey8", "ApiKey1" };
    String[] apis = { "search", "entity", "annotation", "contradiction", "metanoia", "" };
    String[] methods = { "", "read", "write", "delete", "bling" };


}

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

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.apache.commons.lang3.StringUtils;
import java.util.Arrays;

/**
 * Created by luthien on 07/03/2017.
 */

@Controller
public class ClientController {

    private static final String ADMINAPIKEY = "ApiKey1";
    private static final String ADMINSECRETKEY = "PrivateKey1";

    @GetMapping("/demo")
    public String demoForm(Model model) {
        model.addAttribute("demo", new Demo());
        model.addAttribute("demokeys", Arrays.asList(FormValues.demokeys));
        model.addAttribute("apis", Arrays.asList(FormValues.apis));
        model.addAttribute("methods", Arrays.asList(FormValues.methods));
        return "demo";
    }

    @PostMapping("/demo")
    public String demoSubmit(@ModelAttribute Demo demo) {
        ValidationRequest request = new ValidationRequest(ADMINAPIKEY, ADMINSECRETKEY, demo.getApikey(), demo.getApi());
        if (StringUtils.isNotBlank(demo.getMethod())) request.setMethod(demo.getMethod());
        Connector connector = new Connector();
        ValidationResult result = connector.validateApiKey(request);
        if (result.isSuccess()){
            if (StringUtils.isNotBlank(result.getReturnStatus())) demo.setStatus(result.getReturnStatus());
            if (StringUtils.isNotBlank(result.getRemaining())) demo.setRemaining(result.getRemaining());
            if (StringUtils.isNotBlank(result.getSecondsToReset())) demo.setSecondsToReset(result.getSecondsToReset());
        } else {
            demo.setMessage("Error: could not connect to ApiKey Service");
        }
        return "result";
    }


}

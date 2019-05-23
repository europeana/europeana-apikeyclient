package eu.europeana.apikey.client;

import eu.europeana.apikey.client.exception.ApiKeyValidationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
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
        Connector connector;
        try {
            connector = new Connector();
            ValidationResult result = connector.validateApiKey(request);
            if (result.hasConnected()){
                if (StringUtils.isNotBlank(result.getReturnStatus())) demo.setStatus(result.getReturnStatus());
                if (null != result.getRemaining()) demo.setRemaining(String.valueOf(result.getRemaining()));
                if (null != result.getSecondsToReset()) demo.setSecondsToReset(String.valueOf(result.getSecondsToReset()));
                if (StringUtils.isBlank(demo.getApi())) demo.setApi("none");
                if (StringUtils.isBlank(demo.getMethod())) demo.setMethod("nothing");
                if (result.isPageNotFound_404()){
                    demo.setPageNotFound_404(true);
                    demo.setMessage("Error: Apikeyservice not found on server");
                }
                demo.setStatusHumanReadable(); // user experience management measure
            } else {
                demo.setMessage("Error: could not connect to ApiKey Service");
            }
        } catch (ApiKeyValidationException e) {
            demo.setMessage(e.getMessage());
        }
        return "result";
    }


}

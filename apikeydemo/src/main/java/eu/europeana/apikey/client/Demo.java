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
    private boolean pageNotFound_404 = false;

    public String getApi() {
        return StringUtils.isNotBlank(api) ? api : "";
    }

    public void setApi(String api) {
        this.api = api;
    }

    public String getMethod() {
        return StringUtils.isNotBlank(method) ? method : "";
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

    public boolean isPageNotFound_404() {
        return pageNotFound_404;
    }

    public void setPageNotFound_404(boolean pageNotFound_404) {
        this.pageNotFound_404 = pageNotFound_404;
    }

    public void setStatusHumanReadable(){
        switch (status) {
            case "204":
                status += " NO CONTENT: Apikey OK";
                break;
            case "429":
                status += " TOO MANY REQUESTS: try again after midnight";
                break;
            case "410":
                status += " GONE: this ApiKey is deprecated";
                break;
            case "404":
                if (!isPageNotFound_404()) status += " NOT FOUND: ApiKey does not exist";
                break;
            case "400":
                status += " BAD REQUEST: bad or missing parameter(s)";
                break;
            case "200":
                status += " OK";
                break;
            case "418":
                status += " I'M A TEAPOT... glurgl";
                break;
            case "202":
                status += " ACCEPTED. In a manner of speaking.";
                break;
            default:
                status += ".999999999999999999999 +++ INEXCUSABLE DISCOLOURATION ERROR +++ REINSTALL AND REBOOT +++";
        }
    }

    public void setMessage(String message) {
        this.message = message;
    }
}

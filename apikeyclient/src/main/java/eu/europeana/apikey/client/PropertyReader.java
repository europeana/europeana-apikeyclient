package eu.europeana.apikey.client;

import eu.europeana.apikey.client.exception.ApiKeyValidationException;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Created by luthien on 14/06/2017.
 */
public class PropertyReader {

    private static PropertyReader instance = null;

    private Properties props = null;
    private String apiKeyServiceUrl = "";
    String propFileName = "config.properties";

    private PropertyReader() throws ApiKeyValidationException {
        try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream(propFileName)) {
            props = new Properties();
            if (inputStream != null) props.load(inputStream);
            else throw new ApiKeyValidationException("Error: property file '" + propFileName + "' not found in the classpath");
            apiKeyServiceUrl = props.getProperty("apikeyserviceurl");
        } catch (IOException e) {
            throw new ApiKeyValidationException("IOException thrown when trying to read 'apikeyserviceurl' property " +
                    "from property file " + propFileName, e);
        }
    }

    public static synchronized PropertyReader getInstance() throws ApiKeyValidationException {
        if (instance == null)
            instance = new PropertyReader();
        return instance;
    }

    public String getApiKeyServiceUrl(){
        return this.props.getProperty("apikeyserviceurl");
    }
}

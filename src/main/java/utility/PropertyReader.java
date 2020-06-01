package utility;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertyReader {
    private static Properties properties;
    private InputStream inputStream = null;
    private static Logger logger = LogManager.getLogger();

    public PropertyReader() {
        loadProperties();
    }

    private void loadProperties() {
        if (properties == null) {
            properties = new Properties();
            try {
                inputStream = new FileInputStream("src/main/resources/application.properties");
                properties.load(inputStream);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public String readProperty(String key) {
        return properties.getProperty(key);
    }

    public void setProperty(String key, String value) {
        properties.setProperty(key, value);
    }
}

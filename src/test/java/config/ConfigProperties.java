package config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;
import java.util.TimeZone;

public class ConfigProperties {
    private static final Logger LOGGER = LoggerFactory.getLogger(ConfigProperties.class);

    private ConfigProperties() {
    }

    public static final String BASE_URI;
    public static final String API_SECRET_KEY;

    private static final Properties props = new Properties();
    private static final String PATH_TO_RESOURCES;
    public static final String env;

    static {
        env = System.getProperty("env", "dev");
        LOGGER.info("C  O  N  F  I  G  U  R  A  T  I  O  N");
        LOGGER.info("JVM timezone: {}", TimeZone.getDefault().getID());
        LOGGER.info("Environment : " + env);
        PATH_TO_RESOURCES = "src/test/resources/env/" + env;
        readProperties();

        BASE_URI = getProperty("base.uri");
        API_SECRET_KEY = getProperty("api.secret.key");
    }

    private static void readProperties() throws AssertionError {
        String path = PATH_TO_RESOURCES + "/config.properties";
        try {
            LOGGER.info("Reading configuration data from resources file {}", path);
            props.load(new FileReader(path));
            props.load(new FileInputStream(path));
        } catch (IOException e) {
            throw new AssertionError(String.format("An exception occurs during loading of '%s' config file", path), e);
        }
    }

    private static String getProperty(String propertyName, String defaultValue) {
        return System.getProperty(propertyName.toLowerCase(), props.getProperty(propertyName, defaultValue));
    }

    private static String getProperty(String propertyName) {
        String propertyValue = getProperty(propertyName, null);
        LOGGER.info("Read property {} = {}", propertyName, propertyValue);
        return propertyValue;
    }

}
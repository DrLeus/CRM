package ua.com.smart.andrey.leus.CRM.model;

import org.apache.log4j.PropertyConfigurator;
import ua.com.smart.andrey.leus.CRM.controller.Main;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConfigurationLog {

    private final static String PROPERTIES_FILE = "config/log4j.properties";

    public void init() throws CRMException {
        try {
            Properties properties = new Properties();
            properties.load(ConfigurationLog.class.getClassLoader().getResourceAsStream(PROPERTIES_FILE));
            PropertyConfigurator.configure(properties);
        } catch (IOException e) {
            System.out.println("Problem loading sqlDB properties");
            e.printStackTrace();
        }
    }
}

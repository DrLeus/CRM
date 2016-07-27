package ua.com.smart.andrey.leus.CRM.model;

import org.apache.log4j.PropertyConfigurator;
import ua.com.smart.andrey.leus.CRM.controller.Main;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;


public class Configuration {

    private Properties properties;

    public Configuration() {
        try {
            properties = new Properties();
            PropertyConfigurator.configure(properties);
            InputStream is = Main.class.getClassLoader().getResourceAsStream("\"C:\\JavaCourse\\CRM\\src\\main\\resource\\config\\sqlcmd.properties\"");
//            InputStream is = Main.class.getClassLoader().getResourceAsStream("resource/config/sqlcmd.properties");
            properties.load(is);
            is.close();
        } catch (IOException e) {
            System.out.println("Problem loading sqlDB properties");
            e.printStackTrace();
        }
    }

    public String getClassDriver() {
        return properties.getProperty("database.class.driver");
    }

    public String getServerName() {
        return properties.getProperty("database.server.name");
    }

    public String getDatabaseName() {
        return properties.getProperty("database.name");
    }

    public String getDatabaseNameCRM() {
        return properties.getProperty("database.name.CRM");
    }

    public String getDatabaseNameTemp() {
        return properties.getProperty("database.name.temp");
    }

    public String getDatabaseNameTempNew() {
        return properties.getProperty("database.name.temp.new");
    }

    public String getPort() {
        return properties.getProperty("database.port");
    }

    public String getDriver() {
        return properties.getProperty("database.jdbc.driver");
    }

    public String getUserName() {
        return properties.getProperty("database.user.name");
    }

    public String getUserPassword() {
        return properties.getProperty("database.user.password");
    }
}

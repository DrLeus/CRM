package ua.com.smart.andrey.leus.CRM.model;

import org.apache.log4j.PropertyConfigurator;
import ua.com.smart.andrey.leus.CRM.controller.Main;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;


public class Configuration {

    private static final String SQL_PROPERTIES_FILE = "src/main/resource/config/sqlcmd.properties";

    private Properties properties;

    private String driver;
    private String serverName;
    private String port;
    private String classDriver;
    private String databaseName;
    private String userName;
    private String userPassword;
    private String databaseNameCRM;
    private String databaseNameTemp;
    private String databaseNameTempNew;


    public Configuration() {
        properties = new Properties();
        File file = new File(SQL_PROPERTIES_FILE);
        try (FileInputStream fileInput = new FileInputStream(file)) {
            properties.load(fileInput);
            driver = properties.getProperty("database.jdbc.driver");
            serverName = properties.getProperty("database.server.name");
            port = properties.getProperty("database.port");
            classDriver = properties.getProperty("database.class.driver");
            databaseName = properties.getProperty("database.name");
            userName = properties.getProperty("database.user.name");
            userPassword = properties.getProperty("database.user.password");
            databaseNameCRM = properties.getProperty("database.name.CRM");
            databaseNameTemp = properties.getProperty("database.name.temp");
            databaseNameTempNew = properties.getProperty("database.name.temp.new");
        } catch (Exception e) {
                System.err.println("Loading error properties from file");
        }
    }

    public String getClassDriver() {
        return classDriver;
    }

    public String getServerName() {
        return serverName;
    }

    public String getDatabaseName() {
        return databaseName;
    }

    public String getDatabaseNameCRM() {
        return databaseNameCRM;
    }

    public String getDatabaseNameTemp() {
        return databaseNameTemp;
    }

    public String getDatabaseNameTempNew() {
        return databaseNameTempNew;
    }

    public String getPort() {
        return port;
    }

    public String getDriver() {
        return driver;
    }

    public String getUserName() {
        return userName;
    }

    public String getUserPassword() {
        return userPassword;
    }
}

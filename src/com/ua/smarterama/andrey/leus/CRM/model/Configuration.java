package com.ua.smarterama.andrey.leus.CRM.model;

import com.ua.smarterama.andrey.leus.CRM.controller.Main;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.log4j.PropertyConfigurator;

public class Configuration {

    private Properties properties;

    private static Properties logProperty = new Properties();
    private static String logFile;

    @SuppressWarnings("static-access")
    public Configuration(String logFile){
        this.logFile = logFile;
    }

    public Configuration() {
        try {
            properties = new Properties();
            InputStream is = Main.class.getClassLoader().getResourceAsStream("resource/config/sqlcmd.properties");
            properties.load(is);
            is.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void initLog () {
        try {
            logProperty.load(new FileInputStream(logFile));
            PropertyConfigurator.configure(logProperty);
        } catch (IOException e) {
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

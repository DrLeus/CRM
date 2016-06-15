package com.ua.smarterama.andrey.leus.CRM.model;

import com.ua.smarterama.andrey.leus.CRM.controller.Main;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Configuration {

    private Properties properties;

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

    public String getClassDriver() {
        return properties.getProperty("database.class.driver");
    }

    public String getServerName() {
        return properties.getProperty("database.server.name");
    }

    public String getDatabaseName() {
        return properties.getProperty("database.name");
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

package ua.com.smart.andrey.leus.CRM.model;

import org.apache.log4j.PropertyConfigurator;
import ua.com.smart.andrey.leus.CRM.controller.Main;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConfigurationLog {

    private static Properties logProperty = new Properties();
    private static String logFile;
//    private static String LOG_PROPERTIES_FILE= "resource/config/log4j.properties";
    private static String LOG_PROPERTIES_FILE= "C:\\JavaCourse\\smarterama\\CRM\\src\\resource\\config\\log4j.properties";

    @SuppressWarnings("static-access")
    public ConfigurationLog(){
        this.logFile = LOG_PROPERTIES_FILE;
    }


    public void initLog () throws CRMException {
        try {
            logProperty.load(new FileInputStream(logFile));
            PropertyConfigurator.configure(logProperty);
        } catch (IOException e) {
            System.out.println("Problem loading sqlDB properties");
            e.printStackTrace();
        }
    }
}

package com.ua.smarterama.andrey.leus.CRM.controller;

import com.sun.javaws.exceptions.ExitException;
import com.ua.smarterama.andrey.leus.CRM.model.Configuration;
import com.ua.smarterama.andrey.leus.CRM.model.DataBaseManager;
import com.ua.smarterama.andrey.leus.CRM.model.JDBCDataBaseManager;
import org.apache.log4j.Logger;

import java.sql.SQLException;

public class Main {

    private static String LOG_PROPERTIES_FILE= "C:\\JavaCourse\\smarterama\\CRM\\src\\resource\\config\\log4j.properties";
    private static Logger logger = Logger.getLogger(Main.class);


    public static void main(String[] argv) throws Exception {



        Configuration conf = new Configuration(LOG_PROPERTIES_FILE);
        conf.initLog();



        DataBaseManager manager = new JDBCDataBaseManager();

        SelectedModule select = new SelectedModule(manager);

        try {

//            logger.info("Programme run...");

            select.makeChoice();

        } catch (ExitException e) {
            // do nothing
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
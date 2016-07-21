package ua.com.smart.andrey.leus.CRM.controller;

import ua.com.smart.andrey.leus.CRM.controller.command.ExitException;
import ua.com.smart.andrey.leus.CRM.model.CRMException;
import ua.com.smart.andrey.leus.CRM.model.ConfigurationLog;
import ua.com.smart.andrey.leus.CRM.model.DataBaseManager;
import ua.com.smart.andrey.leus.CRM.model.JDBCDataBaseManager;
import ua.com.smart.andrey.leus.CRM.view.Console;
import org.apache.log4j.Logger;


public class Main {


    private static Logger logger = Logger.getLogger(Main.class);


    public static void main(String[] argv) throws Exception {

        ConfigurationLog conf = new ConfigurationLog();
        conf.init();

        DataBaseManager manager = new JDBCDataBaseManager();
        Console view = new Console();

        SelectedModule select = new SelectedModule(manager, view);

        try {

            logger.info("Programme run..."); //logging

            select.makeChoice();

        } catch (ExitException e) {
            // do nothing
        } catch (CRMException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }catch (Exception e) {
            e.printStackTrace();
        }
    }
}
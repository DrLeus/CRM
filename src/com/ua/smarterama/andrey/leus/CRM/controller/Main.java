package com.ua.smarterama.andrey.leus.CRM.controller;

import com.sun.javaws.exceptions.ExitException;
import com.ua.smarterama.andrey.leus.CRM.controller.command.MainController;
import com.ua.smarterama.andrey.leus.CRM.controller.command.warehouse.MainControllerWarehouse;
import com.ua.smarterama.andrey.leus.CRM.model.DataBaseManager;
import com.ua.smarterama.andrey.leus.CRM.model.JDBCDataBaseManager;
import com.ua.smarterama.andrey.leus.CRM.view.Console;

import java.sql.SQLException;

public class Main {

    public static void main(String[] argv) throws Exception {

        DataBaseManager manager = new JDBCDataBaseManager();

        SelectedModule select = new SelectedModule(manager);

        try {

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
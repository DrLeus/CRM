package com.ua.smarterama.andrey.leus.CRM.controller;


import com.ua.smarterama.andrey.leus.CRM.model.DataBaseManager;
import com.ua.smarterama.andrey.leus.CRM.model.JDBCDatabaseManager;
import com.ua.smarterama.andrey.leus.CRM.view.Console;

import java.sql.SQLException;

public class Main {
    public static void main(String[] argv) throws Exception {

        DataBaseManager manager = new JDBCDatabaseManager();
        MainController controller = new MainController(new Console(), manager);

        try {
            controller.run();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }


}

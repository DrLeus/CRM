package com.ua.smarterama.andrey.leus.CRM.controller;


import com.ua.smarterama.andrey.leus.CRM.model.DataBaseManager;
import com.ua.smarterama.andrey.leus.CRM.model.JDBCDataBaseManager;
import com.ua.smarterama.andrey.leus.CRM.view.Console;
import com.ua.smarterama.andrey.leus.CRM.view.View;

import java.sql.SQLException;

public class Main {
    public static void main(String[] argv) throws Exception {

        DataBaseManager manager = new JDBCDataBaseManager();
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

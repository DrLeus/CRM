package com.ua.smarterama.andrey.leus.CRM.controller;


import com.ua.smarterama.andrey.leus.CRM.view.Console;
import com.ua.smarterama.andrey.leus.CRM.view.View;

import java.sql.SQLException;

public class Main {
    public static void main(String[] argv) throws Exception {

        MainController controller = new MainController(new Console());

        try {
            controller.run(controller);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }


}

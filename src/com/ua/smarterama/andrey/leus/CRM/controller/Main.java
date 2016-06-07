package com.ua.smarterama.andrey.leus.CRM.controller;

import com.sun.javaws.exceptions.ExitException;
import com.ua.smarterama.andrey.leus.CRM.controller.command.warehouse.MainControllerWarehouse;
import com.ua.smarterama.andrey.leus.CRM.model.DataBaseManager;
import com.ua.smarterama.andrey.leus.CRM.model.JDBCDataBaseManager;
import com.ua.smarterama.andrey.leus.CRM.view.Console;

import java.sql.SQLException;

public class Main {

    public static void main(String[] argv) throws Exception {

        DataBaseManager manager = new JDBCDataBaseManager();

        Console view = new Console();

        try {

            while (true) {
                view.write("Do you want to initialize and than connect to database CRM for showing all abilities of module? (Y) " +
                        "or connect to your database (N)?;\n");

                String read = view.read();
                if (read.equalsIgnoreCase("Y")) {
                    MainControllerWarehouse controller = new MainControllerWarehouse(new Console(), manager);
                    view.write("Please wait!\n");
                    controller.run(manager);
                    break;
                } else if (read.equalsIgnoreCase("N")) {
                    MainController controller = new MainController(new Console(), manager);
                    controller.run();
                    break;
                } else if (read.equalsIgnoreCase("exit")) {
                    System.exit(0);
                } else {
                    view.write("Incorrect input, try again\n");
                }
            }
        } catch (ExitException e) {
            // do nothing
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
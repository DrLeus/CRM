package com.ua.smarterama.andrey.leus.CRM.controller.command;

import com.ua.smarterama.andrey.leus.CRM.model.DataBaseManager;
import com.ua.smarterama.andrey.leus.CRM.view.Console;

import java.sql.SQLException;

public class ConnectToDataBase extends Command {

    final static String initialNameDB = "CRM";
    final static String initialUserName = "postgres";
    final static String initialPass = "postgres";

    public ConnectToDataBase(DataBaseManager manager, Console view) {
        super(manager, view);
    }

    @Override
    public boolean canProcess(String command) {
        return command.equals("connect");
    }

    @Override
    public void process() {

        while (true) {

            view.write("Do you want to connect to current database (CRM)? (Y/N)");

            String input = view.checkExit(view.read());

            if (input.equalsIgnoreCase("Y")) {
                try {
                    manager.connect(initialNameDB, initialUserName, initialPass);
                    view.write("Connection succeeded to " + initialNameDB + "\n");
                } catch (SQLException e) {
                    view.write(String.format("Oops...Cant get connection to current database" /* in case " + e*/));
                }

                break;
            } else if (input.equalsIgnoreCase("N")) {
                view.write("Please input the database name");
                String nameDB = view.checkExit(view.read());
                view.write("Please input user name");
                String userName = view.checkExit(view.read());
                view.write("Please input password");
                String password = view.checkExit(view.read());
                try {
                    manager.connect(nameDB, userName, password);
                    view.write("Connection succeeded to " + nameDB);
                } catch (SQLException e) {
                        view.write(String.format("Oops...Cant get connection for DB: %s; USER: %s; PASS: %s",
                                nameDB, userName, password /*+ " in case " + e*/));
                }
                break;
            } else {
                view.write("Oops... something wrong");
            }
        }
    }


}

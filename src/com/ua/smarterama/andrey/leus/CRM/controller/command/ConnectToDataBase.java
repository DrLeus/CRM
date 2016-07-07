package com.ua.smarterama.andrey.leus.CRM.controller.command;

import com.ua.smarterama.andrey.leus.CRM.model.DataBaseManager;
import com.ua.smarterama.andrey.leus.CRM.view.Console;

import java.sql.SQLException;

public class ConnectToDataBase extends Command {

    public ConnectToDataBase(DataBaseManager manager, Console view) {
        super(manager, view);
    }

    @Override
    public boolean canProcess(String command) {
        return command.equals("connect");
    }

    @Override
    public void process() {

        String nameDB;
        String userName;
        String password;

        while (true) {

            view.write("Please input the database name");
            nameDB = view.read();
            if (view.checkExit(nameDB)) {
                view.write("Return to main menu!\n");
                return;
            }

            view.write("Please input user name");

            userName = view.read();
            if (view.checkExit(userName)) {
                view.write("Return to main menu!\n");
                return;
            }

            view.write("Please input password");
            password = view.read();
            if (view.checkExit(password)) {
                view.write("Return to main menu!\n");
                return;
            }

            try {
                manager.connect(nameDB, userName, password);
                view.write("\n");
                view.write(String.format("Connection succeeded to '%s'%n", nameDB));
                break;
            } catch (SQLException e) {
                view.write(String.format("Oops...Cant get connection for DB: %s; USER: %s; PASS: %s%n",
                        nameDB, userName, password));
            }
        }
    }
}
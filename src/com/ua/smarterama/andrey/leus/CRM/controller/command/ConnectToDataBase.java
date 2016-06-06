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

        while (true) {

                view.write("Please input the database name");
                String nameDB = view.checkExit(view.read());
                view.write("Please input user name");
                String userName = view.checkExit(view.read());
                view.write("Please input password");
                String password = view.checkExit(view.read());
                try {
                    manager.connect(nameDB, userName, password);
                    view.write("Connection succeeded to " + nameDB + "\n");
                    break;
                } catch (SQLException e) {
                    view.write(String.format("Oops...Cant get connection for DB: %s; USER: %s; PASS: %s \n",
                                nameDB, userName, password /*+ " in case " + e*/));
                }
            }
        }
    }



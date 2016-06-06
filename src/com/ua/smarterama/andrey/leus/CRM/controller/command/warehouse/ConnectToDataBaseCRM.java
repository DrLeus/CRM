package com.ua.smarterama.andrey.leus.CRM.controller.command.warehouse;

import com.ua.smarterama.andrey.leus.CRM.controller.command.Command;
import com.ua.smarterama.andrey.leus.CRM.model.DataBaseManager;
import com.ua.smarterama.andrey.leus.CRM.view.Console;

import java.sql.SQLException;

public class ConnectToDataBaseCRM extends Command {

    final static String initialNameDB = "CRM";
    final static String initialUserName = "postgres";
    final static String initialPass = "postgres";

    public ConnectToDataBaseCRM(DataBaseManager manager, Console view) {
        super(manager, view);
    }

    @Override
    public boolean canProcess(String command) {
        return command.equals("connect");
    }

    @Override
    public void process() {

        while (true) {

            view.write("Do you want to initialize and to connect to database CRM " +
                    "for showing all abilities of module? (Y/N)");

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
                break;
            } else {
                view.write("Oops... something wrong");
            }
        }
    }


}

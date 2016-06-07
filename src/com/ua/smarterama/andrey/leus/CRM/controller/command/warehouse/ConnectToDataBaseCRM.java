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
        return command.equals("connectCRM");
    }

    @Override
    public void process() {


                try {
                    manager.connect(initialNameDB, initialUserName, initialPass);
                    view.write("Connection succeeded to " + initialNameDB + "\n");
                } catch (SQLException e) {
                    view.write(String.format("Oops...Cant get connection to current database  in case " + e));
            }
        }
    }



package com.ua.smarterama.andrey.leus.CRM.controller.command.warehouse;

import com.ua.smarterama.andrey.leus.CRM.controller.command.Command;
import com.ua.smarterama.andrey.leus.CRM.model.Configuration;
import com.ua.smarterama.andrey.leus.CRM.model.DataBaseManager;

import java.sql.SQLException;

public class ConnectToDataBaseCRM extends Command {

    Configuration config = new Configuration();


    public ConnectToDataBaseCRM(DataBaseManager manager) {
        super(manager);
    }

    @Override
    public boolean canProcess(String command) {
        return command.equals("connectCRM");
    }

    @Override
    public void process() {

        try {
            manager.connect(config.getDatabaseNameCRM(), config.getUserName(), config.getUserPassword());
            view.write(String.format("Connection succeeded to %s%n", config.getDatabaseNameCRM()));
        } catch (SQLException e) {
            view.write("Oops...Cant get connection to current database  in case " + e);
        }
    }
}



package com.ua.smarterama.andrey.leus.CRM.controller.command;

import com.ua.smarterama.andrey.leus.CRM.model.DataBaseManager;
import com.ua.smarterama.andrey.leus.CRM.view.Console;

import java.sql.SQLException;

public class IsConnected extends Command {
    public IsConnected(DataBaseManager manager, Console view) {
        super(manager, view);
    }

    @Override
    public boolean canProcess(String command) {

        try {
            return !manager.isConnected();
        } catch (SQLException e) {}
        return true;
    }

    @Override
    public void process() {
        view.write("Oops... Please connect to database!");
    }
}

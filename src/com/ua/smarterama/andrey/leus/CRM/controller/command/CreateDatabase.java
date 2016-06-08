package com.ua.smarterama.andrey.leus.CRM.controller.command;

import com.ua.smarterama.andrey.leus.CRM.model.DataBaseManager;

import java.sql.SQLException;


public class CreateDatabase extends Command {

    public CreateDatabase(DataBaseManager manager) {
        super(manager);
    }

    @Override
    public boolean canProcess(String command) {
        return command.equals("create");
    }

    @Override
    public void process() {

        view.write("Please input database name for creating:\n");

        String nameDataBase = view.read();

        if (view.checkExitB(nameDataBase)) {
            view.write("Return to main menu!\n");
            return;
        }

        try {
            manager.createDatabase(nameDataBase);
            view.write("Database " + nameDataBase + " was created\n");
        } catch (SQLException e) {
            view.write(String.format("Create table, error in case - %s\n", e));
        }
    }
}

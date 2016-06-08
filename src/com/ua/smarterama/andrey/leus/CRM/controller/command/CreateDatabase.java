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

        view.write("\nPlease input database name for creating:\n");

        String nameDataBase = view.checkExit(view.read());

        try {
            manager.createDatabase(nameDataBase);
            view.write("\nDatabse " + nameDataBase + " was created");
        } catch (SQLException e) {
            view.write(String.format("Create table, error in case - %s", e));
        }
    }
}

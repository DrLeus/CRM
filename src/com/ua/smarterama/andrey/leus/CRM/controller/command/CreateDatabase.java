package com.ua.smarterama.andrey.leus.CRM.controller.command;

import com.ua.smarterama.andrey.leus.CRM.model.DataBaseManager;
import com.ua.smarterama.andrey.leus.CRM.view.Console;

import java.sql.SQLException;


public class CreateDatabase extends Command {

    public CreateDatabase(DataBaseManager manager, Console view) {
        super(manager, view);
    }

    @Override
    public boolean canProcess(String command) {
        return command.equals("create");
    }

    @Override
    public void process() {

        view.write("Please input database name for creating:\n");

        String nameDataBase = view.read();

        if (view.checkExit(nameDataBase)) {
            view.write("Return to main menu!\n");
            return;
        }

        try {
            manager.createDatabase(nameDataBase);
            view.write(String.format("Database %s was created%n", nameDataBase));
        } catch (SQLException e) {
            view.write(String.format("Create database, error in case - %s%n", e));
        }
    }
}

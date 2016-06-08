package com.ua.smarterama.andrey.leus.CRM.controller.command.tables;

import com.ua.smarterama.andrey.leus.CRM.controller.command.Command;
import com.ua.smarterama.andrey.leus.CRM.model.DataBaseManager;

import java.sql.SQLException;
import java.util.List;

public class CreateTable extends Command {

    public CreateTable(DataBaseManager manager) {
        super(manager);
    }

    @Override
    public boolean canProcess(String command) {
        return false;
    }

    @Override
    public void process() {}

    public void createTable() {
        view.write("Please input table name:\n");

        String tableName = view.read();

        if (view.checkExitB(tableName)) {
            view.write("Return to main menu!\n");
            return;
        }

        view.write("Please input name of columns and type (for ex. TEXT; for column 'name' must be 'name TEXT')\n" +
                "The first column = 'id' with auto-increment\n");

        List<Object> listColumn = Assistant.inputNames(view);

        try {
            manager.createTable(tableName, listColumn);
            view.write("The table " + tableName + " was created! Success!\n");
        } catch (SQLException e) {
            view.write(String.format("Error create table in case - %s\n", e));
        }
    }
}
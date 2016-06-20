package com.ua.smarterama.andrey.leus.CRM.controller.command.tables;

import com.ua.smarterama.andrey.leus.CRM.controller.command.Command;
import com.ua.smarterama.andrey.leus.CRM.model.DataBaseManager;
import com.ua.smarterama.andrey.leus.CRM.view.View;

import java.sql.SQLException;

public class ClearTable extends Command {

    public ClearTable(DataBaseManager manager) {
        super(manager);
    }

    @Override
    public boolean canProcess(String command) {
        return false;
    }

    @Override
    public void process() {}

    public void clearTable() {

        view.write("Please select table\n");

        String tableName = view.selectTable(manager.getTableNames(), view);

        view.write(String.format("Please confirm, do you really want to clear table '%s'? Y/N%n",tableName));

        if (view.read().equalsIgnoreCase("Y")) {
            try {
                manager.clear(tableName);
                view.write(String.format("Table '%s' was cleared! Success!%n", tableName));
            } catch (SQLException e) {
                view.write(String.format("Error clear table in case - %s%n", e));
            }
        } else {
            view.write("Your action canceled!\n");
        }
    }

}

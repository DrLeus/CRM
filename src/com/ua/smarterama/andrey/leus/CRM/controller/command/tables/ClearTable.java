package com.ua.smarterama.andrey.leus.CRM.controller.command.tables;

import com.ua.smarterama.andrey.leus.CRM.controller.command.Command;
import com.ua.smarterama.andrey.leus.CRM.model.DataBaseManager;
import com.ua.smarterama.andrey.leus.CRM.view.Console;

import java.sql.SQLException;

public class ClearTable extends Command {

    public ClearTable(DataBaseManager manager, Console view) {
        super(manager, view);
    }

    public void clearTable() {

        view.write("Please select table\n");

        String tableName = view.selectTable(manager.getTableNames(), view);

        if (tableName.isEmpty()) {
            view.write("Your action canceled!\n");
            return;
        }

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

    @Override
    public boolean canProcess(String command) {
        return false;
    }

    @Override
    public void process() {}

}

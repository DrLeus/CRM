package com.ua.smarterama.andrey.leus.CRM.controller.command.tables;

import com.ua.smarterama.andrey.leus.CRM.controller.command.Command;
import com.ua.smarterama.andrey.leus.CRM.model.DataBaseManager;

import java.sql.SQLException;

public class RemoveTable extends Command {

    public RemoveTable(DataBaseManager manager) {
        super(manager);
    }

    @Override
    public boolean canProcess(String command) {
        return false;
    }

    @Override
    public void process() {}

    public void removeTable() {
        String tableName = view.selectTable(manager.getTableNames(), view);

        view.write("Please confirm, do you really want to remove '" + tableName + "' table? Y/N\n");

        if (view.read().equalsIgnoreCase("Y")) {
            try {
                manager.dropTable(tableName);
                view.write("Table '" + tableName + "'was removed! Success!\n");
            } catch (SQLException e) {
                view.write(String.format("Error remove table in case - %s%n", e));
            }
        } else {
            view.write("Your action canceled!\n");
        }
    }
}

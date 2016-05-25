package com.ua.smarterama.andrey.leus.CRM.controller.command.tables;

import com.ua.smarterama.andrey.leus.CRM.controller.command.Command;
import com.ua.smarterama.andrey.leus.CRM.model.DataBaseManager;
import com.ua.smarterama.andrey.leus.CRM.view.Console;

import java.sql.SQLException;

/**
 * Created by Admin on 25.05.2016.
 */
public class ClearTable extends Command {



    public ClearTable(DataBaseManager manager, Console view) {
        super(manager, view);
    }

    @Override
    public boolean canProcess(String command) {
        return false;
    }

    @Override
    public void process() {

    }

    public void clearTable() {

        view.write("Please select table\n");

        String tableName = manager.selectTable(manager.getTableNames(), view);

        view.write("Please confirm, do you really want to clear table '" + tableName + "'? Y/N");

        if (view.read().equalsIgnoreCase("Y")) {
            try {
                manager.clear(tableName);
                view.write("Table '" + tableName + "' was cleared! Success!");
            } catch (SQLException e) {
                view.write(String.format("Error clear table in case - %s", e));
            }
        } else {
            view.write("Your action canceled!");
        }
    }

}

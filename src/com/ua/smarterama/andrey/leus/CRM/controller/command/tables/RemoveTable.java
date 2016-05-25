package com.ua.smarterama.andrey.leus.CRM.controller.command.tables;

import com.ua.smarterama.andrey.leus.CRM.controller.command.Command;
import com.ua.smarterama.andrey.leus.CRM.model.DataBaseManager;
import com.ua.smarterama.andrey.leus.CRM.view.Console;

/**
 * Created by Admin on 25.05.2016.
 */
public class RemoveTable extends Command {



    public RemoveTable(DataBaseManager manager, Console view) {
        super(manager, view);
    }

    @Override
    public boolean canProcess(String command) {
        return false;
    }

    @Override
    public void process() {

    }

    public void removeTable() {
        String tableName = manager.selectTable(manager.getTableNames(), view);

        view.write("Please confirm, do you really want to remove '" + tableName + "' table? Y/N");

        if (view.read().equalsIgnoreCase("Y")) {
            manager.dropTable(tableName);
        } else {
            view.write("Your action canceled!");
        }
    }
}

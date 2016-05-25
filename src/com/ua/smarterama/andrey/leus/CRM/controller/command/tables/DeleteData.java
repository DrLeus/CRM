package com.ua.smarterama.andrey.leus.CRM.controller.command.tables;

import com.ua.smarterama.andrey.leus.CRM.controller.command.Command;
import com.ua.smarterama.andrey.leus.CRM.model.DataBaseManager;
import com.ua.smarterama.andrey.leus.CRM.view.Console;

import java.util.List;
import java.util.MissingFormatArgumentException;

/**
 * Created by Admin on 25.05.2016.
 */
public class DeleteData extends Command {

    public DeleteData(DataBaseManager manager, Console view) {
        super(manager, view);
    }

    @Override
    public boolean canProcess(String command) {
        return false;
    }

    @Override
    public void process() {

    }


    public void delete() {
        String tableName = manager.selectTable(manager.getTableNames(), view);

        manager.outputColumnNames(manager.getColumnNames(tableName, ""), manager.getFormatedLine(manager.getColumnNames(tableName, ""), manager.getTableData(tableName, "")), view); // TODO duplicate getTableData

        manager.outputData(manager.getColumnNames(tableName, ""), manager.getTableData(tableName, ""), manager.getFormatedLine(manager.getColumnNames(tableName, ""), manager.getTableData(tableName, "")), view); //TODO duplicate getTableData


        while (true) {
            try {
                view.write("Please input 'id' line to delete\n");

                int input = Integer.parseInt(view.checkExit(view.read()));

                view.write("Please confirm, do you really want to remove position id='" + input + "'? Y/N");

                if (view.read().equalsIgnoreCase("Y")) {
                    manager.delete(input, tableName, view);
                    view.write("Id '" + input + "' removed");
                } else {
                    view.write("Your action canceled!");
                    break;
                }
                break;
            } catch (NumberFormatException e) {
                view.write("Incorrect input, try again");
            }
        }
    }


}

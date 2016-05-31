package com.ua.smarterama.andrey.leus.CRM.controller.command.tables;

import com.ua.smarterama.andrey.leus.CRM.controller.command.Command;
import com.ua.smarterama.andrey.leus.CRM.model.DataBaseManager;
import com.ua.smarterama.andrey.leus.CRM.view.Console;
import com.ua.smarterama.andrey.leus.CRM.view.View;

import java.sql.SQLException;
import java.util.List;
import java.util.MissingFormatArgumentException;


public class DeleteData extends Command {

    public DeleteData(DataBaseManager manager, View view) {
        super(manager, view);
    }

    @Override
    public boolean canProcess(String command) {
        return false;
    }

    @Override
    public void process() {}


    public void delete() {
        String tableName = Assistant.selectTable(manager.getTableNames(), view);

        try {
            Assistant.outputColumnNames(manager.getColumnNames(tableName, ""), Assistant.getFormatedLine(manager.getColumnNames(tableName, ""), manager.getTableData(tableName, "")), view); // TODO duplicate getTableData
            Assistant.outputData(manager.getColumnNames(tableName, ""), manager.getTableData(tableName, ""), Assistant.getFormatedLine(manager.getColumnNames(tableName, ""), manager.getTableData(tableName, "")), view); //TODO duplicate getTableData
        } catch (SQLException e) {
            view.write(String.format("Error in case - %s", e));
        }

        while (true) {
            try {
                view.write("Please input 'id' line to delete\n");

                int input = Integer.parseInt(view.checkExit(view.read()));

                view.write("Please confirm, do you really want to remove position id='" + input + "'? Y/N");

                if (view.read().equalsIgnoreCase("Y")) {
                    try {
                        manager.delete(input, tableName);
                        view.write("Id '" + input + "' removed");
                    } catch (SQLException e) {
                        view.write(String.format("Error delete data in case - %s", e));
                    }
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

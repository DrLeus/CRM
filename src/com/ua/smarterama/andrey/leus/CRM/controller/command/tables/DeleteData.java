package com.ua.smarterama.andrey.leus.CRM.controller.command.tables;

import com.ua.smarterama.andrey.leus.CRM.controller.command.Command;
import com.ua.smarterama.andrey.leus.CRM.model.DataBaseManager;

import java.sql.SQLException;

public class DeleteData extends Command {

    public DeleteData(DataBaseManager manager) {
        super(manager);
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
            Assistant.outputColumnNames(manager.getColumnNames(tableName, ""), Assistant.getFormatedLine(manager.getColumnNames(tableName, ""), manager.getTableData(tableName, ""))); // TODO duplicate getTableData
            Assistant.outputData(manager.getColumnNames(tableName, ""), manager.getTableData(tableName, ""), Assistant.getFormatedLine(manager.getColumnNames(tableName, ""), manager.getTableData(tableName, ""))); //TODO duplicate getTableData
        } catch (SQLException e) {
            view.write(String.format("Error in case - %s\n", e));
        }

        while (true) {
            try {
                view.write("Please input 'id' line to delete\n");

                String line = view.read();

                if (view.checkExitB(line)) {
                    view.write("Return to main menu!\n");
                    return;
                }

                int input = Integer.parseInt(line);

                view.write("Please confirm, do you really want to remove position id='" + input + "'? Y/N\n");

                if (view.read().equalsIgnoreCase("Y")) {
                    try {
                        manager.delete(input, tableName);
                        view.write("Id '" + input + "' removed\n");
                    } catch (SQLException e) {
                        view.write(String.format("Error delete data in case - %s\n", e));
                    }
                } else {
                    view.write("Your action canceled!\n");
                    break;
                }
                break;
            } catch (NumberFormatException e) {
                view.write("Incorrect input, try again\n");
            }
        }
    }


}

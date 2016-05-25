package com.ua.smarterama.andrey.leus.CRM.controller.command.tables;

import com.ua.smarterama.andrey.leus.CRM.controller.command.Command;
import com.ua.smarterama.andrey.leus.CRM.model.DataBaseManager;
import com.ua.smarterama.andrey.leus.CRM.view.Console;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.MissingFormatArgumentException;

public class UpdateDate extends Command {

    public UpdateDate(DataBaseManager manager, Console view) {
        super(manager, view);
    }

    @Override
    public boolean canProcess(String command) {
        return false;
    }

    @Override
    public void process() {

    }


    public void update() {

        String tableName = Assistant.selectTable(manager.getTableNames(),view);

        List<Object> columnNames = manager.getColumnNames(tableName, "");

        Assistant.outputColumnNames(columnNames, Assistant.getFormatedLine(columnNames, manager.getTableData(tableName, "")), view); // TODO duplicate getTableData

        Assistant.outputData(columnNames, manager.getTableData(tableName, ""), Assistant.getFormatedLine(columnNames, manager.getTableData(tableName, "")), view); //TODO duplicate getTableData

        int id;

        while (true) {
            try {
                view.write("\nPlease select row id to update: ");
                id = Integer.parseInt(view.checkExit(view.read()));
                break;
            } catch (NumberFormatException e) {
                view.write("Incorrect input, try again");
            }
        }

        List<Object> list = new ArrayList<>();

        for (int i = 1; i < columnNames.size(); i++) {

            view.write("Please input data for column '" + columnNames.get(i) + "'\n");

            String input = view.checkExit(view.read());

            list.add(input);
        }

        try {
            manager.update(tableName, columnNames, id, list);
            view.write("\nThe row was updated! Success!");
        } catch (SQLException e) {
            view.write(String.format("Error update data in case - %s",  e));
        }


    }


}

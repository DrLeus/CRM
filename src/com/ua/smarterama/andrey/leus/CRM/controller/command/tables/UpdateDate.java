package com.ua.smarterama.andrey.leus.CRM.controller.command.tables;

import com.ua.smarterama.andrey.leus.CRM.controller.command.Command;
import com.ua.smarterama.andrey.leus.CRM.model.DataBaseManager;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UpdateDate extends Command {

    public UpdateDate(DataBaseManager manager) {
        super(manager);
    }

    @Override
    public boolean canProcess(String command) {
        return false;
    }

    @Override
    public void process() {}


    public void update() {

        String tableName = view.selectTable(manager.getTableNames(),view);

        if (tableName.isEmpty()) {
            view.write("Your action canceled!\n");
            return;
        }

        List<Object> columnNames = null;
        try {
            columnNames = manager.getColumnNames(tableName, "");
        } catch (SQLException e) {
            view.write(String.format("Error get column names in case - %s%n", e));
        }

        try {
            view.outputColumnNames(columnNames, view.getFormatedLine(columnNames, manager.getTableData(tableName, ""))); // TODO duplicate getTableData
            view.outputData(columnNames, manager.getTableData(tableName, ""), view.getFormatedLine(columnNames, manager.getTableData(tableName, ""))); //TODO duplicate getTableData
        } catch (SQLException e) {
            view.write(String.format("Error get table data in case - %s%n", e));
        }

        int id;

        while (true) {
            try {
                view.write("\n");
                view.write("Please select row id to update:\n");

                String input = view.read();

                if (view.checkExit(input)) {
                    view.write("Return to main menu!\n");
                    return;
                }

                id = Integer.parseInt(input);
                break;
            } catch (NumberFormatException e) {
                view.write("Incorrect input, try again\n");
            }
        }

        List<Object> list = new ArrayList<>();

        for (int i = 1; i < columnNames.size(); i++) {

            view.write(String.format("Please input data for column '%s'%n", columnNames.get(i)));

            String input = view.read();

            if (view.checkExit(input)) {
                view.write("Return to main menu!\n");
                return;
            }

//            if( input.isEmpty()) {
//
//                try {
//                    list.add(manager.getTableData(tableName, "SELECT " + columnNames.get(i) + " FROM " + tableName + " " +
//                            "WHERE id =" + new BigDecimal(id)));
//                } catch (SQLException e) {
//                    view.write(String.format("Error get table data in case - %s", e));
//                }
//
//            } else {
                list.add(input);
//            }
// TODO fix input with empty input
        }

        try {
            manager.update(tableName, columnNames, id, list);
            view.write("The row was updated! Success!\n");
        } catch (SQLException e) {
            view.write(String.format("Error update data in case - %s%n",  e));
        }
    }
}

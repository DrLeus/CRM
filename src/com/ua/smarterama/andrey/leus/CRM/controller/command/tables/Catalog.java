package com.ua.smarterama.andrey.leus.CRM.controller.command.tables;

import com.ua.smarterama.andrey.leus.CRM.controller.command.*;
import com.ua.smarterama.andrey.leus.CRM.model.DataBaseManager;
import com.ua.smarterama.andrey.leus.CRM.view.View;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.MissingFormatArgumentException;

public class Catalog extends Command {


    @Override
    public boolean canProcess(String command) {
        return command.equals("catalog");
    }

    @Override
    public void process() {

        while (true) {
            try {
                view.write("\nAvalable operations:\n" +
                        "1. Get table data\n" +
                        "2. Insert data (position)\n" +
                        "3. Update data (position)\n" +
                        "4. Delete data (position)\n" +
                        "5. Create table\n" +
                        "6. Remove table\n" +
                        "7. Clear table\n" +
                        "8. Return to main menu\n");

                view.write("\nPlease select operation:\n");

                String input = view.checkExit(view.read());

                if (Integer.parseInt(input) > 8 || Integer.parseInt(input) < 1) {
                    view.write("Incorrect input, try again");
                } else {
                    switch (Integer.parseInt(input)) {
                        case 1:
                            GetTable table = new GetTable(manager, view);
                            table.getTableData();
                            break;
                        case 2:
                            insert();
                            break;
                        case 3:
                            update();
                            break;
                        case 4:
                            delete();
                            break;
                        case 5:
                            createTable();
                            break;
                        case 6:
                            removeTable();
                            break;
                        case 7:
                            clearTable();
                            break;
                        case 8:
                            view.checkExit("exit");
                    }
                }
            } catch (NumberFormatException e) {
                view.write("Incorrect input, try again");
            }
        }
    }


    private void insert() {

        view.write("Please select table\n");

        String tableName = manager.selectTable(manager.getTableNames());

        List<Object> listColumnName = manager.getColumnNames(tableName, "");

        List<Object> list = new ArrayList<>();

        for (int i = 1; i < listColumnName.size(); i++) {

            view.write("Please input data for column '" + listColumnName.get(i) + "'\n");

            String input = view.checkExit(view.read());

            list.add(input);
        }

        manager.insert(tableName, list, view);
    }

    private void update() {

        String tableName = manager.selectTable(manager.getTableNames());

        List<Object> columnNames = manager.getColumnNames(tableName, "");

        outputColumnNames(columnNames, manager.getFormatedLine(columnNames, manager.getTableData(tableName, ""))); // TODO duplicate getTableData

        outputData(columnNames, manager.getTableData(tableName, ""), manager.getFormatedLine(columnNames, manager.getTableData(tableName, ""))); //TODO duplicate getTableData

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

        manager.update(tableName, columnNames, id, list, view);

    }

    private void delete() {
        String tableName = manager.selectTable(manager.getTableNames());

        outputColumnNames(manager.getColumnNames(tableName, ""), manager.getFormatedLine(manager.getColumnNames(tableName, ""), manager.getTableData(tableName, ""))); // TODO duplicate getTableData

        outputData(manager.getColumnNames(tableName, ""), manager.getTableData(tableName, ""), manager.getFormatedLine(manager.getColumnNames(tableName, ""), manager.getTableData(tableName, ""))); //TODO duplicate getTableData


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

    private void createTable() {
        view.write("\nPlease input table name:\n");

        String input = view.checkExit(view.read());

        manager.createTable(input, view);
    }

    private void removeTable() {
        String tableName = manager.selectTable(manager.getTableNames());

        view.write("Please confirm, do you really want to remove '" + tableName + "' table? Y/N");

        if (view.read().equalsIgnoreCase("Y")) {
            manager.dropTable(tableName);
        } else {
            view.write("Your action canceled!");
        }
    }

    private void clearTable() {

        view.write("Please select table\n");

        String tableName = manager.selectTable(manager.getTableNames());

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

    private void outputData(List<Object> listColumnName, List<Object> listValue, String result) {
        try {
            do {
                outputColumnNames(listValue, result);
                for (int i = 0; i < listColumnName.size(); i++) {
                    listValue.remove(0);
                }

            } while (listValue.size() != 0);
        } catch (MissingFormatArgumentException e) { //TODO when table is empty, getTable show error
            view.write("\nThe table is empty!");
        }

    }

    private void outputColumnNames(List<Object> listColumnName, String result) {
        view.write(String.format(result, listColumnName.toArray()));
    }



}

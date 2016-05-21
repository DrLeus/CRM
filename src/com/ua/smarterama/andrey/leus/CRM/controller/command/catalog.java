package com.ua.smarterama.andrey.leus.CRM.controller.command;

import com.ua.smarterama.andrey.leus.CRM.model.DataBaseManager;
import com.ua.smarterama.andrey.leus.CRM.view.View;

import java.util.ArrayList;
import java.util.List;
import java.util.MissingFormatArgumentException;

public class Catalog extends Command {


    public Catalog(DataBaseManager manager, View view) {
        super(manager, view);
    }

    @Override
    public boolean canProcess(String command) {
        return command.equals("catalog");
    }

    @Override
    public void process() {

        while (true) {
            try{
                view.write("\nAvalable operations:\n" +
                        "1. Get table data\n" +
                        "2. Insert data (position)\n" +
                        "3. Update data (position)\n" +
                        "4. Delete data (position)\n" +
                        "5. Create table\n" +
                        "6. Remove table\n" +
                        "7. Clear table\n");

                view.write("\nPlease select operation:\n");

                String input = view.checkExit(view.read());

                if ( Integer.parseInt(input) > 7 || Integer.parseInt(input) < 1 ) {
                    view.write("Incorrect input, try again");
                } else {
                    switch (Integer.parseInt(input)){
                        case 1: getTableData();
                            break;
                        case 2: insert();
                            break;
                        case 3: update();
                            break;
                        case 4: delete();
                            break;
                        case 5: createTable();
                            break;
                        case 6: removeTable();
                            break;
                        case 7: clearTable();
                            break;
                    }
                }
            } catch (NumberFormatException e) {
                view.write("Incorrect input, try again");
            }
        }
    }

    private void getTableData() {
        String tableName = selectTable();

        List<Object> listColumnName = manager.getColumnNames(tableName);

        List<Object> listValue = manager.getTableData(tableName);

        String format = getFormat(listColumnName, listValue);

        outputColumnNames(listColumnName, format);

        outputData(listColumnName, listValue, format);
    }

    private void insert() {

        view.write("Please select table\n");

        String tableName = selectTable();

        List<Object> listColumnName = manager.getColumnNames(tableName);

        List<Object> list = new ArrayList<>();

        for (int i = 1; i <listColumnName.size() ; i++) {

            view.write("Please input data for column '" + listColumnName.get(i) + "'\n");

            String input = view.checkExit(view.read());

            list.add(input);
        }

        manager.insert(tableName, list, view);
    }

    private void update() {

        String tableName = selectTable();

        List<Object> columnNames = manager.getColumnNames(tableName);

        outputColumnNames(columnNames, getFormat(columnNames, manager.getTableData(tableName))); // TODO duplicate getTableData

        outputData(columnNames, manager.getTableData(tableName), getFormat(columnNames, manager.getTableData(tableName))); //TODO duplicate getTableData

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

        for (int i = 1; i <columnNames.size() ; i++) {

            view.write("Please input data for column '" + columnNames.get(i) + "'\n");

            String input = view.checkExit(view.read());

            list.add(input);
        }

        manager.update(tableName, columnNames, id, list, view);

    }

    private void delete() {
        String tableName = selectTable();

        outputColumnNames(manager.getColumnNames(tableName), getFormat(manager.getColumnNames(tableName), manager.getTableData(tableName))); // TODO duplicate getTableData

        outputData(manager.getColumnNames(tableName), manager.getTableData(tableName), getFormat(manager.getColumnNames(tableName), manager.getTableData(tableName))); //TODO duplicate getTableData


        while (true) {
            try {
                view.write("Please input 'id' line to delete\n");

                int input = Integer.parseInt(view.checkExit(view.read()));

                view.write("Please confirm, do you really want to remove position id='" + input + "'? Y/N");

                if (view.read().equalsIgnoreCase("Y")) {
                    manager.delete(input, tableName, view);
                    view.write("Id '" + input +"' removed");
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
        String tableName = selectTable();

        view.write("Please confirm, do you really want to remove '" + tableName + "' table? Y/N");

        if (view.read().equalsIgnoreCase("Y")) {
            manager.dropTable(tableName);
            view.write("Table '" + tableName +"' removed");
        } else {
            view.write("Your action canceled!");
        }
    }

    private void clearTable() {

        view.write("Please select table\n");

        String tableName = selectTable();

        view.write("Please confirm, do you really want to clear table '" + tableName+ "'? Y/N");

        if (view.read().equalsIgnoreCase("Y")) {
            manager.clear(tableName);
            view.write("Table '" + tableName +"' cleared");
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

    private String getFormat(List<Object> listColumnName, List<Object> listValue) {
        String result = "";

        for (int i = 0; i < listColumnName.size(); i++) {
            result += "%-" + getWidthColumn(i, listColumnName, listValue) + "s";
        }
        result += "%n";
        return result;
    }

    private String selectTable() {
        List<String> tables = manager.getTableNames();
        String tableName = null;

        int numberTable = 0;

        view.write("\n Database has next tables: \n");

        for (String sert: tables) {
            view.write("" + ++numberTable + ": " + sert);
        }

        while (true) {
            try{
                view.write("\nPlease select number of table:\n");

                String input = view.checkExit(view.read());

                if ( Integer.parseInt(input) > numberTable || Integer.parseInt(input) < 1 ) {
                    view.write("Incorrect input, try again");
                } else {
                    tableName = tables.get(Integer.parseInt(input)-1);
                    break;
                }
            } catch (NumberFormatException e) {
                view.write("Incorrect input, try again");
            }
        }
        return tableName;
    }

    private int getWidthColumn(int i, List<Object> listColumnName, List<Object> listValue) {

        int result = (String.valueOf(listColumnName.get(i))).length();

        int qtyLine = listValue.size()/listColumnName.size();

        for (int j = 0; j < qtyLine; j++) {
            if (result < (String.valueOf(listValue.get(i+(listColumnName.size()*j)))).length()) {
                result = (String.valueOf(listValue.get(i+(listColumnName.size()*j)))).length();            }
        }
        return result + 2;
    }


}

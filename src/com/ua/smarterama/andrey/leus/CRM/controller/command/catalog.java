package com.ua.smarterama.andrey.leus.CRM.controller.command;

import com.ua.smarterama.andrey.leus.CRM.model.DataBaseManager;
import com.ua.smarterama.andrey.leus.CRM.view.View;

import java.util.List;

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

        view.write("Avalable operations:\n" +
                "1. Get table data\n" +
                "2. Add position\n" +
                "3. Update position\n" +
                "4. Delete position\n" +
                "5. Create table\n" +
                "6. Remove table\n");

        while (true) {
            try{
                view.write("\nPlease select operation:\n");

                String input = view.checkExit(view.read());

                if ( Integer.parseInt(input) > 6 || Integer.parseInt(input) < 1 ) {
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
                    }
                }
            } catch (NumberFormatException e) {
                view.write("Incorrect input, try again");
            }
        }
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

    private void createTable() {
        view.write("\nPlease input table name:\n");

        String input = view.checkExit(view.read());

        manager.createTable(input, view);
    }

    private void delete() {

    }

    private void update() {
    }

    private void insert() {
    }

    private void getTableData() {
        String tableName = selectTable();

        List<Object> listColumnName = manager.getColumnNames(tableName);

        List<Object> listValue = manager.getTableData(tableName);

        String format = getFormat(listColumnName, listValue);

        outputColumnNames(listColumnName, format);

        outputData(listColumnName, listValue, format);
    }

    private void outputData(List<Object> listColumnName, List<Object> listValue, String result) {
        do {
            outputColumnNames(listValue, result);
            for (int i = 0; i < listColumnName.size(); i++) {
                listValue.remove(0);
            }

        } while (listValue.size() != 0);
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

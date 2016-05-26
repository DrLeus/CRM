package com.ua.smarterama.andrey.leus.CRM.controller.command.tables;

import com.ua.smarterama.andrey.leus.CRM.controller.command.Command;
import com.ua.smarterama.andrey.leus.CRM.model.DataBaseManager;
import com.ua.smarterama.andrey.leus.CRM.view.Console;
import com.ua.smarterama.andrey.leus.CRM.view.View;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class InsertData extends Command {

    public InsertData(DataBaseManager manager, View view) {
        super(manager, view);
    }


    public void insertData () {

        view.write("Please select table\n");

        String tableName = Assistant.selectTable(manager.getTableNames(), view);

        List<Object> listColumnName = null;
        List<Object> columnTable = null;
        List<Object> list = null;


        try {
            listColumnName = manager.getColumnNames(tableName, "");

            list = new ArrayList<>();

            for (int i = 1; i < listColumnName.size(); i++) {

                view.write("Please input data for column '" + listColumnName.get(i) + "'\n");

                String input = view.checkExit(view.read());

                list.add(input);
            }

            columnTable = manager.getColumnNames(tableName, "");


        } catch (SQLException e) {
            view.write(String.format("Error get column names in case - %s", e));
        }

        String columns = " (";
        for (int i = 1; i < columnTable.size(); i++) {
            columns += columnTable.get(i) + ",";
        }
        columns = columns.substring(0, columns.length() - 1) + ")";


        String data = " (";
        for (int i = 0; i < list.size(); i++) {
            data += "'" + list.get(i) + "',";
        }
        data = data.substring(0, data.length() - 1) + ")";

        try {
            manager.insert(tableName, columns, data);
            view.write("\nThe row was created! Success!");
        } catch (SQLException e) {
            view.write(String.format("Error insert data in case - %s", e));
        }

    }


    @Override
    public boolean canProcess(String command) {
        return false;
    }

    @Override
    public void process() {}
}

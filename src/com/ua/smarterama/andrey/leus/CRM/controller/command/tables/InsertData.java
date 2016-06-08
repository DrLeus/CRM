package com.ua.smarterama.andrey.leus.CRM.controller.command.tables;

import com.ua.smarterama.andrey.leus.CRM.controller.command.Command;
import com.ua.smarterama.andrey.leus.CRM.model.DataBaseManager;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class InsertData extends Command {

    public InsertData(DataBaseManager manager) {
        super(manager);
    }

    public void insertData() {

        view.write("Please select table\n");

        String tableName = Assistant.selectTable(manager.getTableNames(), view);

        List<Object> listColumnName;
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
            view.write(String.format("Error get column names in case - %s\n", e));
        }


        try {
            manager.insert(tableName, columnTable, list);
            view.write("The row was created! Success!\n");
        } catch (SQLException e) {
            view.write(String.format("Error insert data in case - %s\n", e));
        }

    }


    @Override
    public boolean canProcess(String command) {
        return false;
    }

    @Override
    public void process() {
    }
}

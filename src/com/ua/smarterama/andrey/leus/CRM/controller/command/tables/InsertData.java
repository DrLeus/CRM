package com.ua.smarterama.andrey.leus.CRM.controller.command.tables;

import com.ua.smarterama.andrey.leus.CRM.controller.command.Command;
import com.ua.smarterama.andrey.leus.CRM.model.DataBaseManager;
import com.ua.smarterama.andrey.leus.CRM.view.Console;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Admin on 25.05.2016.
 */
public class InsertData extends Command {

    public InsertData(DataBaseManager manager, Console view) {
        super(manager, view);
    }


    public void insertData () {

        view.write("Please select table\n");

        String tableName = manager.selectTable(manager.getTableNames(), view);

        List<Object> listColumnName = manager.getColumnNames(tableName, "");

        List<Object> list = new ArrayList<>();

        for (int i = 1; i < listColumnName.size(); i++) {

            view.write("Please input data for column '" + listColumnName.get(i) + "'\n");

            String input = view.checkExit(view.read());

            list.add(input);
        }

        manager.insert(tableName, list, view);
    }





    @Override
    public boolean canProcess(String command) {
        return false;
    }

    @Override
    public void process() {}
}

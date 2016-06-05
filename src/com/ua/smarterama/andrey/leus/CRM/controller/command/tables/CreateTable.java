package com.ua.smarterama.andrey.leus.CRM.controller.command.tables;

import com.ua.smarterama.andrey.leus.CRM.controller.command.Command;
import com.ua.smarterama.andrey.leus.CRM.model.DataBaseManager;
import com.ua.smarterama.andrey.leus.CRM.view.View;

import java.sql.SQLException;
import java.util.List;

public class CreateTable extends Command {



    public CreateTable(DataBaseManager manager, View view) {
        super(manager, view);
    }

    @Override
    public boolean canProcess(String command) {
        return false;
    }

    @Override
    public void process() {}

    public void createTable() {
        view.write("Please input table name:\n");

        String tableName = view.checkExit(view.read());

        view.write("Please input name of columns\n" +
                "The first column = 'id' with auto-increment\n");

        List<Object> listColumn = Assistant.inputNames(view);


        try {
            manager.createTable(tableName, listColumn);
            view.write("The table " + tableName + " was created! Success!\n");
        } catch (SQLException e) {
            view.write(String.format("Error create table in case - %s\n", e));
        }
    }
}
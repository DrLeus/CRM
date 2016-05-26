package com.ua.smarterama.andrey.leus.CRM.controller.command.tables;

import com.ua.smarterama.andrey.leus.CRM.controller.command.Command;
import com.ua.smarterama.andrey.leus.CRM.model.DataBaseManager;
import com.ua.smarterama.andrey.leus.CRM.view.Console;
import com.ua.smarterama.andrey.leus.CRM.view.View;

import java.sql.SQLException;
import java.util.List;
import java.util.MissingFormatArgumentException;

/**
 * Created by Admin on 24.05.2016.
 */
public class GetTable extends Command {

    public GetTable(DataBaseManager manager, View view) {
        super(manager, view);
    }

    @Override
    public boolean canProcess(String command) {
        return false;
    }

    @Override
    public void process() {
    }


    public void getTableData() {

        String tableName = Assistant.selectTable(manager.getTableNames(), view);

        List<Object> listColumnName = null;
        try {
            listColumnName = manager.getColumnNames(tableName, "");
        } catch (SQLException e) {
            view.write(String.format("Error get column names in case - %s", e));
        }

        List<Object> listValue = null;
        try {
            listValue = manager.getTableData(tableName, "");
        } catch (SQLException e) {
            view.write(String.format("Error get table data in case - %s", e));
        }

        String format = Assistant.getFormatedLine(listColumnName, listValue);

        Assistant.outputColumnNames(listColumnName, format, view);

        Assistant.outputData(listColumnName, listValue, format, view);
    }

}

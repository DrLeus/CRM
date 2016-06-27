package com.ua.smarterama.andrey.leus.CRM.controller.command.tables;

import com.ua.smarterama.andrey.leus.CRM.controller.command.Command;
import com.ua.smarterama.andrey.leus.CRM.model.DataBaseManager;

import java.sql.SQLException;
import java.util.List;

public class GetTable extends Command {

    public GetTable(DataBaseManager manager) {
        super(manager);
    }

    @Override
    public boolean canProcess(String command) {
        return false;
    }

    @Override
    public void process() {}

    public void getTableData() {

        String tableName = view.selectTable(manager.getTableNames(), view);

        if (tableName.isEmpty()) {
            view.write("Your action canceled!\n");
            return;
        }

        List<Object> listColumnName = null;
        try {
            listColumnName = manager.getColumnNames(tableName, "");
        } catch (SQLException e) {
            view.write(String.format("Error get column names in case - %s%n", e));
        }

        List<Object> listValue = null;
        try {
            listValue = manager.getTableData(tableName, "");
        } catch (SQLException e) {
            view.write(String.format("Error get table data in case - %s%n", e));
        }

        String format = view.getFormatedLine(listColumnName, listValue);

        view.outputColumnNames(listColumnName, format);

        view.outputData(listColumnName, listValue, format);
    }
}

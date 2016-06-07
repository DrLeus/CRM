package com.ua.smarterama.andrey.leus.CRM.controller.command.warehouse;

import com.ua.smarterama.andrey.leus.CRM.controller.command.Command;
import com.ua.smarterama.andrey.leus.CRM.controller.command.tables.Assistant;
import com.ua.smarterama.andrey.leus.CRM.model.DataBaseManager;
import com.ua.smarterama.andrey.leus.CRM.view.View;

import java.sql.SQLException;
import java.util.List;
import java.util.MissingFormatArgumentException;

public class report extends Command {

    public report(DataBaseManager manager, View view) {
        super(manager, view);
    }

    @Override
    public boolean canProcess(String command) {
        return command.equals("Report");
    }

    @Override
    public void process() {

        view.write("\nThe warehouse contains:\n");

        String sql = "SELECT goods.id, code, name, quantity FROM goods, stockbalance WHERE goods.id = stockbalance.id_goods";

        List<Object> listValue = null;
        try {
            listValue = manager.getTableData("", sql);
        } catch (SQLException e) {
            view.write(String.format("Error get table data in case - %s", e));
        }

        List<Object> listColumnName = null;
        try {
            listColumnName = manager.getColumnNames("", sql);
        } catch (SQLException e) {
            view.write(String.format("Error get column names in case - %s", e));
        }

        String format = Assistant.getFormatedLine(listColumnName, listValue);

        outputColumnNames(listColumnName, format);

        outputData(listColumnName, listValue, format);
    }

    private void outputColumnNames(List<Object> listColumnName, String result) {
        view.write(String.format(result, listColumnName.toArray()));
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

}
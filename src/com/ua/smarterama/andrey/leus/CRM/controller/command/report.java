package com.ua.smarterama.andrey.leus.CRM.controller.command;

import com.ua.smarterama.andrey.leus.CRM.controller.command.tables.Assistant;
import com.ua.smarterama.andrey.leus.CRM.model.DataBaseManager;
import com.ua.smarterama.andrey.leus.CRM.view.Console;

import java.util.List;
import java.util.MissingFormatArgumentException;

public class Report extends Command {

    public Report(DataBaseManager manager, Console view) {
        super(manager, view);
    }

    @Override
    public boolean canProcess(String command) {
        return command.equals("report");
    }

    @Override
    public void process() {

        view.write("\nThe warehouse contains:\n");

        String sql = "SELECT goods.id, code, name, quantity FROM goods, stockbalance WHERE goods.id = stockbalance.id_goods";

        List<Object> listValue = manager.getTableData("", sql);

        List<Object> listColumnName = manager.getColumnNames("", sql);

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
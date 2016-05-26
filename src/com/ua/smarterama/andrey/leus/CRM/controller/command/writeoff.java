package com.ua.smarterama.andrey.leus.CRM.controller.command;

import com.ua.smarterama.andrey.leus.CRM.controller.command.tables.Assistant;
import com.ua.smarterama.andrey.leus.CRM.model.DataBaseManager;
import com.ua.smarterama.andrey.leus.CRM.view.Console;
import com.ua.smarterama.andrey.leus.CRM.view.View;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Writeoff extends Command {

    public Writeoff(DataBaseManager manager, View view) {
        super(manager, view);
    }

    @Override
    public boolean canProcess(String command) {
        return command.equals("writeoff");
    }

    @Override
    public void process() {

        Report report = new Report(manager, view);

        report.process();

        String tableName = "stockbalance";


        List<Object> list = Assistant.selectGoodsAndQty(view);

        String sql = "SELECT * FROM " + tableName + " WHERE id_goods=" + list.get(1);

        List<Object> currentValue = null;
        try {
            currentValue = manager.getTableData("", sql);
        } catch (SQLException e) {
            view.write(String.format("Error get table data in case - %s", e));
        }

        Integer newValueGoods = Integer.parseInt(String.valueOf(list.get(0)));

        Integer currentValueGoods = Integer.parseInt(String.valueOf(currentValue.get(1)));

        if (newValueGoods.equals(currentValueGoods)) {

            int id = (new BigDecimal(String.valueOf(currentValue.get(0)))).intValue();
            try {
                manager.delete(id, tableName);
            } catch (SQLException e) {
                view.write(String.format("Error delete data in case - %s", e));
            }
        } else if (newValueGoods < currentValueGoods) {

            list.set(0, currentValueGoods - newValueGoods);

            int id = (new BigDecimal(String.valueOf(currentValue.get(0)))).intValue();

            try {
                manager.update(tableName, manager.getColumnNames(tableName, ""), id, list);
                view.write("The goods was wrote off! Success!\n");
            } catch (SQLException e) {
                view.write(String.format("Error update data in case - %s", e));
            }
        } else {
            view.write("\n Oops...The quantity of goods on warehaus less thsn you want to writeoff!\n");
        }
    }
}

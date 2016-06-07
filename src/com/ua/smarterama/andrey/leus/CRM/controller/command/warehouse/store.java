package com.ua.smarterama.andrey.leus.CRM.controller.command.warehouse;

import com.ua.smarterama.andrey.leus.CRM.controller.command.Command;
import com.ua.smarterama.andrey.leus.CRM.controller.command.tables.Assistant;
import com.ua.smarterama.andrey.leus.CRM.model.DataBaseManager;
import com.ua.smarterama.andrey.leus.CRM.view.Console;
import com.ua.smarterama.andrey.leus.CRM.view.View;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class store extends Command {

    public store(DataBaseManager manager, View view) {
        super(manager, view);
    }

    @Override
    public boolean canProcess(String command) {
        return command.equals("Store");
    }

    @Override
    public void process() {

        String tableName = "stockbalance";

        List<Object> list = Assistant.selectGoodsAndQty(view);


        String sql = "SELECT * FROM " + tableName + " WHERE id_goods=" + list.get(1);

        try {
            List<Object> currentValue = manager.getTableData("", sql);

            list.set(0, (Integer.parseInt(String.valueOf(list.get(0))) + Integer.parseInt(String.valueOf(currentValue.get(1)))));

            int id = (new BigDecimal(String.valueOf(currentValue.get(0)))).intValue();

            manager.update(tableName, manager.getColumnNames(tableName, ""), id, list);

            view.write("The goods was added! Success!\n");

        } catch (IndexOutOfBoundsException e) {//TODO fix it
            try {
                manager.insert(tableName, manager.getColumnNames(tableName,""), list);
                view.write("The goods was added! Success!\n");
            } catch (SQLException e1) {
                view.write(String.format("Error get column names in case - %s",  e1));
            }
        } catch (SQLException e) {
            view.write(String.format("Error update data in case - %s",  e));

        }
    }
}
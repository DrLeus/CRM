package com.ua.smarterama.andrey.leus.CRM.controller.command;

import com.ua.smarterama.andrey.leus.CRM.model.DataBaseManager;
import com.ua.smarterama.andrey.leus.CRM.view.Console;
import com.ua.smarterama.andrey.leus.CRM.view.View;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Store extends Command {

    public Store(DataBaseManager manager, Console view) {
        super(manager, view);
    }

    @Override
    public boolean canProcess(String command) {
        return command.equals("store");
    }

    @Override
    public void process() {

        List<Object> list = new ArrayList<>();

        String tableName = "stockbalance";

        list.add(0, "");

        while (true) {
            try {
                view.write("\nPlease input id of goods:\n");
                list.add(1, Integer.parseInt(view.checkExit(view.read())));
                break;
            } catch (NumberFormatException e) {
                view.write("Incorrect input, try again");
            }
        }

        while (true) {
            try {
                view.write("\nPlease input quantity of goods:\n");
                list.set(0, Integer.parseInt(view.checkExit(view.read())));
                break;
            } catch (NumberFormatException e) {
                view.write("Incorrect input, try again");
            }
        }

        String sql = "SELECT * FROM " + tableName + " WHERE id_goods=" + list.get(1);

        try {
            List<Object> currentValue = manager.getTableData("", sql);

            list.set(0, (Integer.parseInt(String.valueOf(list.get(0))) + Integer.parseInt(String.valueOf(currentValue.get(1)))));

            int id = (new BigDecimal(String.valueOf(currentValue.get(0)))).intValue();

            manager.update(tableName, manager.getColumnNames(tableName, ""), id, list, view);

        } catch (IndexOutOfBoundsException e) {
            manager.insert(tableName, list, view);
        }


    }
}

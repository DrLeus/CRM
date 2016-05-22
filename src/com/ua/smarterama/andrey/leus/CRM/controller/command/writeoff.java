package com.ua.smarterama.andrey.leus.CRM.controller.command;

import com.ua.smarterama.andrey.leus.CRM.model.DataBaseManager;
import com.ua.smarterama.andrey.leus.CRM.view.Console;
import com.ua.smarterama.andrey.leus.CRM.view.View;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class Writeoff extends Command {
    private View view = new Console();

    public Writeoff(DataBaseManager manager, View view) {
            super(manager, view);
    }

    @Override
    public boolean canProcess(String command) {
        return command.equals("writeoff");
    }

    @Override
    public void process() {

        Report report = new Report(manager,view);

        report.process();

        String tableName = "stockbalance";



        List<Object> list = new ArrayList<>();


        list.add(0,"");

        while (true) {
            try {
                view.write("\nPlease input id of goods:\n");
                list.add(1,Integer.parseInt(view.checkExit(view.read())));
                break;
            } catch (NumberFormatException e) {
                view.write("Incorrect input, try again");
            }
        }

        while (true) {
            try {
                view.write("\nPlease input quantity of goods:\n");
                list.set(0,Integer.parseInt(view.checkExit(view.read())));
                break;
            } catch (NumberFormatException e) {
                view.write("Incorrect input, try again");
            }
        }

        String sql = "SELECT * FROM " + tableName + " WHERE id_goods=" + list.get(1);

        try {
            List<Object> currentValue = manager.getTableData("", sql);

            Integer newValueGoods = Integer.parseInt(String.valueOf(list.get(0)));

            Integer currentValueGoods =Integer.parseInt(String.valueOf(currentValue.get(1)));

            if (newValueGoods.equals(currentValueGoods)){

                int id = (new BigDecimal(String.valueOf(currentValue.get(0)))).intValue();
                manager.delete(id, tableName, view);
            } else if (newValueGoods < currentValueGoods) {

                list.set(0, currentValueGoods - newValueGoods);

                int id = (new BigDecimal(String.valueOf(currentValue.get(0)))).intValue();

                manager.update(tableName, manager.getColumnNames(tableName, ""), id, list, view);
            } else{
                view.write("\n Oops...The quantity of goods on warehaus less thsn you want to writeoff!\n");
            }

        } catch (IndexOutOfBoundsException e) {
            manager.insert(tableName, list, view);
        }

    }
}

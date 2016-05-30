package com.ua.smarterama.andrey.leus.CRM.controller.command.tables;

import com.ua.smarterama.andrey.leus.CRM.view.Console;
import com.ua.smarterama.andrey.leus.CRM.view.View;

import java.util.ArrayList;
import java.util.List;
import java.util.MissingFormatArgumentException;

public class Assistant {

    public static void outputColumnNames(List<Object> listColumnName, String result, View view) {
        System.out.println(String.format(result, listColumnName.toArray()));
    }

    public static void outputData(List<Object> listColumnName, List<Object> listValue, String result, View view) {
        try {
            do {
                outputColumnNames(listValue, result, view);
                for (int i = 0; i < listColumnName.size(); i++) {
                    listValue.remove(0);
                }

            } while (listValue.size() != 0);
        } catch (MissingFormatArgumentException e) { //TODO when table is empty, getTable show error
            System.out.println("\nThe table is empty!");
        }

    }

    public static String getFormatedLine(List<Object> listColumnName, List<Object> listValue) {
        String result = "";

        for (int i = 0; i < listColumnName.size(); i++) {
            result += "%-" + getWidthColumn(i, listColumnName, listValue) + "s";
        }
        result += "%n";
        return result;
    }

    private static int getWidthColumn(int i, List<Object> listColumnName, List<Object> listValue) {

        int result = (String.valueOf(listColumnName.get(i))).length();

        int qtyLine = listValue.size() / listColumnName.size();

        for (int j = 0; j < qtyLine; j++) {
            if (result < (String.valueOf(listValue.get(i + (listColumnName.size() * j)))).length()) {
                result = (String.valueOf(listValue.get(i + (listColumnName.size() * j)))).length();
            }
        }
        return result + 2;
    }

    public static List<Object> inputNames(View view) {

        List<Object> list = new ArrayList<>();

        String input = null;
        do {

            view.write("\nPlease input name for next column\n");

            input = view.checkExit(view.read());

            list.add(input);

        } while (!input.isEmpty());

        return list;
    }

    public static String selectTable(List<String> tables, View view) {

        String tableName = null;

        int numberTable = 0;

        view.write("\n Database has next tables: \n");

        for (String sert : tables) {
            view.write("" + ++numberTable + ": " + sert);
        }

        while (true) {
            try {
                view.write("\nPlease select number of table:\n");

                String input = view.checkExit(view.read());

                for (String sert: tables) {
                    if (input.equals(sert)){
                        return sert;
                    }
                }

                if (Integer.parseInt(input) > numberTable || Integer.parseInt(input) < 1) {
                    view.write("Incorrect input, try again");
                } else {
                    tableName = tables.get(Integer.parseInt(input) - 1);
                    break;
                }
            } catch (NumberFormatException e) {
                view.write("Incorrect input, try again");
            }
        }
        return tableName;
    }

    public static List<Object> selectGoodsAndQty(View view) {

        List<Object> list = new ArrayList<>();


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
        return list;
    }
}
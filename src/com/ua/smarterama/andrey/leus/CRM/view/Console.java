package com.ua.smarterama.andrey.leus.CRM.view;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.MissingFormatArgumentException;

public class Console implements View {

    public void write(String message) {
        System.out.println(message);
    }

    public void error(String message, Exception e) {
        System.err.println(message + e);
    }

    public String read() {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        try {
            return reader.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "error Console";
    }

    public boolean checkExit(String input) {
        if (input.equals("exit") | input.equals("return")) {
            return true;
        }
        return false;
    }

    private static Console instance = null;

    private Console() {
    }

    public static synchronized Console getInstance() {
        if (instance == null)
            instance = new Console();
        return instance;
    }

    public void outputColumnNames(List<Object> listColumnName, String formatedLine) {
        System.out.println(String.format(formatedLine, listColumnName.toArray()));
    }

    public void outputData(List<Object> listColumnName, List<Object> listValue, String result) {
        try {
            do {
                outputColumnNames(listValue, result);
                for (int i = 0; i < listColumnName.size(); i++) {
                    listValue.remove(0);
                }

            } while (listValue.size() != 0);
        } catch (MissingFormatArgumentException e) { //TODO when table is empty, getTable show error
            System.out.println("The table is empty!\n");
        }

    }

    public String getFormatedLine(List<Object> listColumnName, List<Object> listValue) {
        String result = "";

        for (int i = 0; i < listColumnName.size(); i++) {
            result += "%-" + getWidthColumn(i, listColumnName, listValue) + "s";
        }
        result += "%n";
        return result;
    }

    private int getWidthColumn(int i, List<Object> listColumnName, List<Object> listValue) {

        int result = (String.valueOf(listColumnName.get(i))).length();

        int qtyLine = listValue.size() / listColumnName.size();

        for (int j = 0; j < qtyLine; j++) {
            if (result < (String.valueOf(listValue.get(i + (listColumnName.size() * j)))).length()) {
                result = (String.valueOf(listValue.get(i + (listColumnName.size() * j)))).length();
            }
        }
        return result + 2;
    }

    public List<Object> inputNames(Console view) {

        List<Object> list = new ArrayList<>();

        String input;
        do {

            view.write("Please input name for next column\n");

            input = (view.read());

            if (view.checkExit(input)) {
                list.clear();
                return list;
            }

            list.add(input);

        } while (!input.isEmpty());

        return list;
    }

    public String selectTable(List<String> tables, Console view) {

        String tableName;

        int numberTable = 0;

        view.write("Database has next tables:\n");

        for (String sert : tables) {
            view.write("" + ++numberTable + ": " + sert);
        }

        while (true) {
            try {
                view.write("Please select table:\n");

                String input = view.read();

                for (String sert : tables) {
                    if (input.equals(sert)) {
                        return sert;
                    }
                }

                if (Integer.parseInt(input) > numberTable || Integer.parseInt(input) < 1) {
                    view.write("Incorrect input, try again\n");
                } else {
                    tableName = tables.get(Integer.parseInt(input) - 1);
                    break;
                }
            } catch (NumberFormatException e) {
                view.write("Incorrect input, try again\n");
            }
        }
        return tableName;
    }

    public List<Object> selectGoodsAndQty(Console view) {

        List<Object> list = new ArrayList<>();

        list.add(0, "");

        while (true) {
            try {
                view.write("Please input id of goods:\n");

                String input = (view.read());

                if (view.checkExit(input)) {
                    list.clear();
                    return list;
                }

                list.add(1, Integer.parseInt(input));
                break;
            } catch (NumberFormatException e) {
                view.write("Incorrect input, try again\n");
            }
        }

        while (true) {
            try {
                view.write("Please input quantity of goods:\n");

                String input = (view.read());

                if (view.checkExit(input)) {
                    list.clear();
                    return list;
                }

                list.set(0, Integer.parseInt(input));
                break;
            } catch (NumberFormatException e) {
                view.write("Incorrect input, try again\n");
            }
        }
        return list;
    }
}


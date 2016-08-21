package ua.com.smart.andrey.leus.CRM.controller.command;

import ua.com.smart.andrey.leus.CRM.model.CRMException;
import ua.com.smart.andrey.leus.CRM.model.DataBaseManager;
import ua.com.smart.andrey.leus.CRM.view.View;

import java.util.*;

public abstract class Command {

    public DataBaseManager manager;
    protected View view;

    public static final String INCORRECT_INPUT_TRY_AGAIN = "Incorrect input, try again\n";

    public Command(View view) {
        this.view = view;
    }

    public Command(DataBaseManager manager, View view) {

        this.manager = manager;
        this.view = view;
    }

    public abstract boolean canProcess(String command) throws CRMException;

    public abstract void process() throws CRMException;


    public boolean checkExit(String input) {
        if ("exit".equals(input) || "return".equals(input)) {
            return true;
        }
        return false;
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
        } catch (MissingFormatArgumentException e) {
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

    public Map<String, String> inputNames(View view) throws CRMException {

        Map<String, String> list = new LinkedHashMap<>();

        String inputKey;
        String inputValue;
        do {

            view.write("Please input name for next column\n");

            inputKey = view.read();

            if (checkExit(inputKey)) {
                list.clear();
                return list;
            }

            view.write("Please input type for column " + inputKey + "\n");

            inputValue = view.read();

            if (checkExit(inputValue)) {
                list.clear();
                return list;
            }
            list.put(inputKey, inputValue);

        } while (!inputKey.isEmpty());

        return list;
    }

    public String selectTable(List<String> tables, View view) throws CRMException {

        String tableName = "";

        int numberTable = 0;

        view.write("Database has next tables:\n");

        for (String sert : tables) {
            view.write(String.format("%s: %s", ++numberTable, sert));
        }

        while (true) {
            try {
                view.write("Please select table:\n");

                String input = view.read();

                if (checkExit(input)) {
                    break;
                }

                for (String nameTables : tables) {
                    if (input.equals(nameTables)) {
                        return nameTables;
                    }
                }

                if (Integer.parseInt(input) > numberTable || Integer.parseInt(input) < 1) {
                    view.write(INCORRECT_INPUT_TRY_AGAIN);
                } else {
                    tableName = tables.get(Integer.parseInt(input) - 1);
                    break;
                }
            } catch (NumberFormatException e) {
                view.write(INCORRECT_INPUT_TRY_AGAIN);
            }
        }
        return tableName;
    }


}
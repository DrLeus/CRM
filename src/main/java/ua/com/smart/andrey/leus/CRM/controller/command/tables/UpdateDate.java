package ua.com.smart.andrey.leus.CRM.controller.command.tables;

import ua.com.smart.andrey.leus.CRM.controller.command.Command;
import ua.com.smart.andrey.leus.CRM.model.CRMException;
import ua.com.smart.andrey.leus.CRM.model.DataBaseManager;
import ua.com.smart.andrey.leus.CRM.view.View;

import java.util.ArrayList;
import java.util.List;

public class UpdateDate extends Command {

    public UpdateDate(DataBaseManager manager, View view) {
        super(manager, view);
    }

    @Override
    public void process() throws CRMException {

        String tableName = selectTable(manager.getTableNames(), view);

        if (tableName.isEmpty()) {
            view.write("Your action canceled!\n");
            return;
        }

        List<Object> columnNames;
        try {
            columnNames = manager.getColumnNames(tableName, "");
        } catch (CRMException e) {
            throw new CRMException(String.format("Error get column names in case - %s%n", e));
        }

        try {
            outputColumnNames(columnNames, getFormatedLine(columnNames, manager.getTableData(tableName, "")));
            outputData(columnNames, manager.getTableData(tableName, ""), getFormatedLine(columnNames, manager.getTableData(tableName, "")));
        } catch (CRMException e) {
            throw new CRMException(String.format("Error get table data in case - %s%n", e));
        }

        int id;

        while (true) {
            try {
                view.write("\n");
                view.write("Please select row id to update:\n");

                String input = view.read();

                if (checkExit(input)) {
                    view.write("Return to main menu!\n");
                    return;
                }

                id = Integer.parseInt(input);
                break;
            } catch (NumberFormatException e) {
                view.write("Incorrect input, try again\n");
            }
        }

        List<Object> list = new ArrayList<>();

        for (int i = 1; i < columnNames.size(); i++) {

            view.write(String.format("Please input data for column '%s'%n", columnNames.get(i)));

            String input = view.read();

            if (checkExit(input)) {
                view.write("Return to main menu!\n");
                return;
            }
            list.add(input);
        }
        try {
            manager.update(tableName, columnNames, id, list);
            view.write("The row was updated! Success!\n");
        } catch (CRMException e) {
            throw new CRMException(String.format("Error update data in case - %s%n", e));
        }
    }

    @Override
    public boolean canProcess(String command) {
        return false;
    }

    @Override
    public String toString(){
        return "3. Update data (position)";
    }
}
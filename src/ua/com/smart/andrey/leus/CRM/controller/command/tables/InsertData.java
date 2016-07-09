package ua.com.smart.andrey.leus.CRM.controller.command.tables;

import ua.com.smart.andrey.leus.CRM.controller.command.Command;
import ua.com.smart.andrey.leus.CRM.model.CRMException;
import ua.com.smart.andrey.leus.CRM.model.DataBaseManager;
import ua.com.smart.andrey.leus.CRM.view.Console;

import java.util.ArrayList;
import java.util.List;

public class InsertData extends Command {

    public InsertData(DataBaseManager manager, Console view) {
        super(manager, view);
    }

    public void insertData() throws CRMException {

        view.write("Please select table\n");

        String tableName = view.selectTable(manager.getTableNames(), view);

        if (tableName.isEmpty()) {
            view.write("Your action canceled!\n");
            return;
        }

        List<Object> listColumnName;
        List<Object> columnTable = null;
        List<Object> list = null;

        try {
            listColumnName = manager.getColumnNames(tableName, "");

            list = new ArrayList<>();

            for (int i = 1; i < listColumnName.size(); i++) {

                view.write(String.format("Please input data for column '%s'%n", listColumnName.get(i)));

                String input = view.read();

                if (view.checkExit(input)) {
                    view.write("Return to main menu!\n");
                    return;
                }

                list.add(input);
            }

            columnTable = manager.getColumnNames(tableName, "");

        } catch (CRMException e) {
            throw new CRMException(String.format("Error get column names in case - %s%n", e));
        }

        try {
            manager.insert(tableName, columnTable, list);
            view.write("The row was created! Success!\n");
        } catch (CRMException e) {
            throw new CRMException(String.format("Error insert data in case - %s%n", e));
        }
    }

    @Override
    public boolean canProcess(String command) {
        return false;
    }

    @Override
    public void process() {
        // do nothing
    }
}

package ua.com.smart.andrey.leus.CRM.controller.command.tables;

import ua.com.smart.andrey.leus.CRM.controller.command.Command;
import ua.com.smart.andrey.leus.CRM.model.CRMException;
import ua.com.smart.andrey.leus.CRM.model.DataBaseManager;
import ua.com.smart.andrey.leus.CRM.view.Console;

import java.util.List;

public class CreateTable extends Command {

    public CreateTable(DataBaseManager manager, Console view) {
        super(manager, view);
    }

    public void createTable() throws CRMException {
        view.write("Please input table name:\n");

        String tableName = view.read();

        if (view.checkExit(tableName)) {
            view.write("Return to main menu!\n");
            return;
        }

        view.write("Please input name of columns and type (for ex. TEXT; for column 'name' must be 'name TEXT')\n" +
                "The first column = 'id' with auto-increment\n");

        List<Object> listColumn = view.inputNames(view);

        if (listColumn.isEmpty()) {
            return;
        }

        try {
            manager.createTable(tableName, listColumn);
            view.write(String.format("The table %s was created! Success!%n", tableName));
        } catch (CRMException e) {
            try {
                manager.dropTable(tableName);
            } catch (CRMException e1) {
                throw new CRMException(String.format("Error drop table in case - %s%n", e1));
            }
            throw new CRMException(String.format("Error create table in case - %s%n", e));
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
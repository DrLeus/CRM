package ua.com.smart.andrey.leus.CRM.controller.command.tables;

import ua.com.smart.andrey.leus.CRM.controller.command.Command;
import ua.com.smart.andrey.leus.CRM.model.CRMException;
import ua.com.smart.andrey.leus.CRM.model.DataBaseManager;
import ua.com.smart.andrey.leus.CRM.view.View;

import java.util.List;
import java.util.Map;

public class CreateTable extends Command {

    public CreateTable(DataBaseManager manager, View view) {
        super(manager, view);
    }

    @Override
    public boolean canProcess(String command) {
        return false;
    }

    @Override
    public void process() throws CRMException {
        view.write("Please input table name:\n");

        String tableName = view.read();

        if (checkExit(tableName)) {
            view.write("Return to main menu!\n");
            return;
        }

        view.write("Please input name of columns and type (for ex. TEXT; for column 'name' must be 'name TEXT')\n" +
                "The first column = 'id' with auto-increment\n");

        Map<String,String> listColumn = inputNames(view);

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
    public String toString(){
        return "5. Create table";
    }
}
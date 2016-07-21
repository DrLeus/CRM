package ua.com.smart.andrey.leus.CRM.controller.command.tables;

import ua.com.smart.andrey.leus.CRM.controller.command.Command;
import ua.com.smart.andrey.leus.CRM.model.CRMException;
import ua.com.smart.andrey.leus.CRM.model.DataBaseManager;
import ua.com.smart.andrey.leus.CRM.view.View;

public class RemoveTable extends Command {

    public RemoveTable(DataBaseManager manager, View view) {
        super(manager, view);
    }

   @Override
    public boolean canProcess(String command) {
        return false;
    }

    @Override
    public void process() throws CRMException {
        String tableName = selectTable(manager.getTableNames(), view);

        if (tableName.isEmpty()) {
            view.write("Your action canceled!\n");
            return;
        }

        view.write(String.format("Please confirm, do you really want to remove '%s' table? Y/N%n", tableName));

        if ("Y".equalsIgnoreCase(view.read())) {
            try {
                manager.dropTable(tableName);
                view.write(String.format("Table '%s' was removed! Success!%n",tableName));
            } catch (CRMException e) {
                throw new CRMException(String.format("Error remove table in case - %s%n", e));
            }
        } else {
            view.write("Your action canceled!\n");
        }
    }

    @Override
    public String toString(){
        return "6. Remove table";
    }
}

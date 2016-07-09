package ua.com.smart.andrey.leus.CRM.controller.command.tables;

import ua.com.smart.andrey.leus.CRM.controller.command.Command;
import ua.com.smart.andrey.leus.CRM.model.CRMException;
import ua.com.smart.andrey.leus.CRM.model.DataBaseManager;
import ua.com.smart.andrey.leus.CRM.view.Console;

public class RemoveTable extends Command {

    public RemoveTable(DataBaseManager manager, Console view) {
        super(manager, view);
    }

    public void removeTable() throws CRMException {

        String tableName = view.selectTable(manager.getTableNames(), view);

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
    public boolean canProcess(String command) {
        return false;
    }

    @Override
    public void process() {
        // do nothing
    }
}

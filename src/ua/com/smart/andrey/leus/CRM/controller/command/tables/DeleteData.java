package ua.com.smart.andrey.leus.CRM.controller.command.tables;

import ua.com.smart.andrey.leus.CRM.controller.command.Command;
import ua.com.smart.andrey.leus.CRM.model.CRMException;
import ua.com.smart.andrey.leus.CRM.model.DataBaseManager;
import ua.com.smart.andrey.leus.CRM.view.Console;

public class DeleteData extends Command {

    public DeleteData(DataBaseManager manager, Console view) {
        super(manager, view);
    }

    public void delete() throws CRMException {
        String tableName = view.selectTable(manager.getTableNames(), view);

        if (tableName.isEmpty()) {
            view.write("Your action canceled!\n");
            return;
        }

        try {
            view.outputColumnNames(manager.getColumnNames(tableName, ""), view.getFormatedLine(manager.getColumnNames(tableName, ""), manager.getTableData(tableName, ""))); // TODO duplicate getTableData
            view.outputData(manager.getColumnNames(tableName, ""), manager.getTableData(tableName, ""), view.getFormatedLine(manager.getColumnNames(tableName, ""), manager.getTableData(tableName, ""))); //TODO duplicate getTableData
        } catch (CRMException e) {
            throw new CRMException(String.format("Error in case - %s%n", e));
        }

        while (true) {
            try {
                view.write("Please input 'id' line to delete\n");

                String line = view.read();

                if (view.checkExit(line)) {
                    view.write("Return to main menu!\n");
                    return;
                }

                int input = Integer.parseInt(line);

                view.write(String.format("Please confirm, do you really want to remove position id='%s'? Y/N%n", input));

                if ("Y".equalsIgnoreCase(view.read())) {
                    try {
                        manager.delete(input, tableName);
                        view.write(String.format("Id '%s' removed%n", input));
                    } catch (CRMException e) {
                        throw new CRMException(String.format("Error delete data in case - %s%n", e));
                    }
                } else {
                    view.write("Your action canceled!\n");
                    break;
                }
                break;
            } catch (NumberFormatException e) {
                view.write("Incorrect input, try again\n");
            }
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

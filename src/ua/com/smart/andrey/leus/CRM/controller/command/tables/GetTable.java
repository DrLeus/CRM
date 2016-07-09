package ua.com.smart.andrey.leus.CRM.controller.command.tables;

import ua.com.smart.andrey.leus.CRM.controller.command.Command;
import ua.com.smart.andrey.leus.CRM.model.CRMException;
import ua.com.smart.andrey.leus.CRM.model.DataBaseManager;
import ua.com.smart.andrey.leus.CRM.view.Console;

import java.util.List;

public class GetTable extends Command {

    public GetTable(DataBaseManager manager, Console view) {
        super(manager, view);
    }

    public void getTableData() throws CRMException {

        String tableName = view.selectTable(manager.getTableNames(), view);

        if (tableName.isEmpty()) {
            view.write("Your action canceled!\n");
            return;
        }

        List<Object> listColumnName = null;
        try {
            listColumnName = manager.getColumnNames(tableName, "");
        } catch (CRMException e) {
            throw new CRMException(String.format("Error get column names in case - %s%n", e));
        }

        List<Object> listValue = null;
        try {
            listValue = manager.getTableData(tableName, "");
        } catch (CRMException e) {
            throw new CRMException(String.format("Error get table data in case - %s%n", e));
        }

        String format = view.getFormatedLine(listColumnName, listValue);

        view.outputColumnNames(listColumnName, format);

        view.outputData(listColumnName, listValue, format);
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

package ua.com.smart.andrey.leus.CRM.controller.command.tables;

import ua.com.smart.andrey.leus.CRM.controller.command.Command;
import ua.com.smart.andrey.leus.CRM.model.CRMException;
import ua.com.smart.andrey.leus.CRM.model.DataBaseManager;
import ua.com.smart.andrey.leus.CRM.view.Console;
import ua.com.smart.andrey.leus.CRM.view.View;

import java.util.List;

public class GetTable extends Command {

    public GetTable(DataBaseManager manager, View view) {
        super(manager, view);
    }

    @Override
    public void process() throws CRMException {

        String tableName = selectTable(manager.getTableNames(), view);

        if (tableName.isEmpty()) {
            view.write("Your action canceled!\n");
            return;
        }

        List<Object> listColumnName;
        try {
            listColumnName = manager.getColumnNames(tableName, "");
        } catch (CRMException e) {
            throw new CRMException(String.format("Error get column names in case - %s%n", e));
        }

        List<Object> listValue;
        try {
            listValue = manager.getTableData(tableName, "");
        } catch (CRMException e) {
            throw new CRMException(String.format("Error get table data in case - %s%n", e));
        }

        String format = getFormatedLine(listColumnName, listValue);

        outputColumnNames(listColumnName, format);

        outputData(listColumnName, listValue, format);
    }

    @Override
    public boolean canProcess(String command) {
        return false;
    }

    @Override
    public String toString(){
        return "1. Get table data";
    }

}

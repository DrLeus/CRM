package ua.com.smart.andrey.leus.CRM.controller.command.warehouse;

import ua.com.smart.andrey.leus.CRM.controller.command.Command;
import ua.com.smart.andrey.leus.CRM.model.CRMException;
import ua.com.smart.andrey.leus.CRM.model.DataBaseManager;
import ua.com.smart.andrey.leus.CRM.view.Console;

import java.util.List;
import java.util.MissingFormatArgumentException;

public class Report extends Command {

    public Report(DataBaseManager manager, Console view) {
        super(manager, view);
    }

    @Override
    public boolean canProcess(String command) {
        return "report".equals(command);
    }

    @Override
    public void process() throws CRMException {

        view.write("The warehouse contains:\n");

        String sql = "SELECT goods.id, code, name, quantity FROM goods, stockbalance WHERE goods.id = stockbalance.id_goods";

        List<Object> listValue = null;
        try {
            listValue = manager.getTableData("", sql);
        } catch (CRMException e) {
            throw new CRMException(String.format("Error get table data in case - %s%n", e));
        }

        List<Object> listColumnName = null;
        try {
            listColumnName = manager.getColumnNames("", sql);
        } catch (CRMException e) {
            throw new CRMException(String.format("Error get column names in case - %s%n", e));
        }

        String format = view.getFormatedLine(listColumnName, listValue);

        outputColumnNames(listColumnName, format);

        outputData(listColumnName, listValue, format);
    }

    private void outputColumnNames(List<Object> listColumnName, String result) {
        view.write(String.format(result, listColumnName.toArray()));
    }

    private void outputData(List<Object> listColumnName, List<Object> listValue, String result) {
        try {
            do {
                outputColumnNames(listValue, result);
                for (int i = 0; i < listColumnName.size(); i++) {
                    listValue.remove(0);
                }

            } while (listValue.size() != 0);
        } catch (MissingFormatArgumentException e) {
            view.write("The table is empty!\n");
        }
    }
}
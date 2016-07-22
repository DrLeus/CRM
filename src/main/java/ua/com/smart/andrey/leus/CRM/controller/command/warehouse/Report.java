package ua.com.smart.andrey.leus.CRM.controller.command.warehouse;

import ua.com.smart.andrey.leus.CRM.controller.command.Command;
import ua.com.smart.andrey.leus.CRM.model.CRMException;
import ua.com.smart.andrey.leus.CRM.model.DataBaseManager;
import ua.com.smart.andrey.leus.CRM.view.View;

import java.util.List;
import java.util.MissingFormatArgumentException;

/** This class allows to output information about goods (from table 'goods') and information about quantities of
 * these goods (from table 'stockbalance').
 */
public class Report extends Command {

    public Report(DataBaseManager manager, View view) {
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

        List<Object> listValue;
        try {
            listValue = manager.getTableData(sql);
        } catch (CRMException e) {
            throw new CRMException(String.format("Error get table data in case - %s%n", e));
        }

        List<Object> listColumnName;
        try {
            listColumnName = manager.getColumnNames(sql);
        } catch (CRMException e) {
            throw new CRMException(String.format("Error get column names in case - %s%n", e));
        }

        String format = getFormatedLine(listColumnName, listValue);

        outputColumnNames(listColumnName, format);

        outputData(listColumnName, listValue, format);
    }
}
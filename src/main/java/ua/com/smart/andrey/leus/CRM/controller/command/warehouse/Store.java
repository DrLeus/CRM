package ua.com.smart.andrey.leus.CRM.controller.command.warehouse;

import ua.com.smart.andrey.leus.CRM.controller.command.Command;
import ua.com.smart.andrey.leus.CRM.model.CRMException;
import ua.com.smart.andrey.leus.CRM.model.DataBaseManager;
import ua.com.smart.andrey.leus.CRM.view.View;

import java.math.BigDecimal;
import java.util.List;


/** This class allows to add to table 'stockbalance' the goods using id of goods.
 */
public class Store extends Command {

    public Store(DataBaseManager manager, View view) {
        super(manager, view);
    }

    @Override
    public boolean canProcess(String command) {
        return "store".equals(command);
    }

    @Override
    public void process() throws CRMException {

        String tableName = "stockbalance";

        List<Object> list = selectGoodsAndQty(view);

        if (list.isEmpty()) {
            return;
        }

        String sql = String.format("SELECT * FROM %s WHERE id_goods=%s", tableName, list.get(1));

        try {
            List<Object> currentValue = manager.getTableData("", sql);

            list.set(0, (Integer.parseInt(String.valueOf(list.get(0))) + Integer.parseInt(String.valueOf(currentValue.get(1)))));

            int id = (new BigDecimal(String.valueOf(currentValue.get(0)))).intValue();

            manager.update(tableName, manager.getColumnNames(tableName, ""), id, list);

            view.write("The goods was added! Success!\n");

        } catch (IndexOutOfBoundsException e) {
            try {
                manager.insert(tableName, manager.getColumnNames(tableName, ""), list);
                view.write("The goods was added! Success!\n");
            } catch (CRMException e1) {
                throw new CRMException(String.format("Error get column names in case - %s%n", e1));
            }
        } catch (CRMException e) {
            view.write(String.format("Error update data in case - %s%n", e));
        }
    }
}
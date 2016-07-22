package ua.com.smart.andrey.leus.CRM.controller.command.warehouse;

import ua.com.smart.andrey.leus.CRM.controller.command.Command;
import ua.com.smart.andrey.leus.CRM.model.CRMException;
import ua.com.smart.andrey.leus.CRM.model.DataBaseManager;
import ua.com.smart.andrey.leus.CRM.view.View;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;


/** This class allows to delete from table 'stockbalance' the goods using id of goods.
 */
public class Writeoff extends Command {

    public Writeoff(DataBaseManager manager, View view) {
        super(manager, view);
    }

    @Override
    public boolean canProcess(String command) {
        return "writeoff".equals(command);
    }

    @Override
    public void process() throws CRMException {

        Report report = new Report(manager, view);

        report.process();

        String tableName = "stockbalance";


        List<Object> list = selectGoodsAndQty(view);

        if (list.isEmpty()) {return;}

        String sql = String.format("SELECT * FROM %s WHERE id_goods=%s", tableName, list.get(1));

        List<Object> currentValue = null;
        try {
            currentValue = manager.getTableData(sql);
        } catch (CRMException e) {
            throw new CRMException(String.format("Error get table data in case - %s%n", e));
        }

        Integer newValueGoods = Integer.parseInt(String.valueOf(list.get(0)));

        Integer currentValueGoods = Integer.parseInt(String.valueOf(currentValue.get(1)));

        if (newValueGoods.equals(currentValueGoods)) {

            int id = (new BigDecimal(String.valueOf(currentValue.get(0)))).intValue();
            try {
                manager.delete(id, tableName);
            } catch (CRMException e) {
                throw new CRMException(String.format("Error delete data in case - %s%n", e));
            }
        } else if (newValueGoods < currentValueGoods) {

            list.set(0, currentValueGoods - newValueGoods);

            int id = (new BigDecimal(String.valueOf(currentValue.get(0)))).intValue();

            try {
                manager.update(tableName, manager.getColumnNames(tableName), id, list);
                view.write("The goods was wrote off! Success!\n");
            } catch (CRMException e) {
                throw new CRMException(String.format("Error update data in case - %s%n", e));
            }
        } else {
            view.write("\n Oops...The quantity of goods on warehouse less than you want to writeoff!\n");
        }
    }

    public List<Object> selectGoodsAndQty(View view) throws CRMException {

        List<Object> list = new ArrayList<>();

        list.add(0, "");

        while (true) {
            try {
                view.write("Please input id of goods:\n");

                String input = view.read();

                if (checkExit(input)) {
                    list.clear();
                    return list;
                }

                list.add(1, Integer.parseInt(input));
                break;
            } catch (NumberFormatException e) {
                view.write(INCORRECT_INPUT_TRY_AGAIN);
            }
        }

        while (true) {
            try {
                view.write("Please input quantity of goods:\n");

                String input = view.read();

                if (checkExit(input)) {
                    list.clear();
                    return list;
                }

                list.set(0, Integer.parseInt(input));
                break;
            } catch (NumberFormatException e) {
                view.write(INCORRECT_INPUT_TRY_AGAIN);
            }
        }
        return list;
    }
}

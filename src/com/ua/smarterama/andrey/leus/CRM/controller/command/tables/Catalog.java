package com.ua.smarterama.andrey.leus.CRM.controller.command.tables;

import com.ua.smarterama.andrey.leus.CRM.controller.command.*;
import com.ua.smarterama.andrey.leus.CRM.model.DataBaseManager;
import com.ua.smarterama.andrey.leus.CRM.view.Console;

public class Catalog extends Command {


    public Catalog(DataBaseManager manager, Console view) {
        super(manager, view);
    }

    @Override
    public boolean canProcess(String command) {
        return command.equals("catalog");
    }

    @Override
    public void process() {

        while (true) {
            try {
                view.write("Available operations:\n" +
                        "1. Get table data\n" +
                        "2. Insert data (position)\n" +
                        "3. Update data (position)\n" +
                        "4. Delete data (position)\n" +
                        "5. Create table\n" +
                        "6. Remove table\n" +
                        "7. Clear table\n" +
                        "8. Return to main menu\n");

                view.write("Please select operation:\n");

                String input = view.checkExit(view.read());

                    switch (Integer.parseInt(input)) {
                        case 1:
                            GetTable table = new GetTable(manager, view);
                            table.getTableData();
                            break;
                        case 2:
                            InsertData insert = new InsertData(manager, view);
                            insert.insertData();
                            break;
                        case 3:
                            UpdateDate update = new UpdateDate(manager, view);
                            update.update();
                            break;
                        case 4:
                            DeleteData delete = new DeleteData(manager, view);
                            delete.delete();
                            break;
                        case 5:
                            CreateTable create = new CreateTable(manager, view);
                            create.createTable();
                            break;
                        case 6:
                            RemoveTable remove = new RemoveTable(manager, view);
                            remove.removeTable();
                            break;
                        case 7:
                            ClearTable clear = new ClearTable(manager, view);
                            clear.clearTable();
                            break;
                        case 8:
                            view.write("Return to main menu!\n");
                            return;
                        default:
                            view.write("Incorrect input, try again\n");
                    }
            } catch (NumberFormatException e) {
                view.write("Incorrect input, try again\n");
            }
        }
    }
}

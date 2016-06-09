package com.ua.smarterama.andrey.leus.CRM.controller.command.tables;

import com.ua.smarterama.andrey.leus.CRM.controller.command.*;
import com.ua.smarterama.andrey.leus.CRM.model.DataBaseManager;

public class Catalog extends Command {

    public Catalog(DataBaseManager manager) {
        super(manager);
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

                String input = view.read();

                if (view.checkExit(input)) {
                    view.write("Return to main menu!\n");
                    return;
                }

                    switch (Integer.parseInt(input)) {
                        case 1:
                            GetTable table = new GetTable(manager);
                            table.getTableData();
                            break;
                        case 2:
                            InsertData insert = new InsertData(manager);
                            insert.insertData();
                            break;
                        case 3:
                            UpdateDate update = new UpdateDate(manager);
                            update.update();
                            break;
                        case 4:
                            DeleteData delete = new DeleteData(manager);
                            delete.delete();
                            break;
                        case 5:
                            CreateTable create = new CreateTable(manager);
                            create.createTable();
                            break;
                        case 6:
                            RemoveTable remove = new RemoveTable(manager);
                            remove.removeTable();
                            break;
                        case 7:
                            ClearTable clear = new ClearTable(manager);
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

package com.ua.smarterama.andrey.leus.CRM.controller.command;

import com.ua.smarterama.andrey.leus.CRM.model.DataBaseManager;
import com.ua.smarterama.andrey.leus.CRM.view.Console;

import java.sql.SQLException;
import java.util.List;

public class DropDataBase extends Command {

    public DropDataBase(DataBaseManager manager, Console view) {
        super(manager, view);
    }

    @Override
    public boolean canProcess(String command) {
        return command.equals("drop");
    }

    @Override
    public void process() {

        String nameDataBase = getNameDataBase();

        if (nameDataBase.isEmpty()) {
            view.write("Nothing to do!\n");
            view.write("Return to main menu!\n");
        } else {

            view.write(String.format("Please confirm, do you really want to drop '%s' database? Y/N%n", nameDataBase));

            if (view.read().equalsIgnoreCase("Y")) {
                try {
                    manager.dropDatabase(nameDataBase);
                    view.write(String.format("Database '%s' dropped%n", nameDataBase));
                } catch (SQLException e) {
                    view.write(String.format("Drop database error in case - %s%n", e));
                }
            } else {
                view.write("Your action canceled!\n");
            }
        }
    }

    public String getNameDataBase() {

        String nameDataBase = "";

        List<String> list = manager.getDatabases();

        int i = 0;

        view.write("The next data bases available:\n");

        for (String sert : list) {
            view.write("" + ++i + ": " + sert);
        }


        while (true) {
            try {
                view.write("Please select database for dropping:\n");

                String input = (view.read());

                if (view.checkExit(input)) {
                    return nameDataBase;
                }
                ;

                for (String sert : list) {
                    if (input.equals(sert)) {
                        return sert;
                    }
                }

                if (Integer.parseInt(input) > i || Integer.parseInt(input) < 1) {
                    view.write("Incorrect input, try again\n");
                } else {
                    nameDataBase = list.get(Integer.parseInt(input) - 1);
                    break;
                }
            } catch (NumberFormatException e) {
                view.write("Incorrect input, try again\n");
            }
        }
        return nameDataBase;
    }
}

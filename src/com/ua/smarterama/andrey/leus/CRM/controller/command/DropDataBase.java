package com.ua.smarterama.andrey.leus.CRM.controller.command;

import com.ua.smarterama.andrey.leus.CRM.model.DataBaseManager;
import com.ua.smarterama.andrey.leus.CRM.view.Console;
import com.ua.smarterama.andrey.leus.CRM.view.View;

import java.sql.SQLException;
import java.util.List;
import java.util.Set;

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

        view.write("Please confirm, do you really want to drop '" + nameDataBase + "' database? Y/N");

        if (view.read().equalsIgnoreCase("Y")) {
            try {
                manager.dropDatabase(nameDataBase);
                view.write("Database '" + nameDataBase + "' dropped");
            } catch (SQLException e) {
                view.write(String.format("Drop database error in case - %s", e));
            }
        } else {
            view.write("Your action canceled!");
        }
    }

    public String getNameDataBase() {
        String nameDataBase = null;

        List<String> list = manager.getDatabases();

        int i = 0;

        view.write("\nThe next data bases avaiilable:\n");

        for (String sert : list) {
            view.write("" + ++i + ": " + sert);
        }


        while (true) {
            try {
                view.write("\nPlease select database for dropping:\n");

                String input = view.checkExit(view.read());

                if (Integer.parseInt(input) > i || Integer.parseInt(input) < 1) {
                    view.write("Incorrect input, try again");
                } else {
                    nameDataBase = list.get(Integer.parseInt(input) - 1);
                    break;
                }
            } catch (NumberFormatException e) {
                view.write("Incorrect input, try again");
            }
        }
        return nameDataBase;
    }
}

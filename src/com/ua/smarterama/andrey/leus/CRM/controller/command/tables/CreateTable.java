package com.ua.smarterama.andrey.leus.CRM.controller.command.tables;

import com.ua.smarterama.andrey.leus.CRM.controller.command.Command;
import com.ua.smarterama.andrey.leus.CRM.model.DataBaseManager;
import com.ua.smarterama.andrey.leus.CRM.view.Console;

/**
 * Created by Admin on 25.05.2016.
 */
public class CreateTable extends Command {



    public CreateTable(DataBaseManager manager, Console view) {
        super(manager, view);
    }

    @Override
    public boolean canProcess(String command) {
        return false;
    }

    @Override
    public void process() {

    }

    public void createTable() {
        view.write("\nPlease input table name:\n");

        String input = view.checkExit(view.read());

        manager.createTable(input, view);
    }
}
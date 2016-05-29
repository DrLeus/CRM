package com.ua.smarterama.andrey.leus.CRM.controller.command;

import com.ua.smarterama.andrey.leus.CRM.model.DataBaseManager;
import com.ua.smarterama.andrey.leus.CRM.view.Console;
import com.ua.smarterama.andrey.leus.CRM.view.View;

import java.util.List;

public class SelectDataBase extends Command {

    public SelectDataBase(DataBaseManager manager, View view) {
        super(manager, view);
    }

    @Override
    public boolean canProcess(String command) {
        return command.equals("list");
    }

    @Override
    public void process() {
        List<String> list = manager.getDatabases();

        view.write("The next data bases available:\n");

        for (String sert : list) {
            view.write(sert);
        }
    }
}

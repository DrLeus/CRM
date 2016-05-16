package com.ua.smarterama.andrey.leus.CRM.controller.command;

import com.ua.smarterama.andrey.leus.CRM.model.DataBaseManager;
import com.ua.smarterama.andrey.leus.CRM.view.View;

public class ConnectToDataBase implements Command {

    private DataBaseManager manager;
    private View view;

    public ConnectToDataBase(DataBaseManager manager, View view) {
        this.manager = manager;
        this.view = view;

    }

    @Override
    public boolean canProcess(String command) {
        return command.equals("connect");
    }

    @Override
    public void process(String command) {

    }
}

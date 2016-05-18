package com.ua.smarterama.andrey.leus.CRM.controller.command;

import com.ua.smarterama.andrey.leus.CRM.model.DataBaseManager;
import com.ua.smarterama.andrey.leus.CRM.view.View;


public class IsConnected extends Command {
    public IsConnected(DataBaseManager manager, View view) {
        super(manager, view);
    }

//    private DataBaseManager manager;
//    private View view;
//
//    public IsConnected(DataBaseManager manager, View view) {
//        this.manager = manager;
//        this.view = view;
//    }

    @Override
    public boolean canProcess(String command) {
        return !manager.isConnected();
    }

    @Override
    public void process() {
        view.write("Oops... Please connect to database!");

    }
}

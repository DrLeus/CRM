package com.ua.smarterama.andrey.leus.CRM.controller.command;

import com.ua.smarterama.andrey.leus.CRM.model.DataBaseManager;

public class IsConnected extends Command {
    public IsConnected(DataBaseManager manager) {
                super(manager);
    }

    @Override
    public boolean canProcess(String command) {
        return !manager.isConnected();
    }

    @Override
    public void process() {
        view.write("Oops... Please connect to database!");
    }
}

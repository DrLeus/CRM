package com.ua.smarterama.andrey.leus.CRM.controller.command;


import com.ua.smarterama.andrey.leus.CRM.model.DataBaseManager;
import com.ua.smarterama.andrey.leus.CRM.view.Console;
import com.ua.smarterama.andrey.leus.CRM.view.View;

import java.sql.Connection;

public abstract class Command {

    protected DataBaseManager manager;
    protected Console view;
    protected Connection connection;

    public Command(Console view) {
        this.view = view;
    }

    public Command() {
    }

    public Command(DataBaseManager manager, Console view) {

        this.manager = manager;
        this.view = view;
    }

    public abstract boolean canProcess(String command);

    public abstract void process();
}
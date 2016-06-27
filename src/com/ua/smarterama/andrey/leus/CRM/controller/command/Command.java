package com.ua.smarterama.andrey.leus.CRM.controller.command;


import com.ua.smarterama.andrey.leus.CRM.model.DataBaseManager;
import com.ua.smarterama.andrey.leus.CRM.view.Console;

import java.sql.SQLException;


public abstract class Command {

    protected DataBaseManager manager;
    protected Console view;

    public Command() {
        this.view = Console.getInstance();
    }

    public Command(DataBaseManager manager) {

        this.manager = manager;
        this.view = Console.getInstance();
    }

    public abstract boolean canProcess(String command);

    public abstract void process();
}
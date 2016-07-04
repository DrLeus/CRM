package com.ua.smarterama.andrey.leus.CRM.controller.command;


import com.ua.smarterama.andrey.leus.CRM.model.DataBaseManager;
import com.ua.smarterama.andrey.leus.CRM.view.Console;


public abstract class Command {

    protected DataBaseManager manager;
    protected Console view;

    public Command(Console view) {
        this.view = view;
    }

    public Command(DataBaseManager manager, Console view) {

        this.manager = manager;
        this.view = view;
    }

    public abstract boolean canProcess(String command);

    public abstract void process();
}
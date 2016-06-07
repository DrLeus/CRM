package com.ua.smarterama.andrey.leus.CRM.controller.command;


import com.ua.smarterama.andrey.leus.CRM.model.DataBaseManager;
import com.ua.smarterama.andrey.leus.CRM.view.View;


public abstract class Command {

    protected DataBaseManager manager;
    protected View view;

    public Command(View view) {
        this.view = view;
    }

    public Command() {
    }

    public Command(DataBaseManager manager, View view) {

        this.manager = manager;
        this.view = view;
    }

    public abstract boolean canProcess(String command);

    public abstract void process();
}
package com.ua.smarterama.andrey.leus.CRM.controller.command;


import com.ua.smarterama.andrey.leus.CRM.model.DataBaseManager;
import com.ua.smarterama.andrey.leus.CRM.view.View;

import java.sql.Connection;

public abstract class Command {

    protected DataBaseManager manager;
    protected View view;
    protected Connection connection;

    public Command(View view) {
        this.view = view;
    }

    public Command() {
    }

    public Command(View view, DataBaseManager manager) {
        this.view = view;
        this.manager = manager;
    }

    public abstract boolean canProcess(String command);

    public abstract void process();
}
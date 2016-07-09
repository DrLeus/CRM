package ua.com.smart.andrey.leus.CRM.controller.command;


import ua.com.smart.andrey.leus.CRM.model.CRMException;
import ua.com.smart.andrey.leus.CRM.model.DataBaseManager;
import ua.com.smart.andrey.leus.CRM.view.Console;

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

    public abstract boolean canProcess(String command) throws CRMException;

    public abstract void process() throws CRMException;
}
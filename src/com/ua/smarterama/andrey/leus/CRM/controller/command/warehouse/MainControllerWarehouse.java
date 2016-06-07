package com.ua.smarterama.andrey.leus.CRM.controller.command.warehouse;

import com.ua.smarterama.andrey.leus.CRM.controller.command.*;
import com.ua.smarterama.andrey.leus.CRM.controller.command.tables.Catalog;
import com.ua.smarterama.andrey.leus.CRM.model.DataBaseManager;
import com.ua.smarterama.andrey.leus.CRM.view.Console;

import java.util.LinkedList;
import java.util.List;

public class MainControllerWarehouse {


    private Console view;
    private Command[] commands;
    private List<String> history = new LinkedList<>();

    public MainControllerWarehouse(Console view, DataBaseManager manager) {
        this.view = view;
        this.commands = new Command[]{
                new ConnectToDataBase(manager, view),
                new Help(view),
                new Exit(view),
                new IsConnected(manager, view),
                new CreateDatabase(manager, view),
                new DropDataBase(manager, view),
                new Catalog(manager, view),
                new report(manager, view),
                new SelectDataBase(manager, view),
                new store(manager, view),
                new Writeoff(manager, view),
                new Unsupported(view),
        };
    }


    public void run() throws Exception {

        InitialDB_CRM.setupTempDates();

        Help.getHelp();

        try {
            doWork();
        } catch (ExitException e) {
//            System.exit(0);
        }
    }

    public void doWork() {

        while (true) {

            view.write("Please input command: \n");

            String input = view.read();

            for (Command command : commands) {
                try {
                    if (command.canProcess(input)) {
                        command.process();
                        history.add(input);
                        break;
                    }
                } catch (Exception e) {
                    if (e instanceof ExitException) {
                        throw e;
                    }
                    e.printStackTrace();
                    break;
                }
            }
        }
    }
}
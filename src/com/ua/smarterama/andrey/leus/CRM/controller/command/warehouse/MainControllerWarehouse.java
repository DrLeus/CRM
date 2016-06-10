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

    public MainControllerWarehouse(DataBaseManager manager) {
        this.view = Console.getInstance();
        this.commands = new Command[]{
                new ConnectToDataBaseCRM(manager),
                new Help(),
                new Exit(),
                new IsConnected(manager),
                new CreateDatabase(manager),
                new DropDataBase(manager),
                new Catalog(manager),
                new Report(manager),
                new SelectDataBase(manager),
                new Store(manager),
                new Writeoff(manager),
                new Unsuported(),
        };
    }

    public void run(DataBaseManager manager) throws Exception {

        InitialDB_CRM.setupTempDates(manager);

        for (Command command : commands) {
            if (command.canProcess("connectCRM")) {
                command.process();
                break;
            }
        }

        view.write(Help.getHelp());
        InitialDB_CRM.getAddComands();

        try {
            doWork();
        } catch (ExitException e) { }
    }

    public void doWork() {

        while (true) {
            view.write("Please input command (or 'help'): \n");

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
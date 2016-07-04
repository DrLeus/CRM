package com.ua.smarterama.andrey.leus.CRM.controller.command.warehouse;

import com.ua.smarterama.andrey.leus.CRM.controller.command.*;
import com.ua.smarterama.andrey.leus.CRM.controller.command.tables.Catalog;
import com.ua.smarterama.andrey.leus.CRM.model.DataBaseManager;
import com.ua.smarterama.andrey.leus.CRM.view.Console;

import java.sql.SQLException;

public class MainControllerWarehouse {

    private Console view;
    private Command[] commands;

    public MainControllerWarehouse(DataBaseManager manager, Console view) {
        this.view = view;
        this.commands = new Command[]{
                new ConnectToDataBaseCRM(manager, view),
                new ConnectToDataBase(manager, view),
                new Help(view),
                new Exit(view),
                new IsConnected(manager, view),
                new CreateDatabase(manager, view),
                new DropDataBase(manager, view),
                new Catalog(manager, view),
                new Report(manager, view),
                new SelectDataBase(manager, view),
                new Store(manager, view),
                new Writeoff(manager, view),
                new Unsuported(view),
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

    public void doWork() throws SQLException {

        while (true) {
            view.write("Please input command (or 'help'): \n");

            String input = view.read();

            for (Command command : commands) {
                try {
                    if (command.canProcess(input)) {
                        command.process();
                        if (input.equals("help")){
                            InitialDB_CRM.getAddComands();
                        }
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
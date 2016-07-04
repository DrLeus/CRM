package com.ua.smarterama.andrey.leus.CRM.controller.command;

import com.ua.smarterama.andrey.leus.CRM.controller.command.tables.Catalog;
import com.ua.smarterama.andrey.leus.CRM.model.DataBaseManager;
import com.ua.smarterama.andrey.leus.CRM.view.Console;

import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

public class MainController {


    private Console view;
    private Command[] commands;
    private List<String> history = new LinkedList<>();

    public MainController(DataBaseManager manager, Console view) {
        this.view = view;
        this.commands = new Command[]{
                new ConnectToDataBase(manager, view),
                new Help(view),
                new Exit(view),
                new IsConnected(manager, view),
                new CreateDatabase(manager, view),
                new DropDataBase(manager, view),
                new Catalog(manager, view),
                new SelectDataBase(manager, view),
                new Unsuported(view),
        };
    }

    public void run() throws Exception {

        view.write(Help.getHelp());
        Help.getConnect();

        try {
            doWork();
        } catch (ExitException e) {
//            System.exit(0);
//            throw new ExitException();
        }
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
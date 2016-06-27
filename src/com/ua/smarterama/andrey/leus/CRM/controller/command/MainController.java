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

    public MainController(DataBaseManager manager) {
        this.view = Console.getInstance();
        this.commands = new Command[]{
                new ConnectToDataBase(manager),
                new Help(),
                new Exit(),
                new IsConnected(manager),
                new CreateDatabase(manager),
                new DropDataBase(manager),
                new Catalog(manager),
                new SelectDataBase(manager),
                new Unsuported(),
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
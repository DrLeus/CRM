package com.ua.smarterama.andrey.leus.CRM.controller;

import com.ua.smarterama.andrey.leus.CRM.controller.command.*;
import com.ua.smarterama.andrey.leus.CRM.model.DataBaseManager;
import com.ua.smarterama.andrey.leus.CRM.view.View;

public class MainController {


    private View view;
        private Command[] commands;

    public MainController(View view, DataBaseManager manager) {
        this.view = view;
        this.commands = new Command[]{
                new ConnectToDataBase(manager, view),
                new Help(view),
                new Exit(view),
                new IsConnected(manager, view),
                new CreateDatabase(manager, view),
                new DropDataBase(manager, view),
                new Catalog(manager, view),
                new Report(manager,view),
                new SelectDataBase(manager, view),
                new Store(view),
                new Writeoff(view),
                new Unsupported(view),
        };
    }

    public MainController() {

    }

    public void run() throws Exception {

        Help.getHelp();

        while (true) {

            view.write("\nВведите команду: ");

            String input = view.read();

            for (Command command : commands) {
                try {
                    if (command.canProcess(input)) {
                        command.process();
                        break;
                    }
                } catch (Exception e) {
                    view.error("Error", e);
                    break;
                }
            }
        }
    }
}
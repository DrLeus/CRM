package com.ua.smarterama.andrey.leus.CRM.controller;

import com.ua.smarterama.andrey.leus.CRM.controller.command.*;
import com.ua.smarterama.andrey.leus.CRM.model.DataBaseManager;
import com.ua.smarterama.andrey.leus.CRM.view.Console;
import com.ua.smarterama.andrey.leus.CRM.view.View;

public class MainController {


    private DataBaseManager manager;
    private View view;
        private Command[] commands;

    public MainController(View view, DataBaseManager manager) {
        this.view = view;
        this.manager = manager;
        this.commands = new Command[]{
                new ConnectToDataBase(view, manager),
                new Catalog(view),
                new Help(view),
                new Exit(view),
                new Report(view),
                new SelectDataBase(),
                new Store(view),
                new Writeoff(view),
                new Unsupported(view)
        };
    }

    public MainController() {

    }

    public void run(MainController controller, DataBaseManager manager) throws Exception {

        Help.getHelp();

        while (true) {

            view.write("Введите команду: ");

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
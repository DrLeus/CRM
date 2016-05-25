package com.ua.smarterama.andrey.leus.CRM.controller.command;

import com.ua.smarterama.andrey.leus.CRM.view.Console;
import com.ua.smarterama.andrey.leus.CRM.view.View;


public class Exit extends Command {

    public Exit(Console view) {
        super(view);
    }

    @Override
    public boolean canProcess(String command) {
        return command.equals("exit");
    }

    @Override
    public void process() {
        view.write("\nSee you again!");
        System.exit(0);
    }
}

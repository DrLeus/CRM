package com.ua.smarterama.andrey.leus.CRM.controller.command;

import com.ua.smarterama.andrey.leus.CRM.view.Console;

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
        view.write("See you again!\n");
        throw new ExitException();
    }
}

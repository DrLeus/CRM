package com.ua.smarterama.andrey.leus.CRM.controller.command;

public class Exit extends Command {

    public Exit() {
        super();
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

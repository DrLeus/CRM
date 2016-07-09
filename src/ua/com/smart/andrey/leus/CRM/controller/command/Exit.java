package ua.com.smart.andrey.leus.CRM.controller.command;

import ua.com.smart.andrey.leus.CRM.view.Console;

public class Exit extends Command {

    public Exit(Console view) {
        super(view);
    }

    @Override
    public boolean canProcess(String command) {
        return "exit".equals(command);
    }

    @Override
    public void process() {
        view.write("See you again!\n");
        throw new ExitException();
    }
}

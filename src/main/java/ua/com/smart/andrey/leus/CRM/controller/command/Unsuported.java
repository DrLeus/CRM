package ua.com.smart.andrey.leus.CRM.controller.command;

import ua.com.smart.andrey.leus.CRM.view.View;

public class Unsuported extends Command {

    public Unsuported(View view) {
        super(view);
    }

    @Override
    public boolean canProcess(String command) {
        return true;
    }

    @Override
    public void process() {
        view.write("Oops...incorrect command!\n");
    }
}

package com.ua.smarterama.andrey.leus.CRM.controller.command;

public class Unsuported extends Command {

    public Unsuported() {
        super();
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

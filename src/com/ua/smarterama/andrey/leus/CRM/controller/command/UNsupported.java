package com.ua.smarterama.andrey.leus.CRM.controller.command;

public class Unsupported extends Command {

    public Unsupported() {
        super();
    }

    @Override
    public boolean canProcess(String command) {
        return true;
    }

    @Override
    public void process() {
        view.write("\nOops...incorrect command!");
    }
}

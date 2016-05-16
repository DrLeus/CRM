package com.ua.smarterama.andrey.leus.CRM.controller.command;

public class DropDataBase extends Command {
    @Override
    public boolean canProcess(String command) {
        return false;
    }

    @Override
    public void process() {

    }
}

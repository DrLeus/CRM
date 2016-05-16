package com.ua.smarterama.andrey.leus.CRM.controller.command;

/**
 * Created by Admin on 16.05.2016.
 */
public class DropDataBase implements Command {
    @Override
    public boolean canProcess(String command) {
        return false;
    }

    @Override
    public void process(String command) {

    }
}

package com.ua.smarterama.andrey.leus.CRM.controller.command;

/**
 * Created by Admin on 11.05.2016.
 */
public interface Command {

    boolean canProcess(String command);

    void process(String command);
}
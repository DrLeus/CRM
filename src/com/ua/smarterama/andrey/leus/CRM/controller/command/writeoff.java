package com.ua.smarterama.andrey.leus.CRM.controller.command;

import com.ua.smarterama.andrey.leus.CRM.view.View;

/**
 * Created by Admin on 11.05.2016.
 */
public class Writeoff implements Command {
    private View view;

    public Writeoff(View view) {
        this.view = view;

    }

    @Override
    public boolean canProcess(String command) {
        return false;
    }

    @Override
    public void process(String command) {

    }
}
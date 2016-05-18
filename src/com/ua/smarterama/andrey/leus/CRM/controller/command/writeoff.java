package com.ua.smarterama.andrey.leus.CRM.controller.command;

import com.ua.smarterama.andrey.leus.CRM.view.View;

public class Writeoff extends Command {
    private View view;

    public Writeoff(View view) {
        this.view = view;

    }

    @Override
    public boolean canProcess(String command) {
        return false;
    }

    @Override
    public void process() {

    }
}

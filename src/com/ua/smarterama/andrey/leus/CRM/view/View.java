package com.ua.smarterama.andrey.leus.CRM.view;

public interface View {

    void write(String message);

    String read();

    boolean checkExit(String input);
}

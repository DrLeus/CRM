package com.ua.smarterama.andrey.leus.CRM.view;

public interface View {

    void write(String message);

    public void error(String message, Exception e);

    String read();
}

package com.ua.smarterama.andrey.leus.CRM.view;

import java.util.List;

public interface View {

    void write(String message);

    void error(String message, Exception e);

    String read();

    String checkExit(String input);


}

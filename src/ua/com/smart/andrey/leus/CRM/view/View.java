package ua.com.smart.andrey.leus.CRM.view;

import ua.com.smart.andrey.leus.CRM.model.CRMException;

import java.io.IOException;

public interface View {

    void write(String message);

    String read() throws IOException, CRMException;

    boolean checkExit(String input);
}

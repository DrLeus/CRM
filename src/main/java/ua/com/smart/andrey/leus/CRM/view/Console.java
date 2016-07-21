package ua.com.smart.andrey.leus.CRM.view;

import ua.com.smart.andrey.leus.CRM.model.CRMException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.MissingFormatArgumentException;

public class Console implements View {

    public void write(String message) {
        System.out.println(message);
    }

    public String read() throws CRMException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        try {
            return reader.readLine();
        } catch (IOException e) {
            throw new CRMException("IO problem in case", e);
        }
    }


}


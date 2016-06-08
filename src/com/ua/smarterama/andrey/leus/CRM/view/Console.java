package com.ua.smarterama.andrey.leus.CRM.view;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Console implements View {

    public void write(String message) {
        System.out.println(message);
    }

    public void error(String message, Exception e) {
        System.err.println(message + e);
    }

    public String read() {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        try {
            return reader.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "error Console";
    }

    public String checkExit(String input) {
        return input;
    }

    public boolean checkExitB(String input) {
        if (input.equals("exit") | input.equals("return")) {
            return true;
        }
        return false;
    }

    private static Console  instance = null;

    private Console() {}

    public static synchronized Console getInstance() {
        if ( instance == null)
            instance = new Console();
        return instance;
    }
}


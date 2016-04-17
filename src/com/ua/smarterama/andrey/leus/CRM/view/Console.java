package com.ua.smarterama.andrey.leus.CRM.view;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;

public class Console implements View {

    public void write(String message) {
        System.out.println(message);
    }

    public void error(String message, Exception e) {
        System.err.println(String.format(message + e));
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

}

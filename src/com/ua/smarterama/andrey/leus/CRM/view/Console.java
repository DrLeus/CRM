package com.ua.smarterama.andrey.leus.CRM.view;


import com.ua.smarterama.andrey.leus.CRM.controller.Main;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.MissingFormatArgumentException;
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

    public String checkExit(String input) {
        if (input.equals("exit")) {
//            System.out.println("\nReturn to main menu!");
            System.out.println("\n");
            System.out.println("See you again!");
            try {
                System.exit(0);
//                Main.main(new String[0]);//TODO return to main menu
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return input;
    }



}


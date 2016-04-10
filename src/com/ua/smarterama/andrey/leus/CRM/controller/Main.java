package com.ua.smarterama.andrey.leus.CRM.controller;

import com.ua.smarterama.andrey.leus.CRM.view.View;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.sql.*;
import java.util.Random;
import java.util.Scanner;

public class Main {

    private static Connection connection;
//    private View view;

    public static void main(String[] argv) throws ClassNotFoundException, SQLException {
        Class.forName("org.postgresql.Driver");
        connection = DriverManager.getConnection(
                "jdbc:postgresql://localhost:5432/CRM", "postgres",
                "postgres");

        // insert temp data
        Statement stmt = connection.createStatement();
        stmt.executeUpdate("DROP TABLE public.goods");
        stmt.executeUpdate("CREATE TABLE goods(" +
                "id INTEGER PRIMARY KEY," +
                "code TEXT UNIQUE NOT NULL, " +
                "codeprevious TEXT UNIQUE NOT NULL, " +
                "name TEXT NOT NULL, " +
                "net_price TEXT NOT NULL, " +
                "cusmomer_price TEXT NOT NULL, " +
                "id_groups INTEGER /*PRIMARY KEY*/ NOT NULL)");
        stmt.executeUpdate("INSERT INTO public.goods (id, code, codeprevious, name, net_price, cusmomer_price, id_groups)" +
                "VALUES (1, 'H77435', '583327893', 'SEAL SV-25 EPDM CAT 2', '2,04', '22,88', '1')");
        stmt.executeUpdate("INSERT INTO public.goods (id, code, codeprevious, name, net_price, cusmomer_price, id_groups)" +
                "VALUES (2, 'H77459', '583337893', 'SEAL SV-40 EPDM CAT 2', '2,35', '25,27', '1')");
        stmt.executeUpdate("INSERT INTO public.goods (id, code, codeprevious, name, net_price, cusmomer_price, id_groups)" +
                "VALUES (3, 'H77484', '583342893', 'SEAL SV-50 EPDM CAT 2', '2,50', '27,75', '1')");
        stmt.executeUpdate("INSERT INTO public.goods (id, code, codeprevious, name, net_price, cusmomer_price, id_groups)" +
                "VALUES (4, 'H77509', '583347893', 'SEAL SV-65 EPDM CAT 2', '4,00', '30,59', '1')");
        stmt.executeUpdate("INSERT INTO public.goods (id, code, codeprevious, name, net_price, cusmomer_price, id_groups)" +
                "VALUES (5, 'H77539', '583352893', 'SEAL SV-80 EPDM CAT 2', '2,99', '42,56', '1')");
        stmt.close();


        doHelp();


        while (true) {

//            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
//            String input = String.valueOf(reader);

            Scanner scanner = new Scanner(System.in);
            String input = scanner.nextLine();

            if (input.equals("addRT")) {
//                doList();
            } else if (input.equals("incoming")) {
//                doFind(command);
            } else if (input.equals("addInvoice")) {
//                doFind(command);
            } else if (input.equals("disposal")) {
//                doFind(command);
            } else if (input.equals("report")) {
                reportGoods();
            } else if (input.equals("listRTs")) {
//                doFind(command);
            } else if (input.equals("listInvoices")) {
//                doFind(command);
            } else if (input.equals("help")) {
                doHelp();
            } else if (input.equals("exit")) {
//                view.write("До скорой встречи!");
                System.exit(0);
            } else {
                System.out.println("Несуществующая команда: " + input);
            }
        }

        // select

//
//        // table names
//        stmt = connection.createStatement();
//        rs = stmt.executeQuery("SELECT table_name FROM information_schema.tables WHERE table_schema='public' AND table_type='BASE TABLE'");
//        while (rs.next()) {
//            System.out.println(rs.getString("table_name"));
//        }
//        rs.close();
//        stmt.close();
//
//        // delete
//        stmt = connection.createStatement();
//        stmt.executeUpdate("DELETE FROM public.user " +
//                "WHERE id > 10 AND id < 100");
//        stmt.close();
//
//        // update
//        PreparedStatement ps = connection.prepareStatement(
//                "UPDATE public.user SET password = ? WHERE id > 3");
//        String pass = "password_" + new Random().nextInt();
//        ps.setString(1, pass);
//        ps.executeUpdate();
//        ps.close();

//        connection.close();
    }

    private static void doHelp() {
        System.out.print("Данный модуль позволяет реализовать следующие операции:\n" +
                "- создание приходной накладной: команда “addRT”;\n" +
                "- оприходование товара: команда “incoming” затем ввести id приходной накладной \n" +
                "- создание расходной накладной: команда “addInvoice”\n" +
                "- списание товар: команда “disposal” затем ввести id расходной накладной \n" +
                "- отчет об остатках на складах: команда “report”\n" +
                "- предоставление списка приходных накладных: команда “listRTs”\n" +
                "- предоставление списка расходных накладных: команда “listInvoices”\n" +
                "Дополнительные команды (list, add, update, delete) доступны в соответствующих разделах.\n" +
                "Для вызова справки введите “help”.\n" +
                "Команда “exit” позволяет выйти на уровень вверх или из модуля.\n" +
                "Введите команду:\n");
    }

    private static void reportGoods() throws SQLException {
        Statement stmt;
        stmt = connection.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT * FROM public.goods");
        System.out.println("id: \t code: \t codeprevious: \t name: \t\t\t\t net_price: \t cusmomer_price: \t id_groups:");
        while (rs.next()) {
            System.out.print(rs.getString("id"));
            System.out.print("\t\t" + rs.getString("code"));
            System.out.print("\t\t" + rs.getString("codeprevious"));
            System.out.print("\t" + rs.getString("name"));
            System.out.print("\t" + rs.getString("net_price"));
            System.out.print("\t\t\t" + rs.getString("cusmomer_price"));
            System.out.println("\t\t\t\t" + rs.getString("id_groups"));
        }
        rs.close();
        stmt.close();
    }
}

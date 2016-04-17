package com.ua.smarterama.andrey.leus.CRM.controller;

import com.ua.smarterama.andrey.leus.CRM.model.Reports;
import com.ua.smarterama.andrey.leus.CRM.model.InitialDB;
import com.ua.smarterama.andrey.leus.CRM.view.View;

import java.sql.*;

public class MainController {

    private Connection connection;
    private View view;
    private Reports report = new Reports();
    private InitialDB initialDB = new InitialDB();

    public MainController(View view) {
        this.view = view;
    }

    public void run() throws SQLException, ClassNotFoundException {

        connect();

        initialDB.setupTempDates(connection);

        doHelp();

        while (true) {

            String input = view.read();

            if (input.equals("addRT")) {
//                doList();
            } else if (input.equals("incoming")) {
//                doFind(command);
            } else if (input.equals("addInvoice")) {
//                doFind(command);
            } else if (input.equals("disposal")) {
//                doFind(command);
            } else if (input.equals("report")) {
                report.reportGoods(connection);
            } else if (input.equals("listIn")) {
                report.reportListIncommingInvoices(connection);
            } else if (input.equals("listOut")) {
//                doFind(command);
            } else if (input.equals("help")) {
                doHelp();
            } else if (input.equals("exit")) {
                view.write("До скорой встречи!");
                System.exit(0);
            } else {
                System.out.println("Несуществующая команда: " + input);
            }
        }
    }

    private void connect() throws ClassNotFoundException, SQLException {
        Class.forName("org.postgresql.Driver");
        connection = DriverManager.getConnection(
                "jdbc:postgresql://localhost:5432/CRM", "postgres",
                "postgres");
    }

    private static void doHelp() {
        System.out.print("Данный модуль позволяет реализовать следующие операции:\n" +
                "- создание приходной накладной: команда “addRT”;\n" +
                "- оприходование товара: команда “incoming” затем ввести id приходной накладной \n" +
                "- создание расходной накладной: команда “addInvoice”\n" +
                "- списание товар: команда “disposal” затем ввести id расходной накладной \n" +
                "- отчет об остатках на складах: команда “report”\n" +
                "- предоставление списка приходных накладных: команда “listIn”\n" +
                "- предоставление списка расходных накладных: команда “listOut”\n" +
                "Дополнительные команды (list, add, update, delete) доступны в соответствующих разделах.\n" +
                "Для вызова справки введите “help”.\n" +
                "Команда “exit” позволяет выйти на уровень вверх или из модуля.\n" +
                "Введите команду:\n");
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
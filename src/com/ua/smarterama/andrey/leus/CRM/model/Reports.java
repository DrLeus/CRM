package com.ua.smarterama.andrey.leus.CRM.model;

import com.ua.smarterama.andrey.leus.CRM.controller.MainController;
import com.ua.smarterama.andrey.leus.CRM.view.View;

import java.sql.*;

public class Reports {

//    private View view;
    private MainController controller;

    public void commanderCatalog(Connection connection, View view, MainController controller) throws SQLException, ClassNotFoundException {

        doHelpCatalog(view);

        while (true) {

            view.write("\nВведите команду: ");

            String input = view.read();

            if (input.equals("list")) {
                doList(connection);
            } else if (input.equals("add")) {
                add(connection, view);
            } else if (input.equals("update")) {
//                report.reportGoods(connection);
            } else if (input.equals("delete")) {
//                report.catalog(connection);
            } else if (input.equals("help")) {
                doHelpCatalog(view);
            } else if (input.equals("exit")) {
                controller.mainCommander(controller);
            } else {
                System.out.println("\nНесуществующая команда: " + input);
            }
        }
    }

    private void add(Connection connection, View view) {

        view.write("\nВведите код товара в формате Н75968");
        String cod = view.read();

        view.write("Введите старый код товара 583327893");
        String oldCode = view.read();

        view.write("Введите наименование товара");
        String names = view.read();

        view.write("Введите net_price товара, в формате 22,22");
        String net_prices = view.read();

        view.write("Введите customer_price товара, в формате 22,22");
        String customer_prices = view.read();

        view.write("Введите группу товара");
        int id_group = Integer.parseInt(view.read());




        try {
            Statement stmt = connection.createStatement();

            stmt.executeUpdate("INSERT INTO public.goods (code, codeprevious, name, net_price, cusmomer_price, id_groups)" +
                    "VALUES ('" + cod + "', '" + oldCode + "', '" + names + "', '" + net_prices + "', '" +
                    customer_prices + "', " + id_group + ")");
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void doHelpCatalog(View view) {
        view.write("\nМодуль catalog позволяет просмотреть каталог товаров,\n доступные команды:\n" +
                "- вывести каталог товаров: команда “list”;\n" +
                "- добавить товар в каталог: команда “add” затем ввести (каждое поле через Enter):\n" +
                "  \t код, старый код, имя товара, входящая цена, цена отпускная, группа товара);\n" +
                "- изменить товар в каталоге: команда “update” затем ввести id товара, далее\n" +
                " \t ввести (каждое поле через Enter): код, старый код, имя товара, входящая цена,\n" +
                " \t цена отпускная, группа товара (пустая строка поле не изменяет;\n" +
                "- удалить товар в справочнике: команда “delete” затем ввести id товара (delete/id);\n" +
                "\nДля вызова справки введите “help”.\n" +
                "Команда “exit” позволяет вернуться в предыдущее меню.\n");
    }


    public void doList(Connection connection) throws SQLException, ClassNotFoundException {

        Statement stmt;
        stmt = connection.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT * FROM public.goods");
        System.out.println("id: \t code: \t codeprevious: \t name: \t\t\t\t net_price: \t customer_price: \t id_groups:");
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

    public void reportIncommingOrders(Connection connection) throws SQLException {
        Statement stmt;
        stmt = connection.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT * FROM public.ListIncomingOrders");
        System.out.println("\n INCOMING INVOICES \n");
        System.out.println("id: \t name supplier: \t\t data: \t\t\t transport operator: \t\t responsible person:");
        while (rs.next()) {
            System.out.print(String.format("%-8s", rs.getString("id")));
            System.out.print(String.format("%-16s", rs.getString("name")));
        System.out.print(String.format("%-32s", rs.getString("data")));
        System.out.print(String.format("%-24s", rs.getString("transport")));
        System.out.println(String.format("%-16s", rs.getString("response_person")));


//            rs = stmt.executeQuery("SELECT * FROM public.incominggoods");

        }
        rs.close();
        stmt.close();
    }
}

//System.out.print(rs.getString("id"));
//        System.out.print("\t\t\t" + rs.getString("name"));
//        System.out.print("\t\t" + rs.getString("data"));
//        System.out.print("\t\t\t" + rs.getString("transport"));
//        System.out.println("\t\t\t\t" + rs.getString("response_person"));


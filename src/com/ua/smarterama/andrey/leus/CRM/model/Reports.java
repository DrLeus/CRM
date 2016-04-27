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
            } else if (input.equals("fexit")) {
                System.exit(0);
            } else {
                System.out.println("\nНесуществующая команда: " + input);
            }
        }
    }

    private void add(Connection connection, View view) {

        view.write("Для внесения нового товара неоходимо ввести (каждое поле через Enter):\n" +
        "  \t код, старый код, имя товара, входящая цена, цена отпускная, группа товара;\n\n" +
                "\t команда exit прерывает внесение данных и возвращает в модуль catalog \n");

        view.write("\nВведите код товара в формате Н75968");
        String code = view.read();
        if (code.equals("exit")){doHelpCatalog(view);return;}
        else if (code.isEmpty()) { view.write("\n\nПоле не может быть пустым, начните заново\n");
            add(connection,view);return;}

        view.write("Введите старый код товара в формате 583327893");
        String oldCode = view.read();
        if (oldCode.equals("exit")){doHelpCatalog(view);return;}

        view.write("Введите наименование товара");
        String name = view.read();
        if (name.equals("exit")){doHelpCatalog(view);return;}
        else if (name.isEmpty()) { view.write("\nПоле не может быть пустым, начните заново\n");
            add(connection,view);return;}

        view.write("Введите net_price товара, в формате 22,22");
        String net_price = view.read();
        if (net_price.equals("exit")){doHelpCatalog(view);return;}
        else if (net_price.isEmpty()) { view.write("\n\nПоле не может быть пустым, начните заново\n");
            add(connection,view);return;}

        view.write("Введите customer_price товара, в формате 22,22");
        String customer_price = view.read();
        if (customer_price.equals("exit")){doHelpCatalog(view);return;}
        else if (customer_price.isEmpty()) { view.write("\n\nПоле не может быть пустым, начните заново\n");
            add(connection,view);return;}

        view.write("Введите группу товара");
        int id_group = Integer.parseInt(view.read());
        if ((String.valueOf(id_group)).equals("exit")){doHelpCatalog(view);return;}
        else if ((String.valueOf(id_group)).isEmpty()) { view.write("\n\nПоле не может быть пустым, начните заново\n");
            add(connection,view);return;}


        try {
            Statement stmt = connection.createStatement();

            stmt.executeUpdate("INSERT INTO public.goods (code, codeprevious, name, net_price, cusmomer_price, id_groups)" +
                    "VALUES ('" + code + "', '" + oldCode + "', '" + name + "', '" + net_price + "', '" +
                    customer_price + "', " + id_group + ")");
            stmt.close();
            view.write("\nТовар успешно добавлен");
            add(connection,view);return;
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void doHelpCatalog(View view) {
        view.write("\nМодуль catalog позволяет просмотреть каталог товаров,\n доступные команды:\n" +
                "- вывести каталог товаров: команда “list”;\n" +
                "- добавить товар в каталог: команда “add” " +
                "- изменить товар в каталоге: команда “update” затем ввести id товара (list), далее\n" +
                " \t ввести (каждое поле через Enter): код, старый код, имя товара, входящая цена,\n" +
                " \t цена отпускная, группа товара (пустая строка поле не изменяет;\n" +
                " \t команда exit прерывает внесение данных\n" +
                "- удалить товар в справочнике: команда “delete” затем ввести id товара (list);\n" +
                " \t команда exit прерывает внесение данных\n" +
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


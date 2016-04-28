package com.ua.smarterama.andrey.leus.CRM.model;

import com.ua.smarterama.andrey.leus.CRM.controller.MainController;
import com.ua.smarterama.andrey.leus.CRM.view.View;

import java.sql.*;

public class Catalog implements Model {

//    private View view;
    private MainController controller;

    public void commander(Connection connection, View view, MainController controller) throws SQLException, ClassNotFoundException {

        help(view);

        while (true) {

            view.write("\nВведите команду: ");

            String input = view.read();

            if (input.equals("list")) {
                list(connection);
            } else if (input.equals("add")) {
                add(connection, view);
            } else if (input.equals("update")) {
                update(connection, view);
            } else if (input.equals("del")) {
                delete(connection, view);
            } else if (input.equals("help")) {
                help(view);
            } else if (input.equals("exit")) {
                controller.commander(controller);
            } else {
                System.out.println("\nНесуществующая команда: " + input);
            }
        }
    }

    private void help(View view) {
        view.write("\nМодуль 'catalog' позволяет просмотреть каталог товаров,\n доступные команды:\n" +
                "- вывести каталог товаров: команда 'list';\n" +
                "- добавить товар в каталог: команда 'add';\n " +
                "- изменить товар в каталоге: команда 'update';\n" +
                "- удалить товар в справочнике: команда 'del'.\n" +
                "\nДля вызова справки введите “help”.\n" +
                "Команда “exit” позволяет вернуться в предыдущее меню.\n");
    }

    public void list(Connection connection) throws SQLException, ClassNotFoundException {

        Statement stmt;
        stmt = connection.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT * FROM public.goods");
        System.out.printf("%4s%10s%16s%24s%16s%16s%12s%n","id","code","codeprevious","name","net_price","customer_price","id_groups");
        for (int i = 0; i < 98 ; i++) {System.out.print("-");}

        System.out.println();
        while (rs.next()) {
            System.out.printf("%4s%10s%16s%24s%16s%16s%12s%n",rs.getString("id"),rs.getString("code"),rs.getString("codeprevious"),
                    rs.getString("name"), rs.getString("net_price"), rs.getString("customer_price"), rs.getString("id_groups"));
        }
        rs.close();
        stmt.close();
    }

    public void add(Connection connection, View view) {

        view.write("Для внесения нового товара неоходимо ввести (каждое поле через Enter):\n" +
        "  \t код, старый код, имя товара, входящая цена, цена отпускная, группа товара;\n\n" +
                "\t команда exit прерывает внесение данных и возвращает в модуль catalog \n");

        view.write("\nВведите код товара в формате Н75968");
        String code = view.read();
        if (code.equals("exit")){
            help(view);return;}
        else if (code.isEmpty()) { view.write("\n\nПоле не может быть пустым, начните заново\n");
            add(connection,view);return;}

        view.write("Введите старый код товара в формате 583327893");
        String oldCode = view.read();
        if (oldCode.equals("exit")){
            help(view);return;}

        view.write("Введите наименование товара");
        String name = view.read();
        if (name.equals("exit")){
            help(view);return;}
        else if (name.isEmpty()) { view.write("\nПоле не может быть пустым, начните заново\n");
            add(connection,view);return;}

        view.write("Введите net_price товара, в формате 22,22");
        String net_price = view.read();
        if (net_price.equals("exit")){
            help(view);return;}
        else if (net_price.isEmpty()) { view.write("\n\nПоле не может быть пустым, начните заново\n");
            add(connection,view);return;}

        view.write("Введите customer_price товара, в формате 22,22");
        String customer_price = view.read();
        if (customer_price.equals("exit")){
            help(view);return;}
        else if (customer_price.isEmpty()) { view.write("\n\nПоле не может быть пустым, начните заново\n");
            add(connection,view);return;}

        view.write("Введите группу товара");
        String id_group = view.read();
        if (id_group.equals("exit")){
            help(view);return;}
        else if (id_group.isEmpty()) { view.write("\n\nПоле не может быть пустым, начните заново\n");
            add(connection,view);return;}


        try {
            Statement stmt = connection.createStatement();

            stmt.executeUpdate("INSERT INTO public.goods (code, codeprevious, name, net_price, customer_price, id_groups)" +
                    "VALUES ('" + code + "', '" + oldCode + "', '" + name + "', '" + net_price + "', '" +
                    customer_price + "', " + id_group + ")");
            stmt.close();
            view.write("\nТовар успешно добавлен");
            add(connection,view);return;
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public void delete(Connection connection, View view) throws SQLException, ClassNotFoundException {
        view.write("Для удаления товара неоходимо ввести id товара и подтвердить удаление.\n" +
                "\t Доступные команды:\n" +
                "  \t - вывести каталог товаров: команда “list”;\n\n" +
                "\t Команда exit прерывает изменение данных и возвращает в модуль catalog \n");

        view.write("\nВведите id товара в числовом формате");

        String line = view.read();

        if (line.equals("exit")){
            help(view);
            return;
        }
        else if (line.equals("list")){
            list(connection);
            delete(connection,view);
            return;
        }
        else if (line.isEmpty()) {
            view.write("\n\nПоле не может быть пустым введите id или exit для выхода из подмодуля\n");
            delete(connection,view);
            return;
        }

        int id;
        try {
            id = Integer.parseInt(line);
        }   catch (NumberFormatException e) {
            view.write("id не должно содержать никаких символов кроме чисел\n");
            delete(connection,view);
            return;
        }

        view.write("Вы подтверждаете удаление? (Y/N)");
         line = view.read();
        if (!line.equals("Y") | !line.equals("y")) {
            view.write("\n удаление товара не подтверждено\n");
            delete(connection,view);
            return;
        }


        try {
            Statement stmt = connection.createStatement();

            stmt.executeUpdate("DELETE FROM public.goods WHERE id=" + id );
            stmt.close();
            view.write("\nТовар успешно удален\n");
            delete(connection,view);return;
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void update(Connection connection, View view) throws SQLException, ClassNotFoundException {
        view.write("Для изменения товара неоходимо ввести (каждое поле через Enter):\n" +
                "  \t id, код, старый код, имя товара, входящая цена, цена отпускная, группа товара;\n" +
                "  \t если поле не подлежит изменению, нажимаем Enter.\n\n" +
                "\t Доступные команды:\n" +
                "  \t - вывести каталог товаров: команда “list”;\n\n" +
                "\t команда exit прерывает внесение данных и возвращает в модуль catalog \n");

        view.write("\nВведите id товара в числовом формате");

        String line = view.read();

        if (line.equals("exit")){
            help(view);
            return;
        }
        else if (line.equals("list")){
            list(connection);
            update(connection,view);
            return;
        }
        else if (line.isEmpty()) {
            view.write("\n\nПоле не может быть пустым введите id или exit для выхода из подмодуля\n");
            update(connection,view);
            return;
        }

        int id;
        try {
            id = Integer.parseInt(line);
        }   catch (NumberFormatException e) {
            view.write("id не должно содержать никаких символов кроме чисел\n");
            update(connection,view);
            return;
        }

        view.write("\nВведите код товара в формате Н75968");
        String code = view.read();
        if (code.equals("exit")){
            help(view);return;}
        else if (code.isEmpty()) {
            code = getDataFromTable(connection, id, "code");
            view.write(code+"\n");
        }

        view.write("Введите старый код товара в формате 583327893");
        String oldCode = view.read();
        if (oldCode.equals("exit")){
            help(view);return;}
        else if (oldCode.isEmpty()) {
            oldCode = getDataFromTable(connection, id, "codeprevious");
            view.write(oldCode+"\n");
        }

        view.write("Введите наименование товара");
        String name = view.read();
        if (name.equals("exit")){
            help(view);return;}
        else if (name.isEmpty()) {
            name = getDataFromTable(connection, id, "name");
            view.write(name+"\n");
        }

        view.write("Введите net_price товара, в формате 22,22");
        String net_price = view.read();
        if (net_price.equals("exit")){
            help(view);return;}
        else if (net_price.isEmpty()) {
            net_price = getDataFromTable(connection, id, "net_price");
            view.write(net_price+"\n");
        }

        view.write("Введите customer_price товара, в формате 22,22");
        String customer_price = view.read();
        if (customer_price.equals("exit")){
            help(view);return;}
        else if (customer_price.isEmpty()) {
            customer_price = getDataFromTable(connection, id, "customer_price");
            view.write(customer_price+"\n");
        }

        view.write("Введите группу товара");
        String id_group = view.read();
        if (id_group.equals("exit")){
            help(view);return;}
        else if (id_group.isEmpty()) {
            id_group = getDataFromTable(connection, id, "id_groups");
            view.write(id_group+"\n");
        }

        try {
            PreparedStatement pstmt = connection.prepareStatement("UPDATE public.goods SET ?=?, ?=?, ?=? WHERE id="+id);

//            pstmt.setInt(1, id);
            pstmt.setString(2, code);
            pstmt.setString(3, oldCode);
            pstmt.setString(4, name);
            pstmt.setString(5, net_price);
            pstmt.setString(6, customer_price);
            pstmt.setString(7, id_group);

            pstmt.executeUpdate();

            pstmt.close();

            view.write("\nТовар успешно изменен\n");
            list(connection, id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    private void list(Connection connection, int id) throws SQLException {
        Statement stmt;
        stmt = connection.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT * FROM public.goods WHERE id=" + id);
        System.out.printf("%4s%10s%16s%24s%16s%16s%12s%n","id","code","codeprevious","name","net_price","customer_price","id_groups");
        for (int i = 0; i < 98 ; i++) {System.out.print("-");}

        System.out.println();
        rs.next();
            System.out.printf("%4s%10s%16s%24s%16s%16s%12s%n",rs.getString("id"),rs.getString("code"),rs.getString("codeprevious"),
                    rs.getString("name"), rs.getString("net_price"), rs.getString("customer_price"), rs.getString("id_groups"));

        rs.close();
        stmt.close();
    }

    private String getDataFromTable(Connection connection, int id, String code) throws SQLException {
        Statement stmt = connection.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT " + code + " FROM public.goods WHERE id="+ id);
        rs.next();
        String result = rs.getString(code);
        rs.close();
        stmt.close();
        return result;
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


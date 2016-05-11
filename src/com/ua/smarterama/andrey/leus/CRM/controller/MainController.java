package com.ua.smarterama.andrey.leus.CRM.controller;

import com.sun.javaws.exceptions.ExitException;
import com.ua.smarterama.andrey.leus.CRM.controller.command.*;
import com.ua.smarterama.andrey.leus.CRM.model.CatalogGoods;
import com.ua.smarterama.andrey.leus.CRM.model.InitialDB;
import com.ua.smarterama.andrey.leus.CRM.model.Model;
import com.ua.smarterama.andrey.leus.CRM.view.View;

import java.sql.*;

public class MainController {

    private Connection connection;
    private View view;
    private Model report = new CatalogGoods(view);
    private InitialDB initialDB = new InitialDB();
    private Command[] commands;

    public MainController(View view) {
        this.view = view;
        this.commands = new Command[] {
                new Catalog(view),
                new Help(view),
                new Exit(view),
                new Report(view),
                new Store(view),
                new Writeoff(view),
                new Unsupported(view)
        };
    }

    public MainController() {
    }

    public void run(MainController controller) throws Exception {

        connect("CRM", "postgres","postgres");

//        initialDB.setupTempDates(connection);
//        System.exit(0);

 //        System.out.println(getAmountRowTable("goods")); // get quantity of rows of table used name of table
//         System.out.println(Arrays.toString(getTableData("suppliers"))); // get table

//        System.exit(0);
//        Report.CatalogGoods(connection, view, controller);
        commander(controller);
    }

    public void commander(MainController controller) throws Exception {
        help();

        while (true) {

            view.write("\nВведите команду: ");

            String input = view.read();

            for (Command command : commands) {
                try {
                    if (command.canProcess(input)) {
                        command.process(input);
                        break;
                    }
                } catch (Exception e) {
                    if (e instanceof ExitException) {
                        throw e;
                    }
                    view.error("Error", e);
                    break;
                }
            }


//            if (input.equals("addIncomingOrder") | input.equals("addIO")) {
////                list();
//            } else if (input.equals("Store")) {
////                doFind(command);
//            } else if (input.equals("addOrder") | input.equals("addOO")) {
////                doFind(command);
//            } else if (input.equals("Writeoff")) {
////                Report.reportGoods(connection);
//            } else if (input.equals("CatalogGoods")) {
//                Report.CatalogGoods(connection, view, controller);
//            } else if (input.equals("Report")) {
////                Report.reportGoods(connection);
//            } else if (input.equals("listIncomingOrders") | input.equals("listIO")) {
////                Report.reportIncommingOrders(connection);
//            } else if (input.equals("listOrders") | input.equals("listOO")) {
////                doFind(command);
//            } else if (input.equals("help")) {
//                help();
//            } else if (input.equals("exit")) {
//
//            } else {
//                view.write("\nНесуществующая команда: " + input);
//            }
        }
    }

    public void connect(String database, String userName, String password) {
            try {
                Class.forName("org.postgresql.Driver");
            } catch (ClassNotFoundException e) {
                throw new RuntimeException("Please add jdbc jar to project.", e);
            }
            try {
                connection = DriverManager.getConnection(
                        "jdbc:postgresql://localhost:5432/" + database, userName,
                        password);
            } catch (SQLException e) {
                connection = null;
                throw new RuntimeException(
                        String.format("Cant get connection for model:%s user:%s",
                                database, userName),
                        e);
            }
        }

//        Class.forName("org.postgresql.Driver");
//        connection = DriverManager.getConnection(
//                "jdbc:postgresql://localhost:5432/CRM", "postgres",
//                "postgres");
//    }

    private void help() {
        view.write("\nДанный модуль позволяет реализовать следующие операции:\n" +
                "- создание приходной накладной: команда “addIncomingOrder” или “addIO”;\n" +
                "- оприходование товара: команда “Store”;\n" +
                "- создание расходной накладной: команда “addOrder” или “addOO”;\n" +
                "- списание товар: команда “Writeoff”;\n" +
                "- отчет об остатках на складах: команда “report”;\n" +
                "- справочник товаров: команда “CatalogGoods”;\n" +
                "- предоставление списка приходных накладных: команда “listIncomingOrders” или “listIO”;\n" +
                "- предоставление списка расходных накладных: команда “listOrders” или “listOO”\n" +
                "Дополнительные команды (list, add, update, delete) доступны в соответствующих разделах.\n" +
                "Для вызова справки введите “help”.\n" +
                "Команда “exit” позволяет выйти на уровень вверх или из модуля.\n");
    }

    private int getAmountRowTable(String tableName) throws SQLException {
        Statement stmt = connection.createStatement();

        ResultSet rsCount = stmt.executeQuery("SELECT COUNT(*) FROM public." + tableName);
        rsCount.next();
        int size = rsCount.getInt(1);
        rsCount.close();
        return size;
    }

    public String[] getTableData (String tableName) throws SQLException {
        int size  = getAmountRowTable(tableName);
        Statement stmt = connection.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT * FROM public." + tableName);
        ResultSetMetaData rsmd = rs.getMetaData();
        String[] result = new String[size];
        int index = 0;
        while (rs.next()){
            String str = "\n";
            for (int i = 1; i <= rsmd.getColumnCount() ; i++) {
                str += String.format("%-16s", rs.getObject(i));
                str += "\t\t";
            }
            result[index] = str;
            index++;
        }
        rs.close();
        stmt.close();
        return result;
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
package com.ua.smarterama.andrey.leus.CRM.controller;

import com.ua.smarterama.andrey.leus.CRM.controller.command.*;
import com.ua.smarterama.andrey.leus.CRM.model.DataBaseManager;
import com.ua.smarterama.andrey.leus.CRM.view.View;
import java.sql.*;

public class MainController {

    private Connection connection;
    private View view;
    private DataBaseManager manager;
    private Command[] commands;

    public MainController() {}

    public MainController(View view) {
        this.view = view;
        this.commands = new Command[]{
                new ConnectToDataBase(manager, view),
                new Catalog(view),
                new Help(view),
                new Exit(view),
                new Report(view),
                new Store(view),
                new Writeoff(view),
                new Unsupported(view)
        };
    }

    public void run(MainController controller) throws Exception {

        connect("CRM", "postgres", "postgres");

        Help.getHelp();



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
                    view.error("Error", e);
                    break;
                }
            }
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

        System.out.println("_________________________________");

        try {
            PreparedStatement ps = connection
                    .prepareStatement("SELECT datname FROM pg_database WHERE datistemplate = false;");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                System.out.println(rs.getString(1));
            }
            rs.close();
            ps.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

//        Class.forName("org.postgresql.Driver");
//        connection = DriverManager.getConnection(
//                "jdbc:postgresql://localhost:5432/CRM", "postgres",
//                "postgres");
//    }


    private int getAmountRowTable(String tableName) throws SQLException {
        Statement stmt = connection.createStatement();

        ResultSet rsCount = stmt.executeQuery("SELECT COUNT(*) FROM public." + tableName);
        rsCount.next();
        int size = rsCount.getInt(1);
        rsCount.close();
        return size;
    }

    public String[] getTableData(String tableName) throws SQLException {
        int size = getAmountRowTable(tableName);
        Statement stmt = connection.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT * FROM public." + tableName);
        ResultSetMetaData rsmd = rs.getMetaData();
        String[] result = new String[size];
        int index = 0;
        while (rs.next()) {
            String str = "\n";
            for (int i = 1; i <= rsmd.getColumnCount(); i++) {
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
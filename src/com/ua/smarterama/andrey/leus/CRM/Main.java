package com.ua.smarterama.andrey.leus.CRM;

import java.sql.*;
import java.util.Random;

/**
 * Created by Admin on 07.04.2016.
 */
public class Main {
    public static void main(String[] argv) throws ClassNotFoundException, SQLException {
        Class.forName("org.postgresql.Driver");
        Connection connection = DriverManager.getConnection(
                "jdbc:postgresql://localhost:5432/CRM", "postgres",
                "postgres");

        // insert
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

        // select
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
}

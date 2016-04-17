package com.ua.smarterama.andrey.leus.CRM.model;

import java.sql.*;

/**
 * Created by Admin on 17.04.2016.
 */
public class Reports {

    public void reportGoods(Connection connection) throws SQLException, ClassNotFoundException {

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

    public void reportListIncommingInvoices(Connection connection) throws SQLException {
        Statement stmt;
        stmt = connection.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT * FROM public.ListIncomingInvoices");
        System.out.println("\n INCOMING INVOICES \n");
        System.out.println("id: \t name supplier: \t\t data: \t\t\t transport operator: \t\t responsible person:");
        while (rs.next()) {
            System.out.print(rs.getString("id"));
            System.out.print("\t\t\t" + rs.getString("name"));
            System.out.print("\t\t" + rs.getString("data"));
            System.out.print("\t\t\t" + rs.getString("transport"));
            System.out.println("\t\t\t\t" + rs.getString("response_person"));
        }
        rs.close();
        stmt.close();
    }
}

// id, name, data, transport, response_person,
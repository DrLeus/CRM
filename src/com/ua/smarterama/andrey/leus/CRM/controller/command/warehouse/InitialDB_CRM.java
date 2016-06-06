package com.ua.smarterama.andrey.leus.CRM.controller.command.warehouse;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class InitialDB_CRM {

    public static void setupTempDates() throws ClassNotFoundException, SQLException {

        Connection connection = DriverManager.getConnection(
                "jdbc:postgresql://localhost:5432/CRM/postgres/postgres");


        // insert table goods
        Statement stmt = connection.createStatement();

        stmt.executeUpdate("DROP DATABASE IF EXISTS CRM");

        stmt.executeUpdate("CREATE DATABASE CRM");

//        stmt.executeUpdate("DROP SEQUENCE public.goods_seq CASCADE");

        stmt.executeUpdate("CREATE SEQUENCE public.goods_seq INCREMENT 1 MINVALUE 1 MAXVALUE 9223372036854775807 START 1 CACHE 1;");

        stmt.executeUpdate("DROP TABLE public.goods CASCADE");

        stmt.executeUpdate("CREATE TABLE goods(" +
                "id NUMERIC NOT NULL DEFAULT nextval('goods_seq'::regclass), CONSTRAINT id_pk PRIMARY KEY(id)," +
                "code TEXT UNIQUE NOT NULL, " +
                "codeprevious TEXT UNIQUE NOT NULL, " +
                "name TEXT NOT NULL, " +
                "net_price TEXT NOT NULL, " +
                "customer_price TEXT NOT NULL, " +
                "id_groups TEXT /*PRIMARY KEY*/ NOT NULL)");
        stmt.executeUpdate("INSERT INTO public.goods (code, codeprevious, name, net_price, customer_price, id_groups)" +
                "VALUES ('H77435', '583327893', 'SEAL SV-25 EPDM CAT 2', '2,04', '22,88', '1')");
        stmt.executeUpdate("INSERT INTO public.goods (code, codeprevious, name, net_price, customer_price, id_groups)" +
                "VALUES ('H77459', '583337893', 'SEAL SV-40 EPDM CAT 2', '2,35', '25,27', '1')");
        stmt.executeUpdate("INSERT INTO public.goods (code, codeprevious, name, net_price, customer_price, id_groups)" +
                "VALUES ('H77484', '583342893', 'SEAL SV-50 EPDM CAT 2', '2,50', '27,75', '1')");
        stmt.executeUpdate("INSERT INTO public.goods (code, codeprevious, name, net_price, customer_price, id_groups)" +
                "VALUES ('H77509', '583347893', 'SEAL SV-65 EPDM CAT 2', '4,00', '30,59', '1')");
        stmt.executeUpdate("INSERT INTO public.goods (code, codeprevious, name, net_price, customer_price, id_groups)" +
                "VALUES ('H77539', '583352893', 'SEAL SV-80 EPDM CAT 2', '2,99', '42,56', '1')");

        // create table suppliers
//        stmt.executeUpdate("DROP TABLE public.suppliers CASCADE");
        stmt.executeUpdate("CREATE TABLE suppliers(" +
                "id NUMERIC PRIMARY KEY," +
                "name TEXT UNIQUE NOT NULL, " +
                "respon_persone TEXT NOT NULL, " +
                "phone TEXT UNIQUE NOT NULL, " +
                "address TEXT NOT NULL)");
        stmt.executeUpdate("INSERT INTO public.suppliers (id, name, respon_persone, phone, address)" +
                "VALUES (1, 'SPX Kolding', 'Niels Raevsager', '+48 3300 000 000', 'Denmark, Kolding')");
        stmt.executeUpdate("INSERT INTO public.suppliers (id, name, respon_persone, phone, address)" +
                "VALUES (2, 'SPX Silkiborg', 'Conni Dones', '+48 430 000 000', 'Denmark, Silkiborg')");
        stmt.executeUpdate("INSERT INTO public.suppliers (id, name, respon_persone, phone, address)" +
                "VALUES (3, 'SPX Unna', 'Isabelle Teillere', '+44 00 000 000', 'Germany, Unna')");
        stmt.executeUpdate("INSERT INTO public.suppliers (id, name, respon_persone, phone, address)" +
                "VALUES (4, 'SPX Bydgosh', 'Katarzyna Drozden', '+42 00 000 000', 'Poland, Bydgosh')");
        stmt.executeUpdate("INSERT INTO public.suppliers (id, name, respon_persone, phone, address)" +
                "VALUES (5, 'SPX Hungary', 'Molnar Mols', '+45 00 000 000', 'Hungary, Budapesht')");


        // create table transport_operators
//        stmt.executeUpdate("DROP TABLE public.transport CASCADE");
        stmt.executeUpdate("CREATE TABLE transport(" +
                "id NUMERIC PRIMARY KEY," +
                "name TEXT UNIQUE NOT NULL, " +
                "respon_persone TEXT NOT NULL, " +
                "phone TEXT NOT NULL, " +
                "address TEXT NOT NULL)");
        stmt.executeUpdate("INSERT INTO public.transport (id, name, respon_persone, phone, address)" +
                "VALUES (1, 'CAT', 'Alan Juret', '+38 044 111 11 11', 'Kiev, Borshchagovka')");
        stmt.executeUpdate("INSERT INTO public.transport (id, name, respon_persone, phone, address)" +
                "VALUES (2, 'Nova Pochta', 'Alex Dumin', '+38 044 222 22 22', 'Kiev, Solomenka')");
        stmt.executeUpdate("INSERT INTO public.transport (id, name, respon_persone, phone, address)" +
                "VALUES (3, 'TNT', 'Inna Krug', '+38 044 333 33 33', 'Kiev, Darnica')");
        stmt.executeUpdate("INSERT INTO public.transport (id, name, respon_persone, phone, address)" +
                "VALUES (4, 'Auto Lux', 'Katerina Strogonova', '+38 044 444 44 44', 'Kiev region, Borispol')");


        // create table Employee
//        stmt.executeUpdate("DROP TABLE public.employee CASCADE");
        stmt.executeUpdate("CREATE TABLE employee(" +
                "id NUMERIC PRIMARY KEY," +
                "name TEXT UNIQUE NOT NULL, " +
                "surname TEXT UNIQUE NOT NULL, " +
                "position TEXT UNIQUE NOT NULL, " +
                "past_position TEXT NOT NULL, " +
                "phone TEXT UNIQUE NOT NULL)");
        stmt.executeUpdate("INSERT INTO public.employee (id, name, surname, position, past_position, phone)" +
                "VALUES (1, 'Elena', 'Tupota', 'director', 'accounter', '+38 050 111 11 11')");
        stmt.executeUpdate("INSERT INTO public.employee (id, name, surname, position, past_position, phone)" +
                "VALUES (2, 'Inna', 'Voinova', 'chief account', 'accounter', '+38 050 222 22 22')");
        stmt.executeUpdate("INSERT INTO public.employee (id, name, surname, position, past_position, phone)" +
                "VALUES (3, 'Valentin', 'Korop', 'service manager', '-', '+38 050 333 33 33')");
        stmt.executeUpdate("INSERT INTO public.employee (id, name, surname, position, past_position, phone)" +
                "VALUES (4, 'Yana', 'Pavlik', 'assistance', '-', '+38 050 444 44 44')");

        // create table ListIncomingInvoice
//        stmt.executeUpdate("DROP TABLE public.ListIncomingOrders CASCADE");
        stmt.executeUpdate("CREATE TABLE ListIncomingOrders(" +
                "id NUMERIC PRIMARY KEY UNIQUE NOT NULL," +
                "name TEXT REFERENCES suppliers(name), " +
                "data timestamp NOT NULL, " +
                "transport TEXT REFERENCES transport(name), " +
                "response_person TEXT REFERENCES employee(surname))");
        stmt.executeUpdate("INSERT INTO public.ListIncomingOrders (id, name, data, transport, response_person)" +
                "VALUES (1, 'SPX Kolding', '2015-10-19 10:23:54', 'TNT', 'Tupota')");
        stmt.executeUpdate("INSERT INTO public.ListIncomingOrders (id, name, data, transport, response_person)" +
                "VALUES (2, 'SPX Unna', '2015-11-29 11:23:54', 'TNT', 'Korop')");
        stmt.executeUpdate("INSERT INTO public.ListIncomingOrders (id, name, data, transport, response_person)" +
                "VALUES (3, 'SPX Silkiborg', '2015-12-09 15:23:54', 'TNT', 'Pavlik')");


        // create table goods for IncomingInvoice
//        stmt.executeUpdate("DROP TABLE public.IncomingGoods CASCADE");
        stmt.executeUpdate("CREATE TABLE IncomingGoods(" +
                "id NUMERIC PRIMARY KEY," +
                "code TEXT REFERENCES goods(code)," +
                "id_incomingOrder NUMERIC REFERENCES ListIncomingOrders(id))");
        stmt.executeUpdate("INSERT INTO public.IncomingGoods (id, code, id_incomingOrder)" +
                "VALUES (1, 'H77435', 1)");
        stmt.executeUpdate("INSERT INTO public.IncomingGoods (id, code, id_incomingOrder)" +
                "VALUES (2, 'H77509', 1)");
        stmt.executeUpdate("INSERT INTO public.IncomingGoods (id, code, id_incomingOrder)" +
                "VALUES (3, 'H77435', 1)");


        stmt.close();
        connection.close();
    }
}

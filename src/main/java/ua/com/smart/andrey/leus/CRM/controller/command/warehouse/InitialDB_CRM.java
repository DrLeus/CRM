package ua.com.smart.andrey.leus.CRM.controller.command.warehouse;

import ua.com.smart.andrey.leus.CRM.model.CRMException;
import ua.com.smart.andrey.leus.CRM.model.Configuration;
import ua.com.smart.andrey.leus.CRM.model.DataBaseManager;

import java.util.ArrayList;
import java.util.List;

public class InitialDB_CRM {

    private static Configuration config = new Configuration();

    public static void getAddComands() {
        System.out.println(addComands);
    }

    static String addComands = "Additional commands\n" +
            " - 'report' - get goods balance on warehouse ;\n" +
            " - 'store' - add goods on warehouse +\n" +
            " - 'writeoff' - write off goods from warehouse ;\n";

    public static void setupTempDates(DataBaseManager manager) throws ClassNotFoundException, CRMException {

        manager.connect(config.getDatabaseName(),config.getUserName(),config.getUserPassword());

        manager.dropDatabase(config.getDatabaseNameCRM());

        manager.createDatabase(config.getDatabaseNameCRM());

        manager.connect(config.getDatabaseNameCRM(),config.getUserName(),config.getUserPassword());

        manager.dropTable("goods");

        // create table GOODS with date

        List<Object> listColumns = new ArrayList<>();

        listColumns.add("code TEXT");
        listColumns.add("name TEXT");
        listColumns.add("net_price TEXT");
        listColumns.add("customer_price TEXT");
        listColumns.add("");
        manager.createTable("goods", listColumns);

        List<Object> value = new ArrayList<>();
        value.add("H77435");
        value.add("SEAL SV-25 EPDM CAT 2");
        value.add("2,04");
        value.add("22,88");
        manager.insert("goods", manager.getColumnNames("goods",""), value); // goods 1

        value.clear();
        value.add("H77459");
        value.add("SEAL SV-40 EPDM CAT 2");
        value.add("2,35");
        value.add("25,57");
         manager.insert("goods", manager.getColumnNames("goods",""), value); // goods 2

        value.clear();
        value.add("H77484");
        value.add("SEAL SV-50 EPDM CAT 2");
        value.add("2,50");
        value.add("27,75");
         manager.insert("goods", manager.getColumnNames("goods",""), value); // goods 3

        value.clear();
        value.add("H77509");
        value.add("SEAL SV-65 EPDM CAT 2");
        value.add("4,00");
        value.add("30,59");
         manager.insert("goods", manager.getColumnNames("goods",""), value); // goods 4

        value.clear();
        value.add("H77539");
        value.add("SEAL SV-80 EPDM CAT 2");
        value.add("5,99");
        value.add("42,56");
         manager.insert("goods", manager.getColumnNames("goods",""), value); // goods 5
        
        
        // create table STOCKBALANCE with data
        
        manager.dropTable("stockbalance");

        listColumns.clear();
        listColumns.add("quantity TEXT");
        listColumns.add("id_goods NUMERIC REFERENCES goods(id)");
        listColumns.add("");
        manager.createTable("stockbalance",listColumns);

        value.clear();
        value.add("50");
        value.add(3);
        manager.insert("stockbalance", manager.getColumnNames("stockbalance",""), value);

        value.clear();
        value.add("20");
        value.add(4);
        manager.insert("stockbalance", manager.getColumnNames("stockbalance",""), value);


        // create table SUPPLIERS with data

        manager.dropTable("suppliers");

        listColumns.clear();
        listColumns.add("name TEXT");
        listColumns.add("respon_person TEXT");
        listColumns.add("phone TEXT");
        listColumns.add("address TEXT");
        listColumns.add("");
        manager.createTable("suppliers",listColumns);

        value.clear();
        value.add("SPX Kolding");
        value.add("Niels Raevsager");
        value.add("+48 3300 000 000");
        value.add("Denmark, Kolding");
        manager.insert("suppliers", manager.getColumnNames("suppliers",""), value);

        value.clear();
        value.add("SPX Silkiborg");
        value.add("Conni Dones");
        value.add("+48 430 000 000");
        value.add("Denmark, Silkiborg");
        manager.insert("suppliers", manager.getColumnNames("suppliers",""), value);

        value.clear();
        value.add("SPX Unna");
        value.add("Isabelle Teillere");
        value.add("+44 00 000 000");
        value.add("Germany, Unna");
        manager.insert("suppliers", manager.getColumnNames("suppliers",""), value);

        value.clear();
        value.add("SPX Bydgosh");
        value.add("Katarzyna Drozden");
        value.add("+42 00 000 000");
        value.add("Poland, Bydgosh");
        manager.insert("suppliers", manager.getColumnNames("suppliers",""), value);

        value.clear();
        value.add("SPX Hungary");
        value.add("Molnar Mols");
        value.add("+45 00 000 000");
        value.add("Hungary, Budapesht");
        manager.insert("suppliers", manager.getColumnNames("suppliers",""), value);

        // create table transport_operators

        manager.dropTable("transport");

        listColumns.clear();
        listColumns.add("name TEXT");
        listColumns.add("respon_person TEXT");
        listColumns.add("phone TEXT");
        listColumns.add("address TEXT");
        listColumns.add("");
        manager.createTable("transport",listColumns);

        value.clear();
        value.add("Nova Pochta");
        value.add("Alex Dumin");
        value.add("+38 044 111 11 11");
        value.add("Kiev, Solomenka");
        manager.insert("transport", manager.getColumnNames("transport",""), value);


        value.clear();
        value.add("TNT");
        value.add("Inna Krug");
        value.add("+38 044 333 33 33");
        value.add("Kiev, Darnica");
        manager.insert("transport", manager.getColumnNames("transport",""), value);

        value.clear();
        value.add("SAT");
        value.add("Alan Juret");
        value.add("+38 044 222 22 22");
        value.add("Kiev, Borshchagovka");
        manager.insert("transport", manager.getColumnNames("transport",""), value);

        // create table Employee

        manager.dropTable("employee");

        listColumns.clear();
        listColumns.add("name TEXT");
        listColumns.add("surname TEXT");
        listColumns.add("position TEXT");
        listColumns.add("past_position TEXT");
        listColumns.add("phone TEXT");
        listColumns.add("");
        manager.createTable("employee",listColumns);

        value.clear();
        value.add("Elena");
        value.add("Tupota");
        value.add("director");
        value.add("accountant");
        value.add("+38 050 111 11 11");
        manager.insert("employee", manager.getColumnNames("employee",""), value);

        value.clear();
        value.add("Inna");
        value.add("Voinova");
        value.add("chief account");
        value.add("accountant");
        value.add("+38 050 222 22 22");
        manager.insert("employee", manager.getColumnNames("employee",""), value);

        value.clear();
        value.add("Valentin");
        value.add("Korop");
        value.add("service manager");
        value.add("-");
        value.add("+38 050 333 33 33");
        manager.insert("employee", manager.getColumnNames("employee",""), value);

        value.clear();
        value.add("Yana");
        value.add("Pavlik");
        value.add("assistance");
        value.add("-");
        value.add("+38 050 444 44 44");
        manager.insert("employee", manager.getColumnNames("employee",""), value);
    }
}

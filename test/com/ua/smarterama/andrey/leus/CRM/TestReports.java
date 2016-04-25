package com.ua.smarterama.andrey.leus.CRM;


import com.ua.smarterama.andrey.leus.CRM.controller.MainController;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertEquals;

import java.sql.*;

public class TestReports {

    private MainController man;

    @Before
    public void setup() throws SQLException, ClassNotFoundException {
//        manager = getDatabaseManager();
        man.connect("CRM", "postgres","postgres");
    }

    @Test
    public void testReportGoods() throws SQLException {
        String input = "goods";
        String expected =
                "_ 327678 |_224_\n" +
                        "  224    | 1462,8482(142857)\n" +
                        "  ______\n" +
                        "   1036\n" +
                        "   896\n" +
                        "   ____\n" +
                        "    1407\n" +
                        "    1344\n" +
                        "    ____\n" +
                        "     638\n" +
                        "     448\n" +
                        "     ___\n" +
                        "      190\n";
        getResult(expected, input);
    }


    private void getResult(String expected, String input) throws SQLException {
        MainController test = new MainController();

        assertEquals(expected, test.getTableData(input));
    }
}
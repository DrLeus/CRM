package com.ua.smarterama.andrey.leus.CRM.controller.command;

import com.ua.smarterama.andrey.leus.CRM.controller.command.tables.ClearTable;
import com.ua.smarterama.andrey.leus.CRM.controller.command.tables.GetTable;
import com.ua.smarterama.andrey.leus.CRM.model.JDBCDataBaseManager;
import com.ua.smarterama.andrey.leus.CRM.view.Console;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import com.ua.smarterama.andrey.leus.CRM.model.DataBaseManager;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class ClearTest {

    private Console view;
    private DataBaseManager dbManager;
    private ClearTable command;

    @Before
    public void setup() {
        view = mock(Console.class);
        dbManager = mock(JDBCDataBaseManager.class);
        command = new ClearTable(dbManager);
    }


    @Test
    public void testClearTable() throws SQLException {
        command.clearTable();
        verify(dbManager).clear("user");
        verify(view).write("Таблица user была успешно очищена.");
    }



    //    @Test
    public void testListTableNames() throws SQLException {
        //given
        List<String> tableNames = new ArrayList<>();
        tableNames.add("test");
        tableNames.add("users");
        when(dbManager.getTableNames()).thenReturn(tableNames);

        //when
        command.manager.getTableData("","");

        //then
        verify(view).write("[test, users]");
    }



}

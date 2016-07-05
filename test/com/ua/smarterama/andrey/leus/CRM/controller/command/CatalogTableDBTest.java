package com.ua.smarterama.andrey.leus.CRM.controller.command;

import com.ua.smarterama.andrey.leus.CRM.controller.command.tables.Catalog;
import com.ua.smarterama.andrey.leus.CRM.controller.command.tables.GetTable;
import com.ua.smarterama.andrey.leus.CRM.model.DataBaseManager;
import com.ua.smarterama.andrey.leus.CRM.model.JDBCDataBaseManager;
import com.ua.smarterama.andrey.leus.CRM.view.Console;
import org.junit.Before;
import org.junit.Test;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;

public class CatalogTableDBTest {

    private Console view;
    private DataBaseManager manager;
    private Command command;
    private Command table;

    @Before
    public void setup() {
        view = mock(Console.class);
        manager = mock(JDBCDataBaseManager.class);
        command = new Catalog(manager, view);
        table = new GetTable(manager, view);
    }

    @Test
    public void testCanProcess() throws Exception {
        boolean canProcess = command.canProcess("catalog");
        assertTrue(canProcess);
    }

    @Test
    public void testCanNotProcess() throws Exception {
        boolean canProcess = command.canProcess("error");
        assertFalse(canProcess);
    }

    @Test
    public void testProcessGetTable() throws SQLException {

        //given
        when(view.read()).thenReturn("1");
        List<String> list = new ArrayList<>();
        list.add("test");
        when(table.manager.getTableNames()).thenReturn(list);
        when(view.selectTable(list, view)).thenReturn("test");
        List<Object> column = new ArrayList<>();
        column.add("name");
        when(table.manager.getColumnNames("test","")).thenReturn(column);
        List<Object> value = new ArrayList<>();
        value.add("pupkin");
        when(table.manager.getTableData("test","")).thenReturn(value);
        when(view.read()).thenReturn("8");
        when(view.read()).thenReturn("exit");

        //when
        command.process();

        //then
        verify(view).write("Available operations:\n" +
                "1. Get table data\n" +
                "2. Insert data (position)\n" +
                "3. Update data (position)\n" +
                "4. Delete data (position)\n" +
                "5. Create table\n" +
                "6. Remove table\n" +
                "7. Clear table\n" +
                "8. Return to main menu\n");

        verify(view).write("Please select operation:\n");

        verify(view).write("The next data bases available:\n");
        verify(view).write("The next data bases available:\n");
        verify(manager).getDatabases();
    }
}

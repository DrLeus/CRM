package com.ua.smarterama.andrey.leus.CRM.controller.command;

import com.ua.smarterama.andrey.leus.CRM.controller.command.tables.*;
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
        table = new GetTable(manager, view);
        when(view.read()).thenReturn("1").thenReturn("8").thenReturn("exit");
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


        //when
        command.process();

        //then
        verify(view, atLeast(2)).write("Available operations:\n" +
                "1. Get table data\n" +
                "2. Insert data (position)\n" +
                "3. Update data (position)\n" +
                "4. Delete data (position)\n" +
                "5. Create table\n" +
                "6. Remove table\n" +
                "7. Clear table\n" +
                "8. Return to main menu\n");

        verify(view, atLeast(2)).write("Please select operation:\n");

        verify(view).write("Return to main menu!\n");
        verify(manager).getTableNames();
    }

    @Test
    public void testProcessGetTableCAnceledAction() throws SQLException {

        //given
        table = new GetTable(manager, view);
        when(view.read()).thenReturn("").thenReturn("8").thenReturn("exit");
        List<String> list = new ArrayList<>();
        list.add("");
        when(table.manager.getTableNames()).thenReturn(list);
        when(view.selectTable(list, view)).thenReturn("");
        List<Object> column = new ArrayList<>();
        column.add("name");
        when(table.manager.getColumnNames("test","")).thenReturn(column);
        List<Object> value = new ArrayList<>();
        value.add("pupkin");
        when(table.manager.getTableData("test","")).thenReturn(value);


        //when
        command.process();

        //then
        verify(view, atLeast(2)).write("Available operations:\n" +
                "1. Get table data\n" +
                "2. Insert data (position)\n" +
                "3. Update data (position)\n" +
                "4. Delete data (position)\n" +
                "5. Create table\n" +
                "6. Remove table\n" +
                "7. Clear table\n" +
                "8. Return to main menu\n");

        verify(view, atLeast(2)).write("Please select operation:\n");
        verify(view).write("Return to main menu!\n");
    }

    @Test
    public void testProcessInsertData() throws SQLException {

        //given
        table = new InsertData(manager, view);
        when(view.read()).thenReturn("2").thenReturn("pupkin").thenReturn("8").thenReturn("exit");
        List<String> list = new ArrayList<>();
        list.add("test");
        when(table.manager.getTableNames()).thenReturn(list);
        when(view.selectTable(list, view)).thenReturn("test");
        List<Object> column = new ArrayList<>();
        column.add("id");
        column.add("name");
        List<Object> value = new ArrayList<>();
        value.add("pupkin");
        when(table.manager.getColumnNames("test","")).thenReturn(column);

        //when
        command.process();

        //then
        verify(view, atLeast(2)).write("Available operations:\n" +
                "1. Get table data\n" +
                "2. Insert data (position)\n" +
                "3. Update data (position)\n" +
                "4. Delete data (position)\n" +
                "5. Create table\n" +
                "6. Remove table\n" +
                "7. Clear table\n" +
                "8. Return to main menu\n");

        verify(view, atLeast(2)).write("Please select operation:\n");

        verify(view).write("Please select table\n");
        verify(view).write("Return to main menu!\n");
        verify(manager).getTableNames();
        verify(manager, atLeast(2)).getColumnNames("test","");
        verify(manager).insert("test", column, value);
    }

    @Test
    public void testProcessInsertDataCanceledActionEmptyTable() throws SQLException {

        //given
        table = new InsertData(manager, view);
        when(view.read()).thenReturn("2").thenReturn("8").thenReturn("exit");
        List<String> list = new ArrayList<>();
        list.add("");
        when(table.manager.getTableNames()).thenReturn(list);
        when(view.selectTable(list, view)).thenReturn("");
        List<Object> column = new ArrayList<>();
        column.add("id");
        column.add("name");
        List<Object> value = new ArrayList<>();
        value.add("pupkin");
        when(table.manager.getColumnNames("test","")).thenReturn(column);

        //when
        command.process();

        //then
        verify(view, atLeast(2)).write("Available operations:\n" +
                "1. Get table data\n" +
                "2. Insert data (position)\n" +
                "3. Update data (position)\n" +
                "4. Delete data (position)\n" +
                "5. Create table\n" +
                "6. Remove table\n" +
                "7. Clear table\n" +
                "8. Return to main menu\n");

        verify(view, atLeast(2)).write("Please select operation:\n");

        verify(view).write("Please select table\n");
        verify(view).write("Return to main menu!\n");
        verify(manager).getTableNames();
    }

    @Test
    public void testProcessUpdateData() throws SQLException {

        //given
        table = new UpdateDate(manager, view);
        when(view.read()).thenReturn("3").thenReturn("1").thenReturn("pupkin").thenReturn("8").thenReturn("exit");
        List<String> list = new ArrayList<>();
        list.add("test");
        when(table.manager.getTableNames()).thenReturn(list);
        when(view.selectTable(list, view)).thenReturn("test");
        List<Object> column = new ArrayList<>();
        column.add("id");
        column.add("name");
        List<Object> value = new ArrayList<>();
        value.add("pupkin");
        when(table.manager.getColumnNames("test","")).thenReturn(column);

        //when
        command.process();

        //then
        verify(view, atLeast(2)).write("Available operations:\n" +
                "1. Get table data\n" +
                "2. Insert data (position)\n" +
                "3. Update data (position)\n" +
                "4. Delete data (position)\n" +
                "5. Create table\n" +
                "6. Remove table\n" +
                "7. Clear table\n" +
                "8. Return to main menu\n");

        verify(view, atLeast(2)).write("Please select operation:\n");

        verify(view).write("\n");
        verify(view).write("Please select row id to update:\n");
        verify(view).write("Return to main menu!\n");
        verify(manager).getTableNames();
        verify(manager).getColumnNames("test","");
        verify(manager).update("test", column, 1, value);
    }

    @Test
    public void testProcessDeleteData() throws SQLException {

        //given
        table = new DeleteData(manager, view);
        when(view.read()).thenReturn("4").thenReturn("1").thenReturn("Y").thenReturn("8").thenReturn("exit");
        List<String> list = new ArrayList<>();
        list.add("test");
        when(table.manager.getTableNames()).thenReturn(list);
        when(view.selectTable(list, view)).thenReturn("test");
        List<Object> column = new ArrayList<>();
        column.add("id");
        column.add("name");
        when(table.manager.getColumnNames("test","")).thenReturn(column);

        //when
        command.process();

        //then
        verify(view, atLeast(2)).write("Available operations:\n" +
                "1. Get table data\n" +
                "2. Insert data (position)\n" +
                "3. Update data (position)\n" +
                "4. Delete data (position)\n" +
                "5. Create table\n" +
                "6. Remove table\n" +
                "7. Clear table\n" +
                "8. Return to main menu\n");

        verify(view, atLeast(2)).write("Please select operation:\n");

        verify(view).write("Please input 'id' line to delete\n");
        verify(view).write("Return to main menu!\n");
        verify(manager).getTableNames();
        verify(manager, atLeast(2)).getColumnNames("test","");
        verify(manager).delete(1, "test");
    }

    @Test
    public void testProcessDeleteDataCanceledAction() throws SQLException {

        //given
        table = new DeleteData(manager, view);
        when(view.read()).thenReturn("4").thenReturn("1").thenReturn("N").thenReturn("8").thenReturn("exit");
        List<String> list = new ArrayList<>();
        list.add("test");
        when(table.manager.getTableNames()).thenReturn(list);
        when(view.selectTable(list, view)).thenReturn("test");
        List<Object> column = new ArrayList<>();
        column.add("id");
        column.add("name");
        when(table.manager.getColumnNames("test","")).thenReturn(column);

        //when
        command.process();

        //then
        verify(view, atLeast(2)).write("Available operations:\n" +
                "1. Get table data\n" +
                "2. Insert data (position)\n" +
                "3. Update data (position)\n" +
                "4. Delete data (position)\n" +
                "5. Create table\n" +
                "6. Remove table\n" +
                "7. Clear table\n" +
                "8. Return to main menu\n");

        verify(view, atLeast(2)).write("Please select operation:\n");

        verify(view).write("Your action canceled!\n");
        verify(view).write("Return to main menu!\n");
        verify(manager).getTableNames();
        verify(manager, atLeast(2)).getColumnNames("test","");
    }

    @Test
    public void testProcessDeleteDataCanceledActionEmptyTableName() throws SQLException {

        //given
        table = new DeleteData(manager, view);
        when(view.read()).thenReturn("4").thenReturn("exit").thenReturn("8").thenReturn("exit");
        List<String> list = new ArrayList<>();
        list.add("");
        when(table.manager.getTableNames()).thenReturn(list);
        when(view.selectTable(list, view)).thenReturn("");
        List<Object> column = new ArrayList<>();
        column.add("id");
        column.add("name");

        //when
        command.process();

        //then
        verify(view, atLeast(2)).write("Available operations:\n" +
                "1. Get table data\n" +
                "2. Insert data (position)\n" +
                "3. Update data (position)\n" +
                "4. Delete data (position)\n" +
                "5. Create table\n" +
                "6. Remove table\n" +
                "7. Clear table\n" +
                "8. Return to main menu\n");

        verify(view, atLeast(2)).write("Please select operation:\n");
        verify(view).write("Your action canceled!\n");
        verify(view).write("Return to main menu!\n");
        verify(manager).getTableNames();
    }

    @Test
    public void testProcessDeleteDataCanceledActionEmptyTable() throws SQLException {

        //given
        table = new DeleteData(manager, view);
        when(view.read()).thenReturn("4").thenReturn("8").thenReturn("exit");
        List<String> list = new ArrayList<>();
        list.add("");
        when(table.manager.getTableNames()).thenReturn(list);
        when(view.selectTable(list, view)).thenReturn("");
        List<Object> column = new ArrayList<>();
        column.add("id");
        column.add("name");
        when(table.manager.getColumnNames("test","")).thenReturn(column);

        //when
        command.process();

        //then
        verify(view, atLeast(2)).write("Available operations:\n" +
                "1. Get table data\n" +
                "2. Insert data (position)\n" +
                "3. Update data (position)\n" +
                "4. Delete data (position)\n" +
                "5. Create table\n" +
                "6. Remove table\n" +
                "7. Clear table\n" +
                "8. Return to main menu\n");

        verify(view, atLeast(2)).write("Please select operation:\n");

        verify(view).write("Your action canceled!\n");
        verify(view).write("Return to main menu!\n");
        verify(manager).getTableNames();
//        verify(manager, atLeast(2)).getColumnNames("test","");
//        verify(manager).delete(1, "test");
    }

    @Test
    public void testProcessCreateTable() throws SQLException {

        //given
        table = new CreateTable(manager, view);
        when(view.read()).thenReturn("5").thenReturn("test").thenReturn("name TEXT").thenReturn("").thenReturn("8").thenReturn("exit");
        List<String> list = new ArrayList<>();
        list.add("test");
        when(table.manager.getTableNames()).thenReturn(list);
        List<Object> column = new ArrayList<>();
        column.add("name");
        when(view.inputNames(view)).thenReturn(column);

        //when
        command.process();

        //then
        verify(view, atLeast(2)).write("Available operations:\n" +
                "1. Get table data\n" +
                "2. Insert data (position)\n" +
                "3. Update data (position)\n" +
                "4. Delete data (position)\n" +
                "5. Create table\n" +
                "6. Remove table\n" +
                "7. Clear table\n" +
                "8. Return to main menu\n");

        verify(view, atLeast(2)).write("Please select operation:\n");
        verify(view).write("Please input table name:\n");
        verify(view).write("Return to main menu!\n");
        verify(manager).createTable("test", column);
    }

    @Test
    public void testProcessCreateTableCanceledAction() throws SQLException {

        //given
        table = new CreateTable(manager, view);
        when(view.read()).thenReturn("5").thenReturn("").thenReturn("8").thenReturn("exit");
        List<String> list = new ArrayList<>();
        list.add("test");
        when(table.manager.getTableNames()).thenReturn(list);
        List<Object> column = new ArrayList<>();
        column.add("name");
        when(view.inputNames(view)).thenReturn(column);

        //when
        command.process();

        //then
        verify(view, atLeast(2)).write("Available operations:\n" +
                "1. Get table data\n" +
                "2. Insert data (position)\n" +
                "3. Update data (position)\n" +
                "4. Delete data (position)\n" +
                "5. Create table\n" +
                "6. Remove table\n" +
                "7. Clear table\n" +
                "8. Return to main menu\n");

        verify(view, atLeast(2)).write("Please select operation:\n");
        verify(view).write("Please input table name:\n");
        verify(view).write("Return to main menu!\n");
    }

    @Test
    public void testProcessRemoveTable() throws SQLException {

        //given
        table = new RemoveTable(manager, view);
        when(view.read()).thenReturn("6").thenReturn("Y").thenReturn("8").thenReturn("exit");
        List<String> list = new ArrayList<>();
        list.add("test");
        when(table.manager.getTableNames()).thenReturn(list);
        when(view.selectTable(list, view)).thenReturn("test");
        List<Object> column = new ArrayList<>();
        column.add("name");
        when(view.inputNames(view)).thenReturn(column);

        //when
        command.process();

        //then
        verify(view, atLeast(2)).write("Available operations:\n" +
                "1. Get table data\n" +
                "2. Insert data (position)\n" +
                "3. Update data (position)\n" +
                "4. Delete data (position)\n" +
                "5. Create table\n" +
                "6. Remove table\n" +
                "7. Clear table\n" +
                "8. Return to main menu\n");

        verify(view, atLeast(2)).write("Please select operation:\n");
        verify(view).write("Please confirm, do you really want to remove 'test' table? Y/N\r\n");
        verify(view).write("Table 'test' was removed! Success!\r\n");
        verify(view).write("Return to main menu!\n");
        verify(manager).dropTable("test");
    }

    @Test
    public void testProcessRemoveTableCanceledAction() throws SQLException {

        //given
        table = new RemoveTable(manager, view);
        when(view.read()).thenReturn("6").thenReturn("N").thenReturn("8").thenReturn("exit");
        List<String> list = new ArrayList<>();
        list.add("test");
        when(table.manager.getTableNames()).thenReturn(list);
        when(view.selectTable(list, view)).thenReturn("test");
        List<Object> column = new ArrayList<>();
        column.add("name");
        when(view.inputNames(view)).thenReturn(column);

        //when
        command.process();

        //then
        verify(view, atLeast(2)).write("Available operations:\n" +
                "1. Get table data\n" +
                "2. Insert data (position)\n" +
                "3. Update data (position)\n" +
                "4. Delete data (position)\n" +
                "5. Create table\n" +
                "6. Remove table\n" +
                "7. Clear table\n" +
                "8. Return to main menu\n");

        verify(view, atLeast(2)).write("Please select operation:\n");
        verify(view).write("Please confirm, do you really want to remove 'test' table? Y/N\r\n");
        verify(view).write("Your action canceled!\n");
        verify(view).write("Return to main menu!\n");
    }

    @Test
    public void testProcessClearTable() throws SQLException {

        //given
        table = new ClearTable(manager, view);
        when(view.read()).thenReturn("7").thenReturn("Y").thenReturn("8").thenReturn("exit");
        List<String> list = new ArrayList<>();
        list.add("test");
        when(table.manager.getTableNames()).thenReturn(list);
        when(view.selectTable(list, view)).thenReturn("test");
        List<Object> column = new ArrayList<>();
        column.add("name");
        when(view.inputNames(view)).thenReturn(column);

        //when
        command.process();

        //then
        verify(view, atLeast(2)).write("Available operations:\n" +
                "1. Get table data\n" +
                "2. Insert data (position)\n" +
                "3. Update data (position)\n" +
                "4. Delete data (position)\n" +
                "5. Create table\n" +
                "6. Remove table\n" +
                "7. Clear table\n" +
                "8. Return to main menu\n");

        verify(view, atLeast(2)).write("Please select operation:\n");
        verify(view).write("Please select table\n");
        verify(view).write("Please confirm, do you really want to clear table 'test'? Y/N\r\n");
        verify(view).write("Table 'test' was cleared! Success!\r\n");
        verify(view).write("Return to main menu!\n");
        verify(manager).clear("test");
    }

    @Test
    public void testProcessClearTableCanceledAction() throws SQLException {

        //given
        table = new ClearTable(manager, view);
        when(view.read()).thenReturn("7").thenReturn("N").thenReturn("8").thenReturn("exit");
        List<String> list = new ArrayList<>();
        list.add("test");
        when(table.manager.getTableNames()).thenReturn(list);
        when(view.selectTable(list, view)).thenReturn("test");
        List<Object> column = new ArrayList<>();
        column.add("name");
        when(view.inputNames(view)).thenReturn(column);

        //when
        command.process();

        //then
        verify(view, atLeast(2)).write("Available operations:\n" +
                "1. Get table data\n" +
                "2. Insert data (position)\n" +
                "3. Update data (position)\n" +
                "4. Delete data (position)\n" +
                "5. Create table\n" +
                "6. Remove table\n" +
                "7. Clear table\n" +
                "8. Return to main menu\n");

        verify(view, atLeast(2)).write("Please select operation:\n");
        verify(view).write("Please select table\n");
        verify(view).write("Please confirm, do you really want to clear table 'test'? Y/N\r\n");
        verify(view).write("Your action canceled!\n");
        verify(view).write("Return to main menu!\n");
    }

    @Test
    public void testProcessReturnToMainMenu() throws SQLException {

        //given
        table = new ClearTable(manager, view);
        when(view.read()).thenReturn("8").thenReturn("exit");

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

        verify(view).write("Return to main menu!\n");
    }
}

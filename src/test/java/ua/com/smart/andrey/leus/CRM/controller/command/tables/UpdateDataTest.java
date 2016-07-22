package ua.com.smart.andrey.leus.CRM.controller.command.tables;

import org.junit.Before;
import org.junit.Test;
import ua.com.smart.andrey.leus.CRM.controller.command.Command;
import ua.com.smart.andrey.leus.CRM.model.CRMException;
import ua.com.smart.andrey.leus.CRM.model.DataBaseManager;
import ua.com.smart.andrey.leus.CRM.model.JDBCDataBaseManager;
import ua.com.smart.andrey.leus.CRM.view.Console;
import ua.com.smart.andrey.leus.CRM.view.View;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;


public class UpdateDataTest {

    private View view;
    private DataBaseManager manager;
    private Command command;
    private Command table;

    @Before
    public void setup() {
        view = mock(Console.class);
        manager = mock(JDBCDataBaseManager.class);
        command = new InsertData(manager, view);
    }

    @Test
    public void testProcessUpdateData() throws CRMException {

        //given
        table = new UpdateDate(manager, view);
        when(view.read()).thenReturn("3").thenReturn("1").thenReturn("pupkin").thenReturn("8").thenReturn("exit");
        List<String> list = new ArrayList<>();
        list.add("test");
        when(table.manager.getTableNames()).thenReturn(list);
        when(table.selectTable(list, view)).thenReturn("test");
        List<Object> column = new ArrayList<>();
        column.add("id");
        column.add("name");
        List<Object> value = new ArrayList<>();
        value.add("pupkin");
        when(table.manager.getColumnNames("test")).thenReturn(column);

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
        verify(manager).getColumnNames("test");
        verify(manager).update("test", column, 1, value);
    }
}
package com.ua.smarterama.andrey.leus.CRM.controller.command;

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

public class DropDBTest {

    private Console view;
    private DataBaseManager manager;
    private Command command;

    @Before
    public void setup() {
        view = mock(Console.class);
        manager = mock(JDBCDataBaseManager.class);
        command = new DropDataBase(manager, view);
    }


    @Test
    public void testCanProcess() throws Exception {
        boolean canProcess = command.canProcess("drop");
        assertTrue(canProcess);
    }

    @Test
    public void testCanNotProcess() throws Exception {
        boolean canProcess = command.canProcess("error");
        assertFalse(canProcess);
    }

    @Test
    public void testProcess() throws SQLException {

        //given
        List<String> list = new ArrayList<>();
        list.add("test");
        when(view.read()).thenReturn("1").thenReturn("Y");
        when(manager.getDatabases()).thenReturn(list);

        //when
        command.process();

        //then
        verify(view).write("The next data bases available:\n");
        verify(view).write("Please select database for dropping:\n");
        verify(view).write("1: test");
        verify(view).write("Please confirm, do you really want to drop 'test' database? Y/N\r\n");
        verify(manager).dropDatabase("test");
        verify(view).write("Database 'test' dropped\r\n");
    }


    @Test
    public void testProcessWithEmptyParameters() throws SQLException {

        //given
        List<String> list = new ArrayList<>();
        list.add("");
        when(view.read()).thenReturn("1").thenReturn("Y");
        when( manager.getDatabases()).thenReturn(list);

        //when
        command.process();

        //then
        verify(view).write("The next data bases available:\n");
        verify(view).write("Please select database for dropping:\n");
        verify(view).write("1: ");
        verify(view).write("Nothing to do!\n");
        verify(view).write("Return to main menu!\n");
    }


}

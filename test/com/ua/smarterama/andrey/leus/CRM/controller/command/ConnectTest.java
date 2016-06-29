package com.ua.smarterama.andrey.leus.CRM.controller.command;

import com.ua.smarterama.andrey.leus.CRM.model.DataBaseManager;
import com.ua.smarterama.andrey.leus.CRM.model.JDBCDataBaseManager;
import com.ua.smarterama.andrey.leus.CRM.view.Console;
import com.ua.smarterama.andrey.leus.CRM.view.View;
import jdk.nashorn.internal.ir.annotations.Ignore;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;

import java.sql.SQLException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;

public class ConnectTest {

    private ConnectToDataBase command;
    private DataBaseManager manager;
    private Console view;

    @Before
    public void setup() {
        manager = mock(JDBCDataBaseManager.class);
        view = mock(Console.class);
        command = new ConnectToDataBase(manager);
    }

    @Test
    public void testCanProcess() throws Exception {
        boolean canProcess = command.canProcess("connect");
        assertTrue(canProcess);
    }

    @Test
    public void testCanNotProcess() throws Exception {
        boolean canProcess = command.canProcess("false");
        assertFalse(canProcess);
    }

    private void shouldPrint(String expected) {
        ArgumentCaptor<String> captor = ArgumentCaptor.forClass(String.class);
        verify(view, atLeastOnce()).write(captor.capture());
        assertEquals(expected, captor.getAllValues().toString());
    }

    @Test
    public void testProcess() throws SQLException {
        //when
        when(view.read()).thenReturn("postgres");
        when(view.read()).thenReturn("postgres");
        when(view.read()).thenReturn("postgres");
        command.process();

        //then
        verify(view).write("Please input the database name");
        verify(view).write("Please input user name");
        verify(view).write("Please input password");
        verify(command).manager.connect("", "postgres", "postgres");
        verify(view).write("Connection succeeded to postgres");

    }
}

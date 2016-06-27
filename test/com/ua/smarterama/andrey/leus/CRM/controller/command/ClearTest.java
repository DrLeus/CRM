package com.ua.smarterama.andrey.leus.CRM.controller.command;

import com.ua.smarterama.andrey.leus.CRM.controller.command.tables.ClearTable;
import com.ua.smarterama.andrey.leus.CRM.view.Console;
import org.junit.Before;
import org.junit.Test;
import com.ua.smarterama.andrey.leus.CRM.model.DataBaseManager;

import java.sql.SQLException;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class ClearTest {

    private DataBaseManager manager;
    private Console view;
    private Command command;


    @Before
    public void setup() {
        manager = mock(DataBaseManager.class);
        view = mock(Console.class);
        command = new ClearTable(manager);
    }

    public void testClearTable() throws SQLException {
        // given

        // when
        command.process();

        // then
        verify(manager).clear("user");
        verify(view).write("Таблица user была успешно очищена.");
    }

    public void testCanProcessClearWithParametersString() {
        // when
        boolean canProcess = command.canProcess("clear|user");

        // then
        assertTrue(canProcess);
    }

    public void testCantProcessClearWithoutParametersString() {
        // when
        boolean canProcess = command.canProcess("clear");

        // then
        assertFalse(canProcess);
    }

    public void testValidationErrorWhenCountParametersIsLessThan2() throws SQLException {
        // when
        try {
            command.process();
            fail();
        } catch (IllegalArgumentException e) {
            // then
            assertEquals("Формат команды 'clear|tableName', а ты ввел: clear", e.getMessage());
        }
    }

    public void testValidationErrorWhenCountParametersIsMoreThan2() {
        // when
        try {
            command.process();
            fail();
        } catch (IllegalArgumentException e) {
            // then
            assertEquals("Формат команды 'clear|tableName', а ты ввел: clear|table|qwe", e.getMessage());
        }
    }
}

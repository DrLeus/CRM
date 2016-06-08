package com.ua.smarterama.andrey.leus.CRM.controller.command;

import com.ua.smarterama.andrey.leus.CRM.model.JDBCDataBaseManager;
import com.ua.smarterama.andrey.leus.CRM.view.Console;
import jdk.nashorn.internal.ir.annotations.Ignore;
import org.junit.Before;
import org.junit.Test;

import java.sql.SQLException;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class CreateSequenceTest {

    private Command command;
    private JDBCDataBaseManager manager = new JDBCDataBaseManager();
    private Console view;

    @Before
    public void setup() {
        manager = mock(JDBCDataBaseManager.class);
        view = mock(Console.class);
        command = new ConnectToDataBase(manager);
    }

    @Ignore
    @Test
    public void testCanProcessWithParameters() {
        //when
//        UserInput input = new UserInput("connect|test|user|password");
//        boolean is = command.is(input);

        //then
//        assertTrue(is);
    }

    @Ignore
    @Test
    public void testCanProcessWithoutParameters() {
        //when
//        UserInput input = new UserInput("connect");
//        boolean is = command.is(input);
//
//        //then
//        assertTrue(is);
    }

    @Ignore
    @Test
    public void testProcess() throws SQLException {
        //when

        //then
        verify(command).manager.connect("CRM", "postgres", "postgres");
        verify(view).write("Connection succeeded to CRM");

    }
}

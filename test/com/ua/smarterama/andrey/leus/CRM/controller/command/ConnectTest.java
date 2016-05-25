package com.ua.smarterama.andrey.leus.CRM.controller.command;

import com.ua.smarterama.andrey.leus.CRM.model.DataBaseManager;
import com.ua.smarterama.andrey.leus.CRM.view.Console;
import com.ua.smarterama.andrey.leus.CRM.view.View;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class ConnectTest {

    private Command command;
    private DataBaseManager manager;
    private Console view;

    @Before
    public void setup() {
        manager = mock(DataBaseManager.class);
        view = mock(Console.class);
        command = new ConnectToDataBase(manager, view);
    }

    @Test
    public void testCanProcessWithParameters() {
        //when
//        UserInput input = new UserInput("connect|test|user|password");
//        boolean is = command.is(input);

        //then
//        assertTrue(is);
    }

    @Test
    public void testCanProcessWithoutParameters() {
        //when
//        UserInput input = new UserInput("connect");
//        boolean is = command.is(input);
//
//        //then
//        assertTrue(is);
    }

    @Test
    public void testProcess() {
        //when

        //then
        verify(command).manager.connect("CRM", "postgres", "postgres");
        verify(view).write("Connection succeeded to CRM");

    }
}

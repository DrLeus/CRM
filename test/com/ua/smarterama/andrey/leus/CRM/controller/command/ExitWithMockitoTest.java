//package com.ua.smarterama.andrey.leus.CRM.controller.command;
//
//import com.ua.smarterama.andrey.leus.CRM.view.Console;
//import com.ua.smarterama.andrey.leus.CRM.view.View;
//import org.junit.Test;
//import org.mockito.Mockito;
//
//import static junit.framework.TestCase.assertTrue;
//import static org.junit.Assert.assertFalse;
//import static org.junit.Assert.fail;
//
///**
// * Created by indigo on 01.09.2015.
// */
//public class ExitWithMockitoTest {
//
//    private Console view = Mockito.mock(Console.class);
//
//    @Test
//    public void testCanProcessExitString() {
//        // given
//        Command command = new Exit(view);
//
//        // when
//        boolean canProcess = command.canProcess("exit");
//
//        // then
//        assertTrue(canProcess);
//    }
//
//    @Test
//    public void testCantProcessQweString() {
//        // given
//        Command command = new Exit(view);
//
//        // when
//        boolean canProcess = command.canProcess("qwe");
//
//        // then
//        assertFalse(canProcess);
//    }
//
//    @Test
//    public void testProcessExitCommand_thowsExitException() {
//        // given
//        Command command = new Exit(view);
//
//        // when
//        try {
//            command.process();
//            fail("Expected ExitException");
//        } catch (ExitException e) {
//            // do nothing
//        }
//
//        // then
//        Mockito.verify(view).write("До скорой встречи!");
//    }
//}

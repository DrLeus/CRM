package ua.com.smart.andrey.leus.CRM.controller.web;

import ua.com.smart.andrey.leus.CRM.service.Service;
import ua.com.smart.andrey.leus.CRM.service.ServiceImpl;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class MainServlet extends HttpServlet {

    private Service service;

    @Override
    public void init() throws ServletException {
        super.init();

        service = new ServiceImpl();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = getAction(req);

        if (action.startsWith("/menu") || action.equals("/")) {
            req.setAttribute("items", service.commandsListMenu());
            req.getRequestDispatcher("menu.jsp").forward(req, resp);
        } else if (action.startsWith("/help")) {
            req.getRequestDispatcher("help.jsp").forward(req, resp);
        } else if (action.startsWith("/connect")) {
            req.getRequestDispatcher("connect.jsp").forward(req, resp);
        } else if (action.startsWith("/createDB")) {
            req.getRequestDispatcher("createDB.jsp").forward(req, resp);
        } else if (action.startsWith("/dropDB")) {
            req.setAttribute("items", service.listDB());
            req.getRequestDispatcher("dropDB.jsp").forward(req, resp);
        } else if (action.startsWith("/listDB")) {
            req.setAttribute("items", service.listDB());
            req.getRequestDispatcher("listDB.jsp").forward(req, resp);
        } else if (action.startsWith("/catalog")) {
            req.setAttribute("items", service.commandsListCatalog());
            req.getRequestDispatcher("catalog.jsp").forward(req, resp);
        } else if (action.startsWith("/createTable")) {
            req.getRequestDispatcher("createTable.jsp").forward(req, resp);
        } else if (action.startsWith("/clearTable")) {
            req.setAttribute("items", service.getListTables());
            req.getRequestDispatcher("removeTable.jsp").forward(req, resp);
        } else if (action.startsWith("/removeTable")) {
            req.setAttribute("items", service.getListTables());
            req.getRequestDispatcher("removeTable.jsp").forward(req, resp);
        } else if (action.startsWith("/getTable")) {
            req.setAttribute("items", service.getListTables());
            req.getRequestDispatcher("selectTable.jsp").forward(req, resp);
        } else if (action.startsWith("/insertData")) {
            req.setAttribute("items", service.getListTables());
            req.getRequestDispatcher("insertData.jsp").forward(req, resp);
        } else if (action.startsWith("/exit")) {
            req.getRequestDispatcher("exit.jsp").forward(req, resp);
//            System.exit(0);
        } else {
            req.getRequestDispatcher("error.jsp").forward(req, resp);
        }
    }

    private String getAction(HttpServletRequest req) {
        String requestURI = req.getRequestURI();
        return requestURI.substring(req.getContextPath().length(), requestURI.length());
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String action = getAction(req);

        try {
            if (action.startsWith("/connect")) {
                String databaseName = req.getParameter("dbname");
                String userName = req.getParameter("username");
                String password = req.getParameter("password");
                service.connect(databaseName, userName, password);
                resp.sendRedirect(resp.encodeRedirectURL("menu"));

            } else if (action.startsWith("/createDB")) {
                String databaseName = req.getParameter("dbname");
                service.createDB(databaseName);
                resp.sendRedirect(resp.encodeRedirectURL("menu"));

            } else if (action.startsWith("/dropDB")) {
                String databaseName = req.getParameter("dbname");
                service.dropDB(databaseName);
                resp.sendRedirect(resp.encodeRedirectURL("menu"));

            } else if (action.startsWith("/createTable")) {
                String tablename = req.getParameter("tablename");
                Map<String, String> column = new LinkedHashMap<>();
                column.put(req.getParameter("column1"), req.getParameter("type1"));
                column.put(req.getParameter("column2"), req.getParameter("type2"));
                column.put(req.getParameter("column3"), req.getParameter("type3"));
                service.createTable(tablename, column);
                resp.sendRedirect(resp.encodeRedirectURL("menu"));

            } else if (action.startsWith("/clearTable")) {
                String tableName = req.getParameter("tablename");
                service.clearTable(tableName);
                resp.sendRedirect(resp.encodeRedirectURL("menu"));

            } else if (action.startsWith("/removeTable")) {
                String tableName = req.getParameter("tablename");
                service.removeTable(tableName);
                resp.sendRedirect(resp.encodeRedirectURL("menu"));

            } else if (action.startsWith("/selectTable")) {
                String tableName = req.getParameter("tablename");
                req.setAttribute("columns", service.getColumnNames(tableName));
                req.setAttribute("data", service.getTableData(tableName));
                req.getRequestDispatcher("getTable.jsp").forward(req, resp);

            } else if (action.startsWith("/insertData")) {
                String tableName = req.getParameter("tablename");
                String value1 = req.getParameter("value1");
                String value2 = req.getParameter("value2");
                String value3 = req.getParameter("value3");
                service.insertData(tableName, value1, value2, value3);
                resp.sendRedirect(resp.encodeRedirectURL("menu"));
            }

        } catch (Exception e) {
            req.setAttribute("message", e.getMessage());
            req.getRequestDispatcher("error.jsp").forward(req, resp);
        }
    }
}


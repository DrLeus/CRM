<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
    <head>
        <title>SQLCmd</title>
    </head>
    <body>
        <form action="createTable" method="post">
            <table>
                <tr>
                    <td>Input table name for creating</td>
                    <td><input type="text" name="tablename"/></td>
                </tr>
                <tr>
                    <td>Input name for column1</td>
                    <td><input type="text" name="column1"/></td>
                    <td>type</td>
                    <td><input type="text" name="type1"/></td>
                </tr>

                <tr>
                    <td>Input name for column2</td>
                    <td><input type="text" name="column2"/></td>
                    <td>type</td>
                    <td><input type="text" name="type2"/></td>
                </tr>


                <tr>
                    <td>Input name for column3</td>
                    <td><input type="text" name="column3"/></td>
                    <td>type</td>
                    <td><input type="text" name="type3"/></td>
                </tr>


                <tr>
                    <td></td>
                    <td><input type="submit" value="create"/></td>
                </tr>
                <td></td>
                <td>
                    <a href="menu">menu</a><br>
                </td>
                </tr>
            </table>
        </form>
    </body>
</html>
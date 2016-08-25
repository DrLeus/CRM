<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
    <head>
        <title>SQLCmd</title>
    </head>
    <body>
        <form action="insertData" method="post">
            <table>
                <c:forEach items="${items}" var="item">
                    <a href="${item}">${item}</a><br>
                </c:forEach>
                <br>
                <tr>
                    <td>Input table name </td>
                    <td><input type="text" name="tablename"/></td>
                </tr>
                <tr>
                    <td>Input value for column1</td>
                    <td>value1</td>
                    <td><input type="text" name="value1"/></td>
                </tr>

                <tr>
                    <td>Input value for column2</td>
                    <td>value2</td>
                    <td><input type="text" name="value2"/></td>
                </tr>


                <tr>
                    <td>Input value for column3</td>
                    <td>value3</td>
                    <td><input type="text" name="value3"/></td>
                </tr>


                <tr>
                    <td></td>
                    <td><input type="submit" value="insert"/></td>
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
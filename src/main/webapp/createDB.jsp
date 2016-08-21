<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
    <head>
        <title>SQLCmd</title>
    </head>
    <body>
        <form action="createDB" method="post">
            <table>
                <tr>
                    <td>Input database name for creating</td>
                    <td><input type="text" name="dbname"/></td>
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
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>SQLCmd</title>
</head>
<body>
<form action="clearTable" method="post">
    <table>
        <c:forEach items="${items}" var="item">
            <a href="${item}">${item}</a><br>
        </c:forEach>
        <tr>
            <td>Input tablename to clear</td>
            <td><input type="text" name="tablename"/></td>
        </tr>
        <tr>
            <td></td>
            <td><input type="submit" value="clear"/></td>
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
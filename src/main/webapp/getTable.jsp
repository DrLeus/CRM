<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>SQLCmd</title>
</head>
<body>
<form action="selectTable" method="post">
    <table>
        <tr>
            <c:forEach items="${columns}" var="columns">
            <td><a href="${columns}">${columns}</a></td>
            </c:forEach>
            <br>

        <tr>
            <c:forEach items="${data}" var="data">
                <td><a href="${data}">${data}</a></td>
            </c:forEach>
        </tr>

        <td>
            <a href="menu">menu</a><br>
        </td>
    </table>
</form>
</body>
</html>
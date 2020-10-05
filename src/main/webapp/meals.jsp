<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<html lang="ru">
<head>
    <title>Title</title>
    <style type="text/css">
        table {
            width: 600px;
            text-align: left;
            font-size: 1em;
        }

        table td, table th {
            border: 1px solid #AAAAAA;
            padding: 5px 4px;
        }

        tr.redText {
            color: red;
        }

        tr.greenText {
            color: green;
        }
    </style>
</head>
<body>
<h3><a href="index.html">Home</a></h3>
<hr>
<h2>Meals</h2>
<table>
    <tr>
        <th>Date</th>
        <th>Description</th>
        <th>Calories</th>
    </tr>
    <c:forEach var="meal" items="${meals}">
        <tr class="${meal.excess ? 'redText' : 'greenText'}">
            <td>
                <fmt:parseDate value="${meal.dateTime}" var="parsedDate" pattern="yyyy-MM-dd'T'HH:mm"/>
                <fmt:formatDate value="${parsedDate}" pattern="dd.MM.yyyy HH:mm"/>
            </td>
            <td>${meal.description}</td>
            <td>${meal.calories}</td>
        </tr>
    </c:forEach>
</table>
</body>
</html>
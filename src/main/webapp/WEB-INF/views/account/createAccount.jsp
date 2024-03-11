<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<form action="/account/create" method="post">
    <label>Bank:</label>
        <select name="bankId">
            <c:forEach items="${banks}" var="banks" varStatus="loop">
                <option value="${banks.id}">
                        ${banks.name}
                </option>
            </c:forEach>
        </select>

    <label>Currency:</label>
    <select name="currency">
        <c:forEach items="${currencies}" var="currencies" varStatus="loop">
            <option value="${currencies}">
                    ${currencies}
            </option>
        </c:forEach>
    </select>
    <input type="submit" name="createAccount">
</form>
</body>
</html>

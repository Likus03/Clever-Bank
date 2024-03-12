<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Withdrawals</title>
</head>
<body>
<form action="/transaction/withdrawals" method="post">
    <input type="text" name="iban" placeholder="Enter account number">
    <input type="text" name="amount" placeholder="Enter amount">

    <label>Currency:</label>
    <select name="currency">
        <c:forEach items="${currencies}" var="currencies" varStatus="loop">
            <option value="${currencies}">
                    ${currencies}
            </option>
        </c:forEach>
    </select>
    <input type="submit" value="withdrawals">
</form>
</body>
</html>

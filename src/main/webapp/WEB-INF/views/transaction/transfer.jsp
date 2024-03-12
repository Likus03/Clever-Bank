<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Transfer</title>
</head>
<body>
<form action="/transaction/transfer" method="post">
    <input type="text" name="senderIban" placeholder="Enter your account number" required>
    <input type="number" step="0.01" name="amount" placeholder="Enter amount" required>

    <label>Currency:</label>
    <select name="currency">
        <c:forEach items="${currencies}" var="currencies" varStatus="loop">
            <option value="${currencies}">
                    ${currencies}
            </option>
        </c:forEach>
    </select>

    <input type="text" name="recipientIban" placeholder="Enter the recipient's account number" required>
    <input type="submit" value="withdrawals">
</form>
</body>
</html>

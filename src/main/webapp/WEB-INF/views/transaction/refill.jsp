<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Refill</title>
</head>
<body>
<form action="/transaction/refill" method="post">
    <input type="text" name="iban" placeholder="Enter account number">
<%--    <input type="text" name="recipientAccountId" placeholder="Enter recipient account number">--%>
    <input type="text" name="amount" placeholder="Enter amount">
    <input type="text" name="currency" placeholder="Enter currency">
    <input type="submit" value="refill">
</form>
</body>
</html>

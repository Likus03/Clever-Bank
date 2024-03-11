<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<form action="/account/create" method="post">
    <label>
        <%--    выпадающий список--%>
        <input type="text" name="name" placeholder="indicate the name of the bank">
    </label>
    <label>
        <%--    выпадающий список--%>
        <input type="text" name="currency" placeholder="indicate the currency of the account">
    </label>
    <input type="submit" name="createAccount">
</form>
</body>
</html>

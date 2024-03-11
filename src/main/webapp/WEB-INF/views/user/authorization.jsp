<%--
  Created by IntelliJ IDEA.
  User: lika_piv
  Date: 11.03.2024
  Time: 11:59
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Authorization</title>
</head>
<body>
<form action="/user/authorization" method="post">
    <input type="text" value="login" placeholder="Enter your login">
    <input type="text" value="password" placeholder="Enter your password">
    <input type="submit" value="authorization">
</form>
</body>
</html>

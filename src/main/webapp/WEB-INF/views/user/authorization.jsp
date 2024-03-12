<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Authorization</title>
</head>
<body>
<form action="/user/authorization" method="post">
    <input type="text" value="login" placeholder="Enter your login" required>
    <input type="text" value="password" placeholder="Enter your password" required>
    <input type="submit" value="authorization">
</form>
</body>
</html>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Clever-Bank</title>
</head>
<body>
<h1 style="text-align: center">Clever Bank</h1>
<h4>Authorization</h4>
<form action="/user/authorization" method="post">
    <label>
        <input type="text" name="login" placeholder="Enter your login">
    </label>
    <label>
        <input type="text" name="password" placeholder="Enter your password">
    </label>
    <input type="submit" value="authorization">
</form>
<form action="/converter" method="get">
    <input type="submit" value="Go to registration">
</form>
</body>
</html>

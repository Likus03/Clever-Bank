<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Registration</title>
</head>
<body>
<h4>Registration</h4>
<form action="/user/registration" method="post">
    <input type="text" name="login" placeholder="Enter your login">
    <input type="text" name="password" placeholder="Enter your password">
    <input type="text" name="phoneNumber" placeholder="Enter your phonenumber">
    <input type="text" name="surname" placeholder="Enter your surname">
    <input type="text" name="firstname" placeholder="Enter your firstname">
    <input type="submit" value="registration">
</form>
<form action="/user/authorization" method="get">
    <input type="submit" value="Go to authorization">
</form>
</body>
</html>

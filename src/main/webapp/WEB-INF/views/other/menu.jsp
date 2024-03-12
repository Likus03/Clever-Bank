<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Menu</title>
</head>
<body>
<form action="/account/create" method="get">
    <label>Create account: </label>
  <input type="submit" value="create">
</form>
<form action="/transaction/refill" method="get">
    <label>Refill account: </label>
    <input type="submit" value="refill">
</form>
<form action="/transaction/withdrawals" method="get">
    <label>Withdrawals: </label>
    <input type="submit" value="withdrawals">
</form>
<form action="/transaction/transfer" method="get">
    <label>Transfer: </label>
    <input type="submit" value="transfer">
</form>
</body>
</html>

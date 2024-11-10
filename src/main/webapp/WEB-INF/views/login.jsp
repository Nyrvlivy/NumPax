<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
<head>
    <title>Login - NumPax</title>
</head>
<body>
<h2>Login</h2>
<form action="${pageContext.request.contextPath}/login" method="post">
    <label for="email">Email:</label>
    <input type="email" id="email" name="email" required/><br/><br/>

    <label for="password">Senha:</label>
    <input type="password" id="password" name="password" required/><br/><br/>

    <button type="submit">Entrar</button>
</form>

<c:if test="${not empty error}">
    <p style="color:red;">${error}</p>
</c:if>
</body>
</html>

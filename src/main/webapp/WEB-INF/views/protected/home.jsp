<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="br.com.numpax.infrastructure.entities.User" %>

<!DOCTYPE html>
<html>
<head>
    <title>Home - NumPax</title>
</head>
<body>
<h2>Bem-vindo, <%= ((User) request.getAttribute("user")).getName() %>!</h2>
<p>Você está autenticado.</p>
<a href="${pageContext.request.contextPath}/logout">Sair</a>
</body>
</html>

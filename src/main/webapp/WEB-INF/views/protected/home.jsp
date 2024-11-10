<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="br.com.numpax.infrastructure.entities.User" %>

<!DOCTYPE html>
<html>
<head>
    <title>Home - NumPax</title>
</head>
<body>
<%
    User user = (User) session.getAttribute("user");
    if (user != null) {
%>
<h2>Bem-vindo, <%= user.getName() %>!</h2>
<p>Você está autenticado.</p>
<a href="${pageContext.request.contextPath}/logout">Sair</a>
<%
} else {
%>
<p>Usuário não autenticado.</p>
<a href="${pageContext.request.contextPath}/login">Fazer Login</a>
<%
    }
%>
</body>
</html>

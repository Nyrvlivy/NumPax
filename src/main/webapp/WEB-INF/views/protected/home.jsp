<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html lang="pt-BR">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Transações - Controle Financeiro</title>
    <link href="<c:url value='/static/lib/bootstrap.min.css'/>" rel="stylesheet">
    <link href="<c:url value='/static/css/modals-styles.css'/>" rel="stylesheet">
    <link href="<c:url value='/static/css/main-styles.css'/>" rel="stylesheet">
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css" rel="stylesheet">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/flatpickr/dist/flatpickr.min.css">
</head>
<body>
<script>
    var contextPath = '<c:out value="${pageContext.request.contextPath}" />';
</script>

<div class="sidebar">
    <h1>numpax</h1>
    <a href="#" class="btn btn-novo mb-3" id="novoButton"><i class="fas fa-plus"></i> Novo</a>
    <a href="#"><i class="fas fa-crown"></i> Seja Premium!</a>
    <a href="#transacoes" id="transacoes" class="active"><i class="fas fa-exchange-alt"></i> Transações</a>
    <a href="#"><i class="fas fa-home"></i> Dashboard</a>
    <a href="#contas"><i class="fas fa-university"></i> Contas</a>
    <a href="#"><i class="fas fa-chart-pie"></i> Relatórios</a>
    <a href="#"><i class="fas fa-ellipsis-h"></i> Mais opções</a>
    <a href="#"><i class="fas fa-cog"></i> Configurações</a>
    <a href="#"><i class="fas fa-question-circle"></i> Central de Ajuda</a>

    <a href="${pageContext.request.contextPath}/logout" class="btn btn-logout logout-button logout-link">
        <i class="fas fa-sign-out-alt"></i> Logout
    </a>
</div>

<div class="main-content">
    <div class="container-fluid">
        <div id="contentContainer">
            <c:if test="${not empty message}">
                <div class="alert alert-${messageType}">${message}</div>
            </c:if>
        </div>
    </div>
</div>

<div class="modal fade" id="underDevelopmentModal" tabindex="-1" aria-labelledby="underDevelopmentModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="underDevelopmentModalLabel">Oops! Função sob Desenvolvimento</h5>
                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
            </div>
            <div class="modal-body">
                <p>Este recurso está atualmente em desenvolvimento. Inscreva-se para obter acesso antecipado!</p>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Fechar</button>
                <button type="button" class="btn btn-primary">Seja Premium</button>
            </div>
        </div>
    </div>
</div>

<div id="modalContainer"></div>

<jsp:include page="/WEB-INF/jsp/partials/modals/new-transaction-menu-modal.jsp" />

<script src="<c:url value='/static/lib/bootstrap.bundle.min.js'/>"></script>
<script src="<c:url value='/static/js/main-scripts.js'/>"></script>
<script src="https://cdn.jsdelivr.net/npm/flatpickr"></script>
<script src="https://npmcdn.com/flatpickr/dist/l10n/pt.js"></script>
</body>
</html>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="pt-BR">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Controle Financeiro</title>
    <link href="<c:url value='/resources/css/bootstrap.min.css'/>" rel="stylesheet">
    <link href="<c:url value='/resources/css/all.min.css'/>" rel="stylesheet">
    <link href="<c:url value='/resources/css/flatpickr.min.css'/>" rel="stylesheet">
    <link href="<c:url value='/resources/css/modals-styles.css'/>" rel="stylesheet">
    <link href="<c:url value='/resources/css/main-styles.css'/>" rel="stylesheet">
</head>
<body>
    <div class="sidebar">
        <h1>numpax</h1>
        <a href="#" class="btn btn-novo mb-3"><i class="fas fa-plus"></i> Novo</a>
        <a href="#" class="active"><i class="fas fa-crown"></i> Seja Premium!</a>
        <a href="#transacoes"><i class="fas fa-exchange-alt"></i> Transações</a>
        <a href="#"><i class="fas fa-home"></i> Dashboard</a>
        <a href="#contas"><i class="fas fa-university"></i> Contas</a>
        <a href="#"><i class="fas fa-chart-pie"></i> Relatórios</a>
        <a href="#"><i class="fas fa-ellipsis-h"></i> Mais opções</a>
        <a href="#"><i class="fas fa-cog"></i> Configurações</a>
        <a href="#"><i class="fas fa-question-circle"></i> Central de Ajuda</a>
    </div>

    <div class="main-content">
        <div class="container-fluid">
            <div id="contentContainer">
                <c:if test="${not empty message}">
                    <div class="alert alert-${messageType}">${message}</div>
                </c:if>
                <!-- Content will be loaded here -->
            </div>
        </div>
    </div>

    <!-- Modal for under development features -->
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

    <script src="<c:url value='/resources/js/bootstrap.bundle.min.js'/>"></script>
    <script src="<c:url value='/resources/js/flatpickr.js'/>"></script>
    <script src="<c:url value='/resources/js/flatpickr.l10n.pt.js'/>"></script>
    <script src="<c:url value='/resources/js/main-scripts.js'/>"></script>
</body>
</html>
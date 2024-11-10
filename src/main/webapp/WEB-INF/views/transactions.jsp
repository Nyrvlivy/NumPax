<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="pt-BR">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Transações - Controle Financeiro</title>

    <!-- CSS Locais -->
    <link href="<c:url value='/static/lib/bootstrap.min.css'/>" rel="stylesheet">
    <link href="<c:url value='/static/css/modals-styles.css'/>" rel="stylesheet">
    <link href="<c:url value='/static/css/main-styles.css'/>" rel="stylesheet">

    <!-- CSS Externos -->
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css" rel="stylesheet">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/flatpickr/dist/flatpickr.min.css">
</head>
<body>
<div class="main-content">
    <div class="container-fluid">
        <div id="contentContainer">
            <!-- Título Dinâmico do Mês -->
            <h3 class="month-title"><c:out value="${currentMonth}" /></h3>

            <div class="summary-header">
                <div class="top-bar">
                    <!-- Dropdown de Transações -->
                    <div class="dropdown">
                        <button class="btn btn-light-blue dropdown-toggle" type="button" id="transactionsDropdown" data-bs-toggle="dropdown" aria-expanded="false">
                            Transações
                        </button>
                        <ul class="dropdown-menu" aria-labelledby="transactionsDropdown">
                            <li><a class="dropdown-item" href="#"><i class="fas fa-exchange-alt text-purple me-2"></i>Transações</a></li>
                            <li><a class="dropdown-item" href="#"><i class="fas fa-arrow-down text-danger me-2"></i>Despesas</a></li>
                            <li><a class="dropdown-item" href="#"><i class="fas fa-arrow-up text-success me-2"></i>Receitas</a></li>
                            <li><a class="dropdown-item" href="#"><i class="fas fa-exchange-alt text-info me-2"></i>Transferências</a></li>
                        </ul>
                    </div>
                </div>

                <div class="summary-buttons">
                    <div id="modalContainer"></div>
                    <button type="button" class="btn btn-expense" id="openExpenseModalBtn">
                        <i class="fas fa-arrow-down text-danger"></i> Nova Despesa
                    </button>
                    <button type="button" class="btn btn-income" id="openIncomeModalBtn">
                        <i class="fas fa-arrow-up text-success"></i> Nova Receita
                    </button>
                    <button type="button" class="btn btn-transfer" id="openTransferModalBtn">
                        <i class="fas fa-exchange-alt text-purple"></i> Nova Transferência
                    </button>
                </div>
            </div>

            <!-- Resumos Financeiros Dinâmicos -->
            <div class="summary-cards">
                <div class="summary-card">
                    <h6>Saldo atual</h6>
                    <h4>R$ <c:out value="${saldoAtual}" /></h4>
                </div>
                <div class="summary-card">
                    <h6>Receitas</h6>
                    <h4 class="text-success">R$ <c:out value="${totalReceitas}" /></h4>
                </div>
                <div class="summary-card">
                    <h6>Despesas</h6>
                    <h4 class="text-danger">R$ <c:out value="${totalDespesas}" /></h4>
                </div>
                <div class="summary-card">
                    <h6>Balanço mensal</h6>
                    <h4 class="text-primary">R$ <c:out value="${balancoMensal}" /></h4>
                </div>
            </div>

            <!-- Lista de Transações -->
            <div class="card">
                <div class="card-body">
                    <div class="d-flex justify-content-between align-items-center mb-3">
                        <h5 class="card-title mb-0">Lista de Transações</h5>
                        <div>
                            <button class="btn btn-outline-secondary me-2"><i class="fas fa-search"></i> Buscar</button>
                            <button class="btn btn-outline-secondary"><i class="fas fa-filter"></i> Filtrar</button>
                        </div>
                    </div>
                    <div class="table-responsive">
                        <table class="table">
                            <thead>
                            <tr>
                                <th>Situação</th>
                                <th>Data</th>
                                <th>Descrição</th>
                                <th>Categoria</th>
                                <th>Conta</th>
                                <th class="text-end">Valor</th>
                                <th class="text-end">Ações</th>
                            </tr>
                            </thead>
                            <tbody>
                            <c:forEach var="transacao" items="${listaTransacoes}">
                                <tr>
                                    <td>
                                        <c:choose>
                                            <c:when test="${transacao.status eq 'COMPLETADA'}">
                                                <i class="fas fa-check-circle text-success status-icon"></i>
                                            </c:when>
                                            <c:otherwise>
                                                <i class="fas fa-times-circle text-danger status-icon"></i>
                                            </c:otherwise>
                                        </c:choose>
                                    </td>
                                    <td><c:out value="${transacao.data}" /></td>
                                    <td><c:out value="${transacao.descricao}" /></td>
                                    <td>
                                                <span class="badge bg-<c:out value='${transacao.categoria.cor}' />">
                                                    <i class="fas <c:out value='${transacao.categoria.icone}' />"></i>
                                                    <c:out value="${transacao.categoria.nome}" />
                                                </span>
                                    </td>
                                    <td><c:out value="${transacao.conta}" /></td>
                                    <td class="text-end <c:out value='${transacao.tipo == "DESPESA" ? "text-danger" : "text-success"}' />">
                                        R$ <fmt:formatNumber value="${transacao.valor}" type="currency" currencySymbol="" />
                                    </td>
                                    <td class="text-end">
                                        <button class="btn btn-sm btn-outline-secondary" onclick="editarTransacao(${transacao.id})">
                                            <i class="fas fa-edit"></i>
                                        </button>
                                        <button class="btn btn-sm btn-outline-secondary" onclick="excluirTransacao(${transacao.id})">
                                            <i class="fas fa-trash"></i>
                                        </button>
                                        <button class="btn btn-sm btn-outline-secondary" onclick="detalhesTransacao(${transacao.id})">
                                            <i class="fas fa-ellipsis-v"></i>
                                        </button>
                                    </td>
                                </tr>
                            </c:forEach>
                            </tbody>
                        </table>
                    </div>
                    <!-- Paginação Dinâmica -->
                    <div class="d-flex justify-content-between align-items-center mt-3">
                        <p class="mb-0">Saldo Previsto Final do Dia: R$ <c:out value="${saldoPrevisto}" /></p>
                        <div class="d-flex align-items-center">
                            <span class="me-2">Linhas por página:</span>
                            <select id="linhasPorPagina" class="form-select form-select-sm me-3" onchange="alterarLinhasPorPagina(this.value)">
                                <option value="10" <c:if test="${linhasPorPagina == 10}">selected</c:if>>10</option>
                                <option value="20" <c:if test="${linhasPorPagina == 20}">selected</c:if>>20</option>
                                <option value="50" <c:if test="${linhasPorPagina == 50}">selected</c:if>>50</option>
                            </select>

                            <nav>
                                <ul class="pagination pagination-sm">
                                    <c:if test="${paginaAtual > 1}">
                                        <li class="page-item"><a class="page-link" href="<c:url value='/transactions?page=${paginaAtual - 1}'/>">&laquo;</a></li>
                                    </c:if>
                                    <c:forEach begin="1" end="${totalPaginas}" var="i">
                                        <li class="page-item <c:if test='${i == paginaAtual}'>active</c:if>">
                                            <a class="page-link" href="<c:url value='/transactions?page=${i}'/>">${i}</a>
                                        </li>
                                    </c:forEach>
                                    <c:if test="${paginaAtual < totalPaginas}">
                                        <li class="page-item"><a class="page-link" href="<c:url value='/transactions?page=${paginaAtual + 1}'/>">&raquo;</a></li>
                                    </c:if>
                                </ul>
                            </nav>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

<!-- Modais para Adicionar/Editar Transações (Exemplo) -->
<div class="modal fade" id="expenseModal" tabindex="-1" aria-labelledby="expenseModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <!-- Conteúdo do Modal -->
        </div>
    </div>
</div>

<!-- Scripts JS Locais -->
<script src="<c:url value='/static/lib/bootstrap.bundle.min.js'/>"></script>
<script src="<c:url value='/static/js/main-scripts.js'/>"></script>

<!-- Scripts JS Externos -->
<script src="https://cdn.jsdelivr.net/npm/flatpickr"></script>
<script src="https://npmcdn.com/flatpickr/dist/l10n/pt.js"></script>

<!-- Scripts Personalizados -->
<script>
    function editarTransacao(id) {
        // Lógica para editar transação
    }

    function excluirTransacao(id) {
        // Lógica para excluir transação
    }

    function detalhesTransacao(id) {
        // Lógica para exibir detalhes da transação
    }

    function alterarLinhasPorPagina(qtd) {
        // Lógica para alterar a quantidade de linhas por página
        window.location.href = '<c:url value="/transactions"/>?linhasPorPagina=' + qtd;
    }
</script>
</body>
</html>
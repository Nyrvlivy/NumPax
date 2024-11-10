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
<div class="main-content">
    <div class="container-fluid">
        <div id="contentContainer">
            <h3 class="page-title month-title">Minhas Transações</h3>

            <div class="summary-header">
                <div class="top-bar">
                    <!-- Dropdown de Transações -->
                    <c:set var="currentNature" value="${nature}" />
                    <div class="dropdown">
                        <button class="btn btn-light-blue dropdown-toggle" type="button" id="transactionsDropdown" data-bs-toggle="dropdown" aria-expanded="false">
                            <c:choose>
                                <c:when test="${currentNature == 'EXPENSE'}">Despesas</c:when>
                                <c:when test="${currentNature == 'INCOME'}">Receitas</c:when>
                                <c:when test="${currentNature == 'TRANSFER'}">Transferências</c:when>
                                <c:otherwise>Transações</c:otherwise>
                            </c:choose>
                        </button>
                        <ul class="dropdown-menu" aria-labelledby="transactionsDropdown">
                            <li>
                                <c:url var="allTransactionsUrl" value="/transactions">
                                    <c:param name="page" value="${paginaAtual}" />
                                    <c:param name="linhasPorPagina" value="${linhasPorPagina}" />
                                </c:url>
                                <a class="dropdown-item" href="${allTransactionsUrl}">
                                    <i class="fas fa-exchange-alt text-purple me-2"></i>Transações
                                </a>
                            </li>
                            <li>
                                <c:url var="expensesUrl" value="/transactions">
                                    <c:param name="nature" value="EXPENSE" />
                                    <c:param name="page" value="1" />
                                    <c:param name="linhasPorPagina" value="${linhasPorPagina}" />
                                </c:url>
                                <a class="dropdown-item" href="${expensesUrl}">
                                    <i class="fas fa-arrow-down text-danger me-2"></i>Despesas
                                </a>
                            </li>
                            <li>
                                <c:url var="incomesUrl" value="/transactions">
                                    <c:param name="nature" value="INCOME" />
                                    <c:param name="page" value="1" />
                                    <c:param name="linhasPorPagina" value="${linhasPorPagina}" />
                                </c:url>
                                <a class="dropdown-item" href="${incomesUrl}">
                                    <i class="fas fa-arrow-up text-success me-2"></i>Receitas
                                </a>
                            </li>
                            <li>
                                <c:url var="transfersUrl" value="/transactions">
                                    <c:param name="nature" value="TRANSFER" />
                                    <c:param name="page" value="1" />
                                    <c:param name="linhasPorPagina" value="${linhasPorPagina}" />
                                </c:url>
                                <a class="dropdown-item" href="${transfersUrl}">
                                    <i class="fas fa-exchange-alt text-info me-2"></i>Transferências
                                </a>
                            </li>
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

            <!-- Summary Cards -->
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
                            <!-- Verifica se a lista de transações está vazia -->
                            <c:if test="${empty listaTransacoes}">
                                <tr>
                                    <td colspan="7" class="text-center">Sem transações registradas.</td>
                                </tr>
                            </c:if>

                            <!-- Itera sobre a lista de transações se não estiver vazia -->
                            <c:forEach var="transacao" items="${listaTransacoes}">
                                <tr>
                                    <td>
                                        <c:choose>
                                            <c:when test="${transacao.effective}">
                                                <i class="fas fa-check-circle text-success status-icon"></i>
                                            </c:when>
                                            <c:otherwise>
                                                <i class="fas fa-times-circle text-danger status-icon"></i>
                                            </c:otherwise>
                                        </c:choose>
                                    </td>
                                    <td>
                                        <fmt:formatDate value="${transacao.transactionDate}" pattern="dd/MM/yyyy" />
                                    </td>
                                    <td><c:out value="${transacao.name}" /></td>
                                    <td>
                                            <span class="badge bg-primary">
                                                <i class="fas fa-tag me-2"></i>
                                                <c:out value="${transacao.categoryName}" />
                                            </span>
                                    </td>
                                    <td><c:out value="${transacao.accountName}" /></td>
                                    <td class="text-end <c:out value='${transacao.amount < 0 ? "text-danger" : "text-success"}' />">
                                        R$ <fmt:formatNumber value="${transacao.amount}" type="currency" currencySymbol="" />
                                    </td>
                                    <td class="text-end">
                                        <button class="btn btn-sm btn-outline-secondary" onclick="editarTransacao('${transacao.transactionId}')">
                                            <i class="fas fa-edit"></i>
                                        </button>
                                        <button class="btn btn-sm btn-outline-secondary" onclick="excluirTransacao('${transacao.transactionId}')">
                                            <i class="fas fa-trash"></i>
                                        </button>
                                        <button class="btn btn-sm btn-outline-secondary" onclick="detalhesTransacao('${transacao.transactionId}')">
                                            <i class="fas fa-ellipsis-v"></i>
                                        </button>
                                    </td>
                                </tr>
                            </c:forEach>
                            </tbody>
                        </table>
                    </div>

                    <!-- Paginação -->
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
                                        <c:url var="prevPageUrl" value="/transactions">
                                            <c:param name="page" value="${paginaAtual - 1}" />
                                            <c:if test="${currentNature != null}">
                                                <c:param name="nature" value="${currentNature}" />
                                            </c:if>
                                            <c:param name="linhasPorPagina" value="${linhasPorPagina}" />
                                        </c:url>
                                        <li class="page-item">
                                            <a class="page-link" href="${prevPageUrl}">&laquo;</a>
                                        </li>
                                    </c:if>
                                    <c:forEach begin="1" end="${totalPaginas}" var="i">
                                        <c:url var="pageUrl" value="/transactions">
                                            <c:param name="page" value="${i}" />
                                            <c:if test="${currentNature != null}">
                                                <c:param name="nature" value="${currentNature}" />
                                            </c:if>
                                            <c:param name="linhasPorPagina" value="${linhasPorPagina}" />
                                        </c:url>
                                        <li class="page-item <c:if test='${i == paginaAtual}'>active</c:if>">
                                            <a class="page-link" href="${pageUrl}">${i}</a>
                                        </li>
                                    </c:forEach>
                                    <c:if test="${paginaAtual < totalPaginas}">
                                        <c:url var="nextPageUrl" value="/transactions">
                                            <c:param name="page" value="${paginaAtual + 1}" />
                                            <c:if test="${currentNature != null}">
                                                <c:param name="nature" value="${currentNature}" />
                                            </c:if>
                                            <c:param name="linhasPorPagina" value="${linhasPorPagina}" />
                                        </c:url>
                                        <li class="page-item">
                                            <a class="page-link" href="${nextPageUrl}">&raquo;</a>
                                        </li>
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

<script src="<c:url value='/static/lib/bootstrap.bundle.min.js'/>"></script>
<script src="<c:url value='/static/js/main-scripts.js'/>"></script>
<script src="https://cdn.jsdelivr.net/npm/flatpickr"></script>
<script src="https://npmcdn.com/flatpickr/dist/l10n/pt.js"></script>

<script>
    function editarTransacao(id) {
        console.log('Editar transação:', id);
    }

    function excluirTransacao(id) {
        console.log('Excluir transação:', id);
    }

    function detalhesTransacao(id) {
        console.log('Detalhes da transação:', id);
    }

    function alterarLinhasPorPagina(qtd) {
        const url = new URL(window.location.href);
        url.searchParams.set('linhasPorPagina', qtd);
        window.location.href = url.toString();
    }
</script>
</body>
</html>

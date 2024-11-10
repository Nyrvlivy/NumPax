<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<div class="main-content">
    <div class="container-fluid">
        <div id="contentContainer">
            <h3 class="month-title"><fmt:formatDate value="${currentDate}" pattern="MMMM, yyyy"/></h3>
            
            // ... dropdown menu code remains the same ...

            <div class="summary-cards">
                <div class="summary-card">
                    <h6>Saldo atual</h6>
                    <h4>R$ <fmt:formatNumber value="${currentBalance}" type="number" pattern="#,##0.00"/></h4>
                </div>
                <div class="summary-card">
                    <h6>Receitas</h6>
                    <h4 class="text-success">R$ <fmt:formatNumber value="${totalIncome}" type="number" pattern="#,##0.00"/></h4>
                </div>
                <div class="summary-card">
                    <h6>Despesas</h6>
                    <h4 class="text-danger">R$ <fmt:formatNumber value="${totalExpenses}" type="number" pattern="#,##0.00"/></h4>
                </div>
                <div class="summary-card">
                    <h6>Balanço mensal</h6>
                    <h4 class="text-primary">R$ <fmt:formatNumber value="${monthlyBalance}" type="number" pattern="#,##0.00"/></h4>
                </div>
            </div>

            <div class="card">
                // ... card header remains the same ...
                
                <div class="table-responsive">
                    <table class="table">
                        <thead>
                            // ... thead remains the same ...
                        </thead>
                        <tbody>
                            <c:forEach items="${transactions}" var="transaction">
                                <tr>
                                    <td><i class="fas fa-check-circle text-success status-icon"></i></td>
                                    <td><fmt:formatDate value="${transaction.date}" pattern="dd/MM/yyyy"/></td>
                                    <td>${transaction.description}</td>
                                    <td>
                                        <span class="badge bg-${transaction.category.color}">
                                            <i class="fas ${transaction.category.icon}"></i> ${transaction.category.name}
                                        </span>
                                    </td>
                                    <td>${transaction.account}</td>
                                    <td class="text-end ${transaction.type == 'EXPENSE' ? 'text-danger' : 'text-success'}">
                                        R$ <fmt:formatNumber value="${transaction.amount}" type="number" pattern="#,##0.00"/>
                                    </td>
                                    <td class="text-end">
                                        <button class="btn btn-sm btn-outline-secondary" onclick="editTransaction(${transaction.id})">
                                            <i class="fas fa-edit"></i>
                                        </button>
                                        <button class="btn btn-sm btn-outline-secondary" onclick="deleteTransaction(${transaction.id})">
                                            <i class="fas fa-trash"></i>
                                        </button>
                                        <button class="btn btn-sm btn-outline-secondary">
                                            <i class="fas fa-ellipsis-v"></i>
                                        </button>
                                    </td>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                </div>

                <div class="d-flex justify-content-between align-items-center mt-3">
                    <p class="mb-0">Saldo Previsto Final do Dia: R$ <fmt:formatNumber value="${predictedBalance}" type="number" pattern="#,##0.00"/></p>
                    <div class="d-flex align-items-center">
                        <span class="me-2">Linhas por página: ${pageSize}</span>
                        <nav>
                            <ul class="pagination pagination-sm">
                                <li class="page-item ${currentPage == 1 ? 'disabled' : ''}">
                                    <a class="page-link" href="?page=${currentPage - 1}">&laquo;</a>
                                </li>
                                <c:forEach begin="1" end="${totalPages}" var="page">
                                    <li class="page-item ${currentPage == page ? 'active' : ''}">
                                        <a class="page-link" href="?page=${page}">${page}</a>
                                    </li>
                                </c:forEach>
                                <li class="page-item ${currentPage == totalPages ? 'disabled' : ''}">
                                    <a class="page-link" href="?page=${currentPage + 1}">&raquo;</a>
                                </li>
                            </ul>
                        </nav>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div> 
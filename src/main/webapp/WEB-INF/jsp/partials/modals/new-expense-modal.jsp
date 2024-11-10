<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!-- Modal HTML -->
<div class="modal fade" id="expenseModal" tabindex="-1" aria-labelledby="expenseModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="expenseModalLabel">Nova Despesa</h5>
                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
            </div>
            <div class="modal-body">
                <!-- Conteúdo do Modal -->
                <form id="expense-form" action="${pageContext.request.contextPath}/addExpense" method="post">
                    <!-- Campos do formulário -->
                    <div class="mb-3">
                        <label for="expenseDescription" class="form-label">Descrição</label>
                        <input type="text" class="form-control" id="expenseDescription" name="description" required>
                    </div>
                    <div class="mb-3">
                        <label for="expenseAmount" class="form-label">Valor</label>
                        <input type="number" step="0.01" class="form-control" id="expenseAmount" name="amount" required>
                    </div>
                    <div class="mb-3">
                        <label for="expenseDate" class="form-label">Data</label>
                        <input type="date" class="form-control" id="expenseDate" name="date" required>
                    </div>
                </form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Fechar</button>
                <button type="button" class="btn btn-primary" onclick="submitExpenseForm()">Salvar</button>
            </div>
        </div>
    </div>
</div>

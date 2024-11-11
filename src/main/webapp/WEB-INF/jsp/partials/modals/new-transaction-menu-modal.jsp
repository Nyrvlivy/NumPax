<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c" %>
<div class="modal new-transaction-menu-modal" tabindex="-1" aria-labelledby="newTransactionMenuModalLabel" aria-hidden="true">
    <div class="modal-dialog modal-dialog-centered modal-md">
        <div class="modal-content">

            <div class="modal-header">
                <h5 class="modal-title" id="newTransactionMenuModalLabel">Criar Nova Transação</h5>
                <p class="modal-subtitle">Escolha o tipo de transação que deseja adicionar</p>
                <button type="button" class="btn-close" aria-label="Fechar"></button>
            </div>

            <div class="modal-body">
                <div class="d-flex flex-column align-items-center">
                    <button type="button" class="btn btn-expense mb-3 w-75" id="menuOpenExpenseModalBtn" aria-label="Criar nova despesa">
                        <span class="emoji" role="img" aria-label="Despesa">💸</span> Nova Despesa
                    </button>
                    <button type="button" class="btn btn-income mb-3 w-75" id="menuOpenIncomeModalBtn" aria-label="Criar nova receita">
                        <span class="emoji" role="img" aria-label="Receita">💰</span> Nova Receita
                    </button>
                    <button type="button" class="btn btn-transfer mb-3 w-75" id="menuOpenTransferModalBtn" aria-label="Criar nova transferência">
                        <span class="emoji" role="img" aria-label="Transferência">🔄</span> Nova Transferência
                    </button>
                </div>
            </div>

            <div class="modal-footer justify-content-center">
                <button type="button" class="btn btn-secondary" data-bs-dismiss="modal" aria-label="Cancelar">Cancelar</button>
            </div>
        </div>
    </div>
</div>

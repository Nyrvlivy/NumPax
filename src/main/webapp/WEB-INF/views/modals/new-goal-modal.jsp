<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<div class="modal goals-modal show d-block" tabindex="-1">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title">Novo Objetivo</h5>
                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Fechar"></button>
            </div>
            <div class="modal-body">
                <form:form modelAttribute="goal" action="${pageContext.request.contextPath}/goals/save" method="POST">
                    <div class="row">
                        <div class="col-md-6">
                            <div class="mb-3">
                                <form:label path="name" class="form-label">Nome do objetivo</form:label>
                                <form:input path="name" class="form-control" placeholder="Educação"/>
                            </div>
                            <div class="mb-3">
                                <form:label path="targetDate" class="form-label">Data</form:label>
                                <form:input path="targetDate" class="form-control" placeholder="08 novembro 2024"/>
                            </div>
                            <div class="mb-3">
                                <label class="form-label">Ícone</label>
                                <div class="d-flex align-items-center">
                                    <c:forEach var="icon" items="${icons}">
                                        <button type="button" class="btn btn-icon" data-icon="${icon}">
                                            <i class="fas fa-${icon}"></i>
                                        </button>
                                    </c:forEach>
                                    <button type="button" class="btn btn-secondary ms-2">OUTROS</button>
                                </div>
                                <form:hidden path="icon" id="selectedIcon"/>
                            </div>
                            <div class="mb-3">
                                <form:label path="description" class="form-label">Descrição</form:label>
                                <form:textarea path="description" class="form-control" rows="3"/>
                            </div>
                        </div>
                        <div class="col-md-6">
                            <div class="mb-3">
                                <form:label path="targetValue" class="form-label">Valor do objetivo</form:label>
                                <form:input path="targetValue" class="form-control" placeholder="R$ 0,00"/>
                            </div>
                            <div class="mb-3">
                                <form:label path="initialValue" class="form-label">Valor inicial do objetivo</form:label>
                                <form:input path="initialValue" class="form-control" placeholder="R$ 0,00"/>
                            </div>
                            <div class="mb-3">
                                <label class="form-label">Cor</label>
                                <div class="d-flex align-items-center">
                                    <c:forEach var="color" items="${colors}">
                                        <button type="button" class="btn btn-color" data-color="${color}" style="background-color: ${color}"></button>
                                    </c:forEach>
                                    <button type="button" class="btn btn-secondary ms-2">OUTROS</button>
                                </div>
                                <form:hidden path="color" id="selectedColor"/>
                            </div>
                        </div>
                    </div>
                    <div class="modal-footer">
                        <button type="submit" class="btn btn-save">SALVAR</button>
                    </div>
                </form:form>
            </div>
        </div>
    </div>
</div>
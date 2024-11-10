<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<div class="modal transfer-modal show d-block" tabindex="-1">
    <div class="modal-dialog">
        <div class="modal-content">
            <!-- ... header remains the same ... -->
            
            <div class="modal-body">
                <form action="<c:url value='/transfer/save'/>" method="post" enctype="multipart/form-data">
                    <!-- Valor da Transferência -->
                    <div class="mb-3 value-input-container">
                        <span class="value-input-prefix" aria-hidden="true">R$</span>
                        <input type="text" name="transferValue" class="value-input" value="${transfer.value != null ? transfer.value : '0,00'}" aria-label="Valor da transferência">
                        <span class="currency-suffix">BRL</span>
                    </div>
                    
                    <!-- Status da Transferência -->
                    <div class="mb-3 d-flex justify-content-between align-items-center">
                        <label class="form-check-label" for="effectedCheck">Foi efetuada</label>
                        <div class="form-check form-switch">
                            <input class="form-check-input" type="checkbox" id="paidCheck" name="effected" 
                                   ${transfer.effected ? 'checked' : ''} aria-label="Marcar como paga">
                        </div>
                    </div>

                    <!-- Contas Dropdown -->
                    <div class="mb-3 dropdown-field" id="sourceAccountDropdown">
                        <select name="sourceAccountId" class="form-control" required>
                            <option value="">Selecione a conta de origem</option>
                            <c:forEach items="${accounts}" var="account">
                                <option value="${account.id}" ${transfer.sourceAccount.id == account.id ? 'selected' : ''}>
                                    <i class="fas ${account.icon} me-2"></i>${account.name}
                                </option>
                            </c:forEach>
                        </select>
                    </div>
                    
                    <div class="mb-3 dropdown-field" id="destinationAccountDropdown">
                        <select name="destinationAccountId" class="form-control" required>
                            <option value="">Selecione a conta de destino</option>
                            <c:forEach items="${accounts}" var="account">
                                <option value="${account.id}" ${transfer.destinationAccount.id == account.id ? 'selected' : ''}>
                                    <i class="fas ${account.icon} me-2"></i>${account.name}
                                </option>
                            </c:forEach>
                        </select>
                    </div>

                    <!-- ... rest of the form remains similar but with proper name attributes and value bindings ... -->
                    
                    <div id="moreDetailsSection" style="display: none;">
                        <div class="mb-3">
                            <textarea class="form-control" name="observation" placeholder="Observação" rows="3" 
                                      aria-label="Observações adicionais">${transfer.observation}</textarea>
                        </div>
                        
                        <div class="mb-3 d-flex justify-content-between align-items-center">
                            <label class="form-check-label" for="fixedTransferCheck">Transferência fixa</label>
                            <div class="form-check form-switch">
                                <input class="form-check-input" type="checkbox" id="fixedTransferCheck" 
                                       name="fixed" ${transfer.fixed ? 'checked' : ''} 
                                       aria-label="Marcar como transferência fixa">
                            </div>
                        </div>
                        
                        <!-- ... repeat options remain similar with proper bindings ... -->
                    </div>
                    
                    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                </form>
            </div>
            <!-- ... footer remains the same ... -->
        </div>
    </div>
</div> 
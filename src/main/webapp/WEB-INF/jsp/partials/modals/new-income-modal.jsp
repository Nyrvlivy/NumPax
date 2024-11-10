<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<div class="modal income-modal show d-block" tabindex="-1">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title">Nova Receita</h5>
                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Fechar"></button>
            </div>
            <div class="modal-body">
                <form:form modelAttribute="incomeForm" method="POST" action="${pageContext.request.contextPath}/income/save">
                    <div class="mb-3 value-input-container">
                        <span class="value-input-prefix" aria-hidden="true">R$</span>
                        <form:input path="value" type="text" class="value-input" value="0,00" aria-label="Valor da receita"/>
                        <span class="currency-suffix">BRL</span>
                    </div>
                    <form:errors path="value" class="form-text text-danger" aria-live="polite"/>
                    
                    <div class="mb-3 d-flex justify-content-between align-items-center">
                        <label class="form-check-label" for="receivedCheck">Foi recebida</label>
                        <div class="form-check form-switch">
                            <form:checkbox path="received" class="form-check-input" id="receivedCheck" aria-label="Marcar como recebida"/>
                        </div>
                    </div>
                    
                    <!-- ... existing date buttons ... -->
                    
                    <div class="mb-3">
                        <form:input path="description" type="text" class="form-control" placeholder="Descrição" aria-label="Descrição da receita"/>
                    </div>
                    
                    <div class="mb-3 dropdown-field" id="categoryDropdown">
                        <form:select path="category" class="d-none">
                            <form:options items="${categories}" itemLabel="name" itemValue="id"/>
                        </form:select>
                        <!-- ... existing category dropdown UI ... -->
                    </div>
                    
                    <div class="mb-3 dropdown-field" id="destinationAccountDropdown">
                        <form:select path="destinationAccount" class="d-none">
                            <form:options items="${accounts}" itemLabel="name" itemValue="id"/>
                        </form:select>
                        <!-- ... existing account dropdown UI ... -->
                    </div>
                    
                    <div class="mb-3">
                        <form:input path="source" type="text" class="form-control" placeholder="De Onde Veio" aria-label="Origem da receita"/>
                    </div>
                    
                    <!-- ... existing file attachment section ... -->
                    
                    <div id="moreDetailsSection" style="display: none;">
                        <div class="mb-3">
                            <form:textarea path="observation" class="form-control" placeholder="Observação" rows="3" aria-label="Observações adicionais"/>
                        </div>
                        
                        <div class="mb-3 d-flex justify-content-between align-items-center">
                            <label class="form-check-label" for="fixedIncomeCheck">Receita fixa</label>
                            <div class="form-check form-switch">
                                <form:checkbox path="fixed" class="form-check-input" id="fixedIncomeCheck" aria-label="Marcar como receita fixa"/>
                            </div>
                        </div>
                        
                        <div class="mb-3 d-flex justify-content-between align-items-center">
                            <label class="form-check-label" for="repeatCheck">Repetir</label>
                            <div class="form-check form-switch">
                                <form:checkbox path="repeat" class="form-check-input" id="repeatCheck" aria-label="Repetir receita"/>
                            </div>
                        </div>

                        <div class="mb-3" id="repeatOptionsSection" style="display: none;">
                            <label for="repeatFrequency" class="form-label">Frequência: </label>
                            <form:select path="frequency" class="form-select" id="repeatFrequency" aria-label="Frequência de repetição">
                                <form:option value="diariamente">Diariamente</form:option>
                                <form:option value="semanalmente">Semanalmente</form:option>
                                <form:option value="mensalmente">Mensalmente</form:option>
                                <form:option value="anualmente">Anualmente</form:option>
                            </form:select>
                        </div>
                    </div>
                    
                    <div class="modal-footer">
                        <button type="submit" name="saveAndNew" class="btn btn-save-and-new" aria-label="Salvar e criar nova receita">SALVAR E CRIAR NOVA</button>
                        <button type="submit" class="btn btn-save" aria-label="Salvar receita">SALVAR</button>
                    </div>
                </form:form>
            </div>
        </div>
    </div>
</div>
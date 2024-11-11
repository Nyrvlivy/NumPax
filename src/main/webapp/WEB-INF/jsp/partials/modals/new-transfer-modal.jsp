<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div class="modal transfer-modal" tabindex="-1">
    <div class="modal-dialog">
        <div class="modal-content">

            <div class="modal-header">
                <h5 class="modal-title">Nova Transferência</h5>
                <button type="button" class="btn-close" aria-label="Fechar"></button>
            </div>

            <div class="modal-body">
                <form>
                    <div class="mb-3 value-input-container">
                        <span class="value-input-prefix" aria-hidden="true">R$</span>
                        <input type="text" class="value-input" value="0,00" aria-label="Valor da transferência">
                        <span class="currency-suffix">BRL</span>
                    </div>
                    <div class="form-text text-danger" aria-live="polite">Deve ter um valor maior que 0</div>

                    <div class="mb-3 d-flex justify-content-between align-items-center">
                        <label class="form-check-label" for="effectedCheck">Foi efetuada</label>
                        <div class="form-check form-switch">
                            <input class="form-check-input" type="checkbox" id="effectedCheck" aria-label="Marcar como efetuada">
                        </div>
                    </div>

                    <div class="mb-3">
                        <button type="button" class="btn btn-sm btn-date active" aria-label="Selecionar data: Hoje">Hoje</button>
                        <button type="button" class="btn btn-sm btn-date" aria-label="Selecionar data: Ontem">Ontem</button>
                        <button type="button" class="btn btn-sm btn-date" aria-label="Selecionar outra data">Outro...</button>
                    </div>

                    <div class="mb-3 dropdown-field" id="sourceAccountDropdown">
                        <div class="wallet-chip" aria-label="Conta de origem selecionada" tabindex="0" role="button" data-default-text='<i class="fas fa-wallet me-2" aria-hidden="true"></i>Conta Origem'>
                            <i class="fas fa-wallet me-2" aria-hidden="true"></i>Conta Origem
                        </div>
                        <div class="custom-dropdown" role="listbox" aria-label="Lista de contas de origem">
                            <c:forEach var="account" items="${accounts}">
                                <div class="custom-dropdown-item" role="option">
                                    <i class="fas ${account.icon} me-2" aria-hidden="true"></i>${account.name}
                                </div>
                            </c:forEach>

                        </div>
                    </div>

                    <div class="mb-3 dropdown-field" id="destinationAccountDropdown">
                        <div class="wallet-chip" aria-label="Conta de destino selecionada" tabindex="0" role="button" data-default-text='<i class="fas fa-wallet me-2" aria-hidden="true"></i>Conta Destino'>
                            <i class="fas fa-wallet me-2" aria-hidden="true"></i>Conta Destino
                        </div>
                        <div class="custom-dropdown" role="listbox" aria-label="Lista de contas de destino">
                            <!-- Assuming dynamic accounts, use JSTL here -->
                            <c:forEach var="account" items="${accounts}">
                                <div class="custom-dropdown-item" role="option">
                                    <i class="fas ${account.icon} me-2" aria-hidden="true"></i>${account.name}
                                </div>
                            </c:forEach>
                        </div>
                    </div>

                    <div class="mb-3">
                        <input type="text" class="form-control" placeholder="Descrição" aria-label="Descrição da transferência">
                    </div>

                    <div class="mb-3">
                        <input type="file" id="fileInput" style="display: none;" multiple aria-label="Anexar arquivos">
                        <button type="button" class="btn btn-link p-0" id="attachFileBtn" aria-label="Anexar arquivo">
                            <i class="fas fa-paperclip me-2" aria-hidden="true"></i>Anexar Arquivo
                        </button>
                    </div>

                    <div class="mb-3 text-end">
                        <a href="#" class="btn btn-link" id="moreDetailsBtn" aria-expanded="false" aria-controls="moreDetailsSection">Mais detalhes <i class="fas fa-chevron-right" aria-hidden="true"></i></a>
                    </div>

                    <div id="moreDetailsSection" style="display: none;">
                        <div class="mb-3">
                            <textarea class="form-control" placeholder="Observação" rows="3" aria-label="Observações adicionais"></textarea>
                        </div>

                        <div class="mb-3 d-flex justify-content-between align-items-center">
                            <label class="form-check-label" for="fixedTransferCheck">Transferência fixa</label>
                            <div class="form-check form-switch">
                                <input class="form-check-input" type="checkbox" id="fixedTransferCheck" aria-label="Marcar como transferência fixa">
                            </div>
                        </div>

                        <div class="mb-3 d-flex justify-content-between align-items-center">
                            <label class="form-check-label" for="repeatCheck">Repetir</label>
                            <div class="form-check form-switch">
                                <input class="form-check-input" type="checkbox" id="repeatCheck" aria-label="Repetir transferência">
                            </div>
                        </div>

                        <div class="mb-3" id="repeatOptionsSection" style="display: none;">
                            <label for="repeatFrequency" class="form-label">Frequência: </label>
                            <select class="form-select" id="repeatFrequency" aria-label="Frequência de repetição">
                                <option value="diariamente">Diariamente</option>
                                <option value="semanalmente">Semanalmente</option>
                                <option value="mensalmente">Mensalmente</option>
                                <option value="anualmente">Anualmente</option>
                            </select>
                        </div>
                    </div>
                </form>
            </div>

            <div class="modal-footer">
                <button type="button" class="btn btn-save-and-new" aria-label="Salvar e criar nova transferência">SALVAR E CRIAR NOVA</button>
                <button type="button" class="btn btn-save" aria-label="Salvar transferência">SALVAR</button>
            </div>
        </div>
    </div>
</div>
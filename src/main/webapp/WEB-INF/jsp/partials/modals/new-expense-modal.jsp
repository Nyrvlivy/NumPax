<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c" %>
<div class="modal expense-modal" tabindex="-1">
    <div class="modal-dialog">
        <div class="modal-content">

            <div class="modal-header">
                <h5 class="modal-title">Nova Despesa</h5>
                <button type="button" class="btn-close" aria-label="Fechar"></button>
            </div>

            <div class="modal-body">
                <form>

                    <div class="mb-3 value-input-container">
                        <span class="value-input-prefix" aria-hidden="true">R$</span>
                        <input type="text" class="value-input" value="0,00" aria-label="Valor da despesa">
                        <span class="currency-suffix">BRL</span>
                    </div>
                    <div class="form-text text-danger" aria-live="polite">Deve ter um valor maior que 0</div>

                    <div class="mb-3 d-flex justify-content-between align-items-center">
                        <label class="form-check-label" for="paidCheck">Foi paga</label>
                        <div class="form-check form-switch">
                            <input class="form-check-input" type="checkbox" id="paidCheck" aria-label="Marcar como paga">
                        </div>
                    </div>

                    <div class="mb-3">
                        <button type="button" class="btn btn-sm btn-date active" aria-label="Selecionar data: Hoje">Hoje</button>
                        <button type="button" class="btn btn-sm btn-date" aria-label="Selecionar data: Ontem">Ontem</button>
                        <button type="button" class="btn btn-sm btn-date" aria-label="Selecionar outra data">Outro...</button>
                    </div>

                    <div class="mb-3">
                        <input type="text" class="form-control" placeholder="Descrição" aria-label="Descrição da despesa">
                    </div>

                    <div class="mb-3 dropdown-field" id="categoryDropdown">
                        <div class="category-chip" aria-label="Categoria selecionada" tabindex="0" role="button" data-default-text='<i class="fas fa-tag me-2" aria-hidden="true"></i>Categoria'>
                            <i class="fas fa-tag me-2" aria-hidden="true"></i>Categoria
                        </div>
                        <div class="custom-dropdown" role="listbox" aria-label="Lista de categorias">
                            <div class="custom-dropdown-item" role="option">
                                <i class="fas fa-utensils me-2" aria-hidden="true"></i>Alimentação
                            </div>
                            <div class="custom-dropdown-item" role="option">
                                <i class="fas fa-home me-2" aria-hidden="true"></i>Moradia
                            </div>
                            <div class="custom-dropdown-item" role="option">
                                <i class="fas fa-car me-2" aria-hidden="true"></i>Transporte
                            </div>
                        </div>
                    </div>

                    <div class="mb-3 dropdown-field" id="sourceAccountDropdown">
                        <div class="wallet-chip" aria-label="Conta de origem selecionada" tabindex="0" role="button" data-default-text='<i class="fas fa-wallet me-2" aria-hidden="true"></i>Conta Origem'>
                            <i class="fas fa-wallet me-2" aria-hidden="true"></i>Conta Origem
                        </div>
                        <div class="custom-dropdown" role="listbox" aria-label="Lista de contas de origem">
                            <div class="custom-dropdown-item" role="option">
                                <i class="fas fa-wallet me-2" aria-hidden="true"></i>Carteira
                            </div>
                            <div class="custom-dropdown-item" role="option">
                                <i class="fas fa-credit-card me-2" aria-hidden="true"></i>Cartão de Crédito
                            </div>
                            <div class="custom-dropdown-item" role="option">
                                <i class="fas fa-university me-2" aria-hidden="true"></i>Conta Bancária
                            </div>
                        </div>
                    </div>

                    <div class="mb-3">
                        <input type="text" class="form-control" placeholder="Para Onde Foi" aria-label="Destino da despesa">
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
                            <label class="form-check-label" for="fixedExpenseCheck">Despesa fixa</label>
                            <div class="form-check form-switch">
                                <input class="form-check-input" type="checkbox" id="fixedExpenseCheck" aria-label="Marcar como despesa fixa">
                            </div>
                        </div>

                        <div class="mb-3 d-flex justify-content-between align-items-center">
                            <label class="form-check-label" for="repeatCheck">Repetir</label>
                            <div class="form-check form-switch">
                                <input class="form-check-input" type="checkbox" id="repeatCheck" aria-label="Repetir despesa">
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
                <button type="button" class="btn btn-save-and-new" aria-label="Salvar e criar nova despesa">SALVAR E CRIAR NOVA</button>
                <button type="button" class="btn btn-save" aria-label="Salvar despesa">SALVAR</button>
            </div>
        </div>
    </div>
</div>
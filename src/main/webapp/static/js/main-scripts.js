document.addEventListener('DOMContentLoaded', function() {
    // Definir a URL do servlet usando contextPath
    const transactionsUrl = contextPath + '/transactions';
    const accountsUrl = contextPath + '/accounts'; // Ajuste conforme mapeamento
    const premiumUrl = contextPath + '/premium';
    const dashboardUrl = contextPath + '/dashboard';
    const reportsUrl = contextPath + '/reports';
    const optionsUrl = contextPath + '/options';
    const configsUrl = contextPath + '/configs';
    const helpcenterUrl = contextPath + '/helpcenter';

    // Função para carregar conteúdo
    function loadContent(pageUrl) {
        fetch(pageUrl)
            .then(response => {
                if (!response.ok) {
                    throw new Error('Network response was not ok');
                }
                return response.text();
            })
            .then(html => {
                document.getElementById('contentContainer').innerHTML = html;
                adjustMainContentBackground();
                initializePageFunctions();
            })
            .catch(error => {
                console.error('Error loading content:', error);
                // Opcional: carregar uma página de erro personalizada
                document.getElementById('contentContainer').innerHTML = '<p> ⚠️ Em desenvolvimento 🚧</p>';
                document.querySelector('.main-content').classList.remove('no-background');
            });
    }

    // Função para ajustar o fundo da main-content
    function adjustMainContentBackground() {
        const mainContent = document.querySelector('.main-content');
        const emptyView = document.getElementById('contentContainer').querySelector('[data-empty="true"]');

        if (emptyView) {
            mainContent.classList.add('no-background');
        } else {
            mainContent.classList.remove('no-background');
        }
    }

    // Carregar a página de transações por padrão
    loadContent(transactionsUrl);

    // Inicializar o modal de desenvolvimento
    var underDevelopmentModal = new bootstrap.Modal(document.getElementById('underDevelopmentModal'));

    // Adicionar ouvintes de eventos aos links da sidebar
    document.querySelectorAll('.sidebar a').forEach(link => {
        link.addEventListener('click', function(e) {
            e.preventDefault();
            document.querySelectorAll('.sidebar a').forEach(l => l.classList.remove('active'));
            this.classList.add('active');

            const href = this.getAttribute('href');

            switch(href) {
                case '#transacoes':
                    loadContent(transactionsUrl);
                    break;
                case '#contas':
                    loadContent(accountsUrl);
                    break;
                case '#premium':
                    loadContent(premiumUrl);
                    break;
                case '#dashboard':
                    loadContent(dashboardUrl);
                    break;
                case '#relatorios':
                    loadContent(reportsUrl);
                    break;
                case '#opcoes':
                    loadContent(optionsUrl);
                    break;
                case '#configuracoes':
                    loadContent(configsUrl);
                    break;
                case '#ajuda':
                    loadContent(helpcenterUrl);
                    break;
                case '#':
                    underDevelopmentModal.show();
                    break;
                default:
                    console.log('Clicked link:', href);
            }
        });
    });

    // Simular o clique no link de transações para carregar o conteúdo inicial
    document.querySelector('.sidebar a[href="#transacoes"]').click();

    // Adicionar ouvinte de eventos ao botão de inscrição no modal de desenvolvimento
    document.querySelector('#underDevelopmentModal .btn-primary').addEventListener('click', function() {
        console.log('Subscribe button clicked');
        // Adicione sua lógica de inscrição aqui
        underDevelopmentModal.hide();
    });

    // Função para inicializar funções específicas da página
    function initializePageFunctions() {
        const openExpenseModalBtn = document.getElementById('openExpenseModalBtn');
        const openIncomeModalBtn = document.getElementById('openIncomeModalBtn');
        const openTransferModalBtn = document.getElementById('openTransferModalBtn');
        const modalContainer = document.getElementById('modalContainer');

        if (openExpenseModalBtn) {
            openExpenseModalBtn.addEventListener('click', function() {
                loadModal(contextPath + '/static/modals/new-expense-modal.jsp'); // Use JSP para modais
            });
        }

        if (openIncomeModalBtn) {
            openIncomeModalBtn.addEventListener('click', function() {
                loadModal(contextPath + '/static/modals/new-income-modal.jsp');
            });
        }

        if (openTransferModalBtn) {
            openTransferModalBtn.addEventListener('click', function() {
                loadModal(contextPath + '/static/modals/new-transfer-modal.jsp');
            });
        }
    }

    // Função para carregar modais via AJAX
    function loadModal(modalFile) {
        fetch(modalFile)
            .then(response => response.text())
            .then(html => {
                modalContainer.innerHTML = html;
                const modal = modalContainer.querySelector('.modal');
                const backdrop = document.createElement('div');
                backdrop.className = 'modal-backdrop show';
                document.body.appendChild(backdrop);

                setTimeout(() => {
                    modal.classList.add('show');
                }, 10);

                initializeModalFunctions(modal, backdrop);
            })
            .catch(error => {
                console.error('Error loading modal:', error);
            });
    }

    // Função para inicializar funcionalidades dos modais
    function initializeModalFunctions(modal, backdrop) {
        const closeModalBtn = modal.querySelector('.btn-close');
        const saveBtn = modal.querySelector('.btn-save');
        const saveAndNewBtn = modal.querySelector('.btn-save-and-new');
        const form = modal.querySelector('form');

        // Função para fechar o modal
        function closeModal() {
            modal.classList.remove('show');
            backdrop.classList.remove('show');
            setTimeout(() => {
                modalContainer.innerHTML = ''; // Remove modal do DOM após a animação
                document.body.removeChild(backdrop);
            }, 300);
        }

        // Fechar o modal ao clicar no backdrop
        backdrop.addEventListener('click', closeModal);

        // Prevenir fechamento ao clicar dentro do modal
        modal.addEventListener('click', (e) => e.stopPropagation());

        // Adicionar ouvinte de eventos ao botão de fechar
        closeModalBtn.addEventListener('click', closeModal);

        // Adicionar ouvinte de eventos ao botão de salvar
        saveBtn.addEventListener('click', function() {
            // Adicione sua lógica de salvamento aqui
            setTimeout(() => {
                showSaveNotification();
                closeModal();
            }, 500);
        });

        // Adicionar ouvinte de eventos ao botão de salvar e adicionar novo
        if (saveAndNewBtn) {
            saveAndNewBtn.addEventListener('click', function() {
                // Adicione sua lógica de salvamento aqui
                setTimeout(() => {
                    showSaveNotification();
                    form.reset();
                }, 500);
            });
        }

        // Funcionalidade de input de arquivos
        const fileInput = modal.querySelector('#fileInput');
        const attachFileBtn = modal.querySelector('#attachFileBtn');
        if (fileInput && attachFileBtn) {
            attachFileBtn.addEventListener('click', function() {
                fileInput.click();
            });

            fileInput.addEventListener('change', function() {
                if (this.files.length > 0) {
                    const fileNames = Array.from(this.files).map(file => file.name).join(', ');
                    attachFileBtn.innerHTML = `<i class="fas fa-paperclip me-2"></i>${fileNames}`;
                } else {
                    attachFileBtn.innerHTML = `<i class="fas fa-paperclip me-2"></i>Anexar Arquivo`;
                }
            });
        }

        // Funcionalidade de botões de data
        modal.querySelectorAll('.btn-date').forEach(button => {
            button.addEventListener('click', function() {
                modal.querySelector('.btn-date.active').classList.remove('active');
                this.classList.add('active');
            });
        });

        // Funcionalidade de dropdown personalizado para categorias e contas
        modal.querySelectorAll('.dropdown-field').forEach(dropdown => {
            const chipElement = dropdown.querySelector('.category-chip, .wallet-chip');
            const customDropdown = dropdown.querySelector('.custom-dropdown');

            // Alternar visibilidade do dropdown ao clicar no chip
            chipElement.addEventListener('click', function(e) {
                e.stopPropagation();
                const isActive = customDropdown.classList.contains('show');

                // Fechar outros dropdowns abertos
                modal.querySelectorAll('.custom-dropdown.show').forEach(openDropdown => {
                    openDropdown.classList.remove('show');
                });

                if (!isActive) {
                    customDropdown.classList.add('show');
                }
            });

            // Lidar com a seleção de itens do dropdown
            dropdown.querySelectorAll('.custom-dropdown-item').forEach(item => {
                item.addEventListener('click', function(e) {
                    e.stopPropagation();

                    // Lidar com a opção "Criar novo destino" para "Para Onde Foi"
                    if (this.id === 'addNewDestination') {
                        // Solicitar ao usuário o novo destino
                        const newDestination = prompt('Insira o nome do novo destino:');
                        if (newDestination) {
                            // Criar novo item no dropdown
                            const newOption = document.createElement('div');
                            newOption.classList.add('custom-dropdown-item');
                            newOption.setAttribute('role', 'option');
                            newOption.innerHTML = `<i class="fas fa-map-marker-alt me-2" aria-hidden="true"></i>${newDestination}`;

                            // Inserir a nova opção antes de "Criar novo destino"
                            customDropdown.insertBefore(newOption, this);

                            // Adicionar ouvinte de eventos à nova opção
                            newOption.addEventListener('click', function(e) {
                                e.stopPropagation();
                                chipElement.innerHTML = this.innerHTML;
                                customDropdown.classList.remove('show');
                                addCloseButton(chipElement);
                            });

                            // Atualizar chipElement com o novo destino
                            chipElement.innerHTML = newOption.innerHTML;
                            addCloseButton(chipElement);
                        }
                    } else {
                        // Atualizar chipElement com o item selecionado
                        chipElement.innerHTML = this.innerHTML;
                        customDropdown.classList.remove('show');
                        addCloseButton(chipElement);
                    }
                });
            });
        });

        // Função para adicionar um botão de fechar ao chip
        function addCloseButton(chipElement) {
            const closeButton = document.createElement('button');
            closeButton.type = 'button';
            closeButton.className = 'btn-close btn-close-white btn-sm ms-2';
            closeButton.setAttribute('aria-label', 'Remove selection');
            closeButton.addEventListener('click', function(e) {
                e.stopPropagation();
                chipElement.innerHTML = chipElement.dataset.defaultText;
            });
            chipElement.appendChild(closeButton);
        }

        // Fechar dropdowns ao clicar fora
        document.addEventListener('click', function(e) {
            if (!e.target.closest('.dropdown-field')) {
                modal.querySelectorAll('.custom-dropdown.show').forEach(dropdown => {
                    dropdown.classList.remove('show');
                });
            }
        });

        // Formatar o valor como moeda
        const valueInputs = modal.querySelectorAll('.value-input');
        valueInputs.forEach(valueInput => {
            valueInput.addEventListener('focus', function() {
                if (this.value === '0,00') {
                    this.value = '';
                }
            });

            valueInput.addEventListener('blur', function() {
                if (this.value === '') {
                    this.value = '0,00';
                }
            });

            valueInput.addEventListener('input', function(e) {
                let value = e.target.value.replace(/\D/g, '');
                if (value === '') {
                    e.target.value = '';
                    return;
                }
                value = (value / 100).toFixed(2) + '';
                value = value.replace(".", ",");
                value = value.replace(/(\d)(?=(\d{3})+(?!\d))/g, "$1.");
                e.target.value = value;
            });
        });

        // Configurar Flatpickr para seleção de data
        flatpickr.localize(flatpickr.l10ns.pt);
        const fp = flatpickr(".btn-date:last-child", {
            dateFormat: "d/m/Y",
            defaultDate: "today",
            onChange: function(selectedDates, dateStr, instance) {
                modal.querySelectorAll('.btn-date').forEach(btn => btn.classList.remove('active'));
                instance.element.classList.add('active');
                instance.element.textContent = dateStr;
            },
            onClose: function(selectedDates, dateStr, instance) {
                const today = new Date();
                const yesterday = new Date(Date.now() - 86400000);
                if (dateStr === instance.formatDate(today, "d/m/Y")) {
                    modal.querySelector('.btn-date:first-child').click();
                } else if (dateStr === instance.formatDate(yesterday, "d/m/Y")) {
                    modal.querySelector('.btn-date:nth-child(2)').click();
                }
            }
        });

        // Garantir que o calendário abra ao clicar em "Outro..."
        modal.querySelector('.btn-date:last-child').addEventListener('click', function(e) {
            e.preventDefault();
            fp.open();
        });

        // Toggle da seção de mais detalhes
        const moreDetailsBtn = modal.querySelector('#moreDetailsBtn');
        const moreDetailsSection = modal.querySelector('#moreDetailsSection');
        const repeatCheck = modal.querySelector('#repeatCheck');
        const repeatOptionsSection = modal.querySelector('#repeatOptionsSection');
        const repeatInput = modal.querySelector('#repeatOptionsSection input[type="number"]');

        if (moreDetailsBtn && moreDetailsSection) {
            moreDetailsBtn.addEventListener('click', function(e) {
                e.preventDefault();
                const isExpanded = this.getAttribute('aria-expanded') === 'true';
                moreDetailsSection.style.display = isExpanded ? 'none' : 'block';
                this.setAttribute('aria-expanded', !isExpanded);
                this.innerHTML = !isExpanded ? 'Menos detalhes <i class="fas fa-chevron-up"></i>' : 'Mais detalhes <i class="fas fa-chevron-right"></i>';
            });
        }

        if (repeatCheck && repeatOptionsSection) {
            repeatCheck.addEventListener('change', function() {
                repeatOptionsSection.style.display = this.checked ? 'flex' : 'none';
            });
        }

        if (repeatInput) {
            repeatInput.addEventListener('input', function() {
                if (this.value < 1) {
                    this.value = 1;
                }
            });
        }

        // Funcionalidade de tags
        const tagsDropdown = modal.querySelector('#tagsDropdown');
        const selectedTags = modal.querySelector('#selectedTags');
        const tagsInput = modal.querySelector('#tagsInput');

        if (tagsDropdown && selectedTags && tagsInput) {
            const tagItems = tagsDropdown.querySelectorAll('.custom-dropdown-item');

            tagItems.forEach(item => {
                item.addEventListener('click', function() {
                    const tagValue = this.getAttribute('data-value');
                    const tagText = this.textContent;

                    if (!tagsInput.value.includes(tagValue)) {
                        if (tagsInput.value) {
                            tagsInput.value += ',';
                        }
                        tagsInput.value += tagValue;

                        const tagSpan = document.createElement('span');
                        tagSpan.className = 'badge bg-secondary me-1';
                        tagSpan.innerHTML = `${tagText} <button type="button" class="btn-close btn-close-white btn-sm" aria-label="Remove ${tagText}"></button>`;
                        tagSpan.querySelector('.btn-close').addEventListener('click', function(e) {
                            e.stopPropagation();
                            tagSpan.remove();
                            tagsInput.value = tagsInput.value.split(',').filter(t => t !== tagValue).join(',');
                            updateSelectedTags();
                        });

                        selectedTags.appendChild(tagSpan);
                        updateSelectedTags();
                    }
                });
            });

            selectedTags.addEventListener('click', function(e) {
                e.stopPropagation();
                tagsDropdown.querySelector('.custom-dropdown').classList.toggle('show');
            });

            function updateSelectedTags() {
                if (selectedTags.querySelectorAll('.badge').length > 0) {
                    selectedTags.querySelector('.text-muted')?.remove();
                } else {
                    selectedTags.innerHTML = '<span class="text-muted">Selecione as tags...</span>';
                }
            }
        }
    }

    // Função para exibir uma notificação temporária de salvamento
    function showSaveNotification() {
        const notification = document.createElement('div');
        notification.className = 'save-notification';
        notification.innerText = 'Salvo!';
        document.body.appendChild(notification);

        setTimeout(() => {
            notification.classList.add('show');
        }, 100);

        setTimeout(() => {
            notification.classList.remove('show');
            setTimeout(() => {
                document.body.removeChild(notification);
            }, 300);
        }, 2000);
    }
});
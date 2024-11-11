document.addEventListener('DOMContentLoaded', function () {
    if (typeof contextPath === 'undefined') {
        console.error('contextPath não está definido.');
        return;
    }

    const transactionsUrl = contextPath + '/transactions';
    const accountsUrl = contextPath + '/accounts';
    const premiumUrl = contextPath + '/premium';
    const dashboardUrl = contextPath + '/dashboard';
    const reportsUrl = contextPath + '/reports';
    const optionsUrl = contextPath + '/options';
    const configsUrl = contextPath + '/configs';
    const helpcenterUrl = contextPath + '/helpcenter';
    const newTransactionMenuUrl = contextPath + '/modal/new-transaction-menu';

    const modalContainer = document.getElementById('modalContainer');

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
                document.getElementById('contentContainer').innerHTML = '<p> ⚠️ Em desenvolvimento 🚧</p>';
                document.querySelector('.main-content').classList.remove('no-background');
            });
    }

    function adjustMainContentBackground() {
        const mainContent = document.querySelector('.main-content');
        const emptyView = document.getElementById('contentContainer').querySelector('[data-empty="true"]');

        if (emptyView) {
            mainContent.classList.add('no-background');
        } else {
            mainContent.classList.remove('no-background');
        }
    }

    var underDevelopmentModal = new bootstrap.Modal(document.getElementById('underDevelopmentModal'));

    // Função para carregar e mostrar modais dinamicamente
    function loadModal(modalFile, callback) {
        fetch(modalFile)
            .then(response => {
                if (!response.ok) {
                    throw new Error('Network response was not ok');
                }
                return response.text();
            })
            .then(html => {
                modalContainer.innerHTML = html;
                const modalElement = modalContainer.querySelector('.modal');
                const bootstrapModal = new bootstrap.Modal(modalElement);
                // Inicializar funcionalidades do modal antes de mostrar
                if (callback && typeof callback === 'function') {
                    callback(modalElement, bootstrapModal);
                }
                bootstrapModal.show();
            })
            .catch(error => {
                console.error('Error loading modal:', error);
            });
    }

    function initializeModalFunctions(modal, bootstrapModal) {
        const closeModalBtn = modal.querySelector('.btn-close');
        const saveBtn = modal.querySelector('.btn-save');
        const saveAndNewBtn = modal.querySelector('.btn-save-and-new');
        const form = modal.querySelector('form');

        function closeModal() {
            bootstrapModal.hide();
        }

        if (closeModalBtn) {
            closeModalBtn.addEventListener('click', closeModal);
        }

        if (saveBtn) {
            saveBtn.addEventListener('click', function () {
                // Adicione aqui a lógica de salvamento
                setTimeout(() => {
                    showSaveNotification();
                    closeModal();
                }, 500);
            });
        }

        if (saveAndNewBtn) {
            saveAndNewBtn.addEventListener('click', function () {
                // Adicione aqui a lógica de salvamento
                setTimeout(() => {
                    showSaveNotification();
                    form.reset();
                }, 500);
            });
        }

        // Início das funcionalidades específicas dos modais

        // File input functionality
        const fileInput = modal.querySelector('#fileInput');
        const attachFileBtn = modal.querySelector('#attachFileBtn');
        if (fileInput && attachFileBtn) {
            attachFileBtn.addEventListener('click', function () {
                fileInput.click();
            });

            fileInput.addEventListener('change', function () {
                if (this.files.length > 0) {
                    const fileNames = Array.from(this.files).map(file => file.name).join(', ');
                    attachFileBtn.innerHTML = `<i class="fas fa-paperclip me-2"></i>${fileNames}`;
                } else {
                    attachFileBtn.innerHTML = `<i class="fas fa-paperclip me-2"></i>Anexar Arquivo`;
                }
            });
        }

        // Date buttons functionality
        modal.querySelectorAll('.btn-date').forEach(button => {
            button.addEventListener('click', function () {
                modal.querySelectorAll('.btn-date').forEach(btn => btn.classList.remove('active'));
                this.classList.add('active');
            });
        });

        // Custom dropdown functionality for categories and wallets
        modal.querySelectorAll('.dropdown-field').forEach(dropdown => {
            const chipElement = dropdown.querySelector('.category-chip, .wallet-chip');
            const customDropdown = dropdown.querySelector('.custom-dropdown');

            // Toggle dropdown visibility when the chip is clicked
            chipElement.addEventListener('click', function (e) {
                e.stopPropagation();
                const isActive = customDropdown.classList.contains('show');

                modal.querySelectorAll('.custom-dropdown.show').forEach(openDropdown => {
                    openDropdown.classList.remove('show');
                });

                if (!isActive) {
                    customDropdown.classList.add('show');
                }
            });

            // Handle selection of dropdown items
            customDropdown.querySelectorAll('.custom-dropdown-item').forEach(item => {
                item.addEventListener('click', function (e) {
                    e.stopPropagation();
                    chipElement.innerHTML = this.innerHTML;
                    customDropdown.classList.remove('show');
                    addCloseButton(chipElement);
                });
            });
        });

        // Function to add a close button to the chip element
        function addCloseButton(chipElement) {
            // Remove existing close button if any
            const existingCloseBtn = chipElement.querySelector('.btn-close');
            if (existingCloseBtn) {
                existingCloseBtn.remove();
            }

            const closeButton = document.createElement('button');
            closeButton.type = 'button';
            closeButton.className = 'btn-close btn-close-white btn-sm ms-2';
            closeButton.setAttribute('aria-label', 'Remover seleção');
            closeButton.addEventListener('click', function (e) {
                e.stopPropagation();
                chipElement.innerHTML = chipElement.dataset.defaultText;
            });
            chipElement.appendChild(closeButton);
        }

        // Close dropdowns when clicking outside
        document.addEventListener('click', function (e) {
            if (!e.target.closest('.dropdown-field')) {
                modal.querySelectorAll('.custom-dropdown.show').forEach(dropdown => {
                    dropdown.classList.remove('show');
                });
            }
        });

        // Format the value as currency
        const valueInputs = modal.querySelectorAll('.value-input');
        valueInputs.forEach(valueInput => {
            valueInput.addEventListener('focus', function () {
                if (this.value === '0,00') {
                    this.value = '';
                }
            });

            valueInput.addEventListener('blur', function () {
                if (this.value === '') {
                    this.value = '0,00';
                }
            });

            valueInput.addEventListener('input', function (e) {
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

        // Setup Flatpickr for date selection
        if (typeof flatpickr !== 'undefined') {
            flatpickr.localize(flatpickr.l10ns.pt);
            const dateButtons = modal.querySelectorAll('.btn-date');
            const otherDateButton = dateButtons[dateButtons.length - 1];
            const fp = flatpickr(otherDateButton, {
                dateFormat: "d/m/Y",
                defaultDate: "today",
                onChange: function (selectedDates, dateStr, instance) {
                    dateButtons.forEach(btn => btn.classList.remove('active'));
                    instance.element.classList.add('active');
                    instance.element.textContent = dateStr;
                },
                onClose: function (selectedDates, dateStr, instance) {
                    const today = new Date();
                    const yesterday = new Date(Date.now() - 86400000);
                    if (dateStr === instance.formatDate(today, "d/m/Y")) {
                        dateButtons[0].click();
                    } else if (dateStr === instance.formatDate(yesterday, "d/m/Y")) {
                        dateButtons[1].click();
                    }
                }
            });

            // Ensure the calendar opens when clicking "Outro..."
            otherDateButton.addEventListener('click', function (e) {
                e.preventDefault();
                fp.open();
            });
        } else {
            console.error('Flatpickr library is not loaded.');
        }

        // More details section toggle
        const moreDetailsBtn = modal.querySelector('#moreDetailsBtn');
        const moreDetailsSection = modal.querySelector('#moreDetailsSection');
        if (moreDetailsBtn && moreDetailsSection) {
            moreDetailsBtn.addEventListener('click', function (e) {
                e.preventDefault();
                const isExpanded = this.getAttribute('aria-expanded') === 'true';
                moreDetailsSection.style.display = isExpanded ? 'none' : 'block';
                this.setAttribute('aria-expanded', !isExpanded);
                this.innerHTML = !isExpanded ? 'Menos detalhes <i class="fas fa-chevron-up"></i>' : 'Mais detalhes <i class="fas fa-chevron-right"></i>';
            });
        }

        // Repeat options toggle
        const repeatCheck = modal.querySelector('#repeatCheck');
        const repeatOptionsSection = modal.querySelector('#repeatOptionsSection');
        if (repeatCheck && repeatOptionsSection) {
            repeatCheck.addEventListener('change', function () {
                repeatOptionsSection.style.display = this.checked ? 'block' : 'none';
            });
        }

        // End of modal-specific functionalities
    }

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

    // Manipulação dos links da sidebar
    document.querySelectorAll('.sidebar a').forEach(link => {
        link.addEventListener('click', function (e) {
            console.log('Link clicado:', this.getAttribute('href'));

            if (this.classList.contains('logout-link')) {
                console.log('Logout link clicado. Permitir navegação.');
                return;
            }

            if (this.id === 'novoButton') {
                e.preventDefault();
                loadModal(newTransactionMenuUrl, function (modalElement, bootstrapModalMenu) {
                    const menuOpenExpenseModalBtn = modalElement.querySelector('#menuOpenExpenseModalBtn');
                    const menuOpenIncomeModalBtn = modalElement.querySelector('#menuOpenIncomeModalBtn');
                    const menuOpenTransferModalBtn = modalElement.querySelector('#menuOpenTransferModalBtn');

                    if (menuOpenExpenseModalBtn) {
                        menuOpenExpenseModalBtn.addEventListener('click', function () {
                            bootstrapModalMenu.hide();
                            loadModal(contextPath + '/modal/new-expense', initializeModalFunctions);
                        });
                    }

                    if (menuOpenIncomeModalBtn) {
                        menuOpenIncomeModalBtn.addEventListener('click', function () {
                            bootstrapModalMenu.hide();
                            loadModal(contextPath + '/modal/new-income', initializeModalFunctions);
                        });
                    }

                    if (menuOpenTransferModalBtn) {
                        menuOpenTransferModalBtn.addEventListener('click', function () {
                            bootstrapModalMenu.hide();
                            loadModal(contextPath + '/modal/new-transfer', initializeModalFunctions);
                        });
                    }
                });
                return;
            }

            e.preventDefault();
            document.querySelectorAll('.sidebar a').forEach(l => l.classList.remove('active'));
            this.classList.add('active');

            const href = this.getAttribute('href');

            switch (href) {
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

    // Inicializar funções na carga da página
    initializePageFunctions();

    function initializePageFunctions() {
        const openExpenseModalBtn = document.getElementById('openExpenseModalBtn');
        const openIncomeModalBtn = document.getElementById('openIncomeModalBtn');
        const openTransferModalBtn = document.getElementById('openTransferModalBtn');

        if (openExpenseModalBtn) {
            openExpenseModalBtn.addEventListener('click', function () {
                loadModal(contextPath + '/modal/new-expense', initializeModalFunctions);
            });
        }

        if (openIncomeModalBtn) {
            openIncomeModalBtn.addEventListener('click', function () {
                loadModal(contextPath + '/modal/new-income', initializeModalFunctions);
            });
        }

        if (openTransferModalBtn) {
            openTransferModalBtn.addEventListener('click', function () {
                loadModal(contextPath + '/modal/new-transfer', initializeModalFunctions);
            });
        }
    }

    window.alterarLinhasPorPagina = function (qtd) {
        const url = new URL(window.location.href);
        url.searchParams.set('linhasPorPagina', qtd);
        url.searchParams.set('page', 1);
        window.location.href = url.toString();
    };
});
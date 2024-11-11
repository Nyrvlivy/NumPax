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
                bootstrapModal.show();

                // Chama o callback após o modal ser mostrado para adicionar event listeners
                if (callback && typeof callback === 'function') {
                    callback(modalElement, bootstrapModal);
                }
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
                setTimeout(() => {
                    showSaveNotification();
                    closeModal();
                }, 500);
            });
        }

        if (saveAndNewBtn) {
            saveAndNewBtn.addEventListener('click', function () {
                setTimeout(() => {
                    showSaveNotification();
                    form.reset();
                }, 500);
            });
        }

        // Adicionar lógica específica para o modal aqui (inputs, dropdowns, etc.)
        // ...
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
                loadModal(newTransactionMenuUrl, function(modalElement, bootstrapModalMenu) {
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

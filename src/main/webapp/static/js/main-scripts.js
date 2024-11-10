document.addEventListener('DOMContentLoaded', function() {
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

    document.querySelectorAll('.sidebar a').forEach(link => {
        link.addEventListener('click', function(e) {
            console.log('Link clicado:', this.getAttribute('href'));

            if (this.classList.contains('logout-link')) {
                console.log('Logout link clicado. Permitir navegação.');
                return;
            }

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

    const underDevModal = document.getElementById('underDevelopmentModal');
    if (underDevModal) {
        const subscribeBtn = underDevModal.querySelector('.btn-primary');
        if (subscribeBtn) {
            subscribeBtn.addEventListener('click', function() {
                console.log('Subscribe button clicked');
                underDevelopmentModal.hide();
            });
        }
    }

    function initializePageFunctions() {
        const openExpenseModalBtn = document.getElementById('openExpenseModalBtn');
        const openIncomeModalBtn = document.getElementById('openIncomeModalBtn');
        const openTransferModalBtn = document.getElementById('openTransferModalBtn');
        const modalContainer = document.getElementById('modalContainer');

        if (openExpenseModalBtn) {
            openExpenseModalBtn.addEventListener('click', function() {
                loadModal(contextPath + '/static/modals/new-expense-modal.jsp');
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

    function loadModal(modalFile) {
        const modalContainer = document.getElementById('modalContainer');
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

    function initializeModalFunctions(modal, backdrop) {
        const closeModalBtn = modal.querySelector('.btn-close');
        const saveBtn = modal.querySelector('.btn-save');
        const saveAndNewBtn = modal.querySelector('.btn-save-and-new');
        const form = modal.querySelector('form');

        function closeModal() {
            modal.classList.remove('show');
            backdrop.classList.remove('show');
            setTimeout(() => {
                modal.parentElement.removeChild(modal);
                document.body.removeChild(backdrop);
            }, 300);
        }

        backdrop.addEventListener('click', closeModal);

        modal.addEventListener('click', (e) => e.stopPropagation());

        if (closeModalBtn) {
            closeModalBtn.addEventListener('click', closeModal);
        }

        if (saveBtn) {
            saveBtn.addEventListener('click', function() {
                setTimeout(() => {
                    showSaveNotification();
                    closeModal();
                }, 500);
            });
        }

        if (saveAndNewBtn) {
            saveAndNewBtn.addEventListener('click', function() {
                setTimeout(() => {
                    showSaveNotification();
                    form.reset();
                }, 500);
            });
        }

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

    window.alterarLinhasPorPagina = function(qtd) {
        const url = new URL(window.location.href);
        url.searchParams.set('linhasPorPagina', qtd);
        url.searchParams.set('page', 1);
        window.location.href = url.toString();
    };
});

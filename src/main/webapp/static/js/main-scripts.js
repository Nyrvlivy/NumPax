document.addEventListener('DOMContentLoaded', function() {
    // Definir a URL do servlet usando contextPath
    const contextPath = window.location.pathname.substring(0, window.location.pathname.indexOf("/", 1));
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
    // loadContent(transactionsUrl); // Removido para evitar carregamento duplicado

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
    // document.querySelector('.sidebar a[href="#transacoes"]').click(); // Removido para evitar carregamento duplicado

    // Adicionar ouvinte de eventos ao botão de inscrição no modal de desenvolvimento
    const underDevModal = document.getElementById('underDevelopmentModal');
    if (underDevModal) {
        const subscribeBtn = underDevModal.querySelector('.btn-primary');
        if (subscribeBtn) {
            subscribeBtn.addEventListener('click', function() {
                console.log('Subscribe button clicked');
                // Adicione sua lógica de inscrição aqui
                underDevelopmentModal.hide();
            });
        }
    }

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
                modal.parentElement.removeChild(modal);
                document.body.removeChild(backdrop);
            }, 300);
        }

        // Fechar o modal ao clicar no backdrop
        backdrop.addEventListener('click', closeModal);

        // Prevenir fechamento ao clicar dentro do modal
        modal.addEventListener('click', (e) => e.stopPropagation());

        // Adicionar ouvinte de eventos ao botão de fechar
        if (closeModalBtn) {
            closeModalBtn.addEventListener('click', closeModal);
        }

        // Adicionar ouvinte de eventos ao botão de salvar
        if (saveBtn) {
            saveBtn.addEventListener('click', function() {
                // Adicione sua lógica de salvamento aqui
                setTimeout(() => {
                    showSaveNotification();
                    closeModal();
                }, 500);
            });
        }

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

        // Função para adicionar um botão de fechar ao chip (se necessário)
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

    window.alterarLinhasPorPagina = function(qtd) {
        const url = new URL(window.location.href);
        url.searchParams.set('linhasPorPagina', qtd);
        url.searchParams.set('page', 1);
        window.location.href = url.toString();
    };
});
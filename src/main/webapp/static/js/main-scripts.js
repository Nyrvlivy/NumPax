document.addEventListener('DOMContentLoaded', function() {
    // Defina a URL do servlet como uma variável direta
    const transactionsUrl = '/numpax_war_exploded/transactions';

    // Function to load content
    function loadContent(page) {
        fetch(page)
            .then(response => response.text())
            .then(html => {
                document.getElementById('contentContainer').innerHTML = html;
                initializePageFunctions();
            })
            .catch(error => {
                console.error('Error loading content:', error);
            });
    }

    // Load transactions page by default
    loadContent(transactionsUrl);

    var underDevelopmentModal = new bootstrap.Modal(document.getElementById('underDevelopmentModal'));

    document.querySelectorAll('.sidebar a').forEach(link => {
        link.addEventListener('click', function(e) {
            e.preventDefault();
            document.querySelectorAll('.sidebar a').forEach(l => l.classList.remove('active'));
            this.classList.add('active');

            if (this.getAttribute('href') === '#transacoes') {
                loadContent(transactionsUrl);
            } else if (this.getAttribute('href') === '#contas') {
                loadContent('accounts-page.jsp');
            } else if (this.getAttribute('href') === '#') {
                underDevelopmentModal.show();
            } else {
                console.log('Clicked link:', this.getAttribute('href'));
            }
        });
    });

    document.querySelector('.sidebar a[href="#transacoes"]').click();


// Add event listener to the Subscribe button in the modal
    document.querySelector('#underDevelopmentModal .btn-primary').addEventListener('click', function() {
        console.log('Subscribe button clicked');
        // Add your subscription logic here
        underDevelopmentModal.hide();
    });

    // Function to initialize page-specific functions
    function initializePageFunctions() {
        const openExpenseModalBtn = document.getElementById('openExpenseModalBtn');
        const openIncomeModalBtn = document.getElementById('openIncomeModalBtn');
        const openTransferModalBtn = document.getElementById('openTransferModalBtn');
        const modalContainer = document.getElementById('modalContainer');

        if (openExpenseModalBtn) {
            openExpenseModalBtn.addEventListener('click', function() {
                loadModal('new-expense-modal.html');
            });
        }

        if (openIncomeModalBtn) {
            openIncomeModalBtn.addEventListener('click', function() {
                loadModal('new-income-modal.html');
            });
        }
        if (openTransferModalBtn) {
            openTransferModalBtn.addEventListener('click', function() {
                loadModal('new-transfer-modal.html');
            });
        }
    }

    function loadModal(modalFile) {
        fetch(modalFile)
            .then(response => response.text())
            .then(html => {
                modalContainer.innerHTML = html;
                const modal = modalContainer.querySelector('.modal');
                const backdrop = document.createElement('div');
                backdrop.className = 'modal-backdrop';
                document.body.appendChild(backdrop);

                setTimeout(() => {
                    backdrop.classList.add('show');
                    modal.classList.add('show');
                }, 10);

                initializeModalFunctions(modal, backdrop);
            });
    }

    function initializeModalFunctions(modal, backdrop) {
        const closeModalBtn = modal.querySelector('.btn-close');
        const saveBtn = modal.querySelector('.btn-save');
        const saveAndNewBtn = modal.querySelector('.btn-save-and-new');
        const form = modal.querySelector('form');

        // Function to close the modal
        function closeModal() {
            modal.classList.remove('show');
            backdrop.classList.remove('show');
            setTimeout(() => {
                modalContainer.innerHTML = ''; // Remove modal from DOM after animation
                document.body.removeChild(backdrop);
            }, 300);
        }

        // Close modal when clicking outside
        backdrop.addEventListener('click', closeModal);

        // Prevent closing when clicking inside the modal
        modal.addEventListener('click', (e) => e.stopPropagation());

        // Add event listener to close button
        closeModalBtn.addEventListener('click', closeModal);

        // Add event listener to save button
        saveBtn.addEventListener('click', function() {
            setTimeout(() => {
                showSaveNotification();
                closeModal();
            }, 500);
        });

        // Add event listener to save and new button if it exists
        if (saveAndNewBtn) {
            saveAndNewBtn.addEventListener('click', function() {
                setTimeout(() => {
                    showSaveNotification();
                    form.reset();
                }, 500);
            });
        }

        // File input functionality
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

        // Date buttons functionality
        modal.querySelectorAll('.btn-date').forEach(button => {
            button.addEventListener('click', function() {
                modal.querySelector('.btn-date.active').classList.remove('active');
                this.classList.add('active');
            });
        });

        // Custom dropdown functionality for categories and wallets
        modal.querySelectorAll('.dropdown-field').forEach(dropdown => {
            const chipElement = dropdown.querySelector('.category-chip, .wallet-chip');
            const customDropdown = dropdown.querySelector('.custom-dropdown');

            // Toggle dropdown visibility when the chip is clicked
            chipElement.addEventListener('click', function(e) {
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
            dropdown.querySelectorAll('.custom-dropdown-item').forEach(item => {
                item.addEventListener('click', function(e) {
                    e.stopPropagation();

                    // Handle "Criar novo destino" option for "Para Onde Foi"
                    if (this.id === 'addNewDestination') {
                        // Prompt user for new destination
                        const newDestination = prompt('Insira o nome do novo destino:');
                        if (newDestination) {
                            // Create new dropdown item
                            const newOption = document.createElement('div');
                            newOption.classList.add('custom-dropdown-item');
                            newOption.setAttribute('role', 'option');
                            newOption.innerHTML = `<i class="fas fa-map-marker-alt me-2" aria-hidden="true"></i>${newDestination}`;

                            // Insert new option before "Criar novo destino"
                            customDropdown.insertBefore(newOption, this);

                            // Add event listener to the new option
                            newOption.addEventListener('click', function(e) {
                                e.stopPropagation();
                                chipElement.innerHTML = this.innerHTML;
                                customDropdown.classList.remove('show');
                                addCloseButton(chipElement);
                            });

                            // Update chipElement with new destination
                            chipElement.innerHTML = newOption.innerHTML;
                            addCloseButton(chipElement);
                        }
                    } else {
                        // Update chipElement with selected item
                        chipElement.innerHTML = this.innerHTML;
                        customDropdown.classList.remove('show');
                        addCloseButton(chipElement);
                    }
                });
            });
        });

        // Function to add a close button to the chip element
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

        // Close dropdowns when clicking outside
        document.addEventListener('click', function(e) {
            if (!e.target.closest('.dropdown-field')) {
                modal.querySelectorAll('.custom-dropdown.show').forEach(dropdown => {
                    dropdown.classList.remove('show');
                });
            }
        });

        // Format the value as currency
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

        // Setup Flatpickr for date selection
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

        // Ensure the calendar opens when clicking "Outro..."
        modal.querySelector('.btn-date:last-child').addEventListener('click', function(e) {
            e.preventDefault();
            fp.open();
        });

        // More details section toggle
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

        // Tags functionality
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

    // Function to show a temporary save notification
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
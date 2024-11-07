-- Inserção das categorias padrão (default)
INSERT ALL
    -- Categorias de INCOME (Receitas)
    INTO Categories (category_id, name, description, icon, category_type, is_active, is_default) 
    VALUES (
        '11111111-2222-3333-4444-555555555555',
        'Salário',
        'Receitas provenientes de salário',
        'salary_icon.png',
        'INCOME',
        1,
        1
    )
    INTO Categories (category_id, name, description, icon, category_type, is_active, is_default)
    VALUES (
        '22222222-2222-3333-4444-555555555555',
        'Freelance',
        'Receitas de trabalhos freelancer',
        'freelance_icon.png',
        'INCOME',
        1,
        1
    )
    
    -- Categorias de EXPENSE (Despesas)
    INTO Categories (category_id, name, description, icon, category_type, is_active, is_default)
    VALUES (
        '33333333-2222-3333-4444-555555555555',
        'Aluguel',
        'Despesas com aluguel',
        'rent_icon.png',
        'EXPENSE',
        1,
        1
    )
    INTO Categories (category_id, name, description, icon, category_type, is_active, is_default)
    VALUES (
        '44444444-2222-3333-4444-555555555555',
        'Alimentação',
        'Despesas com alimentação',
        'food_icon.png',
        'EXPENSE',
        1,
        1
    )
    
    -- Categorias de INVESTMENT (Investimentos)
    INTO Categories (category_id, name, description, icon, category_type, is_active, is_default)
    VALUES (
        '55555555-2222-3333-4444-555555555555',
        'Renda Fixa',
        'Investimentos em renda fixa',
        'fixed_income_icon.png',
        'INVESTMENT',
        1,
        1
    )
    INTO Categories (category_id, name, description, icon, category_type, is_active, is_default)
    VALUES (
        '66666666-2222-3333-4444-555555555555',
        'Renda Variável',
        'Investimentos em renda variável',
        'variable_income_icon.png',
        'INVESTMENT',
        1,
        1
    )
    
    -- Categorias de SAVINGS (Poupança)
    INTO Categories (category_id, name, description, icon, category_type, is_active, is_default)
    VALUES (
        '77777777-2222-3333-4444-555555555555',
        'Reserva de Emergência',
        'Poupança para emergências',
        'emergency_fund_icon.png',
        'SAVINGS',
        1,
        1
    )
    
    -- Categorias de ACCOUNTS (Contas)
    INTO Categories (category_id, name, description, icon, category_type, is_active, is_default)
    VALUES (
        '88888888-2222-3333-4444-555555555555',
        'Conta Corrente',
        'Contas correntes bancárias',
        'checking_account_icon.png',
        'ACCOUNTS',
        1,
        1
    )
    INTO Categories (category_id, name, description, icon, category_type, is_active, is_default)
    VALUES (
        '99999999-2222-3333-4444-555555555555',
        'Conta Poupança',
        'Contas poupança',
        'savings_account_icon.png',
        'ACCOUNTS',
        1,
        1
    )
    
    -- Categorias de TRANSACTIONS (Transações)
    INTO Categories (category_id, name, description, icon, category_type, is_active, is_default)
    VALUES (
        'aaaaaaaa-2222-3333-4444-555555555555',
        'Transferência',
        'Transferências entre contas',
        'transfer_icon.png',
        'TRANSACTIONS',
        1,
        1
    )
    
    -- Categorias de PERSONAL (Pessoal)
    INTO Categories (category_id, name, description, icon, category_type, is_active, is_default)
    VALUES (
        'bbbbbbbb-2222-3333-4444-555555555555',
        'Lazer',
        'Gastos com entretenimento e lazer',
        'leisure_icon.png',
        'PERSONAL',
        1,
        1
    )
SELECT 1 FROM dual; 